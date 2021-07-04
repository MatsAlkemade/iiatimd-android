package com.example.iiatimd_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Button toLoginBtn = findViewById(R.id.mainLoginBtn);
//        toLoginBtn.setOnClickListener(this);
//    }
//
//    public void onClick(View v){
//        Intent toLoginIntent = new Intent(this,AuthActivity.class);
//        startActivity(toLoginIntent);
//    }

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();

//        for (int i = 0; i < 200; i++) {
//            cocktails[i] = "Cocktail" + i;
//        }

        ArrayList<HashMap<String, String>> cocktailArray = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET, Constant.COCKTAILS, response -> {
            try {
                JSONObject object = new JSONObject(response);
                JSONArray cocktails = object.getJSONArray("cocktails");
                for (int i = 0; i < cocktails.length(); i++){
                    HashMap<String, String> cocktailMap = new HashMap<>();

                    cocktailMap.put("id", cocktails.getJSONObject(i).getString("id"));
                    cocktailMap.put("title", cocktails.getJSONObject(i).getString("title"));
                    cocktailMap.put("desc", cocktails.getJSONObject(i).getString("desc"));
                    cocktailMap.put("percentage", cocktails.getJSONObject(i).getString("percentage"));
                    cocktailMap.put("calories", cocktails.getJSONObject(i).getString("calories"));
                    cocktailArray.add(cocktailMap);
                }

                recyclerViewAdapter = new CocktailAdapter(cocktailArray);
                recyclerView.setAdapter(recyclerViewAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}

