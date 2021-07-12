package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeCertificationAttachmentRead;

@Repository
public interface HrmsEmployeeCertificationAttachmentReadRepository
		extends JpaRepository<HrmsEmployeeCertificationAttachmentRead, Integer> {

}
