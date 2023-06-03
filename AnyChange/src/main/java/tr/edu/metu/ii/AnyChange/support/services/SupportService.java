package tr.edu.metu.ii.AnyChange.support.services;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tr.edu.metu.ii.AnyChange.support.dto.RequestDTO;
import tr.edu.metu.ii.AnyChange.support.model.Request;
import tr.edu.metu.ii.AnyChange.support.repositories.RequestRepository;
import tr.edu.metu.ii.AnyChange.user.models.User;
import tr.edu.metu.ii.AnyChange.user.services.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SupportService {
    private UserService userService;
    private RequestRepository requestRepository;
    private JavaMailSender javaMailSender;

    public void createRequest(RequestDTO requestDTO) {
        Request request = new Request();
        request.setDate(LocalDateTime.now());
        request.setText(requestDTO.getText());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.clearContext();
        if (authentication != null) {
            String name = authentication.getName();
            User user = (User) userService.loadUserByUsername(name);
            request.setUser(user);
        }
        requestRepository.save(request);
    }

    public List<RequestDTO> getRequests() {
        List<RequestDTO> requestDTOs = new ArrayList<>();
        requestRepository.findAll().forEach(request -> {
            RequestDTO requestDTO = new RequestDTO();
            requestDTO.setId(request.getId());
            requestDTO.setUserEmail(request.getUser().getEmail());
            requestDTO.setText(request.getText());
            requestDTOs.add(requestDTO);
        });
        return requestDTOs;
    }

    public RequestDTO getRequest(long requestId) {
        Optional<Request> requestOptional = requestRepository.findById(requestId);
        var request = requestOptional.get();
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setId(request.getId());
        requestDTO.setUserEmail(request.getUser().getEmail());
        requestDTO.setText(request.getText());
        return requestDTO;
    }

    public void answerRequest(RequestDTO requestDTO) {
        Optional<Request> requestOptional = requestRepository.findById(requestDTO.getId());
        var request = requestOptional.get();

        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(request.getUser().getEmail());
        mailMessage.setSubject("Support Request Reply!");
        mailMessage.setFrom("<MAIL>");
        mailMessage.setText("Support personnel has responded you: " + requestDTO.getResponseText());

        sendEmail(mailMessage);

        requestRepository.deleteById(requestDTO.getId());
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }
}
