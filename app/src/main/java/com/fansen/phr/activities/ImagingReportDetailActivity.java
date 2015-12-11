package com.fansen.phr.activities;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fansen.phr.R;
import com.fansen.phr.entity.DiagnosticImage;
import com.fansen.phr.entity.DiagnosticImagingReport;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.fragment.details.ImagingReportDetailFragment;
import com.fansen.phr.utils.DensityUtil;
import com.fansen.phr.utils.FileUtil;
import com.fansen.phr.utils.ImageUtil;
import com.fansen.phr.utils.TimeFormat;
import com.fansen.phr.view.SelectPicPopWindow;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class ImagingReportDetailActivity extends AppCompatActivity implements ImagingReportDetailFragment.OnImageReportElementListener, View.OnClickListener{
    public static final String BUNDLE_KEY_DI_REPORT = "diagnostic_imaging_report";
    public static final int REQUEST_CODE_EDIT_FINDING = 2001;
    public static final int REQUEST_CODE_EDIT_RESULT = 2002;
    public static final int REQUEST_CODE_EDIT_RECOMMEND = 2003;
    public static final int REQUEST_CODE_EDIT_RP_TYPE = 2004;
    public static final int REQUEST_CODE_TAKE_PHOTO = 2005;
    public static final int REQUEST_CODE_SELECT_PHOTO = 2006;
    public static final int REQUEST_CODE_EDIT_BODY = 2007;

    private Context context;
    private ImagingReportDetailFragment imagingReportDetailFragment;

    private Encounter currentEncounter;
    private DiagnosticImagingReport currentDiagnosticImagingReport;

    private String currentImageFilePath;

    SelectPicPopWindow popWindow;

    android.support.v4.app.FragmentManager fragmentManager;
    private int gridViewColumnWidth = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imaging_report_detail);
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
        Object object = bundle.getSerializable(EncounterDetailActivity.BUNDLE_KEY_SELECTED_REPORT);
        boolean isEditingReport = false;

        if(object != null){
            currentDiagnosticImagingReport = (DiagnosticImagingReport) object;
            isEditingReport = true;
        } else {
            currentDiagnosticImagingReport = new DiagnosticImagingReport();
        }

        imagingReportDetailFragment = ImagingReportDetailFragment.newInstance(currentDiagnosticImagingReport, isEditingReport);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.imaging_report_fragment_container, imagingReportDetailFragment);
        fragmentTransaction.commit();

        gridViewColumnWidth = DensityUtil.dip2px(context, getResources().getDimension(R.dimen.grid_view_column_width));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_imaging_report_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_submit_imaging_report){
            DiagnosticImagingReport diagnosticImagingReport = imagingReportDetailFragment.getDiagnosticImagingReport();

            Intent data = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(BUNDLE_KEY_DI_REPORT, diagnosticImagingReport);

            data.putExtras(bundle);
            setResult(RESULT_OK, data);

            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Following overide the interface of OnImageReportElementListener from ImagingReportDetailFragment
     * */
    @Override
    public void onViewDiagnosticImage(String imagePath) {
        Intent intent = new Intent(context, ImageViewActivity.class);
        intent.putExtra(ImageViewActivity.BUNDLE_KEY_VIEW_IMAGE, imagePath);

        startActivity(intent);
    }

    @Override
    public void onEditingFinding(String finding) {
        String hint = getResources().getString(R.string.hint_diagnostic_imaging_finding);
        dispatchFreeTextEditingIntent(finding, hint, REQUEST_CODE_EDIT_FINDING);
    }

    @Override
    public void onEditingResult(String result) {
        String hint = getResources().getString(R.string.hint_diagnostic_imaging_result);
        dispatchFreeTextEditingIntent(result, hint, REQUEST_CODE_EDIT_RESULT);
    }

    @Override
    public void onEditingRecommendation(String recommendation) {
        String hint = getResources().getString(R.string.hint_diagnostic_imaging_recommendation);
        dispatchFreeTextEditingIntent(recommendation, hint, REQUEST_CODE_EDIT_RECOMMEND);
    }

    @Override
    public void onAddingDiagnosticImage(View v) {
        popWindow = new SelectPicPopWindow(this, this);
        popWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void onEditingRPType(String rpType) {
        String hint = getResources().getString(R.string.hint_diagnostic_imaging_rp_type);
        dispatchFreeTextEditingIntent(rpType, hint, REQUEST_CODE_EDIT_RP_TYPE);
    }

    @Override
    public void onEditingBodypart(String bodypart) {
        String hint = getResources().getString(R.string.hint_diagnostic_imaging_body);
        dispatchFreeTextEditingIntent(bodypart, hint, REQUEST_CODE_EDIT_BODY);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE_EDIT_FINDING:
                    imagingReportDetailFragment.setImagingFinding(data.getExtras().getString(FreeTextEditActivity.BUNDLE_KEY_FREE_TEXT_VALUE));
                    break;
                case REQUEST_CODE_EDIT_RESULT:
                    imagingReportDetailFragment.setImagingResult(data.getExtras().getString(FreeTextEditActivity.BUNDLE_KEY_FREE_TEXT_VALUE));
                    break;
                case REQUEST_CODE_EDIT_RECOMMEND:
                    imagingReportDetailFragment.setImagingRecommendation(data.getExtras().getString(FreeTextEditActivity.BUNDLE_KEY_FREE_TEXT_VALUE));
                    break;
                case REQUEST_CODE_EDIT_RP_TYPE:
                    imagingReportDetailFragment.setRpType(data.getExtras().getString(FreeTextEditActivity.BUNDLE_KEY_FREE_TEXT_VALUE));
                    break;
                case REQUEST_CODE_EDIT_BODY:
                    imagingReportDetailFragment.setBodypart(data.getExtras().getString(FreeTextEditActivity.BUNDLE_KEY_FREE_TEXT_VALUE));
                    break;
                case REQUEST_CODE_TAKE_PHOTO:
                    handleTakePhoto();
                    break;
                case REQUEST_CODE_SELECT_PHOTO:
                    handleSelectedPhoto(data);
                    break;
                default:
                    break;
            }
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

        startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
    }

    private void dispatchSelectPicturesIntent(){
        Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
        intent1.addCategory(Intent.CATEGORY_OPENABLE);
        intent1.setType("image/jpeg");
        startActivityForResult(intent1, REQUEST_CODE_SELECT_PHOTO);
    }

    private void handleTakePhoto(){
        try {
            DiagnosticImage diagnosticImage = new DiagnosticImage();

            final File thumFile = FileUtil.createClinicalDocImageThumFile(currentImageFilePath);
            final Bitmap bitmap = ImageUtil.getSquareBitmap(currentImageFilePath, gridViewColumnWidth);

            diagnosticImage.setCaptureImageUri(currentImageFilePath);
            diagnosticImage.setThumbnailImageUri(thumFile.getAbsolutePath());
            diagnosticImage.setCreatingDate(TimeFormat.parseDate(new Date(), "yyyyMMdd"));
            diagnosticImage.setThumbnailImage(FileUtil.decodeBitmapToBytes(bitmap));

            imagingReportDetailFragment.addDiagnosticImage(diagnosticImage);

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

    private void handleSelectedPhoto(Intent data){
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

            imagingReportDetailFragment.addDiagnosticImage(diagnosticImage);

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
}
