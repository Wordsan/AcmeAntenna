package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import domain.PlatformSubscription;

@Component
@Transactional
public class PlatformSubscriptionToStringConverter
implements Converter<PlatformSubscription, String> {
	@Override
	public String convert(PlatformSubscription platformSubscription)
	{
		String result;

		if (platformSubscription == null) {
			result = null;
		} else {
			result = String.valueOf(platformSubscription.getId());
		}

		return result;
	}
}