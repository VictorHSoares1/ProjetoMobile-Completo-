package br.edu.ifsp.arq.ads.dmos5.easycooking.viewmodel;

import android.app.Application;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Optional;

import br.edu.ifsp.arq.ads.dmos5.easycooking.model.User;
import br.edu.ifsp.arq.ads.dmos5.easycooking.model.UserWithRecipes;
import br.edu.ifsp.arq.ads.dmos5.easycooking.repository.UsersRepository;

public class UserViewModel extends AndroidViewModel {

    public static final String USER_ID = "USER_ID";

    private UsersRepository usersRepository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        this.usersRepository = new UsersRepository(application);
    }

    public void createUser(User user){
        usersRepository.createUser(user);
    }

    public void updateUser(User user){
        usersRepository.update(user);
    }

    public LiveData<User> login(String email, String password){
        return usersRepository.login(email, password);
    }

    public void logout(){
        PreferenceManager.getDefaultSharedPreferences(getApplication())
                .edit()
                .remove(USER_ID)
                .apply();
    }

    public LiveData<User> isLogged(){
        Optional<String> id = Optional.ofNullable(
                PreferenceManager.getDefaultSharedPreferences(getApplication())
                    .getString(USER_ID, null)
        );
        if(!id.isPresent()){
            return new MutableLiveData<>(null);
        }
        return usersRepository.loadUser(id.get());
    }

    public void resetPassword(String email){
        usersRepository.resetPassword(email);
    }

    public LiveData<List<UserWithRecipes>> getAllUsersWithRecipes() {
        return usersRepository.getAllUsersWithRecipes();
    }

}
