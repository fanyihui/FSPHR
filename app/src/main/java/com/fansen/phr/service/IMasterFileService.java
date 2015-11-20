package com.fansen.phr.service;

import com.fansen.phr.entity.Department;
import com.fansen.phr.entity.Organization;

/**
 * Created by 310078142 on 2015/11/18.
 */
public interface IMasterFileService {
    public int addOrganization(Organization organization);
    public int addDepartment(Department department);
}
