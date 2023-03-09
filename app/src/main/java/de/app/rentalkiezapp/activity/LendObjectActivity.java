package de.app.rentalkiezapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import de.app.rentalkiezapp.R;
import de.app.rentalkiezapp.database.DataSourceRentables;
import de.app.rentalkiezapp.entity.RentObject;

public class LendObjectActivity extends AppCompatActivity {

    //declare necessary variables
    ImageButton btnback;
    TextView textViewTitle,textViewDescription,textViewEmail;
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
        imageViewLendObject=findViewById(R.id.imageViewLendObject);
        textViewTitle=findViewById(R.id.textViewTitle);
        textViewDescription=findViewById(R.id.textViewDescription);
        textViewEmail=findViewById(R.id.textViewEmail);
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
            checkBox.setText("Eintrag deaktiviert");
        }
        else{
            checkBox.setChecked(true);
            checkBox.setText("Eintrag aktiviert");
        }

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                }
                else{
                    //RentObject ist inaktiv (entweder verliehen oder Lender will nicht RentObject nicht mehr verleihen -> Spalte taken in RENTALES_TABLE auf 0 setzen
                    databaseHelperRentables.updateTaken(rentObject.getId(), true);
                    rentObject.setTaken(true);
                }
            }
        });
    }
}