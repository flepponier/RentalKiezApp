package de.app.rentalkiezapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import de.app.rentalkiezapp.R;


public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button btnlogin, btnregister;

    private FirebaseAuth mAuth;

    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        editTextEmail =(EditText) findViewById(R.id.editTextEmail);
        editTextPassword =(EditText) findViewById(R.id.editTextPassword);
        btnlogin =(Button) findViewById(R.id.btnlogin);
        btnregister =(Button) findViewById(R.id.btnregister);
        progressBar= findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkEmail = editTextEmail.getText().toString().trim();
                String checkPass = editTextPassword.getText().toString().trim();

                if(TextUtils.isEmpty(checkEmail)) {
                    editTextEmail.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(checkPass)) {
                    editTextPassword.setError("Password is Required.");
                    return;
                }
                if(checkPass.length()<6) {
                    editTextPassword.setError("Password must be >5");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(checkEmail, checkPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}
