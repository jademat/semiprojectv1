package com.example.zzyzzy.semiprojectv1.service;

import com.example.zzyzzy.semiprojectv1.domain.GalleryImage;
import com.example.zzyzzy.semiprojectv1.domain.GalleryImgDTO;
import com.example.zzyzzy.semiprojectv1.domain.GalleryListDTO;
import com.example.zzyzzy.semiprojectv1.domain.GalleryViewDTO;
import com.example.zzyzzy.semiprojectv1.repository.GalleryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GalleryServiceImpl implements GalleryService {

    private final GalleryRepository galleryMapper;

    @Override
    public List<GalleryListDTO> selectGallery() {
        return galleryMapper.selectGallery();
    }

    @Override
    @Transactional
    public GalleryImgDTO readOneGalleryImage(int gno) {

        galleryMapper.updateViewOne(gno);                               // 조회수 증가
        GalleryViewDTO gal = galleryMapper.selectOneGallery(gno);       // 본문글 가져오기
        List<GalleryImage> gi = galleryMapper.selectGalleryImages(gno); // 본문글에 포함된 이미지들 가져오기

        return new GalleryImgDTO(gal, gi);
    }
}
