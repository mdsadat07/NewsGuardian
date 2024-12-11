package com.example.news_guardian;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvSection = findViewById(R.id.tv_section);
        Button btnOpenUrl = findViewById(R.id.btn_open_url);
        Button btnSave = findViewById(R.id.btn_save_favorites);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String section = intent.getStringExtra("section");
        String url = intent.getStringExtra("url");

        tvTitle.setText(title);
        tvSection.setText(section);

        btnOpenUrl.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });

        btnSave.setOnClickListener(v -> {
            // Add code to save the article to SharedPreferences or SQLite
            Toast.makeText(this, "Article saved to favorites!", Toast.LENGTH_SHORT).show();
        });
    }
}
