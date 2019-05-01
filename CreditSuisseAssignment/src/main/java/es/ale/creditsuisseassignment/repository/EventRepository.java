package es.ale.creditsuisseassignment.repository;

import org.springframework.data.repository.CrudRepository;

import es.ale.creditsuisseassignment.model.Event;

public interface EventRepository extends CrudRepository<Event, Long> {
	
	Event findById(String id);
}

