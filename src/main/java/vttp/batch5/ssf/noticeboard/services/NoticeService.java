package vttp.batch5.ssf.noticeboard.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import vttp.batch5.ssf.noticeboard.models.NoticeData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoticeService {

    @Value("${notice.publishing.server.url}")
    private String publishingServerUrl;

    private final RestTemplate restTemplate;

    public NoticeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> postToNoticeServer(NoticeData noticeData) {
        String endpoint = publishingServerUrl + "/notice";

        Map<String, Object> payload = new HashMap<>();
        payload.put("title", noticeData.getTitle());
        payload.put("poster", noticeData.getPoster());
        payload.put("postDate", noticeData.getPostDate().toEpochDay() * 86400000); // Convert LocalDate to milliseconds
        payload.put("categories", noticeData.getCategories());
        payload.put("text", noticeData.getText());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                endpoint, HttpMethod.POST, requestEntity, Map.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getResponseBodyAsString());
            errorResponse.put("statusCode", e.getStatusCode().value());
            return errorResponse;
        } catch (RestClientException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Failed to publish notice: " + e.getMessage());
            return errorResponse;
        }
    }
}
