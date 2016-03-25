package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;

import com.fansen.phr.PhrSchemaContract;
import com.fansen.phr.entity.MarStatus;
import com.fansen.phr.entity.MedicationAdminRecord;
import com.fansen.phr.entity.MedicationOrder;
import com.fansen.phr.entity.MedicationReminderTimes;
import com.fansen.phr.service.IMedicationAdminRecordService;
import com.fansen.phr.utils.TimeFormat;

import java.util.List;

/**
 * Created by faen on 2016/3/9.
 */
public class MedicationAdminRecordServiceLocalImpl extends BaseServiceLocal implements IMedicationAdminRecordService{
    public MedicationAdminRecordServiceLocalImpl(Context context) {
        super(context);
    }

    @Override
    public MedicationAdminRecord getMAR(MedicationOrder medicationOrder, MedicationReminderTimes MedicationReminderTimes) {
        return null;
    }

    @Override
    public int takenMedication(MedicationAdminRecord medicationAdminRecord) {
        int order_id = medicationAdminRecord.getMedicationOrder().get_id();

        ContentValues values = new ContentValues();
        values.put(PhrSchemaContract.MARTable.COLUMN_NAME_MAR_ORDER_KEY, order_id);
        values.put(PhrSchemaContract.MARTable.COLUMN_NAME_MAR_DT, TimeFormat.parseDate(medicationAdminRecord.getAdminDate(), "yyyyMMdd"));
        values.put(PhrSchemaContract.MARTable.COLUMN_NAME_MAR_STATUS, MarStatus.TAKEN.getName());

        long id = fsPhrDB.insert(PhrSchemaContract.MARTable.TABLE_NAME, values);

        return (int)id;
    }

    @Override
    public void unTakenMedication(int _id) {

    }

    @Override
    public List<MedicationAdminRecord> getExpiredMedication() {
        return null;
    }
}
