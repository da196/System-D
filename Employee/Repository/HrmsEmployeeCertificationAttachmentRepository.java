package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeCertificationAttachment;

@Repository
public interface HrmsEmployeeCertificationAttachmentRepository
		extends JpaRepository<HrmsEmployeeCertificationAttachment, Integer> {

	boolean existsByEmployeeid(int empid);

	boolean existsByEmployeeidAndActive(int empid, int i);

	HrmsEmployeeCertificationAttachment findByEmployeeidAndCertificationid(int empid, int id);

	HrmsEmployeeCertificationAttachment findByCertificationid(int id);

	boolean existsByIdAndActive(int id, int i);

}
