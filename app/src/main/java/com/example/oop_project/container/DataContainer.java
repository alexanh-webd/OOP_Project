package com.example.oop_project.container;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DataContainer<T> {
    private final List<T> data; // Use List instead of ArrayList

    public DataContainer() {
        this.data = new ArrayList<>();
    }

    public void addData(T element) {
        data.add(element);
    }

    public List<T> getAllItems() { // Return List instead of ArrayList
        return new ArrayList<>(data);
    }
    public DataContainer<T> filter(Predicate<T> predicate) {
        DataContainer<T> filteredContainer = new DataContainer<>();

        // Using stream and lambda to filter items
        List<T> filteredItems = data.stream()
                .filter(predicate) //filter by condition
                .collect(Collectors.toList());

        // Add filtered items to the new container
        filteredItems.forEach(filteredContainer::addData);

        return filteredContainer;
    }
}
