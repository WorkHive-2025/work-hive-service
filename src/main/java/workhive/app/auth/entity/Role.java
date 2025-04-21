package workhive.app.auth.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(
        name = "role",
        uniqueConstraints = {
                @UniqueConstraint(name = "role_role_code_unique", columnNames = "role_code")
        }
)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_code", nullable = false, length = 20)
    private String roleCode;

    @Column(name = "role_name", nullable = false, length = 50)
    private String roleName;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Builder
    public Role(String roleCode, String roleName) {
        this.roleCode = roleCode;
        this.roleName = roleName;
    }
}
