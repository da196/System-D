package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsSalutation;

@Service
public interface HrmsSalutationService {

	public ResponseEntity<List<HrmsSalutation>> listHrmsSalutation();

}
