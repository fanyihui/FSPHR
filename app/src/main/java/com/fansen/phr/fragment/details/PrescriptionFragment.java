package com.fansen.phr.fragment.details;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fansen.phr.R;
import com.fansen.phr.adapter.MedicationOrderListAdapter;
import com.fansen.phr.entity.MedicationOrder;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PrescriptionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PrescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrescriptionFragment extends Fragment {
    private RelativeLayout prescriptionView;

    private ListView medicationListView;
    private MedicationOrderListAdapter medicationOrderListAdapter;
    private List<MedicationOrder> medicationOrders;

    private Button addMedicationOrderBtn;

    private GridView prescriptionImagesView;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PrescriptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrescriptionFragment newInstance() {
        PrescriptionFragment fragment = new PrescriptionFragment();

        return fragment;
    }

    public PrescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        prescriptionView = (RelativeLayout) inflater.inflate(R.layout.fragment_prescription, container, false);

        medicationListView = (ListView) prescriptionView.findViewById(R.id.id_prescription_med_list);
        medicationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


        addMedicationOrderBtn = (Button) prescriptionView.findViewById(R.id.id_prescription_add);
        addMedicationOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add code here to open add medication order activity
            }
        });

        prescriptionImagesView = (GridView) prescriptionView.findViewById(R.id.id_fragment_prescription_images);

        return prescriptionView;
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
