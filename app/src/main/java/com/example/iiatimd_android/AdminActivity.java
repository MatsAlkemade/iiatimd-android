package com.example.iiatimd_android;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

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

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameAdminContainer, new AdminCocktailsFragment()).commit();
    }
}