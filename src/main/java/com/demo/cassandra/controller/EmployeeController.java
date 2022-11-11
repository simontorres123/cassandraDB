package com.demo.cassandra.controller;

import com.demo.cassandra.model.Employee;
import com.demo.cassandra.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;
    @GetMapping(value = "/healthcheck", produces = "application/json; charset=utf-8")
    public String getHealthCheck()
    {
        return "{ \"isWorking\" : true }";
    }
    @GetMapping
    public List<Employee> getEmployees()
    {
        Iterable<Employee> result = employeeRepository.findAll();
        List<Employee> employeesList = new ArrayList<Employee>();
        result.forEach(employeesList::add);
        return employeesList;
    }
    @GetMapping("/{id}")
    public Optional<Employee> getEmployee(@PathVariable Integer id)
    {
        Optional<Employee> emp = employeeRepository.findById(id);
        return emp;
    }
    @PutMapping("/{id}")
    public Optional<Employee> updateEmployee(@RequestBody Employee newEmployee, @PathVariable Integer id)
    {
        Optional<Employee> optionalEmp = employeeRepository.findById(id);
        if (optionalEmp.isPresent()) {
            Employee emp = optionalEmp.get();
            emp.setFirstName(newEmployee.getFirstName());
            emp.setLastName(newEmployee.getLastName());
            emp.setEmail(newEmployee.getEmail());
            employeeRepository.save(emp);
        }
        return optionalEmp;
    }
    @DeleteMapping(value = "/{id}", produces = "application/json; charset=utf-8")
    public String deleteEmployee(@PathVariable Integer id) {
        Boolean result = employeeRepository.existsById(id);
        employeeRepository.deleteById(id);
        return "{ \"success\" : "+ (result ? "true" : "false") +" }";
    }
    @PostMapping
    public Employee addEmployee(@RequestBody Employee newEmployee)
    {
        Integer id = new Random().nextInt();
        Employee emp = new Employee(id, newEmployee.getFirstName(), newEmployee.getLastName(), newEmployee.getEmail());
        employeeRepository.save(emp);
        return emp;
    }
}
