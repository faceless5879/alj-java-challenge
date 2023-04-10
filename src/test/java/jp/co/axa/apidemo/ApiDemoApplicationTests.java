package jp.co.axa.apidemo;

import jp.co.axa.apidemo.services.EmployeeService;
import jp.co.axa.apidemo.entities.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;

import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiDemoApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testGetEmployees() throws Exception {
		Employee employee = new Employee();
		employee.setName("Test user");
		employee.setSalary(1000);
		List<Employee> employees = new ArrayList<>(Arrays.asList(employee));

		given(employeeService.retrieveEmployees()).willReturn(employees);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Test user"));
	}

	@Test
	public void testGetEmployeeById() throws Exception {
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("Test user");
		employee.setSalary(1000);

		given(employeeService.getEmployee(1L)).willReturn(employee);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Test user"))
				.andExpect(jsonPath("$.salary").value(1000));
	}

	@Test
	public void testPostEmployee() throws Exception {
		Employee employee = new Employee();
		employee.setName("Test user");
		employee.setSalary(1000);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(employee)))
				.andExpect(status().isOk());

		then(employeeService).should(times(1)).saveEmployee(any(Employee.class));
	}

	@Test
	public void testDeleteEmployee() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/employees/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		then(employeeService).should(times(1)).deleteEmployee(1L);
	}

	@Test
	public void testPutEmployee() throws Exception {
		Employee employee = new Employee();
		employee.setName("Test user");
		employee.setSalary(1000);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(employee)))
				.andExpect(status().isOk());

		given(employeeService.getEmployee(1L)).willReturn(employee);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Test user"))
				.andExpect(jsonPath("$.salary").value(1000));
	}
}
