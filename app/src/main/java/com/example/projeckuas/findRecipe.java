package com.example.projeckuas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;

public class findRecipe extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText name, ingredient, steps;
    private Button save, openGallery, back;
    private ImageView imageView;
    private LinearLayout linearLayoutRecipes;
    private Uri imageUri;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_recipe);

        name = findViewById(R.id.name);
        ingredient = findViewById(R.id.ingredient);
        steps = findViewById(R.id.steps);
        save = findViewById(R.id.btnSave);
        back = findViewById(R.id.btnBack);
        openGallery = findViewById(R.id.btnOpenGallery);
        imageView = findViewById(R.id.imageView);
        linearLayoutRecipes = findViewById(R.id.linearLayoutRecipes);

        db = AppDatabase.getDatabase(this);

        openGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameStr = name.getText().toString();
                String ingredientStr = ingredient.getText().toString();
                String stepStr = steps.getText().toString();
                if (nameStr.isEmpty() || ingredientStr.isEmpty() || stepStr.isEmpty() || imageUri == null) {
                    Toast.makeText(getApplicationContext(), "Silakan isi semua input.", Toast.LENGTH_SHORT).show();
                    return;
                }
                saveRecipe(nameStr, ingredientStr, stepStr, imageUri.toString());
                Toast.makeText(getApplicationContext(), "Resep berhasil disimpan!", Toast.LENGTH_SHORT).show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    Intent pindah = new Intent(findRecipe.this, MainActivity.class);
                    startActivity(pindah);
                }
            }
        });

        loadRecipesFromDatabase();
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveRecipe(String name, String ingredient, String steps, String imagePath) {
        Recipe recipe = new Recipe(name, ingredient, steps, imagePath);
        Executors.newSingleThreadExecutor().execute(() -> {
            db.recipeDao().insert(recipe);
            runOnUiThread(() -> addRecipeToLayout(recipe));
        });
    }

    private void loadRecipesFromDatabase() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Recipe> recipes = db.recipeDao().getAllRecipes();
            runOnUiThread(() -> {
                for (Recipe recipe : recipes) {
                    addRecipeToLayout(recipe);
                }
            });
        });
    }

    private void addRecipeToLayout(Recipe recipe) {
        View recipeView = getLayoutInflater().inflate(R.layout.activity_recipe_detail, null);

        TextView textViewName = recipeView.findViewById(R.id.textViewRecipeName);
        TextView textViewIngredient = recipeView.findViewById(R.id.textViewRecipeIngredient);
        TextView textViewSteps = recipeView.findViewById(R.id.textViewRecipeSteps);
        ImageView imageViewRecipe = recipeView.findViewById(R.id.imageViewRecipe);

        textViewName.setText("Name of Recipe: " + recipe.getName());
        textViewIngredient.setText("Ingredient: " + recipe.getIngredient());
        textViewSteps.setText("Steps: " + recipe.getSteps());
        imageViewRecipe.setImageURI(Uri.parse(recipe.getImagePath()));

        linearLayoutRecipes.addView(recipeView);
    }
}
