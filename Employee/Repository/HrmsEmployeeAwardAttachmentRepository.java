package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeAwardAttachment;

@Repository
public interface HrmsEmployeeAwardAttachmentRepository extends JpaRepository<HrmsEmployeeAwardAttachment, Integer> {

}
