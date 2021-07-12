package com.Hrms.Employee.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeStaffDistributionByAgeResponse {
	private List<EmployeeStaffDistributionByAge> employeeStaffDistributionByAgelist;

}
