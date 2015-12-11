package com.fansen.phr.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fansen.phr.R;
import com.fansen.phr.activities.EncounterCoreInfoActivity;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.utils.TextUtil;
import com.fansen.phr.utils.TimeFormat;

/**
 * Created by Yihui Fan on 2015/10/10.
 */
public class SummaryFragment extends Fragment{
    public static final String BUNDLE_KEY_LATEST_ENCOUNTER = "latest_encounter";

    private RelativeLayout summaryView;
    private TextView vDate;
    private TextView vHospital;
    private TextView vDept;
    private TextView vDiagnosis;
    private TextView vAttendingDoctor;

    private Encounter latestEncounter;

    public static SummaryFragment newInstance(Encounter encounter){
        SummaryFragment summaryFragment = new SummaryFragment();

        if(encounter != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(BUNDLE_KEY_LATEST_ENCOUNTER, encounter);
            summaryFragment.setArguments(bundle);
        }

        return summaryFragment;
    }

    public SummaryFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null){
            latestEncounter = (Encounter) bundle.getSerializable(BUNDLE_KEY_LATEST_ENCOUNTER);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //TODO add code here to initial summary view
        summaryView = (RelativeLayout) inflater.inflate(
                R.layout.fragment_summary, container, false);

        vDate = (TextView) summaryView.findViewById(R.id.id_latest_ent_date);
        vDept = (TextView) summaryView.findViewById(R.id.id_latest_ent_dept);
        vHospital = (TextView) summaryView.findViewById(R.id.id_latest_ent_org);
        vAttendingDoctor = (TextView) summaryView.findViewById(R.id.id_latest_ent_attending_doctor);
        vDiagnosis = (TextView) summaryView.findViewById(R.id.id_latest_ent_diagnosis);

        setLatestEncounter(latestEncounter);

        return summaryView;
    }

    public void setLatestEncounter(Encounter latestEncounter){
        this.latestEncounter = latestEncounter;

        vDate.setText(TimeFormat.parseDate(latestEncounter.getAdmit_date(), "yyyyMMdd"));
        vDept.setText(latestEncounter.getDepartment().getName());
        vHospital.setText(latestEncounter.getOrg().getOrg_name());
        vAttendingDoctor.setText(latestEncounter.getAttendingDoctor().getPhysicianName());
        vDiagnosis.setText(TextUtil.convertDiagnosisListToString(latestEncounter.getDiagnosisList(), EncounterCoreInfoActivity.DELIMITER));
    }
}
