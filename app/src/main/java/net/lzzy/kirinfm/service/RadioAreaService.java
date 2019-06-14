package net.lzzy.kirinfm.service;

import com.google.gson.Gson;

import net.lzzy.kirinfm.models.RadioCategory;
import net.lzzy.kirinfm.models.view.RadioType;
import net.lzzy.kirinfm.network.RequestDataService;

import java.io.IOException;
import java.util.List;

/**
 * 解析地区
 *
 * @author Administrator
 */
public class RadioAreaService {
    public static List<RadioCategory> getRadioCategory() throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(RequestDataService.getRadioCategory(), RadioType.class).getData();
    }

}
