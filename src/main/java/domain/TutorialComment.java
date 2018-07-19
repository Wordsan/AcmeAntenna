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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import cz.jirutka.validator.collection.constraints.EachNotBlank;
import cz.jirutka.validator.collection.constraints.EachNotNull;
import cz.jirutka.validator.collection.constraints.EachURL;
import validators.PastWithMargin;

@Entity
@Access(AccessType.PROPERTY)
public class TutorialComment
extends DomainEntity {
    private User user;
    private Tutorial tutorial;
    private String title;
    private String text;
    private Date creationTime;
    private List<String> pictureUrls = new ArrayList<>();

    @Valid
    @ManyToOne(optional = false)
    @NotNull // Do not delete, this is NOT useless! This gives us a nice validation error instead of a MySQL constraint violation exception.
    public User getUser()
    {
        return user;
    }

    @Valid
    @ManyToOne(optional = false)
    @NotNull // Do not delete, this is NOT useless! This gives us a nice validation error instead of a MySQL constraint violation exception.
    public Tutorial getTutorial()
    {
        return tutorial;
    }

    @NotBlank
    public String getTitle()
    {
        return title;
    }

    @NotBlank
    @Lob
    public String getText()
    {
        return text;
    }

    @NotNull
    @PastWithMargin
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public Date getCreationTime()
    {
        return creationTime;
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

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setTutorial(Tutorial tutorial)
    {
        this.tutorial = tutorial;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void setCreationTime(Date creationTime)
    {
        this.creationTime = creationTime;
    }

    public void setPictureUrls(List<String> pictureUrls)
    {
        this.pictureUrls = pictureUrls;
    }
}
