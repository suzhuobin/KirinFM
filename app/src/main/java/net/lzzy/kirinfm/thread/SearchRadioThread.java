package net.lzzy.kirinfm.thread;

import android.os.AsyncTask;

import net.lzzy.kirinfm.models.Radio;
import net.lzzy.kirinfm.service.SearchRadioService;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public abstract class SearchRadioThread<T> extends AsyncTask<String, Void, List<Radio>> {
    final WeakReference<T> context;

    protected SearchRadioThread(T context) {
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
    protected List<Radio> doInBackground(String... strings) {
        try {
            return SearchRadioService.getSearchRadio(strings[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 获取到数据后
     */
    @Override
    protected void onPostExecute(List<Radio> radios) {
        super.onPostExecute(radios);
        T t = context.get();
        onPostExecute(radios, t);
    }

    protected abstract void onPostExecute(List<Radio> radios, T t);
}
