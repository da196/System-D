package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeShortCourseAttachment;

@Repository
public interface HrmsEmployeeShortCourseAttachmentRepository
		extends JpaRepository<HrmsEmployeeShortCourseAttachment, Integer> {

	HrmsEmployeeShortCourseAttachment findByShortcourseid(int id);

}
