package net.lzzy.kirinfm.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import net.lzzy.kirinfm.R;
import net.lzzy.kirinfm.models.FavoriteFactory;
import net.lzzy.kirinfm.models.Radio;
import net.lzzy.kirinfm.thread.GetNetworkAreaThread;
import net.lzzy.kirinfm.utils.ViewUtils;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Administrator
 */
public class FavoriteFragment extends BaseFragment {
    private ListView listview;
    private List<Radio> radios = FavoriteFactory.getInstance().getFavoriteRadio();
    private GenericAdapter<Radio> adapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected void populate() {
        listview = find(R.id.fragment_favorite_gv);
        //无数据视图
        View empty = find(R.id.fragment_favorite_none);
        listview.setEmptyView(empty);
        adapter = new GenericAdapter<Radio>(getActivity(), R.layout.fragment_favorte_item, radios) {
            @Override
            public void populate(ViewHolder viewHolder, Radio radio) {
                ImageView imageView = viewHolder.getView(R.id.fragment_fm_gv_item_img);
                viewHolder.setTextView(R.id.fragment_favorite_item_tv_name, radio.getTitle());
                viewHolder.setTextView(R.id.fragment_favorite_item_description, radio.getDescription());
                viewHolder.setTextView(R.id.fragment_favorite_item_category, String.valueOf(radio.getCategories()));
                viewHolder.setTextView(R.id.fragment_favorite_item_tv_audience, "" + (radio.getAudience_count()));
            }

            @Override
            public boolean persistInsert(Radio radios) {
                return false;
            }

            @Override
            public boolean persistDelete(Radio radios) {
                return false;
            }

        };
        listview.setAdapter(adapter);
        listview.setOnItemClickListener((parent, view, position, id) -> {
            Radio radio = radios.get(position);
            ViewUtils.showPlayBill(getContext(), "返回收藏", radio
                    .getTitle(), new ArrayList<>(), radio);
            new GetNetworkAreaThread() {
                @Override
                protected void abstractOnPostExecute(List playBills) {
                    ViewUtils.updatePlayBillAdapter(playBills);
                }
            }.execute(radios.get(position).getContent_id());
        });
    }

    @Override
    public void search(String kw) {

    }

    public void updateFavorite() {
        adapter.clear();
        adapter.addAll(FavoriteFactory.getInstance().getFavoriteRadio());
    }
}
