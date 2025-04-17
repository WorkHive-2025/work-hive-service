package workhive.app.auth.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import workhive.app.member.entity.Member;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(
        name = "member_role",
        uniqueConstraints = {
                @UniqueConstraint(name = "member_role_member_id_role_id_unique", columnNames = {"member_id", "role_id"})
        },
        indexes = {
                @Index(name = "idx_member_role_member_id", columnList = "member_id"),
                @Index(name = "idx_member_role_role_id", columnList = "role_id")
        }
)
public class MemberRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_role_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Role role;

    @CreatedDate
    @Column(name = "created_at")
    private String createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private String updatedAt;

    @Builder
    public MemberRole(Member member, Role role) {
        this.member = member;
        this.role = role;
    }

}
