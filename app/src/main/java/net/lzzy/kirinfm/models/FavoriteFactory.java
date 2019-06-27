package net.lzzy.kirinfm.models;

import com.google.gson.Gson;

import net.lzzy.kirinfm.connstants.DbConstants;
import net.lzzy.kirinfm.utils.AppUtils;
import net.lzzy.sqllib.SqlRepository;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzzy_gxy
 * @date 2019/4/17
 * Description:
 */
public class FavoriteFactory {
    private static final FavoriteFactory OUR_INSTANCE = new FavoriteFactory();
    private SqlRepository<Favorite> repository;

    public static FavoriteFactory getInstance() {
        return OUR_INSTANCE;
    }

    private FavoriteFactory() {
        repository = new SqlRepository<>(AppUtils.getContext(), Favorite.class, DbConstants.packager);

    }

    /**
     * 查询收藏的练习
     */
    public Favorite getFavoriteByRadio(String radioId) {
        try {
            List<Favorite> favorites = repository.getByKeyword(radioId, new String[]{Favorite.COL_RADIO_ID}, true);
            if (favorites.size() > 0) {
                return favorites.get(0);
            }
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDeleteString(String radioId) {
        Favorite favorite = getFavoriteByRadio(radioId);
        return favorite == null ? null : repository.getDeleteString(favorite);
    }

    /**
     * 判断是否被收藏
     *
     * @param radioId
     * @return
     */
    public boolean isRadioStarred(String radioId) {
        try {
            List<Favorite> favorites = repository.getByKeyword(radioId,
                    new String[]{Favorite.COL_RADIO_ID}, true);
            return favorites.size() > 0;
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 收藏
     *
     * @param radioId
     * @param title
     * @param audience_count
     * @param cover
     * @param description
     * @param categories
     */
    public void starRadio(int radioId, String title, long audience_count, String cover, String description, String categories) {
        Favorite favorite = getFavoriteByRadio(String.valueOf(radioId));
        if (favorite == null) {
            favorite = new Favorite();
            favorite.setRadioId(radioId);
            favorite.setTitle(title);
            favorite.setAudience_count(audience_count);
            favorite.setCover(cover);
            favorite.setDescription(description);
            favorite.setCategories(categories);
            repository.insert(favorite);
        }
    }

    /**
     * 取消收藏
     *
     * @param radioId
     */
    public void cancelStarRadio(int radioId) {
        Favorite favorite = getFavoriteByRadio(String.valueOf(radioId));
        if (favorite != null) {
            repository.delete(favorite);
        }
    }

    public List<Radio> getFavoriteRadio() {
        List<Favorite> favorites = repository.get();
        List<Radio> radios = new ArrayList<>();
        for (Favorite favorite : favorites) {
            Radio radio = new Radio();
            radio.setContent_id(favorite.getRadioId());
            radio.setAudience_count(favorite.getAudience_count());
            radio.setCover(favorite.getCover());
            radio.setTitle(favorite.getTitle());
            radio.setDescription(favorite.getDescription());
            String s = favorite.getCategories();
            Gson gson = new Gson();
            JSONArray jsonArray = null;
            List<RadioCategory> radioCategories = new ArrayList<>();
            try {
                jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    radioCategories.add(gson.fromJson(jsonArray.get(i).toString(), RadioCategory.class));
                }
                radio.setCategories(radioCategories);
            } catch (JSONException e) {
                e.printStackTrace();
                radio.setCategories(new ArrayList<>());
            }
            radios.add(radio);
        }
        return radios;
    }
}
