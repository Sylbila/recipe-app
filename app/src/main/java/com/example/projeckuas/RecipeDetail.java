package com.example.projeckuas;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetail extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        TextView textViewName = findViewById(R.id.textViewRecipeName);
        TextView textViewIngredient = findViewById(R.id.textViewRecipeIngredient);
        TextView textViewSteps = findViewById(R.id.textViewRecipeSteps);
        ImageView imageViewRecipe = findViewById(R.id.imageViewRecipe);

        // Ambil data dari intent
        String name = getIntent().getStringExtra("name");
        String ingredient = getIntent().getStringExtra("ingredient");
        String steps = getIntent().getStringExtra("steps");
        String imagePath = getIntent().getStringExtra("imagePath");

        // Set data ke view
        textViewName.setText("Name of Recipe: " + name);
        textViewIngredient.setText("Ingredient: " + ingredient);
        textViewSteps.setText("Steps: " + steps);
        imageViewRecipe.setImageURI(Uri.parse(imagePath));
    }
}
