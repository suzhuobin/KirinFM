package net.lzzy.kirinfm.activities;

import android.os.Bundle;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import net.lzzy.kirinfm.utils.AppUtils;


/**
 * @author Administrator
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Fragment fragment;
    private FragmentManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutRes());
        manager = getSupportFragmentManager();
        fragment = manager.findFragmentById(getContainerId());
        if (fragment == null) {
            fragment = createFragment();
            manager.beginTransaction().add(getContainerId(), fragment).commit();
        }
        AppUtils.addActivity(this);
    }

    protected Fragment getFragment() {
        return fragment;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUtils.remoreActivity(this);
    }

    public FragmentManager getManager() {
        return manager;
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

    /**
     * Activity的布局文件
     *
     * @return
     */
    protected abstract int getLayoutRes();

    /**
     * 获取Fragment容器Id
     *
     * @return
     */
    protected abstract int getContainerId();

    /**
     * 生成托管的Fragment对象
     *
     * @return
     */
    protected abstract Fragment createFragment();

}
