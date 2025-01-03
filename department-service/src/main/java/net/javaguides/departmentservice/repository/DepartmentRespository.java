package net.javaguides.departmentservice.repository;

import net.javaguides.departmentservice.DTO.DepartmentDTO;
import net.javaguides.departmentservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRespository extends JpaRepository<Department,Long> {

    Department findByDepartmentCode(String departmentCode);

}
