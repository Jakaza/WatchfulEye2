package ac.za.watchfuleye;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LearnmoreThird extends AppCompatActivity {

    TextView homePage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learnmore_third);

        homePage = findViewById(R.id.learnRedirectText);


        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LoginActivity
                Intent intent = new Intent(LearnmoreThird.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}