package de.app.rentalkiezapp.activity;

import java.sql.Connection;


import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ImageButton;
import android.widget.ListView;


import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import de.app.rentalkiezapp.adapter.ListAdapter;
import de.app.rentalkiezapp.database.DataSourceRentables;
import de.app.rentalkiezapp.entity.RentObject;
import de.app.rentalkiezapp.R;

public class LendActivity extends AppCompatActivity {
    Connection conn;

    //contain  database column names
    DataSourceRentables databaseHelperRentables;
    private String email;
    private ArrayList<RentObject> listRentObjects;
    private ListAdapter listAdapter;
    private ImageButton btnback, btnlogout;
    private ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lend_layout);

        listRentObjects = new ArrayList<>();
        email = getIntent().getStringExtra("email");


        btnback = (ImageButton) findViewById(R.id.btnback);
        btnlogout = (ImageButton) findViewById(R.id.btnlogout);
        listView = findViewById(R.id.listView);

        //set Listener for buttons
        btnback.setOnClickListener(new MyListener());
        btnlogout.setOnClickListener(new MyListener());

        //get RentObjects with matching email
        databaseHelperRentables =new DataSourceRentables(LendActivity.this);
        listRentObjects= databaseHelperRentables.getUserEntries(email);

        //show each Object of ArrayList
        listAdapter = new ListAdapter(LendActivity.this, listRentObjects);
        listView.setAdapter(listAdapter);

        //make objects of listView clickable and set Listener
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LendActivity.this, LendObjectActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("RentObject", listRentObjects.get(position));
                startActivity(intent);
            }
        });


    }

    //Listener for buttons
    public class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btnlogout) {//start Logout-process
                    FirebaseAuth.getInstance().signOut(); //logout User
                    Intent goToLogin = new Intent(LendActivity.this, LoginActivity.class); //go to LoginActivity
                    goToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); //make it impossible to return to previous Activity
                    startActivity(goToLogin);
                    finish();
            } else if (view.getId() == R.id.btnback) {//go to previous page
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            RentObject rentObject = data.getParcelableExtra("updatedRentObject");
            for(int i=0; i<listRentObjects.size();i++){
                if(rentObject.getId()==listRentObjects.get(i).getId()){
                    listRentObjects.get(i).setTaken(rentObject.getTaken());
                    return;
                }
            }
            listAdapter.notifyDataSetChanged();
        }
    }




}