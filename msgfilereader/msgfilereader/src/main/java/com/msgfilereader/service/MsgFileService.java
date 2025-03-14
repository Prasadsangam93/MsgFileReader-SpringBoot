package com.msgfilereader.service;


import org.apache.poi.hsmf.MAPIMessage;
import org.apache.poi.hsmf.datatypes.AttachmentChunks;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MsgFileService {


        public Map<String, Object> processMsgFile (MultipartFile file) throws IOException {
            Map<String, Object> emailData = new HashMap<>();

            try {
                // Parse the .msg file
                MAPIMessage msg = new MAPIMessage(file.getInputStream());

                // Extract email details
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

            } catch (Exception e) {
                emailData.put("error", "Error processing .msg file: " + e.getMessage());
            }

            return emailData;
        }
    }
