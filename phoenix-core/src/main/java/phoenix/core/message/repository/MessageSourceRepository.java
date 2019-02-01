package phoenix.core.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phoenix.core.message.entity.MessageSource;

@Repository
public interface MessageSourceRepository extends JpaRepository<MessageSource,Long> {
}
