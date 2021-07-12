package com.Hrms.Payroll.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Repository.HrmsDesignationRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Payroll.DTO.PayrollWorkflowStepResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollWorkflowStep;
import com.Hrms.Payroll.Repository.HrmsPayrollWorkflowRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollWorkflowStepRepository;

@Service
public class HrmsPayrollWorkflowStepServiceImpl implements HrmsPayrollWorkflowStepService {

	@Autowired
	private HrmsPayrollWorkflowStepRepository hrmsPayrollWorkflowStepRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsDesignationRepository hrmsDesignationRepository;

	@Autowired
	private HrmsPayrollWorkflowRepository hrmsPayrollWorkflowRepository;

	@Override
	public ResponseEntity<HrmsPayrollWorkflowStep> addPayrollWorkflowStep(
			HrmsPayrollWorkflowStep hrmsPayrollWorkflowStep) {
		if (hrmsPayrollWorkflowStepRepository.existsByStepnumberAndActive(hrmsPayrollWorkflowStep.getStepnumber(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPayrollWorkflowStep);
		} else {
			if (hrmsEmployeeRepository.existsByIdAndActive(hrmsPayrollWorkflowStep.getCreatedbyid(), 1)

					&& hrmsPayrollWorkflowRepository.existsByIdAndActive(hrmsPayrollWorkflowStep.getWorkflowid(), 1)

					&& hrmsEmployeeRepository.existsByDesignationIdAndEmploymentstatusid(
							hrmsPayrollWorkflowStep.getDesignationactiveid(), 2)) {
				if ((hrmsPayrollWorkflowStep.getDesignationbackid() == 0 && hrmsEmployeeRepository
						.existsByDesignationIdAndEmploymentstatusid(hrmsPayrollWorkflowStep.getDesignationnextid(), 2))
						|| (hrmsPayrollWorkflowStep.getDesignationnextid() == 0
								&& hrmsEmployeeRepository.existsByDesignationIdAndEmploymentstatusid(
										hrmsPayrollWorkflowStep.getDesignationbackid(), 2))
						|| (hrmsEmployeeRepository.existsByDesignationIdAndEmploymentstatusid(
								hrmsPayrollWorkflowStep.getDesignationbackid(), 2)
								&& hrmsEmployeeRepository.existsByDesignationIdAndEmploymentstatusid(
										hrmsPayrollWorkflowStep.getDesignationnextid(), 2))) {
					hrmsPayrollWorkflowStep.setActive(1);
					hrmsPayrollWorkflowStep.setApproved(0);
					hrmsPayrollWorkflowStep.setUnique_id(UUID.randomUUID());
					hrmsPayrollWorkflowStep.setApprovedbyid(0);
					hrmsPayrollWorkflowStep.setUpdatedbyid(0);

					return ResponseEntity.status(HttpStatus.OK)
							.body(hrmsPayrollWorkflowStepRepository.saveAndFlush(hrmsPayrollWorkflowStep));
				} else {
					return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(hrmsPayrollWorkflowStep);
				}
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsPayrollWorkflowStep);
			}
		}
	}

	@Override
	public ResponseEntity<PayrollWorkflowStepResponse> getPayrollWorkflowStepById(int id) {
		if (hrmsPayrollWorkflowStepRepository.existsByIdAndActive(id, 1)) {

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			PayrollWorkflowStepResponse payrollWorkflowStepResponse = new PayrollWorkflowStepResponse();

			HrmsPayrollWorkflowStep hrmsPayrollWorkflowStep = hrmsPayrollWorkflowStepRepository.findById(id).get();
			payrollWorkflowStepResponse.setActive(hrmsPayrollWorkflowStep.getActive());
			payrollWorkflowStepResponse.setApproved(hrmsPayrollWorkflowStep.getApproved());

			if (hrmsPayrollWorkflowStep.getApproved_date() != null) {
				payrollWorkflowStepResponse
						.setApproved_date(simpleDateFormat.format(hrmsPayrollWorkflowStep.getApproved_date()));
			}

			if (hrmsPayrollWorkflowStep.getApprovedbyid() != 0
					&& hrmsEmployeeRepository.existsById(hrmsPayrollWorkflowStep.getApprovedbyid())) {

				StringBuilder fullname2 = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsPayrollWorkflowStep.getCreatedbyid())
						.get();

				fullname2.append(" -  " + hrmsEmployee.getFirstName().trim());
				fullname2.append(" " + hrmsEmployee.getLastName().trim());

				payrollWorkflowStepResponse.setApprovedby(fullname2.toString());
			}
			payrollWorkflowStepResponse.setApprovedbyid(hrmsPayrollWorkflowStep.getApprovedbyid());
			if (hrmsPayrollWorkflowStep.getCreatedbyid() != 0
					&& hrmsEmployeeRepository.existsById(hrmsPayrollWorkflowStep.getCreatedbyid())) {

				StringBuilder fullname1 = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsPayrollWorkflowStep.getCreatedbyid())
						.get();

				fullname1.append(" -  " + hrmsEmployee.getFirstName().trim());
				fullname1.append(" " + hrmsEmployee.getLastName().trim());

				payrollWorkflowStepResponse.setCreatedby(fullname1.toString());
			}
			payrollWorkflowStepResponse.setCreatedbyid(hrmsPayrollWorkflowStep.getCreatedbyid());
			if (hrmsPayrollWorkflowStep.getDesignationactiveid() != 0
					&& hrmsDesignationRepository.existsById(hrmsPayrollWorkflowStep.getDesignationactiveid())) {
				payrollWorkflowStepResponse.setDesignationactive(hrmsDesignationRepository
						.findById(hrmsPayrollWorkflowStep.getDesignationactiveid()).get().getName());
			}
			payrollWorkflowStepResponse.setDesignationactiveid(hrmsPayrollWorkflowStep.getDesignationactiveid());

			if (hrmsPayrollWorkflowStep.getDesignationbackid() != 0
					&& hrmsDesignationRepository.existsById(hrmsPayrollWorkflowStep.getDesignationbackid())) {
				payrollWorkflowStepResponse.setDesignationback(hrmsDesignationRepository
						.findById(hrmsPayrollWorkflowStep.getDesignationbackid()).get().getName());
			}
			payrollWorkflowStepResponse.setDesignationbackid(hrmsPayrollWorkflowStep.getDesignationbackid());

			if (hrmsPayrollWorkflowStep.getDesignationnextid() != 0
					&& hrmsDesignationRepository.existsById(hrmsPayrollWorkflowStep.getDesignationnextid())) {
				payrollWorkflowStepResponse.setDesignationnext(hrmsDesignationRepository
						.findById(hrmsPayrollWorkflowStep.getDesignationnextid()).get().getName());
			}
			payrollWorkflowStepResponse.setDesignationnextid(hrmsPayrollWorkflowStep.getDesignationnextid());
			payrollWorkflowStepResponse.setId(id);
			payrollWorkflowStepResponse.setRedirected(hrmsPayrollWorkflowStep.getRedirected());
			payrollWorkflowStepResponse.setStepnumber(hrmsPayrollWorkflowStep.getStepnumber());
			payrollWorkflowStepResponse.setStepweight(hrmsPayrollWorkflowStep.getStepweight());

			if (hrmsPayrollWorkflowStep.getUpdatedbyid() != 0
					&& hrmsEmployeeRepository.existsById(hrmsPayrollWorkflowStep.getUpdatedbyid())) {

				StringBuilder fullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsPayrollWorkflowStep.getUpdatedbyid())
						.get();

				fullname.append(" -  " + hrmsEmployee.getFirstName().trim());
				fullname.append(" " + hrmsEmployee.getLastName().trim());

				payrollWorkflowStepResponse.setUpdatedby(fullname.toString());
			}
			payrollWorkflowStepResponse.setUpdatedbyid(hrmsPayrollWorkflowStep.getUpdatedbyid());
			payrollWorkflowStepResponse.setWorkflowid(hrmsPayrollWorkflowStep.getWorkflowid());

			if (hrmsPayrollWorkflowStep.getWorkflowid() != 0
					&& hrmsPayrollWorkflowRepository.existsById(hrmsPayrollWorkflowStep.getWorkflowid())) {
				payrollWorkflowStepResponse.setWorkflowName(hrmsPayrollWorkflowRepository
						.findById(hrmsPayrollWorkflowStep.getWorkflowid()).get().getName());
			}

			return ResponseEntity.status(HttpStatus.OK).body(payrollWorkflowStepResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsPayrollWorkflowStep> updatePayrollWorkflowStep(
			HrmsPayrollWorkflowStep hrmsPayrollWorkflowStep, int id) {
		if (hrmsPayrollWorkflowStepRepository.existsByIdAndActive(id, 1)) {

			if (hrmsEmployeeRepository.existsByIdAndActive(hrmsPayrollWorkflowStep.getCreatedbyid(), 1)
					&& hrmsEmployeeRepository.existsByIdAndActive(hrmsPayrollWorkflowStep.getUpdatedbyid(), 1)

					&& hrmsPayrollWorkflowRepository.existsByIdAndActive(hrmsPayrollWorkflowStep.getWorkflowid(), 1)

					&& hrmsEmployeeRepository.existsByDesignationIdAndEmploymentstatusid(
							hrmsPayrollWorkflowStep.getDesignationactiveid(), 2)) {
				if ((hrmsPayrollWorkflowStep.getDesignationbackid() == 0 && hrmsEmployeeRepository
						.existsByDesignationIdAndEmploymentstatusid(hrmsPayrollWorkflowStep.getDesignationnextid(), 2))
						|| (hrmsPayrollWorkflowStep.getDesignationnextid() == 0
								&& hrmsEmployeeRepository.existsByDesignationIdAndEmploymentstatusid(
										hrmsPayrollWorkflowStep.getDesignationbackid(), 2))
						|| (hrmsEmployeeRepository.existsByDesignationIdAndEmploymentstatusid(
								hrmsPayrollWorkflowStep.getDesignationbackid(), 2)
								&& hrmsEmployeeRepository.existsByDesignationIdAndEmploymentstatusid(
										hrmsPayrollWorkflowStep.getDesignationnextid(), 2))) {

					HrmsPayrollWorkflowStep hrmsPayrollWorkflowStep1 = hrmsPayrollWorkflowStepRepository.findById(id)
							.get();

					hrmsPayrollWorkflowStep.setActive(1);
					hrmsPayrollWorkflowStep.setApproved(0);

					hrmsPayrollWorkflowStep.setApprovedbyid(0);
					hrmsPayrollWorkflowStep.setUpdatedbyid(hrmsPayrollWorkflowStep.getUpdatedbyid());
					hrmsPayrollWorkflowStep.setDate_updated(LocalDateTime.now());
					hrmsPayrollWorkflowStep.setCreatedbyid(hrmsPayrollWorkflowStep1.getCreatedbyid());

					return ResponseEntity.status(HttpStatus.OK)
							.body(hrmsPayrollWorkflowStepRepository.saveAndFlush(hrmsPayrollWorkflowStep));
				} else {
					return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(hrmsPayrollWorkflowStep);
				}
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsPayrollWorkflowStep);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsPayrollWorkflowStep);
		}
	}

	@Override
	public ResponseEntity<?> deletePayrollWorkflowStep(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<List<PayrollWorkflowStepResponse>> getAllPayrollWorkflowStep() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<PayrollWorkflowStepResponse> PayrollWorkflowStepResponselist = new ArrayList<>();

		List<HrmsPayrollWorkflowStep> hrmsPayrollWorkflowSteplist = hrmsPayrollWorkflowStepRepository.findByActive(1);

		hrmsPayrollWorkflowSteplist.forEach(hrmsPayrollWorkflowStep -> {
			PayrollWorkflowStepResponse payrollWorkflowStepResponse = new PayrollWorkflowStepResponse();

			payrollWorkflowStepResponse.setActive(hrmsPayrollWorkflowStep.getActive());
			payrollWorkflowStepResponse.setApproved(hrmsPayrollWorkflowStep.getApproved());

			if (hrmsPayrollWorkflowStep.getApproved_date() != null) {
				payrollWorkflowStepResponse
						.setApproved_date(simpleDateFormat.format(hrmsPayrollWorkflowStep.getApproved_date()));
			}

			if (hrmsPayrollWorkflowStep.getApprovedbyid() != 0
					&& hrmsEmployeeRepository.existsById(hrmsPayrollWorkflowStep.getApprovedbyid())) {

				StringBuilder fullname2 = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsPayrollWorkflowStep.getCreatedbyid())
						.get();

				fullname2.append(" -  " + hrmsEmployee.getFirstName().trim());
				fullname2.append(" " + hrmsEmployee.getLastName().trim());

				payrollWorkflowStepResponse.setApprovedby(fullname2.toString());
			}
			payrollWorkflowStepResponse.setApprovedbyid(hrmsPayrollWorkflowStep.getApprovedbyid());
			if (hrmsPayrollWorkflowStep.getCreatedbyid() != 0
					&& hrmsEmployeeRepository.existsById(hrmsPayrollWorkflowStep.getCreatedbyid())) {

				StringBuilder fullname1 = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsPayrollWorkflowStep.getCreatedbyid())
						.get();

				fullname1.append(" -  " + hrmsEmployee.getFirstName().trim());
				fullname1.append(" " + hrmsEmployee.getLastName().trim());

				payrollWorkflowStepResponse.setCreatedby(fullname1.toString());
			}
			payrollWorkflowStepResponse.setCreatedbyid(hrmsPayrollWorkflowStep.getCreatedbyid());
			if (hrmsPayrollWorkflowStep.getDesignationactiveid() != 0
					&& hrmsDesignationRepository.existsById(hrmsPayrollWorkflowStep.getDesignationactiveid())) {
				payrollWorkflowStepResponse.setDesignationactive(hrmsDesignationRepository
						.findById(hrmsPayrollWorkflowStep.getDesignationactiveid()).get().getName());
			}
			payrollWorkflowStepResponse.setDesignationactiveid(hrmsPayrollWorkflowStep.getDesignationactiveid());

			if (hrmsPayrollWorkflowStep.getDesignationbackid() != 0
					&& hrmsDesignationRepository.existsById(hrmsPayrollWorkflowStep.getDesignationbackid())) {
				payrollWorkflowStepResponse.setDesignationback(hrmsDesignationRepository
						.findById(hrmsPayrollWorkflowStep.getDesignationbackid()).get().getName());
			}
			payrollWorkflowStepResponse.setDesignationbackid(hrmsPayrollWorkflowStep.getDesignationbackid());

			if (hrmsPayrollWorkflowStep.getDesignationnextid() != 0
					&& hrmsDesignationRepository.existsById(hrmsPayrollWorkflowStep.getDesignationnextid())) {
				payrollWorkflowStepResponse.setDesignationnext(hrmsDesignationRepository
						.findById(hrmsPayrollWorkflowStep.getDesignationnextid()).get().getName());
			}
			payrollWorkflowStepResponse.setDesignationnextid(hrmsPayrollWorkflowStep.getDesignationnextid());
			payrollWorkflowStepResponse.setId(hrmsPayrollWorkflowStep.getId());
			payrollWorkflowStepResponse.setRedirected(hrmsPayrollWorkflowStep.getRedirected());
			payrollWorkflowStepResponse.setStepnumber(hrmsPayrollWorkflowStep.getStepnumber());
			payrollWorkflowStepResponse.setStepweight(hrmsPayrollWorkflowStep.getStepweight());

			if (hrmsPayrollWorkflowStep.getUpdatedbyid() != 0
					&& hrmsEmployeeRepository.existsById(hrmsPayrollWorkflowStep.getUpdatedbyid())) {

				StringBuilder fullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsPayrollWorkflowStep.getUpdatedbyid())
						.get();

				fullname.append(" -  " + hrmsEmployee.getFirstName().trim());
				fullname.append(" " + hrmsEmployee.getLastName().trim());

				payrollWorkflowStepResponse.setUpdatedby(fullname.toString());
			}
			payrollWorkflowStepResponse.setUpdatedbyid(hrmsPayrollWorkflowStep.getUpdatedbyid());
			payrollWorkflowStepResponse.setWorkflowid(hrmsPayrollWorkflowStep.getWorkflowid());

			if (hrmsPayrollWorkflowStep.getWorkflowid() != 0
					&& hrmsPayrollWorkflowRepository.existsById(hrmsPayrollWorkflowStep.getWorkflowid())) {
				payrollWorkflowStepResponse.setWorkflowName(hrmsPayrollWorkflowRepository
						.findById(hrmsPayrollWorkflowStep.getWorkflowid()).get().getName());
			}
			PayrollWorkflowStepResponselist.add(payrollWorkflowStepResponse);
		});

		return ResponseEntity.status(HttpStatus.OK).body(PayrollWorkflowStepResponselist);
	}

	@Override
	public ResponseEntity<List<PayrollWorkflowStepResponse>> getAllPayrollWorkflowStepNonApproved() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<PayrollWorkflowStepResponse> PayrollWorkflowStepResponselist = new ArrayList<>();

		List<HrmsPayrollWorkflowStep> hrmsPayrollWorkflowSteplist = hrmsPayrollWorkflowStepRepository
				.findByActiveAndApproved(1, 0);

		hrmsPayrollWorkflowSteplist.forEach(hrmsPayrollWorkflowStep -> {
			PayrollWorkflowStepResponse payrollWorkflowStepResponse = new PayrollWorkflowStepResponse();

			payrollWorkflowStepResponse.setActive(hrmsPayrollWorkflowStep.getActive());
			payrollWorkflowStepResponse.setApproved(hrmsPayrollWorkflowStep.getApproved());

			if (hrmsPayrollWorkflowStep.getApproved_date() != null) {
				payrollWorkflowStepResponse
						.setApproved_date(simpleDateFormat.format(hrmsPayrollWorkflowStep.getApproved_date()));
			}

			if (hrmsPayrollWorkflowStep.getApprovedbyid() != 0
					&& hrmsEmployeeRepository.existsById(hrmsPayrollWorkflowStep.getApprovedbyid())) {

				StringBuilder fullname2 = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsPayrollWorkflowStep.getCreatedbyid())
						.get();

				fullname2.append(" -  " + hrmsEmployee.getFirstName().trim());
				fullname2.append(" " + hrmsEmployee.getLastName().trim());

				payrollWorkflowStepResponse.setApprovedby(fullname2.toString());
			}
			payrollWorkflowStepResponse.setApprovedbyid(hrmsPayrollWorkflowStep.getApprovedbyid());
			if (hrmsPayrollWorkflowStep.getCreatedbyid() != 0
					&& hrmsEmployeeRepository.existsById(hrmsPayrollWorkflowStep.getCreatedbyid())) {

				StringBuilder fullname1 = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsPayrollWorkflowStep.getCreatedbyid())
						.get();

				fullname1.append(" -  " + hrmsEmployee.getFirstName().trim());
				fullname1.append(" " + hrmsEmployee.getLastName().trim());

				payrollWorkflowStepResponse.setCreatedby(fullname1.toString());
			}
			payrollWorkflowStepResponse.setCreatedbyid(hrmsPayrollWorkflowStep.getCreatedbyid());
			if (hrmsPayrollWorkflowStep.getDesignationactiveid() != 0
					&& hrmsDesignationRepository.existsById(hrmsPayrollWorkflowStep.getDesignationactiveid())) {
				payrollWorkflowStepResponse.setDesignationactive(hrmsDesignationRepository
						.findById(hrmsPayrollWorkflowStep.getDesignationactiveid()).get().getName());
			}
			payrollWorkflowStepResponse.setDesignationactiveid(hrmsPayrollWorkflowStep.getDesignationactiveid());

			if (hrmsPayrollWorkflowStep.getDesignationbackid() != 0
					&& hrmsDesignationRepository.existsById(hrmsPayrollWorkflowStep.getDesignationbackid())) {
				payrollWorkflowStepResponse.setDesignationback(hrmsDesignationRepository
						.findById(hrmsPayrollWorkflowStep.getDesignationbackid()).get().getName());
			}
			payrollWorkflowStepResponse.setDesignationbackid(hrmsPayrollWorkflowStep.getDesignationbackid());

			if (hrmsPayrollWorkflowStep.getDesignationnextid() != 0
					&& hrmsDesignationRepository.existsById(hrmsPayrollWorkflowStep.getDesignationnextid())) {
				payrollWorkflowStepResponse.setDesignationnext(hrmsDesignationRepository
						.findById(hrmsPayrollWorkflowStep.getDesignationnextid()).get().getName());
			}
			payrollWorkflowStepResponse.setDesignationnextid(hrmsPayrollWorkflowStep.getDesignationnextid());
			payrollWorkflowStepResponse.setId(hrmsPayrollWorkflowStep.getId());
			payrollWorkflowStepResponse.setRedirected(hrmsPayrollWorkflowStep.getRedirected());
			payrollWorkflowStepResponse.setStepnumber(hrmsPayrollWorkflowStep.getStepnumber());
			payrollWorkflowStepResponse.setStepweight(hrmsPayrollWorkflowStep.getStepweight());

			if (hrmsPayrollWorkflowStep.getUpdatedbyid() != 0
					&& hrmsEmployeeRepository.existsById(hrmsPayrollWorkflowStep.getUpdatedbyid())) {

				StringBuilder fullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsPayrollWorkflowStep.getUpdatedbyid())
						.get();

				fullname.append(" -  " + hrmsEmployee.getFirstName().trim());
				fullname.append(" " + hrmsEmployee.getLastName().trim());

				payrollWorkflowStepResponse.setUpdatedby(fullname.toString());
			}
			payrollWorkflowStepResponse.setUpdatedbyid(hrmsPayrollWorkflowStep.getUpdatedbyid());
			payrollWorkflowStepResponse.setWorkflowid(hrmsPayrollWorkflowStep.getWorkflowid());

			if (hrmsPayrollWorkflowStep.getWorkflowid() != 0
					&& hrmsPayrollWorkflowRepository.existsById(hrmsPayrollWorkflowStep.getWorkflowid())) {
				payrollWorkflowStepResponse.setWorkflowName(hrmsPayrollWorkflowRepository
						.findById(hrmsPayrollWorkflowStep.getWorkflowid()).get().getName());
			}
			PayrollWorkflowStepResponselist.add(payrollWorkflowStepResponse);
		});

		return ResponseEntity.status(HttpStatus.OK).body(PayrollWorkflowStepResponselist);
	}

	@Override
	public ResponseEntity<?> approvePayrollWorkflowStep(int id, int approverid, int status) {
		if (hrmsPayrollWorkflowStepRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollWorkflowStep hrmsPayrollWorkflowStep = hrmsPayrollWorkflowStepRepository.findById(id).get();
			hrmsPayrollWorkflowStep.setApproved(status);
			hrmsPayrollWorkflowStep.setApprovedbyid(approverid);
			hrmsPayrollWorkflowStep.setApproved_date(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollWorkflowStepRepository.saveAndFlush(hrmsPayrollWorkflowStep));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
