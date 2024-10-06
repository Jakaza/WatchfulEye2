package ac.za.watchfuleye;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class EmailValidationActivity extends AppCompatActivity {

    private EditText verifyEmail;
    private ProgressBar progressBar;
    private Button loginButton;
    private TextView output;
    private EmailValidator emailValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_validation);

        // Initialize UI components
        verifyEmail = findViewById(R.id.verify_email);
        progressBar = findViewById(R.id.progressBar);
        loginButton = findViewById(R.id.login_button);
        output = findViewById(R.id.output);

        emailValidator = new EmailValidator();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = verifyEmail.getText().toString().trim();

                if (email.isEmpty()) {
                    output.setText("Please enter an email address.");
                    return;
                }

                // Show progress bar
                progressBar.setVisibility(View.VISIBLE);
                output.setText(""); // Clear previous output

                // Call email validation
                emailValidator.checkEmailWithChatGPT(email, new EmailValidator.EmailValidationCallback() {
                    @Override
                    public void onSuccess(boolean isValid) {
                        runOnUiThread(() -> {
                            progressBar.setVisibility(View.GONE); // Hide progress bar

                            if (email.isEmpty()) {
                                output.setText("Please enter an email address.");
                                output.setTextColor(getResources().getColor(R.color.red));
                            } else if (!validateEmailCharacters(email)) {
                                output.setText("Invalid email format. Please enter a valid email.");
                                output.setTextColor(getResources().getColor(R.color.red));
                            } else if (isValid) {
                                String companyName = getCompanyNameByDomain(email);
                                output.setText("Email is valid.\nCompany: " + companyName);
                                output.setTextColor(getResources().getColor(R.color.green));
                            } else {
                                output.setText("Email is invalid.");
                                output.setTextColor(getResources().getColor(R.color.red));
                            }
                        });
                    }

                    @Override
                    public void onFailure(Exception e) {
                        runOnUiThread(() -> {
                            progressBar.setVisibility(View.GONE); // Hide progress bar
                            output.setText("Error: " + e.getMessage());
                        });
                    }
                });
            }
        });
    }

    private boolean validateEmailCharacters(String email){
        boolean validationStatus = false;
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        if (email != null && email.matches(emailRegex)){
            return  true;
        }
        return  validationStatus;
    }

    public static String getCompanyNameByDomain(String email) {
        // Map to hold domain and corresponding company names
        Map<String, String> domainMap = new HashMap<>();
        domainMap.put("gmail.com", "Google");
        domainMap.put("yahoo.com", "Yahoo");
        domainMap.put("outlook.com", "Microsoft");
        domainMap.put("hotmail.com", "Microsoft");
        domainMap.put("aol.com", "AOL");
        domainMap.put("icloud.com", "Apple");
        domainMap.put("protonmail.com", "ProtonMail");
        domainMap.put("zoho.com", "Zoho Corporation");
        domainMap.put("mail.com", "1&1 Mail & Media");
        domainMap.put("yandex.com", "Yandex");
        domainMap.put("tutanota.com", "Tutanota");
        domainMap.put("fastmail.com", "FastMail");
        domainMap.put("gmx.com", "GMX");
        domainMap.put("mail.ru", "Mail.Ru");
        domainMap.put("tut4life.com", "Tut4Life");
        domainMap.put("cox.net", "Cox Communications");
        domainMap.put("comcast.net", "Comcast");
        domainMap.put("verizon.net", "Verizon");
        domainMap.put("sbcglobal.net", "AT&T");
        domainMap.put("earthlink.net", "EarthLink");
        domainMap.put("inbox.com", "Inbox.com");

        // Extract the domain from the email
        String domain = getDomainFromEmail(email);

        // Return the company name associated with the domain
        return domainMap.getOrDefault(domain, "Unknown Company");
    }

    private static String getDomainFromEmail(String email) {
        if (email != null && email.contains("@")) {
            return email.substring(email.indexOf("@") + 1).toLowerCase();
        }
        return "";
    }
}
