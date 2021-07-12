package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.Userloginresponse;
import com.Hrms.Employee.Entity.HrmsUserLogins;

@Service
public interface HrmsUserLoginsService {

	public ResponseEntity<HrmsUserLogins> adduserlogin(HrmsUserLogins hrmsUserLogins);

	public ResponseEntity<HrmsUserLogins> updateuserlogin(HrmsUserLogins hrmsUserLogins, int id);

	public ResponseEntity<List<Userloginresponse>> viewlog(int id);

	public ResponseEntity<?> logout(int empid);

	public ResponseEntity<List<Userloginresponse>> listAlllogs();

}
