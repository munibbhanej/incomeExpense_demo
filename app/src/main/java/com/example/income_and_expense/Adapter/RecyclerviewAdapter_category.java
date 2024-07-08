package com.example.income_and_expense.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.income_and_expense.Model.Category_model;
import com.example.income_and_expense.R;

import java.util.List;

public class RecyclerviewAdapter_category extends RecyclerView.Adapter<RecyclerviewAdapter_category.ViewHolder> {

    private Context context;
    private List<Category_model> list;

    private OnItemClickListener onItemClickListener;

    public RecyclerviewAdapter_category(Context context, List<Category_model> list, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    // set_category
    public void set_category(List<Category_model> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    //Onclick
    public interface OnItemClickListener {
        void onItemClick(Category_model category);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category_model model = list.get(position);
        holder.category_txt.setText(model.getName());
        holder.category_img.setImageResource(model.getImg());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(model);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView category_img;
        TextView category_txt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category_img = itemView.findViewById(R.id.category_item_img);
            category_txt = itemView.findViewById(R.id.category_item_txt);

        }
    }
}
