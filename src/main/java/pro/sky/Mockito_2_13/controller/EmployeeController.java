package pro.sky.Mockito_2_13.controller;


import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.Mockito_2_13.exception.EmployeeNotFoundException;
import pro.sky.Mockito_2_13.exception.WrongNameException;
import pro.sky.Mockito_2_13.model.Employee;
import pro.sky.Mockito_2_13.service.EmployeeService;


import java.util.Collection;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/add")  //Adding elements to Mapa. Key - first name + last name, value - object.
    public String add(@RequestParam String firstName,
                       @RequestParam String lastName,
                       @RequestParam double salary,
                       @RequestParam int departmentId) {
        check(firstName, lastName);  //Built-in method that detects non-alphabetic characters in first and last name strings. If they are present, there will be a WrongNameException()
        Employee employee = new Employee(firstName, lastName, salary, departmentId);
        service.addEmployee(firstName, lastName, salary, departmentId);
        return "Сотрудник " + employee.toString() + " успешно внесён в коллекцию";
    }

    @GetMapping("/get")
    public String get(@RequestParam String firstName, @RequestParam String lastName) {  //Finding a Map element
        try {
            check(firstName, lastName);  //Built-in method that detects non-alphabetic characters in first and last name strings. If they are present, there will be a WrongNameException()
            return "Сотрудник " + service.findEmployee(firstName, lastName) + " в списке значится";
        } catch (WrongNameException e) {
            return e.getMessage();
        } catch (EmployeeNotFoundException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/remove")
    public String remove(@RequestParam String firstName, @RequestParam String lastName) {  //Removing a Map element by its first and last name
        check(firstName, lastName);  //Built-in method that detects non-alphabetic characters in first and last name strings. If they are present, there will be a WrongNameException()
        service.removeEmployee(firstName, lastName);
        return "Сотрудник успешно удалён из коллекции";
    }

    @GetMapping("/all")
    public Collection<Employee> getAll() {  //Вывод только значений элементов Мапы
        return service.getAll();
    }

    private void check(String... args) {
        for (String arg : args) {
            if (!StringUtils.isAlpha(arg)) {
                throw new WrongNameException("В параметрах имени и(или) фамилии сотрудника есть небуквенные символы");
            }
        }
    }
}
