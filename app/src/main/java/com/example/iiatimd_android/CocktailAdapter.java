package com.example.iiatimd_android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Base64;
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
        public TextView percentageView;
        public TextView caloriesView;
        public ImageView imageView;

        public CocktailViewHolder(View v){
            super(v);
            textView = v.findViewById(R.id.cocktailTextView);
            descTextView = v.findViewById(R.id.descTextView);
            percentageView = v.findViewById(R.id.percentageView);
            caloriesView = v.findViewById(R.id.caloriesView);
            imageView = v.findViewById(R.id.imageView);
        }

    }

    @NonNull
    @Override
    public CocktailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cocktail_card, parent, false);
        return new CocktailViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CocktailAdapter.CocktailViewHolder holder, int position) {
        Log.d("onBindViewHolder", String.valueOf(position));
        holder.textView.setText(cocktailArray.get(position).get("title"));
        holder.descTextView.setText(cocktailArray.get(position).get("desc"));
        holder.percentageView.setText(cocktailArray.get(position).get("percentage") + "%");
        holder.caloriesView.setText(cocktailArray.get(position).get("calories") + " calories");

        if(cocktailArray.get(position).get("photo") != null || cocktailArray.get(position).get("photo") != "") {
            String filteredString = cocktailArray.get(position).get("photo").replace("\n", "");
            byte[] decodedString = Base64.getDecoder().decode(filteredString);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.imageView.setImageBitmap(decodedByte);
        }
    }

    @Override
    public int getItemCount() {
        Log.d("getItemCount", String.valueOf(cocktailArray.size()));
        return cocktailArray.size();
    }

}
