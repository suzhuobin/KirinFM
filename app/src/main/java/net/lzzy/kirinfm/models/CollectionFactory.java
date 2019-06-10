package net.lzzy.kirinfm.models;

import net.lzzy.kirinfm.connstants.DbConstants;
import net.lzzy.kirinfm.utils.AppUtils;
import net.lzzy.sqllib.SqlRepository;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lzzy_gxy
 * @date 2019/4/17
 * Description:
 */
public class CollectionFactory {
    private static final CollectionFactory OUR_INSTANCE = new CollectionFactory();
    private SqlRepository<Collection> repository;
    public static CollectionFactory getInstance() {
        return OUR_INSTANCE;
    }
    private CollectionFactory(){
        repository = new SqlRepository<>(AppUtils.getContext(), Collection.class, DbConstants.packager);

    }

    /**查询收藏的练习*/
    public Collection getFavoriteByRadio(String radioId ){
        try {
            List<Collection> favorites=repository.getByKeyword(radioId,new String[]{Collection.COL_RADIO_ID},true);
            if (favorites.size()>0){
                return favorites.get(0);
            }
        }catch (IllegalAccessException|InstantiationException e){
            e.printStackTrace();
        }
        return null;
    }

    public String getDeleteString(String radioId){
        Collection favorite=getFavoriteByRadio(radioId);
        return favorite==null?null:repository.getDeleteString(favorite);
    }

    //判断是否被收藏
    public boolean isRadioStarred(String radioId){
        try {
            List<Collection> favorites=repository.getByKeyword(radioId,
                    new String[]{Collection.COL_RADIO_ID},true);
            return favorites.size()>0;
        } catch (IllegalAccessException |InstantiationException e) {
            e.printStackTrace();
            return false;
        }
    }

    //收藏
    public void starRadio(int radioId,String title,long audience_count,String cover ){
        Collection favorite=getFavoriteByRadio(String.valueOf(radioId));
        if (favorite==null){
            favorite=new Collection();
            favorite.setRadioId(radioId);
            favorite.setTitle(title);
            favorite.setAudience_count(audience_count);
            favorite.setCover(cover);
            repository.insert(favorite);
        }
    }

    //取消收藏
    public void cancelStarRadio(int radioId){
        Collection favorite=getFavoriteByRadio(String.valueOf(radioId));
        if (favorite!=null){
            repository.delete(favorite);
        }
    }
    public List<Radio> getFavoriteRadio(){
        List<Collection> favorites=repository.get();
        List<Radio> radios=new ArrayList<>();
        for (Collection favorite:favorites){
            Radio radio=new Radio();
            radio.setContent_id(favorite.getRadioId());
            radio.setAudience_count(favorite.getAudience_count());
            radio.setCover(favorite.getCover());
            radio.setTitle(favorite.getTitle());
            radios.add(radio);
        }
        return radios;
    }
}
