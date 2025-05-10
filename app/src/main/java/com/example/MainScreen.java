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

import com.example.porcnakan.MainActivity;
import com.example.porcnakan.R;
import com.example.porcnakan.gameporcces;
import com.example.porcnakan.teamchoose;

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

        // Find the buttons by ID
        Button startGameButton = findViewById(R.id.btnStartGame);
        Button logOutButton = findViewById(R.id.btnLogOut);

        // Check if start game button is found
        if (startGameButton == null) {
            Toast.makeText(this, "Start Game Button not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if logout button is found
        if (logOutButton == null) {
            Toast.makeText(this, "Log Out Button not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        final String myTeam = getIntent().getStringExtra("myTeam");
        startGameButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainScreen.this, teamchoose.class);
            intent.putExtra("myTeam", myTeam);
            startActivity(intent);
        });

        // Add click listener for logout button
        logOutButton.setOnClickListener(v -> {
            // Show logout confirmation
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

            // Redirect to MainActivity
            Intent intent = new Intent(MainScreen.this, MainActivity.class);
            // Clear the activity stack to prevent going back to MainScreen
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}