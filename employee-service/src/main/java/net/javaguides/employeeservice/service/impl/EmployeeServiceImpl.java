package net.javaguides.employeeservice.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import net.javaguides.employeeservice.DTO.OrganizationDTO;
import net.javaguides.employeeservice.service.APIClient;
import net.javaguides.employeeservice.DTO.APIResponseDTO;
import net.javaguides.employeeservice.DTO.DepartmentDTO;
import net.javaguides.employeeservice.DTO.EmployeeDTO;
import net.javaguides.employeeservice.entity.Employee;
import net.javaguides.employeeservice.repository.EmployeeRepository;
import net.javaguides.employeeservice.service.APIClient;
import net.javaguides.employeeservice.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    //@Autowired
    private EmployeeRepository employeeRepository;

    //@Autowired
    private ModelMapper modelMapper;

//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private WebClient webClient;

      private APIClient apiClient;



    @Override
    public EmployeeDTO saveEmployeeDTO(EmployeeDTO employeeDTO) {

        Employee employee = modelMapper.map(employeeDTO,Employee.class);
        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeDTO savedEmployeeDTO = modelMapper.map(savedEmployee,EmployeeDTO.class);
        return savedEmployeeDTO;
    }

    //@CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Retry(name = "${spring.application.name}",fallbackMethod = "getDefaultDepartment")
    @Override
    public APIResponseDTO getEmployeeById(Long employeeId) {

        LOGGER.info("Inside getEmployeeById method.");

        Employee employee = employeeRepository.findById(employeeId).get();

        //Emp - Dept communication - RestTemplate
        /*ResponseEntity<DepartmentDTO> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/departments/"+employee.getDepartmentCode(),
                DepartmentDTO.class);

        DepartmentDTO departmentDTO = responseEntity.getBody();
*/
        //Emp - Dept comm with WebClient
        DepartmentDTO departmentDTO = webClient.get()
                .uri("http://localhost:8080/api/departments/"+employee.getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentDTO.class)
                .block();

        //OpenFeign client
/*        DepartmentDTO departmentDTO = apiClient.getDepartment(employee.getDepartmentCode());
        LOGGER.info("Calling API with FeignClient ID : {}",employee.getDepartmentCode());*/

        //Emp - Org comm with WebClient
        OrganizationDTO organizationDTO = webClient.get()
                .uri("http://localhost:8083/api/organizations/"+employee.getOrganizationCode())
                .retrieve()
                .bodyToMono(OrganizationDTO.class)
                .block();

        EmployeeDTO employeeDTO = modelMapper.map(employee,EmployeeDTO.class);

        APIResponseDTO apiResponseDTO = new APIResponseDTO();
        apiResponseDTO.setEmployeeDTO(employeeDTO);
        apiResponseDTO.setDepartmentDTO(departmentDTO);
        apiResponseDTO.setOrganizationDTO(organizationDTO);

        return apiResponseDTO;
    }

    //fallback method impl for getDepartmentById()
    public APIResponseDTO getDefaultDepartment(Long employeeId, Exception exception) {

        LOGGER.info("Inside getDefaultDepartment method.");

        Employee employee = employeeRepository.findById(employeeId).get();

        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setDepartmentName("R&D Department");
        departmentDTO.setDepartmentCode("RD001");
        departmentDTO.setDepartmentDescription("Research and Development Department");

        EmployeeDTO employeeDTO = modelMapper.map(employee,EmployeeDTO.class);

        APIResponseDTO apiResponseDTO = new APIResponseDTO();
        apiResponseDTO.setEmployeeDTO(employeeDTO);
        apiResponseDTO.setDepartmentDTO(departmentDTO);

        return apiResponseDTO;
    }


}
