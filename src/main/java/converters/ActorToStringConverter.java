package converters;

import org.springframework.core.convert.converter.Converter;
import javax.transaction.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Component;

import domain.Actor;

@Component
@Transactional
public class ActorToStringConverter
implements Converter<Actor, String> {
	@Override
	public String convert(Actor actor)
	{
		String result;

		if (actor == null) {
			result = null;
		} else {
			result = String.valueOf(actor.getId());
		}

		return result;
	}
}