package test.service;
import test.dto.RequestDto;
import java.util.Map;
public interface ApiTestService {
    Map callExternalApi(RequestDto requestDto);
}
