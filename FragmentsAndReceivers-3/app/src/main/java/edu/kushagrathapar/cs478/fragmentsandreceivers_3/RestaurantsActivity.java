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

public class RestaurantsActivity extends Activity implements RestaurantsFragment.ListSelectionListener {
    public static String[] mInterestArray;
    private String configChange;


    private final RestaurantsImageFragment mRestaurantsImageFragment = new RestaurantsImageFragment();
    private FragmentManager mFragmentManager;
    private FrameLayout mRestaurantsFrameLayout, mRestaurantsWebpageFrameLayout;

    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String TAG = "RestaurantsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");

        mInterestArray = getResources().getStringArray(R.array.restaurants);


        setContentView(R.layout.content_restaurants);


        // Get references to the RestaurantsFragment and to the RestaurantsImageFragment
        mRestaurantsFrameLayout = (FrameLayout) findViewById(R.id.restaurants_container);
        mRestaurantsWebpageFrameLayout = (FrameLayout) findViewById(R.id.restaurants_webpage_container);


        // Get a reference to the FragmentManager
        mFragmentManager = getFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();

        // Add the RestaurantsFragment to the layout
        fragmentTransaction.add(R.id.restaurants_container, new RestaurantsFragment());

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
            System.out.println("In land");
            retainLandscapeState();
        } else {
            configChange = "Portrait";
            retainPortraitState();
        }
    }

    private void setLayout() {
        // Determine whether the RestaurantsImageFragment has been added
        if (!mRestaurantsImageFragment.isAdded()) {

            // Make the RestaurantsFragment occupy the entire layout
            mRestaurantsFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    MATCH_PARENT, MATCH_PARENT));
            mRestaurantsWebpageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));
        } else {
            if (configChange == "Landscape") {
                // Make the RestaurantsFragment Layout take 1/3 of the layout's width
                System.out.println("In Landscape");
                mRestaurantsFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));

                // Make the Restaurants Image Fragment Layout take 2/3's of the layout's width
                mRestaurantsWebpageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 2f));
            } else {
                // Make the RestaurantsFragment occupy the entire layout
                System.out.println("In Portrait");
                mRestaurantsFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        0, MATCH_PARENT));
                mRestaurantsWebpageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                        MATCH_PARENT));
            }

        }

    }

    public void retainPortraitState() {
        if (mRestaurantsImageFragment.isAdded()) {
            mRestaurantsFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    0, MATCH_PARENT));
            mRestaurantsWebpageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                    MATCH_PARENT));
        }
    }

    public void retainLandscapeState() {
        if (mRestaurantsImageFragment.isAdded()) {
            mRestaurantsFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    0, MATCH_PARENT, 1f));
            mRestaurantsWebpageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT, 2f));
        }
    }

    @Override
    public void onListSelection(int index) {
        // If the RestaurantsWebpage Fragment has not been added, add it now
        if (!mRestaurantsImageFragment.isAdded()) {

            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = mFragmentManager
                    .beginTransaction();

            // Add the RestaurantsWebpage Fragment to the layout
            fragmentTransaction.add(R.id.restaurants_webpage_container, mRestaurantsImageFragment);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            mFragmentManager.executePendingTransactions();
        }


        // Tell the RestaurantsWebpage Fragment to show the webpage position index

        if (mRestaurantsImageFragment.getShownIndex() != index) {
            mRestaurantsImageFragment.displayWebPage(index);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_restaurants, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_hotels:
                Intent intent = new Intent(this, HotelsActivity.class);
                startActivity(intent);
                return true;

            default:
                return false;
        }
    }
}
