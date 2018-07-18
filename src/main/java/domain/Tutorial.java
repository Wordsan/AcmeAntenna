package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import cz.jirutka.validator.collection.constraints.EachNotBlank;
import cz.jirutka.validator.collection.constraints.EachNotNull;
import cz.jirutka.validator.collection.constraints.EachURL;
import validators.PastWithMargin;

@Entity
@Access(AccessType.PROPERTY)
public class Tutorial
extends DomainEntity {
    private Date lastUpdateTime = new Date();
    private String title;
    private String text;
    private List<String> pictureUrls = new ArrayList<>();

    @NotNull
    @PastWithMargin
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date getLastUpdateTime()
    {
        return lastUpdateTime;
    }

    @NotBlank
    public String getTitle()
    {
        return title;
    }

    @NotBlank
    public String getText()
    {
        return text;
    }

    @NotNull
    @EachNotNull
    @EachNotBlank
    @EachURL
    @ElementCollection
    public List<String> getPictureUrls()
    {
        return pictureUrls;
    }

    public void setLastUpdateTime(Date lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void setPictureUrls(List<String> pictureUrls)
    {
        this.pictureUrls = pictureUrls;
    }
}
