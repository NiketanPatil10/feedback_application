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

import com.example.interviewtest.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

  ActivityRegisterBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = binding.usernameTxt.getText().toString();
                String email = binding.emailTxt.getText().toString();
                String password = binding.passwordTxt.getText().toString();
                String confirmpassword = binding.confirmpasswordTxt.getText().toString();

                if (TextUtils.isEmpty(username)){
                    binding.usernameTxt.setError("Username is required");
                    binding.usernameTxt.requestFocus();
                } else if (TextUtils.isEmpty(email)) {
                    binding.emailTxt.setError("Email id is required");
                    binding.emailTxt.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.passwordTxt.setError("Valid email is required");
                    binding.passwordTxt.requestFocus();
                }else if (TextUtils.isEmpty(password)) {
                    binding.passwordTxt.setError("Please enter your password");
                    binding.passwordTxt.requestFocus();
                }else if (TextUtils.isEmpty(confirmpassword)) {
                    binding.confirmpasswordTxt.setError("Please enter your confirm password");
                    binding.confirmpasswordTxt.requestFocus();
                }else if (password.length()<7) {
                    binding.passwordTxt.setError("Password should be at least 7 digit");
                    binding.passwordTxt.requestFocus();
                }else if (!password.equals(confirmpassword)) {
                    binding.confirmpasswordTxt.setError("Password confirmation is required");
                    binding.confirmpasswordTxt.requestFocus();
                }else {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    registerUser(email,password);
                }
            }

        });


        binding.loginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });



    }





    private void registerUser(String email, String password) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        // crate user
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser =auth.getCurrentUser();
                    Toast.makeText(RegisterActivity.this, "You registered successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(RegisterActivity.this, "Your registration is failed", Toast.LENGTH_SHORT).show();
                }
                binding.progressBar.setVisibility(View.GONE);

            }
        });
    }
}