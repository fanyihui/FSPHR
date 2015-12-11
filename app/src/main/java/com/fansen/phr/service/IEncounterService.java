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
    public void updateHistoricalProblems(long ent_key, String historicalProblems);
    public void updatePhysicalExamDetails(long ent_key, String physicalExamDetails);
    public String getProblemsDescription(long ent_key);
    public String getHistoricalProblems(long ent_key);
    public String getPhysicalExamDetails(long ent_key);
    public String getChiefComplaint(long ent_key);
    public void  updateChiefComplaint(long ent_key, String chiefComplain);
    public Encounter getLatestEncounter();
}
