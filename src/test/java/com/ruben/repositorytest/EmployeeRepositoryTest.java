package com.ruben.repositorytest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    // JUnit Test for Save Employee
    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveEmployeeTest(){
        Employee employee = Employee.builder()
                .firstName("Ruben")
                .lastName("Neven")
                .email("rubenneven@gmail.com")
                .build();

        employeeRepository.save(employee);

        Assertions.assertThat(employee.getId()).isGreaterThan(0);
    }

    // JUnit Test for Get Employee
    @Test
    @Order(2)
    public void getEmployeeTest(){
        Employee employee = employeeRepository.findById(1L).get();
        Assertions.assertThat(employee.getId()).isEqualTo(1L);
    }

    // JUnit Test for Get All Employees
    @Test
    @Order(3)
    public void getAllEmployeeTest(){
        List<Employee> employees = employeeRepository.findAll();
        Assertions.assertThat(employees.size()).isGreaterThan(0);
    }

    // JUnit Test for Update Employee
    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateEmployeeTest(){
        Employee employee = employeeRepository.findById(1L).get();
        employee.setEmail("test@test.com");

        Employee updatedEmployee = employeeRepository.save(employee);

        Assertions.assertThat(updatedEmployee.getEmail()).isEqualTo("test@test.com");
    }

    // JUnit Test for Delete Employee
    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteEmployeeTest(){

        Employee employee = employeeRepository.findById(1L).get();

        employeeRepository.delete(employee);

        Employee nullEmployee = null;
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail("test@test.com");

        if (optionalEmployee.isPresent()){
            nullEmployee = optionalEmployee.get();
        }

        Assertions.assertThat(nullEmployee).isNull();
    }

}