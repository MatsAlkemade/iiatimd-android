package com.example.iiatimd_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import com.example.iiatimd_android.Fragments.CocktailListFragment;
import com.example.iiatimd_android.Fragments.SignInFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameHomeContainer, new CocktailListFragment()).commit();
    }
}