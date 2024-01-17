package com.example.loginmods;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class mailLoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText emailTxt, paswdTxt;
    Button loginBtn;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_login);

        mAuth = FirebaseAuth.getInstance();

        emailTxt = findViewById(R.id.txtEmail);
        paswdTxt = findViewById(R.id.txtPassword);
        loading = findViewById(R.id.progress);
        loginBtn = findViewById(R.id.btnLogin);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);

                String email = String.valueOf(emailTxt.getText());
                String paswd = String.valueOf(paswdTxt.getText());

                if(TextUtils.isEmpty(email)){
                    toast("Enter email.");
                    return;
                }
                if(TextUtils.isEmpty(paswd)){
                    toast("Enter password");
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, paswd)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                loading.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success,
                                    toast("Login success");
                                    Intent intent = new Intent(mailLoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    toast("Login Failed");
                                }
                            }
                        });
            }
        });
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}