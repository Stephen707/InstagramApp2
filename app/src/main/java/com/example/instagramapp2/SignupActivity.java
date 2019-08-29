package com.example.instagramapp2;

import android.content.Intent;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {
    TextInputLayout Username,Email,Password,RePassword;
    TextInputEditText tpassword,trepassword;

    Button BtnSignup;
    ImageView ivBack;
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Username = (TextInputLayout)findViewById(R.id.username);
        Email = (TextInputLayout)findViewById(R.id.email);
        Password = (TextInputLayout)findViewById(R.id.tipassword);
        RePassword = (TextInputLayout)findViewById(R.id.tirepassword);
        tpassword  =(TextInputEditText)findViewById(R.id.pswd);
        trepassword = (TextInputEditText)findViewById(R.id.confirpswd);
        BtnSignup = (Button)findViewById(R.id.btn_signup);
        ivBack  = (ImageView)findViewById(R.id.btnback);
        checkBox = findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    tpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    trepassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else {

                    tpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    trepassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        BtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = Username.getEditText().getText().toString();
                String email = Email.getEditText().getText().toString();
                String password = Password.getEditText().getText().toString();
                String repassword = RePassword.getEditText().getText().toString();
                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || repassword.isEmpty()){
                    Toast.makeText(SignupActivity.this, "All fields required", Toast.LENGTH_SHORT).show();
                    return;
                }else
                if (!password.contentEquals(repassword)){
                    Toast.makeText(SignupActivity.this, "Password not matching", Toast.LENGTH_SHORT).show();
                    return;
                }else
                if (password.length() <= 6){
                    Toast.makeText(SignupActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
                    return;
                }
                signup(username,email,password);

            }
        });
    }

    private void signup(String username, String email, String password) {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                    startActivity(intent);
                    //LoginActivity.usernameCookie = username;
                    Toast.makeText(SignupActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    e.printStackTrace();
                    Toast.makeText(SignupActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
