package com.fansen.phr.service.implementation;

import com.fansen.phr.entity.MedicationAdminRecord;
import com.fansen.phr.entity.MedicationOrder;
import com.fansen.phr.entity.TimeSlot;
import com.fansen.phr.service.IMedicationAdminRecordService;
import com.fansen.phr.service.IMedicationOrderService;

import java.util.List;

/**
 * Created by faen on 2016/3/9.
 */
public class MedicationAdminRecordServiceLocalImpl implements IMedicationAdminRecordService{
    @Override
    public MedicationAdminRecord getMAR(MedicationOrder medicationOrder, TimeSlot timeSlot) {
        return null;
    }

    @Override
    public void takenMedication(int _id) {

    }

    @Override
    public void unTakenMedication(int _id) {

    }

    @Override
    public List<MedicationAdminRecord> getExpiredMedication() {
        return null;
    }
}