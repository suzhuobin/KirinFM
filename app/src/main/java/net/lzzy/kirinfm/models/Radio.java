package net.lzzy.kirinfm.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @author Administrator
 */
public class Radio implements Parcelable {
    /**
     * content_id : 1756
     * content_type : channel
     * cover : http://pic.qingting.fm/2013/channel_logo/1756.jpg
     * title : 广西私家车930
     * description : 根据央视索福瑞发布，2012年私家车930 持续雄踞广西车载收听率第一、市场占有率第一、广告营收第一及三高人群集中度第一，私家车930已成为广西广播第一品牌，全面进入3.0时代！
     * nowplaying : {"id":6960757,"title":"世界不正经","broadcasters":[],"start_time":"21:30:00","duration":3600}
     * audience_count : 478472
     * liveshow_id : 05fd9f6ba5307aa89f3bb940ccd5a601
     * update_time : 2017-11-08 17:00:30
     * categories : [{"id":429,"title":"交通台","pid":428}]
     */
    private int content_id;
    private String content_type;
    private String cover;
    private String title;
    private String description;
    private NowplayingBean nowplaying;
    private long audience_count;
    private String liveshow_id;
    private String update_time;
    private List<RadioCategory> categories;

    public Radio() {
    }

    protected Radio(Parcel in) {
        content_id = in.readInt();
        content_type = in.readString();
        cover = in.readString();
        title = in.readString();
        description = in.readString();
        audience_count = in.readLong();
        liveshow_id = in.readString();
        update_time = in.readString();
        categories = in.createTypedArrayList(RadioCategory.CREATOR);
    }

    public static final Creator<Radio> CREATOR = new Creator<Radio>() {
        @Override
        public Radio createFromParcel(Parcel in) {
            return new Radio(in);
        }

        @Override
        public Radio[] newArray(int size) {
            return new Radio[size];
        }
    };

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NowplayingBean getNowplaying() {
        return nowplaying;
    }

    public void setNowplaying(NowplayingBean nowplaying) {
        this.nowplaying = nowplaying;
    }

    public long getAudience_count() {
        return audience_count;
    }

    public void setAudience_count(long audience_count) {
        this.audience_count = audience_count;
    }

    public String getLiveshow_id() {
        return liveshow_id;
    }

    public void setLiveshow_id(String liveshow_id) {
        this.liveshow_id = liveshow_id;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public List<RadioCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<RadioCategory> categories) {
        this.categories = categories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(content_id);
        dest.writeString(content_type);
        dest.writeString(cover);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(audience_count);
        dest.writeString(liveshow_id);
        dest.writeString(update_time);
        dest.writeTypedList(categories);
    }

    public static class NowplayingBean {
        /**
         * id : 6960757
         * title : 世界不正经
         * broadcasters : []
         * start_time : 21:30:00
         * duration : 3600
         */

        private int id;
        private String title;
        private String start_time;
        private int duration;
        private List<?> broadcasters;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public List<?> getBroadcasters() {
            return broadcasters;
        }

        public void setBroadcasters(List<?> broadcasters) {
            this.broadcasters = broadcasters;
        }
    }

    public static class CategoriesBean {
        /**
         * id : 429
         * title : 交通台
         * pid : 428
         */

        private int id;
        private String title;
        private int pid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }
    }
    public String getCategoriesJson() throws JSONException {
        JSONArray jsonArray=new JSONArray();
        for (RadioCategory radioCategory:categories){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("id",radioCategory.getId());
            jsonObject.put("title",radioCategory.getTitle());
            jsonObject.put("pid",radioCategory.getPid());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }
}
