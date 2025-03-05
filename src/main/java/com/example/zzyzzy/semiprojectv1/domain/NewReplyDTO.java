package com.example.zzyzzy.semiprojectv1.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewReplyDTO {
    private String comments;
    private String userid;
    private Integer ref;
    private Integer pno;
}
