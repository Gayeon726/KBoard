package com.lec.spring.controller;

import com.lec.spring.domain.Attachment;
import com.lec.spring.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController // 데이터 response 하는 컨트롤러
                // @Controller + @ResponseBody 와 같음
public class AttachmentController {

    @Value("${app.upload.path}")
    private String uploadDIr;

    private AttachmentService attachmentService;

    @Autowired
    public void setAttachmentService(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    // 파일 다운로드
    // id: 첨부파일의 id
    // ResponseEntity<T> 를 사용하여
    // '직접' Response data 를 구성
    @RequestMapping("/board/download")
    public ResponseEntity<?> download(Long id){ // 와일드 카드 (어떤 타입이든 담을 수 있음)
        if(id == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // 400 에러

        Attachment file = attachmentService.findById(id);
        if(file == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // 404 에러

        String sourceName = file.getSourcename(); // 원본 이름
        String fileName = file.getFilename(); // 저장된 파일명

        String path = new File(uploadDIr, fileName).getAbsolutePath(); // 저장 파일의 절대경로

        try {
            // 파일 유형 (MIME type) 추출
            String mimeType = Files.probeContentType(Paths.get(path)); // ex) "image/png"

            // 파일 유형이 지정되지 않은 경우
            if(mimeType == null){
                mimeType = "application/octet-stream"; // 일련의 byte 스트림 타입. 유형이 알려지지 않은 경우 지정
            }

            // response body 준비
            Path filePath = Paths.get(path);
                                // Resouce <- InputStream <- 저장된 파일
            Resource resource= new InputStreamResource(Files.newInputStream(filePath));

            // response header 세팅
            HttpHeaders headers = new HttpHeaders(); // 웹 통신에서 쓰는 header 객체

            // ↓ 원본 파일 이름 (souceName) 으로 다운로드하게 하기 위한 세팅 ★★★★★★
            //   반.드.시 URL 인코딩
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(URLEncoder.encode(sourceName,"utf-8")).build());
            headers.setCacheControl("no-cache");
            headers.setContentType(MediaType.parseMediaType(mimeType)); // 유형 지정

            // ResponseEntity<> 리턴 (body, header, status)
            return new ResponseEntity<>(resource, headers, HttpStatus.OK); // 200 에러

        } catch (IOException e) {
            return new ResponseEntity<>(null,null,HttpStatus.CONFLICT); // 409 에러 ( 파일이 없음! )
        }
    }
}
