package com.fansen.phr.service;

import com.fansen.phr.entity.BodyPartDef;
import com.fansen.phr.entity.DictDiagnosis;
import com.fansen.phr.entity.MedicationDict;
import com.fansen.phr.entity.ObservationDef;
import com.fansen.phr.entity.RequestedProcedureTypeDef;

/**
 * Created by 310078142 on 2015/11/18.
 */
public interface ITerminologyService {
    public int addRequestedProcedureCode(RequestedProcedureTypeDef requestedProcedureTypeDef);
    public int addBodyPartCode(BodyPartDef bodyPartDef);
    public int addMedicationCode(MedicationDict medicationDict);
    public int addDiagnosisCode(DictDiagnosis diagnosisCode);
    public int addObservationCode(ObservationDef observationDef);
}
