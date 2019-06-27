package net.lzzy.kirinfm.fragments;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.util.Pair;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import net.lzzy.kirinfm.R;
import net.lzzy.kirinfm.connstants.ApiConstants;
import net.lzzy.kirinfm.models.FavoriteFactory;
import net.lzzy.kirinfm.models.Radio;
import net.lzzy.kirinfm.models.RadioCategory;
import net.lzzy.kirinfm.models.Region;
import net.lzzy.kirinfm.models.NetworkArea;
import net.lzzy.kirinfm.models.view.RadioGenre;
import net.lzzy.kirinfm.network.ApiService;
import net.lzzy.kirinfm.thread.GetNetworkAreaThread;
import net.lzzy.kirinfm.thread.GetRadioTypeThread;
import net.lzzy.kirinfm.thread.SearchRadioThread;
import net.lzzy.kirinfm.utils.AbstractStaticHandler;
import net.lzzy.kirinfm.utils.AppUtils;
import net.lzzy.kirinfm.utils.ViewUtils;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author Administrator
 */
public class FmFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener {
    public static final String ARG_REGIONS = "arg_regions";
    public static final String ARG_THIS_REGION = "arg_thisRegion";
    public static final String ARG_RADIO_CATEGORIES = "arg_radio_Categories";
    public static final int WHAT_GET_THIS_REGION_RADIO_OK = 1;
    public static final int WHAT_GET_THIS_REGION_RADIO_ERROR = 2;
    private static final int WHAT_GET_IMAG = 3;
    public static final int WHAT = 8;
    private List<Region> regions = new ArrayList<>();
    private List<RadioCategory> radioCategories = new ArrayList<>();
    private NetworkArea networkArea;
    private SearchView search;
    private GridView gv;
    private int thisRadioSize = 0;
    private List<Radio> radios = new ArrayList<>();
    private GenericAdapter<Radio> gvAdapter;
    private List<Bitmap> bitmaps = new ArrayList<>();
    private ArrayAdapter<Region> spinnAdapter;
    private ImageView favorite;
    private LinearLayout region_layout;
    private TextView tvRegion;
    private SwipeRefreshLayout refresh;
    private int getLastVisiblePosition = 0, lastVisiblePositionY = 0;
    private int pag = 1;
    private int thisPag = 1;
    private TextView hint;
    private HorizontalListView lv;
    private GenericAdapter<RadioCategory> lvAdapter;

    public static FmFragment newInstance(List<Region> regions,
                                         List<RadioCategory> radioCategories,
                                         NetworkArea networkArea) {
        FmFragment fragment = new FmFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_REGIONS,
                (ArrayList<? extends Parcelable>) regions);
        args.putParcelableArrayList(ARG_RADIO_CATEGORIES,
                (ArrayList<? extends Parcelable>) radioCategories);
        args.putParcelable(ARG_THIS_REGION, networkArea);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            regions = getArguments().getParcelableArrayList(ARG_REGIONS);
            radioCategories = getArguments().getParcelableArrayList(ARG_RADIO_CATEGORIES);
            networkArea = getArguments().getParcelable(ARG_THIS_REGION);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_fm;
    }

    @Override
    protected void populate() {
        initView();
        initData();
    }

    private void initView() {
        lv = find(R.id.fragment_fm_horizontal_lv);
        horizontalLv();
        hint = find(R.id.fragment_practices_tv_hint);
        hint.setVisibility(View.GONE);
        refresh = find(R.id.fragment_fm_refresh);
        refresh.setOnRefreshListener(this);
        tvRegion = find(R.id.fragment_fm_tv_region);
        region_layout = find(R.id.fragment_fm_layout_region);
        search = find(R.id.fragment_fm_search);
        search.setQueryHint("请输入关键词搜索");
        search.setOnQueryTextListener(new ViewUtils.AbstractQueryHandlerRadio() {
            @Override
            public void handleQuery(String kw) {
                if ("".equals(kw)) {
                    getThisRegionRadio((Integer) tvRegion.getTag(), 1, 15,
                            true);
                } else {
                    new SearchRadioThread<FmFragment>(FmFragment.this) {
                        @Override
                        protected void onPostExecute(List<Radio> radios,
                                                     FmFragment fmFragment) {
                            fmFragment.radios.clear();
                            fmFragment.radios.addAll(radios);
                            fmFragment.gvAdapter.notifyDataSetChanged();
                        }
                    }.execute(kw);
                }
            }
        });
        SearchView.SearchAutoComplete auto = search.findViewById(R.id.search_src_text);
        auto.setHintTextColor(Color.WHITE);
        auto.setTextColor(Color.WHITE);
        ImageView icon = search.findViewById(R.id.search_button);
        ImageView icX = search.findViewById(R.id.search_close_btn);
        ImageView icG = search.findViewById(R.id.search_go_btn);
        icon.setColorFilter(Color.RED);
        icX.setColorFilter(Color.WHITE);
        icG.setColorFilter(Color.WHITE);

        gv = find(R.id.fragment_fm_gv);
        //无数据视图
        View empty = find(R.id.no_network);
        gv.setEmptyView(empty);
        /**
         * 初始化要显示的数据
         */
    }

    private void horizontalLv() {
        lvAdapter = new GenericAdapter<RadioCategory>(getContext(),
                R.layout.fragment_fm_horizontal_lv_item, radioCategories) {
            @Override
            public void populate(ViewHolder viewHolder, RadioCategory radioCategory) {
                viewHolder.setTextView(R.id.fragment_fm_horizontal_lv_item_tv,
                        radioCategory.getTitle());
            }

            @Override
            public boolean persistInsert(RadioCategory radioCategory) {
                return false;
            }

            @Override
            public boolean persistDelete(RadioCategory radioCategory) {
                return false;
            }
        };
        lv.setAdapter(lvAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                new GetRadioTypeThread<FmFragment>(FmFragment.this) {
                    @Override
                    protected void onPostExecute(List<Radio> radios,
                                                 FmFragment fmFragment) {
                        fmFragment.radios.clear();
                        fmFragment.gvAdapter.addAll(radios);
                    }
                }.execute(radioCategories.get(position).getId());
            }
        });
    }

    private void initData() {
        for (int i = 0; i < regions.size(); i++) {
            Region region = regions.get(i);
            if (region.getTitle().equals(networkArea.getRegion())) {
                regions.remove(region);
                regions.add(0, region);
                tvRegion.setText(networkArea.getRegion());
                tvRegion.setTag(region.getId());
            }
        }
        gvAdapter = new GenericAdapter<Radio>(getContext(), R.layout.fragment_fm_gv_item,
                radios) {
            @Override
            public void populate(ViewHolder viewHolder, Radio radio) {
                ImageView imageView = viewHolder.getView(R.id.fragment_fm_gv_item_img);
                Picasso.get().load(radio.getCover()).into(imageView);
                viewHolder.setTextView(R.id.fragment_favorite_item_tv_name, radio.getTitle());
                long count = radio.getAudience_count();
                viewHolder.setTextView(R.id.fragment_favorite_item_tv_audience, "" + count);
                ImageView favorite = viewHolder.getView(R.id.fragment_fm_gv_con);
                int starId = FavoriteFactory.getInstance().isRadioStarred
                        (String.valueOf(radio.getContent_id())) ?
                        android.R.drawable.star_big_off :
                        android.R.drawable.star_big_on;
                favorite.setImageResource(starId);
                boolean is = FavoriteFactory.getInstance().isRadioStarred
                        (String.valueOf(radio.getContent_id()));
                if (is) {
                    favorite.setImageResource(android.R.drawable.star_big_on);
                } else {
                    favorite.setImageResource(android.R.drawable.star_big_off);
                }
                favorite.setOnClickListener(v -> {
                    if (is) {
                        FavoriteFactory.getInstance().cancelStarRadio
                                (radio.getContent_id());
                    } else {
                        try {
                            FavoriteFactory.getInstance().starRadio
                                    (radio.getContent_id(),
                                            radio.getTitle(), radio.getAudience_count(),
                                            radio.getCover(),
                                            radio.getDescription(),
                                            radio.getCategoriesJson());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    gvAdapter.notifyDataSetChanged();
                });
            }

            @Override
            public boolean persistInsert(Radio radio) {
                return false;
            }

            @Override
            public boolean persistDelete(Radio radio) {
                return false;
            }
        };
        gv.setAdapter(gvAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Radio radio = radios.get(position);
                ViewUtils.showPlayBill(getContext(), tvRegion.getText().toString(),
                        radio.getTitle(), new ArrayList<>(), radio);
                new GetNetworkAreaThread() {
                    @Override
                    protected void abstractOnPostExecute(List playBills) {
                        ViewUtils.updatePlayBillAdapter(playBills);
                    }
                }.execute(radios.get(position).getContent_id());
            }
        });
        //切换地区
        region_layout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setIcon(R.drawable.location);
            builder.setTitle("选择地区");
            final String[] items = new String[regions.size()];
            for (int i = 0; i < regions.size(); i++) {
                items[i] = regions.get(i).getTitle();
            }
            builder.setSingleChoiceItems(items, -1,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String regionText = items[which];
                            for (Region region : regions) {
                                if (region.getTitle().equals(regionText)) {
                                    tvRegion.setText(regionText);
                                    tvRegion.setTag(region.getId());
                                    getThisRegionRadio(region.getId(), 1, 15, true);
                                }
                            }
                            dialog.dismiss();
                        }
                    });
            builder.show();
        });
        getThisRegionRadio((Integer) tvRegion.getTag(), 1, 15, false);
        gv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    //滚动到底部
                    if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                        hint.setVisibility(View.VISIBLE);
                        View v = (View) view.getChildAt(view.getChildCount() - 1);
                        int[] location = new int[2];
                        /**
                         * 获取坐标
                         */
                        v.getLocationOnScreen(location);
                        int y = location[1];
                        /**
                         * 拖至底部
                         */
                        if (view.getLastVisiblePosition() !=
                                getLastVisiblePosition && lastVisiblePositionY != y) {
                            getLastVisiblePosition = view.getLastVisiblePosition();
                            lastVisiblePositionY = y;
                            return;
                        } else if (view.getLastVisiblePosition() ==
                                getLastVisiblePosition && lastVisiblePositionY == y) {
                            if (thisPag < pag) {
                                thisPag++;
                                getThisRegionRadio((int) tvRegion.getTag(), thisPag,
                                        15, false);
                            } else {
                                hint.setVisibility(View.GONE);
                                Toast.makeText(view.getContext(), "已经加载所有电台",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    getLastVisiblePosition = 0;
                    lastVisiblePositionY = 0;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

            }
        });
    }

    /**
     * @param regionId 地区id
     * @param page     页码
     * @param pageSize 请求数量
     */
    private void getThisRegionRadio(int regionId, int page, int pageSize, boolean refresh) {
        AppUtils.getExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = ApiService.okGet(ApiConstants.getRadio
                            (regionId, page, pageSize));
                    Message message = handler.obtainMessage
                            (WHAT_GET_THIS_REGION_RADIO_OK, new Pair<>(json, refresh));
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(WHAT_GET_THIS_REGION_RADIO_ERROR);
                }
            }
        });
    }

    private MyHandler handler = new MyHandler(FmFragment.this);

    @Override
    public void onRefresh() {
        getThisRegionRadio((Integer) tvRegion.getTag(),
                1, 15, true);
    }

    private static class MyHandler extends AbstractStaticHandler<FmFragment> {
        Gson gson = new Gson();

        public MyHandler(FmFragment fmFragment) {
            super(fmFragment);
        }

        @Override
        public void handleMessage(Message msg, FmFragment fmFragment) {
            switch (msg.what) {
                case WHAT_GET_THIS_REGION_RADIO_OK:
                    Pair<String, Boolean> pair = (Pair<String, Boolean>) msg.obj;
                    RadioGenre radioJSON = gson.fromJson(pair.first, RadioGenre.class);
                    float i = (float) radioJSON.getData().getTotal() / 15;
                    fmFragment.pag = (int) Math.ceil(i);
                    if (pair.second) {
                        fmFragment.gvAdapter.clear();
                        fmFragment.refresh.setRefreshing(false);
                        fmFragment.thisPag = 1;
                        Toast.makeText(fmFragment.getContext(), "更新成功", Toast.LENGTH_LONG).show();
                    } else {

                    }
                    fmFragment.hint.setVisibility(View.GONE);
                    fmFragment.gvAdapter.addAll(radioJSON.getData().getItems());
                    break;
                case WHAT_GET_THIS_REGION_RADIO_ERROR:

                    break;
                case WHAT:

                    break;
                default:
                    break;
            }

        }
    }

    /**
     * 更新收藏状态
     */
    public void updateFavorite() {
        gvAdapter.notifyDataSetChanged();
    }

    @Override
    public void search(String kw) {

    }
}
