package ordernow.backend.ordernow_backend.entities;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.UUID;

import org.springframework.context.annotation.Primary;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import ordernow.backend.ordernow_backend.enums.StatusTypeEnum;

@Entity
@Table(name = "service_table")
public class ServiceTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_service_table")
    private Long idServiceTable;

    @Column(name = "name")
    public String name;

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @Column(name = "qr_token")
    public String qrToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public StatusTypeEnum status;

    @ManyToOne
    @JoinColumn(name = "id_restaurant")
    private Restaurant restaurant;

    public ServiceTable(String name, Restaurant restaurant) {
        this.name = name;
        this.restaurant = restaurant;
        this.createdAt = LocalDateTime.now();
        this.qrToken = generateQrToken(name, restaurant != null ? restaurant.getName() : "");
        this.status = StatusTypeEnum.LIBRE;
    }

    public ServiceTable() {
    }

    private String generateQrToken(String tableName, String restaurantName) {
        try {
            String rawData = restaurantName + ":" + tableName + ":" + UUID.randomUUID() + ":" + System.currentTimeMillis();

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawData.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);

        } catch (NoSuchAlgorithmException e) {
            return UUID.randomUUID().toString();
        }
    }

    public void setStatus(StatusTypeEnum status) {
        this.status = status;
    }
}
