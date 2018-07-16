package domain;

import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import cz.jirutka.validator.collection.constraints.EachNotBlank;
import cz.jirutka.validator.collection.constraints.EachNotEmpty;
import cz.jirutka.validator.collection.constraints.EachNotNull;
import cz.jirutka.validator.collection.constraints.EachURL;
import validators.PastWithMargin;

@Entity
@Access(AccessType.PROPERTY)
public class TutorialComment
extends DomainEntity {
    private Tutorial tutorial;
    private String title = "";
    private String text = "";
    private Date creationTime = new Date();
    private List<String> pictureUrls = new ArrayList<>();

    @Valid
    @NotNull
    @ManyToOne(optional = false)
    public Tutorial getTutorial()
    {
        return tutorial;
    }

    @NotNull
    @NotBlank
    public String getTitle()
    {
        return title;
    }

    @NotNull
    @NotBlank
    public String getText()
    {
        return text;
    }

    @NotNull
    @PastWithMargin
    public Date getCreationTime()
    {
        return creationTime;
    }

    @EachNotEmpty
    @EachURL
    @ElementCollection
    public List<String> getPictureUrls()
    {
        return pictureUrls;
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
