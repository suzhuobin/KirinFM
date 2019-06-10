package net.lzzy.kirinfm.thread;

import android.os.AsyncTask;

import net.lzzy.kirinfm.analytical.DateDataService;
import net.lzzy.kirinfm.models.PlayBill;
import net.lzzy.kirinfm.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
public abstract class GetNetworkAreaThread extends AsyncTask<Integer, Void, List<PlayBill>> {
    String day = "1";

    protected GetNetworkAreaThread() {
    }

    @Override
    protected List<PlayBill> doInBackground(Integer... integers) {
        day = DateTimeUtils.getDay(new Date());
        try {
            return DateDataService.getPlayBill(integers[0], day);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    protected void onPostExecute(List<PlayBill> playBills) {
        super.onPostExecute(playBills);
        abstractOnPostExecute(playBills);
    }

    protected abstract void abstractOnPostExecute(List<PlayBill> playBills);
}