package com.fansen.phr.service;

import com.fansen.phr.entity.ChiefComplaint;

import java.util.List;

/**
 * Created by Yihui Fan on 2015/11/2.
 */
public interface IChiefComplaintService {
    public int addComplaint(long encounter_key, ChiefComplaint chiefComplaint);
    public void addComplaints(long encounter_key, List<ChiefComplaint> complaints);
    public List<ChiefComplaint> getComplaints(long encounter_key);
    public void updateChiefComplaint(ChiefComplaint chiefComplaint);
}
