package com.android.weatherjecpack.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.weatherjecpack.databinding.ItemAreaBinding;

import java.util.List;

public class AreaListAdapter extends RecyclerView.Adapter<AreaListAdapter.AreaListViewHolder> {

    private List<String> mList;
    private OnAreaListener listener;

    public class AreaListViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public AreaListViewHolder(ItemAreaBinding binding){
            super(binding.getRoot());
            textView = binding.areaItemText;
            textView.setOnClickListener(this::onClick);
        }

        public void onClick(View view){
            listener.onItemClick(mList.get(getAdapterPosition()));
        }

    }

    public AreaListAdapter(List<String> list, OnAreaListener clickListener){
        mList = list;
        listener = clickListener;
    }

    public void setList(List<String> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public AreaListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAreaBinding binding = ItemAreaBinding
                .inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new AreaListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AreaListViewHolder holder, int position) {
        holder.textView.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnAreaListener {
        void onItemClick(String areaName);
    }
}
