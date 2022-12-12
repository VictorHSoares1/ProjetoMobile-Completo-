package br.edu.ifsp.arq.ads.dmos5.easycooking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.arq.ads.dmos5.easycooking.model.Recipe;
import br.edu.ifsp.arq.ads.dmos5.easycooking.model.User;
import br.edu.ifsp.arq.ads.dmos5.easycooking.viewmodel.RecipeViewModel;
import br.edu.ifsp.arq.ads.dmos5.easycooking.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView txtTitle;
    private TextView txtLogin;
    private ImageView imageProfile;
    private TextView txtRecipeType;
    private ListView lastActivitiesList;

    private RecipeViewModel recipeViewModel;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        setToolBar();
        setDrawerLayout();
        setNavigationView();
        setComponents();
        setImageProfile();
    }

    private void setImageProfile() {
        imageProfile = navigationView.getHeaderView(0)
                .findViewById(R.id.header_profile_image);
    }

    private void setComponents() {
        txtRecipeType = findViewById(R.id.txt_activity_type);
        txtLogin = navigationView.getHeaderView(0)
                .findViewById(R.id.header_profile_name);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        UserLoginActivity.class);
                startActivity(intent);
            }
        });
    }


    private void setNavigationView() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()){
                    case R.id.nav_home:
                        intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_account:
                        intent = new Intent(MainActivity.this,
                                UserProfileActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_my_recipes:
                        intent = new Intent(MainActivity.this, MyRecipesListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_menu_timer:
                        intent = new Intent(MainActivity.this, TimerActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_settings:
                        intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_logout:
                        userViewModel.logout();
                        txtLogin.setText(R.string.txt_enter);
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void setDrawerLayout() {
        drawerLayout = findViewById(R.id.nav_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.toggle_open,
                R.string.toggle_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setToolBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        txtTitle = findViewById(R.id.toolbar_title);
        txtTitle.setText(getString(R.string.app_name));
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private void loadUserLogged(){
        userViewModel.isLogged().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user != null){
                    txtLogin.setText(user.getName()
                            + " " + user.getSurname() );
                    String image = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString(MediaStore.EXTRA_OUTPUT, null);
                    if(image != null){
                        imageProfile.setImageURI(Uri.parse(image));
                    }else{
                        imageProfile.setImageResource(R.drawable.ic_menu_account);
                    }
                    recipeViewModel.allRecipes(user.getId()).observe(MainActivity.this, new Observer<List<Recipe>>() {
                        @Override
                        public void onChanged(List<Recipe> activities) {
                            if(activities != null){
                                lastActivitiesList = findViewById(R.id.list_last_activities);

                                ArrayAdapter<Recipe> adapter = new ArrayAdapter<Recipe>(
                                        MainActivity.this,
                                        android.R.layout.simple_list_item_1,
                                        activities.subList(0, Math.min(activities.size(), 5))
                                );
                                lastActivitiesList.setAdapter(adapter);


                            } else{
                                lastActivitiesList = findViewById(R.id.list_last_activities);
                                ArrayAdapter<Recipe> adapter = new ArrayAdapter<Recipe>(
                                        MainActivity.this,
                                        android.R.layout.simple_list_item_1,
                                        new ArrayList<Recipe>()
                                );
                                lastActivitiesList.setAdapter(adapter);

                            }
                        }
                    });
                }else{
                    lastActivitiesList = findViewById(R.id.list_last_activities);
                    ArrayAdapter<Recipe> adapter = new ArrayAdapter<Recipe>(
                            MainActivity.this,
                            android.R.layout.simple_list_item_1,
                            new ArrayList<Recipe>()
                    );
                    lastActivitiesList.setAdapter(adapter);


                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserLogged();
    }
}






