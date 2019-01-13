package phoenix.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mate.karolyi
 */
public class ObjectMapperUtil {

    private static final Logger LOGGER = LogManager.getLogger(ObjectMapperUtil.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void writeResponse(String success, String message, HttpStatus httpStatus, HttpServletResponse response)
    {
        Map<String, String> map = new HashMap<String, String>();

        map.put("success", success);
        map.put("message", message);

        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try {
            objectMapper.writeValue(response.getWriter(), map);
        }
        catch(IOException ex)
        {
            LOGGER.error(ex.getStackTrace());
            throw new RuntimeException(ex);
        }
    }

    public static Object deserialize(HttpServletRequest request, Class c)
    {
        try {
            return new ObjectMapper().readValue(request.getInputStream(), c);
        }
        catch(IOException ex)
        {
            LOGGER.error("Something went wrong during deserialization of json: {}", ex);
            throw new RuntimeException(ex);
        }
    }
}