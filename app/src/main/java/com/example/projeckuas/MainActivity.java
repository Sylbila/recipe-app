package com.example.projeckuas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button FindRecipe, Recom, Myrecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FindRecipe=findViewById(R.id.btnFR);
        Recom=findViewById(R.id.btnRC);
        Myrecipe=findViewById(R.id.btnMR);

        FindRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    Intent pindah = new Intent(MainActivity.this, findRecipe.class);
                    startActivity(pindah);
                }
            }
        });Recom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    Intent pindah = new Intent(MainActivity.this, recommendation.class);
                    startActivity(pindah);
                }
            }
        });
        Myrecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    Intent pindah = new Intent(MainActivity.this, RecipeDetail.class);
                    startActivity(pindah);
                }
            }
        });
    }
}