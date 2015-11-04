package com.fansen.phr.service;

import com.fansen.phr.entity.MedicationOrder;

import java.util.List;

/**
 * Created by 310078142 on 2015/11/3.
 */
public interface IMedicationOrderService {
    public List<MedicationOrder> getMedicationOrders(long ent_key);
    public int addMedicationOrder(long ent_key, MedicationOrder medicationOrder);
}
