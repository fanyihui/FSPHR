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
 * Created by Yihui Fan on 2015/10/10.
 */
public class SummaryFragment extends Fragment{
    private LinearLayout summaryView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //TODO add code here to initial summary view
        summaryView = (LinearLayout) inflater.inflate(
                R.layout.fragment_summary, container, false);

        return summaryView;
    }
}
