package com.Hrms.Payroll.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Payroll.DTO.HealthInsuranceResponse;
import com.Hrms.Payroll.DTO.HeslbReportResponse;
import com.Hrms.Payroll.DTO.PAYEResponse;
import com.Hrms.Payroll.DTO.PaySlipResponse;
import com.Hrms.Payroll.DTO.PayrollBankAccountTranserResponse;
import com.Hrms.Payroll.DTO.PayrollJournal;
import com.Hrms.Payroll.DTO.PublicSocialSecurityFundResponse;
import com.Hrms.Payroll.DTO.WorkersCompensationFundResponse;
import com.Hrms.Payroll.Service.HrmsPayrollReportsService;

@RestController
@RequestMapping("v1/payrollReports")
public class HrmsPayrollReportsController {

	@Autowired
	private HrmsPayrollReportsService hrmsPayrollReportsService;

	@GetMapping(value = "/getHeslbReportByMonthAndYear/{year}/{month}")
	public ResponseEntity<HeslbReportResponse> getHeslbReportByMonthAndYear(@PathVariable("year") int year,
			@PathVariable("month") int month) {
		return hrmsPayrollReportsService.getHeslbReportByMonthAndYear(year, month);

	}

	@GetMapping(value = "/getHeslbReportByMonthAndYearAndEmpId/{year}/{month}/{empid}")
	public ResponseEntity<HeslbReportResponse> getHeslbReportByMonthAndYearAndEmpId(@PathVariable("year") int year,
			@PathVariable("month") int month, @PathVariable("empid") int empid) {
		return hrmsPayrollReportsService.getHeslbReportByMonthAndYearAndEmpId(year, month, empid);
	}

	@GetMapping(value = "/getBankTransferByYearAndMonth/{year}/{month}")
	public ResponseEntity<PayrollBankAccountTranserResponse> getBankTransferByYearAndMonth(
			@PathVariable("year") int year, @PathVariable("month") int month) {
		return hrmsPayrollReportsService.getBankTransferByYearAndMonth(year, month);

	}

	@GetMapping(value = "/getBankTransferByEmpIdAndYearAndMonth/{empid}/{year}/{month}")
	public ResponseEntity<PayrollBankAccountTranserResponse> getBankTransferByEmpIdAndYearAndMonth(
			@PathVariable("empid") int empid, @PathVariable("year") int year, @PathVariable("month") int month) {
		return hrmsPayrollReportsService.getBankTransferByEmpIdAndYearAndMonth(empid, year, month);

	}

	@GetMapping(value = "/getWCFPaymentByMonthAndYear/{year}/{month}")
	public ResponseEntity<WorkersCompensationFundResponse> getWCFPaymentByMonthAndYear(@PathVariable("year") int year,
			@PathVariable("month") int month) {
		return hrmsPayrollReportsService.getWCFByMonthAndYear(year, month);

	}

	@GetMapping(value = "/getPSSSFPaymentByMonthAndYear/{year}/{month}")
	public ResponseEntity<PublicSocialSecurityFundResponse> getPSSSFPaymentByMonthAndYear(
			@PathVariable("year") int year, @PathVariable("month") int month) {
		return hrmsPayrollReportsService.getPsssfPaymentByMonthAndYear(year, month);
	}

	@GetMapping(value = "/getZSSFPaymentByMonthAndYear/{year}/{month}")
	public ResponseEntity<PublicSocialSecurityFundResponse> getZSSFPaymentByMonthAndYear(@PathVariable("year") int year,
			@PathVariable("month") int month) {

		return hrmsPayrollReportsService.getZssfPaymentByMonthAndYear(year, month);

	}

	@GetMapping(value = "/getNHIFByMonthAndYear/{year}/{month}")
	public ResponseEntity<HealthInsuranceResponse> getNHIFByMonthAndYear(@PathVariable("year") int year,
			@PathVariable("month") int month) {

		return hrmsPayrollReportsService.getNHIFByMonthAndYear(year, month);
	}

	@GetMapping(value = "/getPAYEByMonthAndYear/{year}/{month}")
	public ResponseEntity<PAYEResponse> getPAYEByMonthAndYear(@PathVariable("year") int year,
			@PathVariable("month") int month) {

		return hrmsPayrollReportsService.getPAYEByMonthAndYear(year, month);

	}

	@GetMapping(value = "/getPayrollJournal/{year}/{month}")
	public ResponseEntity<PayrollJournal> getPayrollJournal(@PathVariable("year") int year,
			@PathVariable("month") int month) {

		return hrmsPayrollReportsService.getPayrollJournal(year, month);

	}

	@GetMapping(value = "/getPayrollPaySlip/{empid}/{year}/{month}")
	public ResponseEntity<PaySlipResponse> getPayrollPaySlip(@PathVariable("empid") int empid,
			@PathVariable("year") int year, @PathVariable("month") int month) {
		return hrmsPayrollReportsService.getPayrollPaySlip(empid, year, month);
	}

}
