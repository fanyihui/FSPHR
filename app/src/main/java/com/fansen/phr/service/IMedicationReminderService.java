package com.fansen.phr.service;

import com.fansen.phr.entity.MedicationReminderTimes;

import java.util.ArrayList;

/**
 * Created by faen on 2016/3/22.
 */
public interface IMedicationReminderService {
    public ArrayList<MedicationReminderTimes> getReminderTimes(int orderId);
    public void addReminderTimes(int orderId, ArrayList<MedicationReminderTimes> reminderTimes);
    public void updateReminderTimes(int orderId, ArrayList<MedicationReminderTimes> reminderTimes);
}
