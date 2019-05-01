package es.ale.creditsuisseassgnment.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import es.ale.creditsuisseassignment.controller.JSONProcessor;
import es.ale.creditsuisseassignment.model.Event;
import es.ale.creditsuisseassignment.service.EventService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JSONProcessor.class})
@ComponentScan("es.ale.creditsuisseassignment")
@DataJpaTest
public class AssignmentTest {
	
	@Autowired
	EventService eventService;
		

	@Test
	public void eventInsertTest() throws Exception {
		Event event = new Event();
		event.setId("AAA");
		eventService.addEvent(event);
		List<Event> result = eventService.getEvent();
		assertTrue(result.get(0).getId() == event.getId());
	}
	
	@Test
	public void eventGetTest() throws Exception {
		Event event = new Event();
		event.setId("BBB");
		eventService.addEvent(event);
		
		event = new Event();
		event.setId("CCC");
		eventService.addEvent(event);

		List<Event> result = eventService.getEvent();
		assertTrue(result.get(0).getId() == "BBB");
		assertTrue(result.get(1).getId() == "CCC");
	}


	@Test
	public void eventGetDuration() throws Exception {
		Event event = new Event();
		event.setId("BBB");
		event.setTimestamp(5L);
		eventService.addEvent(event);
		
		event = new Event();
		event.setId("BBB");
		event.setTimestamp(1L);
		eventService.addEvent(event);

		List<Event> result = eventService.getEvent();
		assertTrue(result.get(0).getTimestamp() == 4L);
	}

	@Test
	public void eventGetAlert() throws Exception {
		Event event = new Event();
		event.setId("BBB");
		event.setTimestamp(5L);
		eventService.addEvent(event);
		
		event = new Event();
		event.setId("BBB");
		event.setTimestamp(0L);
		eventService.addEvent(event);

		List<Event> result = eventService.getEvent();
		assertTrue(result.get(0).getAlert());
	}

}
