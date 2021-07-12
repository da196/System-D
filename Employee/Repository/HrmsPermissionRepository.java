package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsPermission;

@Repository
public interface HrmsPermissionRepository extends JpaRepository<HrmsPermission, Integer> {

}
