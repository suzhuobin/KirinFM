package net.lzzy.kirinfm.thread;

import android.os.AsyncTask;

import net.lzzy.kirinfm.service.RadioAreaService;
import net.lzzy.kirinfm.models.RadioCategory;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public abstract class GetRadioCategoryThread<T> extends AsyncTask<Void, Void, List<RadioCategory>> {
    private final WeakReference<T> context;

    protected GetRadioCategoryThread(T context) {
        this.context = new WeakReference<>(context);
    }

    /**
     * 执行线程前
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<RadioCategory> doInBackground(Void... voids) {
        try {
            return RadioAreaService.getRadioCategory();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 获取到数据后
     */
    @Override
    protected void onPostExecute(List<RadioCategory> radioCategories) {
        super.onPostExecute(radioCategories);
        T t = context.get();
        onPostExecute(radioCategories, t);
    }

    protected abstract void onPostExecute(List<RadioCategory> regions, T t);
}
