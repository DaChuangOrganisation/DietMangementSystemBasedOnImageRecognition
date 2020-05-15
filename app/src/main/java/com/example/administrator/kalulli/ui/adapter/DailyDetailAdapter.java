package com.example.administrator.kalulli.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.base.OnClickListener;
import com.example.administrator.kalulli.data.FoodJson;
import com.example.administrator.kalulli.litepal.DailyCalorie;
import com.example.administrator.kalulli.litepal.FoodItem;
import com.example.administrator.kalulli.utils.ComputerTypeUtil;

import java.util.ArrayList;
import java.util.List;

public class DailyDetailAdapter extends RecyclerView.Adapter<DailyDetailAdapter.ViewHolder>{
    private static final String TAG = "DailyDetailAdapter";
    private Context context;
    //private List<FoodItem> list;
    private ArrayList<FoodItem> list=new ArrayList<FoodItem>();//某顿列表
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public DailyDetailAdapter(Context context, ArrayList<FoodItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DailyDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cameraitem_view,parent,false);
        return new DailyDetailAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DailyDetailAdapter.ViewHolder holder, final int position) {
        if (onClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.click(position,v);
                }
            });
        }

        Log.i(TAG, "onBindViewHolder: "+list.get(position).getImgPath());
        String type = ComputerTypeUtil.getType();
        double number;
//        if (type.equals("早餐")){
//            number = Integer.parseInt(list.get(position).getNumber()) * 2;
//        }else if (type.equals("午餐")){
//            number = Integer.parseInt(list.get(position).getNumber()) * 3.5;
//        }else {
//            number = Integer.parseInt(list.get(position).getNumber()) * 3;
//        }
        number = list.get(position).getCalorie();
        holder.number.setText(number+"");
        holder.name.setText(list.get(position).getFoodName());
        Glide.with(context)
                .load(list.get(position).getImgPath()).placeholder(R.drawable.eat)
                .override(100, 100)//指定图片大小
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeData(int position){
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        TextView number;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.result_image);
            name = itemView.findViewById(R.id.result_name);
            number = itemView.findViewById(R.id.result_number);

        }
    }
}
