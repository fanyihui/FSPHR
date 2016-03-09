package com.fansen.phr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.fansen.phr.R;
import com.fansen.phr.entity.MedicationOrder;

import java.util.ArrayList;

/**
 * Created by Yihui Fan on 2015/12/10.
 */
public class MarListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;

    private ArrayList<MedicationOrder> medicationOrderArrayList = new ArrayList<>();

    public MarListAdapter(Context context, ArrayList<MedicationOrder> medicationOrderArrayList) {
        this.context = context;
        this.medicationOrderArrayList = medicationOrderArrayList;

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return medicationOrderArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return medicationOrderArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        MedicationOrder medicationOrder = medicationOrderArrayList.get(position);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_mar_layout, null);

            viewHolder = new ViewHolder();
            viewHolder.vMarMedName = (TextView) convertView.findViewById(R.id.id_mar_med_name);
            viewHolder.vMarMedDosage = (TextView) convertView.findViewById(R.id.id_mar_dosage);
            viewHolder.vTimeslot = (TextView) convertView.findViewById(R.id.id_mar_datetime_slot);
            viewHolder.switchButton = (ToggleButton) convertView.findViewById(R.id.item_mar_taken_btn);
            viewHolder.switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //TODO add code here to
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.vMarMedName.setText(medicationOrder.getMedication().getName());
        viewHolder.vMarMedDosage.setText(medicationOrder.getDosage()+medicationOrder.getDosage_unit());

        return convertView;
    }

    public class ViewHolder{
        protected TextView vMarMedName;
        protected TextView vMarMedDosage;
        protected TextView vTimeslot;
        protected ToggleButton switchButton;
        //protected TextView vStatus;
    }
}
