package net.lzzy.kirinfm.analytical;

import com.google.gson.Gson;

import net.lzzy.kirinfm.models.Radio;
import net.lzzy.kirinfm.models.view.RadioIntroduction;
import net.lzzy.kirinfm.network.RequestDataService;

import java.io.IOException;
import java.util.List;

/**
 * @author Administrator
 */
public class NetworkLocationService {
    public static List<Radio> getThisRegionRadio(int regionId, int page, int pagesize) throws IOException {
        Gson gson = new Gson();
        String json = RequestDataService.getThisRegionRadio(regionId, page, pagesize);
        return gson.fromJson(json,
                RadioIntroduction.class).getData().getItems();
    }
}
