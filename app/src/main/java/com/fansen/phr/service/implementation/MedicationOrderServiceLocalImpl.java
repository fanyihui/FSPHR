package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fansen.phr.PhrSchemaContract;
import com.fansen.phr.entity.MedicationDict;
import com.fansen.phr.entity.MedicationOrder;
import com.fansen.phr.service.IMedicationOrderService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 310078142 on 2015/11/4.
 */
public class MedicationOrderServiceLocalImpl extends BaseServiceLocal implements IMedicationOrderService{
    public MedicationOrderServiceLocalImpl(Context context) {
        super(context);
    }

    @Override
    public List<MedicationOrder> getMedicationOrders(long ent_key) {
        String sql = "select "+
                PhrSchemaContract.MedicationOrderTable.TABLE_NAME+"."+PhrSchemaContract.MedicationOrderTable._ID +"," +
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
                PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_NAME+"," +
                PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_SPEC+"," +
                PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_CODE +
                " from " +
                PhrSchemaContract.MedicationOrderTable.TABLE_NAME + ","+
                PhrSchemaContract.MedicationDictTable.TABLE_NAME +
                " where " +
                PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_ENT_KEY +"="+ent_key +
                " and "+
                PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_MED_KEY +
                "=" + PhrSchemaContract.MedicationDictTable.TABLE_NAME +"."+PhrSchemaContract.MedicationDictTable._ID;

        List<MedicationOrder> medicationOrders = new ArrayList<>();

        Cursor c = fsPhrDB.rawQuery(sql);

        c.moveToFirst();

        while (!c.isAfterLast()) {
            MedicationDict medicationDict = new MedicationDict();
            MedicationOrder medicationOrder = new MedicationOrder();

            medicationDict.setName(c.getString(c.getColumnIndex(PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_NAME)));
            medicationDict.setSpec(c.getString(c.getColumnIndex(PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_SPEC)));
            medicationDict.setCode(c.getString(c.getColumnIndex(PhrSchemaContract.MedicationDictTable.COLUMN_NAME_DICT_MED_CODE)));

            medicationOrder.setMedication(medicationDict);
            medicationOrder.set_id(c.getInt(c.getColumnIndex(PhrSchemaContract.MedicationOrderTable._ID)));
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

            medicationOrders.add(medicationOrder);

            c.moveToNext();
        }

        c.close();
        fsPhrDB.closeDatabase();

        return medicationOrders;
    }

    @Override
    public int addMedicationOrder(long ent_key, MedicationOrder medicationOrder) {

        ContentValues values = new ContentValues();
        values.put(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_ENT_KEY, ent_key);
        values.put(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_MED_KEY, medicationOrder.getMedication().get_id());
        values.put(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_DOSAGE, medicationOrder.getDosage());
        values.put(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_DOSAGE_UNIT, medicationOrder.getDosage_unit());
        values.put(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_FREQUENCY_INTERVAL, medicationOrder.getFrequency_interval());
        values.put(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_FREQUENCY_INTERVAL_UNIT, medicationOrder.getFrequency_interval_unit());
        values.put(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_FREQUENCY_TIMES, medicationOrder.getFrequency_times());
        values.put(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_QUANTITY, medicationOrder.getQuantity());
        values.put(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_QUANTITY_UNIT, medicationOrder.getQuantity_unit());
        values.put(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_ROUTE, medicationOrder.getRoute());
        values.put(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_PRN, medicationOrder.getPRNIndicator());
        values.put(PhrSchemaContract.MedicationOrderTable.COLUMN_NAME_MED_ORDER_START_TIME, medicationOrder.getStart_time());


        int id = (int) fsPhrDB.insert(PhrSchemaContract.MedicationOrderTable.TABLE_NAME, values);

        return id;
    }
}
