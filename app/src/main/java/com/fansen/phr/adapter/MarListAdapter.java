package com.fansen.phr.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fansen.phr.entity.MedicationOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 310078142 on 2015/12/10.
 */
public class MarListAdapter extends BaseAdapter{
    private ArrayList<MedicationOrder> medicationOrderArrayList = new ArrayList<>();

    public MarListAdapter(ArrayList<MedicationOrder> medicationOrderArrayList){
        this.medicationOrderArrayList = medicationOrderArrayList;
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
        return null;
    }

    public class MARViewHolder{

    }
}
