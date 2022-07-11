package com.microsoft.teams.app.controller;




import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


 
// In this class, we have left the caching approach for tutorial simplicity.
// If users which they can enable caching in this application.
@RestController
@RequestMapping(value = "/api/redis/employee")
public class UtilityController {
 
    private static final Logger LOG = LoggerFactory.getLogger(UtilityController.class);
 
    
    
	@RequestMapping(value = "/getmsteamauthcode")
	@CrossOrigin(origins = "*")
	public String getmsteamauthcode(@RequestParam("code") final String code, @RequestParam("state") final String state,
			@RequestParam("session_state") final String session) throws IOException {
		LOG.info("Saving the new employee to the redis.");

		// FileWriter fout = new FileWriter("/home/husenaiah/Downloads/2022/card.txt");

		// Defining the file name of the file
		Path fileName = Path.of("/home/husenaiah/Downloads/2022/card.txt");

		// Writing into the file
		Files.writeString(fileName, code);

		System.out.println("code ->" + code);
		// fout.write(code);

		System.out.println("state ->" + state);
		System.out.println("session_state ->" + session);
		System.out.println("hellow world");
		System.out.println("hellow world");
		return "Successfully added. Employee with id= ";
	}
	
	  @GetMapping("/getall")
	    public String findAll() {
	        LOG.info("Fetching all employees from the redis.");
	        
	        return "";
	    }
	 
 
	/*
	 * // Save a new employee. // Url - http://localhost:10091/api/redis/employee
	 * 
	 * @PostMapping public String save(@RequestBody final Employee employee) {
	 * LOG.info("Saving the new employee to the redis."); service.save(employee);
	 * return "Successfully added. Employee with id= " + employee.getId(); }
	 */
    // Get all employees.
    // Url - http://localhost:10091/api/redis/employee/getall
  
    // Get employee by id.
    // Url - http://localhost:10091/api/redis/employee/get/<employee_id>
	/*
	 * @GetMapping("/get/{id}") public ResponseEntity<Employee>
	 * findById(@PathVariable("id") final String id) {
	 * LOG.info("Fetching employee with id= " + id); return new
	 * ResponseEntity<Employee>(service.findById(id),HttpStatus.OK); }
	 */
 
    // Delete employee by id.
    // Url - http://localhost:10091/api/redis/employee/delete/<employee_id>
	/*
	 * @DeleteMapping("/delete/{id}") public List<Employee>
	 * delete(@PathVariable("id") final String id) {
	 * LOG.info("Deleting employee with id= " + id); // Deleting the employee.
	 * service.delete(id); // Returning the all employees (post the deleted one).
	 * return findAll(); }
	 */
}


















