package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import domain.User;
import security.Authority;
import security.UserAccount;
import validators.CustomValidator;
import validators.HasCustomValidators;
import validators.UseConstraintsFrom;

@Access(AccessType.PROPERTY)
@HasCustomValidators
public class NewUserForm {
    private User user = new User();
    private String repeatPassword = "";
    public boolean agreesToTerms;

    public NewUserForm()
    {
        user.setUserAccount(new UserAccount("", "", Authority.USER));
    }

    @Valid
    @NotNull
    public User getUser()
    {
        return user;
    }
    public void setUser(User user)
    {
        this.user = user;
    }

    @CustomValidator(message = "{misc.error.passwordDoesNotMatch}", applyOn = "repeatPassword")
    public boolean isValidPasswordsMatch()
    {
        if (user == null || user.getUserAccount() == null || getRepeatPassword() == null) return false;
        return getRepeatPassword().equals(user.getUserAccount().getPassword());
    }

    @UseConstraintsFrom(klazz = UserAccount.class, property = "password")
    public String getRepeatPassword()
    {
        return repeatPassword;
    }
    public void setRepeatPassword(String repeatPassword)
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
