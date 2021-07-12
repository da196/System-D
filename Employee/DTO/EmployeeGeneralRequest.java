package com.Hrms.Employee.DTO;

import java.util.List;

import org.springframework.stereotype.Component;

import com.Hrms.Employee.Entity.HrmsContact;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.HrmsLocationAddress;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeGeneralRequest {

	private HrmsEmployee hrmsEmployee;

	private List<HrmsContact> hrmsContactlist;

	private List<HrmsLocationAddress> adresslist;

}
