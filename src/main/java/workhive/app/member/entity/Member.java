package workhive.app.member.entity;

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
        name = "member",
        uniqueConstraints = {
                @UniqueConstraint(name = "member_username_unique", columnNames = "username")
        }
)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username", nullable = false, length = 64)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name", length = 64)
    private String name;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @CreatedDate
    @Column(name = "created_at")
    private String createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private String updatedAt;

    @Builder
    public Member(String username, String password, String name, Boolean isActive) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.isActive = isActive == null|| isActive;
    }


}
