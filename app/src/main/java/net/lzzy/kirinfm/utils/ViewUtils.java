package net.lzzy.kirinfm.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import net.lzzy.kirinfm.R;
import net.lzzy.kirinfm.activities.MainActivity;
import net.lzzy.kirinfm.connstants.ApiConstants;
import net.lzzy.kirinfm.fragments.FindFragment;
import net.lzzy.kirinfm.models.Announcer;
import net.lzzy.kirinfm.models.PlayBill;
import net.lzzy.kirinfm.models.Radio;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class ViewUtils {
    private static FullScreenDialog dialog;
    private static GenericAdapter<PlayBill> playBillAdapter;
    private static List<PlayBill> playBills = new ArrayList<>();
    private static List<String> urls = new ArrayList<>();
    private static Radio radioHints;

    public static abstract class AbstractQueryHandlerRadio implements SearchView.OnQueryTextListener {
        @Override
        public boolean onQueryTextSubmit(String query) {
            handleQuery(query);
            return true;

        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }

        /**
         * handle query logic
         *
         * @param kw keyword
         * @return end query
         */
        public abstract void handleQuery(String kw);
    }

    public static abstract class AbstractQueryHandler implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            handleQuery(newText);
            return true;
        }

        /**
         * handle query logic
         *
         * @param kw keyword
         * @return end query
         */
        public abstract void handleQuery(String kw);
    }

    public static abstract class AbstractTouchHandler implements View.OnTouchListener {

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return handleTouch(event);
        }

        /**
         * 处理触摸事件
         *
         * @param event 触摸事件对象
         * @return 消费触摸事件吗
         */
        public abstract boolean handleTouch(MotionEvent event);
    }

    /**
     * 判断位置
     *
     * @param targetView
     * @param xAxis
     * @param yAxis
     * @return
     */
    public static boolean isTouchPointInView(View targetView, int xAxis, int yAxis) {
        if (targetView == null) {
            return false;
        }
        int[] location = new int[2];
        targetView.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + targetView.getMeasuredWidth();
        int bottom = top + targetView.getMeasuredHeight();
        return yAxis >= top && yAxis <= bottom && xAxis >= left
                && xAxis <= right;
    }

    public static int px2dp(int pxValue, Context context) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2px(int dpValue, Context context) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static void showPlayBill(Context context, String back, String title,
                                    List<PlayBill> playBillList, Radio radioHint) {
        radioHints = radioHint;
        dialog = new FullScreenDialog(context);
        View view = LayoutInflater.from(context)
                .inflate(R.layout.dialog_play_bill, null, false);
        LinearLayout linearLayout = view.findViewById(R.id.dialog_play_bill_layout_back);
        linearLayout.setOnClickListener(v -> ViewUtils.dismissPlayBill());
        TextView tvBack = view.findViewById(R.id.dialog_play_bill_tv_back);
        tvBack.setText(back);
        TextView tvTitle = view.findViewById(R.id.dialog_play_bill_tv_title);
        tvTitle.setText(title);
        ListView listView = view.findViewById(R.id.dialog_play_bill_lv);
        listView.setEmptyView(view.findViewById(R.id.dialog_play_bill_Empty));
        updateData(playBillList);
        playBillAdapter = new GenericAdapter<PlayBill>(context,
                R.layout.dialog_play_bill_lv_item, playBills) {
            @Override
            public void populate(ViewHolder viewHolder, PlayBill playBill) {
                viewHolder.setTextView(R.id.dialog_play_bill_lv_item_tv_title, playBill.getTitle());
                if (playBill.isPlayIng()) {
                    viewHolder.setImageResource(R.id.dialog_play_bill_lv_item_img_play, R.drawable.playing);
                    TextView tvTitle = viewHolder.getView(R.id.dialog_play_bill_lv_item_tv_title);
                    tvTitle.setTextColor(Color.parseColor("#000000"));
                } else {
                    viewHolder.setImageResource(R.id.dialog_play_bill_lv_item_img_play, R.drawable.sound_wave);
                    TextView tvTitle = viewHolder.getView(R.id.dialog_play_bill_lv_item_tv_title);
                    tvTitle.setTextColor(Color.parseColor("#000000"));
                }
                String announcers = "";
                viewHolder.setTextView(R.id.dialog_play_bill_lv_item_tv_broadcasters,
                        announcers.length() > 0 ? announcers.substring(0,
                                announcers.length() - 1) : "暂无主持人信息");
                viewHolder.setTextView(R.id.dialog_play_bill_lv_item_tv_time,
                        playBill.getStart_time() + "-" + playBill.getEnd_time());
            }

            @Override
            public boolean persistInsert(PlayBill playBill) {
                return false;
            }

            @Override
            public boolean persistDelete(PlayBill playBill) {
                return false;
            }
        };
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (DateTimeUtils.playIf(playBills.get(position).getStart_time())) {
                        ImageView imageView = view.findViewById(R.id.dialog_play_bill_lv_item_iv_pl);
                        imageView.setImageResource(R.drawable.play);
                        if (context instanceof MainActivity) {
                            FindFragment fragment = FindFragment.newInstance(playBills, urls, position, radioHint);
                            ((MainActivity) context).showPlay(fragment);
                            hide();
                        }
                    } else {
                        Toast.makeText(context, "当前节目未开始！", Toast.LENGTH_LONG).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        listView.setAdapter(playBillAdapter);
        dialog.setContentView(view);
        dialog.show();
    }

    private static void updateData(List<PlayBill> playBillList) {
        playBills.clear();
        playBills.addAll(playBillList);
        urls.clear();
        for (PlayBill playBill : playBills) {
            if (playBill.isPlayIng()) {
                urls.add(ApiConstants.getPlayimjUrl(playBill.getChannel_id()));
            } else {
                urls.add(ApiConstants.getDemand(playBill.getChannel_id(),
                        playBill.getStart_time(), playBill.getEnd_time()));
            }
        }
    }

    public static void show() {
        dialog.show();
    }

    public static void hide() {
        dialog.hide();
    }

    public static boolean isShowing() {
        try {
            return dialog.isShowing();
        } catch (Exception e) {
            return false;
        }

    }

    public static void dismissPlayBill() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public static void updatePlayBillAdapter(List<PlayBill> playBillList) {
        if (dialog != null && dialog.isShowing() && playBillAdapter != null) {
            updateData(playBillList);
            playBillAdapter.notifyDataSetChanged();
        }
    }
}
