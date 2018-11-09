package com.mteam.multichoicequiz.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mteam.multichoicequiz.R;
import com.mteam.multichoicequiz.model.Category;

public class AdapterCategory extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnClick onClick;

    public AdapterCategory(OnClick onClick){
        this.onClick=onClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder viewHolder1 = (ViewHolder) viewHolder;

        if (viewHolder instanceof ViewHolder) {
            Category category = onClick.getData(i);
            ((ViewHolder) viewHolder).tvName.setText(category.getName());

            viewHolder.itemView.setOnClickListener(v -> {
                onClick.onClick(i);
            });
        }


    }

    @Override
    public int getItemCount() {
        return onClick.count();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(v -> {

            });
        }
    }

    public interface OnClick {
        int count();

        void onClick(int position);

        Category getData(int position);
    }
}
