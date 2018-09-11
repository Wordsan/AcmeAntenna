package domain;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import cz.jirutka.validator.collection.constraints.EachNotBlank;
import cz.jirutka.validator.collection.constraints.EachNotNull;
import cz.jirutka.validator.collection.constraints.EachURL;
import validators.PastOrPresent;

@Entity
@Access(AccessType.PROPERTY)
public class Tutorial
        extends DomainEntity
{
    private User user;
    private Date lastUpdateTime;
    private String title;
    private String text;
    private List<String> pictureUrls = new ArrayList<>();
    private List<TutorialComment> tutorialComments = new ArrayList<>();

    @ManyToOne(optional = false)
    @NotNull // Do not delete, this is NOT useless! This gives us a nice validation error instead of a MySQL constraint violation exception.
    public User getUser()
    {
        return user;
    }

    @NotNull
    @PastOrPresent
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
    @Lob
    public String getText()
    {
        return text;
    }

    @Valid
    @NotNull
    @EachNotNull
    @EachNotBlank
    @EachURL
    @ElementCollection
    public List<String> getPictureUrls()
    {
        return pictureUrls;
    }

    @NotNull
    @OneToMany(mappedBy = "tutorial")
    @Cascade(CascadeType.DELETE)
    public List<TutorialComment> getTutorialComments()
    {
        return tutorialComments;
    }

    public void setUser(User user)
    {
        this.user = user;
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
        if (pictureUrls == null) pictureUrls = new ArrayList<>();
        this.pictureUrls = pictureUrls;
    }

    public void setTutorialComments(List<TutorialComment> tutorialComments)
    {
        if (tutorialComments == null) tutorialComments = new ArrayList<>();
        this.tutorialComments = tutorialComments;
    }
}
