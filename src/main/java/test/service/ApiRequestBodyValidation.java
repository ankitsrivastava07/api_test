package test.service;

import test.dto.RequestDto;

import java.util.Map;

public interface ApiRequestBodyValidation {

    Map jsonRequestBodySyntaxCheck(RequestDto requestDto);
}
