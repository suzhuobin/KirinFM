package net.lzzy.kirinfm.service;

import com.google.gson.Gson;

import net.lzzy.kirinfm.models.Radio;
import net.lzzy.kirinfm.models.view.RadioGenre;
import net.lzzy.kirinfm.network.RequestDataService;

import java.io.IOException;
import java.util.List;

public class RadioTypeService {
    public static List<Radio> getRadioType(int categoryId) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(RequestDataService.getRadioType(categoryId), RadioGenre.class).getData().getItems();
    }
}
