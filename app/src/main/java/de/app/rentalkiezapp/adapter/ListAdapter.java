package de.app.rentalkiezapp.adapter;

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


import de.app.rentalkiezapp.R;
import de.app.rentalkiezapp.entity.RentObject;

public class ListAdapter extends ArrayAdapter<RentObject> {
    Context context;

    public ListAdapter(Context context, ArrayList<RentObject> rentObjects){

        super(context, R.layout.list_item,rentObjects);
        this.context=context;

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

        //set all Attributes: image, title, availabilty

            //get name of image saved in database
            //-----an alternative would be: get url or blob from db and set pictures accordingly-----
        String imageName= rentObject.getImageReference();
        int resourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        // Set the image resource of the ImageView
        imageViewRentObject.setImageResource(resourceId);

        textViewTitle.setText(rentObject.getTitle());
        if(rentObject.getTaken()==true){
            imageViewAvailable.setImageResource(R.drawable.notavailable);
        }
        else{
            imageViewAvailable.setImageResource(R.drawable.available);
        }


        return convertView;
    }
}