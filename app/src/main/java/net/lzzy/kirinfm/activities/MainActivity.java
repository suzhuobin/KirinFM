package net.lzzy.kirinfm.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import net.lzzy.kirinfm.R;
import net.lzzy.kirinfm.fragments.AnalyzeFragment;
import net.lzzy.kirinfm.fragments.FavoriteFragment;
import net.lzzy.kirinfm.fragments.FindFragment;
import net.lzzy.kirinfm.fragments.FmFragment;
import net.lzzy.kirinfm.models.Radio;
import net.lzzy.kirinfm.models.RadioCategory;
import net.lzzy.kirinfm.models.Region;
import net.lzzy.kirinfm.models.NetworkArea;
import net.lzzy.kirinfm.utils.AppUtils;
import net.lzzy.kirinfm.utils.StaticViewPager;
import net.lzzy.kirinfm.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView favoriteImg;
    private ImageView fmImg;
    private ImageView analyzeImg;
    private TextView favoriteTv;
    private TextView fmTv;
    private TextView analyzeTv;
    private List<Region> regions = new ArrayList<>();
    private Radio radios;
    private List<RadioCategory> radioCategories = new ArrayList<>();
    private NetworkArea networkArea;
    private SparseArray<Fragment> fragmentArray = new SparseArray<>();
    private StaticViewPager pager;
    private FragmentPagerAdapter adapter;
    private static FrameLayout playContainer;
    private static FindFragment playFragment = null;
    private static ImageView plays;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        AppUtils.addActivity(this);
        initView();
        initData();
    }

    private void initData() {
        radioCategories.addAll(getIntent().getParcelableArrayListExtra(SplashActivity.EXTRA_ALL_RADIO_TYPE));
        regions.addAll(getIntent().getParcelableArrayListExtra(SplashActivity.EXTRA_ALL_REGION));
        networkArea = getIntent().getParcelableExtra(SplashActivity.EXTRA_THIS_REGION);
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Fragment getItem(int position) {
                Fragment fragment = fragmentArray.get(position);
                if (fragment != null) {
                    return fragment;
                } else {
                    switch (position) {
                        case 0:
                            fragment = new FavoriteFragment();
                            fragmentArray.append(position, fragment);
                            break;
                        case 1:
                            fragment = FmFragment.newInstance(regions, radioCategories, networkArea);
                            fragmentArray.append(position, fragment);
                            break;
                        case 2:
                            fragment = AnalyzeFragment.newInstance(regions, radios);
                            fragmentArray.append(position, fragment);
                            break;
                        default:
                            break;
                    }
                }
                return fragment;
            }
        };
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Fragment fragment = fragmentArray.get(position);
                if (fragment instanceof FmFragment) {
                    ((FmFragment) fragment).updateFavorite();
                } else if (fragment instanceof FavoriteFragment) {
                    ((FavoriteFragment) fragment).updateFavorite();
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        cutTag(R.id.activity_main_layout_fm);
    }

    //region 加载布局

    //endregion
    private void initView() {
        findViewById(R.id.activity_main_layout_collect).setOnClickListener(this);
        findViewById(R.id.activity_main_layout_fm).setOnClickListener(this);
        findViewById(R.id.activity_main_layout_analyze).setOnClickListener(this);
        favoriteImg = findViewById(R.id.activity_main_img_collect);
        fmImg = findViewById(R.id.activity_main_img_fm);
        analyzeImg = findViewById(R.id.activity_main_img_analyze);
        favoriteTv = findViewById(R.id.activity_main_tv_favorite);
        fmTv = findViewById(R.id.activity_main_tv_fm);
        analyzeTv = findViewById(R.id.activity_main_tv_analyze);
        pager = findViewById(R.id.activity_main_pager);
        playContainer = findViewById(R.id.activity_main_play_icon);
        plays = findViewById(R.id.activity_main_show_now_playing);
    }

    public void showPlay(FindFragment findFragment) {
        playContainer.setVisibility(View.VISIBLE);
        playFragment = findFragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_play_icon,
                findFragment).commit();
    }

    public static void showPlayHint() {
        plays.setVisibility(View.VISIBLE);

    }

    public static void dismissPlay() {
        playContainer.setVisibility(View.GONE);
        if (ViewUtils.isShowing()) {
            plays.setVisibility(View.VISIBLE);
        } else {
            plays.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_layout_collect:
                cutTag(v.getId());
                break;
            case R.id.activity_main_layout_fm:
                cutTag(v.getId());
                break;
            case R.id.activity_main_layout_analyze:
                cutTag(v.getId());
                break;
            default:
                break;
        }
    }

    //region 切换菜单方法

    private void cutTag(int tadId) {
        favoriteImg.setImageResource(R.drawable.ic_local_pressed);
        favoriteTv.setTextColor(Color.parseColor("#707070"));
        fmImg.setImageResource(R.drawable.ic_find_normal);
        fmTv.setTextColor(Color.parseColor("#707070"));
        analyzeImg.setImageResource(R.drawable.ic_chart_normal);
        analyzeTv.setTextColor(Color.parseColor("#707070"));
        switch (tadId) {
            case R.id.activity_main_layout_analyze:
                analyzeImg.setImageResource(R.drawable.ic_chart_pressed);
                analyzeTv.setTextColor(Color.parseColor("#1295DA"));
                pager.setCurrentItem(2);
                break;
            case R.id.activity_main_layout_collect:
                favoriteImg.setImageResource(R.drawable.ic_local_pressed);
                favoriteTv.setTextColor(Color.parseColor("#1295DA"));
                pager.setCurrentItem(0);
                break;
            case R.id.activity_main_layout_fm:
                fmImg.setImageResource(R.drawable.ic_find_pressed);
                fmTv.setTextColor(Color.parseColor("#1295DA"));
                pager.setCurrentItem(1);
                break;
            default:
                break;

        }
    }
    //endregion


    //region 销毁Activity
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUtils.remoreActivity(this);
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


    @Override
    public void onBackPressed() {
        if (playContainer.isShown()) {
            dismissPlay();
        } else {
            super.onBackPressed();
        }
    }
}
