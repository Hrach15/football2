package com.example.porcnakan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class simulate extends AppCompatActivity {

    TextView minuteTextView, eventTextView;
    private final Map<String, Integer> teamLevels = new HashMap<>();
    private final Map<String, List<String>> leagues = new HashMap<>();
    private final Handler handler = new Handler();
    private int gameMinute = 0;

    private int goalsTeam1 = 0;
    private int goalsTeam2 = 0;
    private String team1;
    private String team2;

    private int team1Level;
    private int team2Level;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulate);

        Button tableButton = findViewById(R.id.button);
        tableButton.setVisibility(View.GONE); // Hide button initially

        minuteTextView = findViewById(R.id.tvMinute);
        eventTextView = findViewById(R.id.tvResult); // ✅ fix here!



        tableButton.setOnClickListener(v -> {
            Toast.makeText(this, "Going to table!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(simulate.this, seetable.class);
            intent.putExtra("team1", team1);
            intent.putExtra("team2", team2);
            intent.putExtra("goals1", goalsTeam1);
            intent.putExtra("goals2", goalsTeam2);
            startActivity(intent);
        });


        String myTeam = getIntent().getStringExtra("myTeam");
        if (myTeam == null) {
            minuteTextView.setText("No team selected.");
            return;
        }

        initTeams();

        String league = getLeagueFromTeam(myTeam);
        if (league == null) {
            minuteTextView.setText("Team not found in any league.");
            return;
        }

        List<String> teams = new ArrayList<>(leagues.get(league));
        Collections.sort(teams);

        team1 = myTeam;
        team2 = getFirstAlphabeticalOpponent(teams, myTeam);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            team1Level = teamLevels.getOrDefault(team1, 75);
            team2Level = teamLevels.getOrDefault(team2, 75);
        }

        simulateMinuteByMinute();
    }

    private void simulateMinuteByMinute() {
        handler.postDelayed(() -> {
            gameMinute++;

            // Update minute display centered
            minuteTextView.setText("Minute: " + gameMinute);

            // Check for goals
            if (isGoalScored(team1Level)) {
                goalsTeam1++;
                eventTextView.append("\nMinute " + gameMinute + ": \u26BD " + team1 + " scores!\n");
            }
            if (isGoalScored(team2Level)) {
                goalsTeam2++;
                eventTextView.append("\nMinute " + gameMinute + ": \u26BD " + team2 + " scores!\n");
            }

            if (gameMinute < 90) {
                simulateMinuteByMinute();
            } else {
                eventTextView.append("\n\nFinal Score:\n" + team1 + " " + goalsTeam1 + " - " + goalsTeam2 + " " + team2);
                findViewById(R.id.button).setVisibility(View.VISIBLE);
            }
        }, 100); // 0.1 second per minute
    }

    private boolean isGoalScored(int rating) {
        double chance;
        if (rating > 90) chance = 3.0;
        else if (rating > 80) chance = 2.0;
        else if (rating > 70) chance = 1.5;
        else if (rating > 60) chance = 1.0;
        else chance = 0.5;

        return new Random().nextDouble() * 100 < chance;
    }

    private void initTeams() {
        leagues.put("Premier League", Arrays.asList("Arsenal", "Aston Villa", "Bournemouth", "Brentford", "Brighton", "Burnley", "Chelsea", "Crystal Palace", "Everton", "Fulham", "Liverpool", "Luton Town", "Manchester City", "Manchester United", "Newcastle United", "Nottingham Forest", "Sheffield United", "Tottenham Hotspur", "West Ham", "Wolves"));
        leagues.put("La Liga", Arrays.asList("Alaves", "Almeria", "Athletic Bilbao", "Atletico Madrid", "Barcelona", "Cadiz", "Celta Vigo", "Getafe", "Girona", "Granada", "Las Palmas", "Mallorca", "Osasuna", "Rayo Vallecano", "Real Betis", "Real Madrid", "Real Sociedad", "Sevilla", "Valencia", "Villarreal"));
        leagues.put("Bundesliga", Arrays.asList("Augsburg", "Bayer Leverkusen", "Bayern Munich", "Bochum", "Borussia Dortmund", "Borussia Monchengladbach", "Darmstadt", "Eintracht Frankfurt", "Freiburg", "Heidenheim", "Hoffenheim", "Koln", "Mainz", "RB Leipzig", "Schalke 04", "Stuttgart", "Union Berlin", "Werder Bremen", "Wolfsburg", "Hertha BSC"));
        leagues.put("Serie A", Arrays.asList("AC Milan", "Atalanta", "Bologna", "Cagliari", "Empoli", "Fiorentina", "Frosinone", "Genoa", "Inter Milan", "Juventus", "Lazio", "Lecce", "Monza", "Napoli", "Roma", "Salernitana", "Sassuolo", "Spezia", "Torino", "Udinese"));
        leagues.put("Ligue 1", Arrays.asList("Angers", "Auxerre", "Brest", "Clermont", "Lens", "Lille", "Lorient", "Lyon", "Marseille", "Metz", "Monaco", "Montpellier", "Nantes", "Nice", "Paris Saint-Germain", "Reims", "Rennes", "Strasbourg", "Toulouse", "Troyes"));

        Map<String, Integer> ratings = new HashMap<>();
        ratings.put("Arsenal", 89); ratings.put("Aston Villa", 82); ratings.put("Bournemouth", 74); ratings.put("Brentford", 76);
        ratings.put("Brighton", 79); ratings.put("Burnley", 70); ratings.put("Chelsea", 83); ratings.put("Crystal Palace", 75);
        ratings.put("Everton", 74); ratings.put("Fulham", 73); ratings.put("Liverpool", 90); ratings.put("Luton Town", 68);
        ratings.put("Manchester City", 95); ratings.put("Manchester United", 84); ratings.put("Newcastle United", 82);
        ratings.put("Nottingham Forest", 71); ratings.put("Sheffield United", 69); ratings.put("Tottenham Hotspur", 85);
        ratings.put("West Ham", 78); ratings.put("Wolves", 74);

        ratings.put("Real Madrid", 94); ratings.put("Barcelona", 92); ratings.put("Atletico Madrid", 86); ratings.put("Sevilla", 80);
        ratings.put("Real Sociedad", 82); ratings.put("Real Betis", 80); ratings.put("Villarreal", 81); ratings.put("Valencia", 79);
        ratings.put("Athletic Bilbao", 78); ratings.put("Getafe", 74); ratings.put("Osasuna", 75); ratings.put("Rayo Vallecano", 74);
        ratings.put("Mallorca", 73); ratings.put("Celta Vigo", 75); ratings.put("Cadiz", 72); ratings.put("Las Palmas", 71);
        ratings.put("Granada", 70); ratings.put("Girona", 76); ratings.put("Almeria", 69); ratings.put("Alaves", 70);

        ratings.put("Bayern Munich", 93); ratings.put("Borussia Dortmund", 86); ratings.put("RB Leipzig", 85); ratings.put("Union Berlin", 79);
        ratings.put("Freiburg", 78); ratings.put("Eintracht Frankfurt", 80); ratings.put("Bayer Leverkusen", 87); ratings.put("Mainz", 74);
        ratings.put("Hoffenheim", 76); ratings.put("Stuttgart", 75); ratings.put("Werder Bremen", 73); ratings.put("Wolfsburg", 78);
        ratings.put("Augsburg", 71); ratings.put("Bochum", 70); ratings.put("Darmstadt", 68); ratings.put("Heidenheim", 69);
        ratings.put("Borussia Monchengladbach", 76); ratings.put("Koln", 72); ratings.put("Schalke 04", 72); ratings.put("Hertha BSC", 71);

        ratings.put(" Milan", 87); ratings.put("Inter Milan", 88); ratings.put("Juventus", 88); ratings.put("Napoli", 89);
        ratings.put("Roma", 84); ratings.put("Lazio", 83); ratings.put("Atalanta", 82); ratings.put("Fiorentina", 79);
        ratings.put("Bologna", 77); ratings.put("Monza", 75); ratings.put("Empoli", 72); ratings.put("Torino", 76);
        ratings.put("Udinese", 75); ratings.put("Sassuolo", 74); ratings.put("Salernitana", 70); ratings.put("Lecce", 73);
        ratings.put("Frosinone", 71); ratings.put("Genoa", 74); ratings.put("Cagliari", 72); ratings.put("Spezia", 70);

        ratings.put("Paris Saint-Germain", 91); ratings.put("Marseille", 83); ratings.put("Monaco", 82); ratings.put("Lille", 81);
        ratings.put("Lyon", 79); ratings.put("Nice", 80); ratings.put("Rennes", 80); ratings.put("Reims", 76); ratings.put("Lens", 82);
        ratings.put("Strasbourg", 74); ratings.put("Toulouse", 73); ratings.put("Montpellier", 75); ratings.put("Brest", 74);
        ratings.put("Nantes", 73); ratings.put("Lorient", 72); ratings.put("Clermont", 70); ratings.put("Metz", 71);
        ratings.put("Auxerre", 69); ratings.put("Angers", 68); ratings.put("Troyes", 67); ratings.put("Le Havre", 70);

        teamLevels.putAll(ratings);
    }

    private String getLeagueFromTeam(String team) {
        for (Map.Entry<String, List<String>> entry : leagues.entrySet()) {
            if (entry.getValue().contains(team)) return entry.getKey();
        }
        return null;
    }

    private String getFirstAlphabeticalOpponent(List<String> sortedTeams, String myTeam) {
        for (String team : sortedTeams) {
            if (!team.equals(myTeam)) return team;
        }
        return myTeam; // fallback
    }
}
