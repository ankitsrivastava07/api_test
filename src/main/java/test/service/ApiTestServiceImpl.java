package test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.dto.RequestDto;
import test.service.redis_service.RedisService;
import test.tenant.TenantContext;

import java.util.Map;

@Service
public class ApiTestServiceImpl implements ApiTestService {

    @Autowired
    IntegrationService integrationService;
    @Autowired
    ApiRequestBodyValidation apiRequestBodyValidation;

    @Autowired
    RedisService redisService;

    @Override
    public Map callExternalApi(RequestDto requestDto) {
        String token = TenantContext.getCurrentTenant();
        Map validationApiRequestBodyResult;
        if ((validationApiRequestBodyResult = apiRequestBodyValidation.jsonRequestBodySyntaxCheck(requestDto)).equals("false"))
            return validationApiRequestBodyResult;

        return integrationService.callExternalApi(requestDto);
    }
}
