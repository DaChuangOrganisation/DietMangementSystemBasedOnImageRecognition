package com.example.administrator.kalulli.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.data.FoodJson;
import com.example.administrator.kalulli.data.FoodJson.Element;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by mumu on 2020/5/8.
 */
public class ElementAdapter extends RecyclerView.Adapter<ElementAdapter.ViewHolder> {
    private Context context;
    private List<Element> list;

    public ElementAdapter(Context context, List<Element> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.element_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameView.setText(list.get(position).getName());
        holder.valueView.setText(list.get(position).getValue());
    }

    /**
     * 判断list是否为null,可以避免空指针异常的影响,同时也导致如果发生了空指针异常,无法及时获知.
     *
     * @return list为null返回0, 否则返回list大小.
     */
    @Override
    public int getItemCount() {
        return this.list == null ? 0 : this.list.size();
    }

    public void setData(List<Element> list) {
        this.list = list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        TextView valueView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.nameView = itemView.findViewById(R.id.element_name);
            this.valueView = itemView.findViewById(R.id.element_value);
        }
    }
}
