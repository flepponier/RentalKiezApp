package de.app.rentalkiezapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.app.rentalkiezapp.R;
import de.app.rentalkiezapp.database.DataSourceRegistry;
import de.app.rentalkiezapp.entity.User;

public class RegisterActivity extends AppCompatActivity {
    String firstname, lastname;


    private ImageButton btnback;
    private EditText editTextPersonName,editTextEmail,editTextPassword,editTextStreet,editTextZipcode,editTextTown;
    private Button btnregister;
    private  ProgressBar progressBar;

    DataSourceRegistry dataSourceRegistry;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);


        btnback =(ImageButton) findViewById(R.id.btnback);
        btnregister=(Button) findViewById(R.id.btnregister);

        editTextPersonName = (EditText) findViewById(R.id.editTextPersonName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextStreet = (EditText) findViewById(R.id.editTextStreet);
        editTextZipcode = (EditText) findViewById(R.id.editTextZipcode);
        editTextTown = (EditText) findViewById(R.id.editTextTown);

        progressBar=(findViewById(R.id.progressBar2));

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = editTextPersonName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String street = editTextStreet.getText().toString().trim();
                String zipcode = editTextZipcode.getText().toString().trim();
                String town = editTextTown.getText().toString().trim();

                if(TextUtils.isEmpty(fullName)) {
                    editTextPersonName.setError("Full name is required");
                    return;
                }
                else if(TextUtils.isEmpty(email)) {
                    editTextEmail.setError("Email is required.");
                    return;
                }
                else if(!TextUtils.isEmpty(email)) {
                    if(isEmailValid(email)==false) {
                        editTextEmail.setError("Invalid email.");
                        return;
                    }
                }
                else if(TextUtils.isEmpty(password)) {
                    editTextPassword.setError("Password is required.");
                    return;
                }
                else if(password.length()<6) {
                    editTextPassword.setError("Password must be >5");
                    return;
                }
                else if(TextUtils.isEmpty(street)) {
                    editTextStreet.setError("Street is required.");
                    return;
                }
                else if(TextUtils.isEmpty(zipcode)) {
                    editTextZipcode.setError("Street is required.");
                    return;
                }
                else if(TextUtils.isEmpty(town)) {
                    editTextTown.setError("Town is required.");
                    return;
                }

                //split up fullname into firsname and lastname
                int index = fullName.indexOf(' ');
                if (index == -1) {
                        // no space found
                        return;
                } else {
                    firstname  = fullName.substring(0, index);
                    lastname = fullName.substring(index+1);
                }
                //cast zipcode to Integer
                int zipcodeInt=Integer.parseInt(zipcode);
                progressBar.setVisibility(View.VISIBLE);

                User user = new User(email, firstname, lastname, street, zipcodeInt, town);

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){


                                Toast.makeText(RegisterActivity.this, "User created.", Toast.LENGTH_SHORT).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dataSourceRegistry =new DataSourceRegistry(RegisterActivity.this);
                                        boolean success= dataSourceRegistry.addUser(user);
                                        progressBar.setVisibility(View.GONE);
                                        finish(); // This will switch to the previous activity after a short delay
                                    }
                                }, 1000); // Delay for 1000 milliseconds (1 second)



                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "User could not be registered properly.", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private static boolean isEmailValid(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}