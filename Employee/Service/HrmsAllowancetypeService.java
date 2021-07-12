package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsAllowancetype;

@Service
public interface HrmsAllowancetypeService {

	public ResponseEntity<HrmsAllowancetype> save(HrmsAllowancetype hrmsAllowancetype);

	public ResponseEntity<Optional<HrmsAllowancetype>> viewHrmsAllowancetype(int id);

	public ResponseEntity<HrmsAllowancetype> update(HrmsAllowancetype hrmsAllowancetype, int id);

	public ResponseEntity<?> deleteHrmsAllowancetype(int id);

	public ResponseEntity<List<HrmsAllowancetype>> listHrmsAllowancetype();

}
