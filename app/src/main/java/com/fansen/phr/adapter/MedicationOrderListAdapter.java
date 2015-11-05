package com.fansen.phr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fansen.phr.R;
import com.fansen.phr.entity.MedicationOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 310078142 on 2015/11/3.
 */
public class MedicationOrderListAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater layoutInflater;

    private List<MedicationOrder> medicationOrders;


    public MedicationOrderListAdapter(Context context, List<MedicationOrder> medicationOrders) {
        this.context = context;
        this.medicationOrders = medicationOrders;

        layoutInflater = LayoutInflater.from(context);
    }

    public void addMedicationOrder(MedicationOrder medicationOrder){
        medicationOrders.add(medicationOrder);
        notifyDataSetChanged();
    }

    public void updateMedicationOrder(int position, MedicationOrder medicationOrder){
        medicationOrders.set(position, medicationOrder);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (medicationOrders == null){
            medicationOrders = new ArrayList<>();
        }

        return medicationOrders.size();
    }

    @Override
    public Object getItem(int position) {
        return medicationOrders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_medication_order_layout, null);

            viewHolder = new ViewHolder();
            viewHolder.medicationNameTextView = (TextView) convertView.findViewById(R.id.id_med_order_item_name);
            viewHolder.medicationSpecTextView = (TextView) convertView.findViewById(R.id.id_med_order_item_spec);
            viewHolder.quantityTextView = (TextView) convertView.findViewById(R.id.id_med_order_item_quantity);
            viewHolder.quantityUnitTextView = (TextView) convertView.findViewById(R.id.id_med_order_item_quantity_unit);
            viewHolder.intervalTextView = (TextView) convertView.findViewById(R.id.id_med_order_item_interval);
            viewHolder.intervalUnitTextView = (TextView) convertView.findViewById(R.id.id_med_order_item_interval_unit);
            viewHolder.timesTextView = (TextView) convertView.findViewById(R.id.id_med_order_item_times);
            viewHolder.dosageTextView = (TextView) convertView.findViewById(R.id.id_med_order_item_dosage);
            viewHolder.dosageUnitTextView = (TextView) convertView.findViewById(R.id.id_med_order_item_dosage_unit);
            viewHolder.routeTextView = (TextView) convertView.findViewById(R.id.id_med_order_item_route);

            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MedicationOrder medicationOrder = medicationOrders.get(position);
        viewHolder.medicationNameTextView.setText(medicationOrder.getMedication().getName());
        viewHolder.medicationSpecTextView.setText(medicationOrder.getMedication().getSpec());
        viewHolder.quantityTextView.setText(medicationOrder.getQuantity()+"");
        viewHolder.quantityUnitTextView.setText(medicationOrder.getQuantity_unit());
        viewHolder.intervalTextView.setText(medicationOrder.getFrequency_interval()+"");
        viewHolder.intervalUnitTextView.setText(medicationOrder.getFrequency_interval_unit());
        viewHolder.timesTextView.setText(medicationOrder.getFrequency_times()+"");
        viewHolder.dosageTextView.setText(medicationOrder.getDosage()+"");
        viewHolder.dosageUnitTextView.setText(medicationOrder.getDosage_unit());
        viewHolder.routeTextView.setText(medicationOrder.getRoute());

        return convertView;
    }

    public final class ViewHolder{
        TextView medicationNameTextView;
        TextView medicationSpecTextView;
        TextView quantityTextView;
        TextView quantityUnitTextView;
        TextView intervalTextView;
        TextView intervalUnitTextView;
        TextView timesTextView;
        TextView dosageTextView;
        TextView dosageUnitTextView;
        TextView routeTextView;
    }
}
