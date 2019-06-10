package net.lzzy.kirinfm.models.view;

import net.lzzy.kirinfm.models.NetworkArea;

/**
 * @author Administrator
 */
public class NetworkAddress {
    /**
     * data : {"isp":"电信","county_id":"xx","isp_id":"100017","area":"","county":"XX","city_id":"450200","region":"广西","ip":"222.217.36.113","area_id":"","country_id":"CN","city":"柳州","country":"中国","region_id":"450000"}
     * code : 0
     */

    private NetworkArea data;
    private int code;

    public NetworkArea getData() {
        return data;
    }

    public void setData(NetworkArea data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
