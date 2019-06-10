package net.lzzy.kirinfm.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

/**
 * @author Administrator
 */
public abstract class BaseFragment extends Fragment {
    public BaseFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        populate();
    }

    /**
     * 加载布局
     *
     * @return 布局
     */
    protected abstract int getLayoutRes();

    /**
     * 执行onViewCreated中初始化视图组件、填充数据的任务
     */
    protected abstract void populate();

    /**
     * 查找视图组件 findById();
     *
     * @param id  视图ID
     * @param <T> 视图类型
     * @return 视图
     */
    <T extends View> T find(@IdRes int id) {
        return Objects.requireNonNull(getView()).findViewById(id);
    }

    public abstract void search(String kw);
}
