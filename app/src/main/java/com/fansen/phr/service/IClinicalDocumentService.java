package com.fansen.phr.service;

import com.fansen.phr.entity.ClinicalDocument;

import java.util.List;

/**
 * Created by 310078142 on 2015/11/10.
 */
public interface IClinicalDocumentService {
    public int addClinicalDocument(long ent_key, ClinicalDocument clinicalDocument);
    public List<ClinicalDocument> getClinicalDocuments(long ent_key, String documentType);
    public void updateClinicalDocument(ClinicalDocument clinicalDocument);
}
