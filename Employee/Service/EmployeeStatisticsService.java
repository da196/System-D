package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.EmployeeCount;
import com.Hrms.Employee.DTO.EmployeeCountAll;
import com.Hrms.Employee.DTO.EmployeeEducationlevelCount;
import com.Hrms.Employee.DTO.EmployeeGlobalCount;
import com.Hrms.Employee.DTO.EmployeeStatistics;
import com.Hrms.Employee.DTO.UnitEmployeeDetails;

@Service
public interface EmployeeStatisticsService {
	public ResponseEntity<EmployeeStatistics> findByempid(int empid);

	public ResponseEntity<EmployeeCountAll> findAllCount();

	public ResponseEntity<EmployeeCount> getEmployeeCount(int type);

	public ResponseEntity<List<UnitEmployeeDetails>> getUnitDetails();

	public ResponseEntity<List<EmployeeEducationlevelCount>> getEducationlevelCount();

	public ResponseEntity<EmployeeGlobalCount> findAllGeneralCountPerUnit();

}
