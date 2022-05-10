package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    public void setup(){
        //employeeRepository = Mockito.mock(EmployeeRepository.class);
        //employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
                .id(1L)
                .firstName("mustafa")
                .lastName("güler")
                .email("mustafa@hotmail.com")
                .build();
    }

    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){


        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());

        given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        //when
        Employee savedEmployee = employeeService.saveEmployee(employee);
        //then
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException(){
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        //when
        assertThrows(ResourceNotFoundException.class,()->{
            employeeService.saveEmployee(employee);
        });


        //then
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    @Test
    public void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeeList(){
        //given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(1L)
                .firstName("mustafa")
                .lastName("güler")
                .email("mustafa@hotmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee1));

        //when
        List<Employee> employeeList = employeeService.getAllEmployees();

        //then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @Test
    public void givenEmptyEmployeeList_whenGetAllEmployees_thenReturnEmptyEmployeeList(){
        //given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("mustafa")
                .lastName("güler")
                .email("mustafa@hotmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        //when
        List<Employee> employeeList = employeeService.getAllEmployees();

        //then
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }

    @Test
    public void givenEmployeeId_whenEmployeeById_thenReturnEmployeeObject(){
        //given
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        //when
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();

        //then
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnEmployeeObject(){
        //given
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("ms4@hotmail.com");
        employee.setFirstName("Mstf");
        //when
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        //then
        assertThat(updatedEmployee.getEmail()).isEqualTo("ms4@hotmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Mstf");

    }

    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing(){
        //given

        long employeeId = 1L;

        willDoNothing().given(employeeRepository).deleteById(employeeId);

        //when
        employeeService.deleteEmployee(employeeId);
        //then
        verify(employeeRepository,times(1)).deleteById(employeeId);
    }
}























