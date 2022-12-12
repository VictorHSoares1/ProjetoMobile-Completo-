package br.edu.ifsp.arq.ads.dmos5.easycooking.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import br.edu.ifsp.arq.ads.dmos5.easycooking.model.Recipe;
import br.edu.ifsp.arq.ads.dmos5.easycooking.repository.RecipesRepository;

public class RecipeViewModel extends AndroidViewModel {

    private RecipesRepository recipesRepository;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        recipesRepository = new RecipesRepository(application);
    }

    public void createActivity(Recipe recipe){
        recipesRepository.insert(recipe);
    }

    public void updateActivity(Recipe recipe){
        recipesRepository.update(recipe);
    }

    public void deleteActivity(Recipe recipe){
        recipesRepository.delete(recipe);
    }

    public LiveData<List<Recipe>> allRecipes(String userId){
        return recipesRepository.getAllRecipes(userId);
    }


}
