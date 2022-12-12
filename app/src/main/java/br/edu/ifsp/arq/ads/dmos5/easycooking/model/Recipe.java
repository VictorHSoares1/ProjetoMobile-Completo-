package br.edu.ifsp.arq.ads.dmos5.easycooking.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "recipe")
public class Recipe implements Serializable {

    @NonNull
    @PrimaryKey
    private String id;
    private String userID;
    private RecipeType type;
    private String recipeName;
    private String details;
    private String date;

    public Recipe(String userID, RecipeType type, String recipeName, String details, String date) {
        this.userID = userID;
        this.type = type;
        this.recipeName = recipeName;
        this.details = details;
        this.date = date;
    }


    public Recipe() {
        this("", RecipeType.SALGADAS, "", "", "");
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public RecipeType getType() {
        return type;
    }

    public void setType(RecipeType type) {
        this.type = type;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id.equals(recipe.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString(){
        return String.format("\n%s - %s\nNome: %s\nDetalhes: \n%s\n",
                this.getType(),
                this.getDate(),
                this.getRecipeName(),
                this.getDetails());
    }
}
