package com.example.porcnakan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class gameporcces extends AppCompatActivity {

    TextView myTeamTextView, opponentTextView, gameDayTextView;
    Button simulateButton;

    private final Map<String, List<String>> leagueTeams = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameporcces);

        myTeamTextView = findViewById(R.id.tvMyTeam);
        opponentTextView = findViewById(R.id.tvOpponentTeam);
        gameDayTextView = findViewById(R.id.tvGameDay);
        simulateButton = findViewById(R.id.btnSimulate);

        initializeLeagues();

        String myTeam = getIntent().getStringExtra("myTeam");

        if (myTeam != null) {
            myTeamTextView.setText(" My Team: " + myTeam);
            String opponent = getFirstAlphabeticalOpponent(myTeam);
            opponentTextView.setText("Opponent: " + opponent);

            simulateButton.setOnClickListener(v -> {
                Intent intent = new Intent(gameporcces.this, simulate.class);
                intent.putExtra("myTeam", myTeam);
                intent.putExtra("opponentTeam", opponent);
                startActivity(intent);
            });
        }
    }

    private void initializeLeagues() {
        leagueTeams.put("Premier League", Arrays.asList(
                "Arsenal", "Aston Villa", "Bournemouth", "Brentford", "Brighton & Hove Albion", "Burnley",
                "Chelsea", "Crystal Palace", "Everton", "Fulham", "Liverpool", "Luton Town", "Manchester City",
                "Manchester United", "Newcastle United", "Nottingham Forest", "Sheffield United",
                "Tottenham Hotspur", "West Ham United", "Wolverhampton Wanderers"));

        leagueTeams.put("La Liga", Arrays.asList(
                "Alaves", "Almería", "Athletic Club", "Atlético Madrid", "Barcelona", "Cádiz", "Celta Vigo",
                "Getafe", "Girona", "Granada", "Las Palmas", "Mallorca", "Osasuna", "Rayo Vallecano",
                "Real Betis", "Real Madrid", "Real Sociedad", "Sevilla", "Valencia", "Villarreal"));

        leagueTeams.put("Serie A", Arrays.asList(
                "Atalanta", "Bologna", "Cagliari", "Empoli", "Fiorentina", "Frosinone", "Genoa", "Inter Milan",
                "Juventus", "Lazio", "Lecce", "Milan", "Monza", "Napoli", "Roma", "Salernitana",
                "Sassuolo", "Torino", "Udinese", "Verona"));

        leagueTeams.put("Bundesliga", Arrays.asList(
                "Augsburg", "Bayer Leverkusen", "Bayern Munich", "Bochum", "Borussia Dortmund",
                "Borussia Mönchengladbach", "Darmstadt", "Eintracht Frankfurt", "Freiburg", "Heidenheim",
                "Hoffenheim", "Köln", "Mainz", "RB Leipzig", "Stuttgart", "Union Berlin",
                "Werder Bremen", "Wolfsburg", "Hamburger SV", "Hertha BSC"));

        leagueTeams.put("Ligue 1", Arrays.asList(
                "Ajaccio", "Angers", "Brest", "Clermont", "Le Havre", "Lens", "Lille", "Lorient", "Lyon", "Marseille",
                "Metz", "Monaco", "Montpellier", "Nantes", "Nice", "Paris Saint-Germain", "Reims", "Rennes",
                "Strasbourg", "Toulouse"));
    }

    private String getFirstAlphabeticalOpponent(String myTeam) {
        for (Map.Entry<String, List<String>> entry : leagueTeams.entrySet()) {
            List<String> teams = new ArrayList<>(entry.getValue());
            if (teams.contains(myTeam)) {
                Collections.sort(teams);
                for (String team : teams) {
                    if (!team.equals(myTeam)) {
                        return team;
                    }
                }
            }
        }
        return "No Opponent Found";
    }
}
