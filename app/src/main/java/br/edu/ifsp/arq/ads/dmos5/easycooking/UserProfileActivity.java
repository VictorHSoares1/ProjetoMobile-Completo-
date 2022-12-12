package br.edu.ifsp.arq.ads.dmos5.easycooking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.edu.ifsp.arq.ads.dmos5.easycooking.model.User;
import br.edu.ifsp.arq.ads.dmos5.easycooking.viewmodel.UserViewModel;

public class UserProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitle;
    private TextInputEditText txtName;
    private TextInputEditText txtSurname;
    private TextInputEditText txtEmail;
    private TextInputEditText txtBirthDate;
    private TextInputEditText txtPhone;
    private Spinner spnGender;
    private Button btnUpdateProfile;
    private ImageView imageProfile;

    private Uri photoURI;
    private final int REQUEST_TAKE_PHOTO = 1;

    private User user;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        setToolBar();
        setComponents();
        loadUserLogged();
        setBtnUpdateProfile();
        setImageProfile();
    }

    private void setImageProfile() {
        imageProfile = findViewById(R.id.iv_profile_image);
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });
    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            File photoFile = null;
            try{
                photoFile = createImageFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            photoURI = FileProvider.getUriForFile(this,
                    "br.edu.ifsp.arq.ads.dmos5.ifitnessapp.fileprovider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(intent, REQUEST_TAKE_PHOTO);
        }
    }

    private File createImageFile() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile("PROFILE_" + timestamp, "jpg", storageDir);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit().putString(MediaStore.EXTRA_OUTPUT, photoURI.toString())
                .apply();
        imageProfile.setImageURI(photoURI);
    }

    private void setBtnUpdateProfile() {
        btnUpdateProfile = findViewById(R.id.btn_profile_update);
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });
    }

    private void updateProfile() {
        if(!validate()){
            return;
        }
        user.setName(txtName.getText().toString());
        user.setSurname(txtSurname.getText().toString());
        user.setEmail(txtEmail.getText().toString());
        user.setBirthDate(txtBirthDate.getText().toString());
        user.setPhone(txtPhone.getText().toString());
        user.setGender(getResources()
                .getStringArray(R.array.genders)[spnGender.getSelectedItemPosition()]);
        user.setImage(null); // TODO: atualizar depois

        userViewModel.updateUser(user);

        Toast.makeText(this, R.string.msg_success_profile,
                Toast.LENGTH_SHORT).show();
    }

    private boolean validate() {
        boolean isValid = true;
        if(txtName.getText().toString().trim().isEmpty()){
            txtName.setError("Preencha o campo do nome");
            isValid = false;
        }else{
            txtName.setError(null);
        }
        if(txtSurname.getText().toString().trim().isEmpty()){
            txtSurname.setError("Preencha o campo do sobrenome");
            isValid = false;
        }else{
            txtSurname.setError(null);
        }
        if(txtEmail.getText().toString().trim().isEmpty()){
            txtEmail.setError("Preencha o campo do e-mail");
            isValid = false;
        }else{
            txtEmail.setError(null);
        }
        if(txtBirthDate.getText().toString().trim().isEmpty()){
            txtBirthDate.setError("Preencha o campo da data de nascimento");
            isValid = false;
        }else{
            txtBirthDate.setError(null);
        }
        if(txtPhone.getText().toString().trim().isEmpty()){
            txtPhone.setError("Preencha o campo do telefone");
            isValid = false;
        }else{
            txtPhone.setError(null);
        }
        return isValid;
    }


    private void loadUserLogged() {
        userViewModel.isLogged().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user != null){
                    UserProfileActivity.this.user = user;
                    txtName.setText(user.getName());
                    txtSurname.setText(user.getSurname());
                    txtEmail.setText(user.getEmail());
                    txtBirthDate.setText(user.getBirthDate());
                    txtPhone.setText(user.getPhone());
                    String[] genders = getResources().getStringArray(R.array.genders);
                    for(int i = 0; i < genders.length; i++){
                        if(genders[i].equals(user.getGender())){
                            spnGender.setSelection(i);
                        }
                    }
                }else{
                    startActivity(new Intent(UserProfileActivity.this,
                            UserLoginActivity.class));
                    finish();
                }
            }
        });
    }

    private void setComponents() {
        txtName = findViewById(R.id.txt_edt_name);
        txtSurname = findViewById(R.id.txt_edt_surname);
        txtEmail = findViewById(R.id.txt_edt_email);
        txtBirthDate = findViewById(R.id.txt_edt_birth_date);
        txtPhone = findViewById(R.id.txt_edt_phone);
        spnGender = findViewById(R.id.sp_gender);
    }

    private void setToolBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        txtTitle = findViewById(R.id.toolbar_title);
        txtTitle.setText(getString(R.string.txt_title_profile));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}