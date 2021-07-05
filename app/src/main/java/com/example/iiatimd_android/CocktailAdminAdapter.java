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

public class CocktailAdminAdapter extends RecyclerView.Adapter<CocktailAdminAdapter.AdminViewHolder> {
    private ArrayList<HashMap<String, String>> cocktailArray;

    public CocktailAdminAdapter(ArrayList<HashMap<String, String>> arrayList){
        cocktailArray = arrayList;
    }

    public static class AdminViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ImageView cocktailImage;

        public AdminViewHolder(View v){
            super(v);
            textView = v.findViewById(R.id.cocktailTextViewAdmin);
            cocktailImage = v.findViewById(R.id.cocktailAdminImage);
        }

    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_admin_cocktails_card, parent, false);
        return new AdminViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CocktailAdminAdapter.AdminViewHolder holder, int position) {
        holder.textView.setText(cocktailArray.get(position).get("title"));
        Log.d("onBindViewHolder", cocktailArray.get(position).get("photo"));
        if(cocktailArray.get(position).get("photo") != null || cocktailArray.get(position).get("photo") != "") {
            String filteredString = cocktailArray.get(position).get("photo").replace("\n", "");
            byte[] decodedString = Base64.getDecoder().decode(filteredString);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.cocktailImage.setImageBitmap(decodedByte);
        }

    }

    @Override
    public int getItemCount() {
        Log.d("getItemCount", String.valueOf(cocktailArray.size()));
        return cocktailArray.size();
    }
}
