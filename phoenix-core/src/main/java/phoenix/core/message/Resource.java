package phoenix.core.message;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import phoenix.core.message.entity.MessageSource;
import phoenix.core.message.repository.MessageSourceRepository;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class Resource extends AbstractMessageSource implements ResourceLoaderAware {

    private final Map<String, Map<String, String>>  resourceMap = new HashMap<String, Map<String, String>>();

    private ResourceLoader resourceLoader;
    private MessageSourceRepository messageSourceRepository;

    @Autowired
    public Resource(MessageSourceRepository messageSourceRepository) {
        this.messageSourceRepository = messageSourceRepository;
        reload();
    }

    public Resource() {
        reload();
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

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = (resourceLoader != null ? resourceLoader : new DefaultResourceLoader());
    }

    private String getText(String code, Locale locale) {
        Map<String, String> localeTextMap = resourceMap.get(code);
        String textInCurrentLocale = null;

        if (localeTextMap != null) {
            textInCurrentLocale = localeTextMap.get(locale.getLanguage());
            if (StringUtils.isBlank(textInCurrentLocale)) {
                textInCurrentLocale = localeTextMap.get(Locale.ENGLISH.getLanguage());
            }
        }
        return textInCurrentLocale;
    }

    protected Map<String, Map<String, String>> loadTexts() {
        Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>(0);
        List<MessageSource> messageSourceList = messageSourceRepository.findAll();

        for(MessageSource messageSource : messageSourceList) {
            if(map.get(messageSource.getCode()) == null) {
                Map<String, String> localeTextMap = new HashMap<String, String>(0);

                localeTextMap.put(messageSource.getLocale().getLanguage(), messageSource.getText());
                map.put(messageSource.getCode(), localeTextMap);
            }
            else {
                Map<String, String> localeTextMap = map.get(messageSource.getCode());
                if(localeTextMap.get(messageSource.getLocale()) == null) {
                    localeTextMap.put(messageSource.getLocale().getLanguage(), messageSource.getText());
                }
            }
        }
        return map;
    }

    public void reload() {
        resourceMap.clear();
        resourceMap.putAll(loadTexts());
    }
}
