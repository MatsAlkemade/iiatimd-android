package com.example.iiatimd_android.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.iiatimd_android.AuthActivity;
import com.example.iiatimd_android.Constant;
import com.example.iiatimd_android.HomeActivity;
import com.example.iiatimd_android.MainActivity;
import com.example.iiatimd_android.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddCocktailFragment extends Fragment {

    private static final int IMAGE_PICK = 1;
    private View view;
    private TextInputLayout layoutName, layoutDesc, layoutPercentage, layoutCalories;
    private TextInputEditText txtName, txtDesc, txtPercentage, txtCalories;
    private TextView btnCancel;
    private Button btnConfirm, addImageBtn;
    private ProgressDialog dialog;
    private SharedPreferences userPref;


    public AddCocktailFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_add_cocktail,container, false);
        init();
        return view;
    }

    private void init() {
        userPref = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        layoutName = view.findViewById(R.id.txtLayoutNameCocktail);
        layoutDesc = view.findViewById(R.id.txtLayoutDescCocktail);
        layoutPercentage = view.findViewById(R.id.txtLayoutPercentageCocktail);
        layoutCalories = view.findViewById(R.id.txtLayoutCaloriesCocktail);
        txtName = view.findViewById(R.id.txtNameCocktail);
        txtDesc = view.findViewById(R.id.txtDescCocktail);
        txtPercentage = view.findViewById(R.id.txtPercentageCocktail);
        txtCalories = view.findViewById(R.id.txtCaloriesCocktail);
        addImageBtn = view.findViewById(R.id.imageAddBtn);
        btnCancel = view.findViewById(R.id.addCocktailCancel);
        btnConfirm = view.findViewById(R.id.addCocktailConfirm);
        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);

        btnConfirm.setOnClickListener(v -> {
            layoutName.setErrorEnabled(false);
            layoutDesc.setErrorEnabled(false);
            layoutPercentage.setErrorEnabled(false);
            layoutCalories.setErrorEnabled(false);

            if (validate()) {
                addCocktail();
            }
        });

        btnCancel.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdminContainer, new AdminCocktailsFragment(userPref)).commit();
        });

        addImageBtn.setOnClickListener(v -> {
            Intent gallery = new Intent (Intent.ACTION_PICK);
            gallery.setType("image/*");
            startActivityForResult(gallery, IMAGE_PICK);
        });
    }

    private boolean validate() {
        if (txtName.getText().toString().isEmpty()) {
            layoutName.setErrorEnabled(true);
            layoutName.setError("Name is Required");
            return false;
        }

        if (txtDesc.getText().toString().isEmpty()){
            layoutDesc.setErrorEnabled(true);
            layoutDesc.setError("Description is Required");
            return false;
        }

        if (txtPercentage.getText().toString().isEmpty()){
            layoutPercentage.setErrorEnabled(true);
            layoutPercentage.setError("Percentage is Required");
            return false;
        }

        if (Double.parseDouble(txtPercentage.getText().toString()) > 100){
            layoutPercentage.setErrorEnabled(true);
            layoutPercentage.setError("Percentage must be between 0-100");
            return false;
        }
        if (txtCalories.getText().toString().isEmpty()){
            layoutCalories.setErrorEnabled(true);
            layoutCalories.setError("Calories is Required");
            return false;
        }

        return true;
    }

    private void addCocktail() {
        Log.d("werktHet?", "komt die erin ");

        dialog.setMessage("Adding cocktail");
        Log.d("toevoegen", "voegt die hem toe ");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Constant.COCKTAIL_CREATE, response -> {
            Log.d("postRequest", "verstuurt die de post ");
            try {
                Log.d("response2", response);
                JSONObject object = new JSONObject(response);
                Log.d("state", String.valueOf(object.getBoolean("success")));
                if (object.getBoolean("success") == true) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdminContainer, new AdminCocktailsFragment(userPref)).commit();
                    Toast.makeText(getContext(), "Cocktail added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        }, error -> {
            error.printStackTrace();
            dialog.dismiss();
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token","");
                Log.d("token", token);
                HashMap<String,String> map = new HashMap<>();
                map.put("Authorization","Bearer "+token);
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("title",txtName.getText().toString());
                map.put("desc",txtDesc.getText().toString());
                map.put("percentage",txtPercentage.getText().toString());
                map.put("calories",txtCalories.getText().toString());
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }
}
