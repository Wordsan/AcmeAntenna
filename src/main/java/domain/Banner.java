package domain;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Access(AccessType.PROPERTY)
public class Banner extends DomainEntity {

    private String pictureUrl;
    private String targetPage;
    private String creditCard;
    private Agent agent;


    public Banner()
    {
        super();
    }

    @NotBlank
    @URL
    public String getPictureUrl()
    {
        return this.pictureUrl;
    }

    public void setPictureUrl(final String pictureUrl)
    {
        this.pictureUrl = pictureUrl;
    }

    @NotBlank
    @URL
    public String getTargetPage()
    {
        return this.targetPage;
    }

    public void setTargetPage(final String targetPage)
    {
        this.targetPage = targetPage;
    }

    @NotBlank
    @CreditCardNumber
    public String getCreditCard()
    {
        return this.creditCard;
    }

    public void setCreditCard(final String creditCard)
    {
        this.creditCard = creditCard;
    }

    @ManyToOne(optional = false)
    public Agent getAgent()
    {
        return this.agent;
    }

    public void setAgent(final Agent agent)
    {
        this.agent = agent;
    }

}
