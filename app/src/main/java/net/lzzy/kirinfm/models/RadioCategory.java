package net.lzzy.kirinfm.models;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Administrator
 */
public class RadioCategory implements Parcelable {
    /**
     * id : 433
     * title : 资讯台
     */
    private int id;
    private String title;
    private int pid;

    protected RadioCategory(Parcel in) {
        id = in.readInt();
        title = in.readString();
        pid = in.readInt();
    }

    public static final Creator<RadioCategory> CREATOR = new Creator<RadioCategory>() {
        @Override
        public RadioCategory createFromParcel(Parcel in) {
            return new RadioCategory(in);
        }

        @Override
        public RadioCategory[] newArray(int size) {
            return new RadioCategory[size];
        }
    };

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

    public RadioCategory() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeInt(pid);
    }
}
