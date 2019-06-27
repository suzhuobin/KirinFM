package net.lzzy.kirinfm.connstants;

import net.lzzy.kirinfm.utils.DateTimeUtils;

import java.util.Date;

public class ApiConstants {
    /**
     * 获取名人名言
     */
    public static final String GET_DICTUM = "http://api.avatardata.cn/MingRenMingYan/" +
            "Random?key=b954d5d2cbf94e60ad09f1c6a8eb3db3";

    /**
     * 星座
     */

    public static final String GET_XINGZUO = "http://api.avatardata.cn/XingZuoPeiDui/Lookup";

    /**
     * 搜索
     */
    public static final String GET_SEARCH_RADIOA = "https://i.qingting.fm/wapi/search?k=";
    public static final String GET_SEARCH_RADIOB = "&groups=channel_live";

    public static String getSearchRadio(String title) {
        return GET_SEARCH_RADIOA + title + GET_SEARCH_RADIOB;
    }

    /**
     * 获取所有地区
     */
    public static final String GET_REGION = "https://rapi.qingting.fm/regions";

    /**
     * 获取所有电台类别
     */
    public static final String GET_ALL_RADIO_CATEGORY = "https://rapi.qingting.fm/categories?type=channel";

    public static final String GET_THIS_REGION = "https://ip.qingting.fm/ip";

    /**
     * 获取当前地区的电台
     */
    private static final String GET_RADIO_A = "https://rapi.qingting.fm/categories/";
    private static final String GET_RADIO_B = "/channels?with_total=true&page=";
    private static final String GET_RADIO_C = "&pagesize=";

    public static String getRadio(int regionId, int page, int pagesize) {
        return GET_RADIO_A + regionId + GET_RADIO_B + page + GET_RADIO_C + pagesize;
    }

    private static final String GET_RADIO_PlayBill_A = "https://rapi.qingting.fm/v2/channels/";
    /**
     * 获取当前电台的节目
     */
    private static final String GET_RADIO_PlayBill_B = "/playbills?day=";

    public static String getRadioPlayBill(int contentId, String day) {
        return GET_RADIO_PlayBill_A + contentId + GET_RADIO_PlayBill_B + day;
    }

    /***
     * 获取电台类别的电台
     */
    private static final String GET_RADIO_RADIO_CATEGORY_A = "https://rapi.qingting.fm/categories/";
    private static final String GET_RADIO_RADIO_CATEGORY_B = "/channels?with_total=true&page=1&pagesize=12";

    public static String getRadioCategory(int id) {
        return GET_RADIO_RADIO_CATEGORY_A + id + GET_RADIO_RADIO_CATEGORY_B;
    }

    private static final String GET_RADIO_INFO = "https://i.qingting.fm/wapi/channels/";

    /**
     * 获取当前电台的信息
     */
    public static String getRadioInfo(int radioId) {
        return GET_RADIO_INFO + radioId;
    }

    /**
     * 获取轮播广告
     */
    public static final String GET_ADVERTISING = "https://rapi.qingting.fm/recommendations/0/banner?more=false&replay=false";

    /**
     * 直播地址
     */
    private static String GET_PLAY_URL_A = "http://lhttp.qingting.fm/live/";
    private static String GET_PLAY_URL_B = "/64k.mp3";

    public static String getPlayimjUrl(int playBillId) {
        return GET_PLAY_URL_A + playBillId + GET_PLAY_URL_B;
    }

    private static String GET_DEMAND_URL_A = "http://lcache.qingting.fm/cache/";
    private static String GET_DEMAND_URL_B = "_24_0.aac";

    public static String getDemand(int playBillId, String start_time, String end_time) {
        String date = DateTimeUtils.DATE_TIME_DEMAND.format(new Date());
        start_time = start_time.replaceAll(":", "");
        end_time = end_time.replaceAll(":", "");
        return GET_DEMAND_URL_A + date + "/" + playBillId + "/" + playBillId
                + "_" + date + "_" + start_time + "_" + end_time + GET_DEMAND_URL_B;
    }
}

