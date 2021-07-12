package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsSocialSecuritySchemeServiceProvider;

@Repository
public interface HrmsSocialSecuritySchemeServiceProviderRepository
		extends JpaRepository<HrmsSocialSecuritySchemeServiceProvider, Integer> {

	boolean existsByNameAndActive(String name, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsSocialSecuritySchemeServiceProvider findByIdAndActive(int id, int i);

	List<HrmsSocialSecuritySchemeServiceProvider> findByActive(int i);

}
