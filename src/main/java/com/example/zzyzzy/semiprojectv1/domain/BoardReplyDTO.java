package com.example.zzyzzy.semiprojectv1.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BoardReplyDTO {
   private Board bd;
   private List<?> rps;


    public BoardReplyDTO( Board bd,List<?> rps) {
        this.rps = rps;
        this.bd = bd;
    }
}
