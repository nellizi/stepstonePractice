package com.likelion.stepstone.notification.model;

import com.likelion.stepstone.user.model.UserEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {

    private Long id;
    @Setter
    private String title;
    @Setter
    private String message;
    @Setter
    private boolean checked;

    private Long userCid;

    private LocalDateTime createdAt;
    @Setter
    private NotificationType notificationType;

    public static NotificationDto toDto(NotificationEntity entity){
        NotificationDto dto = NotificationDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .message(entity.getMessage())
                .checked(entity.isChecked())
                .userCid(entity.getUserEntity().getUserCid())
                .createdAt(entity.getCreatedAt())
                .notificationType(entity.getNotificationType())
                .build();

        return dto;
    }

}
