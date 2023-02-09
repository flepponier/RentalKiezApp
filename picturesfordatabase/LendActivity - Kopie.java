package de.app.rentalkiezapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import de.app.rentalkiez.entity.RentObject;
import de.app.rentalkiezapp.R;

public class LendActivity extends AppCompatActivity {

    //contain  database column names
    private static final String KEY_UID = "users";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_CONDITION = "condition";
    private static final String KEY_BORROWED = "borrowed";

    private ArrayList<de.app.rentalkiez.entity.RentObject> rentObjects;


    private ImageButton back, logout;
    private ListView listView;

    private FirebaseFirestore firebaseFirestore;
    private DocumentReference documentReference;
    private CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lend_layout);

        rentObjects = new ArrayList<>();

        back = (ImageButton) findViewById(R.id.btnback);
        logout = (ImageButton) findViewById(R.id.btnlogout);
        //listView = findViewById(R.id.listView);

        //connect Firebase Database
        firebaseFirestore = FirebaseFirestore.getInstance();

        documentReference = firebaseFirestore.collection("users").document().collection("rentobject").document();
        collectionReference = firebaseFirestore.collection("users/rentobject");

        loadEntries();
        ArrayAdapter<de.app.rentalkiez.entity.RentObject> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rentObjects);
        listView.setAdapter(arrayAdapter);


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

    public void loadEntries() {
        collectionReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    RentObject singleRentable = documentSnapshot.toObject(RentObject.class);
                    rentObjects.add(singleRentable);
                    System.out.println(singleRentable);
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}