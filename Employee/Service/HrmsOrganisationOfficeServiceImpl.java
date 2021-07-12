package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.OrganizationOfficeResponse;
import com.Hrms.Employee.Entity.HrmsOrganisationOffice;
import com.Hrms.Employee.Repository.HrmsLocationCityRepository;
import com.Hrms.Employee.Repository.HrmsOrganisationOfficeCategoryRepository;
import com.Hrms.Employee.Repository.HrmsOrganisationOfficeRepository;

@Service
public class HrmsOrganisationOfficeServiceImpl implements HrmsOrganisationOfficeService {

	@Autowired
	private HrmsOrganisationOfficeRepository hrmsOrganisationOfficeRepository;

	@Autowired
	private HrmsLocationCityRepository hrmsLocationCityRepository;

	@Autowired
	private HrmsOrganisationOfficeCategoryRepository hrmsOrganisationOfficeCategoryRepository;

	@Override
	public ResponseEntity<HrmsOrganisationOffice> addOrganisationOffice(HrmsOrganisationOffice hrmsOrganisationOffice) {

		hrmsOrganisationOffice.setUnique_id(UUID.randomUUID());
		hrmsOrganisationOffice.setActive(1);
		hrmsOrganisationOffice.setApproved(0);

		if (hrmsOrganisationOfficeRepository.existsByNameAndOfficetypeidAndActive(hrmsOrganisationOffice.getName(),
				hrmsOrganisationOffice.getOfficetypeid(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

		} else {
			if (hrmsLocationCityRepository.existsById(hrmsOrganisationOffice.getCityid())
					&& hrmsOrganisationOfficeCategoryRepository.existsById(hrmsOrganisationOffice.getOfficetypeid())) {

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsOrganisationOfficeRepository.save(hrmsOrganisationOffice));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsOrganisationOffice);
			}
		}
	}

	@Override
	public ResponseEntity<OrganizationOfficeResponse> getOrganisationOffice(int id) {
		if (hrmsOrganisationOfficeRepository.existsByIdAndActive(id, 1)) {
			HrmsOrganisationOffice organizationOffice = hrmsOrganisationOfficeRepository.findById(id).get();
			OrganizationOfficeResponse organizationOfficeResponse = new OrganizationOfficeResponse();

			organizationOfficeResponse.setActive(organizationOffice.getActive());
			organizationOfficeResponse.setApproved(organizationOffice.getApproved());
			organizationOfficeResponse.setCityid(organizationOffice.getCityid());

			if (organizationOffice.getCityid() != 0
					&& hrmsLocationCityRepository.existsById(organizationOffice.getCityid())) {
				organizationOfficeResponse.setCityName(
						hrmsLocationCityRepository.findById(organizationOffice.getCityid()).get().getName());
			}
			organizationOfficeResponse.setDescription(organizationOffice.getDescription());
			organizationOfficeResponse.setId(organizationOffice.getId());
			organizationOfficeResponse.setName(organizationOffice.getName());
			organizationOfficeResponse.setOfficetypeid(organizationOffice.getOfficetypeid());
			if (organizationOffice.getOfficetypeid() != 0
					&& hrmsOrganisationOfficeCategoryRepository.existsById(organizationOffice.getOfficetypeid())) {
				organizationOfficeResponse.setOfficetypeName(hrmsOrganisationOfficeCategoryRepository
						.findById(organizationOffice.getOfficetypeid()).get().getName());
			}

			return ResponseEntity.status(HttpStatus.OK).body(organizationOfficeResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsOrganisationOffice> updateOrganisationOffice(
			HrmsOrganisationOffice hrmsOrganisationOffice, int id) {
		hrmsOrganisationOffice.setDate_updated(LocalDateTime.now());
		hrmsOrganisationOffice.setActive(1);
		hrmsOrganisationOffice.setApproved(0);
		if (hrmsOrganisationOfficeRepository.existsById(id)) {
			if (hrmsLocationCityRepository.existsById(hrmsOrganisationOffice.getCityid())
					&& hrmsOrganisationOfficeCategoryRepository.existsById(hrmsOrganisationOffice.getOfficetypeid())) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsOrganisationOfficeRepository.save(hrmsOrganisationOffice));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsOrganisationOffice);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsOrganisationOffice);
		}
	}

	@Override
	public ResponseEntity<?> deleteOrganisationOffice(int id) {
		if (hrmsOrganisationOfficeRepository.existsById(id)) {
			HrmsOrganisationOffice hrmsOrganisationOffice = hrmsOrganisationOfficeRepository.findById(id).get();
			hrmsOrganisationOffice.setActive(0);
			hrmsOrganisationOffice.setDate_updated(LocalDateTime.now());
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsOrganisationOfficeRepository.save(hrmsOrganisationOffice));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<OrganizationOfficeResponse>> listOrganisationOffice() {
		List<OrganizationOfficeResponse> OrganizationOfficeResponselist = new ArrayList<>();
		hrmsOrganisationOfficeRepository.findByActive(1).forEach(organizationOffice -> {
			OrganizationOfficeResponse organizationOfficeResponse = new OrganizationOfficeResponse();

			organizationOfficeResponse.setActive(organizationOffice.getActive());
			organizationOfficeResponse.setApproved(organizationOffice.getApproved());
			organizationOfficeResponse.setCityid(organizationOffice.getCityid());

			if (organizationOffice.getCityid() != 0
					&& hrmsLocationCityRepository.existsById(organizationOffice.getCityid())) {
				organizationOfficeResponse.setCityName(
						hrmsLocationCityRepository.findById(organizationOffice.getCityid()).get().getName());
			}
			organizationOfficeResponse.setDescription(organizationOffice.getDescription());
			organizationOfficeResponse.setId(organizationOffice.getId());
			organizationOfficeResponse.setName(organizationOffice.getName());
			organizationOfficeResponse.setOfficetypeid(organizationOffice.getOfficetypeid());
			if (organizationOffice.getOfficetypeid() != 0
					&& hrmsOrganisationOfficeCategoryRepository.existsById(organizationOffice.getOfficetypeid())) {
				organizationOfficeResponse.setOfficetypeName(hrmsOrganisationOfficeCategoryRepository
						.findById(organizationOffice.getOfficetypeid()).get().getName());
			}

			OrganizationOfficeResponselist.add(organizationOfficeResponse);

		});

		return ResponseEntity.status(HttpStatus.OK).body(OrganizationOfficeResponselist);
	}

}
