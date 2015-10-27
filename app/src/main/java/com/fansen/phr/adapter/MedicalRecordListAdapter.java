package com.fansen.phr.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fansen.phr.R;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.utils.TimeFormat;

import java.util.List;

/**
 * Created by Yihui Fan on 2015/10/10.
 */
public class MedicalRecordListAdapter extends RecyclerView.Adapter<MedicalRecordListAdapter.MedicalRecordListViewHolder>{

    private List<Encounter> encounterList;
    private MedicalRecordItemClickListener itemClickListener = null;

    public MedicalRecordListAdapter(List<Encounter> encounters) {
        encounterList = encounters;
    }

    @Override
    public MedicalRecordListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.phr_item_layout, parent, false);
        return new MedicalRecordListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MedicalRecordListViewHolder holder, int position) {
        Encounter encounter = encounterList.get(position);

        holder.vDate.setText(TimeFormat.parseDate(encounter.getAdmit_date(), "yyyyMMdd"));
        holder.vHospital.setText(encounter.getOrg().getOrg_name());
        holder.vDept.setText(encounter.getDepartment().getName());
        holder.vDiagnosis.setText(encounter.getDiagnosis().get(0).getDiagnosis_dict().getName());
    }

    @Override
    public int getItemCount() {
        return encounterList.size();
    }

    public void setItemClickListener(MedicalRecordItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }



    public class MedicalRecordListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected TextView vDate;
        protected TextView vHospital;
        protected TextView vDept;
        protected TextView vDiagnosis;

        public MedicalRecordListViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            vDate = (TextView) itemView.findViewById(R.id.ent_date);
            vHospital = (TextView) itemView.findViewById(R.id.ent_org);
            vDept = (TextView) itemView.findViewById(R.id.ent_dept);
            vDiagnosis = (TextView) itemView.findViewById(R.id.ent_diagnosis);
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

