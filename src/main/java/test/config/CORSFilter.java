package test.config;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.annotation.AccessType;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import test.service.redis_service.RedisService;
import test.tenant.TenantContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static test.constant.RequestConstant.CREATE_TOKEN;

//@Async
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {
    Logger logger = Logger.getLogger("");
    @Autowired
    RedisService redisService;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String tocken = request.getHeader("KEY");
        String uri = request.getRequestURI();
        String method = request.getMethod();
        logger.info("Request method called is " + method + " and uri " + uri);

        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE , PATCH , PUT , HEAD");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me , KEY");
        if (method.equals(HttpMethod.OPTIONS.name()))
            return;
        try {
            if (!((HttpServletRequest) req).getRequestURI().equals(CREATE_TOKEN)
                    && (!StringUtils.hasLength(tocken) || "null".equalsIgnoreCase(tocken))) {
                Map error = new HashMap();
                Map errors = new HashMap();

                error.put("KeyMissing", "Token can't be null or empty");
                error.put("isKeyExist", Boolean.FALSE);
                errors.put("errors", error);

                Gson gson = new Gson();
                String json = gson.toJson(errors);
                response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                response.setHeader(HttpStatus.BAD_REQUEST.name(), json);
                response.sendError(HttpStatus.BAD_REQUEST.value(), json);
                return;
            }
            String redisTok;
            if ((redisTok = redisService.get(tocken)) == null) {
                redisService.save(tocken);
                redisTok = redisService.get(tocken);
                TenantContext.setCurrentTenant(tocken);
                //chain.doFilter(request, response);
            } else {
                Map error = new HashMap();
                Map errors = new HashMap();

                error.put("requestInQueue", "Your request already in Queue. Please wait");
                error.put("isPrevReqExist", Boolean.TRUE);
                errors.put("errors", error);

                Gson gson = new Gson();
                String json = gson.toJson(errors);
                response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                response.setHeader(HttpStatus.BAD_REQUEST.name(), json);
                response.sendError(HttpStatus.BAD_REQUEST.value(), json);
                redisService.remove(tocken);
                return;
            }
            logger.info("Redish Token Value is " + redisTok);
            chain.doFilter(request, response);
        }
        finally {
            redisService.remove(tocken);
            TenantContext.removeCurrentTenant();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
