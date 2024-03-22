package main.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class Utils {
    public static <T> T processGetRequest(String url, Class<T> class_) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        T object;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            object = objectMapper.readValue(responseEntity.getBody(), class_);
            return object;
        } catch (JsonProcessingException error) {
            System.err.println(error.getMessage());
            return null;
        }
    }

    public static ResponseEntity<String> processPostRequest(String url, String token, String requestBody, HttpMethod httpMethod,
                                                            MediaType mediaType) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        if (mediaType != null) {
            httpHeaders.setContentType(mediaType);
        }
        if (token != null) {
            httpHeaders.setBearerAuth(token);
        }
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, httpHeaders);

        return restTemplate.exchange(url, httpMethod, httpEntity, String.class);
    }
}
