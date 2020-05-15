package com.example.administrator.kalulli.ui.suggest;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.base.OnClickListener;
import com.example.administrator.kalulli.litepal.Recommendation;
import com.example.administrator.kalulli.ui.adapter.SuggestAdapter;
import com.wang.avi.AVLoadingIndicatorView;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuggestFragment extends Fragment
        implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {

    public static int number = 0;
    public static Map<String,Integer>map = new HashMap();


    private static final String TAG = "SuggestFragment";
    @BindView(R.id.suggest_recyclerView)
    RecyclerView suggestRecyclerView;
    Unbinder unbinder;
    GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
    @BindView(R.id.do_fbtn)
    RapidFloatingActionButton doFbtn;
    @BindView(R.id.do_fbtn_layout)
    RapidFloatingActionLayout doFbtnLayout;
//    @BindView(R.id.loading)
//    AVLoadingIndicatorView loading;
    private int baseSize = 0;

    int selectPosition = 4;
    private RapidFloatingActionHelper rfabHelper;
    private List<Recommendation> recommendations = new ArrayList<>();//存储推荐的数据
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            loading.hide();
//            initRecyclerView();
        }
    };


    public SuggestFragment() {
        // Required empty public constructor
    }

    private void initRecyclerView() {
        SuggestAdapter suggestAdapter = new SuggestAdapter(recommendations, getContext());
        suggestAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void click(int position, View view) {
                Intent intent = new Intent(getActivity(), ShowFoodActivity.class);
//                intent.putExtra("objectId", alllist.get(position).getObjectId());
//                if (position + 1 <= baseSize){
//                    intent.putExtra("table", TableUtil.FOOD_TABLE_NAME);
//                }else {
//                    intent.putExtra("table", TableUtil.DAILY_FOOD_TABLE_NAME);
//
//                }
                startActivity(intent);
            }
        });
        suggestRecyclerView.setAdapter(suggestAdapter);
        suggestRecyclerView.setLayoutManager(layoutManager);
    }



    public static SuggestFragment getInstance() {
        return new SuggestFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suggest, container, false);
        unbinder = ButterKnife.bind(this, view);
        initFloating();//初始化浮动按钮
        return view;
    }

    private void initFloating() {
//        loading.show();
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(getContext());
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel("早餐推荐")
                .setResId(R.drawable.ic_add_black_24dp)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(1)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("午餐推荐")
                .setResId(R.drawable.ic_add_black_24dp)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(2)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("晚餐推荐")
                .setResId(R.drawable.ic_add_black_24dp)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(3)
        );
        rfaContent
                .setItems(items)
                .setIconShadowRadius(5)
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(5)
        ;
        rfabHelper = new RapidFloatingActionHelper(
                getContext(),
                doFbtnLayout,
                doFbtn,
                rfaContent
        ).build();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //浮动按钮点击事件
    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        switch (position){
            case 0:
                recommendations = LitePal.where("name = ?","菜心")
                        .find(Recommendation.class);
                Log.d(TAG,"推荐菜品个数：" + String.valueOf(recommendations.size()));
                SuggestAdapter adapter = new SuggestAdapter(recommendations,getContext());
                suggestRecyclerView.setAdapter(adapter);
                suggestRecyclerView.setLayoutManager(layoutManager);
                break;
            case 1:
                break;
            case 2:
                break;
        }
        rfabHelper.collapseContent();
    }

    //推荐算法
    public void recommendationAlgorithm(){

    }
}
