package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import domain.Handyworker;
import security.Authority;
import security.UserAccount;
import validators.CustomValidator;
import validators.HasCustomValidators;
import validators.UseConstraintsFrom;

@Access(AccessType.PROPERTY)
@HasCustomValidators
public class NewHandyworkerForm {

    private Handyworker handyworker = new Handyworker();
    private String repeatPassword = "";
    private boolean agreesToTerms;

    public NewHandyworkerForm()
    {
        this.handyworker.setUserAccount(new UserAccount("", "", Authority.HANDYWORKER));
    }

    @NotNull
    public Handyworker getHandyworker()
    {
        return this.handyworker;
    }

    public void setHandyworker(final Handyworker handyworker)
    {
        this.handyworker = handyworker;
    }

    @CustomValidator(message = "{misc.error.passwordDoesNotMatch}", applyOn = "repeatPassword")
    public boolean isValidPasswordsMatch()
    {
        if (handyworker == null || handyworker.getUserAccount() == null) return false;
        if (getRepeatPassword() == null) return handyworker.getUserAccount().getPassword() == null;
        return getRepeatPassword().equals(handyworker.getUserAccount().getPassword());
    }

    @UseConstraintsFrom(klazz = UserAccount.class, property = "password")
    public String getRepeatPassword()
    {
        return this.repeatPassword;
    }

    public void setRepeatPassword(final String repeatPassword)
    {
        this.repeatPassword = repeatPassword;
    }

    @AssertTrue(message = "{users.mustAgreeToTerms}")
    public boolean getAgreesToTerms()
    {
        return agreesToTerms;
    }

    public void setAgreesToTerms(boolean agreesToTerms)
    {
        this.agreesToTerms = agreesToTerms;
    }
}
