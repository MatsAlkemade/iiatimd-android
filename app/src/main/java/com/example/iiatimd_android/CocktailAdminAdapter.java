package com.example.iiatimd_android;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class CocktailAdminAdapter extends RecyclerView.Adapter<CocktailAdminAdapter.AdminViewHolder> {
    private Context context;
    private ArrayList<HashMap<String, String>> cocktailArray;
    private ArrayList<HashMap<String, String>> listAll;
    private SharedPreferences preferences;

    public CocktailAdminAdapter(Context contextVar, SharedPreferences preferencesVar, ArrayList<HashMap<String, String>> arrayList){
        context = contextVar;
        cocktailArray = arrayList;
        listAll = new ArrayList<>(arrayList);
        preferences = preferencesVar;
    }

    public static class AdminViewHolder extends RecyclerView.ViewHolder{
        public TextView textView, removeCocktailBtn;
        public ImageView cocktailImage;

        public AdminViewHolder(View v){
            super(v);
            textView = v.findViewById(R.id.cocktailTextViewAdmin);
            cocktailImage = v.findViewById(R.id.cocktailAdminImage);
            removeCocktailBtn = v.findViewById(R.id.removeCocktail);
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
        holder.removeCocktailBtn.setOnClickListener(v -> {
            removeCocktail(cocktailArray.get(position).get("id"), position);
        });
    }

    private void removeCocktail(String id, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to remove this cocktail?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringRequest request = new StringRequest(Request.Method.POST,Constant.COCKTAIL_DELETE, response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getBoolean("success")){
                            cocktailArray.remove(position);
                            notifyItemRemoved(position);
                            notifyDataSetChanged();
                            listAll.clear();
                            listAll.addAll(cocktailArray);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {

                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        String token = preferences.getString("token","");
                        HashMap<String,String> map = new HashMap<>();
                        map.put("Authorization","Bearer "+token);
                        return map;
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> map = new HashMap<>();
                        map.put("id",id+"");
                        return map;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(request);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    public int getItemCount() {
        Log.d("getItemCount", String.valueOf(cocktailArray.size()));
        return cocktailArray.size();
    }
}
