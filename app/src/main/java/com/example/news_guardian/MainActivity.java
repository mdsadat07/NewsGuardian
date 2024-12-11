package com.example.news_guardian;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArticleAdapter adapter;
    private EditText searchTerm;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize RecyclerView, EditText, and Button
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchTerm = findViewById(R.id.search_term);
        searchButton = findViewById(R.id.search_button);

        // Set up Search Button click listener
        searchButton.setOnClickListener(v -> {
            String query = searchTerm.getText().toString().trim();
            if (!query.isEmpty()) {
                fetchArticles(query);
            } else {
                Toast.makeText(MainActivity.this, "Please enter a search term", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchArticles(String query) {
        new Thread(() -> {
            try {
                String jsonResponse = ApiClient.fetchArticles(query);
                List<GuardianResponse.Article> articles = parseJsonResponse(jsonResponse);

                runOnUiThread(() -> {
                    if (articles.isEmpty()) {
                        Toast.makeText(MainActivity.this, "No results found", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter = new ArticleAdapter(MainActivity.this, articles);
                        recyclerView.setAdapter(adapter);
                    }
                });
            } catch (IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this, "Failed to fetch articles", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }

    private List<GuardianResponse.Article> parseJsonResponse(String jsonResponse) {
        List<GuardianResponse.Article> articles = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray results = jsonObject.getJSONObject("response").getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject articleObject = results.getJSONObject(i);
                String title = articleObject.getString("webTitle");
                String url = articleObject.getString("webUrl");
                String section = articleObject.getString("sectionName");

                articles.add(new GuardianResponse.Article(title, url, section));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu for Help
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_help) {
            // Show Help Dialog
            showHelpDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showHelpDialog() {
        new AlertDialog.Builder(this)
                .setTitle("How to Use")
                .setMessage("1. Use the search bar to find specific news articles.\n" +
                            "2. Tap on a news item to view detailed information.\n" +
                            "3. Use the back button or toolbar to navigate back.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
