package net.lzzy.kirinfm.service;


import com.google.gson.Gson;

import net.lzzy.kirinfm.models.PlayBill;
import net.lzzy.kirinfm.network.RequestDataService;
import net.lzzy.kirinfm.utils.DateTimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class DateDataService {

    public static List<PlayBill> getPlayBill(Integer contentId, String day) throws JSONException, ParseException, IOException {
        String json = RequestDataService.getRadioPlayBill(contentId, day);
        List<PlayBill> playBills = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray(day);
        Gson gson = new Gson();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            PlayBill playBill = gson.fromJson(object.toString(), PlayBill.class);
            playBill.setPlayIng(DateTimeUtils.playIngIf(playBill.getStart_time(), playBill.getEnd_time()));
            playBills.add(playBill);
        }
        return playBills;
    }
}
