package com.example.hospitalapi.notification.infrastructure.persistence.adapter;

import com.example.hospitalapi.notification.domain.entity.Notification;
import com.example.hospitalapi.notification.domain.repository.NotificationRepository;
import com.example.hospitalapi.notification.domain.valueobject.NotificationChannel;
import com.example.hospitalapi.notification.domain.valueobject.NotificationId;
import com.example.hospitalapi.notification.domain.valueobject.NotificationStatus;
import com.example.hospitalapi.notification.domain.valueobject.RecipientType;
import com.example.hospitalapi.notification.infrastructure.persistence.entity.NotificationJpaEntity;
import com.example.hospitalapi.notification.infrastructure.persistence.repository.NotificationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter implementing NotificationRepository using JPA
 */
@Component
@RequiredArgsConstructor
public class NotificationRepositoryAdapter implements NotificationRepository {

    private final NotificationJpaRepository notificationJpaRepository;

    @Override
    public Notification save(Notification notification) {
        NotificationJpaEntity notificationJpaEntity = mapToJpaEntity(notification);
        NotificationJpaEntity savedEntity = notificationJpaRepository.save(notificationJpaEntity);
        return mapToDomainEntity(savedEntity);
    }

    @Override
    public Optional<Notification> findById(NotificationId id) {
        return notificationJpaRepository.findById(id.toString())
                .map(this::mapToDomainEntity);
    }

    @Override
    public List<Notification> findAll() {
        return notificationJpaRepository.findAll().stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Notification> findByRecipientId(String recipientId) {
        return notificationJpaRepository.findByRecipientId(recipientId).stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Notification> findByRecipientType(RecipientType recipientType) {
        return notificationJpaRepository.findByRecipientType(recipientType).stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Notification> findByChannel(NotificationChannel channel) {
        return notificationJpaRepository.findByChannel(channel).stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Notification> findByStatus(NotificationStatus status) {
        return notificationJpaRepository.findByStatus(status).stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(NotificationId id) {
        notificationJpaRepository.deleteById(id.toString());
    }

    @Override
    public boolean existsById(NotificationId id) {
        return notificationJpaRepository.existsById(id.toString());
    }

    private NotificationJpaEntity mapToJpaEntity(Notification notification) {
        return NotificationJpaEntity.builder()
                .id(notification.getId().toString())
                .recipientId(notification.getRecipientId())
                .recipientType(notification.getRecipientType())
                .channel(notification.getChannel())
                .subject(notification.getSubject())
                .content(notification.getContent())
                .status(notification.getStatus())
                .sentAt(notification.getSentAt())
                .createdAt(notification.getCreatedAt())
                .updatedAt(notification.getUpdatedAt())
                .build();
    }

    private Notification mapToDomainEntity(NotificationJpaEntity entity) {
        NotificationId notificationId = new NotificationId(entity.getId());
        
        // Create a new notification with the required fields
        Notification notification = new Notification(
                notificationId,
                entity.getRecipientId(),
                entity.getRecipientType(),
                entity.getChannel(),
                entity.getSubject(),
                entity.getContent()
        );
        
        // Set the status based on the entity's status
        switch (entity.getStatus()) {
            case SENT:
                notification.markAsSent();
                break;
            case FAILED:
                notification.markAsFailed();
                break;
            case CANCELLED:
                notification.cancel();
                break;
            default:
                // PENDING is the default status when creating a notification
                break;
        }
        
        return notification;
    }
}