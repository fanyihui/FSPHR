package com.fansen.phr.service;

import com.fansen.phr.entity.Organization;

/**
 * Created by 310078142 on 2015/10/19.
 */
public interface IOrganizationService {
    public long addOrganization(Organization organization);
    public Organization getOrgbyKey(int key);
}
