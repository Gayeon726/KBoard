package com.lec.spring.util;

import com.lec.spring.config.PrincipalDetails;
import com.lec.spring.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class U {
    // 현재 로그인 한 사용 자 User
    public static User getLoggedUser(){
        PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        return user;
    }

    // 현재 request 구하기
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attrs.getRequest();
    }

    // 현재 session 구하기
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    // 첨부파일 정도 (MultipartFile) 출력하기
    public static void printfileInfo(MultipartFile file) {
        String originalFileName = file.getOriginalFilename(); // 원본 파일 (최초 파일의 이름)

        if(originalFileName == null || originalFileName.isEmpty()){
            System.out.println("\t파일이 없습니다");
            return;
        }

        System.out.println("""
                    Original File Nam : %s
                    CleanPath : %s
                    File size : %s
                    MiME : %s
                """.formatted(
                        originalFileName,
                        StringUtils.cleanPath(originalFileName),
                        file.getSize() + " bytes",  // 용량 (byte)
                        file.getContentType()       // content type (MIME type)
                ));

        // 이미지 파일 여부
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());  // 이미지 <- InputStream으로 읽어냄
            if(bufferedImage != null){                                          // 이미지가 아니라면 null을 리턴
                System.out.println("""
                        이미지 파일입니다: %d x %d
                        """.formatted(bufferedImage.getWidth(), bufferedImage.getHeight())); // 이미지 가로 세로 불러오기
            }else {
                System.out.println("\t이미지 파일이 아닙니다");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}












