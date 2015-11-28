package com.fansen.phr.fragment.details;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fansen.phr.R;
import com.fansen.phr.adapter.ClinicalDocumentCaptureImageAdapter;
import com.fansen.phr.adapter.ImageAdapterModel;
import com.fansen.phr.adapter.LabResultItemListAdapter;
import com.fansen.phr.entity.DiagnosticImage;
import com.fansen.phr.entity.LabObservation;
import com.fansen.phr.entity.LabReport;
import com.fansen.phr.entity.OrderCodeDef;
import com.fansen.phr.entity.SpecimenTypeCodeDef;
import com.fansen.phr.utils.FileUtil;
import com.fansen.phr.utils.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class LabReportDetailFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener{
    public static final String BUNDLE_KEY_CURRENT_LAB_REPORT = "current_lab_report";
    public static final String BUNDLE_KEY_IS_EDITING = "is_editing_mode";

    private Context context;
    private OnLabReportDataChangeListener onLabReportDataChangeListener;

    private ClinicalDocumentCaptureImageAdapter labRefImagesGridViewAdapter;
    private List<ImageAdapterModel> imageAdapterModelList = new ArrayList<>();
    private List<DiagnosticImage> refImageList = new ArrayList<>();
    private LabResultItemListAdapter labResultItemListAdapter;
    private List<LabObservation> labObservations = new ArrayList<>();

    private LabReport labReport = null;
    private boolean isEditingMode = false;

    private RelativeLayout labReportFragmentLayout;
    private TextView labReportOrderTextView;
    private TextView labReportSpecimenTypeTextView;
    private TextView specimenCollectingDateTextView;
    private TextView reportingDateTextView;
    private ListView labResultItemListView;
    private Button addLabResultItemBtn;
    private GridView labReportRefImgGridView;
    private Button addRefImgBtn;

    private Calendar cal = Calendar.getInstance();

    private DatePickerDialog.OnDateSetListener collectingDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateCollectingDate();
        }
    };

    private DatePickerDialog.OnDateSetListener reportingDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateReportingDate();
        }
    };


    public static LabReportDetailFragment newInstance(LabReport labReport, boolean isEditingMode){
        LabReportDetailFragment labReportDetailFragment = new LabReportDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_KEY_CURRENT_LAB_REPORT, labReport);
        bundle.putBoolean(BUNDLE_KEY_IS_EDITING, isEditingMode);
        labReportDetailFragment.setArguments(bundle);

        return labReportDetailFragment;
    }

    public LabReportDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        context = getActivity();

        if (getArguments() != null){
            labReport = (LabReport) getArguments().getSerializable(BUNDLE_KEY_CURRENT_LAB_REPORT);
            isEditingMode = getArguments().getBoolean(BUNDLE_KEY_IS_EDITING);
        } else {
            labReport = new LabReport();
        }

        labRefImagesGridViewAdapter = new ClinicalDocumentCaptureImageAdapter(context, imageAdapterModelList, this);
        labResultItemListAdapter = new LabResultItemListAdapter(context, labObservations);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        labReportFragmentLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_lab_report_detail, container, false);

        labReportOrderTextView = (TextView) labReportFragmentLayout.findViewById(R.id.id_lab_report_fragment_order);
        labReportOrderTextView.setOnClickListener(this);

        labReportSpecimenTypeTextView = (TextView) labReportFragmentLayout.findViewById(R.id.id_lab_report_fragment_specimen);
        labReportSpecimenTypeTextView.setOnClickListener(this);

        specimenCollectingDateTextView = (TextView) labReportFragmentLayout.findViewById(R.id.id_lab_report_fragment_specimen_collecting_date);
        specimenCollectingDateTextView.setText(TimeFormat.parseDate(new Date(), "yyyyMMdd"));
        specimenCollectingDateTextView.setOnClickListener(this);

        reportingDateTextView = (TextView) labReportFragmentLayout.findViewById(R.id.id_lab_report_fragment_reporting_date);
        reportingDateTextView.setText(TimeFormat.parseDate(new Date(), "yyyyMMdd"));
        reportingDateTextView.setOnClickListener(this);

        labResultItemListView = (ListView) labReportFragmentLayout.findViewById(R.id.id_lab_result_item_list);
        labResultItemListView.setOnItemClickListener(this);
        RelativeLayout headerLayout = (RelativeLayout) inflater.inflate(R.layout.lab_result_item_list_header_layout, null);
        labResultItemListView.addHeaderView(headerLayout);
        labResultItemListView.setAdapter(labResultItemListAdapter);

        labReportRefImgGridView = (GridView) labReportFragmentLayout.findViewById(R.id.id_lab_report_grid_view);
        labReportRefImgGridView.setOnItemClickListener(this);
        labReportRefImgGridView.setAdapter(labRefImagesGridViewAdapter);

        addLabResultItemBtn = (Button) labReportFragmentLayout.findViewById(R.id.id_lab_result_item_add_btn);
        addLabResultItemBtn.setOnClickListener(this);

        addRefImgBtn = (Button) labReportFragmentLayout.findViewById(R.id.id_lab_report_btn_add_image);
        addRefImgBtn.setOnClickListener(this);

        return labReportFragmentLayout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onLabReportDataChangeListener = (OnLabReportDataChangeListener) context;
        } catch (ClassCastException cce){
            throw new ClassCastException(context.toString()
                    +" must implement OnLabReportDataChangeListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onLabReportDataChangeListener = null;
    }

    public LabReport getLabReport(){
        OrderCodeDef orderCode = new OrderCodeDef();
        orderCode.setName(labReportOrderTextView.getText().toString());
        orderCode.setCode(labReportOrderTextView.getText().toString());

        SpecimenTypeCodeDef specimenTypeCodeDef = new SpecimenTypeCodeDef();
        specimenTypeCodeDef.setName(labReportSpecimenTypeTextView.getText().toString());
        specimenTypeCodeDef.setCode(labReportSpecimenTypeTextView.getText().toString());

        String collectingDate = specimenCollectingDateTextView.getText().toString();
        String reportingDate = reportingDateTextView.getText().toString();

        labReport.setOrderCode(orderCode);
        labReport.setSpecimenTypeCode(specimenTypeCodeDef);
        labReport.setSpecimenCollectedDate(TimeFormat.format("yyyyMMdd", collectingDate));
        labReport.setReportDate(TimeFormat.format("yyyyMMdd", reportingDate));
        labReport.setObservations(labObservations);
        labReport.setReferenceImages(refImageList);

        return labReport;
    }

    private void updateCollectingDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        specimenCollectingDateTextView.setText(simpleDateFormat.format(cal.getTime()));
    }

    private void updateReportingDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        reportingDateTextView.setText(simpleDateFormat.format(cal.getTime()));
    }

    public void setLabReportOrder(String order){
        labReportOrderTextView.setText(order);
    }

    public void setLabReportSpecimenType(String specimenType){
        labReportSpecimenTypeTextView.setText(specimenType);
    }

    public void addLabResultItem(LabObservation labObservation){
        if(labObservation == null){
            return;
        }

        labResultItemListAdapter.addLabObservation(labObservation);
    }

    public void addRefImage(DiagnosticImage diagnosticImage){
        if (diagnosticImage != null){
            ImageAdapterModel imageAdapterModel = new ImageAdapterModel();
            imageAdapterModel.setImagePath(diagnosticImage.getCaptureImageUri());
            imageAdapterModel.setThumbnailBitmap(FileUtil.encodeBytesToBitmap(diagnosticImage.getThumbnailImage()));

            refImageList.add(diagnosticImage);
            labRefImagesGridViewAdapter.addImage(imageAdapterModel);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.id_lab_report_fragment_specimen_collecting_date:
                new DatePickerDialog(context, collectingDateListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                ).show();
                break;
            case R.id.id_lab_report_fragment_reporting_date:
                new DatePickerDialog(context, reportingDateListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                ).show();
                break;
            case R.id.id_lab_result_item_add_btn:
                onLabReportDataChangeListener.onAddLabResultItem();
                break;
            case R.id.id_lab_report_btn_add_image:
                onLabReportDataChangeListener.onAddLabReportRefImage(v);
                break;
            case R.id.id_lab_report_fragment_order:
                onLabReportDataChangeListener.onOrderContentEditing(labReportOrderTextView.getText().toString());
                break;
            case R.id.id_lab_report_fragment_specimen:
                onLabReportDataChangeListener.onSpecimenTextEditing(labReportSpecimenTypeTextView.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public interface OnLabReportDataChangeListener{
        public void onAddLabResultItem();
        public void onAddLabReportRefImage(View v);
        public void onEditLabResultItem();
        public void onViewRefImage();
        public void onOrderContentEditing(String order);
        public void onSpecimenTextEditing(String specimenType);
    }
}
