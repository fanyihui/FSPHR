package com.fansen.phr.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.fansen.phr.R;

public class FreeTextFragment extends Fragment {
    private static final String ARG_VALUE = "value";
    private static final String ARG_HINT = "hint";

    private String value;
    private String hint;

    private OnSaveActionListener mListener;

    private RelativeLayout rootView;
    private EditText editText;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param value the value of EditText.
     * @param hint the hint for the EditText.
     * @return A new instance of fragment FreeTextFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FreeTextFragment newInstance(String value, String hint) {
        FreeTextFragment fragment = new FreeTextFragment();
        Bundle args = new Bundle();
        args.putString(ARG_VALUE, value);
        args.putString(ARG_HINT, hint);
        fragment.setArguments(args);
        return fragment;
    }

    public FreeTextFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            value = getArguments().getString(ARG_VALUE);
            hint = getArguments().getString(ARG_HINT);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = (RelativeLayout) inflater.inflate(R.layout.fragment_free_text, container, false);

        editText = (EditText) rootView.findViewById(R.id.id_free_edit_text);
        editText.setText(value);
        editText.setHint(hint);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onSaveActionPressed(String value) {
        if (mListener != null) {
            mListener.onSave(value);
        }
    }

    public String getValue(){
        return editText.getText().toString();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*try{
            mListener = (OnSaveActionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnSaveActionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.menu_free_text_activity, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //if (item.getItemId() == R.id.action_save){
        //    onSaveActionPressed(editText.getText().toString());
        //}

        return super.onOptionsItemSelected(item);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnSaveActionListener {
        public void onSave(String value);
    }

}
