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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fansen.phr.R;
import com.fansen.phr.activities.MedicationOrderEditActivity;
import com.fansen.phr.adapter.MedicationOrderListAdapter;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.entity.MedicationDict;
import com.fansen.phr.entity.MedicationOrder;
import com.fansen.phr.service.IMedicationDictService;
import com.fansen.phr.service.IMedicationOrderService;
import com.fansen.phr.service.implementation.MedicationDictServiceLocalImpl;
import com.fansen.phr.service.implementation.MedicationOrderServiceLocalImpl;

import java.util.ArrayList;
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
    public static final int ADD_MED_REQUEST = 7;
    public static final int EDIT_MED_REQUEST = 8;
    public static final int ADD_IMAGE_REQUEST = 9;

    private RelativeLayout prescriptionView;

    private ListView medicationListView;
    private MedicationOrderListAdapter medicationOrderListAdapter;
    private List<MedicationOrder> medicationOrders;

    private Button addMedicationOrderBtn;

    private GridView prescriptionImagesView;

    private OnFragmentInteractionListener mListener;

    private Context context;

    private IMedicationOrderService medicationOrderService;
    private IMedicationDictService medicationDictService;

    private Encounter encounter;

    public static final String BUNDLE_KEY_ENT = "encounter";
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PrescriptionFragment.
     */
    public static PrescriptionFragment newInstance(Encounter encounter) {
        PrescriptionFragment fragment = new PrescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_KEY_ENT, encounter);
        fragment.setArguments(bundle);

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
        context = getActivity();
        medicationOrderService = new MedicationOrderServiceLocalImpl(context);
        medicationDictService = new MedicationDictServiceLocalImpl(context);

        final Bundle bundle = getArguments();
        encounter = (Encounter) bundle.getSerializable(BUNDLE_KEY_ENT);

        prescriptionView = (RelativeLayout) inflater.inflate(R.layout.fragment_prescription, container, false);

        medicationListView = (ListView) prescriptionView.findViewById(R.id.id_prescription_med_list);
        medicationOrders = medicationOrderService.getMedicationOrders(encounter.getEncounter_key());
        if (medicationOrders == null){
            medicationOrders = new ArrayList<>();
        }
        medicationOrderListAdapter = new MedicationOrderListAdapter(context, medicationOrders);
        medicationListView.setAdapter(medicationOrderListAdapter);
        medicationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO add code here to open one medication order to modify
                Intent intent = new Intent(context, MedicationOrderEditActivity.class);
                startActivityForResult(intent, EDIT_MED_REQUEST);
            }
        });


        addMedicationOrderBtn = (Button) prescriptionView.findViewById(R.id.id_prescription_add);
        addMedicationOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add code here to open add medication order activity
                Intent intent = new Intent(context, MedicationOrderEditActivity.class);
                startActivityForResult(intent, ADD_MED_REQUEST);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //TODO add code here to handle the activities result.
        if (requestCode == ADD_MED_REQUEST){
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                String name = bundle.getString(MedicationOrderEditActivity.MED_NAME);
                String spec = bundle.getString(MedicationOrderEditActivity.MED_SPEC);
                String quantity = bundle.getString(MedicationOrderEditActivity.QUANTITY);
                String quantity_unit = bundle.getString(MedicationOrderEditActivity.QUANTITY_UNIT);
                String interval = bundle.getString(MedicationOrderEditActivity.FREQ_INTERVAL);
                String interval_unit = bundle.getString(MedicationOrderEditActivity.FREQ_INTERVAL_UNIT);
                String times = bundle.getString(MedicationOrderEditActivity.FREQ_TIMES);
                String dosage = bundle.getString(MedicationOrderEditActivity.DOSAGE);
                String dosage_unit = bundle.getString(MedicationOrderEditActivity.DOSAGE_UNIT);
                String route = bundle.getString(MedicationOrderEditActivity.ROUTE);
                boolean prnChecked = bundle.getBoolean(MedicationOrderEditActivity.PRN);
                String start_time = bundle.getString(MedicationOrderEditActivity.START_TIME);

                MedicationOrder medicationOrder = new MedicationOrder();
                MedicationDict medicationDict = new MedicationDict();
                medicationDict.setName(name);
                medicationDict.setCode(name);
                medicationDict.setSpec(spec);
                int med_dict_id = medicationDictService.addMedicationDict(medicationDict);
                medicationDict.set_id(med_dict_id);

                medicationOrder.setMedication(medicationDict);
                medicationOrder.setQuantity(Float.valueOf(quantity));
                medicationOrder.setQuantity_unit(quantity_unit);
                medicationOrder.setFrequency_interval(Integer.valueOf(interval));
                medicationOrder.setFrequency_interval_unit(interval_unit);
                medicationOrder.setFrequency_times(Integer.valueOf(times));
                medicationOrder.setDosage(Float.valueOf(dosage));
                medicationOrder.setDosage_unit(dosage_unit);
                medicationOrder.setRoute(route);
                medicationOrder.setPRNIndicator(prnChecked ? 1 : 0);
                medicationOrder.setStart_time(start_time);

                int med_order_id = medicationOrderService.addMedicationOrder(encounter.getEncounter_key(), medicationOrder);
                medicationOrder.set_id(med_order_id);

                medicationOrderListAdapter.addMedicationOrder(medicationOrder);
            }
        } else if(requestCode == EDIT_MED_REQUEST){

        } else if(requestCode == ADD_IMAGE_REQUEST){

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
