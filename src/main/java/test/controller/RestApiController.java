package test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import test.constant.RequestConstant;
import test.service.ApiTestService;
import test.dto.RequestDto;
import test.service.redis_service.RedisService;
import test.service.webClientService.WebClientService;
import test.tenant.TenantContext;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static test.constant.RequestConstant.HTTP_REQUEST;

@RestController
@RequestMapping(path = {"/call-external-api", "/create-key"})
public class RestApiController {
    @Autowired
    ApiTestService apiTestService;
    @Autowired
    RedisService redisService;
    @PostMapping
    public ResponseEntity<?> externalApi(@RequestBody @Valid RequestDto requestDto, HttpServletRequest request) {
        Map response = apiTestService.callExternalApi(requestDto);
        Integer statusCode = response != null && response.get(HTTP_REQUEST) != null ? (Integer) response.get(HTTP_REQUEST) : 200;
        redisService.remove(TenantContext.getCurrentTenant());
        return new ResponseEntity<>(response, HttpStatus.valueOf(statusCode));
    }

    @GetMapping
    public ResponseEntity<?> createKey() {
        Map<String, String> keyValue = new HashMap<>();
        keyValue.put(RequestConstant.REQUEST_KEY, UUID.randomUUID().toString());
        return new ResponseEntity<>(keyValue, HttpStatus.CREATED);
    }
}
