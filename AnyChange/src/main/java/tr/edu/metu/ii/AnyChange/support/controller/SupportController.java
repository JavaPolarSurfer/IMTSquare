package tr.edu.metu.ii.AnyChange.support.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tr.edu.metu.ii.AnyChange.support.dto.RequestDTO;
import tr.edu.metu.ii.AnyChange.support.services.SupportService;

import java.util.List;

@Controller
@AllArgsConstructor
public class SupportController {
    private SupportService supportService;
    @GetMapping("/requestSupport")
    String requestSupport(Model model) {
        RequestDTO requestDTO = new RequestDTO();
        model.addAttribute("request", requestDTO);
        return "requestSupport";
    }

    @PostMapping("/requestSupport")
    String requestSupport(@ModelAttribute("request")RequestDTO requestDTO) {
        supportService.createRequest(requestDTO);
        return "redirect:/products";
    }

    @GetMapping("/manageSupportRequests")
    String manageSupportRequests(Model model) {
        List<RequestDTO> requestDTOList = supportService.getRequests();
        model.addAttribute("requests", requestDTOList);
        return "manageSupportRequests";
    }

    @GetMapping("/removeRequest")
    String removeRequest(@RequestParam("requestId")long requestId, Model model) {
        RequestDTO requestDTO = supportService.getRequest(requestId);
        model.addAttribute("request", requestDTO);
        return "closeRequest";
    }

    @PostMapping("/removeRequest")
    String removeRequest(@ModelAttribute("request")RequestDTO requestDTO, Model model) {
        supportService.answerRequest(requestDTO);
        return "redirect:/manageSupportRequests";
    }
}
