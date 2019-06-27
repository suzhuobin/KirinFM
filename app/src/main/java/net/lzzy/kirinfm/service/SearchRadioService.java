package net.lzzy.kirinfm.service;

import com.google.gson.Gson;

import net.lzzy.kirinfm.models.Radio;
import net.lzzy.kirinfm.models.view.Seek;
import net.lzzy.kirinfm.network.RequestDataService;

import java.io.IOException;
import java.util.List;

public class SearchRadioService {
    public static List<Radio> getSearchRadio(String key) throws IOException {
        Gson gson = new Gson();
        String json = RequestDataService.getSeekRadio(key);

        return gson.fromJson(json,
                Seek.class).getData().getData().get(0).getDoclist().getDocs();
    }
}
