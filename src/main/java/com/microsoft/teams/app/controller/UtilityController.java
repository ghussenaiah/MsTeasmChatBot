package com.microsoft.teams.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonPrimitive;
import com.microsoft.graph.models.TeamsAppInstallation;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.teams.app.AuthenticationService;
import com.microsoft.teams.app.entity.AdpCardRequest;

import okhttp3.Request;

// In this class, we have left the caching approach for tutorial simplicity.
// If users which they can enable caching in this application.
@RestController
@RequestMapping(value = "/api")
public class UtilityController {

	private static final Logger log = LoggerFactory.getLogger(UtilityController.class);
	
	
	
	// Install app checker 
	//@PostConstruct	
	public void apichecker() throws Exception {

		try {

			final GraphServiceClient<Request> graphClient = AuthenticationService.getInstance();
			TeamsAppInstallation teamsAppInstallation = new TeamsAppInstallation();
			teamsAppInstallation.additionalDataManager().put("teamsApp@odata.bind", new JsonPrimitive(
					"https://graph.microsoft.com/v1.0/appCatalogs/teamsApps/c3cd172d-b69a-4e8f-847b-51d4746e2f3f"));

			/*
			 * teamsAppInstallation.additionalDataManager().put("teamsApp@odata.bind", new
			 * JsonPrimitive(
			 * "https://graph.microsoft.com/v1.0/appCatalogs/teamsApps/fb162ead-38f7-4473-85db-5389af86b5f8"
			 * ));
			 */
			graphClient.chats("19:493cf7bf446f44a995aaf83f2586c1c0@thread.v2").installedApps().buildRequest()
					.post(teamsAppInstallation);
		} catch (Exception e) {
			System.out.println("issue checking here");
			throw new Exception();
		}

	}
	

	@RequestMapping(value = "/getmsteamauthcode")
	@CrossOrigin(origins = "*")
	public String getmsteamauthcode(@RequestParam("code") final String code, @RequestParam("state") final String state,
			@RequestParam("session_state") final String session) throws IOException {
		log.info("Saving the new employee to the redis.");

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

	@PostMapping("/post")
	public String postBody(@RequestBody AdpCardRequest comment) {
		log.info("Fetching all employees from the redis.");
		log.info("Fetching employee from the redis.%s", comment);
		System.out.println("comment is " + comment.getComment());
		boolean b=true;
		
		while(b) {
			System.out.println("qweqweqweqwe");
		}
		
		return "Hello " + comment;
	}

	@RequestMapping("/getHello")
	public String getHello() {
		return "Hello ";
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
