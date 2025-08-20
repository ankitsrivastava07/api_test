package test.service;

import org.springframework.stereotype.Service;
import test.dto.RequestDto;

import java.util.Map;

@Service
public interface IntegrationService {

    Map callExternalApi(RequestDto requestDto);
}
