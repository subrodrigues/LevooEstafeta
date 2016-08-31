package pt.levoo.courier.android.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pt.levoo.courier.android.R;
import pt.levoo.courier.android.firebase.interfaces.FirebaseAuthStateCallbackInterface;
import pt.levoo.courier.android.firebase.interfaces.FirebaseCourierOrderCallbackInterface;
import pt.levoo.courier.android.firebase.models.FirebaseCourierOrderModel;

/**
 * Created by filiperodrigues on 31/08/16.
 */
public class LevooCourierFirebaseHelper {

    private Context mContext;
    private String mFirebaseCourierOrderPath = null;

    private FirebaseDatabase database = null;
    private FirebaseAuthStateCallbackInterface mFirebaseAuthStateCallbackInterface = null;
    private FirebaseCourierOrderCallbackInterface mFirebaseCourierOrderCallbackInterface = null;

    private DatabaseReference mCourierOrderDbReference = null;

    private FirebaseCourierOrderModel mCourierOrder;

    private String firebaseUserId = null;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public LevooCourierFirebaseHelper(Context context) {
        this.mContext = context;
    }

    public void initFirebaseHelper(String userId){
        if(TextUtils.isEmpty(this.firebaseUserId)) {
            Log.i("TAG", "initFirebaseHelper UID("+userId+")");
            this.firebaseUserId = userId;

            mFirebaseCourierOrderPath = String.format(mContext.getResources().getString(R.string.firebase_courier_order_path), firebaseUserId);

            database = FirebaseDatabase.getInstance();

            if (database != null) {
                mCourierOrderDbReference = database.getReference(mFirebaseCourierOrderPath);

                mCourierOrderDbReference.addListenerForSingleValueEvent(mCourierOrderSingleEventListener);
                mCourierOrderDbReference.addChildEventListener(mCourierOrderChildEventListener);
            }

            mAuth = FirebaseAuth.getInstance();
            mAuthListener = mFirebaseAuthStateChangedCallback;

            mAuth.addAuthStateListener(mAuthListener);
        } else {
            Log.i("TAG", "initFirebaseHelper user already set UID(" + userId + ")");
        }
    }

    FirebaseAuth.AuthStateListener mFirebaseAuthStateChangedCallback = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                /*
                if(mFirebaseAuthStateCallbackInterface != null)
                    mFirebaseAuthStateCallbackInterface.firebaseAuthStateChange(true);
                    */

                Log.d("Firebase", "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                // User is signed out
                /*
                if(mFirebaseAuthStateCallbackInterface != null)
                    mFirebaseAuthStateCallbackInterface.firebaseAuthStateChange(false);
                    */

                Log.d("Firebase", "onAuthStateChanged:signed_out");
                //    UserUtils.removeUserFromPreferences(); // Local logout TODO: this is not necessary because will be always clean at this point. Just check
            }
            // ...
        }
    };

    public FirebaseDatabase getFirebaseDatabase() {
        return database;
    }

    public void subscribeFirebaseAuthState(FirebaseAuthStateCallbackInterface mCallback){
        this.mFirebaseAuthStateCallbackInterface = mCallback;
    }


    public void subscribeFirebaseCourierOrderCallback(FirebaseCourierOrderCallbackInterface mCallback){
        this.mFirebaseCourierOrderCallbackInterface = mCallback;
    }

    /**
     * Callback used to get the first instance of Orders when app is launched
     */
    private ValueEventListener mCourierOrderSingleEventListener = new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.e("ORDER", dataSnapshot.getValue().toString());
            FirebaseCourierOrderModel currentOrder = dataSnapshot.getValue(FirebaseCourierOrderModel.class);
            mCourierOrder = currentOrder;
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Failed to read value
            Log.w("Firebase Application", "Failed to read value.", databaseError.toException());

            // Network error is already being treated at our side
            if(databaseError.getCode() != DatabaseError.NETWORK_ERROR){
                ifUserIsLoggedLogoutAtFirebaseError();
            }
        }
    };

    private void ifUserIsLoggedLogoutAtFirebaseError() {
//        final LoginResponse login = UserUtils.getSessionData();
//
//        if(login != null) {
//            UserUtils.removeUserFromPreferencesAndFirebaseSignOut(); // Local logout
//            EventBus.getDefault().post(new UserSessionEvent(false, UserSessionEvent.TokenExpiredActivityToOpen.CREDENTIALS_ACTIVITY));
//        }
    }

    /**
     * Callback used to update Orders when change occurs
     */
    private ChildEventListener mCourierOrderChildEventListener = new ChildEventListener() {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.d("Order added", dataSnapshot.toString());
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Log.e("CHANGED", dataSnapshot.getValue().toString());

            FirebaseCourierOrderModel currentOrder = dataSnapshot.getValue(FirebaseCourierOrderModel.class);
            mCourierOrder = currentOrder;
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            mCourierOrder = null;
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Failed to read value
            Log.w("Firebase Application", "Failed to read value.", databaseError.toException());

            // Network error is already being treated at our side
            if(databaseError.getCode() != DatabaseError.NETWORK_ERROR){
                ifUserIsLoggedLogoutAtFirebaseError();
            }
        }

    };

    public FirebaseAuth getFirebaseAuth() {
        return mAuth;
    }

    public void setFirebaseAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    public void signOut(){
        if(mAuth != null) {
            mAuth.signOut();
        }
        firebaseUserId = null;
    }

}
