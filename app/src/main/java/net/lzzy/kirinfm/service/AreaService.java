package net.lzzy.kirinfm.service;

import com.google.gson.Gson;

import net.lzzy.kirinfm.models.Region;
import net.lzzy.kirinfm.models.view.Area;
import net.lzzy.kirinfm.network.RequestDataService;

import java.io.IOException;
import java.util.List;

/**
 * @author Administrator
 */
public class AreaService {
    public static List<Region> getRegion() throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(RequestDataService.getRegion(), Area.class).getData();
    }
}
