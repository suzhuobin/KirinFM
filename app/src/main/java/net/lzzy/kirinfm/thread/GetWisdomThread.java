package net.lzzy.kirinfm.thread;

import android.os.AsyncTask;

import com.google.gson.Gson;

import net.lzzy.kirinfm.service.WisdomService;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * 获取名人名言
 *
 * @param <T> Context
 * @author Administrator
 */
public abstract class GetWisdomThread<T> extends AsyncTask<Void, Void, String> {
    private Gson gson = new Gson();
    private final WeakReference<T> context;

    protected GetWisdomThread(T context) {
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
    protected String doInBackground(Void... integers) {
        try {
            return WisdomService.getWisdom();
        } catch (IOException e) {
            e.printStackTrace();
            return "获取名人名言失败";
        }
    }

    /**
     * 获取到数据后
     */
    @Override
    protected void onPostExecute(String wisdom) {
        super.onPostExecute(wisdom);
        T t = context.get();
        onPostExecute(wisdom, t);
    }

    protected abstract void onPostExecute(String wisdom, T t);
}
