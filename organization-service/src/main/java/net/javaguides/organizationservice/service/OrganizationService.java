package net.javaguides.organizationservice.service;

import net.javaguides.organizationservice.DTO.OrganizationDTO;

public interface OrganizationService {

    OrganizationDTO saveOrganization(OrganizationDTO organizationDTO);

    OrganizationDTO getOrganizationByCode(String organizationCode);
}
