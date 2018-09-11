package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import domain.Agent;
import security.Authority;
import security.UserAccount;
import validators.CustomValidator;
import validators.HasCustomValidators;
import validators.UseConstraintsFrom;

@Access(AccessType.PROPERTY)
@HasCustomValidators
public class NewAgentForm {

    private Agent agent = new Agent();
    private String repeatPassword = "";
    private boolean agreesToTerms;

    public NewAgentForm()
    {
        this.agent.setUserAccount(new UserAccount("", "", Authority.AGENT));
    }

    @NotNull
    public Agent getAgent()
    {
        return this.agent;
    }

    public void setAgent(final Agent agent)
    {
        this.agent = agent;
    }

    @CustomValidator(message = "{misc.error.passwordDoesNotMatch}", applyOn = "repeatPassword")
    public boolean isValidPasswordsMatch()
    {
        if (agent == null || agent.getUserAccount() == null) return false;
        if (getRepeatPassword() == null) return agent.getUserAccount().getPassword() == null;
        return getRepeatPassword().equals(agent.getUserAccount().getPassword());
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
