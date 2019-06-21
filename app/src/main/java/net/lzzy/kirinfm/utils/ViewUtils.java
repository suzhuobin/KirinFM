package net.lzzy.kirinfm.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;

import net.lzzy.kirinfm.R;
import net.lzzy.kirinfm.models.Announcer;
import net.lzzy.kirinfm.models.PlayBill;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.util.List;

/**
 * @author Administrator
 */
public class ViewUtils {


    private static FullScreenDialog dialog;
    private static GenericAdapter<PlayBill> playBillAdapter;
    private static List<PlayBill> playBills;

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

    public static void showPlayBill(Context context, String back, String title, List<PlayBill> playBillList) {
        dialog = new FullScreenDialog(context);
        View view = LayoutInflater.from(context)
                .inflate(R.layout.radio_program, null, false);
        LinearLayout linearLayout = view.findViewById(R.id.radio_program_layout_back);
        linearLayout.setOnClickListener(v -> ViewUtils.dismissPlayBill());
        TextView tvBack = view.findViewById(R.id.radio_program_tv_back);
        tvBack.setText(back);
        TextView tvTitle = view.findViewById(R.id.radio_program_tv_title);
        tvTitle.setText(title);
        ListView listView = view.findViewById(R.id.radio_program_lv);
        listView.setEmptyView(view.findViewById(R.id.radio_program_Empty));
        playBills = playBillList;
        playBillAdapter = new GenericAdapter<PlayBill>(context,
                R.layout.program_information, playBills) {
            @Override
            public void populate(ViewHolder viewHolder, PlayBill playBill) {
                viewHolder.setTextView(R.id.program_information_tv_title, playBill.getTitle());
                viewHolder.setTextView(R.id.program_information_lv_item_tv_count, "0");
                if (playBill.isPlayIng()) {
                    viewHolder.setImageResource(R.id.program_information_lv_item_img_sound_wave, R.drawable.playing);
                    TextView tvTitle = viewHolder.getView(R.id.program_information_tv_title);
                    tvTitle.setTextColor(Color.parseColor("#00FFFF"));
                } else {
                    viewHolder.setImageResource(R.id.program_information_lv_item_img_sound_wave, R.drawable.sound_wave);
                    TextView tvTitle = viewHolder.getView(R.id.program_information_tv_title);
                    tvTitle.setTextColor(Color.parseColor("#000000"));
                }
                String broadcasters = "";
                for (Announcer broadcaster : playBill.getBroadcasters()) {
                    broadcasters = broadcasters.concat(broadcaster.getUsername()).concat("、");
                }
                viewHolder.setTextView(R.id.program_information_tv_broadcasters,
                        broadcasters.length() > 0 ? broadcasters.substring(0, broadcasters.length() - 1) : "");
                viewHolder.setTextView(R.id.program_information_tv_time,
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
        listView.setAdapter(playBillAdapter);
        dialog.setContentView(view);
        dialog.show();
    }

    public static void dismissPlayBill() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public static void updatePlayBillAdapter(List<PlayBill> playBillList) {
        if (dialog != null && dialog.isShowing() && playBillAdapter != null) {
            playBills.clear();
            playBills.addAll(playBillList);
            playBillAdapter.notifyDataSetChanged();
        }
    }
}
