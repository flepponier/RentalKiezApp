package de.app.rentalkiezapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.app.rentalkiezapp.R;
import de.app.rentalkiezapp.database.DataSourceRentables;
import de.app.rentalkiezapp.entity.RentObject;

public class RentObjectActivity extends AppCompatActivity {

    //declare necessary variables
    ImageButton btnback, btntaken;
    TextView textViewTitle,textViewDescription,textViewEmail, textViewState;
    ImageView imageViewLendObject;
    DataSourceRentables databaseHelperRentables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_object_layout);

        //connect buttons from rent_object_layout.xml
        btnback=findViewById(R.id.btnback);
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
        btntaken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RentObjectActivity.this, "Hurray! The thing is available", Toast.LENGTH_SHORT).show();
            }
        });

    }
}