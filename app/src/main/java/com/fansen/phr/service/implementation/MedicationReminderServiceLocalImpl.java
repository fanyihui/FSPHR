package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fansen.phr.PhrSchemaContract;
import com.fansen.phr.entity.MedicationReminderTimes;
import com.fansen.phr.service.IMedicationReminderService;

import java.util.ArrayList;

/**
 * Created by faen on 2016/3/22.
 */
public class MedicationReminderServiceLocalImpl extends BaseServiceLocal implements IMedicationReminderService {
    public MedicationReminderServiceLocalImpl(Context context) {
        super(context);
    }

    @Override
    public ArrayList<MedicationReminderTimes> getReminderTimes(int orderId) {
        String sql = "select "+
                PhrSchemaContract.MARScheduledTimeTable._ID + "," +
                PhrSchemaContract.MARScheduledTimeTable.COLUMN_NAME_SCHEDULED_TIME + "," +
                PhrSchemaContract.MARScheduledTimeTable.COLUMN_NAME_SEQUENCE_NUMBER +
                " from "+PhrSchemaContract.MARScheduledTimeTable.TABLE_NAME +
                " where "+PhrSchemaContract.MARScheduledTimeTable.COLUMN_NAME_MED_ORDER_KEY +"="+orderId;

        ArrayList<MedicationReminderTimes> reminderTimes = new ArrayList<>();

        Cursor c = fsPhrDB.rawQuery(sql);

        c.moveToFirst();

        while (!c.isAfterLast()) {
            MedicationReminderTimes medicationReminderTime = new MedicationReminderTimes();
            medicationReminderTime.setSequenceNumber(c.getInt(c.getColumnIndex(PhrSchemaContract.MARScheduledTimeTable.COLUMN_NAME_SEQUENCE_NUMBER)));
            medicationReminderTime.setReminderTime(c.getString(c.getColumnIndex(PhrSchemaContract.MARScheduledTimeTable.COLUMN_NAME_SCHEDULED_TIME)));
            medicationReminderTime.set_id(c.getInt(c.getColumnIndex(PhrSchemaContract.MARScheduledTimeTable._ID)));

            reminderTimes.add(medicationReminderTime);

            c.moveToNext();
        }

        c.close();
        fsPhrDB.closeDatabase();

        return reminderTimes;
    }

    @Override
    public void addReminderTimes(int orderId, ArrayList<MedicationReminderTimes> reminderTimes) {
        if (reminderTimes != null){
            for (int i=0;i<reminderTimes.size();i++){
                MedicationReminderTimes medicationReminderTime = reminderTimes.get(i);

                ContentValues values = new ContentValues();
                values.put(PhrSchemaContract.MARScheduledTimeTable.COLUMN_NAME_MED_ORDER_KEY, orderId);
                values.put(PhrSchemaContract.MARScheduledTimeTable.COLUMN_NAME_SEQUENCE_NUMBER, medicationReminderTime.getSequenceNumber());
                values.put(PhrSchemaContract.MARScheduledTimeTable.COLUMN_NAME_SCHEDULED_TIME, medicationReminderTime.getReminderTime());

                fsPhrDB.insert(PhrSchemaContract.MARScheduledTimeTable.TABLE_NAME, values);
            }
        }
    }

    @Override
    public void updateReminderTimes(int orderId, ArrayList<MedicationReminderTimes> reminderTimes) {

    }
}
