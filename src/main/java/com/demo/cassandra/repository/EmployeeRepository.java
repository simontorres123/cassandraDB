package com.demo.cassandra.repository;

import com.demo.cassandra.model.Employee;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface EmployeeRepository extends CassandraRepository<Employee,Integer> {
}
