package com.leonyr.easyutils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * ==============================================================
 * Description:
 * <p>
 * Created by leonyr on 2019.04.15
 * (C) Copyright LeonyR Corporation 2018 All Rights Reserved.
 * ==============================================================
 */
public class MAdapter extends RecyclerView.Adapter<MAdapter.MViewHolder> {

    List<String> datas;

    public MAdapter() {
        this(null);
    }

    public MAdapter(List<String> datas) {
        this.datas = datas;
    }

    public void addItem(String s){
        if (null == datas){
            datas = new ArrayList<>();
        }
        datas.add(0, s);
        notifyItemInserted(0);
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        return new MViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MViewHolder holder, int position) {
        holder.content.setText(datas.get(position));
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datas.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == datas ? 0 : datas.size();
    }

    static class MViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        ImageView btnDel;

        MViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.item_content);
            btnDel = itemView.findViewById(R.id.item_del);
        }
    }
}
