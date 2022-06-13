package com.microsoft.teams.app;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.microsoft.bot.builder.TurnContext;
import com.microsoft.teams.app.entity.Ticket_296;

@Component
public class CommonUtility {
	
	
	public void removeContextData(ConcurrentHashMap<String, Ticket_296> ticket,TurnContext turnContext) {
		
		 Ticket_296 tkt = ticket.get(turnContext.getActivity().getFrom().getId());
		 if(tkt!=null) {
			 ticket.remove(turnContext.getActivity().getFrom().getId());
		 }
	}

}
