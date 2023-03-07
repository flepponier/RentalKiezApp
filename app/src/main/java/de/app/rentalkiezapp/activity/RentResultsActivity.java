package de.app.rentalkiezapp.activity;

import java.sql.Connection;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ImageButton;
import android.widget.ListView;


import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import de.app.rentalkiezapp.adapter.ListAdapter;
import de.app.rentalkiezapp.entity.RentObject;
import de.app.rentalkiezapp.R;

public class RentResultsActivity extends AppCompatActivity {

    private ArrayList<de.app.rentalkiezapp.entity.RentObject> rentObjects;

    private ImageButton back, logout;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_results_activity);

        rentObjects = new ArrayList<>();
        back = (ImageButton) findViewById(R.id.btnback);
        logout = (ImageButton) findViewById(R.id.btnlogout);
        listView = findViewById(R.id.listView);

        //set Listener for buttons
        back.setOnClickListener(new MyListener());
        logout.setOnClickListener(new MyListener());

        //save passed ArrayList from RentActivity
        rentObjects = getIntent().getParcelableArrayListExtra("listRentObjects");

        //show each Object of ArrayList
        ListAdapter listAdapter = new ListAdapter(RentResultsActivity.this, rentObjects);
        listView.setAdapter(listAdapter);

        //make objects of listView clickable and set Listener
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RentResultsActivity.this, RentObjectActivity.class);
                intent.putExtra("RentObject", rentObjects.get(position));
                startActivity(intent);
            }
        });


    }

    //Listener for buttons
    public class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btnlogout) {//start Logout-process
                FirebaseAuth.getInstance().signOut(); //logout
                Intent goToLogin = new Intent(RentResultsActivity.this, LoginActivity.class); //go to LoginActivity
                goToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); //make it impossible to return to previous Activity
                startActivity(goToLogin);
                finish();
            } else if (view.getId() == R.id.btnrent) {//go to previous page
                finish();
            }
        }
    }




}