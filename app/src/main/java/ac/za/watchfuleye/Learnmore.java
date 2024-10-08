package ac.za.watchfuleye;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Learnmore extends AppCompatActivity {

    Button nextPage;
    TextView homePage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learnmore);


        nextPage = findViewById(R.id.next_page);
        homePage = findViewById(R.id.learnRedirectText);

        // Set click listener for the Login button
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LoginActivity
                Intent intent = new Intent(Learnmore.this, LearnmoreSecond.class);
                startActivity(intent);
            }
        });

        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LoginActivity
                Intent intent = new Intent(Learnmore.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}