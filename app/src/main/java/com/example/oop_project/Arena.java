package com.example.oop_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Arena extends AppCompatActivity {
    private RecyclerView recyclerArena;
    private TextView tvEmptyView;
    private LutemonAdapter adapter;
    private DataContainer<Lutemon> arenaContainer;
    private List<Lutemon> fullInforSelectedLutemon;
    private int attackTurn1;
    private int attackTurn2;
    private int getRemainingHealth1;
    private int getRemainingHealth2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_arena);
        recyclerArena = findViewById(R.id.recycler_arena);
        recyclerArena.setLayoutManager(new LinearLayoutManager(this));
        tvEmptyView = findViewById(R.id.tv_empty_view);
        arenaContainer = new DataContainer<>();

        List<Lutemon> lutemonList = DataProvider.getInstance().getLutemonData();
        List<Lutemon> selectedLutemons = getIntent().getParcelableArrayListExtra("selected");
        fullInforSelectedLutemon = new ArrayList<>();
        for (Lutemon selectedLtm : selectedLutemons) {
            for (Lutemon lutemon : lutemonList) {
                if (selectedLtm.getName().equals(lutemon.getName())) {
                    fullInforSelectedLutemon.add(lutemon);
                }
            }
        }
        System.out.println(fullInforSelectedLutemon);
        for (Lutemon seLTM : fullInforSelectedLutemon) {
            arenaContainer.addData(seLTM);
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
        recyclerArena.setAdapter(adapter);
    }
    private void setupButtonListeners() {
        Button btnBattle = findViewById(R.id.btn_battle);
        btnBattle.setOnClickListener(v -> {
            recyclerArena.setAdapter(adapter);
            //refreshData();
            updateAdapterLutemon(fullInforSelectedLutemon);
        });
        Button btn_moveHome = findViewById(R.id.btn_arenaToHome);
        btn_moveHome.setOnClickListener(v->{
            Intent intent1 = new Intent(Arena.this, MainActivity.class);
            startActivity(intent1);
        });
        Button btn_attack = findViewById(R.id.btn_nextAttack);
        attackTurn1 = 1;
        attackTurn2 = 0;
        Lutemon lutemon1 = fullInforSelectedLutemon.get(0);
        Lutemon lutemon2 = fullInforSelectedLutemon.get(1);
        int attack1 = Integer.parseInt(lutemon1.getAttack());
        int def1 = Integer.parseInt(lutemon1.getDefense());
        int attack2 = Integer.parseInt(lutemon2.getAttack());
        int def2 = Integer.parseInt(lutemon2.getDefense());
        getRemainingHealth1 = Integer.parseInt(lutemon1.getHealth());
        getRemainingHealth2 = Integer.parseInt(lutemon2.getHealth());
        int initailhealth1 = Integer.parseInt(lutemon1.getHealth());
        int initalhealth2 = Integer.parseInt(lutemon2.getHealth());
        System.out.println(getRemainingHealth1);
        System.out.println(getRemainingHealth2);

        btn_attack.setOnClickListener(v -> {
            Random rand = new Random();
            if (attackTurn1 > attackTurn2) {
                Toast.makeText(Arena.this,lutemon1.getName() + " turns to attack " + lutemon2.getName(), Toast.LENGTH_SHORT).show();
                getRemainingHealth2 = getRemainingHealth2 - attack1 + def2;
                lutemon2.setHealth(getRemainingHealth2);
                System.out.println(lutemon2.getHealth());
                if (getRemainingHealth2 >= 0) {
                    lutemon2.setHealth(getRemainingHealth2);
                    Toast.makeText(Arena.this,lutemon2.getName() + " still alive after being attacked by " + lutemon1.getName(), Toast.LENGTH_SHORT).show();
                } else {
                    lutemon2.setHealth(0);
                    Toast.makeText(Arena.this,lutemon2.getName() + " is dead after being attacked by " + lutemon1.getName(), Toast.LENGTH_SHORT).show();
                }
                recyclerArena.setAdapter(adapter);
                //refreshData(updatedHealth);
                attackTurn1 = 0;
                attackTurn2 = 1;
            } else if (attackTurn1 < attackTurn2) {
                Toast.makeText(Arena.this,lutemon2.getName() + " turns to attack " + lutemon1.getName(), Toast.LENGTH_SHORT).show();
                getRemainingHealth1 = getRemainingHealth1 - attack2 + def1;
                if (getRemainingHealth1 >= 0) {
                    lutemon1.setHealth(getRemainingHealth1);
                    Toast.makeText(Arena.this,lutemon1.getName() + " still alive after being attacked by " + lutemon2.getName(), Toast.LENGTH_SHORT).show();
                } else {
                    lutemon1.setHealth(0);
                    Toast.makeText(Arena.this,lutemon1.getName() + " is dead after being attacked by " + lutemon2.getName(), Toast.LENGTH_SHORT).show();
                }
                //updatedHealth.add(lutemon1);
                //updatedHealth.add(lutemon2);
                recyclerArena.setAdapter(adapter);
                //refreshData(updatedHealth);
                attackTurn1 = 1;
                attackTurn2 = 0;
            }
            if (Integer.parseInt(lutemon1.getHealth()) == 0) {
                lutemon1.setHealth(initailhealth1);
                lutemon2.setHealth(initalhealth2);
                //lutemon1.setExperience(Integer.parseInt(lutemon1.getExperience()));
                lutemon2.setExperience(Integer.parseInt(lutemon2.getExperience()) + 1);
                Toast.makeText(Arena.this,lutemon2.getName() + " beats " + lutemon1.getName(), Toast.LENGTH_SHORT).show();
                finish();
            } else if (Integer.parseInt(lutemon2.getHealth()) == 0) {
                lutemon1.setHealth(initailhealth1);
                lutemon2.setHealth(initalhealth2);
                lutemon1.setExperience(Integer.parseInt(lutemon1.getExperience()) + 1);
                Toast.makeText(Arena.this,lutemon1.getName() + " beats " + lutemon2.getName(), Toast.LENGTH_SHORT).show();
                finish();
            }
            //System.out.println(updatedHealth);
        });
    }
    private void updateAdapterLutemon(List<Lutemon> lutemonList) {
        adapter.updateLutemon(lutemonList);

        // Show/hide empty view
        if (lutemonList.isEmpty()) {
            tvEmptyView.setVisibility(android.view.View.VISIBLE);
            recyclerArena.setVisibility(android.view.View.GONE);
        } else {
            tvEmptyView.setVisibility(android.view.View.GONE);
            recyclerArena.setVisibility(android.view.View.VISIBLE);
        }
    }
    private void refreshData(List<Lutemon> datain) {
        // Get fresh data from DataProvider
        List<Lutemon> lutemonList = DataProvider.getInstance().getLutemonData();
        List<Lutemon> currentData = getIntent().getParcelableArrayListExtra("selected");
        //List<Lutemon> currentData = datain;
        List<Lutemon> updatedData = datain;
        for (Lutemon currentltm : currentData) {
            //currentltm.setAttack(Integer.parseInt(currentltm.getAttack()) + 2);
            //currentltm.setDefense(Integer.parseInt(currentltm.getDefense()) + 3);
            //currentltm.setExperience(Integer.parseInt(currentltm.getExperience()) + 1);
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