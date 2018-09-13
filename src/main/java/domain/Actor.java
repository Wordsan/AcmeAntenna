package domain;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import security.UserAccount;
import validators.NullOrNotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {

    private String name;
    private String surname;
    private String email;
    private String pictureUrl;
    private String phoneNumber;
    private String postalAddress;
    private UserAccount userAccount;
    private boolean banned;


    @NotBlank
    public String getName()
    {
        return this.name;
    }

    @NotBlank
    public String getSurname()
    {
        return this.surname;
    }

    @Transient
    public String getFullName()
    {
        if (this.getName() == null || this.getSurname() == null) {
            return null;
        }
        return this.getName().trim() + " " + this.getSurname().trim();
    }

    @NotBlank
    @Email
    public String getEmail()
    {
        return this.email;
    }

    @Pattern(regexp = "^\\+?\\d+$")
    @NullOrNotBlank
    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }

    @URL
    @NullOrNotBlank
    public String getPictureUrl()
    {
        return this.pictureUrl;
    }

    @NullOrNotBlank
    public String getPostalAddress()
    {
        return this.postalAddress;
    }

    @OneToOne(optional = false)
    @NotNull // Do not delete, this is NOT useless! This gives us a nice validation error instead of a MySQL constraint violation exception.
    @Cascade(CascadeType.DELETE)
    public UserAccount getUserAccount()
    {
        return this.userAccount;
    }

    public void setUserAccount(final UserAccount userAccount)
    {
        this.userAccount = userAccount;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public void setSurname(final String surname)
    {
        this.surname = surname;
    }

    public void setEmail(final String email)
    {
        this.email = email;
    }

    public void setPictureUrl(final String pictureUrl)
    {
        this.pictureUrl = pictureUrl;
    }

    public void setPhoneNumber(final String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public void setPostalAddress(final String postalAddress)
    {
        this.postalAddress = postalAddress;
    }

    public boolean getBanned()
    {
        return this.banned;
    }

    public void setBanned(final boolean banned)
    {
        this.banned = banned;
    }
}
