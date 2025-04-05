package com.example.oop_project.DataProvider;

import com.example.oop_project.model.Lutemon;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class DataProvider {
    // 1. Make it singleton
    private static DataProvider instance;

    // 2. Make list private and unmodifiable
    private final List<Lutemon> lutemons = new ArrayList<>();

    // 3. Private constructor with initial data
    private DataProvider() {
        initializeDefaultData();
    }

    public static synchronized DataProvider getInstance() {
        if (instance == null) {
            instance = new DataProvider();
        }
        return instance;
    }

    private void initializeDefaultData() {
        //lutemons.add(new Lutemon("Lutemon1", "White", 5, 4, 0, 20));
        //lutemons.add(new Lutemon("lutemon2", "Green", 7, 2, 0, 19));
        //lutemons.add(new Lutemon("Lutemon3", "Pink", 6, 7, 0, 21));
        //lutemons.add(new Lutemon("Lutemon4", "Orange", 7, 8, 0, 19));
        //lutemons.add(new Lutemon("Lutemon5", "Black", 8, 9, 0, 23));
    }

    // 4. Return unmodifiable list
    public List<Lutemon> getLutemonData() {
        return Collections.unmodifiableList(lutemons);
    }

    // 5. Safe add method
    public void addNewLutemon(Lutemon newLutemon) {
        if(newLutemon != null) {
            lutemons.add(newLutemon);
        }
    }

    // Optional: Get copy of the list
    public List<Lutemon> getLutemonCopy() {
        return new ArrayList<>(lutemons);
    }
}