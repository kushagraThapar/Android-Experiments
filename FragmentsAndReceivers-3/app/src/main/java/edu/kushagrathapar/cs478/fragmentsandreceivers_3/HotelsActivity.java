package edu.kushagrathapar.cs478.fragmentsandreceivers_3;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


public class HotelsActivity extends Activity
        implements HotelsFragment.ListSelectionListener {
    public static String[] mInterestArray;
    private String configChange;


    private final HotelsImageFragment mHotelsImageFragment = new HotelsImageFragment();
    private FragmentManager mFragmentManager;
    private FrameLayout mHotelsFrameLayout, mHotelsWebPageFrameLayout;
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String TAG = "HotelsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");

        mInterestArray = getResources().getStringArray(R.array.hotels);


        setContentView(R.layout.content_hotels);

        // Get references to the HotelsFragment and to the HotelsImageFragment
        mHotelsFrameLayout = (FrameLayout) findViewById(R.id.hotels_fragment_container);

        mHotelsWebPageFrameLayout = (FrameLayout) findViewById(R.id.hotels_webpage_fragment_container);


        // Get a reference to the FragmentManager
        mFragmentManager = getFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();

        // Add the HotelsFragment to the layout
        fragmentTransaction.add(R.id.hotels_fragment_container, new HotelsFragment());

        // Commit the FragmentTransaction
        fragmentTransaction.commit();

        // Add a OnBackStackChangedListener to reset the layout when the back stack changes
        mFragmentManager
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {

                        setLayout();
                    }
                });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            configChange = "Landscape";

            retainLandscapeState();
        } else {
            configChange = "Portrait";

            retainPortraitState();
        }
    }


    private void setLayout() {
        // Determine whether the HotelsImageFragment has been added
        if (!mHotelsImageFragment.isAdded()) {

            // Make the HotelsFragment occupy the entire layout
            mHotelsFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    MATCH_PARENT, MATCH_PARENT));

            mHotelsWebPageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));
        } else {
            if (configChange == "Landscape") {
                // Make the Hotels Layout take 1/3 of the layout's width

                mHotelsFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));

                // Make the Hotels Image take 2/3's of the layout's width
                mHotelsWebPageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 2f));
            } else {
                // Make the HotelsFragment occupy the entire layout

                mHotelsFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        0, MATCH_PARENT));

                mHotelsWebPageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                        MATCH_PARENT));
            }

        }

    }

    //Called when the screen orientation changes from landscape to portrait mode
    public void retainPortraitState() {
        if (mHotelsImageFragment.isAdded()) {

            mHotelsFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    0, MATCH_PARENT));

            mHotelsWebPageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                    MATCH_PARENT));
        }
    }

    //Called when the screen orientation changes from portrait to landscape mode
    public void retainLandscapeState() {
        if (mHotelsImageFragment.isAdded()) {

            mHotelsFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    0, MATCH_PARENT, 1f));

            mHotelsWebPageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT, 2f));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hotels, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch (item.getItemId()) {
            case R.id.action_restaurants:
                Intent intent = new Intent(this, RestaurantsActivity.class);
                startActivity(intent);
                break;
            default:
                return false;
        }
        return true;

    }

    @Override
    public void onListSelection(int index) {
        // If the QuoteFragment has not been added, add it now
        if (!mHotelsImageFragment.isAdded()) {

            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = mFragmentManager
                    .beginTransaction();

            // Add the HotelsImageFragment to the layout
            fragmentTransaction.add(R.id.hotels_webpage_fragment_container, mHotelsImageFragment);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            mFragmentManager.executePendingTransactions();
        }


        // Tell the HotelsImageFragment to show the webpage at position index

        if (mHotelsImageFragment.getShownIndex() != index) {
            mHotelsImageFragment.displayWebPage(index);
        }


    }
}



























