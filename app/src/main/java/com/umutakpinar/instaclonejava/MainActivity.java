package com.umutakpinar.instaclonejava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthCredential;
import com.google.firebase.installations.local.PersistedInstallation;
import com.umutakpinar.instaclonejava.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){

            /* User is signed in */
            userEmail = user.getEmail();
            Intent intent = new Intent(MainActivity.this,FeedActivity.class);
            intent.putExtra("userEmail",userEmail);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if(intent.getBooleanExtra("registration",false)){
            binding.editTextEmail.setText(intent.getStringExtra("email"));
            binding.editTextPassword.setText(intent.getStringExtra("pass"));
        }
    }

    public void btnSignInClicked(View view){
        String email = binding.editTextEmail.getText().toString();
        String pass = binding.editTextPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                task.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent = new Intent(MainActivity.this,FeedActivity.class);
                        userEmail = email;
                        intent.putExtra("userEmail",userEmail);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public void btnSignUpClicked(View view){
        String email = binding.editTextEmail.getText().toString();
        String pass = binding.editTextPassword.getText().toString();
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);


        intent.putExtra("email",email);
        intent.putExtra("pass",pass);

        startActivity(intent);
    }
}