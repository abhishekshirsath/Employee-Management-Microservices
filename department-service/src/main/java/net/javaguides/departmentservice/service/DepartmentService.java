package net.javaguides.departmentservice.service;

import net.javaguides.departmentservice.DTO.DepartmentDTO;

public interface DepartmentService {

    DepartmentDTO saveDepartment(DepartmentDTO departmentDTO);

    DepartmentDTO getDepartmentByCode(String code);

}
