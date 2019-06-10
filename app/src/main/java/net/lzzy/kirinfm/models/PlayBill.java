package net.lzzy.kirinfm.models;

import java.util.List;

/**
 * @author Administrator
 */
public class PlayBill {
    /**
     * id : 258312
     * start_time : 00:00:00
     * end_time : 01:00:00
     * duration : 3600
     * res_id : 4875
     * day : 1
     * channel_id : 4875
     * program_id : 1905033
     * title : 每夜唱不停
     * broadcasters : [{"id":23767,"username":"950MusicRadio","thumb":"http://tp4.sinaimg.cn/1723167603/180/40021850714/1","weibo_name":"950MusicRadio","weibo_id":"1723167603"}]
     */

    private int id;
    private String start_time;
    private String end_time;
    private boolean playIng;

    public boolean isPlayIng() {
        return playIng;
    }

    public void setPlayIng(boolean playIng) {
        this.playIng = playIng;
    }

    private int duration;
    private int res_id;
    private int day;
    private int channel_id;
    private int program_id;
    private String title;
    private List<Announcer> broadcasters;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRes_id() {
        return res_id;
    }

    public void setRes_id(int res_id) {
        this.res_id = res_id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    public int getProgram_id() {
        return program_id;
    }

    public void setProgram_id(int program_id) {
        this.program_id = program_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Announcer> getBroadcasters() {
        return broadcasters;
    }

    public void setBroadcasters(List<Announcer> broadcasters) {
        this.broadcasters = broadcasters;
    }


}