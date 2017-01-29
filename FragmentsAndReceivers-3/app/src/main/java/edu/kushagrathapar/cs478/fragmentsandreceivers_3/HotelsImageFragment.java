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

public class HotelsImageFragment extends Fragment {


    private static final String TAG = "HotelsImageFragment";
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
        //set up the WebViewClient
        wv.setWebViewClient(new WebViewClient());
        //Load the url corresponding to the list item clicked
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
        return inflater.inflate(R.layout.fragment_hotels_webpage, container, false);

    }

    // Set up some information about the WebView
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
        initializeWebpageUris();

        wv = (WebView) getActivity().findViewById(R.id.webView);
        mTitleArrayLen = HotelsActivity.mInterestArray.length;
    }

    //method to initializa the web urls
    public void initializeWebpageUris() {

        mUriList.add(Uri.parse(getString(R.string.HILTON_URI)));
        mUriList.add(Uri.parse(getString(R.string.HAMPTON_URI)));
        mUriList.add(Uri.parse(getString(R.string.DRAKE_URI)));
        mUriList.add(Uri.parse(getString(R.string.RITZ_URI)));
        mUriList.add(Uri.parse(getString(R.string.SOFITEL_URI)));
        mUriList.add(Uri.parse(getString(R.string.OMNI_URI)));
    }
}
