package com.Hrms.Payroll.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Repository.HrmsCurrencyRepository;
import com.Hrms.Employee.Repository.HrmsInsuranceProviderRepository;
import com.Hrms.Payroll.DTO.PayrollContributionMandatoryInsuranceResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollContributionMandatoryInsurance;
import com.Hrms.Payroll.Repository.HrmsInsuranceTypeRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionMandatoryInsuranceRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionTypeMandatoryRepository;

@Service
public class HrmsPayrollContributionMandatoryInsuranceServiceImpl
		implements HrmsPayrollContributionMandatoryInsuranceService {

	@Autowired
	private HrmsPayrollContributionMandatoryInsuranceRepository hrmsPayrollContributionMandatoryInsuranceRepository;

	@Autowired
	private HrmsPayrollContributionTypeMandatoryRepository hrmsPayrollContributionTypeMandatoryRepository;

	@Autowired
	private HrmsCurrencyRepository hrmsCurrencyRepository;

	@Autowired
	private HrmsInsuranceTypeRepository hrmsInsuranceTypeRepository;

	@Autowired

	private HrmsInsuranceProviderRepository hrmsInsuranceProviderRepository;

	@Override
	public ResponseEntity<HrmsPayrollContributionMandatoryInsurance> addPayrollContributionMandatoryInsurance(
			HrmsPayrollContributionMandatoryInsurance hrmsPayrollContributionMandatoryInsurance) {
		if (hrmsPayrollContributionMandatoryInsuranceRepository.existsByContributiontypeidAndActive(
				hrmsPayrollContributionMandatoryInsurance.getContributiontypeid(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPayrollContributionMandatoryInsurance);
		} else {
			if (hrmsInsuranceProviderRepository
					.existsByIdAndActive(hrmsPayrollContributionMandatoryInsurance.getServiceproviderid(), 1)
					&& hrmsInsuranceTypeRepository
							.existsByIdAndActive(hrmsPayrollContributionMandatoryInsurance.getInsurancetypeid(), 1)
					&& hrmsPayrollContributionTypeMandatoryRepository.existsByIdAndActive(
							hrmsPayrollContributionMandatoryInsurance.getContributiontypeid(), 1)) {
				hrmsPayrollContributionMandatoryInsurance.setActive(1);
				hrmsPayrollContributionMandatoryInsurance.setApproved(0);
				hrmsPayrollContributionMandatoryInsurance.setUnique_id(UUID.randomUUID());

				hrmsPayrollContributionMandatoryInsurance
						.setRate(hrmsPayrollContributionMandatoryInsurance.getRateemployee()
								+ hrmsPayrollContributionMandatoryInsurance.getRateemployer());
				hrmsPayrollContributionMandatoryInsurance
						.setAmount(hrmsPayrollContributionMandatoryInsurance.getAmountemployee()
								+ hrmsPayrollContributionMandatoryInsurance.getAmountemployer());

				return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollContributionMandatoryInsuranceRepository
						.saveAndFlush(hrmsPayrollContributionMandatoryInsurance));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
						.body(hrmsPayrollContributionMandatoryInsurance);
			}

		}
	}

	@Override
	public ResponseEntity<PayrollContributionMandatoryInsuranceResponse> getPayrollContributionMandatoryInsuranceById(
			int id) {
		if (hrmsPayrollContributionMandatoryInsuranceRepository.existsByIdAndActive(id, 1)) {

			HrmsPayrollContributionMandatoryInsurance dbm = hrmsPayrollContributionMandatoryInsuranceRepository
					.findByIdAndActive(id, 1);
			PayrollContributionMandatoryInsuranceResponse payrollContributionMandatoryInsuranceResponse = new PayrollContributionMandatoryInsuranceResponse();

			payrollContributionMandatoryInsuranceResponse.setActive(dbm.getActive());
			payrollContributionMandatoryInsuranceResponse.setAmount(dbm.getAmount());
			payrollContributionMandatoryInsuranceResponse.setAmountemployee(dbm.getAmountemployee());
			payrollContributionMandatoryInsuranceResponse.setAmountemployer(dbm.getAmountemployer());
			payrollContributionMandatoryInsuranceResponse.setApproved(dbm.getApproved());
			if (dbm.getContributiontypeid() != 0
					&& hrmsPayrollContributionTypeMandatoryRepository.existsById(dbm.getContributiontypeid())) {
				payrollContributionMandatoryInsuranceResponse
						.setContributiontype(hrmsPayrollContributionTypeMandatoryRepository
								.findById(dbm.getContributiontypeid()).get().getName());
			}
			payrollContributionMandatoryInsuranceResponse.setContributiontypeid(dbm.getContributiontypeid());
			if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
				payrollContributionMandatoryInsuranceResponse
						.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
			}

			payrollContributionMandatoryInsuranceResponse.setCurrencyid(dbm.getCurrencyid());

			payrollContributionMandatoryInsuranceResponse.setDescription(dbm.getDescription());

			payrollContributionMandatoryInsuranceResponse.setId(dbm.getId());

			payrollContributionMandatoryInsuranceResponse.setIsformularcomputed(dbm.getIsformularcomputed());

			payrollContributionMandatoryInsuranceResponse.setRate(dbm.getRate());

			payrollContributionMandatoryInsuranceResponse.setRateemployee(dbm.getRateemployee());

			payrollContributionMandatoryInsuranceResponse.setRateemployer(dbm.getRateemployer());
			if (dbm.getServiceproviderid() != 0
					&& hrmsInsuranceProviderRepository.existsById(dbm.getServiceproviderid())) {
				payrollContributionMandatoryInsuranceResponse.setServiceprovider(
						hrmsInsuranceProviderRepository.findById(dbm.getServiceproviderid()).get().getName());
			}

			payrollContributionMandatoryInsuranceResponse.setServiceproviderid(dbm.getServiceproviderid());

			payrollContributionMandatoryInsuranceResponse.setInsurancetypeid(dbm.getInsurancetypeid());

			if (dbm.getInsurancetypeid() != 0 && hrmsInsuranceTypeRepository.existsById(dbm.getInsurancetypeid())) {
				payrollContributionMandatoryInsuranceResponse.setInsurancetype(
						hrmsInsuranceTypeRepository.findById(dbm.getInsurancetypeid()).get().getName());
			}

			return ResponseEntity.status(HttpStatus.OK).body(payrollContributionMandatoryInsuranceResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsPayrollContributionMandatoryInsurance> updatePayrollContributionMandatoryInsurance(
			HrmsPayrollContributionMandatoryInsurance hrmsPayrollContributionMandatoryInsurance, int id) {
		if (hrmsPayrollContributionMandatoryInsuranceRepository.existsByIdAndActive(id, 1)) {

			if (hrmsInsuranceProviderRepository
					.existsByIdAndActive(hrmsPayrollContributionMandatoryInsurance.getServiceproviderid(), 1)
					&& hrmsPayrollContributionTypeMandatoryRepository
							.existsByIdAndActive(hrmsPayrollContributionMandatoryInsurance.getContributiontypeid(), 1)
					&& hrmsInsuranceTypeRepository
							.existsByIdAndActive(hrmsPayrollContributionMandatoryInsurance.getInsurancetypeid(), 1)) {
				hrmsPayrollContributionMandatoryInsurance.setActive(1);
				hrmsPayrollContributionMandatoryInsurance.setApproved(0);
				hrmsPayrollContributionMandatoryInsurance.setDate_updated(LocalDateTime.now());

				hrmsPayrollContributionMandatoryInsurance
						.setRate(hrmsPayrollContributionMandatoryInsurance.getRateemployee()
								+ hrmsPayrollContributionMandatoryInsurance.getRateemployer());
				hrmsPayrollContributionMandatoryInsurance
						.setAmount(hrmsPayrollContributionMandatoryInsurance.getAmountemployee()
								+ hrmsPayrollContributionMandatoryInsurance.getAmountemployer());

				UUID uuid = hrmsPayrollContributionMandatoryInsuranceRepository.findById(id).get().getUnique_id();
				if (uuid != null) {

					hrmsPayrollContributionMandatoryInsurance.setUnique_id(uuid);
				}
				LocalDateTime createdd = hrmsPayrollContributionMandatoryInsuranceRepository.findById(id).get()
						.getDate_created();
				if (createdd != null) {
					hrmsPayrollContributionMandatoryInsurance.setDate_created(createdd);
				}
				return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollContributionMandatoryInsuranceRepository
						.saveAndFlush(hrmsPayrollContributionMandatoryInsurance));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
						.body(hrmsPayrollContributionMandatoryInsurance);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePayrollContributionMandatoryInsurance(int id) {
		if (hrmsPayrollContributionMandatoryInsuranceRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollContributionMandatoryInsurance hrmsPayrollContributionMandatoryInsurance = hrmsPayrollContributionMandatoryInsuranceRepository
					.findByIdAndActive(id, 1);
			hrmsPayrollContributionMandatoryInsurance.setActive(0);
			hrmsPayrollContributionMandatoryInsurance.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollContributionMandatoryInsuranceRepository
					.saveAndFlush(hrmsPayrollContributionMandatoryInsurance));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PayrollContributionMandatoryInsuranceResponse>> getAllPayrollContributionMandatoryInsurance() {
		List<HrmsPayrollContributionMandatoryInsurance> dbms = hrmsPayrollContributionMandatoryInsuranceRepository
				.findByActiveOrderByIdDesc(1);

		List<PayrollContributionMandatoryInsuranceResponse> payrollContributionMandatoryInsuranceResponselist = new ArrayList<>();
		dbms.forEach(dbm -> {
			PayrollContributionMandatoryInsuranceResponse payrollContributionMandatoryInsuranceResponse = new PayrollContributionMandatoryInsuranceResponse();

			payrollContributionMandatoryInsuranceResponse.setActive(dbm.getActive());
			payrollContributionMandatoryInsuranceResponse.setAmount(dbm.getAmount());
			payrollContributionMandatoryInsuranceResponse.setAmountemployee(dbm.getAmountemployee());
			payrollContributionMandatoryInsuranceResponse.setAmountemployer(dbm.getAmountemployer());
			payrollContributionMandatoryInsuranceResponse.setApproved(dbm.getApproved());
			if (dbm.getContributiontypeid() != 0
					&& hrmsPayrollContributionTypeMandatoryRepository.existsById(dbm.getContributiontypeid())) {
				payrollContributionMandatoryInsuranceResponse
						.setContributiontype(hrmsPayrollContributionTypeMandatoryRepository
								.findById(dbm.getContributiontypeid()).get().getName());
			}
			payrollContributionMandatoryInsuranceResponse.setContributiontypeid(dbm.getContributiontypeid());
			if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
				payrollContributionMandatoryInsuranceResponse
						.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
			}

			payrollContributionMandatoryInsuranceResponse.setCurrencyid(dbm.getCurrencyid());

			payrollContributionMandatoryInsuranceResponse.setDescription(dbm.getDescription());

			payrollContributionMandatoryInsuranceResponse.setId(dbm.getId());

			payrollContributionMandatoryInsuranceResponse.setIsformularcomputed(dbm.getIsformularcomputed());

			payrollContributionMandatoryInsuranceResponse.setRate(dbm.getRate());

			payrollContributionMandatoryInsuranceResponse.setRateemployee(dbm.getRateemployee());

			payrollContributionMandatoryInsuranceResponse.setRateemployer(dbm.getRateemployer());
			if (dbm.getServiceproviderid() != 0
					&& hrmsInsuranceProviderRepository.existsById(dbm.getServiceproviderid())) {
				payrollContributionMandatoryInsuranceResponse.setServiceprovider(
						hrmsInsuranceProviderRepository.findById(dbm.getServiceproviderid()).get().getName());
			}

			payrollContributionMandatoryInsuranceResponse.setServiceproviderid(dbm.getServiceproviderid());

			payrollContributionMandatoryInsuranceResponse.setInsurancetypeid(dbm.getInsurancetypeid());

			if (dbm.getInsurancetypeid() != 0 && hrmsInsuranceTypeRepository.existsById(dbm.getInsurancetypeid())) {
				payrollContributionMandatoryInsuranceResponse.setInsurancetype(
						hrmsInsuranceTypeRepository.findById(dbm.getInsurancetypeid()).get().getName());
			}

			payrollContributionMandatoryInsuranceResponselist.add(payrollContributionMandatoryInsuranceResponse);
		});

		return ResponseEntity.status(HttpStatus.OK).body(payrollContributionMandatoryInsuranceResponselist);
	}

}
