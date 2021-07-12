package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.HrmsSalarystructureDetails;
import com.Hrms.Employee.Entity.HrmsSalarystructure;

@Service
public interface HrmsSalarystructureService {

	public ResponseEntity<HrmsSalarystructure> save(HrmsSalarystructure hrmsSalarystructure);

	public ResponseEntity<Optional<HrmsSalarystructure>> viewHrmsSalarystructure(int id);

	public ResponseEntity<HrmsSalarystructure> update(HrmsSalarystructure hrmsSalarystructure, int id);

	public ResponseEntity<?> deleteHrmsSalarystructure(int id);

	public ResponseEntity<List<HrmsSalarystructureDetails>> listHrmsSalarystructure();

}
