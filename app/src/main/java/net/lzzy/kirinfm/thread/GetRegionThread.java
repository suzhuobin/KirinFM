package net.lzzy.kirinfm.thread;

import android.os.AsyncTask;

import net.lzzy.kirinfm.service.AreaService;
import net.lzzy.kirinfm.models.Region;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public abstract class GetRegionThread<T> extends AsyncTask<Void, Void, List<Region>> {

    private final WeakReference<T> context;

    protected GetRegionThread(T context) {
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
    protected List<Region> doInBackground(Void... integers) {
        try {
            return AreaService.getRegion();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 获取到数据后
     */
    @Override
    protected void onPostExecute(List<Region> regions) {
        super.onPostExecute(regions);
        T t = context.get();
        onPostExecute(regions, t);
    }

    protected abstract void onPostExecute(List<Region> regions, T t);
}
