package vttp.batch5.ssf.noticeboard.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import vttp.batch5.ssf.noticeboard.models.NoticeData;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoticeService {

    @Value("${notice.publishing.server.url}")
    private String publishingServerUrl;

    private final RestTemplate restTemplate;
    private final NoticeRepository noticeRepository;
    private final ObjectMapper objectMapper;

    public NoticeService(RestTemplate restTemplate, NoticeRepository noticeRepository, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.noticeRepository = noticeRepository;
        this.objectMapper = objectMapper;
    }

    public Map<String, Object> postToNoticeServer(NoticeData noticeData) {
        String endpoint = publishingServerUrl + "/notice";

        Map<String, Object> payload = new HashMap<>();
        payload.put("title", noticeData.getTitle());
        payload.put("poster", noticeData.getPoster());
        payload.put("postDate", noticeData.getPostDate().toEpochDay() * 86400000);
        payload.put("categories", noticeData.getCategories());
        payload.put("text", noticeData.getText());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(endpoint, HttpMethod.POST, requestEntity, Map.class);
            Map<String, Object> responseBody = response.getBody();

            if (responseBody != null && responseBody.containsKey("id")) {
                String jsonResponse = objectMapper.writeValueAsString(responseBody);
                noticeRepository.insertNotices(responseBody.get("id").toString(), jsonResponse);
            }

            return responseBody;

        } catch (Exception e) {
            return Map.of("message", "I/O error: " + e.getMessage());
        }
    }
}
