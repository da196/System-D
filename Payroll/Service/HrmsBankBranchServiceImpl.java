package com.Hrms.Payroll.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.DTO.BankBranchResponse;
import com.Hrms.Payroll.Entity.HrmsBankBranch;
import com.Hrms.Payroll.Repository.HrmsBankBranchRepository;
import com.Hrms.Payroll.Repository.HrmsBankRepository;

@Service
public class HrmsBankBranchServiceImpl implements HrmsBankBranchService {

	@Autowired
	private HrmsBankBranchRepository hrmsBankBranchRepository;

	@Autowired
	private HrmsBankRepository hrmsBankRepository;

	@Override
	public ResponseEntity<HrmsBankBranch> addBankBranch(HrmsBankBranch hrmsBankBranch) {
		if (hrmsBankBranchRepository.existsByNameAndActive(hrmsBankBranch.getName(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsBankBranch);
		} else {
			if (hrmsBankRepository.existsByIdAndActive(hrmsBankBranch.getBankid(), 1)) {

				hrmsBankBranch.setActive(1);
				hrmsBankBranch.setApproved(0);
				hrmsBankBranch.setUnique_id(UUID.randomUUID());

				return ResponseEntity.status(HttpStatus.OK).body(hrmsBankBranchRepository.saveAndFlush(hrmsBankBranch));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsBankBranch);
			}
		}
	}

	@Override
	public ResponseEntity<BankBranchResponse> getBankBranchById(int id) {
		if (hrmsBankBranchRepository.existsByIdAndActive(id, 1)) {
			HrmsBankBranch dbm = hrmsBankBranchRepository.findByIdAndActive(id, 1);
			BankBranchResponse bankBranchResponse = new BankBranchResponse();
			bankBranchResponse.setActive(dbm.getActive());
			bankBranchResponse.setApproved(dbm.getApproved());
			bankBranchResponse.setBankid(dbm.getBankid());
			if (dbm.getBankid() != 0 && hrmsBankRepository.existsById(dbm.getBankid())) {
				bankBranchResponse.setBankName(hrmsBankRepository.findById(dbm.getBankid()).get().getName());
			}
			bankBranchResponse.setCode(dbm.getCode());
			bankBranchResponse.setId(dbm.getId());
			bankBranchResponse.setName(dbm.getName());
			bankBranchResponse.setPhysicaladdress(dbm.getPhysicaladdress());
			bankBranchResponse.setPostaladdress(dbm.getPostaladdress());
			bankBranchResponse.setSortcode(dbm.getSortcode());
			bankBranchResponse.setTelephone(dbm.getTelephone());

			return ResponseEntity.status(HttpStatus.OK).body(bankBranchResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsBankBranch> updateBankBranch(HrmsBankBranch hrmsBankBranch, int id) {
		if (hrmsBankBranchRepository.existsByIdAndActive(id, 1)) {
			if (hrmsBankRepository.existsByIdAndActive(hrmsBankBranch.getBankid(), 1)) {
				hrmsBankBranch.setActive(1);
				hrmsBankBranch.setApproved(0);
				hrmsBankBranch.setDate_updated(LocalDateTime.now());
				HrmsBankBranch hrmsBankBranch1 = hrmsBankBranchRepository.findById(id).get();
				if (hrmsBankBranch1.getDate_created() != null) {
					hrmsBankBranch.setDate_created(hrmsBankBranch1.getDate_created());
				}
				if (hrmsBankBranch1.getUnique_id() != null) {
					hrmsBankBranch.setUnique_id(hrmsBankBranch1.getUnique_id());
				}

				return ResponseEntity.status(HttpStatus.OK).body(hrmsBankBranchRepository.saveAndFlush(hrmsBankBranch));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsBankBranch);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deleteBankBranch(int id) {
		if (hrmsBankBranchRepository.existsByIdAndActive(id, 1)) {
			HrmsBankBranch hrmsBankBranch = hrmsBankBranchRepository.findByIdAndActive(id, 1);
			hrmsBankBranch.setActive(0);
			hrmsBankBranch.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsBankBranchRepository.saveAndFlush(hrmsBankBranch));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<BankBranchResponse>> getAllBankBranch() {

		List<BankBranchResponse> bankBranchResponselist = new ArrayList<>();

		List<HrmsBankBranch> dbms = hrmsBankBranchRepository.findByActive(1);
		dbms.forEach(dbm -> {
			BankBranchResponse bankBranchResponse = new BankBranchResponse();
			bankBranchResponse.setActive(dbm.getActive());
			bankBranchResponse.setApproved(dbm.getApproved());
			bankBranchResponse.setBankid(dbm.getBankid());
			if (dbm.getBankid() != 0 && hrmsBankRepository.existsById(dbm.getBankid())) {
				bankBranchResponse.setBankName(hrmsBankRepository.findById(dbm.getBankid()).get().getName());
			}
			bankBranchResponse.setCode(dbm.getCode());
			bankBranchResponse.setId(dbm.getId());
			bankBranchResponse.setName(dbm.getName());
			bankBranchResponse.setPhysicaladdress(dbm.getPhysicaladdress());
			bankBranchResponse.setPostaladdress(dbm.getPostaladdress());
			bankBranchResponse.setSortcode(dbm.getSortcode());
			bankBranchResponse.setTelephone(dbm.getTelephone());

			bankBranchResponselist.add(bankBranchResponse);

		});

		return ResponseEntity.status(HttpStatus.OK).body(bankBranchResponselist);

	}

	@Override
	public ResponseEntity<List<BankBranchResponse>> getBankBranchByBankId(int bankid) {
		if (hrmsBankRepository.existsById(bankid)) {
			List<BankBranchResponse> bankBranchResponselist = new ArrayList<>();

			List<HrmsBankBranch> dbms = hrmsBankBranchRepository.findByBankidAndActive(bankid, 1);
			dbms.forEach(dbm -> {
				BankBranchResponse bankBranchResponse = new BankBranchResponse();
				bankBranchResponse.setActive(dbm.getActive());
				bankBranchResponse.setApproved(dbm.getApproved());
				bankBranchResponse.setBankid(dbm.getBankid());
				if (dbm.getBankid() != 0 && hrmsBankRepository.existsById(dbm.getBankid())) {
					bankBranchResponse.setBankName(hrmsBankRepository.findById(dbm.getBankid()).get().getName());
				}
				bankBranchResponse.setCode(dbm.getCode());
				bankBranchResponse.setId(dbm.getId());
				bankBranchResponse.setName(dbm.getName());
				bankBranchResponse.setPhysicaladdress(dbm.getPhysicaladdress());
				bankBranchResponse.setPostaladdress(dbm.getPostaladdress());
				bankBranchResponse.setSortcode(dbm.getSortcode());
				bankBranchResponse.setTelephone(dbm.getTelephone());

				bankBranchResponselist.add(bankBranchResponse);

			});

			return ResponseEntity.status(HttpStatus.OK).body(bankBranchResponselist);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

	}

}
