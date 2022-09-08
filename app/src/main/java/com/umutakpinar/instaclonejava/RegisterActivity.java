package com.umutakpinar.instaclonejava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.umutakpinar.instaclonejava.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Intent intent = getIntent();

        //Firebase Auth oluşturduk
        mAuth = FirebaseAuth.getInstance();

        //MainAct içerisinde mail ve pass yazıldıysa onları buraya aktardım tekrar yazmak zorunda kalmasın diye
        binding.editTextMail.setText(intent.getStringExtra("email"));
        binding.editTextPass.setText(intent.getStringExtra("pass"));
    }

    public void registerClicked(View view){
        String email = binding.editTextMail.getText().toString();
        String pass = binding.editTextPass.getText().toString();

        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(task -> {
            task.addOnSuccessListener(authResult -> {
                //if succesfull!
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                intent.putExtra("registration",true);
                intent.putExtra("email",email);
                intent.putExtra("pass",pass);
                Toast.makeText(this, "Successfully registered!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish(); //bunu kapatıyorumm!
            }).addOnFailureListener(e -> {
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            });
        });
    }

}