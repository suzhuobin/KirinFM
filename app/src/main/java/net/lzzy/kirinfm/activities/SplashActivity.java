package net.lzzy.kirinfm.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import net.lzzy.kirinfm.R;
import net.lzzy.kirinfm.thread.GetRadioCategoryThread;
import net.lzzy.kirinfm.thread.GetRegionThread;
import net.lzzy.kirinfm.thread.GetThisRegionThread;
import net.lzzy.kirinfm.thread.GetWisdomThread;
import net.lzzy.kirinfm.models.RadioCategory;
import net.lzzy.kirinfm.models.Region;
import net.lzzy.kirinfm.models.NetworkArea;
import net.lzzy.kirinfm.utils.AbstractStaticHandler;
import net.lzzy.kirinfm.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class SplashActivity extends AppCompatActivity {
    //region
    /**
     * ExtraKey
     */
    public static final String EXTRA_ALL_RADIO_TYPE = "AllRadioCategory";
    public static final String EXTRA_ALL_REGION = "all_region";
    public static final String EXTRA_THIS_REGION = "this_region";
    private static final int WHAT_GET_ADVERTISINGS_OK = 12;
    private static final int WHAT_GET_ADVERTISINGS_ERROR = 13;
    //endregion
    // region 常量
    /**
     * 倒计时进行时
     */
    public static final int WHAT_COUNT_ING = 1;
    /**
     * 倒计时完成时
     */
    public static final int WHAT_COUNT_DONE = 2;
    /**
     * 倒计时报错时
     */
    public static final int WHAT_COUNT_ERROR = 3;
    /**
     * 获取名人名言成功
     */
    public static final int WHAT_GET_SENTENCE_OK = 4;
    /**
     * 获取名人名言失败
     */
    public static final int WHAT_GET_SENTENCE_ERROR = 5;
    /**
     * 获取所有地区成功
     */
    private static final int WHAT_GET_REGION_OK = 6;
    /**
     * 获取所有地区失败
     */
    private static final int WHAT_GET_REGION_ERROR = 7;
    /**
     * 获取所有电台类别成功
     */
    private static final int WHAT_GET_ALL_RADIO_CATEGORY_OK = 8;
    /**
     * 获取所有电台类别失败
     */
    public static final int WHAT_GET_ALL_RADIO_CATEGORY_ERROR = 9;
    /**
     * 获取当前地区成功
     */
    public static final int WHAT_GET_THIS_REGION_OK = 10;
    /**
     * 获取当前地区失败
     */
    public static final int WHAT_GET_THIS_REGION_ERROR = 11;
    //endregion

    private TextView time;
    private TextView hint;
    private int seconds = 5;
    private List<Region> regions = new ArrayList<>();
    private List<RadioCategory> radioCategories = new ArrayList<>();
    private NetworkArea thisRegion;
    private boolean getWisdom = false, getRegion = false,
            getRadioCategory = false, getThisRegion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        AppUtils.addActivity(this);
        time = findViewById(R.id.activity_start_tv_time);
        hint = findViewById(R.id.activity_start_tv_hint);
        //判断网络是否可用
        if (!AppUtils.isNetworkAvailable()) {
            goToMain(this);
        } else {
            //获取所需数据
            executeGetDataThread();
        }
    }

    /**
     * 获取数据
     */
    @SuppressLint("StaticFieldLeak")
    private void executeGetDataThread() {
        //倒计时
        AppUtils.getExecutor().execute(this::countDown);
        //获取名人名言
        new GetWisdomThread<SplashActivity>(SplashActivity.this) {
            @Override
            protected void onPostExecute(String wisdom, SplashActivity splashActivity) {
                splashActivity.hint.setText(wisdom);
                splashActivity.getWisdom = true;
                goToMain(splashActivity);
            }
        }.execute();
        //获取所有的地区
        new GetRegionThread<SplashActivity>(SplashActivity.this) {
            @Override
            protected void onPostExecute(List<Region> regions, SplashActivity splashActivity) {
                splashActivity.regions.addAll(regions);
                splashActivity.getRegion = true;
                goToMain(splashActivity);
            }
        }.execute();
        //获取所有的电台类别
        new GetRadioCategoryThread<SplashActivity>(SplashActivity.this) {
            @Override
            protected void onPostExecute(List<RadioCategory> regions, SplashActivity splashActivity) {
                splashActivity.radioCategories.addAll(regions);
                splashActivity.getRadioCategory = true;
                goToMain(splashActivity);
            }
        }.execute();
        //获取当前地区
        new GetThisRegionThread<SplashActivity>(SplashActivity.this) {
            @Override
            protected void onPostExecute(NetworkArea thisRegion, SplashActivity splashActivity) {
                splashActivity.thisRegion = thisRegion;
                splashActivity.getThisRegion = true;
                goToMain(splashActivity);
            }
        }.execute();
    }

    /**
     * 倒计时
     */
    private void countDown() {
        while (seconds > 0) {
            Message message = handler.obtainMessage(WHAT_COUNT_ING, seconds);
            handler.sendMessage(message);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(WHAT_COUNT_ERROR);
            }
            seconds--;
        }
        handler.sendEmptyMessage(WHAT_COUNT_DONE);
    }

    private MyHandler handler = new MyHandler(this);

    private static class MyHandler extends AbstractStaticHandler<SplashActivity> {
        Gson gson = new Gson();

        MyHandler(SplashActivity context) {
            super(context);
        }

        @Override
        public void handleMessage(Message msg, SplashActivity activity) {
            switch (msg.what) {
                case WHAT_COUNT_ING:
                    //倒计时进行中
                    activity.time.setText(msg.obj + "");
                    break;
                case WHAT_COUNT_DONE:
                    //倒计时完成
                    goToMain(activity);
                    break;
                case WHAT_COUNT_ERROR:
                    //倒计时错误
                    goToMain(activity);
                    break;
                //endregion
                default:
                    break;
            }
        }
    }

    private static void goToMain(SplashActivity activity) {
        if (activity.getThisRegion && activity.getRadioCategory
                && activity.getRegion && activity.getWisdom && activity.seconds <= 0) {
            Intent intent = new Intent(activity, MainActivity.class);
            intent.putParcelableArrayListExtra(EXTRA_ALL_RADIO_TYPE,
                    (ArrayList<? extends Parcelable>) activity.radioCategories);
            intent.putParcelableArrayListExtra(EXTRA_ALL_REGION,
                    (ArrayList<? extends Parcelable>) activity.regions);
            intent.putExtra(EXTRA_THIS_REGION, activity.thisRegion);
            activity.startActivity(intent);
            activity.finish();
        }
    }

    //region

    /**
     * 销毁Activity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUtils.remoreActivity(this);
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppUtils.setRunningActivity(getLocalClassName());
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppUtils.setStopped(getLocalClassName());
    }
    //endregion
}
