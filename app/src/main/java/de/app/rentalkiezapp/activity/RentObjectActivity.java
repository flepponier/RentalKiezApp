package de.app.rentalkiezapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import de.app.rentalkiezapp.R;
import de.app.rentalkiezapp.database.DataSourceRentables;
import de.app.rentalkiezapp.entity.RentObject;

public class RentObjectActivity extends AppCompatActivity {

    //declare necessary variables
    ImageButton btnback, btnlogout, btntaken;
    TextView textViewTitle,textViewDescription,textViewEmail, textViewState;
    ImageView imageViewLendObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_object_layout);

        //connect buttons from rent_object_layout.xml
        btnback=findViewById(R.id.btnback);
        btnlogout=findViewById(R.id.btnlogout);
        btntaken=findViewById(R.id.btntaken);
        imageViewLendObject=findViewById(R.id.imageViewLendObject);
        textViewTitle=findViewById(R.id.textViewTitle);
        textViewDescription=findViewById(R.id.textViewDescription);
        textViewEmail=findViewById(R.id.textViewEmail);

        //save RentObject passed from LendActivity.java
        RentObject rentObject = getIntent().getParcelableExtra("RentObject");

        //get id for correct drawable
        String imageName= rentObject.getImageReference();
        int resourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        // Set the image resource of the ImageView
        imageViewLendObject.setImageResource(resourceId);

        //set TextView contents from RentObject
        textViewTitle.setText(rentObject.getTitle());
        textViewDescription.setText(rentObject.getDescription());
        textViewEmail.setText(rentObject.getEmail());


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut(); //logout
                Intent goToLogin = new Intent(RentObjectActivity.this, LoginActivity.class); //go to LoginActivity
                goToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); //make it impossible to return to previous Activity
                startActivity(goToLogin);
                finish();
            }
        });
        btntaken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RentObjectActivity.this, "Hurray! The thing is available", Toast.LENGTH_SHORT).show();
            }
        });

    }
}