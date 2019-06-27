package net.lzzy.kirinfm.models;

import net.lzzy.sqllib.AsPrimaryKey;
import net.lzzy.sqllib.Ignored;
import net.lzzy.sqllib.Sqlitable;

/**
 * @author lzzy_gxy
 * @date 2019/4/16
 * Description:
 */
public class Favorite implements Sqlitable {
    @Ignored
    public static final String COL_RADIO_ID="radioId";
    @AsPrimaryKey
    private int radioId;
    private String title;
    private long audience_count;
    private String cover;
    private String description;
    private String categories;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public int getRadioId() {
        return radioId;
    }

    public void setRadioId(int radioId) {
        this.radioId = radioId;
    }

    public static String getColRadioId() {
        return COL_RADIO_ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getAudience_count() {
        return audience_count;
    }

    public void setAudience_count(long audience_count) {
        this.audience_count = audience_count;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public Object getIdentityValue() {
        return radioId;
    }

    @Override
    public boolean needUpdate() {
        return false;
    }
}
