package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup(){
        employee = Employee.builder()
                .firstName("mustafa")
                .lastName("güler")
                .email("m@hotmail.com")
                .build();
    }

    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){

        //when - action or the behavior that we are going test
        Employee savedEmployee = employeeRepository.save(employee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);

    }

    @Test
    public void givenEmployeeList_whenFindAll_thenEmployeeList(){

        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("mustafa")
                .lastName("güler")
                .email("m@hotmail.com")
                .build();

        Employee employee1 = Employee.builder()
                .firstName("musa")
                .lastName("güler")
                .email("musa@hotmail.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        //when - action or the behavior that we are going test
        List<Employee> employees = employeeRepository.findAll();

        //then - verify the output
        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(2);
    }
    @DisplayName("JUnit test for get employee by id operation")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject(){

        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("mustafa")
                .lastName("güler")
                .email("m@hotmail.com")
                .build();

        employeeRepository.save(employee);

        //when - action or the behavior that we are going test
        Employee employeeDb = employeeRepository.findById(employee.getId()).get();

        //then - verify the output
        assertThat(employeeDb).isNotNull();
    }

    @DisplayName("JUnit test for get employee by email operation")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject(){
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("mustafa")
                .lastName("güler")
                .email("m@hotmail.com")
                .build();
        //when - action or the behavior that we are going test
        Employee employeeDb = employeeRepository.findByEmail(employee.getEmail()).get();
        //then - verify the output
        assertThat(employeeDb).isNotNull();

    }
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnEmployeeObject(){

        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("mustafa")
                .lastName("güler")
                .email("m@hotmail.com")
                .build();
        employeeRepository.save(employee);

        //when - action or the behavior that we are going test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("m@hotmail.com");
        savedEmployee.setEmail("Es");
        Employee updatedEmployee = employeeRepository.save(employee);

        //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("m@hotmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Es");
    }
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee(){

        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("mustafa")
                .lastName("güler")
                .email("m@hotmail.com")
                .build();
        employeeRepository.save(employee);
        //when - action or the behavior that we are going test
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        //then - verify the output
        assertThat(employeeOptional.isEmpty());
    }

    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject(){
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("mustafa")
                .lastName("güler")
                .email("m@hotmail.com")
                .build();
        employeeRepository.save(employee);
        String f = "mustafa";
        String l = "güler";

        //when - action or the behavior that we are going test
        Employee savedEmployee = employeeRepository.findByJPQL(f,l);

        assertThat(savedEmployee).isNotNull();

    }

    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject(){
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("mustafa")
                .lastName("güler")
                .email("m@hotmail.com")
                .build();
        employeeRepository.save(employee);
        String f = "mustafa";
        String l = "güler";

        //when - action or the behavior that we are going test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(f,l);

        assertThat(savedEmployee).isNotNull();

    }

    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject(){
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("mustafa")
                .lastName("güler")
                .email("m@hotmail.com")
                .build();
        employeeRepository.save(employee);

        //when - action or the behavior that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(),employee.getLastName());

        assertThat(savedEmployee).isNotNull();

    }

    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject(){
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("mustafa")
                .lastName("güler")
                .email("m@hotmail.com")
                .build();
        employeeRepository.save(employee);

        //when - action or the behavior that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSQLNamedParams(employee.getFirstName(),employee.getLastName());

        assertThat(savedEmployee).isNotNull();

    }
}

/*
*
*
//given - precondition or setup

//when - action or the behavior that we are going test

//then - verify the output
*
* */














































