package net.lzzy.kirinfm.models;

/**
 * @author Administrator
 */
public class Play {
    private String radioName;
    private String cover;
    private String playBillName;
    private String broadcasters;
    private boolean playIng;

    public Play() {
    }

    public Play(String radioName, String cover, String playBillName, String broadcasters, boolean playIng) {
        this.radioName = radioName;
        this.cover = cover;
        this.playBillName = playBillName;
        this.broadcasters = broadcasters;
        this.playIng = playIng;
    }

    public String getRadioName() {
        return radioName;
    }

    public void setRadioName(String radioName) {
        this.radioName = radioName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPlayBillName() {
        return playBillName;
    }

    public void setPlayBillName(String playBillName) {
        this.playBillName = playBillName;
    }

    public String getBroadcasters() {
        return broadcasters;
    }

    public void setBroadcasters(String broadcasters) {
        this.broadcasters = broadcasters;
    }

    public boolean isPlayIng() {
        return playIng;
    }

    public void setPlayIng(boolean playIng) {
        this.playIng = playIng;
    }
}
