package jschool.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;

/** Service for aditional methids, used everywhere*/
@Service
public class UtilService {
    /**
     * parses json input into object
     * */
    private static final Logger logger = Logger.getLogger(UtilService.class);

    public static JsonNode parseJsonInput(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            logger.info("Try to parse JSON:" + json);
            jsonNode = objectMapper.readTree(json);
        } catch (IOException e) {
            logger.error(e.toString());
        }
        return jsonNode;
    }
}
