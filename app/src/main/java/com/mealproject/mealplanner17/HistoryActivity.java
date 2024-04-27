package com.mealproject.mealplanner17;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mealproject.mealplanner17.Adapters.HistoryAdapter;

import java.util.ArrayList;
import java.util.Set;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView noHistoryText;
    private HistoryAdapter historyAdapter;
    private Set<String> clickedRecipeIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // the singleton class
        clickedRecipeIds = ClickedRecipeIdsSingleton.getInstance().getClickedRecipeIds();

        recyclerView = findViewById(R.id.history_recycler_view);
        noHistoryText = findViewById(R.id.no_history_text);


        ArrayList<String> clickedRecipeIdsFromIntent = getIntent().getStringArrayListExtra("clickedRecipeIds");

        //clickedRecipeIds from Singleton
        if (clickedRecipeIdsFromIntent != null) {
            clickedRecipeIds.addAll(clickedRecipeIdsFromIntent);
        }

        if (!clickedRecipeIds.isEmpty()) {
            // If there are clicked recipes
            noHistoryText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE); // Show recycler view


            historyAdapter = new HistoryAdapter(this, new ArrayList<>(clickedRecipeIds), recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(historyAdapter);
        } else {
            // no clicked recipes
            noHistoryText.setVisibility(View.VISIBLE); // Show no history message
            recyclerView.setVisibility(View.GONE); // Hide recycler view
        }
    }
}
