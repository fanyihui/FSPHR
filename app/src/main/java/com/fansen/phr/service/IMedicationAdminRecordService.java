package com.fansen.phr.service;

import com.fansen.phr.entity.MedicationAdminRecord;
import com.fansen.phr.entity.MedicationOrder;
import com.fansen.phr.entity.MedicationReminderTimes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by faen on 2016/3/9.
 */
public interface IMedicationAdminRecordService {
    public MedicationAdminRecord getMAR(MedicationOrder medicationOrder, MedicationReminderTimes MedicationReminderTimes);
    public int addNewMAR(MedicationAdminRecord medicationAdminRecord);
    public void updateMAR(MedicationAdminRecord medicationAdminRecord);
    public void takenMedication(int _id);
    public void unTakenMedication(int _id);
    public ArrayList<MedicationAdminRecord> getExpiredMedication();
    public ArrayList<MedicationAdminRecord> getMARByDate(String date);
}
