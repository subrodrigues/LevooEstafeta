package pt.levoo.courier.android.firebase.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by filiperodrigues on 31/08/16.
 */
public class FirebaseCourierClientModel implements Parcelable {
    private String name;
    private String address;

    public FirebaseCourierClientModel() {
    }

    protected FirebaseCourierClientModel(Parcel in) {
        name = in.readString();
        address = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static final Creator<FirebaseCourierClientModel> CREATOR = new Creator<FirebaseCourierClientModel>() {
        @Override
        public FirebaseCourierClientModel createFromParcel(Parcel in) {
            return new FirebaseCourierClientModel(in);
        }

        @Override
        public FirebaseCourierClientModel[] newArray(int size) {
            return new FirebaseCourierClientModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(address);
    }
}
