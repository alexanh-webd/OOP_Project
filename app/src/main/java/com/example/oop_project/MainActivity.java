package com.example.oop_project;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerInventory;
    private TextView tvEmptyView;
    private LutemonAdapter adapter;
    private DataContainer<Lutemon> lutemonDataContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        recyclerInventory = findViewById(R.id.recycler_main);
        recyclerInventory.setLayoutManager(new LinearLayoutManager(this));
        tvEmptyView = findViewById(R.id.tv_empty_view);
        // Load data from the datacontainer
        lutemonDataContainer = new DataContainer<>();

        // Load sample datas
        List<Lutemon> lutemonList = DataProvider.getInstance().getLutemonData();
        for (Lutemon lutemon : lutemonList) {
            lutemonDataContainer.addData(lutemon);
        }
        // set up recycler view
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
        recyclerInventory.setAdapter(adapter);
    }
    private void setupButtonListeners() {
        Button btnViewHome = findViewById(R.id.btn_arenaToHome);
        btnViewHome.setOnClickListener(v -> {
            recyclerInventory.setAdapter(adapter);
            refreshData();
        });
        Button btnCreateNewLutemon = findViewById(R.id.btn_CreateNewLtm);
        btnCreateNewLutemon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, cr_new_ltmActivity.class);
            startActivity(intent);
        });
        Button btnMoveTotraining = findViewById(R.id.btn_MoveToTraining);
        btnMoveTotraining.setOnClickListener(v->{
            List<Lutemon> selected = adapter.getSelectedLutemons();
            if (selected.size() == 1) {
                Intent intent1 = new Intent(this, Training_lutemon.class);
                intent1.putParcelableArrayListExtra("selected", new ArrayList<>(selected));
                startActivity(intent1);
            }
        });
        Button btnMovetoBattle = findViewById(R.id.btn_moveToBattle);
        btnMovetoBattle.setOnClickListener(v->{
            List<Lutemon> selected = adapter.getSelectedLutemons();
            if (selected.size() == 2) {
                Intent intent2 = new Intent(this, Arena.class);
                intent2.putParcelableArrayListExtra("selected", new ArrayList<>(selected));
                startActivity(intent2);
            }
        });
    }
    private void updateAdapterLutemon(List<Lutemon> lutemonList) {
        adapter.updateLutemon(lutemonList);

        // Show/hide empty view
        if (lutemonList.isEmpty()) {
            tvEmptyView.setVisibility(android.view.View.VISIBLE);
            recyclerInventory.setVisibility(android.view.View.GONE);
        } else {
            tvEmptyView.setVisibility(android.view.View.GONE);
            recyclerInventory.setVisibility(android.view.View.VISIBLE);
        }
    }
    private void refreshData() {
        // Get fresh data from DataProvider
        List<Lutemon> currentData = DataProvider.getInstance().getLutemonData();
        updateAdapterLutemon(currentData);
    }
    public void onItemSelected(List<Lutemon> selected) {
        // Update UI based on selection count
        Button btnStart = findViewById(R.id.btn_MoveToTraining);
        btnStart.setEnabled(selected.size() == 2);
    }
}