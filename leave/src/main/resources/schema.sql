CREATE TABLE `Salary` (
    `salary_id` INT PRIMARY KEY AUTO_INCREMENT,
    `employee_id` BIGINT,
    `base_salary` BIGINT,
    `hra` BIGINT,
    `da` BIGINT,
    `other_allowances` BIGINT,
    `deductions` BIGINT
--    FOREIGN KEY (employee_id) REFERENCES Employee(employee_id)
);


