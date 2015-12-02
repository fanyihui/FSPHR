package com.fansen.phr.service;

import com.fansen.phr.entity.LabReport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 310078142 on 2015/12/1.
 */
public interface ILabReportService {
    public int addNewLabReport(long ent_key, LabReport labReport);
    public void updateLabReport(LabReport labReport);
    public ArrayList<LabReport> getLabReports(long ent_key);
}
