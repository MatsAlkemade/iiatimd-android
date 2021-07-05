package com.example.iiatimd_android;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.iiatimd_android.Fragments.AdminCocktailsFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

public class AddCocktailActivity extends AppCompatActivity {

    private static final int IMAGE_PICK = 1;
    private TextInputLayout layoutName, layoutDesc, layoutPercentage, layoutCalories;
    private TextInputEditText txtName, txtDesc, txtPercentage, txtCalories;
    private TextView btnCancel;
    private ImageView addImage;
    private Button btnConfirm, addImageBtn;
    private ProgressDialog dialog;
    private SharedPreferences userPref;
    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cocktail);
        init();
    }

    private void init(){
        userPref = getSharedPreferences("user", Context.MODE_PRIVATE);
        layoutName = findViewById(R.id.txtLayoutNameCocktail);
        layoutDesc = findViewById(R.id.txtLayoutDescCocktail);
        layoutPercentage = findViewById(R.id.txtLayoutPercentageCocktail);
        layoutCalories = findViewById(R.id.txtLayoutCaloriesCocktail);
        txtName = findViewById(R.id.txtNameCocktail);
        txtDesc = findViewById(R.id.txtDescCocktail);
        txtPercentage = findViewById(R.id.txtPercentageCocktail);
        txtCalories = findViewById(R.id.txtCaloriesCocktail);
        addImage = findViewById(R.id.imageAddCocktail);
        addImageBtn = findViewById(R.id.imageAddBtn);
        btnCancel = findViewById(R.id.addCocktailCancel);
        btnConfirm = findViewById(R.id.addCocktailConfirm);
        dialog = new ProgressDialog(this);
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
            finish();
        });

        addImageBtn.setOnClickListener(v -> {
            Intent gallery = new Intent (Intent.ACTION_PICK);
            gallery.setType("image/*");
            startActivityForResult(gallery, IMAGE_PICK);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==IMAGE_PICK && resultCode==RESULT_OK){
            Uri imgUri = data.getData();
            addImage.setImageURI(imgUri);

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        dialog.setMessage("Adding cocktail");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Constant.COCKTAIL_CREATE, response -> {
            try {
                Log.d("response", response);
                JSONObject object = new JSONObject(response);
                Log.d("state", String.valueOf(object.getBoolean("success")));
                if (object.getBoolean("success") == true) {
                    Toast.makeText(this, "Cocktail added", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                map.put("photo", bitmapToString(bitmap));
                Log.d("photoBitmap", map.get("photo"));
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private String bitmapToString(Bitmap bitmap){
        if(bitmap != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte [] array = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(array,Base64.DEFAULT);
        }

        return "";
    }
}