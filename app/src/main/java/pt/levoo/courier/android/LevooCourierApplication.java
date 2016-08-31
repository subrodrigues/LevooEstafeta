package pt.levoo.courier.android;

import android.app.Application;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import pt.levoo.courier.android.firebase.LevooCourierFirebaseHelper;

/**
 * Created by filiperodrigues on 31/08/16.
 */
public class LevooCourierApplication extends Application{
    private static LevooCourierApplication app = null;

    public static LevooCourierApplication getInstance() {
        return app;
    }

//    private LevooCourierFirebaseHelper mFirebaseHelper = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Application", "onCreate");

        app = this;

        //    Utils.setCurrentLocale(getBaseContext(), "pt");
    }

//
//        mFirebaseHelper = new LevooCourierFirebaseHelper(this);
////        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
////        if(user != null) {
////            initFirebaseHelper(user.getUid());
////        }
//        initFirebaseHelper("e0");
//
//
//    }
//
//    public void initFirebaseHelper(String uid) {
//        if (mFirebaseHelper == null) {
//            Log.i("Application", "initFirebaseHelper uid("+uid+")");
//            mFirebaseHelper = new LevooCourierFirebaseHelper(this);
//        }
//        mFirebaseHelper.initFirebaseHelper(uid);
//    }
}
