package pt.levoo.courier.android.firebase.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by filiperodrigues on 24/05/16.
 */
public class FirebaseCourierOrderModel implements Parcelable{

    private String id;
    private String description;
    private FirebaseCourierClientModel client;
    private FirebaseCourierStoreModel store;

    public FirebaseCourierOrderModel() {
    }

    protected FirebaseCourierOrderModel(Parcel in) {
        id = in.readString();
        description = in.readString();
        client = in.readParcelable(FirebaseCourierClientModel.class.getClassLoader());
        store = in.readParcelable(FirebaseCourierStoreModel.class.getClassLoader());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FirebaseCourierClientModel getClient() {
        return client;
    }

    public void setClient(FirebaseCourierClientModel client) {
        this.client = client;
    }

    public FirebaseCourierStoreModel getStore() {
        return store;
    }

    public void setStore(FirebaseCourierStoreModel store) {
        this.store = store;
    }

    public static final Creator<FirebaseCourierOrderModel> CREATOR = new Creator<FirebaseCourierOrderModel>() {
        @Override
        public FirebaseCourierOrderModel createFromParcel(Parcel in) {
            return new FirebaseCourierOrderModel(in);
        }

        @Override
        public FirebaseCourierOrderModel[] newArray(int size) {
            return new FirebaseCourierOrderModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(description);
        parcel.writeParcelable(client, i);
        parcel.writeParcelable(store, i);
    }
}
