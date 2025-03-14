package com.msgfilereader.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="MsgFileReader")
@Entity
public class MsgFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Lob
    @Column(name = "email_data", columnDefinition = "TEXT") // Use TEXT if CLOB is not supported
    private String emailData;

    @Column(name = "stored_date", nullable = false)
    private LocalDateTime storedDate;

    public MsgFile(String emailData, LocalDateTime storedDate) {
        this.emailData = emailData;
        this.storedDate = storedDate;
    }

}