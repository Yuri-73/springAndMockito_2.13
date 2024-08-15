package pro.sky.Mockito_2_13.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.sky.Mockito_2_13.exception.EmployeeAlreadyAddException;
import pro.sky.Mockito_2_13.exception.EmployeeNotFoundException;
import pro.sky.Mockito_2_13.exception.EmployeeStorageIsFullException;
import pro.sky.Mockito_2_13.model.Employee;

class EmployeeServiceTest {
    EmployeeService employeeService = new EmployeeService();

    @Test
    void testAdd() {
        //test:
        employeeService.addEmployee("test", "test2", 50_000d, 1);
        var allEmployees = employeeService.getAll();
        //check:
        Assertions.assertEquals(1, allEmployees.size());
        var employee = allEmployees.iterator().next();  //Вытаскиваем значение элемента Листа
        //Вариант сравнения через junit.jupiter-5:
        Assertions.assertEquals("Test", employee.getFirstName());
        Assertions.assertEquals("Test2", employee.getLastName());
        Assertions.assertEquals(50_000d, employee.getSalary());
        Assertions.assertEquals(1, employee.getDepartment());
        //Вариант сравнения через assertThat():
        org.assertj.core.api.Assertions.assertThat("Test2").isEqualTo(employee.getLastName());
    }
    @Test
    void testAddWhenStorageIsFull() {
        //test:
        for (int i = 0; i < 10; i++) {
            employeeService.addEmployee("test_", "test_test_" + i, 0d, 0);
        }
        //check - при добавлении элемента за предел Мапы:
        Assertions.assertThrows(EmployeeStorageIsFullException.class, () -> employeeService.addEmployee("test", "test", 0d, 0));
    }

    @Test
    void testAddWhenAlreadyExists() {
        //test:
        employeeService.addEmployee("test", "test", 0d, 0);
        //check - если такой ключ в Мапе уже есть:
        Assertions.assertThrows(EmployeeAlreadyAddException.class, () -> employeeService.addEmployee("test", "test", 0d, 0));
    }

    @Test
    void testFinde() {
        //test:
        employeeService.addEmployee("test", "test2", 50_000d, 1);
        //check - находим добавленный элемент в Мапе:
        var actual = employeeService.findEmployee("test", "test2");
        Assertions.assertEquals("Test", actual.getFirstName());
        Assertions.assertEquals("Test2", actual.getLastName());
        Assertions.assertEquals(50_000d, actual.getSalary());
        Assertions.assertEquals(1, actual.getDepartment());
    }

    @Test
    void testFindWhenNotExist() {
        //check - убеждаемся в выскакивании исключения при отсутствии в Мапе элемента по ключу:
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> employeeService.findEmployee("test", "test2"));
    }

    @Test
    void testRemove() {
        //test:
        employeeService.addEmployee("test", "test2", 50_000d, 1);
        //check - убеждаемся, что в Мапе 1 элемент:
        Assertions.assertEquals(1, employeeService.getAll().size());
        //check - убеждаемся, что в Мапе объект удаления создан:
        Assertions.assertTrue(employeeService.removeEmployee("test", "test2"));
        //check - Выброс исключения, если в Мапе нет такого объекта:
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> employeeService.removeEmployee("not_add", "not_add"));
    }

    @Test
    void testGetAll() {
        //test - добавлены 3 компонента в Мапу:
        employeeService.addEmployee("test1", "test_test1", 50_000d, 1);
        employeeService.addEmployee("test2", "test_test2", -50_000d, 1);
        employeeService.addEmployee("test3", "test_test3", 50_000d, -1);

        var all = employeeService.getAll();
        //check - убеждаемся, что в Листе всего 3 компонента:
        org.assertj.core.api.Assertions.assertThat(all.size()).isEqualTo(3);
        //check - убеждаемся, что Лист содержит указанные 3 объекта в любом порядке с именами и фамилиями с прописной буквы:
        org.assertj.core.api.Assertions.assertThat(all)
                .containsExactlyInAnyOrder(
                        new Employee("Test1", "Test_test1", 50_000d, 1),
                        new Employee("Test2", "Test_test2", -50_000d, 1),
                        new Employee("Test3", "Test_test3", 50_000d, -1));
    }
}