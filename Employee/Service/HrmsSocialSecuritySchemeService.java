package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsSocialSecurityScheme;

@Service
public interface HrmsSocialSecuritySchemeService {

	public ResponseEntity<HrmsSocialSecurityScheme> save(HrmsSocialSecurityScheme hrmsSocialSecurityScheme);

	public ResponseEntity<Optional<HrmsSocialSecurityScheme>> viewHrmsSocialSecurityScheme(int id);

	public ResponseEntity<HrmsSocialSecurityScheme> update(HrmsSocialSecurityScheme hrmsSocialSecurityScheme, int id);

	public ResponseEntity<?> deleteHrmsSocialSecurityScheme(int id);

	public ResponseEntity<List<HrmsSocialSecurityScheme>> listHrmsSocialSecurityScheme();

}
