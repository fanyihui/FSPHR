package com.fansen.phr.service;

import com.fansen.phr.entity.DiagnosticImage;

import java.util.List;

/**
 * Created by 310078142 on 2015/11/19.
 */
public interface IDiagnosticImageService {
    public void addDiagnosticImages(int dir_key, List<DiagnosticImage> diagnosticImages);
    public List<DiagnosticImage> getDiagnosticImages(int dir_key);
}
