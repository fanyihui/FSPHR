package com.fansen.phr.service;

import com.fansen.phr.entity.DiagnosticImagingReport;

import java.util.List;

/**
 * Created by 310078142 on 2015/11/16.
 */
public interface IDiagnosticImagingReportService {
    public List<DiagnosticImagingReport> getDiagnosticImagingReports(long ent_key);
    public int addDiagnosticImagingReport(long ent_key, DiagnosticImagingReport diagnosticImagingReport);
    public void updateDiagnosticImagingReport(DiagnosticImagingReport diagnosticImagingReport);
    public void deleteDiagnosticImagingReport(DiagnosticImagingReport diagnosticImagingReport);
}
