package com.example;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.porcnakan.R;
import com.example.porcnakan.gameporcces;

public class MainScreen extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        // Fix Window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the button by ID
        Button startGameButton = findViewById(R.id.btnStartGame);

        // Check if button is found
        if (startGameButton == null) {
            Toast.makeText(this, "Button not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Set click listener to navigate to gameporcces class
        startGameButton.setOnClickListener(v -> {
            Toast.makeText(this, "Starting Game...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainScreen.this, gameporcces.class);
            startActivity(intent);
        });

    }
}
