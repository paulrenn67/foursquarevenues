package com.rakettitiede.foursquaredemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

/**
 * Make FourSquare REST service search for venues
 */
public class MainActivity extends AppCompatActivity implements FourSquareApi.Listener {
    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    private final int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private ListView mListView;
    private VenueModel mVenueModel;
    private VenueAdapter mVenueAdapter;
    private FourSquareApi mFourSquareApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVenueModel= new VenueModel();
        mVenueAdapter = new VenueAdapter(
                this,
                mVenueModel,
                getString(R.string.no_address),
                getString(R.string.distance_meters_format));
        mFourSquareApi = new FourSquareApi(this.getApplicationContext(), this);

        mListView = (ListView) findViewById(R.id.listview);
        mListView.setEmptyView(findViewById(R.id.empty_view));
        mListView.setAdapter(mVenueAdapter);

        EditText editText =(EditText ) findViewById(R.id.edittext);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Intentionally empty.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(LOG_TAG, "onTextChanged " + s);
                try {
                    mFourSquareApi.searchVenues(s.toString());
                } catch (UnsupportedEncodingException e) {
                    onVenueError(e.getMessage());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Intentionally empty.
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
    }

    @Override
    protected void onStop () {
        super.onStop();
        if (mFourSquareApi != null) {
            mFourSquareApi.cancelAll();
        }
    }

    @Override
    public void onVenueResponse(VenueModel venues) {
        mVenueModel.clear();
        mVenueModel.addAll(venues);
        mVenueAdapter.notifyDataSetChanged();
    }

    @Override
    public void onVenueError(String errorMessage) {
        Log.e(LOG_TAG, errorMessage);

        Toast toast = Toast.makeText(getApplicationContext(),
                errorMessage, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
}
