package net.javaguides.employeeservice.service;

import net.javaguides.employeeservice.DTO.DepartmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(url = "http://localhost:8080", value = "DEPARTMENT-SERVICE")
// Spring cloud Load balancer
@FeignClient(name = "DEPARTMENT-SERVICE")
@Service
public interface APIClient {

    @GetMapping("api/departments/{departmentCode}")
    DepartmentDTO getDepartment(@PathVariable String departmentCode);
}
