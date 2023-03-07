package de.app.rentalkiezapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.regex.Pattern;

import de.app.rentalkiezapp.R;
import de.app.rentalkiezapp.database.DatabaseHelperRentables;
import de.app.rentalkiezapp.entity.RentObject;

public class RentActivity extends AppCompatActivity {

    private ImageButton btnback, btnlogout;
    private Button btngo;
    private EditText editTextUserInput;
    private Spinner spinnerMenue;

    private ArrayList<RentObject> listRentObjects;

    private DatabaseHelperRentables databaseHelperRentables;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        listRentObjects=new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_layout);

        btnback = (ImageButton) findViewById(R.id.btnback);
        btnlogout = (ImageButton) findViewById(R.id.btnlogout);
        btngo = findViewById(R.id.btngo);
        editTextUserInput = findViewById(R.id.editTextUserInput);
        spinnerMenue = findViewById(R.id.spinnerFilter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.disctricts_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        btnback.setOnClickListener(new MyListener());
        btnlogout.setOnClickListener(new MyListener());
        btngo.setOnClickListener(new MyListener());
    }

    private void goToRentResultsActivity(){
        Intent goToRentResults = new Intent(RentActivity.this, RentResultsActivity.class);
        goToRentResults.putExtra("listRentObjects", listRentObjects);
        startActivity(goToRentResults);
    }

    public class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if (view.getId()==R.id.btnlogout){
                if (view.getId() == R.id.btnlogout) {
                    FirebaseAuth.getInstance().signOut(); //logout
                    Intent goToLogin = new Intent(RentActivity.this, LoginActivity.class); //go to LoginActivity
                    goToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); //make it impossible to return to previous Activity
                    startActivity(goToLogin);
                    finish();
            }
            else if(view.getId()==R.id.btnback){
                finish();
            }

            else if(view.getId()==R.id.btngo){
                String input = editTextUserInput.getText().toString().trim();

                //check input for validity and kind (zipcode or text)
                if (Pattern.matches("[0-9]+",input)) {
                    if(input.length() == 5){
                        int zipcode = Integer.parseInt(input);
                        //search for RentObjects filtered by zipcode
                        databaseHelperRentables = new DatabaseHelperRentables(RentActivity.this);
                        listRentObjects=databaseHelperRentables.getZipcodeSearchEntries(zipcode);
                        if(listRentObjects!=null){
                            goToRentResultsActivity();
                        }
                        else{
                            btngo.setError("No results. Try something else!");

                        }
                    }
                    else{
                        //Toast if zipcode doesn´t contain 5 numbers
                        Toast.makeText(RentActivity.this, "zipcode must hava 5 numbers", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(Pattern.matches("[a-zA-Z]+", input) && input.length()>2){
                    //search for RentObjects filtered by RentObject Title
                        databaseHelperRentables = new DatabaseHelperRentables(RentActivity.this);
                        listRentObjects = databaseHelperRentables.getTextSearchEntries(input);

                        goToRentResultsActivity();
                }
                else{
                    //Toast if input length is sub 3 or both numbers and letters where typed in
                    Toast.makeText(RentActivity.this, "input min. 3 letters or full zipcode", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}