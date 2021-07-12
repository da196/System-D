package com.Hrms.Employee.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeEducationAttachment;

@Repository
public interface HrmsEmployeeEducationAttachmentRepository
		extends JpaRepository<HrmsEmployeeEducationAttachment, Integer> {

	Optional<HrmsEmployeeEducationAttachment> findByEmployeeidAndEducationid(int id, int id2);

	HrmsEmployeeEducationAttachment findByEducationid(int id);

}
