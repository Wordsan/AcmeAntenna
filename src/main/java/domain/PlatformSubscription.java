package domain;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
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

import validators.CustomValidator;
import validators.HasCustomValidators;

@Entity
@Access(AccessType.PROPERTY)
@HasCustomValidators
@Table(indexes = {
        @Index(columnList = "user_id, platform_id, startDate"), // PlatformSubscriptionRepository.findAllByUserAndPlatformOrderByStartDateDesc, PlatformSubscriptionRepository.findOverlapping
        @Index(columnList = "user_id, startDate") // PlatformSubscriptionRepository.findAllByUserOrderByStartDateDesc
})
public class PlatformSubscription
        extends DomainEntity
{
    public static final String KEYCODE_ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final int KEYCODE_LENGTH = 32;

    private User user;
    private Platform platform;
    private Date startDate;
    private Date endDate;
    private String creditCard;
    private String keyCode;

    @ManyToOne(optional = false)
    @NotNull // Do not delete, this is NOT useless! This gives us a nice validation error instead of a MySQL constraint violation exception.
    public User getUser()
    {
        return user;
    }

    @ManyToOne(optional = false)
    @NotNull // Do not delete, this is NOT useless! This gives us a nice validation error instead of a MySQL constraint violation exception.
    public Platform getPlatform()
    {
        return platform;
    }

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    public Date getStartDate()
    {
        return startDate;
    }

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    public Date getEndDate()
    {
        return endDate;
    }

    @CustomValidator(message = "{platform_subscriptions.error.startDateMustBeBeforeEndDate}", applyOn = {"startDate", "endDate"})
    @Transient
    public boolean isValidStartDateBeforeEndDate()
    {
        if (startDate == null || endDate == null) return true;
        Date tmpStart = DateUtils.truncate(startDate, Calendar.DAY_OF_MONTH);
        Date tmpEnd = DateUtils.truncate(endDate, Calendar.DAY_OF_MONTH);
        return tmpStart.before(tmpEnd) || tmpStart.equals(tmpEnd);
    }


    @NotBlank
    @CreditCardNumber
    @Pattern(regexp = "^[0-9 ]+$", message = "{org.hibernate.validator.constraints.CreditCardNumber.message}")
    public String getCreditCard()
    {
        return creditCard;
    }

    @Transient
    public String getObscuredCreditCard()
    {
        String tmp = creditCard.replaceAll("[^0-9]", "");
        if (tmp.length() > 4) {
            return StringUtils.repeat("*", tmp.length() - 4) + tmp.substring(tmp.length() - 4);
        } else {
            return StringUtils.repeat("*", tmp.length());
        }
    }

    @NotBlank
    @Pattern(regexp = "[0-9A-Z]{32}")
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
