package com.example.oop_project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oop_project.DataProvider.DataProvider;
import com.example.oop_project.adapter.LutemonAdapter;
import com.example.oop_project.container.DataContainer;
import com.example.oop_project.model.Lutemon;

import java.util.ArrayList;
import java.util.List;

public class Training_lutemon extends AppCompatActivity {
    private LutemonAdapter adapter;
    private TextView tvEmptyView;
    private RecyclerView recyclerTraining;
    private DataContainer<Lutemon> selectedContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_training_lutemon);
        recyclerTraining = findViewById(R.id.recycler_training);
        recyclerTraining.setLayoutManager(new LinearLayoutManager(this));
        tvEmptyView = findViewById(R.id.tv_empty_view);
        selectedContainer = new DataContainer<>();
        // Initialize adapter
        List<Lutemon> lutemonList = DataProvider.getInstance().getLutemonData();
        List<Lutemon> selectedLutemons = getIntent().getParcelableArrayListExtra("selected");
        List<Lutemon> fullInforSelectedLutemon = new ArrayList<>();
        for (Lutemon selectedLtm : selectedLutemons) {
            for (Lutemon lutemon : lutemonList) {
                if (selectedLtm.getName().equals(lutemon.getName())) {
                    fullInforSelectedLutemon.add(lutemon);
                }
            }
        }
        System.out.println(fullInforSelectedLutemon);
        for (Lutemon seLTM : fullInforSelectedLutemon) {
            selectedContainer.addData(seLTM);
        }
        setupRecyclerView();
        setupButtonListeners();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void setupRecyclerView() {
        adapter = new LutemonAdapter(this, new ArrayList<>());
        recyclerTraining.setAdapter(adapter);
    }
    private void setupButtonListeners() {
        Button btnTrain = findViewById(R.id.btn_training);
        btnTrain.setOnClickListener(v->{
            recyclerTraining.setAdapter(adapter);
            refreshData();
        });
    }
    private void updateAdapterLutemon(List<Lutemon> lutemonList) {
        adapter.updateLutemon(lutemonList);

        // Show/hide empty view
        if (lutemonList.isEmpty()) {
            tvEmptyView.setVisibility(android.view.View.VISIBLE);
            recyclerTraining.setVisibility(android.view.View.GONE);
        } else {
            tvEmptyView.setVisibility(android.view.View.GONE);
            recyclerTraining.setVisibility(android.view.View.VISIBLE);
        }
    }
    private void refreshData() {
        // Get fresh data from DataProvider
        List<Lutemon> lutemonList = DataProvider.getInstance().getLutemonData();
        List<Lutemon> currentData = getIntent().getParcelableArrayListExtra("selected");
        List<Lutemon> updatedData = new ArrayList<>();
        for (Lutemon currentltm : currentData) {
            currentltm.setAttack(Integer.parseInt(currentltm.getAttack()) + 2);
            currentltm.setDefense(Integer.parseInt(currentltm.getDefense()) + 3);
            currentltm.setExperience(Integer.parseInt(currentltm.getExperience()) + 1);
            currentltm.setHealth(Integer.parseInt(currentltm.getExperience()) + 4);
            updatedData.add(currentltm);
        }
        for (Lutemon updateddataLTM : lutemonList) {
            for (Lutemon lutemonInUpdatedData : updatedData) {
                if (updateddataLTM.getName().equals(lutemonInUpdatedData.getName())) {
                    updateddataLTM.setAttack(Integer.parseInt(lutemonInUpdatedData.getAttack()));
                    updateddataLTM.setDefense(Integer.parseInt(lutemonInUpdatedData.getDefense()));
                    updateddataLTM.setHealth(Integer.parseInt(lutemonInUpdatedData.getHealth()));
                    updateddataLTM.setExperience(Integer.parseInt(lutemonInUpdatedData.getExperience()));
                }
            }
        }
        updateAdapterLutemon(updatedData);
    }
}