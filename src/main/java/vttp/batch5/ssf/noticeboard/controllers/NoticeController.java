package vttp.batch5.ssf.noticeboard.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import vttp.batch5.ssf.noticeboard.models.NoticeData;
import vttp.batch5.ssf.noticeboard.services.NoticeService;

import java.util.Map;

@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/")
    public String showLandingPage(Model model) {
        model.addAttribute("notice", new NoticeData());
        return "notice";
    }

    @PostMapping("/notice")
    public String processNotice(
            @Valid @ModelAttribute("notice") NoticeData noticeData,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "notice";
        }

        Map<String, Object> response = noticeService.postToNoticeServer(noticeData);

        if (response.containsKey("id")) {
            model.addAttribute("noticeId", response.get("id"));
            return "success";
        } else {
            model.addAttribute("errorMessage", response.getOrDefault("message", "ERROR"));
            return "error";
        }
    }
}
