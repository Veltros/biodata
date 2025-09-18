package com.example.biodata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BiodataAdapter extends RecyclerView.Adapter<BiodataAdapter.ViewHolder> {

    private final ArrayList<String> daftar;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String name, int position);
        void onItemLongClick(String name, int position);
    }

    public BiodataAdapter(ArrayList<String> daftar, OnItemClickListener listener) {
        this.daftar = daftar;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nama = daftar.get(position);
        holder.textView.setText(nama);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(nama, position));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onItemLongClick(nama, position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return daftar.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
