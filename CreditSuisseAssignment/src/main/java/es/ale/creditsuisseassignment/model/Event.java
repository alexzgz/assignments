package es.ale.creditsuisseassignment.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "event")
public class Event {


	/**
	 * Attributes
	 */

	@Id
	private String id;
	private Long timestamp;
	private String type;
	private String host;
	private Boolean alert;
	
	
	
	/**
	 * Getters and setters
	 */

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}

	public Boolean getAlert() {
		return alert;
	}
	public void setAlert(Boolean alert) {
		this.alert = alert;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", timestamp=" + timestamp + ", type=" + type + ", host=" + host + ", alert=" + alert
				+ "]";
	}
	
}
