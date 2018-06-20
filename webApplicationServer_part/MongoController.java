package com.kangup.dvpis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import com.kangup.dvpis.api.Alarm;
import com.kangup.dvpis.api.Measurement;
import com.kangup.dvpis.api.MeasurementSetting;
import com.kangup.dvpis.api.PrimaryUser;
import com.kangup.dvpis.api.QueryOutput;
import com.kangup.dvpis.api.Reminder;
import com.kangup.dvpis.api.SecondaryUser;
import com.kangup.dvpis.api.SystemInformation;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongoController {


	// Set credentials      
	MongoCredential credential = MongoCredential.createScramSha1Credential("userName", "dbName", "password".toCharArray());
	ServerAddress serverAddress = new ServerAddress("adress", 39261);
	//ServerAddress serverAddress = new ServerAddress("localhost", 27017);
	
	// Mongo Client
	MongoClient mongoClient = new MongoClient(serverAddress,Arrays.asList(credential)); 

	// Mongo DB Factory
	SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(
	            mongoClient, "dvpis_database");

	MongoTemplate mongoTemplate = new MongoTemplate(simpleMongoDbFactory);
	
	public SecondaryUser findSecondary(String username){
		Criteria criteria = new Criteria("username");
		criteria.is(username);		
		Query query = new Query(criteria);
					
		SecondaryUser user = mongoTemplate.findOne(query, SecondaryUser.class, "Secondary_Users");
		
		return user;
	}
	
	public PrimaryUser findPrimary(SecondaryUser secondaryUser, String primaryName){
		PrimaryUser primaryUser = null;
		for(PrimaryUser primary : secondaryUser.getPrimaryUsers()){
			if(primary.getName().equals(primaryName)){
				primaryUser = primary;
			}
		}
		return primaryUser;
	}
	
	public String[] listPrimaryUsers(String secondaryName){
		SecondaryUser user = findSecondary(secondaryName);
		String[] primaryUsers = new String[user.getPrimaryUsers().size()];
		int i = 0;
		for(PrimaryUser primary : user.getPrimaryUsers()){
			primaryUsers[i++] = primary.getName();
		}
		return primaryUsers;
	}
	
	public String[] authenticateUser(String secondaryName, String password){
		AggregationOperation unwind = Aggregation.unwind("primaryUsers");
		AggregationOperation match = Aggregation.match(Criteria.where("username").is(secondaryName).and("password").is(password));
		AggregationOperation group = Aggregation.group("null").first("systemInformation").as("systemInformation")
															  .push("$primaryUsers.name").as("output_primaryUserNames");

		Aggregation aggregation = Aggregation.newAggregation(unwind,match,group);
		
		List<QueryOutput> result = mongoTemplate.aggregate(aggregation, "Secondary_Users", QueryOutput.class).getMappedResults();
				
		if(result.isEmpty()) return null;
		else{
			userLogin(secondaryName, result.get(0).getSystemInformation());
			return result.get(0).getOutput_primaryUserNames();
		}
	}
	
	public MeasurementSetting getMeasurementsSetting(String secondaryName, String primaryName){
		/*db.Secondary_Users.aggregate([
		{$match:{"username":"test"}},
	    {$unwind:"$primaryUsers"},
	    {$match:{"primaryUsers.name":"Francisco Juliano"}},
	    {$unwind:"$primaryUsers.measurements"},
	    {$project:{"primaryUsers.measurements":1}},
	    {$sort:{"primaryUsers.measurements.date":1}},
	    {$group:{_id:"$primaryUsers.measurements.measurementType.group",
             description:{$first:"$primaryUsers.measurements.measurementType.description"},    
             startDate:{$first:"$primaryUsers.measurements.date"},
             lastDate:{$last:"$primaryUsers.measurements.date"}}}          
		])*/
		
		AggregationOperation match1 = Aggregation.match(Criteria.where("username").is(secondaryName));
		AggregationOperation unwind1 = Aggregation.unwind("primaryUsers");
		AggregationOperation match2 = Aggregation.match(Criteria.where("primaryUsers.name").is(primaryName));
		AggregationOperation unwind2 = Aggregation.unwind("primaryUsers.measurements");
		AggregationOperation sort = Aggregation.sort(Sort.Direction.ASC, "primaryUsers.measurements.measurementType.group");
		AggregationOperation group = Aggregation.group("primaryUsers.measurements.measurementType.group")
      		  								.first("primaryUsers.measurements.measurementType.description").as("description")
											.min("primaryUsers.measurements.date").as("startDate")
											.max("primaryUsers.measurements.date").as("lastDate");

		Aggregation aggregation = Aggregation.newAggregation(match1,unwind1,match2,unwind2,sort,group);
		
		AggregationResults<DBObject> result = mongoTemplate.aggregate(aggregation, "Secondary_Users", DBObject.class);
		
		List<DBObject> settings = result.getMappedResults();
		MeasurementSetting setting = new MeasurementSetting(settings.size());
		int i = 0;
		for(DBObject ob : settings){
			setting.getGroups()[i] = (int) ob.get("_id");
			setting.getDescriptions()[i] = (String) ob.get("description");
			setting.getStartDates()[i] = (long) ob.get("startDate");
			setting.getLastDates()[i++] = (long) ob.get("lastDate");
		}
		
		return setting;
	}
	
	public int[] getMeasurementsByPeriod(String secondaryName, String primaryName, 
																int group, long date, int periodType){		
		/*
		db.Secondary_Users.aggregate([
        {$unwind:"$primaryUsers"},
        {$unwind:"$primaryUsers.measurements"},
        {$match:{"username":"testUser","primaryUsers.name":"Francisco Juliano","primaryUsers.measurements.measurementType.group":3,"primaryUsers.measurements.date":{$gte:1347456156577,$lte:1347628956577}}},
        //{$project:{"primaryUsers.measurements.measurementType.group":1,"primaryUsers.measurements.date":1,"primaryUsers.measurements.value":1}},
        {$sort:{"primaryUsers.measurements.date":1}},
        {$group:{_id:"null",
                 result_measurements:{$push:"$primaryUsers.measurements.value"}}}
		]);
		*/
		
		long endDate = getMaxDate(date, periodType);
		
		AggregationOperation match1 = Aggregation.match(Criteria.where("username").is(secondaryName));
		AggregationOperation unwind1 = Aggregation.unwind("primaryUsers");
		AggregationOperation match2 = Aggregation.match(Criteria.where("primaryUsers.name").is(primaryName));
		AggregationOperation unwind2 = Aggregation.unwind("primaryUsers.measurements");
		AggregationOperation match = Aggregation.match(Criteria.where("primaryUsers.measurements.measurementType.group").is(group)
								.andOperator(Criteria.where("primaryUsers.measurements.date").gte(date),
										     Criteria.where("primaryUsers.measurements.date").lte(endDate)));
		
		AggregationOperation sort = Aggregation.sort(Sort.Direction.ASC, "primaryUsers.measurements.date");
		AggregationOperation group_query = Aggregation.group("null").push("$primaryUsers.measurements.value").as("output_measurements");
				
		Aggregation aggregation = Aggregation.newAggregation(match1,unwind1,match2,unwind2,match,sort,group_query);

		AggregationResults<QueryOutput> result = mongoTemplate.aggregate(aggregation, "Secondary_Users", QueryOutput.class);
		
		return result.getMappedResults().get(0).getOutput_measurements();
	}
	
	public SystemInformation getSystemInformation(String secondaryName){
		AggregationOperation match = Aggregation.match(Criteria.where("username").is(secondaryName));
		AggregationOperation project = Aggregation.project("systemInformation");
		
		Aggregation aggregation = Aggregation.newAggregation(match,project);
		
		AggregationResults<QueryOutput> result = mongoTemplate.aggregate(aggregation, "Secondary_Users", QueryOutput.class);
		
		return result.getMappedResults().get(0).getSystemInformation();
	}
	
	public ArrayList<Alarm> getLastAlarms(String secondaryName, String primaryName, int num){
		/*db.Secondary_Users.aggregate([
	    {$unwind:"$primaryUsers"},
	    {$unwind:"$primaryUsers.alarms"},
	    {$match:{"username":"test","primaryUsers.name":"Francisco Juliano"}},
	    {$project:{"primaryUsers.name":1,"primaryUsers.alarms":1}},
	    {$sort:{"primaryUsers.alarms.date":1}},
	    {$limit:1},
	    {$group:{_id:"null",
	             output_alarms:{$push:"$primaryUsers.alarms"}}}
		])*/
		
		AggregationOperation unwind1 = Aggregation.unwind("primaryUsers");
		AggregationOperation unwind2 = Aggregation.unwind("primaryUsers.alarms");
		AggregationOperation match = Aggregation.match(Criteria.where("username").is(secondaryName)
								.and("primaryUsers.name").is(primaryName));
		AggregationOperation sort = Aggregation.sort(Sort.Direction.ASC, "primaryUsers.alarms.date");
		AggregationOperation limit = Aggregation.limit(num);
		AggregationOperation group = Aggregation.group("null").push("$primaryUsers.alarms").as("output_alarms");
		
		Aggregation aggregation = Aggregation.newAggregation(unwind1,unwind2,match,sort,limit,group);

		AggregationResults<QueryOutput> result = mongoTemplate.aggregate(aggregation, "Secondary_Users", QueryOutput.class);
		
		return result.getMappedResults().get(0).getOutput_alarms();
	}
	
	public void updateInformation(String secondaryName, SystemInformation information){
		Criteria criteria = new Criteria("username");
		criteria.is(secondaryName);		
		Query query = new Query(criteria);
		
		Update update = new Update();
		update.set("systemInformation", information);

		mongoTemplate.updateFirst(query, update, "Secondary_Users");
	}
	
	public void userLogin(String secondaryName, SystemInformation information){
		long date = new java.util.Date().getTime();
		long expiration = date + (2 * 3600000);
		information.setLastLogonDate(date);
		information.setSessionExpirationDate(expiration);
		updateInformation(secondaryName, information);
	}
	
	public void userLogout(String secondaryName){
		SystemInformation information = getSystemInformation(secondaryName);
		long date = new java.util.Date().getTime();
		information.setLastLogoutDate(date);
		
		updateInformation(secondaryName, information);
	}
	
	public void setReminder(String secondaryName, Reminder reminder){
		/*db.Secondary_Users.aggregate([
		{$match:{"username":"test"}},
		{$project:{"reminders":1}},
		{$sort:{"reminders.date":1}},
		])*/
			
		ArrayList<Reminder> reminders = getReminders(secondaryName);
	
		if(reminders == null){
			reminders = new ArrayList<Reminder>();
		}
		
		reminders.add(reminder);
		Criteria criteria = new Criteria("username");
		criteria.is(secondaryName);		
		Query query = new Query(criteria);
		
		Update update = new Update();
		update.set("reminders", reminders);

		mongoTemplate.updateFirst(query, update, "Secondary_Users");
	}
	
	public Reminder getReminder(String secondaryName){
		System.out.println("getReminder call!");
		
		ArrayList<Reminder> reminders = getReminders(secondaryName);
		if(reminders != null) {
			Reminder reminder = reminders.get(0);

			return reminder;
		}else return null;
	}
	
	public void deleteReminder(String secondaryName, Reminder reminder){
		ArrayList<Reminder> reminders = getReminders(secondaryName);
		Reminder r;
		if(reminders != null){
			for(int i = 0; i < reminders.size(); i++){
				r = reminders.get(i);
				if(r.getDate()==reminder.getDate() && r.getDescription().equals(reminder.getDescription())){
					reminders.remove(i);
					break;
				}
			}
			Criteria criteria = new Criteria("username");
			criteria.is(secondaryName);		
			Query query = new Query(criteria);
			
			Update update = new Update();
			update.set("reminders", reminders);

			mongoTemplate.updateFirst(query, update, "Secondary_Users");	
		}	
	}
	
	public ArrayList<Reminder> getReminders(String secondaryName){
		/*db.Secondary_Users.aggregate([
		                      		{$match:{"username":"test"}},
		                            {$unwind:"$reminders"},
		                      		{$project:{"reminders":1}},
		                      		{$sort:{"reminders.date":1}},
		                            {$group:{_id:"null",
		                      	             reminders:{$push:"$reminders"}}}
		                      		])*/
		
		AggregationOperation match = Aggregation.match(Criteria.where("username").is(secondaryName));
		AggregationOperation unwind = Aggregation.unwind("reminders");
		AggregationOperation sort = Aggregation.sort(Sort.Direction.ASC, "reminders.date");
		AggregationOperation group = Aggregation.group("null").push("$reminders").as("reminders");
		Aggregation aggregation = Aggregation.newAggregation(match,unwind,sort,group);

		AggregationResults<QueryOutput> result = mongoTemplate.aggregate(aggregation, "Secondary_Users", QueryOutput.class);
		
		if(!result.getMappedResults().isEmpty()) {
			ArrayList<Reminder> reminders = (ArrayList<Reminder>) result.getMappedResults().get(0).getReminders();
			return reminders;
		}
		
		return null;
		
	}
	
	public long getMaxDate(long date, int periodType){
		long oneDayInMilliSecond = 86400000;
		
		date = date + (oneDayInMilliSecond * periodType);
		return date;
	}
}
