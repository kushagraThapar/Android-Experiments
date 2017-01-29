package edu.kushagrathapar.cs478.fragmentsandreceivers_3;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;

public class RestaurantsImageFragment extends Fragment {

    private static final String TAG = "RestaurantsImgFragment";
    public static ArrayList<Uri> mUriList = new ArrayList<>();
    private WebView wv = null;
    private int mCurrIdx = -1;
    private int mTitleArrayLen;

    public int getShownIndex() {
        return mCurrIdx;
    }

    public void displayWebPage(int newIndex) {
        if (newIndex < 0 || newIndex >= mTitleArrayLen)
            return;
        mCurrIdx = newIndex;
        Uri aUri = mUriList.get(mCurrIdx);
        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl(aUri.toString());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurants_webpage, container, false);

    }

    // Set up some information about the WbView
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
        initializeWebpageUris();

        wv = (WebView) getActivity().findViewById(R.id.webView);
        mTitleArrayLen = RestaurantsActivity.mInterestArray.length;
    }

    //Load the Webpage Urls
    public void initializeWebpageUris() {

        mUriList.add(Uri.parse(getString(R.string.CHIPOTLE_URI)));
        mUriList.add(Uri.parse(getString(R.string.MARKET_CREATIONS_URI)));
        mUriList.add(Uri.parse(getString(R.string.QUARTINO_URI)));
        mUriList.add(Uri.parse(getString(R.string.SU_CASA_URI)));
        mUriList.add(Uri.parse(getString(R.string.GRILL_URI)));
        mUriList.add(Uri.parse(getString(R.string.ZENWHICH_URI)));

    }
}