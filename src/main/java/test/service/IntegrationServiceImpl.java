package test.service;

import com.google.gson.Gson;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import test.dto.RequestDto;
import test.service.webClientService.WebClientService;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class IntegrationServiceImpl implements IntegrationService {

    @Autowired
    private WebClientService webClientService;
    @Autowired
    private ApiRequestBodyValidation apiRequestBodyValidation;

    private Logger logger = (Logger) LoggerFactory.getLogger("");

    private Map get(RequestDto requestDto) {
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

    private Map post(RequestDto requestDto) {
        Map response = apiRequestBodyValidation.jsonRequestBodySyntaxCheck(requestDto);
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

    public Map callExternalApi(RequestDto requestDto) {
        Map result = null;
        String method = requestDto.getMethodType();
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

    public Map delete(RequestDto requestDto) {
        // need to do
        return null;
    }
}
