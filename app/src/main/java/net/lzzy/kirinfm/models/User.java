package net.lzzy.kirinfm.models;

import android.content.Context;
import android.content.SharedPreferences;

import net.lzzy.kirinfm.utils.AppUtils;

/**
 * @author lzzy_gxy on 2019/6/24.
 * Description:
 */
public class User {
    public static final String KEY_TIME = "keyTime";
    private SharedPreferences spTime;

    private static final User INSTANCE = new User();

    private User() {
        spTime = AppUtils.getContext()
                .getSharedPreferences("refresh_time", Context.MODE_PRIVATE);
    }

    public static User getInstance() {
        return INSTANCE;
    }
}
