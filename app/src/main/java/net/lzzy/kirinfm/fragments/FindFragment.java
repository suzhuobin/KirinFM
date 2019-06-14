package net.lzzy.kirinfm.fragments;

import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.lzzy.kirinfm.R;
import net.lzzy.kirinfm.models.Play;
import net.lzzy.kirinfm.models.PlayBill;
import net.lzzy.kirinfm.utils.AbstractStaticHandler;
import net.lzzy.kirinfm.utils.AppUtils;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * @author Administrator
 */
public class FindFragment extends BaseFragment {
    private List<Play> plays;
    private TextView tvBegin;
    private SeekBar sbProgress;
    private TextView tvFinish;
    private ImageView ivFront;
    private ImageView ivPause;
    private ImageView ivQueen;
    private int position = 0;
    private static IjkMediaPlayer player;
    private List<PlayBill> playBills;
    private List<PlayBill> urls;
    private String playUrl = null;
//    public static FindFragment newInstance( List<PlayBill> playBills,){
//
//    }

    private MyHandler handler = new MyHandler(FindFragment.this);
    private GenericAdapter<Play> adapter;

    private static class MyHandler extends AbstractStaticHandler<FindFragment> {

        MyHandler(FindFragment context) {
            super(context);
        }

        @Override
        public void handleMessage(Message msg, FindFragment findFragment) {
            if (player != null) {
                long pos = player.getCurrentPosition();
                findFragment.setSeekBar(pos);
                findFragment.handler.sendEmptyMessageDelayed(0, 1000);
            }
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_find;
    }

    @Override
    protected void populate() {
        player = AppUtils.getPlayer();
        initView();
        configPlayer();
        playMusic();
        initDots();
    }

    private void initDots() {
        adapter = new GenericAdapter<Play>(getActivity(), R.layout.fragment_find, plays) {
            @Override
            public void populate(ViewHolder viewHolder, Play play) {
                ImageView imageView = viewHolder.getView(R.id.fragment_find_iv_picture);
                Picasso.get().load(play.getCover())
                        .into(imageView);
                viewHolder.setTextView(R.id.fragment_find_name, play.getRadioName());
                viewHolder.setTextView(R.id.fragment_find_tv_title, play.getPlayBillName());
                viewHolder.setTextView(R.id.fragment_find_tv_anchor, play.getBroadcasters());
            }

            @Override
            public boolean persistInsert(Play play) {
                return false;
            }

            @Override
            public boolean persistDelete(Play play) {
                return false;
            }

        };
    }

    private void configPlayer() {
        ivPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int res = playOrPause() ? R.drawable.pause :
                        R.drawable.play;
                ivPause.setImageResource(res);
            }
        });
        ivFront.setOnClickListener(v -> switchMusic(false));
        ivQueen.setOnClickListener(v -> switchMusic(true));
        sbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && player != null) {
                    player.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initView() {
        tvBegin = find(R.id.fragment_find_tv_begin);
        sbProgress = find(R.id.fragment_find_sb_progress);
        tvFinish = find(R.id.fragment_find_tv_finish);
        ivFront = find(R.id.fragment_find_iv_front);
        ivPause = find(R.id.fragment_find_iv_pause);
        ivQueen = find(R.id.fragment_find_iv_queen);
    }

    @Override
    public void search(String kw) {

    }

    public void setSeekBar(long position) {
        sbProgress.setProgress((int) position);
        String pastTime = formatPlayerTime((int) position);
        tvBegin.setText(pastTime);
    }

    private String formatPlayerTime(int max) {
        max /= 1000;
        int min = max / 60;
        int seconds = max % 6;
        return String.format(Locale.CHINA, "%d:02d", min, seconds);
    }

    private boolean playOrPause() {
        if (player.isPlaying()) {
            player.pause();
            return true;
        } else {
            player.start();
            return false;
        }
    }

    private void switchMusic(boolean backward) {
        if (backward) {
            if (position > 0) {
                position--;
            } else {
                Toast.makeText(getContext(), "no previous music!", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            if (position < urls.size() - 1) {
                position++;
            } else {
                Toast.makeText(getContext(), "no already the last music!", Toast.LENGTH_SHORT).show();
                return;
            }

        }
        playUrl = String.valueOf(urls.get(position));
        playMusic();

    }

    private void playMusic() {
        try {
            player.reset();
            player.setDataSource(playUrl);
            player.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(IMediaPlayer iMediaPlayer) {
                    switchMusic(false);
                }
            });
            player.prepareAsync();
            player.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(IMediaPlayer iMediaPlayer) {
                    Toast.makeText(getContext(), "playing", Toast.LENGTH_SHORT).show();
                    iMediaPlayer.start();
                    int max = (int) iMediaPlayer.getDuration();
                    if (max > 0) {
                        sbProgress.setMax(max);
                    }
                    String allTime = formatPlayerTime(max);
                    tvFinish.setText(allTime);
                    handler.sendEmptyMessage(0);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
