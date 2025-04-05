package com.example.porcnakan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class simulate extends AppCompatActivity {

    private TextView tvGameResult;
    private Button btnSeeTable;
    private Random random = new Random();

    private int team1Goals = 0;
    private int team2Goals = 0;
    private String myTeam = "Your Team";
    private String opponentTeam = "Opponent";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulate);


        tvGameResult = findViewById(R.id.tvGameResult);
        btnSeeTable = findViewById(R.id.btnSeeTable);


        simulateMatch();

        // Navigate to table when button is clicked
        btnSeeTable.setOnClickListener(v -> {
            Intent intent = new Intent(simulate.this, seetable.class);
            intent.putExtra("MY_TEAM", myTeam);
            intent.putExtra("OPP_TEAM", opponentTeam);
            intent.putExtra("MY_TEAM_GOALS", team1Goals);
            intent.putExtra("OPP_TEAM_GOALS", team2Goals);
            startActivity(intent);
        });
    }

    private void simulateMatch() {
        int totalMinutes = 90;
        team1Goals = 0;
        team2Goals = 0;

        for (int minute = 1; minute <= totalMinutes; minute++) {
            if (random.nextInt(100) < 4) {
                if (random.nextBoolean()) {
                    team1Goals++;
                } else {
                    team2Goals++;
                }
            }
        }

        // Show result in TextView
        String result = "🏁 Full-time: " + team1Goals + " - " + team2Goals;
        if (team1Goals > team2Goals) {
            result += "\n🎉 Your team Wins!";
        } else if (team2Goals > team1Goals) {
            result += "\n🎉 Your team lose";
        } else {
            result += "\n🤝 It's a Draw!";
        }

        tvGameResult.setText(result);
    }
}
