package com.fansen.phr.adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.fansen.phr.R;
import com.fansen.phr.entity.MarStatus;
import com.fansen.phr.entity.MedicationAdminRecord;
import com.fansen.phr.entity.MedicationOrder;
import com.fansen.phr.utils.TimeFormat;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Yihui Fan on 2015/12/10.
 */
public class MarListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private OnMarStatusChanged onMarStatusChanged;

    private ArrayList<MedicationAdminRecord> medicationOrderArrayList = new ArrayList<>();

    public MarListAdapter(Context context, OnMarStatusChanged onMarStatusChanged, ArrayList<MedicationAdminRecord> medicationOrderArrayList) {
        this.context = context;
        this.medicationOrderArrayList = medicationOrderArrayList;
        this.onMarStatusChanged = onMarStatusChanged;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final MedicationAdminRecord medicationAdminRecord = medicationOrderArrayList.get(position);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_mar_layout, null);

            viewHolder = new ViewHolder();
            viewHolder.vMarMedName = (TextView) convertView.findViewById(R.id.id_mar_med_name);
            viewHolder.vMarMedDosage = (TextView) convertView.findViewById(R.id.id_mar_dosage);
            viewHolder.vTimeslot = (TextView) convertView.findViewById(R.id.id_mar_item_timeslot);
            viewHolder.switchButton = (ToggleButton) convertView.findViewById(R.id.item_mar_taken_btn);

            viewHolder.switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        onMarStatusChanged.onMarTaken(medicationAdminRecord);
                    } else{
                        onMarStatusChanged.onMarUntaken(medicationAdminRecord);
                    }
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.vMarMedName.setText(medicationAdminRecord.getMedicationOrder().getMedication().getName());
        viewHolder.vMarMedDosage.setText(medicationAdminRecord.getMedicationOrder().getDosage()+medicationAdminRecord.getMedicationOrder().getDosage_unit());
        viewHolder.vTimeslot.setText(medicationAdminRecord.getMedicationReminderTimes().getReminderTime());

        return convertView;
    }

    public class ViewHolder{
        protected TextView vMarMedName;
        protected TextView vMarMedDosage;
        protected TextView vTimeslot;
        protected ToggleButton switchButton;
        //protected TextView vStatus;
    }

    public interface OnMarStatusChanged{
        public void onMarTaken(MedicationAdminRecord medicationAdminRecord);
        public void onMarUntaken(MedicationAdminRecord medicationAdminRecord);
    }
}
