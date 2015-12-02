package com.fansen.phr.service.implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fansen.phr.PhrSchemaContract;
import com.fansen.phr.entity.BodyPartDef;
import com.fansen.phr.entity.DictDiagnosis;
import com.fansen.phr.entity.MedicationDict;
import com.fansen.phr.entity.ObservationDef;
import com.fansen.phr.entity.OrderCodeDef;
import com.fansen.phr.entity.RequestedProcedureTypeDef;
import com.fansen.phr.entity.SpecimenTypeCodeDef;
import com.fansen.phr.service.ITerminologyService;

/**
 * Created by 310078142 on 2015/11/19.
 */
public class TermilologyServiceLocalImpl extends BaseServiceLocal implements ITerminologyService{
    public TermilologyServiceLocalImpl(Context context) {
        super(context);
    }

    @Override
    public int addRequestedProcedureCode(RequestedProcedureTypeDef requestedProcedureTypeDef) {
        /*if (requestedProcedureTypeDef.get_id() > 0){
            return  requestedProcedureTypeDef.get_id();
        }*/

        String name = requestedProcedureTypeDef.getName();
        Cursor c = fsPhrDB.query(PhrSchemaContract.RequestedProcedureTypeDefTable.TABLE_NAME, new String[]{PhrSchemaContract.RequestedProcedureTypeDefTable._ID}, PhrSchemaContract.RequestedProcedureTypeDefTable.COLUMN_NAME_RP_DEF_NAME + "=?", new String[]{requestedProcedureTypeDef.getName()});
        c.moveToFirst();

        int id = 0;

        if (c.getCount() > 0)
            id = c.getInt(c.getColumnIndex(PhrSchemaContract.RequestedProcedureTypeDefTable._ID));
        else {
            ContentValues values = new ContentValues();
            values.put(PhrSchemaContract.RequestedProcedureTypeDefTable.COLUMN_NAME_RP_DEF_NAME, name);
            values.put(PhrSchemaContract.RequestedProcedureTypeDefTable.COLUMN_NAME_RP_DEF_CODE, name);
            id = (int) fsPhrDB.insert(PhrSchemaContract.RequestedProcedureTypeDefTable.TABLE_NAME, values);
        }

        c.close();
        fsPhrDB.closeDatabase();

        requestedProcedureTypeDef.set_id(id);

        return id;
    }

    @Override
    public int addBodyPartCode(BodyPartDef bodyPartDef) {
        /*if(bodyPartDef.get_id() > 0){
            return bodyPartDef.get_id();
        }*/

        String name = bodyPartDef.getName();
        Cursor c = fsPhrDB.query(PhrSchemaContract.BodypartDefTable.TABLE_NAME, new String[]{PhrSchemaContract.BodypartDefTable._ID}, PhrSchemaContract.BodypartDefTable.COLUMN_NAME_BODY_PART_DEF_NAME + "=?", new String[]{name});
        c.moveToFirst();

        int id = 0;

        if (c.getCount() > 0)
            id = c.getInt(c.getColumnIndex(PhrSchemaContract.BodypartDefTable._ID));
        else {
            ContentValues values = new ContentValues();
            values.put(PhrSchemaContract.BodypartDefTable.COLUMN_NAME_BODY_PART_DEF_NAME, name);
            values.put(PhrSchemaContract.BodypartDefTable.COLUMN_NAME_BODY_PART_DEF_CODE, name);
            id = (int) fsPhrDB.insert(PhrSchemaContract.BodypartDefTable.TABLE_NAME, values);
        }

        c.close();
        fsPhrDB.closeDatabase();

        bodyPartDef.set_id(id);

        return id;
    }

    @Override
    public int addMedicationCode(MedicationDict medicationDict) {
        return 0;
    }

    @Override
    public int addDiagnosisCode(DictDiagnosis diagnosisCode) {
        return 0;
    }

    @Override
    public int addObservationCode(ObservationDef observationDef) {
        /*if (observationDef.get_id() > 0){
            return observationDef.get_id();
        }*/
        String name = observationDef.getName();
        String code = observationDef.getCode();
        String range = observationDef.getNormalRange();

        Cursor c = fsPhrDB.query(PhrSchemaContract.ObservationDefTable.TABLE_NAME,
                new String[]{PhrSchemaContract.ObservationDefTable._ID},
                PhrSchemaContract.ObservationDefTable.COLUMN_NAME_OBR_DEF_NAME + "=?", new String[]{name});
        c.moveToFirst();

        int id = 0;

        if (c.getCount() > 0)
            id = c.getInt(c.getColumnIndex(PhrSchemaContract.ObservationDefTable._ID));
        else {
            ContentValues values = new ContentValues();
            values.put(PhrSchemaContract.ObservationDefTable.COLUMN_NAME_OBR_DEF_NAME, name);
            values.put(PhrSchemaContract.ObservationDefTable.COLUMN_NAME_OBR_DEF_CODE, code);
            values.put(PhrSchemaContract.ObservationDefTable.COLUMN_NAME_OBR_DEF_NORMAL_RANGE, range);

            id = (int) fsPhrDB.insert(PhrSchemaContract.ObservationDefTable.TABLE_NAME, values);
        }

        c.close();
        fsPhrDB.closeDatabase();

        observationDef.set_id(id);

        return id;
    }

    @Override
    public int addSpecimenTypeCode(SpecimenTypeCodeDef specimenTypeCodeDef) {
        /*if (specimenTypeCodeDef.get_id() > 0){
            return specimenTypeCodeDef.get_id();
        }*/

        String name = specimenTypeCodeDef.getName();
        String code = specimenTypeCodeDef.getCode();

        Cursor c = fsPhrDB.query(PhrSchemaContract.SpecimenTypeCodeDefTable.TABLE_NAME,
                new String[]{PhrSchemaContract.SpecimenTypeCodeDefTable._ID},
                PhrSchemaContract.SpecimenTypeCodeDefTable.COLUMN_NAME_SPECIMEN_CODE_DEF_NAME + "=?", new String[]{name});
        c.moveToFirst();

        int id = 0;

        if (c.getCount() > 0)
            id = c.getInt(c.getColumnIndex(PhrSchemaContract.SpecimenTypeCodeDefTable._ID));
        else {
            ContentValues values = new ContentValues();
            values.put(PhrSchemaContract.SpecimenTypeCodeDefTable.COLUMN_NAME_SPECIMEN_CODE_DEF_NAME, name);
            values.put(PhrSchemaContract.SpecimenTypeCodeDefTable.COLUMN_NAME_SPECIMEN_CODE_DEF_CODE, code);
            id = (int) fsPhrDB.insert(PhrSchemaContract.SpecimenTypeCodeDefTable.TABLE_NAME, values);
        }

        c.close();
        fsPhrDB.closeDatabase();

        specimenTypeCodeDef.set_id(id);

        return id;
    }

    @Override
    public int addOrderCode(OrderCodeDef orderCodeDef) {
        /*if (orderCodeDef.get_id() > 0){
            return orderCodeDef.get_id();
        }*/

        String name = orderCodeDef.getName();
        String code = orderCodeDef.getCode();

        Cursor c = fsPhrDB.query(PhrSchemaContract.OrderCodeDefTable.TABLE_NAME,
                new String[]{PhrSchemaContract.OrderCodeDefTable._ID},
                PhrSchemaContract.OrderCodeDefTable.COLUMN_NAME_ORDER_CODE_DEF_NAME + "=?", new String[]{name});
        c.moveToFirst();

        int id = 0;

        if (c.getCount() > 0)
            id = c.getInt(c.getColumnIndex(PhrSchemaContract.OrderCodeDefTable._ID));
        else {
            ContentValues values = new ContentValues();
            values.put(PhrSchemaContract.OrderCodeDefTable.COLUMN_NAME_ORDER_CODE_DEF_NAME, name);
            values.put(PhrSchemaContract.OrderCodeDefTable.COLUMN_NAME_ORDER_CODE_DEF_CODE, code);
            id = (int) fsPhrDB.insert(PhrSchemaContract.OrderCodeDefTable.TABLE_NAME, values);
        }

        c.close();
        fsPhrDB.closeDatabase();

        orderCodeDef.set_id(id);

        return id;
    }
}
