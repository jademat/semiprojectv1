package com.example.zzyzzy.semiprojectv1.service;

import com.example.zzyzzy.semiprojectv1.domain.GalleryListDTO;
import com.example.zzyzzy.semiprojectv1.domain.GalleryImgDTO;

import java.util.List;

public interface GalleryService {

    List<GalleryListDTO> selectGallery();

    GalleryImgDTO readOneGalleryImage(int gno);

}
