package test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import test.dto.RequestDto;
import test.service.redis_service.RedisService;
import test.service.webClientService.WebClientService;
import test.tenant.TenantContext;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static test.constant.RequestConstant.HTTP_REQUEST;

@Service
public class ApiTestServiceImpl implements ApiTestService {
    @Autowired
    WebClientService webClientService;
    @Autowired
    RedisService redisService;
    @Override
    public Map callExternalApi(RequestDto requestDto) {
        String token  = TenantContext.getCurrentTenant();
        return webClientService.callExternalApi(requestDto);
    }
}
