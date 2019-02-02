package phoenix.core.i18n.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

/**
 * @author mate.karolyi
 */
@Table(name = "RSC_RESOURCE")
@Entity
@IdClass(MessageSource.MessageSourcePK.class)
public class MessageSource {

    @Id
    private String code;

    @Id
    private Locale locale;

    private String text;

    public MessageSource() { }

    public MessageSource(String code, Locale locale, String text) {
        this.code = code;
        this.locale = locale;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    protected static class MessageSourcePK implements Serializable {
        private String code;
        private Locale locale;

        public MessageSourcePK() {}

        public MessageSourcePK(String code, Locale locale) {
            this.code = code;
            this.locale = locale;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MessageSourcePK that = (MessageSourcePK) o;
            return Objects.equals(code, that.code) &&
                    Objects.equals(locale, that.locale);
        }

        @Override
        public int hashCode() {
            return Objects.hash(code, locale);
        }
    }
}
