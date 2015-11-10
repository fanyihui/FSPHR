package com.fansen.phr.fragment.details;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fansen.phr.R;
import com.fansen.phr.activities.MedicationOrderEditActivity;
import com.fansen.phr.adapter.ClinicalDocumentCaptureImageAdapter;
import com.fansen.phr.adapter.ImageAdapterModel;
import com.fansen.phr.adapter.MedicationOrderListAdapter;
import com.fansen.phr.entity.ClinicalDocument;
import com.fansen.phr.entity.ClinicalDocumentType;
import com.fansen.phr.entity.Encounter;
import com.fansen.phr.entity.MedicationDict;
import com.fansen.phr.entity.MedicationOrder;
import com.fansen.phr.entity.Physician;
import com.fansen.phr.service.IClinicalDocumentService;
import com.fansen.phr.service.IMedicationDictService;
import com.fansen.phr.service.IMedicationOrderService;
import com.fansen.phr.service.implementation.ClinicalDocumentServiceLocalImpl;
import com.fansen.phr.service.implementation.MedicationDictServiceLocalImpl;
import com.fansen.phr.service.implementation.MedicationOrderServiceLocalImpl;
import com.fansen.phr.utils.FileUtil;
import com.fansen.phr.utils.TimeFormat;
import com.fansen.phr.view.SelectPicPopWindow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PrescriptionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PrescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrescriptionFragment extends Fragment implements View.OnClickListener {
    public static final int ADD_MED_REQUEST = 7;
    public static final int EDIT_MED_REQUEST = 8;
    public static final int TAKE_IMAGE_REQUEST = 9;
    public static final int SELECT_IMAGE_REQUEST = 10;

    private RelativeLayout prescriptionView;

    private ListView medicationListView;
    private MedicationOrderListAdapter medicationOrderListAdapter;
    private List<MedicationOrder> medicationOrders;

    private Button addMedicationOrderBtn;
    private Button addPrescriptionImageBtn;

    private GridView prescriptionImagesView;
    private ClinicalDocumentCaptureImageAdapter imageAdapter;
    private List<ClinicalDocument> clinicalDocuments = new ArrayList<>();
    private List<ImageAdapterModel> imageAdapterModels = new ArrayList<>();

    private SelectPicPopWindow popWindow;

    private OnFragmentInteractionListener mListener;

    private Context context;

    private IMedicationOrderService medicationOrderService;
    private IMedicationDictService medicationDictService;
    private IClinicalDocumentService clinicalDocumentService;

    private Encounter encounter;

    public static final String BUNDLE_KEY_ENT = "encounter";
    public static final String BUNDLE_KEY_SELECTED_MED_ORDER = "selected_med_order";

    private int selectedMedicationOrderPosition = -1;

    private String currentImageFilePath;
    private int columnWidth = 128;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PrescriptionFragment.
     */
    public static PrescriptionFragment newInstance(Encounter encounter) {
        PrescriptionFragment fragment = new PrescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_KEY_ENT, encounter);
        fragment.setArguments(bundle);

        return fragment;
    }

    public PrescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        medicationOrderService = new MedicationOrderServiceLocalImpl(context);
        medicationDictService = new MedicationDictServiceLocalImpl(context);
        clinicalDocumentService = new ClinicalDocumentServiceLocalImpl(context);

        final Bundle bundle = getArguments();
        encounter = (Encounter) bundle.getSerializable(BUNDLE_KEY_ENT);

        prescriptionView = (RelativeLayout) inflater.inflate(R.layout.fragment_prescription, container, false);

        medicationListView = (ListView) prescriptionView.findViewById(R.id.id_prescription_med_list);
        medicationOrders = medicationOrderService.getMedicationOrders(encounter.getEncounter_key());
        if (medicationOrders == null) {
            medicationOrders = new ArrayList<>();
        }
        medicationOrderListAdapter = new MedicationOrderListAdapter(context, medicationOrders);
        medicationListView.setAdapter(medicationOrderListAdapter);
        medicationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedMedicationOrderPosition = position;
                MedicationOrder medicationOrder = (MedicationOrder) medicationOrderListAdapter.getItem(position);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable(BUNDLE_KEY_SELECTED_MED_ORDER, medicationOrder);
                Intent intent = new Intent(context, MedicationOrderEditActivity.class);
                intent.putExtras(bundle1);
                startActivityForResult(intent, EDIT_MED_REQUEST);
            }
        });


        addMedicationOrderBtn = (Button) prescriptionView.findViewById(R.id.id_prescription_add_med_btn);
        addMedicationOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MedicationOrderEditActivity.class);
                startActivityForResult(intent, ADD_MED_REQUEST);
            }
        });

        clinicalDocuments = clinicalDocumentService.getClinicalDocuments(encounter.getEncounter_key(), ClinicalDocumentType.PRESCRIPTION.getName());
        if (clinicalDocuments != null){
            for (int i=0; i<clinicalDocuments.size();i++){
                ClinicalDocument cd = clinicalDocuments.get(i);
                String imagePath = cd.getCaptureImageUri();
                Bitmap bitmap = setPic(imagePath);

                imageAdapterModels.add(new ImageAdapterModel(imagePath, bitmap));
            }
        }

        prescriptionImagesView = (GridView) prescriptionView.findViewById(R.id.id_fragment_prescription_images);
        imageAdapter = new ClinicalDocumentCaptureImageAdapter(context, imageAdapterModels, this);
        prescriptionImagesView.setAdapter(imageAdapter);

        addPrescriptionImageBtn = (Button) prescriptionView.findViewById(R.id.id_clinical_image_add_btn);
        addPrescriptionImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow = new SelectPicPopWindow(PrescriptionFragment.this.getActivity(), PrescriptionFragment.this);
                popWindow.showAtLocation(PrescriptionFragment.this.getView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

        return prescriptionView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ADD_MED_REQUEST) {
                handleNewMedOrder(data);
            } else if (requestCode == EDIT_MED_REQUEST) {
                handleEditMedOrder(data);
            } else if (requestCode == TAKE_IMAGE_REQUEST) {
                handleCameraPhoto(data);
            } else if (requestCode == SELECT_IMAGE_REQUEST) {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleNewMedOrder(Intent data){
        Bundle bundle = data.getExtras();
        String name = bundle.getString(MedicationOrderEditActivity.MED_NAME);
        String spec = bundle.getString(MedicationOrderEditActivity.MED_SPEC);
        String quantity = bundle.getString(MedicationOrderEditActivity.QUANTITY);
        String quantity_unit = bundle.getString(MedicationOrderEditActivity.QUANTITY_UNIT);
        String interval = bundle.getString(MedicationOrderEditActivity.FREQ_INTERVAL);
        String interval_unit = bundle.getString(MedicationOrderEditActivity.FREQ_INTERVAL_UNIT);
        String times = bundle.getString(MedicationOrderEditActivity.FREQ_TIMES);
        String dosage = bundle.getString(MedicationOrderEditActivity.DOSAGE);
        String dosage_unit = bundle.getString(MedicationOrderEditActivity.DOSAGE_UNIT);
        String route = bundle.getString(MedicationOrderEditActivity.ROUTE);
        boolean prnChecked = bundle.getBoolean(MedicationOrderEditActivity.PRN);
        String start_time = bundle.getString(MedicationOrderEditActivity.START_TIME);

        MedicationOrder medicationOrder = new MedicationOrder();
        MedicationDict medicationDict = new MedicationDict();
        medicationDict.setName(name);
        medicationDict.setCode(name);
        medicationDict.setSpec(spec);
        int med_dict_id = medicationDictService.addMedicationDict(medicationDict);
        medicationDict.set_id(med_dict_id);

        medicationOrder.setMedication(medicationDict);
        medicationOrder.setQuantity(Float.valueOf(quantity));
        medicationOrder.setQuantity_unit(quantity_unit);
        medicationOrder.setFrequency_interval(Integer.valueOf(interval));
        medicationOrder.setFrequency_interval_unit(interval_unit);
        medicationOrder.setFrequency_times(Integer.valueOf(times));
        medicationOrder.setDosage(Float.valueOf(dosage));
        medicationOrder.setDosage_unit(dosage_unit);
        medicationOrder.setRoute(route);
        medicationOrder.setPRNIndicator(prnChecked ? 1 : 0);
        medicationOrder.setStart_time(start_time);

        int med_order_id = medicationOrderService.addMedicationOrder(encounter.getEncounter_key(), medicationOrder);
        medicationOrder.set_id(med_order_id);

        medicationOrderListAdapter.addMedicationOrder(medicationOrder);
    }

    private void handleEditMedOrder(Intent data){
        Bundle bundle = data.getExtras();
        String name = bundle.getString(MedicationOrderEditActivity.MED_NAME);
        String spec = bundle.getString(MedicationOrderEditActivity.MED_SPEC);
        String quantity = bundle.getString(MedicationOrderEditActivity.QUANTITY);
        String quantity_unit = bundle.getString(MedicationOrderEditActivity.QUANTITY_UNIT);
        String interval = bundle.getString(MedicationOrderEditActivity.FREQ_INTERVAL);
        String interval_unit = bundle.getString(MedicationOrderEditActivity.FREQ_INTERVAL_UNIT);
        String times = bundle.getString(MedicationOrderEditActivity.FREQ_TIMES);
        String dosage = bundle.getString(MedicationOrderEditActivity.DOSAGE);
        String dosage_unit = bundle.getString(MedicationOrderEditActivity.DOSAGE_UNIT);
        String route = bundle.getString(MedicationOrderEditActivity.ROUTE);
        boolean prnChecked = bundle.getBoolean(MedicationOrderEditActivity.PRN);
        String start_time = bundle.getString(MedicationOrderEditActivity.START_TIME);

        MedicationDict medicationDict = new MedicationDict();
        medicationDict.setName(name);
        medicationDict.setCode(name);
        medicationDict.setSpec(spec);
        int med_dict_id = medicationDictService.addMedicationDict(medicationDict);
        medicationDict.set_id(med_dict_id);

        MedicationOrder medicationOrder = (MedicationOrder) medicationOrderListAdapter.getItem(selectedMedicationOrderPosition);

        medicationOrder.setMedication(medicationDict);
        medicationOrder.setQuantity(Float.valueOf(quantity));
        medicationOrder.setQuantity_unit(quantity_unit);
        medicationOrder.setFrequency_interval(Integer.valueOf(interval));
        medicationOrder.setFrequency_interval_unit(interval_unit);
        medicationOrder.setFrequency_times(Integer.valueOf(times));
        medicationOrder.setDosage(Float.valueOf(dosage));
        medicationOrder.setDosage_unit(dosage_unit);
        medicationOrder.setRoute(route);
        medicationOrder.setPRNIndicator(prnChecked ? 1 : 0);
        medicationOrder.setStart_time(start_time);

        medicationOrderService.updateMedicationOrder(medicationOrder);
        medicationOrderListAdapter.updateMedicationOrder(selectedMedicationOrderPosition, medicationOrder);
    }

    private void handleCameraPhoto(Intent data){
        ClinicalDocument clinicalDocument = new ClinicalDocument();
        clinicalDocument.setCaptureImageUri(currentImageFilePath);
        clinicalDocument.setDocumentType(ClinicalDocumentType.PRESCRIPTION.getName());
        clinicalDocument.setCreatingDate(TimeFormat.parseDate(new Date(), "yyyyMMdd"));

        int id = clinicalDocumentService.addClinicalDocument(encounter.getEncounter_key(), clinicalDocument);
        clinicalDocument.set_id(id);
        clinicalDocuments.add(clinicalDocument);

        Bitmap bitmap = setPic(currentImageFilePath);
        imageAdapter.addImage(new ImageAdapterModel(currentImageFilePath, bitmap));
    }

    private void dispatchTakePictureIntent(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imageFile = null;

        try{
            imageFile = FileUtil.createClinicalDocImageFile(context, encounter.getEncounter_key());
            currentImageFilePath = imageFile.getAbsolutePath();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }

        if (imageFile !=null){
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        }

        startActivityForResult(intent, TAKE_IMAGE_REQUEST);
    }

    private Bitmap setPic(String imagePath) {
        //int targetW = prescriptionImagesView.getColumnWidth();

        //if(targetW <=0){
        //    targetW = 128;
        //}

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;


		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if (columnWidth > 0) {
            scaleFactor = photoW / columnWidth;
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);

        return bitmap;
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.btn_pick_photo:
                Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                intent1.addCategory(Intent.CATEGORY_OPENABLE);
                intent1.setType("image/jpeg");
                startActivityForResult(intent1, SELECT_IMAGE_REQUEST);
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
}
