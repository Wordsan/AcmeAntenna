package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

import domain.Agent;
import repositories.AgentRepository;

@Component
@Transactional
public class StringToAgentConverter implements Converter<String, Agent> {

    @Autowired
    private AgentRepository repository;


    @Override
    public Agent convert(final String text)
    {
        Agent result;

        try {
            if (StringUtils.isEmpty(text)) {
                result = null;
            } else {
                final int id = Integer.valueOf(text);
                result = this.repository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }

        return result;
    }
}
