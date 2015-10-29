package com.fansen.phr.fragment.details;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fansen.phr.R;
import com.fansen.phr.activities.ChiefComplaintEditActivity;

import java.util.ArrayList;
import java.util.List;


public class ProblemsFragment extends Fragment {
    public static final int SUBMIT_COMPLAINT_REQUEST = 4;  // The request code


    private OnFragmentInteractionListener mListener;
    private Button addComplaintBtn;
    private ListView complaintListView;
    private List<String> complaintList;

    private ArrayAdapter<String> complainListAdapter;
    private Context context;

    //private EditText currentProblemsEditText;
    private RelativeLayout problemsView;


    public static ProblemsFragment newInstance() {
        ProblemsFragment fragment = new ProblemsFragment();
        return fragment;
    }

    public ProblemsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = this.getActivity();

        problemsView = (RelativeLayout) inflater.inflate(R.layout.fragment_problems, container, false);
        addComplaintBtn = (Button) problemsView.findViewById(R.id.id_problems_add_complaint_btn);
        addComplaintBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiefComplaintEditActivity.class);

                startActivityForResult(intent, SUBMIT_COMPLAINT_REQUEST);

                //TODO add code here to open the chief complaint editing activity
            }
        });

        complaintListView = (ListView) problemsView.findViewById(R.id.id_problems_complaint_list);
        complaintList = new ArrayList<>();

        complainListAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,complaintList);
        complaintListView.setAdapter(complainListAdapter);

        /*complaintEditText = (EditText) problemsView.findViewById(R.id.text_complaint);
        complaintEditText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO add code here to open the chief complaint editing activity
            }
        });

        currentProblemsEditText = (EditText) problemsView.findViewById(R.id.text_current_problems);
        currentProblemsEditText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO add code here to open the current problems editing activity
            }
        });*/


        return problemsView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SUBMIT_COMPLAINT_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                String complaint = bundle.getString(ChiefComplaintEditActivity.COMPLAINT_KEY);
                String duration = bundle.getString(ChiefComplaintEditActivity.DURATION_KEY);
                String unit = bundle.getString(ChiefComplaintEditActivity.DURATION_UNIT_KEY);

                complaintList.add(complaint+"持续"+duration+unit);

                complainListAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
