package ru.gb.authorizationservice.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import ru.gb.common.generic.entities.GenericEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="attempts")
@Data
@RequiredArgsConstructor
public class PasswordChangeAttempt {
    @Id
    private Long id;

    @Column(name="created_when")
    @CreationTimestamp
    private LocalDateTime createdWhen;

    @Column(name = "is_verified")
    private boolean isVerified;

    @Column(name = "verification_code")
    private String code;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;
}
