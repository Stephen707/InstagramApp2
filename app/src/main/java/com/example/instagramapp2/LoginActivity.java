package com.example.instagramapp2;

import android.content.Intent;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    TextInputLayout textusername,textpassword;
    TextInputEditText tpassword;
    Button BtnLogin;
    TextView tvSignup;
    ImageView ivBack;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textpassword = (TextInputLayout) findViewById(R.id.tipassword);
        textusername = (TextInputLayout) findViewById(R.id.tiUsername);
        BtnLogin = (Button) findViewById(R.id.btn_Login);
        tvSignup = (TextView) findViewById(R.id.signup);
        ivBack = (ImageView) findViewById(R.id.btnback);
        tpassword = findViewById(R.id.pswd);
        checkBox = findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    tpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else {
                    tpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ParseUser parseUser = ParseUser.getCurrentUser();
       if (parseUser != null) {
            goActivity();
        } else {


            BtnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = textusername.getEditText().getText().toString();
                    String password = textpassword.getEditText().getText().toString();
                    if (username.isEmpty() && password.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "All fields required", Toast.LENGTH_SHORT).show();
                    } else {
                        login(username, password);
                    }

                }
            });
       }

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
    private void login(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e!= null){
                    Log.e(TAG,"Issue with login");
                    Toast.makeText(LoginActivity.this, "Username or Password is incorrect", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    return;
                }
                goActivity();
            }
        });
    }
    private void goActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
