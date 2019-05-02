package es.ale.creditsuisseassignment.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.ale.creditsuisseassignment.Application;
import es.ale.creditsuisseassignment.model.Event;
import es.ale.creditsuisseassignment.service.EventService;

@Component
public class JSONProcessor {

	/**
	 * Attributes
	 */
	
	@Autowired
	private EventService eventService;
	
	/** Logger of the app **/
	private static final Logger log = LoggerFactory.getLogger(JSONProcessor.class);

	
	/**
	 * Method that process the json file
	 */
	public void process(String jsonfilepath) {
		
		List<CompletableFuture<Event>> listInsertedEvents = new ArrayList<CompletableFuture<Event>>();
	    try {
	    	FileInputStream stream = new FileInputStream(new File(jsonfilepath));
	        Scanner sc = new Scanner(stream, "UTF-8");
	        Gson gson = new GsonBuilder().create();
	        
	        while (sc.hasNextLine()) {
	            String line = sc.nextLine();
		        Event event = gson.fromJson(line, Event.class);
		        log.debug("Event read ==> "+event.toString());
				CompletableFuture<Event> resultEvent = eventService.addEvent(event);
				listInsertedEvents.add(resultEvent);
				
				if (listInsertedEvents.size() == 20) {
					CompletableFuture.allOf(listInsertedEvents.toArray(new CompletableFuture[0]))
			        // avoid throwing an exception in the join() call
			        .exceptionally(ex -> null)
			        .join();
					
					listInsertedEvents.clear();
				}
	        }

	        sc.close();
	        
	        // Just in case there was an exception, because Scanner suppresses exceptions
	        if (sc.ioException() != null) {
	            throw sc.ioException();
	        }
	    } catch (UnsupportedEncodingException ex) {
	    } catch (IOException ex) {
	    }
	    
	    // We wait until every "futured" task finishes
		CompletableFuture.allOf(listInsertedEvents.toArray(new CompletableFuture[0]))
        // avoid throwing an exception in the join() call
        .exceptionally(ex -> null)
        .join();

	    
		// Printing the final result in the log, if needed
		List<Event> listado = eventService.getEvent();
		listado.forEach((event) -> {log.debug(event.toString());});

		
		
	}
	
	
	
	
	
	
	
}
