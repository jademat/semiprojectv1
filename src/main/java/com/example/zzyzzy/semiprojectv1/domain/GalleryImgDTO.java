package com.example.zzyzzy.semiprojectv1.domain;

import lombok.Data;

import java.util.List;

@Data
public class GalleryImgDTO {

    private GalleryViewDTO gal;     // 갤러리 게시글
    private List<?> gi;         // 게시글에 포함된 이미지들

    public GalleryImgDTO(GalleryViewDTO gal, List<?> gi) {
        this.gal = gal;
        this.gi = gi;
    }
}
