package com.example.iiatimd_android;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class CocktailAdapter extends RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder> {
    private ArrayList<HashMap<String, String>> cocktailArray;

    public CocktailAdapter(ArrayList<HashMap<String, String>> arrayList){
        Log.d("cocktailadapter","werkt");
        cocktailArray = arrayList;
    }

    public static class CocktailViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public TextView descTextView;

        public CocktailViewHolder(View v){
            super(v);
            textView = v.findViewById(R.id.cocktailTextView);
            descTextView = v.findViewById(R.id.descTextView);
        }
    }

    @NonNull
    @Override
    public CocktailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cocktail_card, parent, false);
        return new CocktailViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CocktailAdapter.CocktailViewHolder holder, int position) {
        Log.d("onBindViewHolder", String.valueOf(position));
        holder.textView.setText(cocktailArray.get(position).get("title"));
        holder.descTextView.setText(cocktailArray.get(position).get("desc"));
    }

    @Override
    public int getItemCount() {
        Log.d("getItemCount", String.valueOf(cocktailArray.size()));
        return cocktailArray.size();
    }

}
