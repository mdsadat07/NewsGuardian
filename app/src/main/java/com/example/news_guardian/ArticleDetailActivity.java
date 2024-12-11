package com.example.news_guardian;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class ArticleDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");
        String section = getIntent().getStringExtra("section");

        TextView titleTextView = findViewById(R.id.detail_title);
        TextView sectionTextView = findViewById(R.id.detail_section);
        TextView urlTextView = findViewById(R.id.detail_url);
        Button backButton = findViewById(R.id.back_button);

        titleTextView.setText(title);
        sectionTextView.setText(section);
        urlTextView.setText(url);

        // Open URL in a browser when clicked
        urlTextView.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });

        // Back button functionality
        backButton.setOnClickListener(v -> finish());
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
                .setMessage("1. View the details of the selected news article.\n" +
                            "2. Navigate back using the back button or toolbar.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
