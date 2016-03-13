package com.fansen.phr.service;

import com.fansen.phr.entity.MedicationAdminRecord;
import com.fansen.phr.entity.MedicationOrder;
import com.fansen.phr.entity.MARScheduledTime;

import java.util.List;

/**
 * Created by faen on 2016/3/9.
 */
public interface IMedicationAdminRecordService {
    public MedicationAdminRecord getMAR(MedicationOrder medicationOrder, MARScheduledTime MARScheduledTime);
    public void takenMedication(int _id);
    public void unTakenMedication(int _id);
    public List<MedicationAdminRecord> getExpiredMedication();
}
