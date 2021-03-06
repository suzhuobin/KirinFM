package net.lzzy.kirinfm.network;

import net.lzzy.kirinfm.connstants.ApiConstants;

import java.io.IOException;

/**
 * @author Administrator
 */
public class RequestDataService {
    public static String getSeekRadio(String key) throws IOException {
        return ApiService.okGet(ApiConstants.getSearchRadio(key));
    }

    public static String getThisRegionRadio(int regionId, int page, int pagesize) throws IOException {
        String json = ApiService.okGet(ApiConstants.getRadio(regionId, page, pagesize));
        return json;
    }

    public static String getAdvertising() throws IOException {
        return ApiService.okGet(ApiConstants.GET_ADVERTISING);
    }

    public static String getRadioPlayBill(int contentId, String day) throws IOException {
        return ApiService.okGet(ApiConstants.getRadioPlayBill(contentId, day));
    }

    public static String getRadioCategory() throws IOException {
        return ApiService.okGet(ApiConstants.GET_ALL_RADIO_CATEGORY);
    }

    public static String getRegion() throws IOException {
        return ApiService.okGet(ApiConstants.GET_REGION);
    }

    public static String getThisRegion() throws IOException {
        return ApiService.okGet(ApiConstants.GET_THIS_REGION);
    }

    public static String getWisdom() throws IOException {
        return ApiService.okGet(ApiConstants.GET_DICTUM);
    }

    public static String getRadioType(int categoryId) throws IOException {
        return ApiService.okGet(ApiConstants.getRadioCategory(categoryId));
    }
}
