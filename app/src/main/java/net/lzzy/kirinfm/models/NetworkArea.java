package net.lzzy.kirinfm.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Administrator
 */
public class NetworkArea implements Parcelable {
    /**
     * isp : 电信
     * county_id : xx
     * isp_id : 100017
     * area :
     * county : XX
     * city_id : 450200
     * region : 广西
     * ip : 222.217.36.113
     * area_id :
     * country_id : CN
     * city : 柳州
     * country : 中国
     * region_id : 450000
     */

    private String isp;
    private String county_id;
    private String isp_id;
    private String area;
    private String county;
    private String city_id;
    private String region;
    private String ip;
    private String area_id;
    private String country_id;
    private String city;
    private String country;
    private String region_id;

    public NetworkArea() {

    }

    public NetworkArea(Parcel in) {
        isp = in.readString();
        county_id = in.readString();
        isp_id = in.readString();
        area = in.readString();
        county = in.readString();
        city_id = in.readString();
        region = in.readString();
        ip = in.readString();
        area_id = in.readString();
        country_id = in.readString();
        city = in.readString();
        country = in.readString();
        region_id = in.readString();
    }

    public static final Creator<NetworkArea> CREATOR = new Creator<NetworkArea>() {
        @Override
        public NetworkArea createFromParcel(Parcel in) {
            return new NetworkArea(in);
        }

        @Override
        public NetworkArea[] newArray(int size) {
            return new NetworkArea[size];
        }
    };

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getCounty_id() {
        return county_id;
    }

    public void setCounty_id(String county_id) {
        this.county_id = county_id;
    }

    public String getIsp_id() {
        return isp_id;
    }

    public void setIsp_id(String isp_id) {
        this.isp_id = isp_id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(isp);
        dest.writeString(county_id);
        dest.writeString(isp_id);
        dest.writeString(area);
        dest.writeString(county);
        dest.writeString(city_id);
        dest.writeString(region);
        dest.writeString(ip);
        dest.writeString(area_id);
        dest.writeString(country_id);
        dest.writeString(city);
        dest.writeString(country);
        dest.writeString(region_id);
    }
}
