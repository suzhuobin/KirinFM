package net.lzzy.kirinfm.fragments;

import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import net.lzzy.kirinfm.R;
import net.lzzy.kirinfm.activities.MainActivity;
import net.lzzy.kirinfm.models.FavoriteFactory;
import net.lzzy.kirinfm.models.Play;
import net.lzzy.kirinfm.models.PlayBill;
import net.lzzy.kirinfm.models.Radio;
import net.lzzy.kirinfm.utils.AbstractStaticHandler;
import net.lzzy.kirinfm.utils.AppUtils;
import net.lzzy.kirinfm.utils.DateTimeUtils;
import net.lzzy.kirinfm.utils.ViewUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * @author Administrator
 */
public class FindFragment extends BaseFragment {
    private static final String ARG_URLS = "ARG_URLS";
    private static final String ARG_PLAYBILLS = "ARG_PLAYBILLS";
    private static final String PLAY_POSITION = "play_Position";
    public static final String RADIO_NAME = "radioName";
    public static final String RADIO_IMG = "radioImg";
    public static final String RADIO_HINT = "radioHint";
    private List<Play> plays;
    private TextView tvBegin;
    private SeekBar sbProgress;
    private TextView tvFinish;
    private ImageView ivFront;
    private ImageView ivPause;
    private ImageView ivQueen;
    private int position = 0;
    private IjkMediaPlayer player;
    private List<PlayBill> playBills = new ArrayList<>();
    private List<String> urls = new ArrayList<>();
    private String playUrl = null;
    private TextView tvLive;
    private Radio radioFavorite;
    private ImageView collect;
    private TextView playBillName;
    private TextView broadcasters;

    public static FindFragment newInstance(List<PlayBill> playBills, List<String> urls,
                                           int playPosition, Radio radioHint) {
        FindFragment fragment = new FindFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PLAYBILLS, (ArrayList<? extends Parcelable>) playBills);
        args.putStringArrayList(ARG_URLS, (ArrayList<String>) urls);
        args.putInt(PLAY_POSITION, playPosition);
        args.putParcelable(RADIO_HINT, radioHint);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        player = AppUtils.getPlayer();
        if (getArguments() != null) {
            radioFavorite = getArguments().getParcelable(RADIO_HINT);
            playBills = getArguments().getParcelableArrayList(ARG_PLAYBILLS);
            urls = getArguments().getStringArrayList(ARG_URLS);
            position = getArguments().getInt(PLAY_POSITION);
            playUrl = urls.get(getArguments().getInt(PLAY_POSITION));
        }
    }

    private MyHandler handler = new MyHandler(FindFragment.this);

    private static class MyHandler extends AbstractStaticHandler<FindFragment> {

        MyHandler(FindFragment context) {
            super(context);
        }

        @Override
        public void handleMessage(Message msg, FindFragment findFragment) {
            if (findFragment.player != null) {
                long pos = findFragment.player.getCurrentPosition();
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
        initView();
        configPlayer();
        playMusic();
        try {
            initDots(playBills.get(position));
        } catch (Exception e) {

        }
    }

    private void initDots(PlayBill playBill) {
        Play play = new Play();
        if (playBill.getBroadcasters().size() > 0) {
            play.setBroadcasters(playBill.getBroadcasters().toString());
        } else {
            play.setBroadcasters("[暂无主持人信息]");
        }
        play.setCover(radioFavorite.getCover());
        play.setPlayBillName(playBill.getTitle());
        play.setRadioName(radioFavorite.getTitle());
        TextView radioName = find(R.id.fragment_find_name);
        radioName.setText(play.getRadioName());
        playBillName.setText(play.getPlayBillName());
        broadcasters.setText(play.getBroadcasters());
    }

    private void configPlayer() {
        ivPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playBills.get(position).isPlayIng()) {
                    Toast.makeText(getContext(), "正在直播", Toast.LENGTH_SHORT).show();
                } else {
                    int res = playOrPause() ? R.drawable.play : R.drawable.pause;
                    ivPause.setImageResource(res);
                }
            }
        });
        ivFront.setOnClickListener(v -> switchMusic(true));
        ivQueen.setOnClickListener(v -> switchMusic(false));
        sbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (playBills.get(position).isPlayIng()) {
                    sbProgress.setEnabled(false);
                } else {
                    sbProgress.setEnabled(true);
                    if (fromUser && player != null) {
                        player.seekTo(progress);
                    }
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
        playBillName = find(R.id.fragment_find_tv_title);
        broadcasters = find(R.id.fragment_find_tv_anchor);
        broadcasters = find(R.id.fragment_find_tv_anchor);
//        collect = find(R.id.fragment_find_collect);
        tvLive = find(R.id.fragment_find_in_live);
        tvBegin = find(R.id.fragment_find_tv_begin);
        sbProgress = find(R.id.fragment_find_sb_progress);
        tvFinish = find(R.id.fragment_find_tv_finish);
        ivFront = find(R.id.fragment_find_iv_front);
        ivPause = find(R.id.fragment_find_iv_pause);
        ivQueen = find(R.id.fragment_find_iv_queen);
        ImageView back = find(R.id.fragment_find_layout_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewUtils.isShowing()) {
                    ViewUtils.show();
                }
                MainActivity.dismissPlay();
                MainActivity.showPlayHint();
            }
        });
        ImageView list = find(R.id.fragment_find_list);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewUtils.isShowing()) {
                    ViewUtils.show();
                } else {
                    ViewUtils.showPlayBill(getContext(), "关闭", radioFavorite.getTitle(),
                            playBills, radioFavorite);
                }
                MainActivity.dismissPlay();
                MainActivity.showPlayHint();

            }
        });

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
        int seconds = max % 60;
        int hour = max / 60;
        int mins = max / 60;
        return String.format(Locale.CHINA, "%d:%02d", min, seconds);
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
                Toast.makeText(getContext(), "it's already the last music", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        playUrl = String.valueOf(urls.get(position));
        playMusic();


    }

    private void playMusic() {
        try {
            if (DateTimeUtils.playIf(playBills.get(position).getStart_time())) {
                try {
                    ivPause.setImageResource(R.drawable.pause);
                    player.reset();
                    if (playBills.get(position).isPlayIng()) {
                        tvLive.setVisibility(View.VISIBLE);
                    } else {
                        tvLive.setVisibility(View.GONE);
                    }
                    boolean is = FavoriteFactory.getInstance().isRadioStarred(String.valueOf(radioFavorite.getContent_id()));
                    int starId = is ? R.drawable.fond : R.drawable.no_fond;
                    PlayBill playBill = playBills.get(position);
                    playBillName.setText(playBill.getTitle());
                    player.setDataSource(playUrl);
                    player.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(IMediaPlayer iMediaPlayer) {
                            Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();
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
            } else {
                Toast.makeText(getContext(), "节目未开始！", Toast.LENGTH_LONG).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
