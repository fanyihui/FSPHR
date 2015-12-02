package com.fansen.phr.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fansen.phr.R;
import com.fansen.phr.activities.ImageViewActivity;
import com.fansen.phr.entity.DiagnosticImage;
import com.fansen.phr.entity.LabReport;
import com.fansen.phr.utils.FileUtil;
import com.fansen.phr.utils.TimeFormat;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yihui Fan on 2015/11/28.
 */
public class LabReportListAdapter extends RecyclerView.Adapter<LabReportListAdapter.LabReportListViewHolder>{
    private List<LabReport> labReportList;
    private LabReportItemClickedListener labReportItemClickedListener;

    public LabReportListAdapter(List<LabReport> labReports,LabReportItemClickedListener labReportItemClickedListener) {
        if (labReports == null){
            labReportList = new ArrayList<>();
        } else {
            this.labReportList = labReports;
        }
        this.labReportItemClickedListener = labReportItemClickedListener;
    }

    public void addLabReport(LabReport labReport){
        labReportList.add(labReport);
        notifyDataSetChanged();
    }

    public void updateLabReport(int position, LabReport labReport){
        if(0 <= position && position <= labReportList.size()){
            labReportList.set(position, labReport);
            notifyItemChanged(position);
        }
    }

    public void removeLabReport(int position){
        if(0 <= position && position <= labReportList.size()){
            labReportList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void setLabReportList(List<LabReport> labReportList){
        this.labReportList = labReportList;
        notifyDataSetChanged();
    }

    public LabReport getLabReport(int position){
        return labReportList.get(position);
    }

    @Override
    public LabReportListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lab_report_list_layout, parent, false);
        return new LabReportListViewHolder(itemView);
        //return null;
    }

    @Override
    public void onBindViewHolder(LabReportListViewHolder holder, int position) {
        LabReport labReport = labReportList.get(position);
        List<DiagnosticImage> diagnosticImages = labReport.getReferenceImages();

        if(diagnosticImages != null && diagnosticImages.size()>0){
            byte[] bytes = diagnosticImages.get(0).getThumbnailImage();
            holder.imageView.setImageBitmap(FileUtil.encodeBytesToBitmap(bytes));
        }

        holder.orderTextView.setText(labReport.getOrderCode().getName());
        holder.specimenTextView.setText(labReport.getSpecimenTypeCode().getName());
        holder.reportingDateTextView.setText(TimeFormat.parseDate(labReport.getSpecimenCollectedDate(), "yyyyMMdd"));
    }

    @Override
    public int getItemCount() {
        return labReportList.size();
    }

    public void setLabReportItemClickedListener(LabReportItemClickedListener labReportItemClickedListener){
        this.labReportItemClickedListener = labReportItemClickedListener;
    }

    public class LabReportListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;
        private TextView orderTextView;
        private TextView specimenTextView;
        private TextView reportingDateTextView;

        public LabReportListViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            imageView = (ImageView) itemView.findViewById(R.id.id_lab_report_list_cardview_image_view);
            orderTextView = (TextView) itemView.findViewById(R.id.id_lab_report_list_cardview_order);
            specimenTextView = (TextView) itemView.findViewById(R.id.id_lab_report_list_cardview_specimen);
            reportingDateTextView = (TextView) itemView.findViewById(R.id.id_lab_report_list_cardview_reporting_date);
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
