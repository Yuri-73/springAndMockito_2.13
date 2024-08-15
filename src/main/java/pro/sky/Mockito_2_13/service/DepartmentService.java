package pro.sky.Mockito_2_13.service;

import org.springframework.stereotype.Service;
import pro.sky.Mockito_2_13.exception.EmployeeNotFoundException;
import pro.sky.Mockito_2_13.model.Employee;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public double maxSalary(int deptId) {
        return employeeService.getAll()  //List of employees from Mapa
                .stream()
                .filter(e -> e.getDepartment() == deptId)
                .map(Employee::getSalary)  //Turning Mapa into Salary List
                .max(Comparator.comparingDouble(o->o))//Determining the maximum salary from a List with data type Double (Option)

                .orElseThrow(() -> new EmployeeNotFoundException("Нет значений"));  //If the List is empty, an exception is thrown.
    }
    public double minSalary(int deptId) {
        return employeeService.getAll()
                .stream()
                .filter(e -> e.getDepartment() == deptId)
                .map(Employee::getSalary)
                .min(Comparator.comparingDouble(o->o))
                .orElseThrow(() -> new EmployeeNotFoundException("Нет сотрудников в департаменте!"));
    }
    public List<Employee> findAllByDept(int deptId) {
        return employeeService.getAll()  //List of employees from Mapa
                .stream()
                .filter(e -> e.getDepartment() == deptId)
                .collect(Collectors.toList());  //Output of the List of all employees of the department
    }

    public Map<Integer, List<Employee>> groupDeDept() {  //Формирование Мапы, где ключ - номер департамента, а значение - сотрудник этого департамента
        Map<Integer, List<Employee>> map = employeeService.getAll()  //Список сотрудников из Мапы
                .stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));  //Группировка: ключ - департамент, а значение - список сотрудников
        return map;
    }

    public double sum(int deptId) {
        return employeeService.getAll() //List of employees from Mapa
                .stream()
                .filter(e -> e.getDepartment() == deptId)  //Фильтрация Списка только по нужному департаменту
                .mapToDouble(Employee::getSalary)
                .sum();
    }
}
