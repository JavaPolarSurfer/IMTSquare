package tr.edu.metu.ii.AnyChange.support.model;

import jakarta.persistence.*;
import lombok.*;
import tr.edu.metu.ii.AnyChange.user.models.User;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private String text;
    @OneToOne
    private User user;
}
