package com.fansen.phr.service.implementation;

import com.fansen.phr.entity.MedicationReminderTimes;
import com.fansen.phr.service.IMedicationReminderService;

import java.util.ArrayList;

/**
 * Created by faen on 2016/3/22.
 */
public class MedicationReminderServiceLocalImpl implements IMedicationReminderService {
    @Override
    public ArrayList<MedicationReminderTimes> getReminderTimes(int orderId) {
        return null;
    }

    @Override
    public void addReminderTimes(int orderId, ArrayList<MedicationReminderTimes> reminderTimes) {

    }

    @Override
    public void updateReminderTimes(int orderId, ArrayList<MedicationReminderTimes> reminderTimes) {

    }
}
