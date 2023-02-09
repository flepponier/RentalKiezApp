package de.app.rentalkiezapp.activity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ImageButton;
import android.widget.ListView;




import java.util.ArrayList;

import de.app.rentalkiezapp.ListAdapter;
import de.app.rentalkiezapp.entity.RentObject;
import de.app.rentalkiezapp.R;

public class LendActivity extends AppCompatActivity {
    Connection conn;

    //contain  database column names
    private static final String KEY_UID = "users";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_CONDITION = "condition";
    private static final String KEY_BORROWED = "borrowed";

    private ArrayList<de.app.rentalkiezapp.entity.RentObject> rentObjects;


    private ImageButton back, logout;
    private ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lend_layout);

        rentObjects = new ArrayList<>();

        back = (ImageButton) findViewById(R.id.btnback);
        logout = (ImageButton) findViewById(R.id.btnlogout);
        listView = findViewById(R.id.listView);

        loadEntries();
        ListAdapter listAdapter = new ListAdapter(LendActivity.this, rentObjects);
        listView.setAdapter(listAdapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LendActivity.this, LendObjectActivity.class);
                intent.putExtra("RentObject", rentObjects.get(position));
                startActivity(intent);
            }
        });


    }

    public class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btnlogout) {
                finish();
                finish();
            } else if (view.getId() == R.id.btnrent) {
                finish();
            }
        }
    }


    public void loadEntries(){
        for(int i=0; i<10;i++){
            rentObjects.add(new RentObject(i, "Bohrmaschine", "Some description of my Object just for example, some more text to make it more realistic", "Sehr gut","someemailyuh@web.de", 23, false));
        }
    }

    /*
    public void loadEntries() {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT title, description, state, rented, email FROM rentables");
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String state = resultSet.getString("state");
                Boolean rented = resultSet.getBoolean("rented");
                String email = resultSet.getString("email");

                System.out.println(title+description+state+rented+email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    */

}