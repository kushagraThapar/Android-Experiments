package edu.kushagrathapar.cs478.fragmentsandreceivers_3;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class HotelsFragment extends ListFragment {

    private static final String TAG = "Hotels Fragment";

    // TODO: Rename and change types of parameters

    private ListSelectionListener mListener = null;
    private int mCurrIdx = -1;


    // Callback interface that allows this Fragment to notify the HotelsActivity Activity when
    // user clicks on a List Item
    public interface ListSelectionListener {
        public void onListSelection(int index);
    }

    // Called when the user selects an item from the List
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {

        // Indicates the selected item has been checked
        getListView().setItemChecked(pos, true);

        // Inform the HotelsImageFragment Activity that the item in position pos has been selected
        mListener.onListSelection(pos);

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
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");
        return super.onCreateView(inflater, container, savedInstanceState);

    }


    @Override
    public void onAttach(Activity activity) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onAttach()");
        super.onAttach(activity);



            // Set the ListSelectionListener for communicating with the HotelsImageFragment Activity
            mListener = (ListSelectionListener) activity;


    }

    @Override
    public void onActivityCreated(Bundle savedState) {

        Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
        super.onActivityCreated(savedState);

        // Set the list adapter for the ListView

        setListAdapter(new ArrayAdapter<String>(getActivity(),
                R.layout.fragment_hotels, HotelsActivity.mInterestArray));

        // Set the list choice mode to allow only one selection at a time
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //If an item has been selected, set its checked state
        if (-1 != mCurrIdx)
            getListView().setItemChecked(mCurrIdx, true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
