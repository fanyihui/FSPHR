package com.fansen.phr.activities;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fansen.phr.R;
import com.fansen.phr.entity.DiagnosticImage;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.entity.LabObservation;
import com.fansen.phr.entity.LabReport;
import com.fansen.phr.fragment.details.LabReportDetailFragment;
import com.fansen.phr.utils.DensityUtil;
import com.fansen.phr.utils.FileUtil;
import com.fansen.phr.utils.ImageUtil;
import com.fansen.phr.utils.TimeFormat;
import com.fansen.phr.view.SelectPicPopWindow;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class LabReportDetailActivity extends AppCompatActivity implements LabReportDetailFragment.OnLabReportDataChangeListener, View.OnClickListener{
    public static final String BUNDEL_KEY_LAB_REPORT = "lab_report";
    public static final String BUNDLE_KEY_IS_EDITING = "is_editing_mode";

    public static final int REQUEST_CODE_EDITING_ORDER = 3001;
    public static final int REQUEST_CODE_EDITING_SPECIMEN = 3002;
    public static final int REQUEST_CODE_ADD_LAB_ITEM = 3003;
    public static final int REQUEST_CODE_LAB_TAKE_PHOTO = 3004;
    public static final int REQUEST_CODE_LAB_SELECT_PHOTO = 3005;
    private Context context;
    LabReportDetailFragment labReportDetailFragment;

    private Encounter currentEncounter;
    private LabReport currentLabReport;

    private String currentImageFilePath;

    SelectPicPopWindow popWindow;

    FragmentManager fragmentManager;
    private int gridViewColumnWidth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_report_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        context = this;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        currentEncounter = (Encounter) bundle.getSerializable(EncounterDetailActivity.BUNDLE_KEY_SELECTED_ENCOUNTER);

        Object object = bundle.getSerializable(BUNDEL_KEY_LAB_REPORT);
        boolean isEditingReport = false;

        if (object != null){
            currentLabReport = (LabReport) object;
            isEditingReport = true;
        } else {
            currentLabReport = new LabReport();
        }

        labReportDetailFragment = LabReportDetailFragment.newInstance(currentLabReport, isEditingReport);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.id_lab_report_fragment_container, labReportDetailFragment);
        fragmentTransaction.commit();

        gridViewColumnWidth = DensityUtil.dip2px(context, getResources().getDimension(R.dimen.grid_view_column_width));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lab_report_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_submit_lab_report){
            LabReport labReport = labReportDetailFragment.getLabReport();

            Intent data = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(BUNDEL_KEY_LAB_REPORT, labReport);
            data.putExtras(bundle);

            setResult(RESULT_OK, data);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE_EDITING_ORDER:
                    labReportDetailFragment.setLabReportOrder(data.getExtras().getString(FreeTextEditActivity.BUNDLE_KEY_FREE_TEXT_VALUE));
                    break;
                case REQUEST_CODE_EDITING_SPECIMEN:
                    labReportDetailFragment.setLabReportSpecimenType(data.getExtras().getString(FreeTextEditActivity.BUNDLE_KEY_FREE_TEXT_VALUE));
                    break;
                case REQUEST_CODE_ADD_LAB_ITEM:
                    handleAddLabResultItemIntent(data);
                    break;
                case REQUEST_CODE_LAB_TAKE_PHOTO:
                    handleAddRefImageIntent();
                    break;
                case REQUEST_CODE_LAB_SELECT_PHOTO:
                    handleSelectRefImageIntent(data);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onAddLabResultItem() {
        Intent intent = new Intent(context,LabResultItemEditingActivity.class);

        startActivityForResult(intent, REQUEST_CODE_ADD_LAB_ITEM);
    }

    @Override
    public void onAddLabReportRefImage(View v) {
        popWindow = new SelectPicPopWindow(this, this);
        popWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void onEditLabResultItem() {

    }

    @Override
    public void onViewRefImage(String imagePath) {
        Intent intent = new Intent(context, ImageViewActivity.class);
        intent.putExtra(ImageViewActivity.BUNDLE_KEY_VIEW_IMAGE, imagePath);

        startActivity(intent);
    }

    @Override
    public void onOrderContentEditing(String order) {
        String hint = getResources().getString(R.string.hint_lab_report_order);
        dispatchFreeTextEditingIntent(order, hint, REQUEST_CODE_EDITING_ORDER);
    }

    @Override
    public void onSpecimenTextEditing(String specimenType) {
        String hint = getResources().getString(R.string.hint_lab_report_specimen);;
        dispatchFreeTextEditingIntent(specimenType, hint, REQUEST_CODE_EDITING_SPECIMEN);
    }

    private void dispatchFreeTextEditingIntent(String value, String hint, int requestCode){
        Intent intent = new Intent(context, FreeTextEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(FreeTextEditActivity.BUNDLE_KEY_FREE_TEXT_VALUE, value);
        bundle.putString(FreeTextEditActivity.BUNDLE_KEY_FREE_TEXT_HINT, hint);
        bundle.putString(FreeTextEditActivity.BUNDLE_KEY_FREE_TEXT_TITLE, hint);

        intent.putExtras(bundle);

        startActivityForResult(intent, requestCode);
    }

    private void handleAddLabResultItemIntent(Intent data){
        LabObservation labObservation = (LabObservation) data.getExtras().getSerializable(LabResultItemEditingActivity.BUNDLE_KEY_LAB_OBSERVATION);

        labReportDetailFragment.addLabResultItem(labObservation);
    }

    private void handleAddRefImageIntent(){
        try {
            DiagnosticImage diagnosticImage = new DiagnosticImage();

            final File thumFile = FileUtil.createClinicalDocImageThumFile(currentImageFilePath);
            final Bitmap bitmap = ImageUtil.getSquareBitmap(currentImageFilePath, gridViewColumnWidth);

            diagnosticImage.setCaptureImageUri(currentImageFilePath);
            diagnosticImage.setThumbnailImageUri(thumFile.getAbsolutePath());
            diagnosticImage.setCreatingDate(TimeFormat.parseDate(new Date(), "yyyyMMdd"));
            diagnosticImage.setThumbnailImage(FileUtil.decodeBitmapToBytes(bitmap));

            labReportDetailFragment.addRefImage(diagnosticImage);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    FileUtil.saveBitmapToFile(bitmap, thumFile);
                }
            });
            thread.start();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    private void handleSelectRefImageIntent(Intent data){
        Uri selectedImage = data.getData();
        ContentResolver contentResolver = context.getContentResolver();

        try{
            DiagnosticImage diagnosticImage = new DiagnosticImage();

            final File tempFile = FileUtil.createClinicalDocImageFile(context, currentEncounter.getEncounter_key());
            final File thumFile = FileUtil.createClinicalDocImageThumFile(tempFile.getAbsolutePath());
            final Bitmap bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage);
            final Bitmap bm = ImageUtil.getBitmap(bitmap, gridViewColumnWidth, gridViewColumnWidth);

            currentImageFilePath = tempFile.getAbsolutePath();

            diagnosticImage.setThumbnailImageUri(thumFile.getAbsolutePath());
            diagnosticImage.setCaptureImageUri(currentImageFilePath);
            diagnosticImage.setCreatingDate(TimeFormat.parseDate(new Date(), "yyyyMMdd"));
            diagnosticImage.setThumbnailImage(FileUtil.decodeBitmapToBytes(bm));

            labReportDetailFragment.addRefImage(diagnosticImage);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    FileUtil.saveBitmapToFile(bitmap, tempFile);
                    FileUtil.saveBitmapToFile(bm, thumFile);
                    bitmap.recycle();
                }
            });

            thread.start();

        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.btn_pick_photo:
                dispatchSelectPicturesIntent();
                popWindow.dismiss();
                break;
            case R.id.btn_take_photo:
                dispatchTakePictureIntent();
                popWindow.dismiss();
                break;
            default:
                break;
        }
    }

    private void dispatchTakePictureIntent(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imageFile = null;

        try{
            imageFile = FileUtil.createClinicalDocImageFile(context, currentEncounter.getEncounter_key());
            currentImageFilePath = imageFile.getAbsolutePath();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }

        if (imageFile !=null){
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        }

        startActivityForResult(intent, REQUEST_CODE_LAB_TAKE_PHOTO);
    }

    private void dispatchSelectPicturesIntent(){
        Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
        intent1.addCategory(Intent.CATEGORY_OPENABLE);
        intent1.setType("image/jpeg");
        startActivityForResult(intent1, REQUEST_CODE_LAB_SELECT_PHOTO);
    }
}
