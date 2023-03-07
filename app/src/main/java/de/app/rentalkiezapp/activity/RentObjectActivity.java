package de.app.rentalkiezapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import de.app.rentalkiezapp.R;

public class RentObjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_object_activity);
    }

public class MyListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnlogout) {
            Intent intent = new Intent(RentObjectActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if (view.getId() == R.id.btnback) {
            finish();
        }
    }
}
}
