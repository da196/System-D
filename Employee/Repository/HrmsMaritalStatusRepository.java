package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsMaritalStatus;

@Repository
public interface HrmsMaritalStatusRepository extends JpaRepository<HrmsMaritalStatus, Integer> {

}
