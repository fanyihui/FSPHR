package com.fansen.phr.service;

import com.fansen.phr.entity.Diagnosis;
import com.fansen.phr.entity.DictDiagnosis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 310078142 on 2015/10/20.
 */
public interface IDiagnosisService {
    public int addNewDiagnosis(Diagnosis diagnosis);
    public ArrayList<Diagnosis> getDiagnosis(long encounter_key);

    public ArrayList<Diagnosis> addDiagnosisList(long encounter_key, ArrayList<String> diagnosises);

}
