package net.lzzy.kirinfm.service;

import com.google.gson.Gson;

import net.lzzy.kirinfm.models.view.CelebritySayings;
import net.lzzy.kirinfm.network.RequestDataService;

import java.io.IOException;

/**
 * @author Administrator
 */
public class WisdomService {
    public static String getWisdom() throws IOException {
        Gson gson = new Gson();
        CelebritySayings dictum = gson.fromJson(RequestDataService.getWisdom(), CelebritySayings.class);
        return dictum.getDictum().getFamous_saying();
    }
}
