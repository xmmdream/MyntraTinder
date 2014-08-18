package anil.myntratinder;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import anil.myntratinder.utils.DatabaseHelper;
import anil.myntratinder.views.ProductStackView;
import anil.myntratinder.views.SingleProductView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TinderUIFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TinderUIFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class TinderUIFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_GROUP_LABEL = "groupLabel";
    private static final String ARG_GROUP_FILE_NAME = "fileName";
    private static final String ARG_GROUP_START_FROM_KEY = "startFromKey";
    private static final String ARG_GROUP_MAX_PRODUCTS_KEY = "maxProductsKey";
    private static final String ARG_GROUP_POST_DATA_HEAD = "postDataHead";
    private static final String ARG_GROUP_POST_DATA_TAIL = "postDataTail";

    // TODO: Rename and change types of parameters
    private String mGroupLabel;
    private String mFileName;
    private String mStartFromKey;
    private String mMaxProductsKey;
    private String mPostDataHead;
    private String mPostDataTail;

    private OnFragmentInteractionListener mListener;

    ProductStackView mProductStackView;
    DatabaseHelper db;
    int startFrom;
    String maxProducts;
    SharedPreferences sharedPreferences;


    public static TinderUIFragment newInstance(String groupLabel, String fileName, String startFromKey, String maxProductsKey, String postDataHead, String postDataTail) {
        TinderUIFragment fragment = new TinderUIFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GROUP_LABEL, groupLabel);
        args.putString(ARG_GROUP_FILE_NAME, fileName);
        args.putString(ARG_GROUP_START_FROM_KEY, startFromKey);
        args.putString(ARG_GROUP_MAX_PRODUCTS_KEY, maxProductsKey);
        args.putString(ARG_GROUP_POST_DATA_HEAD, postDataHead);
        args.putString(ARG_GROUP_POST_DATA_TAIL, postDataTail);
        fragment.setArguments(args);
        return fragment;
    }
    public TinderUIFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGroupLabel = getArguments().getString(ARG_GROUP_LABEL);
            mFileName = getArguments().getString(ARG_GROUP_FILE_NAME);
            mStartFromKey = getArguments().getString(ARG_GROUP_START_FROM_KEY);
            mMaxProductsKey = getArguments().getString(ARG_GROUP_MAX_PRODUCTS_KEY);
            mPostDataHead = getArguments().getString(ARG_GROUP_POST_DATA_HEAD);
            mPostDataTail = getArguments().getString(ARG_GROUP_POST_DATA_TAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tinder_ui, container, false);
        mProductStackView = (ProductStackView) view.findViewById(R.id.tinder_mProductStack);
        db = new DatabaseHelper(getActivity().getApplicationContext());
        doInitialize();

        mProductStackView.setmProductStackListener(new ProductStackView.ProductStackListener() {
            @Override
            public void onUpdateProgress(boolean positif, float percent, View view) {
                SingleProductView item = (SingleProductView)view;
                item.onUpdateProgress(positif, percent, view);
            }

            @Override
            public void onCancelled(View beingDragged) {
                SingleProductView item = (SingleProductView)beingDragged;
                item.onCancelled(beingDragged);
            }

            @Override
            public void onChoiceMade(boolean choice, View beingDragged) {
                SingleProductView item = (SingleProductView)beingDragged;
                item.onChoiceMade(choice, beingDragged);
                //todo: handle what to do after the choice is made.
            }
        });

        return view;
    }

    private void doInitialize() {

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}