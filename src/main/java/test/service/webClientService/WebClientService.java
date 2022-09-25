package test.service.webClientService;

import reactor.core.publisher.Mono;
import test.dto.RequestDto;

import java.util.Map;

public interface WebClientService {

    Map callExternalApi(RequestDto requestDto);

    Map get(RequestDto requestDto);

    Map post(RequestDto requestDto);

    Map delete(RequestDto requestDto);

    Map checkValidJsnBody(RequestDto requestDto);
}
