package com.example.porcnakan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

import com.example.table;

import java.util.*;

public class teamchoose extends AppCompatActivity {

    private Spinner leagueSpinner, teamSpinner;
    private Button playButton;
    private Map<String, List<String>> leagues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teamchoose);

        leagueSpinner = findViewById(R.id.leagueSpinner);
        teamSpinner = findViewById(R.id.teamSpinner);
        playButton = findViewById(R.id.button2);

        initializeLeagues();
        setupLeagueSpinner();


        playButton.setOnClickListener(v -> {
            String selectedTeam = teamSpinner.getSelectedItem().toString();
            Intent intent = new Intent(teamchoose.this, table.class);
            intent.putExtra("selectedTeam", selectedTeam); // This must match exactly
            startActivity(intent);

        });
    }

    private void initializeLeagues() {
        leagues = new LinkedHashMap<>();

        leagues.put("Premier League", Arrays.asList(
                "Arsenal", "Aston Villa", "Bournemouth", "Brentford", "Brighton & Hove Albion", "Burnley",
                "Chelsea", "Crystal Palace", "Everton", "Fulham", "Liverpool", "Luton Town", "Manchester City",
                "Manchester United", "Newcastle United", "Nottingham Forest", "Sheffield United",
                "Tottenham Hotspur", "West Ham United", "Wolverhampton Wanderers"));

        leagues.put("La Liga", Arrays.asList(
                "Alaves", "Almería", "Athletic Club", "Atlético Madrid", "Barcelona", "Cádiz", "Celta Vigo",
                "Getafe", "Girona", "Granada", "Las Palmas", "Mallorca", "Osasuna", "Rayo Vallecano",
                "Real Betis", "Real Madrid", "Real Sociedad", "Sevilla", "Valencia", "Villarreal"));

        leagues.put("Bundesliga", Arrays.asList(
                "Augsburg", "Bayer Leverkusen", "Bayern Munich", "Bochum", "Borussia Dortmund",
                "Borussia Mönchengladbach", "Darmstadt", "Eintracht Frankfurt", "Freiburg", "Heidenheim",
                "Hoffenheim", "Köln", "Mainz", "RB Leipzig", "Stuttgart", "Union Berlin",
                "Werder Bremen", "Wolfsburg"));

        leagues.put("Serie A", Arrays.asList(
                "Atalanta", "Bologna", "Cagliari", "Empoli", "Fiorentina", "Frosinone", "Genoa", "Inter Milan",
                "Juventus", "Lazio", "Lecce", "Milan", "Monza", "Napoli", "Roma", "Salernitana",
                "Sassuolo", "Torino", "Udinese", "Verona"));

        leagues.put("Ligue 1", Arrays.asList(
                "Clermont", "Le Havre", "Lens", "Lille", "Lorient", "Lyon", "Marseille", "Metz", "Monaco",
                "Montpellier", "Nantes", "Nice", "Paris Saint-Germain", "Reims", "Rennes", "Strasbourg",
                "Toulouse", "Brest"));
    }

    private void setupLeagueSpinner() {
        ArrayAdapter<String> leagueAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>(leagues.keySet()));
        leagueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leagueSpinner.setAdapter(leagueAdapter);

        leagueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                String selectedLeague = (String) parent.getItemAtPosition(position);
                updateTeamSpinner(leagues.get(selectedLeague));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updateTeamSpinner(List<String> teams) {
        ArrayAdapter<String> teamAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, teams);
        teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamSpinner.setAdapter(teamAdapter);
    }
}
