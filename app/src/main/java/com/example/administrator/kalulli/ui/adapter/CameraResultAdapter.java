package com.example.administrator.kalulli.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.LogUtil;
import com.bumptech.glide.Glide;
import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.data.FoodJson;
import com.example.administrator.kalulli.ui.camera.CameraResultActivity;
import com.example.administrator.kalulli.utils.ComputerTypeUtil;
import com.example.administrator.kalulli.utils.TableUtil;

import java.util.List;

/**
 * Created by Administrator on 2019/4/1.
 */

public class CameraResultAdapter extends RecyclerView.Adapter<CameraResultAdapter.ViewHolder> {
    private static final String TAG = "CameraResultAdapter";
    private Context context;
    private List<FoodJson> list;
    private RecyclerView.LayoutManager layoutManager;

    public CameraResultAdapter(Context context, List<FoodJson> list) {
        this.context = context;
        this.list = list;
        this.layoutManager = new LinearLayoutManager(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cameraitem_view, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: " + list.get(position).getPicture_url());
        String type = ComputerTypeUtil.getType();
        double number;
//        if (type.equals("早餐")){
//            number = Integer.parseInt(list.get(position).getNumber()) * 2;
//        }else if (type.equals("午餐")){
//            number = Integer.parseInt(list.get(position).getNumber()) * 3.5;
//        }else {
//            number = Integer.parseInt(list.get(position).getNumber()) * 3;
//        }
        number = Integer.parseInt(list.get(position).getNumber());
        holder.number.setText(number + "");
        holder.name.setText(list.get(position).getFoodname());
        Glide.with(context)
                .load(list.get(position).getPicture_url()).placeholder(R.drawable.eat)
                .override(300, 300)//指定图片大小
                .into(holder.imageView);
        Toast.makeText(context, "" + list.get(position).getElements().size(), Toast.LENGTH_LONG).show();
        //适配器复用
        ElementAdapter elementAdapter = (ElementAdapter) holder.recyclerView.getAdapter();
        List<FoodJson.Element> elements = list.get(position).getElements();
        if (elementAdapter == null) {
            holder.recyclerView.setLayoutManager(layoutManager);
            holder.recyclerView.setAdapter(new ElementAdapter(context, elements));
        } else {
            elementAdapter.setData(elements);
            elementAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        TextView number;
        RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.result_image);
            name = itemView.findViewById(R.id.result_name);
            number = itemView.findViewById(R.id.result_number);
            recyclerView = itemView.findViewById(R.id.result_element);
        }
    }
}
