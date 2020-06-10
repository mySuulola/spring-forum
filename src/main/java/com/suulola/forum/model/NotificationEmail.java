package com.suulola.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotificationEmail {

    private String subject;

    private String receipient;

    private String body;
}
