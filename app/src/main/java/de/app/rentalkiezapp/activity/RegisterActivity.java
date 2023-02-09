package de.app.rentalkiezapp.activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import de.app.rentalkiezapp.R;

public class RegisterActivity extends AppCompatActivity {
    private ImageButton btnback;
    private EditText editTextPersonName,editTextEmail,editTextPassword,editTextStreet,editTextZipcode,editTextTown;
    private Button btnregister;
    private  ProgressBar progressBar;

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

                if(TextUtils.isEmpty(email)) {
                    editTextEmail.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    editTextPassword.setError("Password is Required.");
                    return;
                }
                if(password.length()<6) {
                    editTextPassword.setError("Password must be >5");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User Created.", Toast.LENGTH_LONG).show();
                            userID = mAuth.getCurrentUser().getUid(); //save userID of new User
                            DocumentReference documentReference = mFirestore.collection("users").document(userID); //create Collection "users" with userID
                            Map<String,Object> user = new HashMap<>();//create Data with Hashmap
                            user.put("email", email);
                            user.put("fName", fullName);
                            user.put("street", street);
                            user.put("zipcode", zipcode);
                            user.put("town", town);

                            //add user to Firebase and log success
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "onSuccess: user Profile is created for"+userID);
                                }
                            });

                            progressBar.setVisibility(View.GONE);
                            finish();
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Error!" + task.getException().getMessage(),Toast.LENGTH_LONG).show();
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
}