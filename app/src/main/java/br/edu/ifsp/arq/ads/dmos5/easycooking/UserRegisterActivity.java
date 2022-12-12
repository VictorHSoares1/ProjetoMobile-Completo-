package br.edu.ifsp.arq.ads.dmos5.easycooking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import br.edu.ifsp.arq.ads.dmos5.easycooking.model.User;
import br.edu.ifsp.arq.ads.dmos5.easycooking.viewmodel.UserViewModel;

public class UserRegisterActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitle;
    private TextInputEditText txtName;
    private TextInputEditText txtEmail;
    private TextInputEditText txtPassword;
    private Button btnRegister;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        setToolBar();
        setBtnRegister();
    }

    private void setBtnRegister() {
        txtName = findViewById(R.id.txt_edt_name);
        txtEmail = findViewById(R.id.txt_edt_email);
        txtPassword = findViewById(R.id.txt_edt_password);
        btnRegister = findViewById(R.id.btn_user_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()) {
                    User user = new User(
                            txtEmail.getText().toString(),
                            txtName.getText().toString(),
                            "",
                            txtPassword.getText().toString(),
                            "",
                            "",
                            "",
                            ""
                    );
                    if(user.getPassword().length() >= 6){
                        userViewModel.createUser(user);
                        finish();
                    }else{
                        Toast.makeText(UserRegisterActivity.this,
                                R.string.msg_erro_password, Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    private boolean validate() {
        boolean isValid = true;
        if(txtName.getText().toString().trim().isEmpty()){
            txtName.setError("Preencha o campo do nome");
            isValid = false;
        }else{
            txtName.setError(null);
        }
        if(txtEmail.getText().toString().trim().isEmpty()){
            txtEmail.setError("Preencha o campo do e-mail");
            isValid = false;
        }else{
            txtEmail.setError(null);
        }
        if(txtPassword.getText().toString().trim().isEmpty()){
            txtPassword.setError("Preencha o campo da senha");
            isValid = false;
        }else{
            txtPassword.setError(null);
        }
        return isValid;
    }

    private void setToolBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        txtTitle = findViewById(R.id.toolbar_title);
        txtTitle.setText(getString(R.string.txt_title_new_user));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}