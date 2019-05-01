package es.ale.creditsuisseassignment.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import es.ale.creditsuisseassignment.controller.JSONProcessor;
import es.ale.creditsuisseassignment.model.Event;
import es.ale.creditsuisseassignment.repository.EventRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;
	/** Logger of the app **/
	private static final Logger log = LoggerFactory.getLogger(JSONProcessor.class);

	
	
	/**
	 * Insert (when is the first occurrence of the event) or update (when is the second occurrence of the event, calculating the duration and alert)
	 * We do it multithreading by annotating the method with Async 
	 * @param event
	 * @return
	 */
    @Async
	public CompletableFuture<Event> addEvent(Event event) {
    	
    	Event resultEvent;
    	// If an event with the same ID doesn't exist, we synchronize the get and save in order to avoid race conditions
    	synchronized (this) {
	    	// Check if the event exists
	    	resultEvent = eventRepository.findById(event.getId());
	    	if (resultEvent == null) {
		    	resultEvent = eventRepository.save(event);
				log.debug("New Event --> "+event.toString());
		    	return CompletableFuture.completedFuture(resultEvent);
	    	}
    	}
    	
    	// If an event with the same ID exists, we do the calculations of duration and alert
		// Setting the duration
		event.setTimestamp(Math.abs(event.getTimestamp() - resultEvent.getTimestamp()));
		log.debug("Event completed (we have found both the started and finished events) with a duration of "+event.getTimestamp());
		
		// Duration > 4 --> Alert = true. Otherwise --> Alert = false 
		if (event.getTimestamp() > 4) {
			event.setAlert(true);
		}
		else {
			event.setAlert(false);
		}
		
		// Updating the event
		resultEvent = eventRepository.save(event);

		return CompletableFuture.completedFuture(resultEvent);
	}

    
	public List<Event> getEvent() {
		return (List<Event>) eventRepository.findAll();
	}

}
