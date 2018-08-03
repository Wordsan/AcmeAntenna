
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class MaintenanceRequest extends DomainEntity {

	private User		user;
	private Handyworker	handyworker;
	private String		creditCard;
	private Date		creationTime;
	private String		description;
	private Antenna		antenna;
	private Date		doneTime;
	private String		resultsDescription;


	@ManyToOne(optional = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@ManyToOne(optional = false)
	public Handyworker getHandyworker() {
		return this.handyworker;
	}

	public void setHandyworker(final Handyworker handyworker) {
		this.handyworker = handyworker;
	}

	//@NotBlank
	@CreditCardNumber
	public String getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final String creditCard) {
		this.creditCard = creditCard;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getCreationTime() {
		return this.creationTime;
	}

	public void setCreationTime(final Date creationTime) {
		this.creationTime = creationTime;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Antenna getAntenna() {
		return this.antenna;
	}

	public void setAntenna(final Antenna antenna) {
		this.antenna = antenna;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getDoneTime() {
		return this.doneTime;
	}

	public void setDoneTime(final Date doneTime) {
		this.doneTime = doneTime;
	}

	public String getResultsDescription() {
		return this.resultsDescription;
	}

	public void setResultsDescription(final String resultsDescription) {
		this.resultsDescription = resultsDescription;
	}

}
