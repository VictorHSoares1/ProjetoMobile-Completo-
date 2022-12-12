package br.edu.ifsp.arq.ads.dmos5.easycooking.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.arq.ads.dmos5.easycooking.model.Recipe;

public class RecipesRepository {

    private FirebaseFirestore firestore;

    public RecipesRepository(Application application) {
        firestore = FirebaseFirestore.getInstance();
    }

    public void insert(Recipe recipe){
        firestore.collection("recipe").add(recipe)
                .addOnSuccessListener(unused ->{
            Log.d(this.toString(), "Receita cadastrada com sucesso.");
        });
    }

    public void update(Recipe recipe){
        firestore.collection("recipe").document(recipe.getId())
                .set(recipe).addOnSuccessListener(unused -> {
                    Log.d(this.toString(), "Receita atualizada com sucesso.");
        });
    }

    public void delete(Recipe recipe){
        firestore.collection("recipe").document(recipe.getId())
                .delete().addOnSuccessListener(unused -> {
                    Log.d(this.toString(), "Receita removida com sucesso.");
        });
    }

    public LiveData<List<Recipe>> getAllRecipes(String userId) {
        MutableLiveData<List<Recipe>> liveData = new MutableLiveData<>();
        List<Recipe> recipes = new ArrayList<>();
        firestore.collection("recipe").whereEqualTo("userID", userId).orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot doc: task.getResult()){
                        Recipe recipe = doc.toObject(Recipe.class);
                        recipe.setId(doc.getId());
                        recipes.add(recipe);
                    }
                }
                liveData.setValue(recipes);
            }
        });

        return liveData;
    }
}
