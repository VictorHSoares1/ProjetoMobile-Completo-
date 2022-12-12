package br.edu.ifsp.arq.ads.dmos5.easycooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.edu.ifsp.arq.ads.dmos5.easycooking.adapter.RecipeAdapter;
import br.edu.ifsp.arq.ads.dmos5.easycooking.model.Recipe;
import br.edu.ifsp.arq.ads.dmos5.easycooking.model.User;
import br.edu.ifsp.arq.ads.dmos5.easycooking.viewmodel.RecipeViewModel;
import br.edu.ifsp.arq.ads.dmos5.easycooking.viewmodel.UserViewModel;

public class MyRecipesListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitulo;

    private FloatingActionButton btnNewRecipe;

    private ListView recipesList;
    private RecipeAdapter adapter;

    UserViewModel userViewModel;
    RecipeViewModel recipeViewModel;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipes_list);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        setToolBar();
        setBtnNewRecipe();
    }

    private void setAdapter() {
        recipeViewModel.allRecipes(user.getId()).observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> activities) {
                adapter = new RecipeAdapter(MyRecipesListActivity.this, android.R.layout.simple_list_item_1, activities);
                recipesList = (ListView) findViewById(R.id.activity_list);
                recipesList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void deleteRecipe(int pos){
        recipeViewModel.allRecipes(user.getId()).observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                recipeViewModel.deleteActivity(recipes.get(pos));
                setAdapter();
                Toast.makeText(
                        MyRecipesListActivity.this,"Receita removida com sucesso!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadUserLogged() {
        userViewModel.isLogged().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user == null) {
                    startActivity(new Intent(MyRecipesListActivity.this, UserLoginActivity.class));
                    finish();
                }else{
                    MyRecipesListActivity.this.user = user;
                    setAdapter();
                }
            }
        });
    }

    private void setBtnNewRecipe() {
        btnNewRecipe = findViewById(R.id.btn_add_recipe);
        btnNewRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyRecipesListActivity.this, RecipeRegisterActivity.class);
                intent.putExtra("recipe", new Recipe());
                startActivity(intent);
            }
        });
    }

    private void setToolBar() {
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.toolbar_title);
        txtTitulo.setText(R.string.title_my_recipes);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserLogged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}