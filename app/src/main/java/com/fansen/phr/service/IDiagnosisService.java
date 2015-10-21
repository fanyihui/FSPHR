package com.fansen.phr.service;

import com.fansen.phr.entity.Diagnosis;

import java.util.List;

/**
 * Created by 310078142 on 2015/10/20.
 */
public interface IDiagnosisService {
    public int addNewDiagnosis(Diagnosis diagnosis);
    public List<Diagnosis> getDiagnosis(long encounter_key);
}
