package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;

import security.UserAccount;
import validators.CustomValidator;
import validators.HasCustomValidators;
import validators.UseConstraintsFrom;

@Access(AccessType.PROPERTY)
@HasCustomValidators
public class EditOwnPasswordForm {
    private String oldPassword;
    private String newPassword;
    private String repeatNewPassword;

    @CustomValidator(message = "{misc.error.passwordDoesNotMatch}", applyOn = "repeatPassword")
    public boolean isValidPasswordsMatch()
    {
        if (getNewPassword() == null || getRepeatNewPassword() == null) return true;
        return getRepeatNewPassword().equals(getNewPassword());
    }

    @UseConstraintsFrom(klazz = UserAccount.class, property = "password")
    public String getOldPassword()
    {
        return oldPassword;
    }

    @UseConstraintsFrom(klazz = UserAccount.class, property = "password")
    public String getNewPassword()
    {
        return newPassword;
    }

    @UseConstraintsFrom(klazz = UserAccount.class, property = "password")
    public String getRepeatNewPassword()
    {
        return repeatNewPassword;
    }

    public void setOldPassword(String oldPassword)
    {
        this.oldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword)
    {
        this.newPassword = newPassword;
    }

    public void setRepeatNewPassword(String repeatNewPassword)
    {
        this.repeatNewPassword = repeatNewPassword;
    }
}
