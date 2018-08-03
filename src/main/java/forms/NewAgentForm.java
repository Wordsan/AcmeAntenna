
package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import security.Authority;
import security.UserAccount;
import validators.CustomValidator;
import validators.HasCustomValidators;
import validators.UseConstraintsFrom;
import domain.Agent;

@Access(AccessType.PROPERTY)
@HasCustomValidators
public class NewAgentForm {

	private Agent	agent			= new Agent();
	private String	repeatPassword	= "";


	public NewAgentForm() {
		this.agent.setUserAccount(new UserAccount("", "", Authority.AGENT));
	}

	@Valid
	@NotNull
	public Agent getAgent() {
		return this.agent;
	}
	public void setAgent(final Agent agent) {
		this.agent = agent;
	}

	@CustomValidator(message = "{misc.error.passwordDoesNotMatch}", applyOn = "repeatPassword")
	public boolean isValidPasswordsMatch() {
		if (this.agent == null || this.agent.getUserAccount() == null || this.getRepeatPassword() == null)
			return false;
		return this.getRepeatPassword().equals(this.agent.getUserAccount().getPassword());
	}

	@UseConstraintsFrom(klazz = UserAccount.class, property = "password")
	public String getRepeatPassword() {
		return this.repeatPassword;
	}
	public void setRepeatPassword(final String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
}
