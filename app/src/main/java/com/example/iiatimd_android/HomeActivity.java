package com.example.iiatimd_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.iiatimd_android.Fragments.AppDatabase;
import com.example.iiatimd_android.Fragments.Cocktail;
import com.example.iiatimd_android.Fragments.CocktailListFragment;
import com.example.iiatimd_android.Fragments.SignInFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());

/*
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "CocktailDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
*/

//        ArrayList<HashMap<String, String>> cocktailArray = new ArrayList<>();

        if(db.cocktailDAO().getAll().size() < 1){

            StringRequest request = new StringRequest(Request.Method.GET, Constant.COCKTAILS, response -> {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray cocktails = object.getJSONArray("cocktails");
                    for (int i = 0; i < cocktails.length(); i++){
                        //                    HashMap<String, String> cocktailMap = new HashMap<>();
                        //
                        //                    cocktailMap.put("id", cocktails.getJSONObject(i).getString("id"));
                        //                    cocktailMap.put("title", cocktails.getJSONObject(i).getString("title"));
                        //                    cocktailMap.put("desc", cocktails.getJSONObject(i).getString("desc"));
                        //                    cocktailMap.put("percentage", cocktails.getJSONObject(i).getString("percentage"));
                        //                    cocktailMap.put("calories", cocktails.getJSONObject(i).getString("calories"));
                        //                    cocktailMap.put("photo", cocktails.getJSONObject(i).getString("photo"));
                        //                    cocktailArray.add(cocktailMap);

                        String cocktailTitle = cocktails.getJSONObject(i).getString("title");
                        String cocktailDesc = cocktails.getJSONObject(i).getString("desc");
                        String cocktailCalories = cocktails.getJSONObject(i).getString("calories");
                        String cocktailPercentage = cocktails.getJSONObject(i).getString("percentage");
                        int cocktailId = Integer.parseInt(cocktails.getJSONObject(i).getString("id"));

                        Cocktail[] cocktailArray = new Cocktail[cocktails.length()];
                        cocktailArray[i] = new Cocktail(cocktailTitle, cocktailDesc, cocktailCalories, cocktailPercentage, cocktailId);

                        db.cocktailDAO().InsertCocktail(cocktailArray[i]);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {
                error.printStackTrace();
            });

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        }

        if(db.cocktailDAO().getAll().size() > 0){
            String name = db.cocktailDAO().getAll().get(0).getTitle();
            Log.d("dbTest", name);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.frameHomeContainer, new CocktailListFragment()).commit();
        init();
    }

    private void init() {
        preferences = getSharedPreferences("user",Context.MODE_PRIVATE);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.page2: {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Do you want to logout?");
                    builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            logout();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
                switch (item.getItemId()) {
                    case R.id.page1:
                        Intent toGoHomeActivityIntent = new Intent(this, HomeActivity.class);
                        startActivity(toGoHomeActivityIntent);
                }
            }

            return super.onOptionsItemSelected(item);
        }

        private void logout() {
            StringRequest request = new StringRequest(Request.Method.GET, Constant.LOGOUT, res -> {

                try {
                    JSONObject object = new JSONObject(res);
                    if (object.getBoolean("success")) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {
                error.printStackTrace();
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    String token = preferences.getString("token", "");
                    HashMap<String, String> map = new HashMap<>();
                    map.put("Authorization", "Bearer " + token);
                    return map;
                }
            };

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        }
    }

