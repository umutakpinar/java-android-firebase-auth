package com.umutakpinar.instaclonejava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.umutakpinar.instaclonejava.databinding.ActivityFeedBinding;

public class FeedActivity extends AppCompatActivity {

    private ActivityFeedBinding binding;
    private String userEmail;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Intent intent = getIntent();
        userEmail = intent.getStringExtra("userEmail");
        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.textView3.setText(userEmail);
    }

    public void btnLogoutClicked(View view){
        auth.signOut();

        Intent intent = new Intent(FeedActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}