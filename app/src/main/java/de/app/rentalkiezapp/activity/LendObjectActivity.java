package de.app.rentalkiezapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import de.app.rentalkiezapp.R;
import de.app.rentalkiezapp.entity.RentObject;

public class LendObjectActivity extends AppCompatActivity {

    //declare necessary variables
    TextView textViewTitle,textViewDescription,textViewEmail, textViewState;
    ImageView imageViewObject,imageViewAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lendobject_layout);

        //connect buttons from lendobject_layout.xml
        textViewTitle=findViewById(R.id.textViewTitle);
        textViewDescription=findViewById(R.id.textViewDescription);
        textViewEmail=findViewById(R.id.textViewEmail);
        textViewState =findViewById(R.id.textViewState);
        imageViewObject=findViewById(R.id.imageViewObject);
        imageViewAvailable=findViewById(R.id.imageViewAvailable);

        //save RentObject passed from LendActivity.java
        RentObject rentObject = getIntent().getParcelableExtra("RentObject");

        //set TextView contents from RentObject
        textViewTitle.setText(rentObject.getTitle());
        textViewDescription.setText(rentObject.getDescription());
        textViewEmail.setText(rentObject.getEmail());
        textViewState.setText(rentObject.getState());

        //set drawable depending on availability of RentObject
        if(rentObject.getTaken()==true) {
            imageViewAvailable.setImageResource(R.drawable.notavailable);
        }

        //ausstehend: set imageViewObject und imageViewAvailable abhängig von state
    }
}