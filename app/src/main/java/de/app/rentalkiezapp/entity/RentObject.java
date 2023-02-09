package de.app.rentalkiezapp.entity;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class RentObject implements Parcelable {
    int id;
    String  title, description, state, email;
    int imageBytes;
    Boolean taken;

    public RentObject(int id, String title, String description, String state, String email, int imageBytes, Boolean taken) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.state = state;
        this.email = email;
        this.imageBytes = imageBytes;
        this.taken = taken;
    }

    protected RentObject(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        state = in.readString();
        email = in.readString();
        imageBytes = in.readInt();
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(int imageBytes) {
        this.imageBytes = imageBytes;
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
                ", state='" + state + '\'' +
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
        dest.writeString(state);
        dest.writeString(email);
        dest.writeInt(imageBytes);
        dest.writeByte((byte) (taken == null ? 0 : taken ? 1 : 2));
    }
}


