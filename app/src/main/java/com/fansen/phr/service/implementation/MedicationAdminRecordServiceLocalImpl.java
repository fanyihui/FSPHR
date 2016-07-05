package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fansen.phr.PhrSchemaContract;
import com.fansen.phr.entity.MarStatus;
import com.fansen.phr.entity.MedicationAdminRecord;
import com.fansen.phr.entity.MedicationDict;
import com.fansen.phr.entity.MedicationOrder;
import com.fansen.phr.entity.MedicationReminderTimes;
import com.fansen.phr.entity.OrderStatus;
import com.fansen.phr.service.IMedicationAdminRecordService;
import com.fansen.phr.utils.TimeFormat;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by faen on 2016/3/9.
 */
public class MedicationAdminRecordServiceLocalImpl extends BaseServiceLocal implements IMedicationAdminRecordService{
    public MedicationAdminRecordServiceLocalImpl(Context context) {
        super(context);
    }

    @Override
    public int addNewMAR(MedicationAdminRecord medicationAdminRecord) {
        int order_id = medicationAdminRecord.getMedicationOrder().get_id();
        int scheduled_time_id = medicationAdminRecord.getMedicationReminderTimes().get_id();
        Date currentDate = new Date();

        String date = TimeFormat.parseDate(currentDate);
        String time = TimeFormat.parseTime(currentDate);

        ContentValues values = new ContentValues();
        values.put(PhrSchemaContract.MARTable.COLUMN_NAME_MAR_ORDER_KEY, order_id);
        values.put(PhrSchemaContract.MARTable.COLUMN_NAME_MAR_SCHEDULED_TIME_KEY, scheduled_time_id);
        values.put(PhrSchemaContract.MARTable.COLUMN_NAME_MAR_DT, date);
        values.put(PhrSchemaContract.MARTable.COLUMN_NAME_MAR_TM, time);
        values.put(PhrSchemaContract.MARTable.COLUMN_NAME_MAR_STATUS, MarStatus.TAKEN.getName());
        values.put(PhrSchemaContract.MARTable.COLUMN_NAME_MAR_DOSAGE, medicationAdminRecord.getMedicationOrder().getDosage());
        values.put(PhrSchemaContract.MARTable.COLUMN_NAME_MAR_DOSAGE_UNIT, medicationAdminRecord.getMedicationOrder().getDosage_unit());

        long id = fsPhrDB.insert(PhrSchemaContract.MARTable.TABLE_NAME, values);

        return (int)id;
    }

    @Override
    public void updateMAR(MedicationAdminRecord medicationAdminRecord) {
        ContentValues values = new ContentValues();

        values.put(PhrSchemaContract.MARTable.COLUMN_NAME_MAR_STATUS, medicationAdminRecord.getStatus());
        values.put(PhrSchemaContract.MARTable.COLUMN_NAME_MAR_DT, TimeFormat.parseDate(medicationAdminRecord.getAdminDate()));

        String[] args = {String.valueOf(medicationAdminRecord.get_id())};

        fsPhrDB.update(PhrSchemaContract.MARTable.TABLE_NAME, values, "_id=?", args);
    }

    @Override
    public MedicationAdminRecord getMAR(MedicationOrder medicationOrder, MedicationReminderTimes MedicationReminderTimes) {
        return null;
    }

    @Override
    public void takenMedication(int _id) {
        ContentValues values = new ContentValues();
        values.put(PhrSchemaContract.MARTable.COLUMN_NAME_MAR_STATUS, MarStatus.TAKEN.getName());

        String[] args = {String.valueOf(_id)};

        fsPhrDB.update(PhrSchemaContract.MARTable.TABLE_NAME, values, "_id=?", args);
    }

    @Override
    public void unTakenMedication(int _id) {
        ContentValues values = new ContentValues();
        values.put(PhrSchemaContract.MARTable.COLUMN_NAME_MAR_STATUS, MarStatus.UNTAKEN.getName());

        String[] args = {String.valueOf(_id)};

        fsPhrDB.update(PhrSchemaContract.MARTable.TABLE_NAME, values, "_id=?", args);
    }

    @Override
    public ArrayList<MedicationAdminRecord> getExpiredMedication() {
        return null;
    }

    @Override
    public ArrayList<MedicationAdminRecord> getMARByDate(String date) {
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
                PhrSchemaContract.MARTable.TABLE_NAME + "._ID as mar_id," +
                PhrSchemaContract.MARTable.TABLE_NAME + "."+PhrSchemaContract.MARTable.COLUMN_NAME_MAR_STATUS + " as mar_status," +
                PhrSchemaContract.MedicationDictTable.TABLE_NAME+"."+PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_NAME+" as med_name," +
                PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_SPEC+"," +
                PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_CODE +
                " from "+PhrSchemaContract.MARScheduledTimeTable.TABLE_NAME +
                " JOIN "+PhrSchemaContract.MedicationOrderTable.TABLE_NAME + " ON "+
                PhrSchemaContract.MedicationOrderTable.TABLE_NAME+"."+PhrSchemaContract.MedicationOrderTable._ID +"="+PhrSchemaContract.MARScheduledTimeTable.COLUMN_NAME_MED_ORDER_KEY +
                " JOIN "+ PhrSchemaContract.MedicationDictTable.TABLE_NAME + " ON " +
                PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_MED_KEY + "=" + PhrSchemaContract.MedicationDictTable.TABLE_NAME +"."+PhrSchemaContract.MedicationDictTable._ID +
                " LEFT OUTER JOIN "+PhrSchemaContract.MARTable.TABLE_NAME+" ON "+ PhrSchemaContract.MARTable.COLUMN_NAME_MAR_SCHEDULED_TIME_KEY +"="+PhrSchemaContract.MARScheduledTimeTable.TABLE_NAME+"."+PhrSchemaContract.MARScheduledTimeTable._ID +
                " where "+PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_STATUS +"='"+ OrderStatus.ACTIVE.getName()+"'" +
                " and "+PhrSchemaContract.MARTable.COLUMN_NAME_MAR_DT +"='" +date+ "'"+
                " or " + PhrSchemaContract.MARTable.COLUMN_NAME_MAR_DT + " is null" +
                " order by "+PhrSchemaContract.MARScheduledTimeTable.COLUMN_NAME_SCHEDULED_TIME;

        ArrayList<MedicationAdminRecord> medicationAdminRecords = new ArrayList<>();

        Cursor c = fsPhrDB.rawQuery(sql);

        c.moveToFirst();

        while (!c.isAfterLast()) {
            MedicationDict medicationDict = new MedicationDict();
            MedicationOrder medicationOrder = new MedicationOrder();
            MedicationReminderTimes medicationReminderTime = new MedicationReminderTimes();
            MedicationAdminRecord medicationAdminRecord = new MedicationAdminRecord();

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

            medicationAdminRecord.set_id(c.getInt(c.getColumnIndex("mar_id")));
            medicationAdminRecord.setStatus(c.getString(c.getColumnIndex("mar_status")));
            medicationAdminRecord.setMedicationOrder(medicationOrder);
            medicationAdminRecord.setMedicationReminderTimes(medicationReminderTime);

            medicationAdminRecords.add(medicationAdminRecord);

            c.moveToNext();
        }

        c.close();
        fsPhrDB.closeDatabase();

        return medicationAdminRecords;
    }
}
