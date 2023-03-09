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
import de.app.rentalkiezapp.database.DataSourceRentables;
import de.app.rentalkiezapp.entity.RentObject;
import de.app.rentalkiezapp.entity.areaTOzipcode;

public class RentActivity extends AppCompatActivity {

    private ImageButton btnback, btnlogout;
    private Button btngo;
    private EditText editTextUserInput;
    private Spinner spinnerMenue;

    private ArrayList<RentObject> listRentObjects;

    private DataSourceRentables databaseHelperRentables;


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

    private void goToRentResultsActivityIfListNotNull(){
        if (listRentObjects==null) {
            btngo.setError("Invalid Input");
            Toast.makeText(RentActivity.this, "No results. Try something else!", Toast.LENGTH_SHORT).show();
        } else {
            Intent goToRentResults = new Intent(RentActivity.this, RentResultsActivity.class);
            goToRentResults.putExtra("listRentObjects", this.listRentObjects);
            startActivity(goToRentResults);
        }
    }

    public class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if (view.getId()==R.id.btnlogout){
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


                if(input.equals("")){
                    //get input from DropDownMenue
                    String selection = spinnerMenue.getSelectedItem().toString();
                    if(selection.equals("...")){
                        btngo.setError("Invalid Input");
                        Toast.makeText(RentActivity.this, "Select area or search something.", Toast.LENGTH_SHORT).show();
                    }else{
                        btngo.setError(null);
                        String [] args = areaTOzipcode.get_query_for_zipcode_range_dependent_on_area(selection);
                        databaseHelperRentables = new DataSourceRentables(RentActivity.this);
                        listRentObjects=databaseHelperRentables.getSpinnerEntries(args);
                        goToRentResultsActivityIfListNotNull();
                        return;
                    }
                }
                else {
                    //check input for validity and kind (zipcode or text)
                    if (Pattern.matches("[0-9]+", input)) {
                        if (input.length() == 5) {
                            btngo.setError(null);
                            int zipcode = Integer.parseInt(input);
                            //search for RentObjects filtered by zipcode
                            databaseHelperRentables = new DataSourceRentables(RentActivity.this);
                            listRentObjects = databaseHelperRentables.getZipcodeSearchEntries(zipcode);
                            goToRentResultsActivityIfListNotNull();
                        } else {
                            //Toast if zipcode doesn´t contain 5 numbers
                            btngo.setError("Invalid Input");
                            Toast.makeText(RentActivity.this, "zipcode must hava 5 numbers.", Toast.LENGTH_SHORT).show();
                        }
                    } else if (Pattern.matches("[a-zA-Z]+", input) && input.length() > 2) {
                        btngo.setError(null);
                        //search for RentObjects filtered by title of RentObject
                        databaseHelperRentables = new DataSourceRentables(RentActivity.this);
                        listRentObjects = databaseHelperRentables.getTextSearchEntries(input);
                        goToRentResultsActivityIfListNotNull();
                    } else {
                        //Toast if input length is sub 3 or both numbers and letters where typed in
                        btngo.setError("Invalid Input");
                        Toast.makeText(RentActivity.this, "input min. 3 letters or full zipcode.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }
    }
}