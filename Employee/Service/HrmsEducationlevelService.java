package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEducationlevel;

@Service
public interface HrmsEducationlevelService {
	public ResponseEntity<HrmsEducationlevel> save(HrmsEducationlevel hrmsEducationlevel);

	public ResponseEntity<Optional<HrmsEducationlevel>> viewHrmsEducationlevel(int id);

	public ResponseEntity<HrmsEducationlevel> update(HrmsEducationlevel hrmsEducationlevel, int id);

	public ResponseEntity<?> deleteHrmsEducationlevel(int id);

	public ResponseEntity<List<HrmsEducationlevel>> listHrmsEducationlevel();

}
