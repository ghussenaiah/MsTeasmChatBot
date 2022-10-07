// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.teams.app;

import com.microsoft.bot.builder.Bot;
import com.microsoft.bot.integration.AdapterWithErrorHandler;
import com.microsoft.bot.integration.BotFrameworkHttpAdapter;
import com.microsoft.bot.integration.Configuration;
import com.microsoft.bot.integration.spring.BotController;
import com.microsoft.bot.integration.spring.BotDependencyConfiguration;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import javax.websocket.server.ServerEndpointConfig.Configurator;

import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication

// Use the default BotController to receive incoming Channel messages. A custom
// controller could be used by eliminating this import and creating a new
// org.springframework.web.bind.annotation.RestController.
// The default controller is created by the Spring Boot container using
// dependency injection. The default route is /api/messages.
@Import({ BotController.class })

@ComponentScan(basePackages = "com.microsoft.teams.app")
@EnableScheduling
@EnableTransactionManagement
@Slf4j
public class Application extends BotDependencyConfiguration {

    public static void main(String[] args) throws IOException {
    	
    	//Logger logger = (Logger) LogManager.getLogger("com.bcv.kagami");
    		//	Configurator.setLevel(logger.getName(), Level.ERROR);
        SpringApplication.run(Application.class, args);
       // System.out.println("Application started");
	
        
        //String killCommand = String.format("kill -9 %s", processId);
        String runbccommad1 = String.format("disown -h %d",1);
        String runbccommad2 = String.format("disown -h %d",2);
		
		try {
			Process l = Runtime.getRuntime().exec(runbccommad1);
			log.debug("making as backend process for app jobs 1 .. {} with pid : {}", l.toString());
			log.info("making as backend process for app jobs 1 .. {} with pid : {}", l.toString());
			
		} catch (IOException e) {

		}

		try {
			Process l = Runtime.getRuntime().exec(runbccommad2);
			log.info("making as backend process for app jobs 2.. {} with pid : {}", l.toString());
		} catch (IOException e) {

		}
		
        
        
        
        
  //  	String command=String.format("disown -h %1");

		//logger.info("-----------------------------executing jar : {}", command);
	//	 Runtime.getRuntime().exec(command);
		//System.out.println("Command Executed");
    }

    /**
     * Returns the Bot for this application.
     *
     * <p>
     * The @Component annotation could be used on the Bot class instead of this
     * method
     * with the @Bean annotation.
     * </p>
     *
     * @return The Bot implementation for this application.
     */
    @Bean
    public Bot getBot() {
        return new EchoBot();
    }

    /**
     * Returns a custom Adapter that provides error handling.
     *
     * @param configuration The Configuration object to use.
     * @return An error handling BotFrameworkHttpAdapter.
     */
    @Override
    public BotFrameworkHttpAdapter getBotFrameworkHttpAdaptor(Configuration configuration) {
        return new AdapterWithErrorHandler(configuration);
    }
}
