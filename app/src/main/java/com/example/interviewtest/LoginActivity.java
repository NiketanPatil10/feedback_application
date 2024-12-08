
package com.example.interviewtest;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.interviewtest.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

  ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signuptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });


        binding.loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =binding.emailTxt.getText().toString();
                String password =binding.passwordTxt.getText().toString();

                if (TextUtils.isEmpty(email)){
                    binding.emailTxt.setError("Email is required");
                    binding.emailTxt.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    binding.emailTxt.setError("Valid email is required");
                    binding.emailTxt.requestFocus();
                }else if(TextUtils.isEmpty(password)){
                    binding.passwordTxt.setError("Password is required");
                    binding.passwordTxt.requestFocus();
                }else {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    loginuser(email,password);
                }



            }
        });



    }

    private void loginuser(String email, String password) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser firebaseUser = auth.getCurrentUser();
                if (task.isSuccessful()){
                    binding.progressBar.setVisibility(View.GONE);

                    if (email.equals("niketanpatil1012@gmail.com")) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else  {
                        startActivity(new Intent(LoginActivity.this, FeedbackActivity.class));

                    }

                }else
                {
                    Toast.makeText(LoginActivity.this, "Log in failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}