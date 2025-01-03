package net.javaguides.organizationservice.service.impl;

import net.javaguides.organizationservice.DTO.OrganizationDTO;
import net.javaguides.organizationservice.entity.Organization;
import net.javaguides.organizationservice.repository.OrganizationRepository;
import net.javaguides.organizationservice.service.OrganizationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrganizationDTO saveOrganization(OrganizationDTO organizationDTO) {

        Organization organization = modelMapper.map(organizationDTO,Organization.class);

        Organization savedOrganization =  organizationRepository.save(organization);

        OrganizationDTO savedOrganizationDTO = modelMapper.map(savedOrganization,OrganizationDTO.class);

        return savedOrganizationDTO;

    }

    @Override
    public OrganizationDTO getOrganizationByCode(String organizationCode) {

        Organization organization = organizationRepository.findByOrganizationCode(organizationCode);

        OrganizationDTO organizationDTO = modelMapper.map(organization,OrganizationDTO.class);

        return organizationDTO;
    }
}
