package com.fansen.phr.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fansen.phr.R;
import com.fansen.phr.activities.EncounterCoreInfoActivity;
import com.fansen.phr.entity.Diagnosis;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.entity.sort.EncounterSort;
import com.fansen.phr.utils.TimeFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Yihui Fan on 2015/10/10.
 */
public class MedicalRecordListAdapter extends RecyclerView.Adapter<MedicalRecordListAdapter.MedicalRecordListViewHolder>{

    private List<Encounter> encounterList;
    private MedicalRecordItemClickListener itemClickListener = null;
    private String priorDate = "";

    public MedicalRecordListAdapter(List<Encounter> encounters) {
        if (encounters == null){
            encounterList = new ArrayList<>();
        } else {
            encounterList = encounters;
        }

        //Collections.sort(encounterList, new EncounterSort());
        Collections.sort(encounterList, Collections.<Encounter>reverseOrder(new EncounterSort()));
    }

    @Override
    public MedicalRecordListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_encounter_layout, parent, false);
        return new MedicalRecordListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MedicalRecordListViewHolder holder, int position) {
        Encounter encounter = encounterList.get(position);

        List<Diagnosis> diagnosises = encounter.getDiagnosisList();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i=0; i<diagnosises.size(); i++){
            stringBuffer.append(diagnosises.get(i).getDiagnosis_dict().getName());
            if (i != diagnosises.size()-1){
                stringBuffer.append(EncounterCoreInfoActivity.DELIMITER);
            }
        }

        Context context = holder.rootViewLayout.getContext();
        if(position % 2 != 0){
            holder.cardViewLayout.setBackgroundColor(context.getResources().getColor(R.color.cardviewBackgroundDark));
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.cardviewBackgroundDark));
        } else {
            holder.cardViewLayout.setBackgroundColor(context.getResources().getColor(R.color.cardviewBackgroundLight));
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.cardviewBackgroundLight));
        }

        Date date = encounter.getAdmit_date();
        String dateString = TimeFormat.parseDate(date);
        if(!dateString.equals(priorDate)){
            holder.vDateYear.setText(TimeFormat.getYear(date));
            holder.vDateDayMonth.setText(TimeFormat.getDay(date)+"/"+TimeFormat.getMonth(date));
            priorDate = dateString;
            holder.dotView.setVisibility(View.VISIBLE);
            holder.vDateYear.setVisibility(View.VISIBLE);
            holder.vDateDayMonth.setVisibility(View.VISIBLE);
        } else{
            holder.dotView.setVisibility(View.GONE);
            holder.vDateYear.setVisibility(View.GONE);
            holder.vDateDayMonth.setVisibility(View.GONE);
        }

        holder.vHospital.setText(encounter.getOrg().getOrg_name());
        holder.vDept.setText(encounter.getDepartment().getName());
        holder.vDiagnosis.setText(stringBuffer.toString());
        holder.vAttendingDoctor.setText(encounter.getAttendingDoctor().getPhysicianName());
    }

    @Override
    public int getItemCount() {
        return encounterList.size();
    }

    public void setItemClickListener(MedicalRecordItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public void addEncounter(Encounter encounter){
        if (encounterList != null){
            encounterList.add(encounter);
            //Collections.sort(encounterList, new EncounterSort());
            Collections.sort(encounterList, Collections.<Encounter>reverseOrder(new EncounterSort()));
        }

        notifyDataSetChanged();

        //notifyItemInserted(0);
    }

    public class MedicalRecordListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected CardView cardView;
        protected RelativeLayout rootViewLayout;
        protected RelativeLayout cardViewLayout;
        protected View dotView;
        protected TextView vDateYear;
        protected TextView vDateDayMonth;
        protected TextView vHospital;
        protected TextView vDept;
        protected TextView vDiagnosis;
        protected TextView vAttendingDoctor;

        public MedicalRecordListViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            rootViewLayout = (RelativeLayout)itemView;
            cardView = (CardView) itemView.findViewById(R.id.id_ent_card_view);
            cardViewLayout = (RelativeLayout) itemView.findViewById(R.id.id_ent_card_view_layout);
            dotView = itemView.findViewById(R.id.id_ent_item_dot);
            vDateYear = (TextView) itemView.findViewById(R.id.ent_date_year);
            vDateDayMonth = (TextView) itemView.findViewById(R.id.ent_date_month);
            vHospital = (TextView) itemView.findViewById(R.id.ent_org);
            vDept = (TextView) itemView.findViewById(R.id.ent_dept);
            vDiagnosis = (TextView) itemView.findViewById(R.id.ent_diagnosis);
            vAttendingDoctor = (TextView) itemView.findViewById(R.id.ent_attending_doctor);
        }

        @Override
        public void onClick(View v) {
            if(itemClickListener != null){
                itemClickListener.itemClicked(v, getAdapterPosition());
            }

        }
    }

    public interface MedicalRecordItemClickListener{
        public void itemClicked(View v, int position);
    }
}

