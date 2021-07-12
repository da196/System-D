package com.Hrms.Payroll.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface SendPayslipNotification {
	public ResponseEntity<?> sendPaySlipNotification(String slipdate);

}
