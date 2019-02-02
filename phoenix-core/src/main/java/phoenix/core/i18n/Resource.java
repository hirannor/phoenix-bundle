package phoenix.core.i18n;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import phoenix.core.i18n.entity.MessageSource;
import phoenix.core.i18n.repository.MessageSourceRepository;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author mate.karolyi
 */
@Component
public class Resource extends AbstractMessageSource {

    private final Map<String, Map<String, String>>  resourceMap = new HashMap<String, Map<String, String>>();

    private ResourceLoader resourceLoader;
    private MessageSourceRepository messageSourceRepository;

    @Autowired
    public Resource(MessageSourceRepository messageSourceRepository) {
        this.messageSourceRepository = messageSourceRepository;
    }

    @PostConstruct
    public void init() {
        resourceMap.putAll(loadTexts());
    }

    @Cacheable("resources")
    public List<MessageSource> getLocalizedMessages() {
        return messageSourceRepository.findAll();
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        return getText(code, locale);
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String msg = getText(code, locale);
        MessageFormat result = createMessageFormat(msg, locale);
        return result;
    }

    private String getText(String code, Locale locale) {
        Map<String, String> codeTextMap = resourceMap.get(locale.getLanguage());
        String textInCurrentLocale = null;

        if (codeTextMap != null) {
            textInCurrentLocale = codeTextMap.get(code);
            if (StringUtils.isBlank(textInCurrentLocale)) {
                textInCurrentLocale = resourceMap.get(Locale.getDefault()).get(code);
            }

        }
        return textInCurrentLocale;
    }

    private Map<String, Map<String, String>> loadTexts() {
        Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>(0);
        List<MessageSource> messageSourceList = getLocalizedMessages();

        for(MessageSource messageSource : messageSourceList) {
            if(map.get(messageSource.getLocale().getLanguage()) == null) {
                Map<String, String> codeTextMap = new HashMap<String, String>(0);

                codeTextMap.put(messageSource.getCode(), messageSource.getText());
                map.put(messageSource.getLocale().getLanguage(), codeTextMap);

            } else {
               Map<String, String> codeTextMap = map.get(messageSource.getLocale().getLanguage());
               codeTextMap.put(messageSource.getCode(), messageSource.getText());

            }
        }
        return map;
    }
}
