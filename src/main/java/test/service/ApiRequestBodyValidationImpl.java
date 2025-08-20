package test.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import test.constant.RequestConstant;
import test.dto.RequestDto;

import java.util.HashMap;
import java.util.Map;

@Component
public class ApiRequestBodyValidationImpl implements ApiRequestBodyValidation {

    private Logger logger = LoggerFactory.getLogger("");

    private Map checkValidJsnBody(RequestDto requestDto) {
        return requestBodyValidation(requestDto);
    }

    private Map requestBodyValidation(RequestDto requestDto) {
        Map errors = new HashMap();
        Map error = new HashMap();
        try {
            Map map = new Gson().fromJson(requestDto.getRequestBody(), Map.class);
            errors.put("flag", Boolean.TRUE);
            errors.put("json", map);
            return errors;
        } catch (JsonSyntaxException exception) {
            String jsnFmt = "{ \"attribute\": \"value\n\n" + "}";
            errors.put("flag", Boolean.FALSE);
            error.put("error", "Invalid json, please provide valid json format ");
            error.put("correctFormat", jsnFmt);
            errors.put("errors", error);
            errors.put(RequestConstant.HTTP_REQUEST, 400);
            logger.info("Invalid json, please provide valid json format  " + errors);
            return errors;
        }

    }

    @Override
    public Map jsonRequestBodySyntaxCheck(RequestDto requestDto) {
        return checkValidJsnBody(requestDto);
    }
}