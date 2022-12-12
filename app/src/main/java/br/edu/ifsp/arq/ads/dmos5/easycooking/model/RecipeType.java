package br.edu.ifsp.arq.ads.dmos5.easycooking.model;

public enum RecipeType {

    SALGADAS (1, "Salgadas"),
    DOCES (2, "Doces"),
    BEBIDAS (3, "Bebidas");


    private int id;
    private String type;

    RecipeType(int id, String type){
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
