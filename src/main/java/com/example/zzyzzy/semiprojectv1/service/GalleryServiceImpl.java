package com.example.zzyzzy.semiprojectv1.service;

import com.example.zzyzzy.semiprojectv1.domain.Gallery;
import com.example.zzyzzy.semiprojectv1.repository.GalleryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GalleryServiceImpl implements GalleryService {

    private final GalleryRepository galleryMapper;

    @Override
    public List<Gallery> selectGallery() {
        return galleryMapper.selectGallery();
    }
}
