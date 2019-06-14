package net.lzzy.kirinfm.thread;


import android.os.AsyncTask;

import com.google.gson.Gson;

import net.lzzy.kirinfm.service.NetworkAddressService;
import net.lzzy.kirinfm.models.NetworkArea;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * @author Administrator
 */
public abstract class GetThisRegionThread<T> extends AsyncTask<Void, Void, NetworkArea> {
    private Gson gson = new Gson();
    private final WeakReference<T> context;

    protected GetThisRegionThread(T context) {
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
    protected NetworkArea doInBackground(Void... voids) {
        try {
            return NetworkAddressService.getThisRegion();
        } catch (IOException e) {
            e.printStackTrace();
            return new NetworkArea();
        }
    }

    /**
     * 获取到数据后
     */
    @Override
    protected void onPostExecute(NetworkArea thisRegion) {
        super.onPostExecute(thisRegion);
        T t = context.get();
        onPostExecute(thisRegion, t);
    }

    protected abstract void onPostExecute(NetworkArea thisRegion, T t);
}
