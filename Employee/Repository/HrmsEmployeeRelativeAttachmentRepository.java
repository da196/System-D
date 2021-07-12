package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeRelativeAttachment;

@Repository
public interface HrmsEmployeeRelativeAttachmentRepository
		extends JpaRepository<HrmsEmployeeRelativeAttachment, Integer> {

	HrmsEmployeeRelativeAttachment findByRelativeid(int id);

}
