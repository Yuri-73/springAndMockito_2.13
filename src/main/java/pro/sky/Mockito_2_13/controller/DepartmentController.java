package pro.sky.Mockito_2_13.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.Mockito_2_13.exception.EmployeeNotFoundException;
import pro.sky.Mockito_2_13.model.Employee;
import pro.sky.Mockito_2_13.service.DepartmentService;


import java.util.List;
import java.util.Map;

    @RestController
    @RequestMapping("/department")
    public class DepartmentController {
        private final DepartmentService service;

        public DepartmentController(DepartmentService service) {
            this.service = service;
        }
        @GetMapping("{deptId}/salary/sum")
        public double sumByDept(@PathVariable int deptId) {  //Сумма зарплат по выбранному департаменту
            return service.sum(deptId);
        }

        @GetMapping("{deptId}/salary/max")
        public double max(@PathVariable int deptId) {  //Максимальная зарплата по выбранному департаменту
            return service.maxSalary(deptId);
        }

        @GetMapping("{deptId}/salary/min")
        public String min(@PathVariable int deptId) {
            try {
                return "" + service.minSalary(deptId);
            } catch (EmployeeNotFoundException e) {
                return e.getMessage();
            }
        }

        @GetMapping("{deptId}/employees")
        public List<Employee> find(@PathVariable int deptId) {  //Вывод списка сотрудников выбранного департамента
            return service.findAllByDept(deptId);
        }
        @GetMapping("/employees")
        public Map<Integer, List<Employee>> group() {  //Вывод сотрудников всех департаментов друг за другом
            return service.groupDeDept();
        }
}
