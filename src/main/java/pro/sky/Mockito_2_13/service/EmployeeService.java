package pro.sky.Mockito_2_13.service;

import org.springframework.stereotype.Service;
import pro.sky.Mockito_2_13.exception.EmployeeAlreadyAddException;
import pro.sky.Mockito_2_13.exception.EmployeeNotFoundException;
import pro.sky.Mockito_2_13.exception.EmployeeStorageIsFullException;
import pro.sky.Mockito_2_13.model.Employee;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.capitalize;

@Service
public class EmployeeService {

    private static int SIZE = 10;  //Недопустимое количество мест в Мапе
    private final Map<String, Employee> employees = new HashMap<>();

    public String addEmployee(String firstName, String lastName, double salary, int deportmentId) {
        if (employees.size() == SIZE) {
            throw new EmployeeStorageIsFullException();
        }
        var key = makeKey(firstName, lastName);  //Формирование ключа
        if (employees.containsKey(key)) { //Если такой ключ имеется в Мапе, то выскакивает исключение
            throw new EmployeeAlreadyAddException();
        }
        employees.put(key, new Employee(capitalize(firstName),
                capitalize(lastName),
                salary,
                deportmentId)); //we create a new employee by converting the lowercase initial letter in the first and last name to uppercase (if they were not uppercase initially)
        return "Сотрудник успешно добавлен в коллекцию Мап";
    }

    public Employee findEmployee(String firstName, String lastName) {
        var emp = employees.get(makeKey(firstName, lastName));  //Find an employee in Map using his key
        if (emp == null) {
            throw new EmployeeNotFoundException("Такого сотрудника нет!");
        }
        return emp;
    }

    public Boolean removeEmployee(String firstName, String lastName) {
        Employee removed = employees.remove(makeKey(firstName, lastName)); //Удаление в Мапе компонента по ключу
        if (removed == null) {  //Если объект отсутствует, то выбрасываем исключение:
            throw new EmployeeNotFoundException("Такого сотрудника нет!");
        }
        return true;
    }

    public Collection<Employee> getAll() {
        return employees.values();  //We are transferring all employees to Map
    }

    private String makeKey(String firstName, String lastName) {  //Built-in method for generating a first name + last name key consisting of only lowercase letters
        return (firstName + " " + lastName).toLowerCase();
    }
}
