package pt.levoo.courier.android;

import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.DismissOverlayView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.Frame;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import pt.levoo.courier.android.firebase.LevooCourierFirebaseHelper;

public class MainActivity extends WearableActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {
    private static final LatLng PINK = new LatLng(41.137439, -8.631733);

    private LevooCourierFirebaseHelper mFirebaseHelper;
    private GoogleMap mMap;
    private MapFragment mMapFragment;
    private DismissOverlayView mDismissOverlay;

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT = new SimpleDateFormat("HH:mm", Locale.US);

    private BoxInsetLayout mContainerView;
    private FrameLayout mOfflineViewContainer;

    private Handler mCircularRevealHandler = null;
    private Runnable mCircularRevealRunnable = null;
    private boolean mStopCircularRevealAnimation = false;
    private float mScaleButton = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
        setContent();

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if(user != null) {
//            initFirebaseHelper(user.getUid());
//        }
    }

    private void initViews() {
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        mOfflineViewContainer = (FrameLayout) findViewById(R.id.main_activity_offline_view_container);
        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mDismissOverlay = (DismissOverlayView) findViewById(R.id.dismiss_overlay);

        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);

        mFirebaseHelper = new LevooCourierFirebaseHelper(this);
    }

    private void setContent() {
        mDismissOverlay.setIntroText("Long press to exit");
        mDismissOverlay.showIntroIfNecessary();

        initFirebaseHelper("e0");

        mOfflineViewContainer.setOnTouchListener(mCircularRevealTouchListener);
    }

    public void initFirebaseHelper(String uid) {
        if (mFirebaseHelper == null) {
            Log.i("Application", "initFirebaseHelper uid("+uid+")");
            mFirebaseHelper = new LevooCourierFirebaseHelper(this);
        }
        mFirebaseHelper.initFirebaseHelper(uid);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mDismissOverlay.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(PINK)
                .title("Current Position")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_current_position_icon))
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PINK, 17));
        mMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
        if (isAmbient()) {
            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.black));
        /*    mTextView.setTextColor(getResources().getColor(android.R.color.white));
            mClockView.setVisibility(View.VISIBLE);

            mClockView.setText(AMBIENT_DATE_FORMAT.format(new Date()));
        */
        } else {
            mContainerView.setBackground(null);
        /*    mTextView.setTextColor(getResources().getColor(android.R.color.black));
            mClockView.setVisibility(View.GONE);
            */
        }
    }

    View.OnTouchListener mCircularRevealTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                mStopCircularRevealAnimation = false;
                mCircularRevealRunnable = initButtonCircularRevealAnimation();

                mCircularRevealHandler = new Handler();
                mCircularRevealHandler.post(mCircularRevealRunnable);

            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                mStopCircularRevealAnimation = true;
                mCircularRevealHandler.removeCallbacks(mCircularRevealRunnable);

                mScaleButton = 1.0f;
                mOfflineViewContainer.setScaleX(mScaleButton);
                mOfflineViewContainer.setScaleY(mScaleButton);
            }

            return false;
        }
    };

    private Runnable initButtonCircularRevealAnimation() {
        return new Runnable() {

            public void run() {
                if (!mStopCircularRevealAnimation && mCircularRevealHandler != null) {
                    mOfflineViewContainer.setScaleX(mScaleButton);
                    mOfflineViewContainer.setScaleY(mScaleButton);

                    mScaleButton += 0.05f;

                    mCircularRevealHandler.postDelayed(mCircularRevealRunnable, 100);
                }
            }
        };
    }
}
