package com.example.memberapi.model;

import lombok.Data;

@Data
public class MemberJSONResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String createTs;
    private String lastUpdatedTs;
}
