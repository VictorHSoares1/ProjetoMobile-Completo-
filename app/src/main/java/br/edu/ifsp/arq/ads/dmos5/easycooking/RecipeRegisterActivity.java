package br.edu.ifsp.arq.ads.dmos5.easycooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import br.edu.ifsp.arq.ads.dmos5.easycooking.model.Recipe;
import br.edu.ifsp.arq.ads.dmos5.easycooking.model.RecipeType;
import br.edu.ifsp.arq.ads.dmos5.easycooking.model.User;
import br.edu.ifsp.arq.ads.dmos5.easycooking.viewmodel.RecipeViewModel;
import br.edu.ifsp.arq.ads.dmos5.easycooking.viewmodel.UserViewModel;


public class RecipeRegisterActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitle;
    private TextInputEditText txtDate;
    private TextInputEditText txtNomeReceita;
    private TextInputEditText txtDetalhesReceita;
    private Spinner spnActivityType;
    private Button btnSave;

    private UserViewModel userViewModel;
    private RecipeViewModel recipeViewModel;

    private User user;

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_register);
        recipe = (Recipe) getIntent().getExtras().getSerializable("recipe");
        setComponents();
        if(recipe.getId() != null){
            fillFields();
        }
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        setBtnSave();
    }

    private void fillFields() {
        RecipeType[] recipeTypes = RecipeType.values();
        for(int i = 0; i < recipeTypes.length; i++){
            if(recipeTypes[i].equals(recipe.getType())){
                spnActivityType.setSelection(i);
            }
        }
        txtDate.setText(recipe.getDate());
        txtNomeReceita.setText(String.valueOf(recipe.getRecipeName()));
        txtDetalhesReceita.setText(String.valueOf(recipe.getDetails()));
    }

    private void setComponents() {
        spnActivityType = findViewById(R.id.sp_actvity);
        txtDate = findViewById(R.id.txt_edt_date);
        txtNomeReceita = findViewById(R.id.txt_edt_recipe_name);
        txtDetalhesReceita = findViewById(R.id.txt_edt_details);
        btnSave = findViewById(R.id.btn_activity_register);
    }

    private void setBtnSave() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recipe.getId() == null){
                    addRecipe();
                }else{
                    updateRecipe();
                }

            }
        });
    }

    private void updateRecipe() {
        if(validate()){
            recipe.setType(RecipeType.values()[spnActivityType.getSelectedItemPosition()]);
            recipe.setDate(txtDate.getText().toString());
            recipe.setRecipeName(txtNomeReceita.getText().toString());
            recipe.setDetails(txtDetalhesReceita.getText().toString());
            recipeViewModel.updateActivity(recipe);
            Toast.makeText(RecipeRegisterActivity.this,
                    "Receita atualizada com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void addRecipe() {
        if(validate()){
            Recipe activity = new Recipe(
                    user.getId(),
                    RecipeType.values()[spnActivityType.getSelectedItemPosition()],
                    txtNomeReceita.getText().toString(),
                    txtDetalhesReceita.getText().toString(),
                    txtDate.getText().toString()
            );
            recipeViewModel.createActivity(activity);
            Toast.makeText(RecipeRegisterActivity.this,
                    "Receita adicionada com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean validate() {
        boolean isValid = true;
        if(txtDate.getText().toString().trim().isEmpty()){
            txtDate.setError("Preencha o campo da data");
            isValid = false;
        }else{
            txtDate.setError(null);
        }
        if(txtNomeReceita.getText().toString().trim().isEmpty()){
            txtNomeReceita.setError("Preencha o campo do nome da receita");
            isValid = false;
        }else{
            txtNomeReceita.setError(null);
        }

        if(txtDetalhesReceita.getText().toString().trim().isEmpty()){
            txtDetalhesReceita.setError("Preencha o campo dos detalhes da receita");
            isValid = false;
        }else{
            txtDetalhesReceita.setError(null);
        }
        return isValid;
    }

    private void loadUserLogged() {
        userViewModel.isLogged().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user == null){
                    startActivity(new Intent(RecipeRegisterActivity.this,
                            UserLoginActivity.class));
                    finish();
                }else{
                    RecipeRegisterActivity.this.user = user;
                }
            }
        });
    }

    private void setToolBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        txtTitle = findViewById(R.id.toolbar_title);
        if(recipe.getId() == null){
            txtTitle.setText(getString(R.string.txt_title_new_activity));
        }else{
            txtTitle.setText(getString(R.string.txt_title_edit_recipe));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolBar();
        loadUserLogged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}