package com.example.oop_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.oop_project.R;
import com.example.oop_project.model.Lutemon;

import java.util.List;

public class LutemonAdapter extends RecyclerView.Adapter<LutemonAdapter.ViewHolder> {
    private final List<Lutemon> lutemonList;
    private final Context context;
    public LutemonAdapter(Context context, List<Lutemon> lutemonList) {
        this.context = context;
        this.lutemonList = lutemonList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lutemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lutemon lutemonElement = lutemonList.get(position);
        holder.tvLutemonName.setText(lutemonElement.getName());
        holder.tvLutemonHealth.setText(lutemonElement.getHealth());
        holder.tvLutemonExp.setText(lutemonElement.getExperience());
        holder.tvLutemonColor.setText(lutemonElement.getColor());
        holder.tvLutemonAttack.setText(lutemonElement.getAttack());
        holder.tvLutemonDefense.setText(lutemonElement.getDefense());
        Glide.with(context).load(lutemonElement.getPicURL()).into(holder.lutemonPic);

    }

    @Override
    public int getItemCount() {
        return lutemonList.size();
    }
    public void updateLutemon(List<Lutemon> newLutemon) { // Use List<Lutemon>
        lutemonList.clear();
        lutemonList.addAll(newLutemon);
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLutemonName, tvLutemonColor, tvLutemonAttack, tvLutemonDefense, tvLutemonHealth, tvLutemonExp;
        ImageView lutemonPic;
        ViewHolder(@NonNull View itemview) {
            super(itemview);
            tvLutemonAttack = itemview.findViewById(R.id.tv_lutemonAttack);
            tvLutemonColor = itemview.findViewById(R.id.tv_LutemonColor);
            tvLutemonDefense = itemview.findViewById(R.id.tv_lutemonDefense);
            tvLutemonName = itemview.findViewById(R.id.tv_lutemonName);
            tvLutemonExp = itemview.findViewById(R.id.tv_Lutemonexp);
            tvLutemonHealth = itemview.findViewById(R.id.tv_LutemonHealth);
            lutemonPic = itemview.findViewById(R.id.IW_lutemonPic);
        }
    }
}
