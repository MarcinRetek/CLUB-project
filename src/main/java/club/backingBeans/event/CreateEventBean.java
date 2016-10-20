package club.backingBeans.event;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import club.DAO.event.Event;
import club.DAO.user.User;
import club.EJB.EventEJB;
import club.EJB.interfaces.LocalEvent;
import club.EJB.interfaces.LocalNews;
import club.backingBeans.BasicFrontendBean;
import club.backingBeans.user.LoginUserBean;

@Named(value="createEventBean")
@RequestScoped
public class CreateEventBean extends EventBean{
		
	@Inject @Named("loginUserBean")
	private LoginUserBean loginUserBean;
		
	public String submit() {
		return super.createAndRedirect();
	}
	
	@PostConstruct
	public void init() {
		System.out.println("In CreateEventBean init()");
	}
	
	@Override
	public Event getFromFields() {
		
		Event event = new Event();
		event.setAuthor(super.getAuthor());
		event.setTitle(super.getTitle());
		event.setText(super.getText());
		event.setStartTime(convertStartTimeToTimestamp());
		event.setDurationInMinutes(super.getDurationInMinutes());
		event.setCreated(Timestamp.from(Instant.now()));
		event.setAttendees(new ArrayList<User>());
		return event;
	}
	
	@Override
	public Event updateFromFields() {
		
		Event eventToUpdate = (Event)super.getById(getId());
		eventToUpdate.setTitle(super.getTitle());
		eventToUpdate.setText(super.getText());
		eventToUpdate.setDurationInMinutes(super.getDurationInMinutes());
		return eventToUpdate;
	}	
	
	private Timestamp convertStartTimeToTimestamp() {
		Instant startTimeAsInstant = super.getStartTime().toInstant();
		return Timestamp.from(startTimeAsInstant);
	}

}
