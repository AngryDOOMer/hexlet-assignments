package exercise.model;

import jakarta.persistence.*;

// BEGIN
import lombok.Data;
import java.time.LocalDate;
import static jakarta.persistence.GenerationType.IDENTITY;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "task")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    public String title;

    private String description;

    @CreatedDate
    private LocalDate createdAt;

    @LastModifiedDate
    private LocalDate updatedAt;
}
// END
