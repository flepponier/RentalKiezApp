package de.app.rentalkiezapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import de.app.rentalkiezapp.R;
import de.app.rentalkiezapp.database.DataSourceRentables;
import de.app.rentalkiezapp.entity.RentObject;

public class LendObjectActivity extends AppCompatActivity {

    //declare necessary variables
    ImageButton btnback, btnlogout;
    TextView textViewTitle,textViewDescription,textViewEmail,textViewTaken;
    ImageView imageViewLendObject;
    CheckBox checkBox;
    DataSourceRentables databaseHelperRentables;
    int resourceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lend_object_layout);

        //connect buttons from lend_object_layout.xml
        btnback=findViewById(R.id.btnback);
        btnlogout=findViewById(R.id.btnlogout);

        imageViewLendObject=findViewById(R.id.imageViewLendObject);
        textViewTitle=findViewById(R.id.textViewTitle);
        textViewDescription=findViewById(R.id.textViewDescription);
        textViewEmail=findViewById(R.id.textViewEmail);
        textViewTaken=findViewById(R.id.textViewTaken);

        checkBox=findViewById(R.id.checkBox);

        //save RentObject passed from LendActivity.java
        RentObject rentObject = getIntent().getParcelableExtra("RentObject");

        String imageName= rentObject.getImageReference();
        resourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        // Set the image resource of the ImageView
        imageViewLendObject.setImageResource(resourceId);

        //set TextView contents from RentObject
        textViewTitle.setText(rentObject.getTitle());
        textViewDescription.setText(rentObject.getDescription());
        textViewEmail.setText(rentObject.getEmail());

        //set drawable depending on availability of RentObject
        if(rentObject.getTaken()==true) {
            checkBox.setChecked(false);
            textViewTaken.setText("Eintrag deaktiviert");
        }
        else{
            checkBox.setChecked(true);
            textViewTaken.setText("Eintrag aktiviert");
        }

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("updatedRentObject", rentObject);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();

                finish();
            }
        });

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut(); //logout
                Intent goToLogin = new Intent(LendObjectActivity.this, LoginActivity.class); //go to LoginActivity
                goToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); //make it impossible to return to previous Activity
                startActivity(goToLogin);
                finish();
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                databaseHelperRentables=new DataSourceRentables(LendObjectActivity.this);
               ;
                if(isChecked){
                    //RentObject ist aktiv -> Spalte taken in RENTALES_TABLE auf 0 setzen
                    databaseHelperRentables.updateTaken(rentObject.getId(), false);
                    rentObject.setTaken(false);
                    textViewTaken.setText("Eintrag aktiviert");

                }
                else{
                    //RentObject ist inaktiv (entweder verliehen oder Lender will nicht RentObject nicht mehr verleihen -> Spalte taken in RENTALES_TABLE auf 0 setzen
                    databaseHelperRentables.updateTaken(rentObject.getId(), true);
                    rentObject.setTaken(true);
                    textViewTaken.setText("Eintrag deaktiviert");
                }
            }
        });
    }
}