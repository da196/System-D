package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsSalaryScale;

@Service
public interface HrmsSalaryScaleService {

	public ResponseEntity<HrmsSalaryScale> save(HrmsSalaryScale hrmsSalaryScale);

	public ResponseEntity<Optional<HrmsSalaryScale>> viewHrmsSalaryScale(int id);

	public ResponseEntity<HrmsSalaryScale> update(HrmsSalaryScale hrmsSalaryScale, int id);

	public ResponseEntity<?> deleteHrmsSalaryScale(int id);

	public ResponseEntity<List<HrmsSalaryScale>> listHrmsSalaryScale();
}
