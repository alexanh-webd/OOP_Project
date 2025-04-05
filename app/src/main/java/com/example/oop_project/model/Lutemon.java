package com.example.oop_project.model;

public class Lutemon {
    private String name;
    private String color;
    private int attack;
    private int defense;
    private int experience;
    private int health;
    private String picURL;

    public Lutemon(String name, String color, int attack, int defense, int experience, int health, String picURL) {
        this.name = name;
        this.color = color;
        this.attack = attack;
        this.defense = defense;
        this.experience = experience;
        this.health = health;
        this.picURL = picURL;
    }
    public String getPicURL() {
        return this.picURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAttack() {
        return Integer.toString(attack);
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public String getDefense() {
        return Integer.toString(defense);
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public String getExperience() {
        return Integer.toString(experience);
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getHealth() {
        return Integer.toString(health);
    }

    public void setHealth(int health) {
        this.health = health;
    }
    @Override
    public String toString() {
        return "Lutemon{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", attack=" + attack +
                ", defense=" + defense +
                ", experience=" + experience +
                ", health=" + health +
                '}';
    }
}
