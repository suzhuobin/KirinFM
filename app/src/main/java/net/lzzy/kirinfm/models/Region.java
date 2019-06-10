package net.lzzy.kirinfm.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


/**
 * @author Administrator
 */
public class Region implements Parcelable {
    /**
     * id : 3
     * title : 北京
     */

    private int id;
    private String title;

    public Region() {

    }

    private Region(Parcel in) {
        id = in.readInt();
        title = in.readString();
    }

    public static final Creator<Region> CREATOR = new Creator<Region>() {
        @Override
        public Region createFromParcel(Parcel in) {
            return new Region(in);
        }

        @Override
        public Region[] newArray(int size) {
            return new Region[size];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
    }

    @NonNull
    @Override
    public String toString() {
        return title;
    }
}
