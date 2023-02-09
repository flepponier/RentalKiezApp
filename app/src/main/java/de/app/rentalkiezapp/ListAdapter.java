package de.app.rentalkiezapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;


import de.app.rentalkiezapp.entity.RentObject;

public class ListAdapter extends ArrayAdapter<RentObject> {


    public ListAdapter(Context context, ArrayList<RentObject> rentObjects){

        super(context, R.layout.list_item,rentObjects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        RentObject rentObject = getItem(position);

        if (convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);

        }

        ImageView imageViewRentObject = convertView.findViewById(R.id.imageViewRentObjec);
        ImageView imageViewAvailable = convertView.findViewById(R.id.imageViewAvailable);
        TextView textViewTitle = convertView.findViewById(R.id.title);
        TextView textViewState = convertView.findViewById(R.id.state);

        imageViewRentObject.setImageResource(R.drawable.bohrmaschine);
        textViewTitle.setText(rentObject.getTitle());
        textViewState.setText(rentObject.getState());
        if(rentObject.getTaken()==true){
            imageViewAvailable.setImageResource(R.drawable.notavailable);
        }
        else{
            imageViewAvailable.setImageResource(R.drawable.available);
        }


        return convertView;
    }
}