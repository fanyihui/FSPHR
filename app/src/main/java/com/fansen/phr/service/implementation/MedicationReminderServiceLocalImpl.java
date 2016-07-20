package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fansen.phr.PhrSchemaContract;
import com.fansen.phr.entity.MedicationDict;
import com.fansen.phr.entity.MedicationOrder;
import com.fansen.phr.entity.MedicationReminderTimes;
import com.fansen.phr.entity.OrderStatus;
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
    public ArrayList<MedicationReminderTimes> getAllActiveMedicationReminders() {
        String sql = "select "+
                PhrSchemaContract.MARScheduledTimeTable.TABLE_NAME +"."+PhrSchemaContract.MARScheduledTimeTable._ID + "," +
                PhrSchemaContract.MARScheduledTimeTable.COLUMN_NAME_SCHEDULED_TIME + "," +
                PhrSchemaContract.MARScheduledTimeTable.TABLE_NAME +"."+PhrSchemaContract.MARScheduledTimeTable.COLUMN_NAME_SEQUENCE_NUMBER + " as seq_num,"+
                PhrSchemaContract.MARScheduledTimeTable.COLUMN_NAME_MED_ORDER_KEY +","+
                PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_PRN +"," +
                PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_ROUTE +"," +
                PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_QUANTITY+"," +
                PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_QUANTITY_UNIT +"," +
                PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_DOSAGE +"," +
                PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_DOSAGE_UNIT +"," +
                PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_FREQUENCY_INTERVAL+"," +
                PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_FREQUENCY_INTERVAL_UNIT+"," +
                PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_FREQUENCY_TIMES+"," +
                PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_START_TIME+"," +
                PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_NOTES+"," +
                PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_STATUS+"," +
                PhrSchemaContract.MedicationDictTable.TABLE_NAME+"."+PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_NAME+" as med_name," +
                PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_SPEC+"," +
                PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_CODE +
                " from "+PhrSchemaContract.MARScheduledTimeTable.TABLE_NAME +
                " JOIN "+PhrSchemaContract.MedicationOrderTable.TABLE_NAME + " ON "+
                PhrSchemaContract.MedicationOrderTable.TABLE_NAME+"."+PhrSchemaContract.MedicationOrderTable._ID +"="+PhrSchemaContract.MARScheduledTimeTable.COLUMN_NAME_MED_ORDER_KEY +
                " JOIN "+ PhrSchemaContract.MedicationDictTable.TABLE_NAME + " ON " +
                PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_MED_DICT_KEY + "=" + PhrSchemaContract.MedicationDictTable.TABLE_NAME +"."+PhrSchemaContract.MedicationDictTable._ID +
                " where "+PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_STATUS +"='"+ OrderStatus.ACTIVE.getName()+"'" +
                " order by "+PhrSchemaContract.MARScheduledTimeTable.COLUMN_NAME_SCHEDULED_TIME;

        ArrayList<MedicationReminderTimes> reminderTimes = new ArrayList<>();

        Cursor c = fsPhrDB.rawQuery(sql);

        c.moveToFirst();

        while (!c.isAfterLast()) {
            MedicationDict medicationDict = new MedicationDict();
            MedicationOrder medicationOrder = new MedicationOrder();
            MedicationReminderTimes medicationReminderTime = new MedicationReminderTimes();

            medicationDict.setName(c.getString(c.getColumnIndex("med_name")));
            medicationDict.setSpec(c.getString(c.getColumnIndex(PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_SPEC)));
            medicationDict.setCode(c.getString(c.getColumnIndex(PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_CODE)));

            medicationOrder.setMedication(medicationDict);
            medicationOrder.set_id(c.getInt(c.getColumnIndex(PhrSchemaContract.MARScheduledTimeTable.COLUMN_NAME_MED_ORDER_KEY)));
            medicationOrder.setQuantity(c.getFloat(c.getColumnIndex(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_QUANTITY)));
            medicationOrder.setQuantity_unit(c.getString(c.getColumnIndex(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_QUANTITY_UNIT)));
            medicationOrder.setFrequency_interval(c.getInt(c.getColumnIndex(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_FREQUENCY_INTERVAL)));
            medicationOrder.setFrequency_interval_unit(c.getString(c.getColumnIndex(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_FREQUENCY_INTERVAL_UNIT)));
            medicationOrder.setFrequency_times(c.getInt(c.getColumnIndex(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_FREQUENCY_TIMES)));
            medicationOrder.setDosage(c.getFloat(c.getColumnIndex(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_DOSAGE)));
            medicationOrder.setDosage_unit(c.getString(c.getColumnIndex(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_DOSAGE_UNIT)));
            medicationOrder.setRoute(c.getString(c.getColumnIndex(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_ROUTE)));
            medicationOrder.setPRNIndicator(c.getInt(c.getColumnIndex(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_PRN)));
            medicationOrder.setStart_time(c.getString(c.getColumnIndex(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_START_TIME)));
            medicationOrder.setNotes(c.getString(c.getColumnIndex(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_NOTES)));
            medicationOrder.setStatus(c.getString(c.getColumnIndex(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_STATUS)));

            medicationReminderTime.setMedicationOrder(medicationOrder);
            medicationReminderTime.setSequenceNumber(c.getInt(c.getColumnIndex("seq_num")));
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
