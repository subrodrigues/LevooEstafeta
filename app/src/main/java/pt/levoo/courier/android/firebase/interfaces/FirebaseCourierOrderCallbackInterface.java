package pt.levoo.courier.android.firebase.interfaces;


import pt.levoo.courier.android.firebase.models.FirebaseCourierOrderModel;

/**
 * Created by filiperodrigues on 23/05/16.
 */
public interface FirebaseCourierOrderCallbackInterface {

    void notifyOrderChanged(FirebaseCourierOrderModel orderChanged);
    void notifyOrderRemoved(FirebaseCourierOrderModel orderRemoved);
}
