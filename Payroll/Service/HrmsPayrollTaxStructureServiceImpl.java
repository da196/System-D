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

import com.Hrms.Employee.Repository.HrmsCurrencyRepository;
import com.Hrms.Payroll.DTO.PayrollTaxStructureResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollTaxStructure;
import com.Hrms.Payroll.Repository.HrmsPayrollTaxStructureRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollTaxTypeRepository;

@Service
public class HrmsPayrollTaxStructureServiceImpl implements HrmsPayrollTaxStructureService {

	@Autowired
	private HrmsPayrollTaxStructureRepository hrmsPayrollTaxStructureRepository;

	@Autowired
	private HrmsPayrollTaxTypeRepository hrmsPayrollTaxTypeRepository;

	@Autowired
	private HrmsCurrencyRepository hrmsCurrencyRepository;

	@Override
	public ResponseEntity<HrmsPayrollTaxStructure> addPayrollTaxStructure(
			HrmsPayrollTaxStructure hrmsPayrollTaxStructure) {
		if (hrmsPayrollTaxStructureRepository.existsByTaxtypeidAndCurrencyidAndActiveAndAmountmaxAndAmountmin(
				hrmsPayrollTaxStructure.getTaxtypeid(), hrmsPayrollTaxStructure.getCurrencyid(), 1,
				hrmsPayrollTaxStructure.getAmountmax(), hrmsPayrollTaxStructure.getAmountmin())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPayrollTaxStructure);
		} else {
			if (hrmsPayrollTaxTypeRepository.existsByIdAndActive(hrmsPayrollTaxStructure.getTaxtypeid(), 1)
					&& hrmsCurrencyRepository.existsByIdAndActive(hrmsPayrollTaxStructure.getCurrencyid(), 1)) {
				hrmsPayrollTaxStructure.setActive(1);
				hrmsPayrollTaxStructure.setApproved(0);
				hrmsPayrollTaxStructure.setUnique_id(UUID.randomUUID());

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPayrollTaxStructureRepository.saveAndFlush(hrmsPayrollTaxStructure));
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsPayrollTaxStructure);
			}
		}
	}

	@Override
	public ResponseEntity<PayrollTaxStructureResponse> getPayrollTaxStructureById(int id) {
		if (hrmsPayrollTaxStructureRepository.existsByIdAndActive(id, 1)) {
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			PayrollTaxStructureResponse payrollTaxStructureResponse = new PayrollTaxStructureResponse();
			HrmsPayrollTaxStructure hrmsPayrollTaxStructure = hrmsPayrollTaxStructureRepository.findByIdAndActive(id,
					1);

			payrollTaxStructureResponse.setActive(hrmsPayrollTaxStructure.getActive());
			payrollTaxStructureResponse.setAmountmax(hrmsPayrollTaxStructure.getAmountmax());
			payrollTaxStructureResponse.setAmountmin(hrmsPayrollTaxStructure.getAmountmin());
			payrollTaxStructureResponse.setAmount(hrmsPayrollTaxStructure.getAmount());
			payrollTaxStructureResponse.setApproved(hrmsPayrollTaxStructure.getApproved());
			if (hrmsPayrollTaxStructure.getCurrencyid() != 0
					&& hrmsCurrencyRepository.existsById(hrmsPayrollTaxStructure.getCurrencyid())) {
				payrollTaxStructureResponse.setCurrency(
						hrmsCurrencyRepository.findById(hrmsPayrollTaxStructure.getCurrencyid()).get().getName());
			}
			payrollTaxStructureResponse.setCurrencyid(hrmsPayrollTaxStructure.getCurrencyid());
			if (hrmsPayrollTaxStructure.getDatelastchanged() != null) {
				payrollTaxStructureResponse
						.setDatelastchanged(simpleDateFormat.format(hrmsPayrollTaxStructure.getDatelastchanged()));
			}
			payrollTaxStructureResponse.setDescription(hrmsPayrollTaxStructure.getDescription());
			payrollTaxStructureResponse.setId(id);
			payrollTaxStructureResponse.setIsformularcomputed(hrmsPayrollTaxStructure.getIsformularcomputed());
			payrollTaxStructureResponse.setRate(hrmsPayrollTaxStructure.getRate());
			if (hrmsPayrollTaxStructure.getTaxtypeid() != 0
					&& hrmsPayrollTaxTypeRepository.existsById(hrmsPayrollTaxStructure.getTaxtypeid())) {
				payrollTaxStructureResponse.setTaxtype(
						hrmsPayrollTaxTypeRepository.findById(hrmsPayrollTaxStructure.getTaxtypeid()).get().getName());
			}
			payrollTaxStructureResponse.setTaxtypeid(hrmsPayrollTaxStructure.getTaxtypeid());

			return ResponseEntity.status(HttpStatus.OK).body(payrollTaxStructureResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsPayrollTaxStructure> updatePayrollTaxStructure(
			HrmsPayrollTaxStructure hrmsPayrollTaxStructure, int id) {
		if (hrmsPayrollTaxStructureRepository.existsByIdAndActive(id, 1)) {
			if (hrmsPayrollTaxTypeRepository.existsByIdAndActive(hrmsPayrollTaxStructure.getTaxtypeid(), 1)
					&& hrmsCurrencyRepository.existsByIdAndActive(hrmsPayrollTaxStructure.getCurrencyid(), 1)) {
				hrmsPayrollTaxStructure.setActive(1);
				hrmsPayrollTaxStructure.setApproved(0);
				hrmsPayrollTaxStructure.setDate_updated(LocalDateTime.now());

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPayrollTaxStructureRepository.saveAndFlush(hrmsPayrollTaxStructure));
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsPayrollTaxStructure);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePayrollTaxStructure(int id) {
		if (hrmsPayrollTaxStructureRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollTaxStructure hrmsPayrollTaxStructure = hrmsPayrollTaxStructureRepository.findByIdAndActive(id,
					1);
			hrmsPayrollTaxStructure.setActive(0);
			hrmsPayrollTaxStructure.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollTaxStructureRepository.saveAndFlush(hrmsPayrollTaxStructure));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PayrollTaxStructureResponse>> getAllPayrollTaxStructure() {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		List<PayrollTaxStructureResponse> PayrollTaxStructureResponselist = new ArrayList<>();
		List<HrmsPayrollTaxStructure> hrmsPayrollTaxStructurelist = hrmsPayrollTaxStructureRepository.findByActive(1);
		hrmsPayrollTaxStructurelist.forEach(hrmsPayrollTaxStructure -> {
			PayrollTaxStructureResponse payrollTaxStructureResponse = new PayrollTaxStructureResponse();

			payrollTaxStructureResponse.setActive(hrmsPayrollTaxStructure.getActive());
			payrollTaxStructureResponse.setAmountmax(hrmsPayrollTaxStructure.getAmountmax());
			payrollTaxStructureResponse.setAmountmin(hrmsPayrollTaxStructure.getAmountmin());
			payrollTaxStructureResponse.setAmount(hrmsPayrollTaxStructure.getAmount());
			payrollTaxStructureResponse.setApproved(hrmsPayrollTaxStructure.getApproved());
			if (hrmsPayrollTaxStructure.getCurrencyid() != 0
					&& hrmsCurrencyRepository.existsById(hrmsPayrollTaxStructure.getCurrencyid())) {
				payrollTaxStructureResponse.setCurrency(
						hrmsCurrencyRepository.findById(hrmsPayrollTaxStructure.getCurrencyid()).get().getName());
			}
			payrollTaxStructureResponse.setCurrencyid(hrmsPayrollTaxStructure.getCurrencyid());
			if (hrmsPayrollTaxStructure.getDatelastchanged() != null) {
				payrollTaxStructureResponse
						.setDatelastchanged(simpleDateFormat.format(hrmsPayrollTaxStructure.getDatelastchanged()));
			}
			payrollTaxStructureResponse.setDescription(hrmsPayrollTaxStructure.getDescription());
			payrollTaxStructureResponse.setId(hrmsPayrollTaxStructure.getId());
			payrollTaxStructureResponse.setIsformularcomputed(hrmsPayrollTaxStructure.getIsformularcomputed());
			payrollTaxStructureResponse.setRate(hrmsPayrollTaxStructure.getRate());
			if (hrmsPayrollTaxStructure.getTaxtypeid() != 0
					&& hrmsPayrollTaxTypeRepository.existsById(hrmsPayrollTaxStructure.getTaxtypeid())) {
				payrollTaxStructureResponse.setTaxtype(
						hrmsPayrollTaxTypeRepository.findById(hrmsPayrollTaxStructure.getTaxtypeid()).get().getName());
			}
			payrollTaxStructureResponse.setTaxtypeid(hrmsPayrollTaxStructure.getTaxtypeid());

			PayrollTaxStructureResponselist.add(payrollTaxStructureResponse);
		});

		return ResponseEntity.status(HttpStatus.OK).body(PayrollTaxStructureResponselist);
	}

	@Override
	public ResponseEntity<PayrollTaxStructureResponse> getPayrollTaxStructureByAmount(Double amount) {
		if (amount > 0.00) {
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			PayrollTaxStructureResponse payrollTaxStructureResponse = new PayrollTaxStructureResponse();

			List<HrmsPayrollTaxStructure> hrmsPayrollTaxStructurelist = hrmsPayrollTaxStructureRepository
					.findByActive(1);
			hrmsPayrollTaxStructurelist.forEach(hrmsPayrollTaxStructure -> {
				if (hrmsPayrollTaxTypeRepository.findById(hrmsPayrollTaxStructure.getTaxtypeid()).get().getName()
						.toLowerCase().contains("paye")
						&& (amount >= hrmsPayrollTaxStructure.getAmountmin()
								&& amount <= hrmsPayrollTaxStructure.getAmountmax())) {

					payrollTaxStructureResponse.setActive(hrmsPayrollTaxStructure.getActive());
					payrollTaxStructureResponse.setAmountmax(hrmsPayrollTaxStructure.getAmountmax());
					payrollTaxStructureResponse.setAmountmin(hrmsPayrollTaxStructure.getAmountmin());

					payrollTaxStructureResponse.setAmount(hrmsPayrollTaxStructure.getAmount());
					Double amounttaxpaye = (double) (hrmsPayrollTaxStructure.getAmount()
							+ (double) (hrmsPayrollTaxStructure.getRate()
									* (amount - (hrmsPayrollTaxStructure.getAmountmin() - 1))));
					payrollTaxStructureResponse.setAmountPaye(amounttaxpaye);

					payrollTaxStructureResponse.setApproved(hrmsPayrollTaxStructure.getApproved());
					if (hrmsPayrollTaxStructure.getCurrencyid() != 0
							&& hrmsCurrencyRepository.existsById(hrmsPayrollTaxStructure.getCurrencyid())) {
						payrollTaxStructureResponse.setCurrency(hrmsCurrencyRepository
								.findById(hrmsPayrollTaxStructure.getCurrencyid()).get().getName());
					}
					payrollTaxStructureResponse.setCurrencyid(hrmsPayrollTaxStructure.getCurrencyid());
					if (hrmsPayrollTaxStructure.getDatelastchanged() != null) {
						payrollTaxStructureResponse.setDatelastchanged(
								simpleDateFormat.format(hrmsPayrollTaxStructure.getDatelastchanged()));
					}
					payrollTaxStructureResponse.setDescription(hrmsPayrollTaxStructure.getDescription());
					payrollTaxStructureResponse.setId(hrmsPayrollTaxStructure.getId());
					payrollTaxStructureResponse.setIsformularcomputed(hrmsPayrollTaxStructure.getIsformularcomputed());
					payrollTaxStructureResponse.setRate(hrmsPayrollTaxStructure.getRate());
					if (hrmsPayrollTaxStructure.getTaxtypeid() != 0
							&& hrmsPayrollTaxTypeRepository.existsById(hrmsPayrollTaxStructure.getTaxtypeid())) {
						payrollTaxStructureResponse.setTaxtype(hrmsPayrollTaxTypeRepository
								.findById(hrmsPayrollTaxStructure.getTaxtypeid()).get().getName());
					}
					payrollTaxStructureResponse.setTaxtypeid(hrmsPayrollTaxStructure.getTaxtypeid());
				}
			});

			return ResponseEntity.status(HttpStatus.OK).body(payrollTaxStructureResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);

		}
	}

}
