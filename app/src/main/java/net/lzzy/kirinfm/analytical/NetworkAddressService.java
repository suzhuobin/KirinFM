package net.lzzy.kirinfm.analytical;

import com.google.gson.Gson;

import net.lzzy.kirinfm.models.NetworkArea;
import net.lzzy.kirinfm.models.view.NetworkAddress;
import net.lzzy.kirinfm.network.RequestDataService;

import java.io.IOException;

/**
 * @author Administrator
 */
public class NetworkAddressService {
    public static NetworkArea getThisRegion() throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(RequestDataService.getThisRegion(), NetworkAddress.class).getData();
    }
}
