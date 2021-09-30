import com.bridgeLabz.employeePayroll.EmployeeDepartment;
import com.bridgeLabz.employeePayroll.EmployeePayRollData;
import com.bridgeLabz.employeePayroll.EmployeePayRollService;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DBIOTest {


    @Test
    public void readDatabaseAndGetEntriesCount() throws SQLException {

        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        employeePayRollService.readEmployeePayrollData(EmployeePayRollService.IOService.DB_IO);
        long entries = employeePayRollService.listSize();
        employeePayRollService.printList();
        Assert.assertEquals(4, entries);

    }

    @Test
    public void readDatabaseAndUpdateTheDatabase() throws SQLException {

        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        employeePayRollService.readEmployeePayrollData(EmployeePayRollService.IOService.DB_IO);
        employeePayRollService.updateEmployeeData("Tresia", 2000);

    }

    @Test
    public void getEmployeePayrollDataByName() throws SQLException {

        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        List<EmployeePayRollData> employeePayRollDataList = employeePayRollService.getEmployeePayrollDataByName("Tresia");

        Assert.assertEquals("Tresia", employeePayRollDataList.get(0).getName());

    }

    @Test
    public void getEmployeePayrollDataByDate() throws SQLException {

        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        List<EmployeePayRollData> employeePayRollDataList = employeePayRollService.getEmployeePayrollDataByDateRange("2021-03-01", "2021-12-01");
        for (EmployeePayRollData data : employeePayRollDataList){
            System.out.println(data.getName());
        }
        Assert.assertEquals("Tresia", employeePayRollDataList.get(0).getName());

    }

    @Test
    public void getSumOfSalaryOfEmployee() throws SQLException {

        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        List<EmployeePayRollData> employeePayRollDataList = employeePayRollService.getSumOfEmployeeSalary();
        for (EmployeePayRollData data : employeePayRollDataList){
            System.out.println(data.getGender() + ", " + data.getSalary());
        }
        Assert.assertEquals(40000.0, employeePayRollDataList.get(0).getSalary(), 0.00);

    }

    @Test
    public void getAvgSalaryOfEmployee() throws SQLException {

        EmployeePayRollService employeePayRollService = new EmployeePayRollService();

        List<EmployeePayRollData> employeeAvgSalaryList = employeePayRollService.getAvgSumMinMaxEmployeeSalary("avg");
        List<EmployeePayRollData> employeeMinSalaryList = employeePayRollService.getAvgSumMinMaxEmployeeSalary("min");
        List<EmployeePayRollData> employeeMaxSalaryList = employeePayRollService.getAvgSumMinMaxEmployeeSalary("max");


        for (EmployeePayRollData data : employeeAvgSalaryList){
            System.out.println(data.getGender() + ", " + data.getSalary());
        }
        for (EmployeePayRollData data : employeeMinSalaryList){
            System.out.println(data.getGender() + ", " + data.getSalary());
        }
        for (EmployeePayRollData data : employeeMaxSalaryList){
            System.out.println(data.getGender() + ", " + data.getSalary());
        }
        Assert.assertEquals(20000.0, employeeAvgSalaryList.get(0).getSalary(), 0.00);
        Assert.assertEquals(10000.0, employeeMinSalaryList.get(0).getSalary(), 0.00);
        Assert.assertEquals(30000.0, employeeMaxSalaryList.get(0).getSalary(), 0.00);



    }

    @Test
    public void getGenderCountOfEmployee() throws SQLException {

        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        List<EmployeePayRollData> employeePayRollDataList = employeePayRollService.getEmployeeCountByGender();
        for (EmployeePayRollData data : employeePayRollDataList){
            System.out.println(data.getGender() + ", " + data.getSalary());
        }
        Assert.assertEquals(2, employeePayRollDataList.get(0).getSalary(), 0.00);

    }

    @Test
    public void addEmployeeToPayroll() throws SQLException {

        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        EmployeePayRollData employeePayRollDataList = employeePayRollService.addEmployeeToPayroll("Ajay", 'M', 23000, LocalDate.now());
        int result = employeePayRollService.getEmployeePayrollDataByName("Ajay").size();

        Assert.assertEquals(1, result);

    }

    @Test
    public void addDepartmentToEmployee() throws SQLException {

        EmployeePayRollService employeePayRollService = new EmployeePayRollService();
        EmployeeDepartment employeeDepartment= employeePayRollService.addEmployeeDepartment("marketing", "Ajay");

        Assert.assertEquals(2, employeeDepartment.getDepartment_id());
        Assert.assertEquals(15, employeeDepartment.getEmp_id()); 


    }
}
