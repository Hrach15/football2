package com.example.porcnakan;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.*;

public class seetable extends AppCompatActivity {

    static class TeamStanding {
        String name;
        int points;
        int gf;
        int ga;

        TeamStanding(String name, int points, int gf, int ga) {
            this.name = name;
            this.points = points;
            this.gf = gf;
            this.ga = ga;
        }

        int goalDifference() {
            return gf - ga;
        }
    }

    private final Map<String, List<String>> leagues = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seetable);

        TextView tvTable = findViewById(R.id.tvTable);
        SharedPreferences prefs = getSharedPreferences("LeagueTable", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        initLeagues();

        // Get match result from intent
        String team1 = getIntent().getStringExtra("team1");
        String team2 = getIntent().getStringExtra("team2");
        int goals1 = getIntent().getIntExtra("goals1", 0);
        int goals2 = getIntent().getIntExtra("goals2", 0);

        String leagueName = getLeagueFromTeam(team1);
        if (leagueName == null || !getLeagueFromTeam(team2).equals(leagueName)) {
            tvTable.setText("Error: Teams not in the same league.");
            return;
        }

        // Update stats for team1
        updateTeamStats(editor, prefs, team1, goals1, goals2);

        // Update stats for team2
        updateTeamStats(editor, prefs, team2, goals2, goals1);

        editor.apply();

        // Build updated table
        List<TeamStanding> standings = new ArrayList<>();
        for (String team : leagues.get(leagueName)) {
            int points = prefs.getInt(team + "_points", 0);
            int gf = prefs.getInt(team + "_gf", 0);
            int ga = prefs.getInt(team + "_ga", 0);
            standings.add(new TeamStanding(team, points, gf, ga));
        }

        // Sort by points, then goal difference
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            standings.sort((a, b) -> {
                if (b.points != a.points) return b.points - a.points;
                return b.goalDifference() - a.goalDifference();
            });
        }

        // Build display
        StringBuilder table = new StringBuilder();
        table.append("League: ").append(leagueName).append("\n\n");
        table.append(String.format("%-3s %-20s %-5s %-5s %-5s %-5s\n", "#", "Team", "Pts", "GF", "GA", "GD"));
        table.append("-----------------------------------------------------\n");

        int rank = 1;
        for (TeamStanding team : standings) {
            table.append(String.format(
                    "%-3d %-20s %-5d %-5d %-5d %-5d\n",
                    rank++, team.name, team.points, team.gf, team.ga, team.goalDifference()
            ));
        }

        tvTable.setText(table.toString());
    }

    private void updateTeamStats(SharedPreferences.Editor editor, SharedPreferences prefs,
                                 String team, int gf, int ga) {
        int oldPoints = prefs.getInt(team + "_points", 0);
        int oldGF = prefs.getInt(team + "_gf", 0);
        int oldGA = prefs.getInt(team + "_ga", 0);

        int newPoints = oldPoints;
        if (gf > ga) newPoints += 3;
        else if (gf == ga) newPoints += 1;

        editor.putInt(team + "_points", newPoints);
        editor.putInt(team + "_gf", oldGF + gf);
        editor.putInt(team + "_ga", oldGA + ga);
    }

    private void initLeagues() {
        leagues.put("Premier League", Arrays.asList("Arsenal", "Aston Villa", "Bournemouth", "Brentford", "Brighton", "Burnley", "Chelsea", "Crystal Palace", "Everton", "Fulham", "Liverpool", "Luton Town", "Manchester City", "Manchester United", "Newcastle United", "Nottingham Forest", "Sheffield United", "Tottenham Hotspur", "West Ham", "Wolves"));
        leagues.put("La Liga", Arrays.asList("Alaves", "Almeria", "Athletic Bilbao", "Atletico Madrid", "Barcelona", "Cadiz", "Celta Vigo", "Getafe", "Girona", "Granada", "Las Palmas", "Mallorca", "Osasuna", "Rayo Vallecano", "Real Betis", "Real Madrid", "Real Sociedad", "Sevilla", "Valencia", "Villarreal"));
        leagues.put("Bundesliga", Arrays.asList("Augsburg", "Bayer Leverkusen", "Bayern Munich", "Bochum", "Borussia Dortmund", "Borussia Monchengladbach", "Darmstadt", "Eintracht Frankfurt", "Freiburg", "Heidenheim", "Hoffenheim", "Koln", "Mainz", "RB Leipzig", "Schalke 04", "Stuttgart", "Union Berlin", "Werder Bremen", "Wolfsburg", "Hertha BSC"));
        leagues.put("Serie A", Arrays.asList("Milan", "Atalanta", "Bologna", "Cagliari", "Empoli", "Fiorentina", "Frosinone", "Genoa", "Inter Milan", "Juventus", "Lazio", "Lecce", "Monza", "Napoli", "Roma", "Salernitana", "Sassuolo", "Spezia", "Torino", "Udinese"));
        leagues.put("Ligue 1", Arrays.asList("Angers", "Auxerre", "Brest", "Clermont", "Lens", "Lille", "Lorient", "Lyon", "Marseille", "Metz", "Monaco", "Montpellier", "Nantes", "Nice", "Paris Saint-Germain", "Reims", "Rennes", "Strasbourg", "Toulouse", "Troyes"));
    }

    private String getLeagueFromTeam(String team) {
        for (Map.Entry<String, List<String>> entry : leagues.entrySet()) {
            if (entry.getValue().contains(team)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
