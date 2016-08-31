package pt.levoo.courier.android.firebase.interfaces;

import pt.levoo.android.firebase.models.FirebaseOrderModel;

/**
 * Created by filiperodrigues on 23/05/16.
 */
public interface FirebaseCourierOrderCallbackInterface {

    // TODO: change to specific object
    void notifyOrderChanged(FirebaseOrderModel orderChanged);
    void notifyOrderRemoved(FirebaseOrderModel orderRemoved);
}
