package de.app.rentalkiezapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class RentObject  implements Parcelable{
    int id;
    String  title, description, email;
    String imageReference;
    Boolean taken;

    public RentObject(int id, String title, String description,  String email, String imageReference, Boolean taken) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.email = email;
        this.imageReference = imageReference;
        this.taken = taken;
    }
    public RentObject(){}

    protected RentObject(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        email = in.readString();
        imageReference = in.readString();
        byte tmpTaken = in.readByte();
        taken = tmpTaken == 0 ? null : tmpTaken == 1;
    }


    public static final Creator<RentObject> CREATOR = new Creator<RentObject>() {
        @Override
        public RentObject createFromParcel(Parcel in) {
            return new RentObject(in);
        }

        @Override
        public RentObject[] newArray(int size) {
            return new RentObject[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageReference() {
        return imageReference;
    }

    public void setImageReference(String imageReference) {
        this.imageReference = imageReference;
    }

    public Boolean getTaken() {
        return taken;
    }

    public void setTaken(Boolean taken) {
        this.taken = taken;
    }

    @Override
    public String toString() {
        return "RentObject{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", taken=" + taken +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(email);
        dest.writeString(imageReference);
        dest.writeByte((byte) (taken == null ? 0 : taken ? 1 : 2));
    }
}


