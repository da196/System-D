package com.Hrms.Payroll.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Payroll.Service.SendPayslipNotification;

@RestController
@RequestMapping("v1/sendPayslipNotification")
public class PayrollSendPayslipNotificationController {
	@Autowired
	private SendPayslipNotification sendPayslipNotification;

	@GetMapping(value = "/sendPaySlipNotification/{slipdate}")
	public ResponseEntity<?> sendPaySlipNotification(@PathVariable("slipdate") String slipdate) {

		return sendPayslipNotification.sendPaySlipNotification(slipdate);

	}

}
