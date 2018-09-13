package domain;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import repositories.MaintenanceRequestRepository;
import validators.CustomValidator;
import validators.HasCustomValidators;
import validators.NullOrNotBlank;
import validators.PastOrPresent;

@Entity
@Access(AccessType.PROPERTY)
@HasCustomValidators
@Table(indexes = {
        @Index(columnList = "handyworker_id, doneTime"), // HandyworkerRepository.findServedMaintenanceRequest, HandyworkerRepository.findNotServedMaintenanceRequest
        @Index(columnList = "handyworker_id, antenna_id, doneTime"), // MaintenanceRequestRepository.findPendingRequests
})
public class MaintenanceRequest extends DomainEntity {

    private User user;
    private Handyworker handyworker;
    private CreditCard creditCard;
    private Date creationTime;
    private String description;
    private Antenna antenna;
    private Date doneTime;
    private String resultsDescription;

    @NotNull
    @ManyToOne(optional = false)
    public User getUser()
    {
        return this.user;
    }

    public void setUser(final User user)
    {
        this.user = user;
    }

    @NotNull
    @ManyToOne(optional = false)
    public Handyworker getHandyworker()
    {
        return this.handyworker;
    }

    public void setHandyworker(final Handyworker handyworker)
    {
        this.handyworker = handyworker;
    }

    @Valid
    @NotNull
    @Embedded
    public CreditCard getCreditCard()
    {
        return this.creditCard;
    }

    public void setCreditCard(final CreditCard creditCard)
    {
        this.creditCard = creditCard;
    }

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @PastOrPresent
    public Date getCreationTime()
    {
        return this.creationTime;
    }

    public void setCreationTime(final Date creationTime)
    {
        this.creationTime = creationTime;
    }

    @NotBlank
    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(final String description)
    {
        this.description = description;
    }

    @NotNull
    @ManyToOne(optional = false)
    public Antenna getAntenna()
    {
        return this.antenna;
    }

    public void setAntenna(final Antenna antenna)
    {
        this.antenna = antenna;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date getDoneTime()
    {
        return this.doneTime;
    }

    public void setDoneTime(final Date doneTime)
    {
        this.doneTime = doneTime;
    }

    @NullOrNotBlank
    public String getResultsDescription()
    {
        return this.resultsDescription;
    }
    public void setResultsDescription(final String resultsDescription)
    {
        this.resultsDescription = resultsDescription;
    }

    @Transient
    @CustomValidator(applyOn = "resultsDescription", message = "{org.hibernate.validator.constraints.NotBlank.message}")
    public boolean isValid()
    {
        if (getDoneTime() != null) {
            if (getResultsDescription() == null) return false;
        }

        return true;
    }
}
