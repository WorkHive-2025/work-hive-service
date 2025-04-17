package workhive.app.member.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import workhive.app.member.converter.MapToStringConverter;

import java.util.Map;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(
        name = "oauth2",
        uniqueConstraints = {
                @UniqueConstraint(name = "oauth2_key_platform_unique", columnNames = {"key", "platform"}),
                @UniqueConstraint(name = "oauth2_member_id_unique", columnNames = "member_id")
        }
)
public class Oauth2  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oauth2_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @Column(name = "platform", nullable = false, length = 20)
    private String platform;

    @Column(name = "key", nullable = false, length = 255)
    private String key;

    @Column(name = "args", length = 255)
    @Convert(converter = MapToStringConverter.class)
    private Map<String, Object> args;

    @CreatedDate
    @Column(name = "created_at")
    private String createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private String updatedAt;

    @Builder
    public Oauth2(Member member, String platform, String key, Map<String, Object> args) {
        this.member = member;
        this.platform = platform;
        this.key = key;
        this.args = args;
    }
}
