package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.EducationcourseDetailed;
import com.Hrms.Employee.Entity.HrmsEducationcourse;

@Service
public interface HrmsEducationcourseService {
	public ResponseEntity<HrmsEducationcourse> save(HrmsEducationcourse hrmsEducationcourse);

	public ResponseEntity<Optional<HrmsEducationcourse>> viewHrmsEducationcourse(int id);

	public ResponseEntity<HrmsEducationcourse> update(HrmsEducationcourse hrmsEducationcourse, int id);

	public ResponseEntity<?> deleteHrmsEducationcourse(int id);

	public ResponseEntity<List<HrmsEducationcourse>> listHrmsEducationcourse();

	public ResponseEntity<List<HrmsEducationcourse>> EducationcourseByLevelCode(int levelCode);

	public ResponseEntity<List<EducationcourseDetailed>> listHrmsEducationcourseDetailed();

}
