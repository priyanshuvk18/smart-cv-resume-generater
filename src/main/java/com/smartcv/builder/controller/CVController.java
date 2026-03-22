package com.smartcv.builder.controller;

import com.smartcv.builder.dto.CVDto;
import com.smartcv.builder.dto.MessageResponse;
import com.smartcv.builder.entity.CVDetail;
import com.smartcv.builder.service.CVService;
import com.smartcv.builder.service.PDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cv")
public class CVController {

    @Autowired
    private CVService cvService;

    @Autowired
    private PDFService pdfService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CVDto> saveCV(@RequestBody CVDto cvDto) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(cvService.saveCV(cvDto, userEmail));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<CVDto>> getAllUserCVs() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(cvService.getUserCVs(userEmail));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CVDto> getCVById(@PathVariable Long id) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(cvService.getCVById(id, userEmail));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteCV(@PathVariable Long id) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        cvService.deleteCV(id, userEmail);
        return ResponseEntity.ok(new MessageResponse("CV deleted successfully!"));
    }

    @GetMapping("/download/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) throws IOException {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        byte[] pdfContent = pdfService.generatePdfFromCv(id, userEmail);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "SmartCV_" + id + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);
    }
}
