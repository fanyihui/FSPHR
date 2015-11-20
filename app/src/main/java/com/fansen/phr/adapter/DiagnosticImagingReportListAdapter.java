package com.fansen.phr.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fansen.phr.R;
import com.fansen.phr.entity.DiagnosticImage;
import com.fansen.phr.entity.DiagnosticImagingReport;
import com.fansen.phr.utils.FileUtil;
import com.fansen.phr.utils.TimeFormat;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yihui Fan on 2015/11/15.
 */
public class DiagnosticImagingReportListAdapter extends RecyclerView.Adapter<DiagnosticImagingReportListAdapter.DiagnosticImagingReportListViewHolder>{
    private List<DiagnosticImagingReport> diagnosticImagingReportList;
    private DiagnosticImagingReportItemClickListener diagnosticImagingReportItemClickListener;

    public DiagnosticImagingReportListAdapter(List<DiagnosticImagingReport> diagnosticImagingReportList) {
        if (diagnosticImagingReportList == null){
            this.diagnosticImagingReportList = new ArrayList<>();
        } else{
            this.diagnosticImagingReportList = diagnosticImagingReportList;
        }
    }

    public void addDiagnosticImagingReport(DiagnosticImagingReport diagnosticImagingReport){
        diagnosticImagingReportList.add(diagnosticImagingReport);
        notifyDataSetChanged();
    }

    public void updateDiagnosticImagingReport(int position, DiagnosticImagingReport diagnosticImagingReport){
        if (0 <= position && position <= diagnosticImagingReportList.size()) {
            diagnosticImagingReportList.set(position, diagnosticImagingReport);
            notifyItemChanged(position, diagnosticImagingReport);
        }
    }

    public DiagnosticImagingReport getDiagnosticImagingReport(int position){
        return diagnosticImagingReportList.get(position);
    }

    public void removeDiagnosticImagingReport(int position){
        if (0 <= position && position <= diagnosticImagingReportList.size()) {
            diagnosticImagingReportList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void setDiagnosticImagingReportList(List<DiagnosticImagingReport> diagnosticImagingReportList) {
        this.diagnosticImagingReportList = diagnosticImagingReportList;
        notifyDataSetChanged();
    }

    public void setDiagnosticImagingReportItemClickListener(DiagnosticImagingReportItemClickListener diagnosticImagingReportItemClickListener) {
        this.diagnosticImagingReportItemClickListener = diagnosticImagingReportItemClickListener;
    }

    @Override
    public DiagnosticImagingReportListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_requested_procedure_layout, parent, false);
        return new DiagnosticImagingReportListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DiagnosticImagingReportListViewHolder holder, int position) {
        DiagnosticImagingReport diagnosticImagingReport = diagnosticImagingReportList.get(position);

        List<DiagnosticImage> diagnosticImages = diagnosticImagingReport.getDiagnosticImages();

        if(diagnosticImages != null && diagnosticImages.size()>0){
            byte[] bytes = diagnosticImages.get(0).getThumbnailImage();
            holder.diImageView.setImageBitmap(FileUtil.encodeBytesToBitmap(bytes));
        }

        holder.diDateTextView.setText(TimeFormat.parseDate(diagnosticImagingReport.getRequestedProcedureDate(), "yyyyMMdd"));
        holder.diResultTextView.setText(diagnosticImagingReport.getResult());
        holder.diModalityTextView.setText(diagnosticImagingReport.getModality());
        holder.diRpTypeTextView.setText(diagnosticImagingReport.getRequestedProcedureTypeDef().getName());
        holder.bodypartTextView.setText(diagnosticImagingReport.getBodypart().getName());
    }

    @Override
    public int getItemCount() {
        return diagnosticImagingReportList.size();
    }

    public class DiagnosticImagingReportListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView diImageView;
        private TextView diDateTextView;
        private TextView diResultTextView;
        private TextView diModalityTextView;
        private TextView diRpTypeTextView;
        private TextView bodypartTextView;

        public DiagnosticImagingReportListViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            diImageView = (ImageView) itemView.findViewById(R.id.id_rp_cardview_image_view);
            diDateTextView = (TextView) itemView.findViewById(R.id.id_rp_cardview_date_view);
            diResultTextView = (TextView) itemView.findViewById(R.id.id_rp_cardview_result_view);
            diModalityTextView = (TextView) itemView.findViewById(R.id.id_rp_cardview_modality_view);
            diRpTypeTextView = (TextView) itemView.findViewById(R.id.id_rp_cardview_rptype_view);
            bodypartTextView = (TextView) itemView.findViewById(R.id.id_rp_cardview_bodypart_view);
        }

        @Override
        public void onClick(View v) {
            if (diagnosticImagingReportItemClickListener != null){
                diagnosticImagingReportItemClickListener.itemClicked(v, getAdapterPosition());
            }
        }
    }


    public interface DiagnosticImagingReportItemClickListener {
        public void itemClicked(View v, int position);
    }
}
