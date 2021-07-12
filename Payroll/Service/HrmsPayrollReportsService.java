package com.Hrms.Payroll.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.DTO.HealthInsuranceResponse;
import com.Hrms.Payroll.DTO.HeslbReportResponse;
import com.Hrms.Payroll.DTO.PAYEResponse;
import com.Hrms.Payroll.DTO.PaySlipResponse;
import com.Hrms.Payroll.DTO.PayrollBankAccountTranserResponse;
import com.Hrms.Payroll.DTO.PayrollJournal;
import com.Hrms.Payroll.DTO.PublicSocialSecurityFundResponse;
import com.Hrms.Payroll.DTO.WorkersCompensationFundResponse;

@Service

public interface HrmsPayrollReportsService {

	public ResponseEntity<HeslbReportResponse> getHeslbReportByMonthAndYear(int year, int month);

	public ResponseEntity<HeslbReportResponse> getHeslbReportByMonthAndYearAndEmpId(int year, int month, int empid);

	public ResponseEntity<PayrollBankAccountTranserResponse> getBankTransferByYearAndMonth(int year, int month);

	public ResponseEntity<PayrollBankAccountTranserResponse> getBankTransferByEmpIdAndYearAndMonth(int empid, int year,
			int month);

	public ResponseEntity<WorkersCompensationFundResponse> getWCFByMonthAndYear(int year, int month);

	public ResponseEntity<PublicSocialSecurityFundResponse> getPsssfPaymentByMonthAndYear(int year, int month);

	public ResponseEntity<PublicSocialSecurityFundResponse> getZssfPaymentByMonthAndYear(int year, int month);

	public ResponseEntity<HealthInsuranceResponse> getNHIFByMonthAndYear(int year, int month);

	public ResponseEntity<PAYEResponse> getPAYEByMonthAndYear(int year, int month);

	public ResponseEntity<PayrollJournal> getPayrollJournal(int year, int month);

	public ResponseEntity<PaySlipResponse> getPayrollPaySlip(int employeeid, int year, int month);

}
