package net.lzzy.kirinfm.thread;

import android.os.AsyncTask;

import net.lzzy.kirinfm.service.NetworkLocationService;
import net.lzzy.kirinfm.models.Radio;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public abstract class GetThisRegionRadioThread<T> extends AsyncTask<Void, Void, List<Radio>> {
    final WeakReference<T> context;
    int regionId, page, pagesize;

    protected GetThisRegionRadioThread(T context, int regionId, int page, int pagesize) {
        this.context = new WeakReference<>(context);
        this.regionId = regionId;
        this.page = page;
        this.pagesize = pagesize;
    }

    /**
     * 执行线程前
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Radio> doInBackground(Void... voids) {
        try {
            return NetworkLocationService.getThisRegionRadio(regionId, page, page);
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
