package com.example.iiatimd_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.iiatimd_android.Fragments.AddCocktailFragment;
import com.example.iiatimd_android.Fragments.AdminCocktailsFragment;
import com.example.iiatimd_android.Fragments.SignInFragment;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameAdminContainer, new AdminCocktailsFragment()).commit();
    }
}