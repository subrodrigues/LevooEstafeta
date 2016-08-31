package pt.levoo.courier.android.firebase.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by filiperodrigues on 24/05/16.
 */
public class FirebaseCourierOrderModel implements Parcelable{

    private String delivery_option;
    private String id;
    private String description;
    private String request_code;
//    private String date;
    private String initial_timestamp;
    private String ending_timestamp;
    private String state;
    private String total_price;
    private FirebaseOrderTimelineModel timeline;

    public FirebaseCourierOrderModel() {
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

    public String getInitial_timestamp() {
        return initial_timestamp;
    }

    public long getInitialTimestampLong() {
        return Long.valueOf(initial_timestamp);
    }

    public void setInitial_timestamp(String initial_timestamp) {
        this.initial_timestamp = initial_timestamp;
    }

    public String getEnding_timestamp() {
        return ending_timestamp;
    }

    public long getEndingTimestampLong() {
        return Long.valueOf(ending_timestamp);
    }

    public void setEnding_timestamp(String ending_timestamp) {
        this.ending_timestamp = ending_timestamp;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getRequest_code() {
        return request_code;
    }

    public void setRequest_code(String request_code) {
        this.request_code = request_code;
    }

    public String getDelivery_option() {
        return delivery_option;
    }

    public void setDelivery_option(String delivery_option) {
        this.delivery_option = delivery_option;
    }

    public FirebaseOrderTimelineModel getTimeline() {
        return timeline;
    }

    public void setTimeline(FirebaseOrderTimelineModel timeline) {
        this.timeline = timeline;
    }

    public int getOrderNumberValue(){
        if(id != null && id.length() >= 2){
            return Integer.valueOf(id.substring(1, id.length()));
        }

        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.delivery_option);
        dest.writeString(this.id);
        dest.writeString(this.description);
        dest.writeString(this.request_code);
    //    dest.writeString(this.date);
        dest.writeString(this.initial_timestamp);
        dest.writeString(this.ending_timestamp);
        dest.writeString(this.state);
        dest.writeString(this.total_price);
        dest.writeParcelable(timeline, flags);
    }

    protected FirebaseCourierOrderModel(Parcel in) {
        this.delivery_option = in.readString();
        this.id = in.readString();
        this.description = in.readString();
        this.request_code = in.readString();
    //    this.date = in.readString();
        this.initial_timestamp = in.readString();
        this.ending_timestamp = in.readString();
        this.state = in.readString();
        this.total_price = in.readString();
        this.timeline = in.readParcelable(FirebaseOrderTimelineModel.class.getClassLoader());
    }

    public static final Creator<FirebaseCourierOrderModel> CREATOR = new Creator<FirebaseCourierOrderModel>() {
        @Override
        public FirebaseCourierOrderModel createFromParcel(Parcel source) {
            return new FirebaseCourierOrderModel(source);
        }

        @Override
        public FirebaseCourierOrderModel[] newArray(int size) {
            return new FirebaseCourierOrderModel[size];
        }
    };
}
