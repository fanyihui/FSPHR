package com.fansen.phr.fragment.details;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fansen.phr.R;
import com.fansen.phr.adapter.ClinicalDocumentCaptureImageAdapter;
import com.fansen.phr.adapter.ImageAdapterModel;
import com.fansen.phr.entity.BodyPartDef;
import com.fansen.phr.entity.DiagnosticImage;
import com.fansen.phr.entity.DiagnosticImagingReport;
import com.fansen.phr.entity.RequestedProcedureTypeDef;
import com.fansen.phr.utils.FileUtil;
import com.fansen.phr.utils.SpinnerUtil;
import com.fansen.phr.utils.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ImagingReportDetailFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener{
    public static final String BUNDLE_KEY_CURRENT_REPORT = "current_diagnostic_imaging_report";
    public static final String BUNDLE_KEY_IS_EDITING = "is_editing_mode";

    private RelativeLayout imagingReportDetailFragmentView;
    private TextView rpDateTextView;
    private TextView rpTypeTextView;
    private TextView bodypartTextView;
    private Spinner modalitySpinner;
    private TextView findingTextView;
    private TextView resultTextView;
    private TextView recommendationTextView;
    private GridView diagnosticImagesGridView;
    private Button addDiagnosticImageBtn;

    private Context context;
    private OnImageReportElementListener onImageReportElementListener;

    private ClinicalDocumentCaptureImageAdapter diagnosticImagesGridViewAdapter;
    private List<ImageAdapterModel> imageAdapterModelList = new ArrayList<>();
    private List<DiagnosticImage> diagnosticImageList = new ArrayList<>();
    private DiagnosticImagingReport diagnosticImagingReport = null;

    private boolean isEditingMode = false;

    private Calendar cal = Calendar.getInstance();

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        }
    };

    public static ImagingReportDetailFragment newInstance(DiagnosticImagingReport diagnosticImagingReport, boolean isEditingMode){
        ImagingReportDetailFragment fragment = new ImagingReportDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_KEY_CURRENT_REPORT, diagnosticImagingReport);
        bundle.putBoolean(BUNDLE_KEY_IS_EDITING, isEditingMode);
        fragment.setArguments(bundle);

        return fragment;
    }

    public ImagingReportDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            diagnosticImagingReport = (DiagnosticImagingReport) getArguments().getSerializable(BUNDLE_KEY_CURRENT_REPORT);
            isEditingMode = getArguments().getBoolean(BUNDLE_KEY_IS_EDITING);
        } else {
            diagnosticImagingReport = new DiagnosticImagingReport();
            isEditingMode = false;
        }

        context = getActivity();
        diagnosticImagesGridViewAdapter = new ClinicalDocumentCaptureImageAdapter(context, imageAdapterModelList, this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            onImageReportElementListener = (OnImageReportElementListener) context;
        }catch(ClassCastException e) {
            throw new ClassCastException(context.toString()
                    +" must implement OnImageReportElementListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onImageReportElementListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        imagingReportDetailFragmentView = (RelativeLayout) inflater.inflate(R.layout.fragment_imaging_report_detail, container, false);

        rpDateTextView = (TextView) imagingReportDetailFragmentView.findViewById(R.id.id_imaging_report_detail_textview_rp_date);
        rpDateTextView.setText(TimeFormat.parseDate(new Date()));
        rpDateTextView.setOnClickListener(this);

        bodypartTextView = (TextView) imagingReportDetailFragmentView.findViewById(R.id.id_imaging_report_detail_textview_body);
        bodypartTextView.setOnClickListener(this);

        modalitySpinner = (Spinner) imagingReportDetailFragmentView.findViewById(R.id.id_imaging_report_detail_modality_spinner);

        rpTypeTextView = (TextView) imagingReportDetailFragmentView.findViewById(R.id.id_imaging_report_detail_textview_rp_type);
        rpTypeTextView.setOnClickListener(this);

        findingTextView = (TextView) imagingReportDetailFragmentView.findViewById(R.id.id_imaging_report_detail_textview_finding);
        findingTextView.setOnClickListener(this);

        resultTextView = (TextView) imagingReportDetailFragmentView.findViewById(R.id.id_imaging_report_detail_textview_result);
        resultTextView.setOnClickListener(this);

        recommendationTextView = (TextView) imagingReportDetailFragmentView.findViewById(R.id.id_imaging_report_detail_textview_recommend);
        recommendationTextView.setOnClickListener(this);

        diagnosticImagesGridView = (GridView) imagingReportDetailFragmentView.findViewById(R.id.id_imaging_report_detail_grid_view);
        diagnosticImagesGridView.setOnItemClickListener(this);
        diagnosticImagesGridView.setAdapter(diagnosticImagesGridViewAdapter);

        addDiagnosticImageBtn = (Button) imagingReportDetailFragmentView.findViewById(R.id.id_imaging_report_detail_btn_add_image);
        addDiagnosticImageBtn.setOnClickListener(this);

        if (isEditingMode){
            setRpType(diagnosticImagingReport.getRequestedProcedureTypeDef().getName());
            setBodypart(diagnosticImagingReport.getBodypart().getName());
            setModality(diagnosticImagingReport.getModality());
            setImagingFinding(diagnosticImagingReport.getFindings());
            setImagingResult(diagnosticImagingReport.getResult());
            setImagingRecommendation(diagnosticImagingReport.getRecommendation());
            setRpDate(diagnosticImagingReport.getRequestedProcedureDate());
            List<DiagnosticImage> diagnosticImages = diagnosticImagingReport.getDiagnosticImages();
            for (int i=0; i< diagnosticImages.size(); i++){
                DiagnosticImage diagnosticImage = diagnosticImages.get(i);
                addDiagnosticImage(diagnosticImage);
            }
        }

        return imagingReportDetailFragmentView;
    }

    public void addDiagnosticImage(DiagnosticImage diagnosticImage){
        if (diagnosticImage != null){
            ImageAdapterModel imageAdapterModel = new ImageAdapterModel();
            imageAdapterModel.setImagePath(diagnosticImage.getCaptureImageUri());
            imageAdapterModel.setThumbnailBitmap(FileUtil.encodeBytesToBitmap(diagnosticImage.getThumbnailImage()));

            diagnosticImageList.add(diagnosticImage);
            diagnosticImagesGridViewAdapter.addImage(imageAdapterModel);
        }
    }

    public DiagnosticImagingReport getDiagnosticImagingReport(){
        RequestedProcedureTypeDef rpDef = new RequestedProcedureTypeDef();
        rpDef.setName(rpTypeTextView.getText().toString());
        rpDef.setCode(rpTypeTextView.getText().toString());

        BodyPartDef bodyPartDef = new BodyPartDef();
        bodyPartDef.setName(bodypartTextView.getText().toString());
        bodyPartDef.setCode(bodypartTextView.getText().toString());

        diagnosticImagingReport.setDiagnosticImages(diagnosticImageList);
        diagnosticImagingReport.setFindings(findingTextView.getText().toString());
        diagnosticImagingReport.setResult(resultTextView.getText().toString());
        diagnosticImagingReport.setRecommendation(recommendationTextView.getText().toString());
        diagnosticImagingReport.setModality(modalitySpinner.getSelectedItem().toString());
        diagnosticImagingReport.setRequestedProcedureDate(TimeFormat.format(rpDateTextView.getText().toString()));
        diagnosticImagingReport.setRequestedProcedureTypeDef(rpDef);
        diagnosticImagingReport.setBodypart(bodyPartDef);

        //TODO add code here to set imaging observation list;

        return diagnosticImagingReport;
    }

    public void setDiagnosticImagingReport(DiagnosticImagingReport diagnosticImagingReport){
        this.diagnosticImagingReport = diagnosticImagingReport;
    }

    public Bitmap getFirstImage(){
        ImageAdapterModel imageAdapterModel = (ImageAdapterModel) diagnosticImagesGridViewAdapter.getItem(0);

        return imageAdapterModel.getThumbnailBitmap();
    }

    public void setImagingResult(String result){
        resultTextView.setText(result);
    }

    public void setImagingFinding(String finding){
        findingTextView.setText(finding);
    }

    public void setModality(String modality){
        SpinnerUtil.setSpinnerItemSelectedByValue(modalitySpinner, modality);
    }

    public void setBodypart(String bodypart){
        bodypartTextView.setText(bodypart);
    }

    public void setRpType(String rpType){
        rpTypeTextView.setText(rpType);
    }

    public void setImagingRecommendation(String recommendation){
        recommendationTextView.setText(recommendation);
    }

    public void setRpDate(Date rpDate){
        rpDateTextView.setText(TimeFormat.parseDate(rpDate));
    }

    public void setDiagnosticImageList(){
        //TODO add code here
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.id_imaging_report_detail_textview_rp_type:
                onImageReportElementListener.onEditingRPType(rpTypeTextView.getText().toString());
                break;
            case R.id.id_imaging_report_detail_textview_finding:
                onImageReportElementListener.onEditingFinding(findingTextView.getText().toString());
                break;
            case R.id.id_imaging_report_detail_textview_result:
                onImageReportElementListener.onEditingResult(resultTextView.getText().toString());
                break;
            case R.id.id_imaging_report_detail_textview_recommend:
                onImageReportElementListener.onEditingRecommendation(recommendationTextView.getText().toString());
                break;
            case R.id.id_imaging_report_detail_textview_body:
                onImageReportElementListener.onEditingBodypart(bodypartTextView.getText().toString());
                break;
            case R.id.id_imaging_report_detail_textview_rp_date:
                new DatePickerDialog(context, listener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                ).show();
                break;
            case R.id.id_imaging_report_detail_btn_add_image:
                onImageReportElementListener.onAddingDiagnosticImage(v);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageAdapterModel imageAdapterModel = (ImageAdapterModel)diagnosticImagesGridViewAdapter.getItem(position);
        String path = imageAdapterModel.getImagePath();
        onImageReportElementListener.onViewDiagnosticImage(path);
    }

    private void updateDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        rpDateTextView.setText(simpleDateFormat.format(cal.getTime()));
    }

    public interface OnImageReportElementListener {
        public void onViewDiagnosticImage(String imagePath);
        public void onEditingRPType(String rpType);
        public void onEditingFinding(String finding);
        public void onEditingResult(String result);
        public void onEditingRecommendation(String recommendation);
        public void onAddingDiagnosticImage(View v);
        public void onEditingBodypart(String bodypart);
    }
}
