package com.example.porcnakan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class gameporcces extends AppCompatActivity {
    private TextView myTeamTextView, opponentTeamTextView;
    private Button simulateButton;

    private String myClub;
    private String opponentClub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameporcces);

        // Find views in layout
        myTeamTextView = findViewById(R.id.tvMyTeam);
        opponentTeamTextView = findViewById(R.id.tvOpponentTeam);
        simulateButton = findViewById(R.id.btnSimulate);

        // Get selected teams from Intent
        myClub = getIntent().getStringExtra("MY_CLUB");
        opponentClub = getIntent().getStringExtra("OPPONENT_CLUB");

        // Log the received values for debugging
        Log.d("GameProcess", "Received My Team: " + myClub);
        Log.d("GameProcess", "Received Opponent: " + opponentClub);

        // Ensure values are not null
        if (myClub == null || myClub.isEmpty()) {
            myClub = "Unknown Team";
        }
        if (opponentClub == null || opponentClub.isEmpty()) {
            opponentClub = "Unknown Opponent";
        }

        // Display teams in the TextViews
        myTeamTextView.setText("⚽ My Team: " + myClub);
        opponentTeamTextView.setText("⚔️ Opponent: " + opponentClub);

        // Set up button click to go to simulate activity
        simulateButton.setOnClickListener(v -> {
            Intent intent = new Intent(gameporcces.this, simulate.class);
            intent.putExtra("TEAM1", myClub);
            intent.putExtra("TEAM2", opponentClub);
            startActivity(intent);
        });
    }
}
