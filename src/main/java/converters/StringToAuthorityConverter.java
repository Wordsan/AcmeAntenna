package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import domain.Actor;
import security.Authority;

@Component
@Transactional
public class StringToAuthorityConverter
implements Converter<String, Authority> {
	@Override
	public Authority convert(String string)
	{
		Authority result;

		if (string == null) {
			result = null;
		} else {
			result = new Authority();
			result.setAuthority(string);
		}

		return result;
	}
}