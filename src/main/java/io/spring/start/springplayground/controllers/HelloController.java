package io.spring.start.springplayground.controllers;

import io.spring.start.springplayground.models.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController("/")
@Slf4j
public class HelloController {

    @GetMapping("/")
    String helloWorld() {
        return "Hello world, Cool, okay";
    }

    @Autowired
    DataSource ds;

    @Autowired
    PlatformTransactionManager transactionManager;

    JdbcTemplate jdbcTemplate;

    @PostMapping("/")
    boolean addEmployee(@RequestBody Employee employee) {
        jdbcTemplate = new JdbcTemplate(ds);

        String insertEmployeeSql = "INSERT INTO employee (employee_id, name) values (?, ?)";

        try {
            jdbcTemplate.update(insertEmployeeSql, employee.getEmployeeId(), employee.getName());
        } catch (DataAccessException e) {
            log.debug("Failed to insert employee exception e: {?}", e);
            return false;
        }

        return true;
    }
}
