package com.example.controller;

import com.example.common.Result;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;

@RestController
@RequestMapping()
public class FileUploadController {

    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

    public FileUploadController() {
        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("无法创建文件存储目录，请检查权限设置和路径是否正确。", ex);
        }
    }

    @PostMapping("/uploadFile")
    public Result uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            if (fileName.contains("..")) {
                throw new RuntimeException("抱歉！文件名包含非法路径序列 " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // 准备文件的URL
            String fileUrl = "http://localhost:9090/files/" + fileName;
            return Result.success(fileUrl);

        } catch (IOException ex) {
            throw new RuntimeException("无法存储文件。错误：" + ex.getMessage());
        }
    }

    // 新增一个用于访问文件的映射
    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = this.fileStorageLocation.resolve(filename).normalize();
            if (file.startsWith(this.fileStorageLocation) && Files.exists(file)) {
                Resource resource = new UrlResource(file.toUri());
                String contentType = determineFileContentType(file);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("请求的文件不在存储位置，无法访问：" + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("无法访问文件：" + filename, e);
        }
    }

    private String determineFileContentType(Path file) {
        String filename = file.getFileName().toString();
        if (filename.endsWith(".png")) {
            return "image/png";
        } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return "image/jpeg";
        } // Add more conditions for different file types as needed
        return "application/octet-stream"; // Default to binary stream
    }
}