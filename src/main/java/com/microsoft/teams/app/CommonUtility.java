package com.microsoft.teams.app;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;


import org.springframework.stereotype.Component;

import com.microsoft.bot.builder.TurnContext;
import com.microsoft.teams.app.entity.Ticket_296;

// cEu8Q~u90O9y64cPUfVrtbocOqjmh3QHDTASJcFX  value client secrets
// f12a8447-9713-43e4-84a2-66c8873fd8ba
// Applicaion Id 685bdac5-8cc2-4cc9-87a5-ee7d8914ac2c

@Component
public class CommonUtility {
	
	/*
	 * @Autowired AuthenticationService authService;
	 */
	
	
	public void removeContextData(ConcurrentHashMap<String, Ticket_296> ticket,TurnContext turnContext) {
		
		 Ticket_296 tkt = ticket.get(turnContext.getActivity().getFrom().getId());
		 if(tkt!=null) {
			 ticket.remove(turnContext.getActivity().getFrom().getId());
		 }
	}
	
	
	public String CalculateDateDifference(Date start, Date end) {

		long difference_In_Time = end.getTime() - start.getTime();

		StringBuilder date = new StringBuilder();

		// Calucalte time difference in
		// seconds, minutes, hours, years,
		// and days
		long difference_In_Seconds = (difference_In_Time / 1000) % 60;

		long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;

		long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;

		long difference_In_Years = (difference_In_Time / (1000l * 60 * 60 * 24 * 365));

		long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;

		// Print the date difference in
		// years, in days, in hours, in
		// minutes, and in seconds

		if (difference_In_Years > 0) {
			date.append(difference_In_Years).append(" years");
		} else if (difference_In_Days > 0) {
			date.append(difference_In_Days).append(" days");
		} else if (difference_In_Hours > 0) {
			date.append(difference_In_Hours).append(" hours");
		} else if (difference_In_Minutes > 0) {
			date.append(difference_In_Hours).append(" minutes");
		} else if (difference_In_Seconds > 0) {
			date.append(difference_In_Seconds).append(" seconds");
		}

		System.out.print("Difference " + "between two dates is: ");

		System.out.println(difference_In_Years + " years, " + difference_In_Days + " days, " + difference_In_Hours
				+ " hours, " + difference_In_Minutes + " minutes, " + difference_In_Seconds + " seconds");
		
		return date.toString();
	}
	
	

}
