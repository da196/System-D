package com.Hrms.Perfomance.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceOprasForm;

@Service
public interface HrmsPerformanceOprasFormService {

	public ResponseEntity<PerformanceOprasForm> getPerformanceOprasFormByFinancialYearAndEmployeeid(int financialyearid,
			int employeeid);

}
