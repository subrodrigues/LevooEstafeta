package pt.levoo.courier.android.firebase.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by filiperodrigues on 31/08/16.
 */
public class FirebaseCourierStoreModel implements Parcelable {
    private String address;

    public FirebaseCourierStoreModel() {
    }

    protected FirebaseCourierStoreModel(Parcel in) {
        address = in.readString();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static final Creator<FirebaseCourierStoreModel> CREATOR = new Creator<FirebaseCourierStoreModel>() {
        @Override
        public FirebaseCourierStoreModel createFromParcel(Parcel in) {
            return new FirebaseCourierStoreModel(in);
        }

        @Override
        public FirebaseCourierStoreModel[] newArray(int size) {
            return new FirebaseCourierStoreModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(address);
    }
}
