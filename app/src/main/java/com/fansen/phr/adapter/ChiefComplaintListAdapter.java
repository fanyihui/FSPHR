package com.fansen.phr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fansen.phr.R;
import com.fansen.phr.entity.ChiefComplaint;

import java.util.List;

/**
 * Created by 310078142 on 2015/11/2.
 */
public class ChiefComplaintListAdapter extends BaseAdapter{
    private Context context;
    private List<ChiefComplaint> chiefComplaints;
    private LayoutInflater layoutInflater;

    public ChiefComplaintListAdapter(Context context, List<ChiefComplaint> chiefComplaints){
        this.context = context;
        this.chiefComplaints = chiefComplaints;

        layoutInflater = LayoutInflater.from(context);
    }

    public void addComplaint(ChiefComplaint chiefComplaint){
        chiefComplaints.add(chiefComplaint);
        notifyDataSetChanged();
    }

    public void updateComplaint(int position, ChiefComplaint chiefComplaint){
        chiefComplaints.set(position, chiefComplaint);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (chiefComplaints == null)
            return 0;

        return chiefComplaints.size();
    }

    @Override
    public Object getItem(int position) {
        return chiefComplaints.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_chief_complaint_layout, null);

            viewHolder = new ViewHolder();
            viewHolder.symptomView = (TextView) convertView.findViewById(R.id.id_cc_item_symptom);
            viewHolder.durationView = (TextView) convertView.findViewById(R.id.id_cc_item_duration);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ChiefComplaint chiefComplaint = chiefComplaints.get(position);

        viewHolder.symptomView.setText(chiefComplaint.getSymptom());
        viewHolder.durationView.setText(chiefComplaint.getDuration() + chiefComplaint.getDuration_unit());

        return convertView;
    }

    public final class ViewHolder{
        public TextView symptomView;
        public TextView durationView;
    }
}
