package ac.za.watchfuleye;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.logsignsqlpractice.databinding.ActivitySignupBinding;


public class RegisterActivity extends AppCompatActivity {
    Button signupButton;

    TextView loginRedirectText;

    EditText signupEmail, signupPassword , signupConfirm , signupName , signupSurname , signupIdNumber, signupAddress ;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupConfirm = findViewById(R.id.signup_confirm);
        signupName = findViewById(R.id.signup_name);
        signupSurname = findViewById(R.id.signup_surname);
        signupIdNumber = findViewById(R.id.signup_id_number);
        signupAddress = findViewById(R.id.signup_address);


        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.signup_button);

        databaseHelper = new DatabaseHelper(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = signupEmail.getText().toString();
                String password = signupPassword.getText().toString();
                String confirmPassword = signupConfirm.getText().toString();
                String firstname = signupName.getText().toString();
                String surname = signupSurname.getText().toString();
                String idNumber = signupIdNumber.getText().toString();
                String address = signupAddress.getText().toString();

                if(address.equals("")||firstname.equals("")||surname.equals("")||idNumber.equals("") || email.equals("")||password.equals("")||confirmPassword.equals(""))
                    Toast.makeText(RegisterActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                else{
                    if(password.equals(confirmPassword)){
                        Boolean checkUserEmail = databaseHelper.checkEmail(email);

                        if(checkUserEmail == false){
                            Boolean insert = databaseHelper.insertData(email, password, firstname, surname ,  idNumber , address );

                            if(insert == true){
                                Toast.makeText(RegisterActivity.this, "Signup Successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(RegisterActivity.this, "Signup Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "User already exists! Please login", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this, "Invalid Password!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}