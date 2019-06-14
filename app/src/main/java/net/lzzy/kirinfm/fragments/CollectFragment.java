package net.lzzy.kirinfm.fragments;

import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import net.lzzy.kirinfm.R;
import net.lzzy.kirinfm.thread.GetNetworkAreaThread;
import net.lzzy.kirinfm.models.CollectionFactory;
import net.lzzy.kirinfm.models.Radio;
import net.lzzy.kirinfm.utils.ViewUtils;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * 收藏Fragment
 * A simple {@link Fragment} subclass.
 *
 * @author Administrator
 */
public class CollectFragment extends BaseFragment/* implements MainActivity.UpdateFragmentInterface */ {

    private GridView gv;
    private List<Radio> radios = CollectionFactory.getInstance().getFavoriteRadio();
    private GenericAdapter<Radio> adapter;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_collect;

    }

    @Override
    protected void populate() {
        gv = find(R.id.fragment_collect_gv);
        //无数据视图
        View empty = find(R.id.fragment_collect_none);
        gv.setEmptyView(empty);
        adapter = new GenericAdapter<Radio>(getActivity(), R.layout.fragment_fm_gv_item, radios) {
            @Override
            public void populate(ViewHolder viewHolder, Radio radio) {
                ImageView imageView = viewHolder.getView(R.id.fragment_fm_gv_item_img);
                Picasso.get().load(radio.getCover())
                        .into(imageView);
                viewHolder.setTextView(R.id.fragment_fm_gv_item_tv_name, radio.getTitle());
                viewHolder.setTextView(R.id.fragment_fm_gv_item_tv_audience, "收听：" + (radio.getAudience_count()));
                ImageView img = viewHolder.getView(R.id.fragment_fm_gv_con);
                int starId = CollectionFactory.getInstance().isRadioStarred(String.valueOf(radio.getContent_id())) ?
                        android.R.drawable.star_big_off : android.R.drawable.star_big_on;
                img.setImageResource(starId);
                boolean is = CollectionFactory.getInstance().isRadioStarred(String.valueOf(radio.getContent_id()));
                if (is) {
                    img.setImageResource(android.R.drawable.star_big_on);
                } else {
                    img.setImageResource(android.R.drawable.star_big_off);
                }
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (is) {
                            CollectionFactory.getInstance().cancelStarRadio(radio.getContent_id());
                        } else {
                            CollectionFactory.getInstance().starRadio(radio.getContent_id(), radio.getTitle(), radio.getAudience_count(), radio.getCover());
                        }
                        updateFavorite();
                    }
                });
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
        gv.setAdapter(adapter);
        gv.setOnItemClickListener((parent, view, position, id) -> {
            ViewUtils.showPlayBill(getContext(), "返回收藏",
                    radios.get(position).getTitle(), new ArrayList<>());
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


    /* @Override
     public void UpdateAdapter() {
         updateFavorite();
     }
     */
    public void updateFavorite() {
        adapter.clear();
        adapter.addAll(CollectionFactory.getInstance().getFavoriteRadio());
    }
}
