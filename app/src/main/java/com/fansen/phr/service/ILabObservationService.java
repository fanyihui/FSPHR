package com.fansen.phr.service;

import com.fansen.phr.entity.LabObservation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 310078142 on 2015/12/1.
 */
public interface ILabObservationService {
    public void addLabObservation(int labReportId, List<LabObservation> labObservations);
    public void updateLabObservation(int labReportId, List<LabObservation> labObservations);
    public ArrayList<LabObservation> getLabObservations(int labReportId);
}
