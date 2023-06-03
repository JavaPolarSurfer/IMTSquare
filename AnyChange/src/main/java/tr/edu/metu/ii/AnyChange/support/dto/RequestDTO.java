package tr.edu.metu.ii.AnyChange.support.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDTO {
    private String text;
    private String userEmail;
    private long id;
    private String responseText;
}
