package phoenix.core.i18n.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phoenix.core.i18n.entity.MessageSource;

@Repository
public interface MessageSourceRepository extends JpaRepository<MessageSource,Long> {
}
