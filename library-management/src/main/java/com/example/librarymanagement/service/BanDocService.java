package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.TaoTheRequest;
import com.example.librarymanagement.entity.BanDoc;
import com.example.librarymanagement.repository.BanDocRepository;
import org.springframework.stereotype.Service;

@Service
public class BanDocService {
    private final BanDocRepository banDocRepository;

    public BanDocService(BanDocRepository banDocRepository) {
        this.banDocRepository = banDocRepository;
    }

    public BanDoc createBanDoc(TaoTheRequest request) {
        BanDoc banDoc = new BanDoc();
        banDoc.setHoTen(request.getHoTen());
        banDoc.setDiaChi(request.getDiaChi());
        banDoc.setEmail(request.getEmail());
        banDoc.setSdt(request.getSdt());
        return banDocRepository.save(banDoc);
    }
}
