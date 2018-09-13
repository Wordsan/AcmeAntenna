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
public class Actor
        extends DomainEntity
{
    private String name;
    private String surname;
    private String email;
    private String pictureUrl;
    private String phoneNumber;
    private String postalAddress;
    private UserAccount userAccount;

    @NotBlank
    public String getName() { return name; }

    @NotBlank
    public String getSurname() { return surname; }

    @Transient
    public String getFullName()
    {
        if (getName() == null || getSurname() == null) return null;
        return getName().trim() + " " + getSurname().trim();
    }

    @NotBlank
    @Email
    public String getEmail() { return email; }

    @Pattern(regexp = "^\\+?\\d+$")
    @NullOrNotBlank
    public String getPhoneNumber() { return phoneNumber; }

    @URL
    @NullOrNotBlank
    public String getPictureUrl() { return pictureUrl; }

    @NullOrNotBlank
    public String getPostalAddress() { return postalAddress; }

    @OneToOne(optional = false)
    @NotNull // Do not delete, this is NOT useless! This gives us a nice validation error instead of a MySQL constraint violation exception.
    @Cascade(CascadeType.DELETE)
    public UserAccount getUserAccount()
    {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount)
    {
        this.userAccount = userAccount;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPictureUrl(String pictureUrl)
    {
        this.pictureUrl = pictureUrl;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public void setPostalAddress(String postalAddress)
    {
        this.postalAddress = postalAddress;
    }

}
