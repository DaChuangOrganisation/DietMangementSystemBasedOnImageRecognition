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
import android.widget.Adapter;
import android.widget.Toast;

import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.base.OnClickListener;
import com.example.administrator.kalulli.litepal.Recommendation;
import com.example.administrator.kalulli.litepal.User;
import com.example.administrator.kalulli.ui.adapter.SuggestAdapter;
import com.example.administrator.kalulli.ui.daily.DailyFragment;
import com.example.administrator.kalulli.utils.DailyUtil;
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
    private static String[] classifications = {"谷薯芋、杂豆、主食", "蛋类、肉类及制品",
            "奶类及制品","蔬果和菌藻","坚果、大豆及制品","饮料","食用油、油脂及制品",
            "调味品","零食、点心、冷饮","其它","菜肴"};

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
                .setLabel("每日推荐")
                .setResId(R.drawable.ic_add_black_24dp)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(1)
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
        User user = LitePal.findFirst(User.class);
        int BMILevel = user.suggest_BMI();
        double remainingCalorie = DailyUtil.getNeedCalorie();
        if(remainingCalorie<0)
            Toast.makeText(getContext(), "今日饮食已达标!", Toast.LENGTH_SHORT).show();
        int remainingMeal = user.getDishes();//api
        SuggestAdapter adapter = null;
        List<Recommendation> finalRecommendations = null;
        String[] categories = null;
        switch (position){
            case 0:
                switch (BMILevel){
                    case 1://菜肴  谷薯芋、杂豆、主食  蛋类、肉类及制品
                        categories = new String[]{"菜肴","谷薯芋、杂豆、主食","蛋类、肉类及制品"};
                        break;
                    case 2://谷薯芋、杂豆、主食 坚果、大豆及制品   蔬果和菌藻 奶类及制品
                        categories = new String[]{"谷薯芋、杂豆、主食","坚果、大豆及制品","蔬果和菌藻 奶类及制品"};
                        break;
                    case 3://谷薯芋、杂豆、主食  蔬果和菌藻
                        categories = new String[]{"谷薯芋、杂豆、主食","蔬果和菌藻"};
                        break;
                    case 4://蔬果和菌藻
                        categories = new String[]{"蔬果和菌藻"};
                        break;
                }
                finalRecommendations = recommendationAlgorithm(categories,remainingCalorie,remainingMeal);
                List<Recommendation> cuttedRecommendations = new ArrayList<>();
                for(int i=0;i<remainingMeal;){
                    int randomNumber = user.getRandom(0,finalRecommendations.size()-1);//api
                    Recommendation pickoutOne = finalRecommendations.get(randomNumber);
                    if(!cuttedRecommendations.contains(pickoutOne)){//非重复推荐
                        cuttedRecommendations.add(pickoutOne);
                        i++;
                    }
                }
                adapter = new SuggestAdapter(finalRecommendations,getContext());
                break;
        }
        suggestRecyclerView.setAdapter(adapter);
        suggestRecyclerView.setLayoutManager(layoutManager);
        rfabHelper.collapseContent();
    }

    //推荐算法
    private List<Recommendation> recommendationAlgorithm(String[] categories,double remainCalorie,int remainMeal){
        List<Recommendation> recommendations = new ArrayList<>();
        String singleCalorie = String.valueOf(Double.valueOf(remainCalorie).intValue()/remainMeal);
        for(int i = 0;i<categories.length;i++){
            List<Recommendation> metRecommendations = LitePal.where("classification=? and calorie<=?",categories[i],singleCalorie)
                    .limit(remainMeal)
                    .find(Recommendation.class);
            unionList(recommendations,metRecommendations);
        }
        return recommendations;
    }

    private List<Recommendation> unionList(List<Recommendation> beJoined, List<Recommendation> join){
        if(join.size()!=0){
            for (Recommendation r:join) {
                beJoined.add(r);
            }
        }
        return beJoined;
    }
}
