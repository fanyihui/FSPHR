package com.fansen.phr.entity.sort;

import com.fansen.phr.entity.Encounter;

import java.util.Comparator;

/**
 * Created by faen on 2016/7/5.
 */
public class EncounterSort implements Comparator<Encounter>{
    @Override
    public int compare(Encounter lhs, Encounter rhs) {
        int flag = lhs.getAdmit_date().compareTo(rhs.getAdmit_date());

        return flag;
    }
}
