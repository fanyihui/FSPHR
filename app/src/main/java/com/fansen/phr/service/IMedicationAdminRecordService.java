package com.fansen.phr.service;

import com.fansen.phr.entity.MedicationAdminRecord;
import com.fansen.phr.entity.MedicationOrder;
import com.fansen.phr.entity.MedicationReminderTimes;

import java.util.List;

/**
 * Created by faen on 2016/3/9.
 */
public interface IMedicationAdminRecordService {
    public MedicationAdminRecord getMAR(MedicationOrder medicationOrder, MedicationReminderTimes MedicationReminderTimes);
    public int takenMedication(MedicationAdminRecord medicationAdminRecord);
    public void unTakenMedication(int _id);
    public List<MedicationAdminRecord> getExpiredMedication();
}
