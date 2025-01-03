package net.javaguides.employeeservice.service;

import net.javaguides.employeeservice.DTO.APIResponseDTO;
import net.javaguides.employeeservice.DTO.EmployeeDTO;

public interface EmployeeService {

    EmployeeDTO saveEmployeeDTO(EmployeeDTO employeeDTO);

    APIResponseDTO getEmployeeById(Long employeeId);

}
