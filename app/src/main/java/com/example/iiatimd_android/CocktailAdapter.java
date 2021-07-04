package com.example.iiatimd_android;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CocktailAdapter extends RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder> {
    private String[] cocktails;

    public CocktailAdapter(String[] cocktails){
        this.cocktails = cocktails;
    }
    public static class CocktailViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;

        public CocktailViewHolder(View v){
            super(v);
            textView = textView.findViewById(R.id.textView);
        }
    }

    @NonNull
    @Override
    public CocktailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cocktail_card, parent, false);
        CocktailViewHolder cocktailViewHolder = new CocktailViewHolder(v);
        return cocktailViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CocktailAdapter.CocktailViewHolder holder, int position) {
        holder.textView.setText(cocktails[position]);
    }

    @Override
    public int getItemCount() {
        return cocktails.length;
    }

}
