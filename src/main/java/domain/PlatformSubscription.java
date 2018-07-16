package domain;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Access(AccessType.PROPERTY)
public class PlatformSubscription
extends DomainEntity {
    private User user;
    private Platform platform;
    private Date startDate;
    private Date endDate;
    private String creditCard;
    private String keyCode;

    @Valid
    @ManyToOne(optional = false)
    public User getUser()
    {
        return user;
    }

    @Valid
    @ManyToOne(optional = false)
    public Platform getPlatform()
    {
        return platform;
    }

    @NotNull
    public Date getStartDate()
    {
        return startDate;
    }

    @NotNull
    public Date getEndDate()
    {
        return endDate;
    }

    @NotNull
    @NotBlank
    @CreditCardNumber
    public String getCreditCard()
    {
        return creditCard;
    }

    @NotNull
    @NotBlank
    @Size(min = 32, max = 32)
    public String getKeyCode()
    {
        return keyCode;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setPlatform(Platform platform)
    {
        this.platform = platform;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public void setCreditCard(String creditCard)
    {
        this.creditCard = creditCard;
    }

    public void setKeyCode(String keyCode)
    {
        this.keyCode = keyCode;
    }
}
