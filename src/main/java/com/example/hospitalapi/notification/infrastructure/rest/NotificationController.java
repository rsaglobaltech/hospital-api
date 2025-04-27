package com.example.hospitalapi.notification.infrastructure.rest;

import com.example.hospitalapi.notification.application.command.SendNotificationCommand;
import com.example.hospitalapi.notification.application.query.GetAllNotificationsQuery;
import com.example.hospitalapi.notification.application.query.GetNotificationByIdQuery;
import com.example.hospitalapi.notification.application.query.NotificationResponse;
import com.example.hospitalapi.notification.domain.valueobject.NotificationId;
import com.example.hospitalapi.notification.infrastructure.rest.request.SendNotificationRequest;
import com.example.hospitalapi.shared.domain.bus.CommandBus;
import com.example.hospitalapi.shared.domain.bus.QueryBus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for notification endpoints
 */
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification Management", description = "API for managing notifications")
public class NotificationController {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    /**
     * Send a notification
     * @param request the notification request
     * @return the ID of the created notification
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Send a notification", description = "Sends a notification to the specified recipient")
    public ResponseEntity<String> sendNotification(@Valid @RequestBody SendNotificationRequest request) {
        SendNotificationCommand command = SendNotificationCommand.builder()
                .recipientId(request.getRecipientId())
                .recipientType(request.getRecipientType())
                .channel(request.getChannel())
                .subject(request.getSubject())
                .content(request.getContent())
                .build();

        NotificationId notificationId = (NotificationId) commandBus.dispatch(command).join();

        return ResponseEntity.status(HttpStatus.CREATED).body(notificationId.toString());
    }

    /**
     * Get a notification by ID
     * @param id the notification ID
     * @return the notification
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a notification by ID", description = "Retrieves a notification by ID")
    public ResponseEntity<NotificationResponse> getNotificationById(@PathVariable String id) {
        GetNotificationByIdQuery query = new GetNotificationByIdQuery(id);

        try {
            NotificationResponse response = queryBus.dispatch(query).join();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get all notifications
     * @return list of all notifications
     */
    @GetMapping
    @Operation(summary = "Get all notifications", description = "Retrieves all notifications")
    public ResponseEntity<List<NotificationResponse>> getAllNotifications() {
        GetAllNotificationsQuery query = new GetAllNotificationsQuery();

        List<NotificationResponse> response = queryBus.dispatch(query).join();

        return ResponseEntity.ok(response);
    }
}
