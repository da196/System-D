package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsSocialSecuritySchemeServiceProvider;

@Service
public interface HrmsSocialSecuritySchemeServiceProviderService {

	public ResponseEntity<HrmsSocialSecuritySchemeServiceProvider> addSocialSecuritySchemeServiceProvider(
			HrmsSocialSecuritySchemeServiceProvider hrmsSocialSecuritySchemeServiceProvider);

	public ResponseEntity<HrmsSocialSecuritySchemeServiceProvider> getSocialSecuritySchemeServiceProviderById(int id);

	public ResponseEntity<HrmsSocialSecuritySchemeServiceProvider> updateSocialSecuritySchemeServiceProvider(
			HrmsSocialSecuritySchemeServiceProvider hrmsSocialSecuritySchemeServiceProvider, int id);

	public ResponseEntity<?> deleteSocialSecuritySchemeServiceProvider(int id);

	public ResponseEntity<List<HrmsSocialSecuritySchemeServiceProvider>> getAllSocialSecuritySchemeServiceProvider();

}
