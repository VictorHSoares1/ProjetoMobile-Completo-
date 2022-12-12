package br.edu.ifsp.arq.ads.dmos5.easycooking.model;

import java.io.Serializable;
import java.util.List;

public class UserWithRecipes implements Serializable {

    private User user;
    private List<Recipe> recipes;

    public UserWithRecipes(User user, List<Recipe> recipes) {
        this.user = user;
        this.recipes = recipes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

}
