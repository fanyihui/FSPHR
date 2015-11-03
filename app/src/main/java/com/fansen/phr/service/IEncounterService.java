package com.fansen.phr.service;

import com.fansen.phr.entity.Encounter;

import java.util.List;

/**
 * Created by 310078142 on 2015/10/12.
 */
public interface IEncounterService {
    public List<Encounter> getAllEncounters();
    public long addNewEncounter(Encounter encounter);
    public boolean updateEncounter(Encounter encounter);
    public boolean deleteEncounter(long ent_key);
    public void updateProblemsDescription(long ent_key, String problemsDescription);
    public String getProblemsDescription(long ent_key);
}
