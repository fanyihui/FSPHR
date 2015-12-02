package com.fansen.phr.service;

import com.fansen.phr.entity.DiagnosticImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 310078142 on 2015/12/1.
 */
public interface ILabReportRefImageService {
    public void addLabReportRefImages(int labReportID, List<DiagnosticImage> refImages);
    public void updateLabReportRefImages(int labReportID, List<DiagnosticImage> refImages);
    public ArrayList<DiagnosticImage> getLabReportRefImages(int labReportId);
}
