package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.porcnakan.R;
import com.example.porcnakan.teamchoose;

import java.util.ArrayList;
import java.util.List;

public class table extends AppCompatActivity {
private Button next;
    private TextView teamNameTextView;
    private ListView matchListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        teamNameTextView = findViewById(R.id.teamNameTextView);
        matchListView = findViewById(R.id.matchListView);
        next = findViewById(R.id.button);

        // Get the selected team from the intent
        String selectedTeam = getIntent().getStringExtra("selectedTeam");
        teamNameTextView.setText("Team: " + selectedTeam);


        List<String> matchList = generateMatchList(selectedTeam);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, matchList);
        matchListView.setAdapter(adapter);

        next.setOnClickListener(v -> {
            Intent intent = new Intent(table.this, MainScreen.class);
            startActivity(intent);
        });
    }

    private List<String> generateMatchList(String selectedTeam) {
        List<String> matches = new ArrayList<>();
        for (int i = 1; i <= 38; i++) {
            matches.add("Match " + i + ": " + selectedTeam + " vs Opponent " + i);
        }
        return matches;
    }
}
