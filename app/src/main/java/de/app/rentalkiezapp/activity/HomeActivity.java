package de.app.rentalkiezapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

import de.app.rentalkiezapp.R;

public class HomeActivity extends AppCompatActivity {
    Button lend, rent;
    ImageButton logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        logout = (ImageButton) findViewById(R.id.btnlogout);
        rent = (Button) findViewById(R.id.btnrent);
        lend = (Button) findViewById(R.id.btnlend);

        MyListener myListener = new MyListener();

        logout.setOnClickListener(myListener);
        rent.setOnClickListener(myListener);
        lend.setOnClickListener(myListener);
    }
    public class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if (view.getId()==R.id.btnlogout){
                FirebaseAuth.getInstance().signOut(); //logout
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                finish();
            }
            else if(view.getId()==R.id.btnrent){
                //hier einbauen, dass Userdaten weitergereicht werden
                //gotoRent.putExtra("calculatedMwtS", calculatedMwtS);
                startActivity(new Intent(HomeActivity.this, RentActivity.class));
            }
            else if(view.getId()==R.id.btnlend){
                Intent gotoLend = new Intent(HomeActivity.this, LendActivity.class);
                //hier einbauen, dass Userdaten weitergereicht werden
                //gotoLend.putExtra("calculatedMwtS", calculatedMwtS);
                startActivity(gotoLend);
            }
        }
    }
}