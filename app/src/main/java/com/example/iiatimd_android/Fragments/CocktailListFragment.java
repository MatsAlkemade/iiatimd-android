package com.example.iiatimd_android.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.iiatimd_android.CocktailAdapter;
import com.example.iiatimd_android.Constant;
import com.example.iiatimd_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CocktailListFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public CocktailListFragment(){
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_cocktail_list, container, false);
        init();
        return view;
    }


    private void init() {
        recyclerView = view.findViewById(R.id.recyclerCocktailView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();

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
                    cocktailMap.put("photo", cocktails.getJSONObject(i).getString("photo"));
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

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }
}


