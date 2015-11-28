package com.fansen.phr.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.fansen.phr.entity.LabReport;

import java.util.List;

/**
 * Created by 310078142 on 2015/11/28.
 */
public class LabReportListAdapter extends RecyclerView.Adapter<LabReportListAdapter.LabReportListViewHolder>{
    private List<LabReport> labReportList;
    private LabReportItemClickedListener labReportItemClickedListener;

    public LabReportListAdapter(List<LabReport> labReports,LabReportItemClickedListener labReportItemClickedListener) {
        this.labReportList = labReports;
        this.labReportItemClickedListener = labReportItemClickedListener;
    }

    @Override
    public LabReportListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(LabReportListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setLabReportItemClickedListener(LabReportItemClickedListener labReportItemClickedListener){
        this.labReportItemClickedListener = labReportItemClickedListener;
    }

    public class LabReportListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public LabReportListViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {
            labReportItemClickedListener.itemClicked(v, getAdapterPosition());
        }
    }

    public interface LabReportItemClickedListener{
        public void itemClicked(View v, int position);
    }
}
