package test.service.webClientService;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import test.dto.RequestDto;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static test.constant.RequestConstant.HTTP_REQUEST;

@Service
public class WebClientServiceImpl implements WebClientService {
    Logger logger = Logger.getLogger("");

    @Override
    public Map callExternalApi(RequestDto requestDto) {
        String method = requestDto.getMethodType().toLowerCase();
        return callExternalApi(requestDto, method);
    }

    @Override
    public Map get(RequestDto requestDto) {
        Gson gson = new Gson();
        try {
            WebClient webClient = WebClient.create();
            Map response = new HashMap();
            String res = webClient
                    .get()
                    .uri(requestDto.getUri())
                    .header("Content-Type", "application/json")
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(12000))
                    .block();
            response.put("response", res);
            return response;
        } catch (WebClientResponseException.BadRequest exception) {
            logger.info("Bad Request Error occurred during external api calling " + exception.getResponseBodyAsString());
            Map response = gson.fromJson(exception.getResponseBodyAsString(), Map.class);
            return response;
        } catch (WebClientResponseException.GatewayTimeout exception) {
            logger.info(" Request timeout occurred " + exception.getResponseBodyAsString());
            Map response = gson.fromJson(exception.getResponseBodyAsString(), Map.class);
            return response;
        } catch (WebClientResponseException.NotFound exception) {
            logger.info(" Not Found error occurred " + exception.getResponseBodyAsString());
            Map response = gson.fromJson(exception.getResponseBodyAsString(), Map.class);
            return response;
        } catch (WebClientResponseException.ServiceUnavailable exception) {
            logger.info(" Service unavailable error occurred " + exception.getResponseBodyAsString());
            Map response = gson.fromJson(exception.getResponseBodyAsString(), Map.class);
            return response;
        }
    }

    @Override
    public Map post(RequestDto requestDto) {
        Map response = checkValidJsnBody(requestDto);
        if (!(boolean) (response.get("flag"))) {
            return response;
        }

        return WebClient.builder().build()
                .post()
                .uri(requestDto.getUri())
                .bodyValue(response.get("json"))
                .retrieve()
                .bodyToMono(Map.class)
                .timeout(Duration.ofMillis(12000))
                .block();
    }

    public Map checkValidJsnBody(RequestDto requestDto) {
        return requestBodyValidation(requestDto);
    }
    private Map requestBodyValidation(RequestDto requestDto) {
        Map errors = new HashMap();
        Map error = new HashMap();
        try {
            Gson gson = new Gson();
            Map map = gson.fromJson(requestDto.getRequestBody(), Map.class);
            if (map == null)
                throw new JsonSyntaxException("Invalid Json Exception");
            errors.put("flag", Boolean.TRUE);
            errors.put("json", map);
            return errors;
        } catch (JsonSyntaxException exception) {
            String jsnFmt = "{ \"attribute\": \"value\n\n" + "}";
            errors.put("flag", Boolean.FALSE);
            error.put("error", "Invalid json, please provide valid json format ");
            error.put("correctFormat", jsnFmt);
            errors.put("errors", error);
            errors.put(HTTP_REQUEST, 400);
            logger.info("Invalid json, please provide valid json format  " + errors);
            return errors;
        }
    }

    private Map callExternalApi(RequestDto requestDto, String method) {
        Map result = null;
        switch (method) {
            case "post": {
                return post(requestDto);
            }

            case "get": {
                return get(requestDto);
            }

            case "delete": {
                return delete(requestDto);
            }
        }
        return result;
    }

    @Override
    public Map delete(RequestDto requestDto) {
        // need to do
        return null;
    }
}
