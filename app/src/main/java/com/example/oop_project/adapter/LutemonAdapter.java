package com.example.oop_project.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.oop_project.R;
import com.example.oop_project.model.Lutemon;

import java.util.ArrayList;
import java.util.List;

public class LutemonAdapter extends RecyclerView.Adapter<LutemonAdapter.ViewHolder> {
    private final List<Lutemon> lutemonList;
    private final Context context;
    private final SparseBooleanArray selectedItems = new SparseBooleanArray();
    private OnItemClickListener clickListener;
    private static final int MAX_SELECTIONS = 2;

    // Correct interface definition
    public interface OnItemClickListener {
        void onItemClick(List<Lutemon> selected);
    }

    public LutemonAdapter(Context context, List<Lutemon> lutemonList) {
        this.context = context;
        this.lutemonList = lutemonList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;  // Removed incorrect cast
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

        // Convert numbers to String
        holder.tvLutemonName.setText(lutemonElement.getName());
        holder.tvLutemonHealth.setText(String.valueOf(lutemonElement.getHealth()));
        holder.tvLutemonExp.setText(String.valueOf(lutemonElement.getExperience()));
        holder.tvLutemonColor.setText(lutemonElement.getColor());
        holder.tvLutemonAttack.setText(String.valueOf(lutemonElement.getAttack()));
        holder.tvLutemonDefense.setText(String.valueOf(lutemonElement.getDefense()));

        Glide.with(context).load(lutemonElement.getPicURL()).into(holder.lutemonPic);

        // Selection highlight
        holder.itemView.setBackgroundColor(
                selectedItems.get(position) ? Color.LTGRAY : Color.TRANSPARENT
        );

        holder.itemView.setOnClickListener(v -> {
            toggleSelection(position);
            notifyItemChanged(position);
            if (clickListener != null) {
                clickListener.onItemClick(getSelectedLutemons());
            }
        });
    }
    private void toggleSelection(int position) {
        if (selectedItems.get(position)) {
            selectedItems.delete(position);
        } else {
            if (selectedItems.size() < MAX_SELECTIONS) {
                selectedItems.put(position, true);
            } else {
                Toast.makeText(context, "Maximum 2 selections allowed", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public List<Lutemon> getSelectedLutemons() {
        List<Lutemon> selected = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            int pos = selectedItems.keyAt(i);
            selected.add(lutemonList.get(pos));
        }
        return selected;
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
