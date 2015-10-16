package com.fansen.phr.service.implementation;

import android.content.Context;

import com.fansen.phr.entity.Encounter;
import com.fansen.phr.service.IEncounterService;

import java.util.List;

/**
 * Created by 310078142 on 2015/10/12.
 */
public class EncounterServiceLocalImpl implements IEncounterService{


    public EncounterServiceLocalImpl(Context context) {
    }

    @Override
    public List<Encounter> getAllEncounters() {
        //TODO add code here to query encounter list from database
        return null;
    }

    @Override
    public long addNewEncounter(Encounter encounter) {
        return 0;
    }

    @Override
    public boolean updateEncounter(Encounter encounter) {
        return false;
    }

    @Override
    public boolean deleteEncounter(long ent_key) {
        return false;
    }
}
