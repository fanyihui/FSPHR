package com.fansen.phr.adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.fansen.phr.R;
import com.fansen.phr.entity.MedicationAdminRecord;
import com.fansen.phr.entity.MedicationReminderTimes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by faen on 2016/3/18.
 */
public class MedicationReminderAdapter extends BaseAdapter {
    private ArrayList<MedicationReminderTimes> times;
    private Context context;
    private LayoutInflater layoutInflater;
    private Calendar cal = Calendar.getInstance();
    private int currentPosition = 0;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

    private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal.set(Calendar.MINUTE, minute);

            MedicationReminderTimes medicationReminderTimes = new MedicationReminderTimes();
            medicationReminderTimes.setSequenceNumber(currentPosition + 1);
            medicationReminderTimes.setReminderTime(simpleDateFormat.format(cal.getTime()));

            times.set(currentPosition, medicationReminderTimes);
            notifyDataSetChanged();
        }
    };

    public MedicationReminderAdapter(Context context, ArrayList<MedicationReminderTimes> times){
        this.context = context;
        this.times = times;

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return times.size();
    }

    @Override
    public Object getItem(int position) {
        return times.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_med_reminder_times, null);

            viewHolder = new ViewHolder();
            viewHolder.sequenceTextView = (TextView) convertView.findViewById(R.id.id_med_reminder_seq);
            viewHolder.timeTextView = (TextView) convertView.findViewById(R.id.id_med_reminder_time);
            viewHolder.timeTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPosition = position;
                    new TimePickerDialog(context, listener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show();
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.sequenceTextView.setText("第"+times.get(position).getSequenceNumber()+"次");
        viewHolder.timeTextView.setText(times.get(position).getReminderTime());

        return convertView;
    }

    public class ViewHolder{
        protected TextView sequenceTextView;
        protected TextView timeTextView;
    }
}
