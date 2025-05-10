package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.porcnakan.R;
import com.example.porcnakan.gameporcces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class table extends AppCompatActivity {

    private Button next;
    private TextView teamNameTextView;
    private ListView matchListView;



    private final Map<String, List<String>> leagueTeams = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);


        teamNameTextView = findViewById(R.id.teamNameTextView);
        matchListView = findViewById(R.id.matchListView);
        next = findViewById(R.id.next);

        initializeLeagues();


        String selectedTeam = getIntent().getStringExtra("selectedTeam");
        teamNameTextView.setText("Team: " + selectedTeam);


        List<String> matchList = generateMatchList(selectedTeam);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, matchList);
        matchListView.setAdapter(adapter);


        next.setOnClickListener(v -> {
            Intent intent = new Intent(table.this, gameporcces.class);
            intent.putExtra("myTeam", selectedTeam);
            startActivity(intent);
        });
    }


    private void initializeLeagues() {
        leagueTeams.put("Premier League", List.of(
                "Arsenal", "Aston Villa", "Bournemouth", "Brentford", "Brighton & Hove Albion", "Burnley",
                "Chelsea", "Crystal Palace", "Everton", "Fulham", "Liverpool", "Luton Town", "Manchester City",
                "Manchester United", "Newcastle United", "Nottingham Forest", "Sheffield United",
                "Tottenham Hotspur", "West Ham United", "Wolverhampton Wanderers"));

        leagueTeams.put("La Liga", List.of(
                "Alaves", "Almería", "Athletic Club", "Atlético Madrid", "Barcelona", "Cádiz", "Celta Vigo",
                "Getafe", "Girona", "Granada", "Las Palmas", "Mallorca", "Osasuna", "Rayo Vallecano",
                "Real Betis", "Real Madrid", "Real Sociedad", "Sevilla", "Valencia", "Villarreal"));

        leagueTeams.put("Serie A", List.of(
                "Atalanta", "Bologna", "Cagliari", "Empoli", "Fiorentina", "Frosinone", "Genoa", "Inter Milan",
                "Juventus", "Lazio", "Lecce", "Milan", "Monza", "Napoli", "Roma", "Salernitana",
                "Sassuolo", "Torino", "Udinese", "Verona"));

        leagueTeams.put("Bundesliga", List.of(
                "Augsburg", "Bayer Leverkusen", "Bayern Munich", "Bochum", "Borussia Dortmund",
                "Borussia Mönchengladbach", "Darmstadt", "Eintracht Frankfurt", "Freiburg", "Heidenheim",
                "Hoffenheim", "Köln", "Mainz", "RB Leipzig", "Stuttgart", "Union Berlin",
                "Werder Bremen", "Wolfsburg"));

        leagueTeams.put("Ligue 1", List.of(
                "Clermont", "Le Havre", "Lens", "Lille", "Lorient", "Lyon", "Marseille", "Metz", "Monaco",
                "Montpellier", "Nantes", "Nice", "Paris Saint-Germain", "Reims", "Rennes", "Strasbourg",
                "Toulouse", "Brest"));
    }


    private List<String> generateMatchList(String selectedTeam) {
        List<String> matches = new ArrayList<>();


        for (Map.Entry<String, List<String>> entry : leagueTeams.entrySet()) {
            if (entry.getValue().contains(selectedTeam)) {
                // Create matchups with other teams in the same league
                for (String opponent : entry.getValue()) {
                    if (!opponent.equals(selectedTeam)) {
                        matches.add(selectedTeam + " vs " + opponent);
                    }
                }
                break;
            }
        }

        // Fallback if no league is found
        if (matches.isEmpty()) {
            matches.add("No matches available for " + selectedTeam);
        }

        return matches;

    }
}
