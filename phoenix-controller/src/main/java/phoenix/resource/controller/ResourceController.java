package phoenix.resource.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import phoenix.core.i18n.Resource;
import phoenix.core.i18n.entity.MessageSource;

import java.util.List;

@RestController
public class ResourceController {

    private Resource resource;

    public ResourceController(Resource resource) {
        this.resource = resource;
    }

    @GetMapping("/resources")
    public List<MessageSource> getResources() {
        return resource.getLocalizedMessages();
    }
}
