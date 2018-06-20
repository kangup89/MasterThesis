package com.kangup.dvpis;
import java.security.SignatureException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kangup.dvpis.api.Alarm;
import com.kangup.dvpis.api.MeasurementSetting;
import com.kangup.dvpis.api.Reminder;
import com.kangup.dvpis.api.SystemInformation;
import com.kangup.dvpis.jwt.JwtService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@RestController
public class HomeController {
	
	
	private final MongoController mongoService = new MongoController();

	private final JwtService jwtService = new JwtService();

	
	@RequestMapping(value = "/authenticateUser/{username}/{password}", 
					method = RequestMethod.GET, 
					produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<String[]> authenticateUser(@PathVariable String username, 
													 @PathVariable String password,
													 HttpServletResponse response){
		
		String[] output_primaryUserNames = mongoService.authenticateUser(username, password);
					
		if(output_primaryUserNames == null){
			return new ResponseEntity<String[]>(HttpStatus.NOT_FOUND);
		}else{
			String token = jwtService.tokenFor(username);
			response.setHeader("Authorization", token);
			
			return new ResponseEntity<String[]>(output_primaryUserNames, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/getMeasurementsSetting",
							method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<MeasurementSetting> getMeasurementsSetting(HttpServletRequest request) throws SignatureException {
		String secondaryName = getNameFromToken(request);
		String primaryName = request.getHeader("primaryUser");
		
		return new ResponseEntity<MeasurementSetting>(mongoService.getMeasurementsSetting(secondaryName, primaryName), HttpStatus.OK);
	}
		
	@RequestMapping(value = "/getMeasurementsByPeriod/{group}/{date}/{periodType}", 
							method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<int[]> getMeasurementsByPeriod(@PathVariable int group, @PathVariable long date, 
										@PathVariable int periodType, HttpServletRequest request) throws SignatureException {

		String secondaryName = getNameFromToken(request);
		String primaryName = request.getHeader("primaryUser");
		
		int[] measurements = mongoService.getMeasurementsByPeriod(secondaryName, primaryName, group, date, periodType);
		return new ResponseEntity<int[]>(measurements, HttpStatus.OK);	
	}

	@RequestMapping(value = "/getSystemInformation",
							method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
	public SystemInformation getSystemInformation(HttpServletRequest request) throws SignatureException{
			
		String secondaryName = getNameFromToken(request);
		SystemInformation information = mongoService.getSystemInformation(secondaryName);
		
		return information;
	}
	
	@RequestMapping(value = "/getLastAlarms/{num}",
			method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
	public ArrayList<Alarm> getLastAlarms(@PathVariable int num, HttpServletRequest request) throws SignatureException{
		
		String secondaryName = getNameFromToken(request);
		String primaryName = request.getHeader("primaryUser");
		
		ArrayList<Alarm> alarms = mongoService.getLastAlarms(secondaryName, primaryName, num);
		return alarms;
	}
	
	@RequestMapping(value = "/setReminder", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
	public void setReminder(HttpServletRequest request, @RequestBody Reminder reminder) throws SignatureException {
		String secondaryName = getNameFromToken(request);
							
		mongoService.setReminder(secondaryName, reminder);		
	}
	
	@RequestMapping(value = "/getReminder", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
	public Reminder getReminder(HttpServletRequest request) throws SignatureException {
		String secondaryName = getNameFromToken(request);
		
		System.out.println("getReminder call!");
		
		return mongoService.getReminder(secondaryName);
	}
	
	@RequestMapping(value = "/deleteReminder", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
	public void deleteReminder(HttpServletRequest request, @RequestBody Reminder reminder) throws SignatureException {
		String secondaryName = getNameFromToken(request);
					
		mongoService.deleteReminder(secondaryName, reminder);		
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public void userLogout(HttpServletRequest request) throws SignatureException{
		String secondaryName = getNameFromToken(request);
		mongoService.userLogout(secondaryName);
	}
	
	public String getNameFromToken(HttpServletRequest request) throws SignatureException{
		String token = request.getHeader("Authorization");
		Jws<Claims> claims = jwtService.verify(token);
		String secondaryName = claims.getBody().getSubject().toString();
		
		return secondaryName;
	}	
}
