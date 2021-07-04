package com.example.iiatimd_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button toLoginBtn = findViewById(R.id.mainLoginBtn);
        toLoginBtn.setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();


        String[] cocktails = new String[200];

        for(int i=0; i < 200; i++){
            cocktails[i] = "Cocktail" + i;
        }

        recyclerViewAdapter = new CocktailAdapter(cocktails);
        recyclerView.setAdapter(recyclerViewAdapter);
        RequestQueue queue = Volley.newRequestQueue(this);

    }

    public void onClick(View v){
        Intent toLoginIntent = new Intent(this,AuthActivity.class);
        startActivity(toLoginIntent);
    }
}