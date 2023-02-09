package de.app.rentalkiezapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import de.app.rentalkiezapp.R;

public class RentActivity extends AppCompatActivity {

    ImageButton back, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_layout);

        back = (ImageButton) findViewById(R.id.btnback);
        logout = (ImageButton) findViewById(R.id.btnlogout);
    }
    public class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if (view.getId()==R.id.btnlogout){
                finish();

            }
            else if(view.getId()==R.id.btnrent){
                finish();
            }
        }
    }
}