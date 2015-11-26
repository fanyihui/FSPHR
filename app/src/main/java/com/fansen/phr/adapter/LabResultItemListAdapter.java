package com.fansen.phr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fansen.phr.R;
import com.fansen.phr.entity.LabObservation;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 310078142 on 2015/11/25.
 */
public class LabResultItemListAdapter extends BaseAdapter{
    private Context context;
    private List<LabObservation> labObservations;
    private LayoutInflater layoutInflater;

    public LabResultItemListAdapter(Context context, List<LabObservation> labObservations){
        this.context = context;
        if (labObservations == null){
            labObservations = new ArrayList<>();
        } else {
            this.labObservations = labObservations;
        }

        layoutInflater = LayoutInflater.from(context);
    }

    public void addLabObservation(LabObservation labObservation){
        if (labObservation != null){
            labObservations.add(labObservation);
            notifyDataSetChanged();
        }
    }

    public void updateLabObservation(int position, LabObservation labObservation){
        if(labObservation != null && position >=0){
            labObservations.set(position, labObservation);
            notifyDataSetChanged();
        }
    }

    public void removeLabObservation(int position){
        if(position >=0){
            labObservations.remove(position);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return labObservations.size();
    }

    @Override
    public Object getItem(int position) {
        return labObservations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_lab_result_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.codeTextView = (TextView) convertView.findViewById(R.id.id_lab_result_item_list_code);
            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.id_lab_result_item_list_name);
            viewHolder.valueTextView = (TextView) convertView.findViewById(R.id.id_lab_result_item_list_value);
            viewHolder.unitTextView = (TextView) convertView.findViewById(R.id.id_lab_result_item_list_unit);
            viewHolder.rangeTextView = (TextView) convertView.findViewById(R.id.id_lab_result_item_list_range);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LabObservation labObservation = labObservations.get(position);
        viewHolder.codeTextView.setText(labObservation.getObservationDef().getCode());
        viewHolder.nameTextView.setText(labObservation.getObservationDef().getName());
        viewHolder.valueTextView.setText(labObservation.getValue());
        viewHolder.unitTextView.setText(labObservation.getUnit());
        viewHolder.rangeTextView.setText(labObservation.getObservationDef().getNormalRange());

        return convertView;
    }

    public final class ViewHolder{
        protected TextView codeTextView;
        protected TextView nameTextView;
        protected TextView valueTextView;
        protected TextView unitTextView;
        protected TextView rangeTextView;
    }
}
