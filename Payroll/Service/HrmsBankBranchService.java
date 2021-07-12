package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.DTO.BankBranchResponse;
import com.Hrms.Payroll.Entity.HrmsBankBranch;

@Service
public interface HrmsBankBranchService {

	public ResponseEntity<HrmsBankBranch> addBankBranch(HrmsBankBranch hrmsBankBranch);

	public ResponseEntity<BankBranchResponse> getBankBranchById(int id);

	public ResponseEntity<HrmsBankBranch> updateBankBranch(HrmsBankBranch hrmsBankBranch, int id);

	public ResponseEntity<?> deleteBankBranch(int id);

	public ResponseEntity<List<BankBranchResponse>> getAllBankBranch();

	public ResponseEntity<List<BankBranchResponse>> getBankBranchByBankId(int bankid);

}
