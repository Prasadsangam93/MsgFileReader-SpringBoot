package com.msgfilereader.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msgfilereader.entity.MsgFile;
import com.msgfilereader.repository.MsgFileRepository;
import org.apache.poi.hsmf.MAPIMessage;
import org.apache.poi.hsmf.datatypes.AttachmentChunks;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MsgFileService {

    private final MsgFileRepository msgFileRepository;
    private final ObjectMapper objectMapper = new ObjectMapper(); // For JSON conversion

    public MsgFileService(MsgFileRepository msgFileRepository) {
        this.msgFileRepository = msgFileRepository;
    }

    public Map<String, Object> processMsgFile(MultipartFile file) throws IOException {
        Map<String, Object> response = new HashMap<>();

        try {
            // Parse the .msg file
            MAPIMessage msg = new MAPIMessage(file.getInputStream());

            // Extract email details
            Map<String, Object> emailData = new HashMap<>();
            emailData.put("subject", msg.getSubject());
            emailData.put("sender", msg.getDisplayFrom());
            emailData.put("to", msg.getDisplayTo());
            emailData.put("cc", msg.getDisplayCC());
            emailData.put("bcc", msg.getDisplayBCC());
            emailData.put("date", msg.getMessageDate());
            emailData.put("body", msg.getTextBody());

            // Process attachments
            List<String> attachmentNames = new ArrayList<>();
            for (AttachmentChunks attachment : msg.getAttachmentFiles()) {
                attachmentNames.add(attachment.getAttachLongFileName() != null ?
                        attachment.getAttachLongFileName().toString() : "Unnamed Attachment");
            }
            emailData.put("attachments", attachmentNames.isEmpty() ? "No attachments" : attachmentNames);

            // Convert email data to JSON format
            String emailJson = objectMapper.writeValueAsString(emailData);

            // Save JSON data in the database with timestamp
            MsgFile emailMessage = new MsgFile(emailJson, LocalDateTime.now());
            msgFileRepository.save(emailMessage);

            response.put("message", "Email saved successfully");
            response.put("emailData", emailJson);
            response.put("storedDate", LocalDateTime.now().toString());

        } catch (Exception e) {
            response.put("error", "Error processing .msg file: " + e.getMessage());
        }

        return response;
    }
}
