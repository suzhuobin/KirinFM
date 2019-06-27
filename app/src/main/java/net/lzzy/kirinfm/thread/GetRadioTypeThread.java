package net.lzzy.kirinfm.thread;

import android.os.AsyncTask;

import net.lzzy.kirinfm.models.Radio;
import net.lzzy.kirinfm.service.RadioTypeService;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public abstract class GetRadioTypeThread<T> extends AsyncTask<Integer, Void, List<Radio>> {
    private final WeakReference<T> context;

    protected GetRadioTypeThread(T context) {
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
    protected List<Radio> doInBackground(Integer... integers) {
        try {
            return RadioTypeService.getRadioType(integers[0]);
        } catch (IOException e) {
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
