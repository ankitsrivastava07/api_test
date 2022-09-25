package test.dto;

import test.annotation.NotNull;

public class RequestDto {
    private String requestBody;
    @NotNull(message = "Url can't be null ")
    private String uri;

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestJson) {
        this.requestBody = requestJson;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    @NotNull(message = "Method can't be null")
    private String methodType;

    @Override
    public String toString() {
        return "RequestDto{" +
                "requestBody=" +
                ", uri='" + uri + '\'' +
                ", methodType='" + methodType + '\'' +
                '}';
    }
}
