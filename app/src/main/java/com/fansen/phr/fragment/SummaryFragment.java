package com.fansen.phr.fragment;

//import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fansen.phr.R;
import com.fansen.phr.activities.EncounterCoreInfoActivity;
import com.fansen.phr.adapter.MarListAdapter;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.entity.MarStatus;
import com.fansen.phr.entity.MedicationAdminRecord;
import com.fansen.phr.utils.TextUtil;
import com.fansen.phr.utils.TimeFormat;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Yihui Fan on 2015/10/10.
 */
public class SummaryFragment extends Fragment implements MarListAdapter.OnMarStatusChanged{
    Context context;
    private OnSummaryFragmentInteractionListener onSummaryFragmentInteractionListener;

    public static final String BUNDLE_KEY_LATEST_ENCOUNTER = "latest_encounter";
    public static final String BUNDLE_KEY_MED_ORDER_REMINDER_LIST = "medication_order_reminders";

    private LinearLayout summaryView;
    private CardView latestEncounterCardView;
    private TextView vAdmitDate;
    private TextView vDischargeDate;
    private TextView vDash;
    private TextView vHospital;
    private TextView vDept;
    private TextView vDiagnosis;
    private TextView vAttendingDoctor;
    private TextView vEmptyRecord;
    private TextView patientClassTextView;

    //private Button navigateBackBtn;
    //private Button navigateForwardBtn;
    private TextView marTimeslotTextView;

    private ListView marListView;
    private TextView emptyMarTipsTextView;

    private Encounter latestEncounter;

    private MarListAdapter marListAdapter;
    //private ArrayList<MedicationReminderTimes> medicationOrderReminders;
    private ArrayList<MedicationAdminRecord> medicationAdminRecords;

    public static SummaryFragment newInstance(Encounter encounter, ArrayList<MedicationAdminRecord> medicationAdminRecords){
        SummaryFragment summaryFragment = new SummaryFragment();

        Bundle bundle = new Bundle();
        if(encounter != null) {
            bundle.putSerializable(BUNDLE_KEY_LATEST_ENCOUNTER, encounter);
        }

        if (medicationAdminRecords != null){
            bundle.putSerializable(BUNDLE_KEY_MED_ORDER_REMINDER_LIST, medicationAdminRecords);
        }

        summaryFragment.setArguments(bundle);

        return summaryFragment;
    }

    public SummaryFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();

        Bundle bundle = getArguments();
        if (bundle != null){
            latestEncounter = (Encounter) bundle.getSerializable(BUNDLE_KEY_LATEST_ENCOUNTER);
            medicationAdminRecords = (ArrayList<MedicationAdminRecord>) bundle.getSerializable(BUNDLE_KEY_MED_ORDER_REMINDER_LIST);
        }

        if (medicationAdminRecords == null){
            medicationAdminRecords = new ArrayList<>();
        }

        marListAdapter = new MarListAdapter(context, this, medicationAdminRecords);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            onSummaryFragmentInteractionListener = (OnSummaryFragmentInteractionListener) context;
        } catch(ClassCastException cce){
            throw new ClassCastException(context.toString()
                    +" must implement OnSummaryFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onSummaryFragmentInteractionListener = null;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //TODO add code here to initial summary view
        summaryView = (LinearLayout) inflater.inflate(
                R.layout.fragment_summary, container, false);

        latestEncounterCardView = (CardView) summaryView.findViewById(R.id.id_latest_ent_card_view);
        latestEncounterCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSummaryFragmentInteractionListener.onLatestEncounterClicked(latestEncounter);
            }
        });

        vAdmitDate = (TextView) summaryView.findViewById(R.id.id_ent_admit_date);
        vDischargeDate = (TextView) summaryView.findViewById(R.id.id_ent_discharge_date);
        vDash = (TextView) summaryView.findViewById(R.id.id_ent_date_dash);
        vDept = (TextView) summaryView.findViewById(R.id.id_ent_dept);
        vHospital = (TextView) summaryView.findViewById(R.id.id_ent_org);
        vAttendingDoctor = (TextView) summaryView.findViewById(R.id.id_ent_attending_doctor);
        vDiagnosis = (TextView) summaryView.findViewById(R.id.id_ent_diagnosis);
        vEmptyRecord = (TextView) summaryView.findViewById(R.id.id_tips_for_empty_record);
        patientClassTextView = (TextView) summaryView.findViewById(R.id.id_ent_patient_class);

        marListView = (ListView) summaryView.findViewById(R.id.id_mar_list_view);

        /*navigateBackBtn = (Button) summaryView.findViewById(R.id.id_mar_back_btn);
        navigateBackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO add code here to change implement the behavior of button
            }
        });

        navigateForwardBtn = (Button) summaryView.findViewById(R.id.id_mar_forward_btn);
        navigateForwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        emptyMarTipsTextView = (TextView) summaryView.findViewById(R.id.id_tips_for_empty_medicine);
        if (marListAdapter.isEmpty()){
            emptyMarTipsTextView.setVisibility(View.VISIBLE);
        } else{
            emptyMarTipsTextView.setVisibility(View.GONE);
        }

        marTimeslotTextView = (TextView) summaryView.findViewById(R.id.id_mar_datetime_slot);

        marListView.setAdapter(marListAdapter);

        if (latestEncounter == null){
            vEmptyRecord.setVisibility(View.VISIBLE);
            latestEncounterCardView.setVisibility(View.GONE);
        } else {
            vEmptyRecord.setVisibility(View.GONE);
            latestEncounterCardView.setVisibility(View.VISIBLE);
        }

        updateView();

        return summaryView;
    }

    private void updateView(){
        if (latestEncounter == null){
            return;
        }

        latestEncounterCardView.setVisibility(View.VISIBLE);
        vEmptyRecord.setVisibility(View.GONE);

        vAdmitDate.setText(TimeFormat.parseDate(latestEncounter.getAdmit_date()));

        String dischargeDate = TimeFormat.parseDate(latestEncounter.getAdmit_date());
        if (dischargeDate ==null || dischargeDate.equals("")){
            vDischargeDate.setVisibility(View.GONE);
            vDash.setVisibility(View.GONE);
        } else {
            vDischargeDate.setVisibility(View.VISIBLE);
            vDash.setVisibility(View.VISIBLE);
            vDischargeDate.setText(dischargeDate);
        }

        vDept.setText(latestEncounter.getDepartment().getName());
        vHospital.setText(latestEncounter.getOrg().getOrg_name());
        vAttendingDoctor.setText(latestEncounter.getAttendingDoctor().getPhysicianName());
        vDiagnosis.setText(TextUtil.convertDiagnosisListToString(latestEncounter.getDiagnosisList(), EncounterCoreInfoActivity.DELIMITER));
        patientClassTextView.setText(latestEncounter.getPatientClass());
    }

    public void setLatestEncounter(Encounter latestEncounter){
        Bundle bundle = getArguments();
        if (bundle !=null) {
            bundle.putSerializable(BUNDLE_KEY_LATEST_ENCOUNTER, latestEncounter);
            this.latestEncounter = latestEncounter;
            updateView();
        }
    }

    public void updateMAR(ArrayList<MedicationAdminRecord> medicationAdminRecords){
        Bundle bundle = getArguments();

        if (medicationAdminRecords != null){
            bundle.putSerializable(BUNDLE_KEY_MED_ORDER_REMINDER_LIST, medicationAdminRecords);
            marListAdapter.updateMar(medicationAdminRecords);
        }

        if (marListAdapter.isEmpty()){
            emptyMarTipsTextView.setVisibility(View.VISIBLE);
        } else{
            emptyMarTipsTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onMarTaken(MedicationAdminRecord medicationAdminRecord) {
        medicationAdminRecord.setStatus(MarStatus.TAKEN.getName());
        medicationAdminRecord.setAdminDate(new Date());

        onSummaryFragmentInteractionListener.onMedicationTaken(medicationAdminRecord);
    }

    @Override
    public void onMarUntaken(MedicationAdminRecord medicationAdminRecord) {
        medicationAdminRecord.setStatus(MarStatus.UNTAKEN.getName());

        onSummaryFragmentInteractionListener.onMedicationUntaken(medicationAdminRecord);
    }

    public interface OnSummaryFragmentInteractionListener {
        public void onLatestEncounterClicked(Encounter encounter);
        public void onMedicationTaken(MedicationAdminRecord medicationAdminRecord);
        public void onMedicationUntaken(MedicationAdminRecord medicationAdminRecord);
    }
}
