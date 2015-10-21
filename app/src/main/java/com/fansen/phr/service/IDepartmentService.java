package com.fansen.phr.service;

import com.fansen.phr.entity.Department;

/**
 * Created by 310078142 on 2015/10/20.
 */
public interface IDepartmentService {
    public long addDepartment(Department department);
    public Department getDepartmentByKey(long key);
}
