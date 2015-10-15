package com.fansen.phr.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fansen.phr.R;

/**
 * Created by Yihui Fan on 2015/10/12.
 */
public class AddOutpatientEncounterFragment extends Fragment{
    LinearLayout addOutpatientEntView = null;

    public AddOutpatientEncounterFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addOutpatientEntView = (LinearLayout) inflater.inflate(R.layout.fragment_add_outpatient, container, false);


        return addOutpatientEntView;

        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
