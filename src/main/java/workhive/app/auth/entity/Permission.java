package workhive.app.auth.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(
        name = "permission",
        uniqueConstraints = {
                @UniqueConstraint(name = "permission_request_path_http_method_unique", columnNames = {"request_path", "http_method"})
        }
)
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long id;

    @Column(name = "request_path", nullable = false, length = 255)
    private String requestPath;

    @Column(name = "http_method", nullable = false, length = 20)
    private String httpMethod;

    @Column(name = "description", length = 255)
    private String description;

    @CreatedDate
    @Column(name = "created_at")
    private String createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private String updatedAt;

    @Builder
    public Permission(String requestPath, String httpMethod, String description) {
        this.requestPath = requestPath;
        this.httpMethod = httpMethod;
        this.description = description;
    }
}
