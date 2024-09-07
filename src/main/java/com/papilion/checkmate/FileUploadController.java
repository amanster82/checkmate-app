package com.papilion.checkmate;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@CrossOrigin
public class FileUploadController {

    // Define the directory where files will be stored
    private String UPLOAD_DIR = "uploads/";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadMultipleFiles(@RequestParam("files") List<MultipartFile> files, @RequestParam("title") String str) {
        if (files.isEmpty() || str.isEmpty() || str.equals("undefined")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select files to upload.");
        }else{
            UPLOAD_DIR += str+"/";
        }

        StringBuilder uploadedFiles = new StringBuilder();
        makeDirectoryIfNotExist(UPLOAD_DIR);
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
                    Files.write(path, bytes);
                    System.out.println("UPLOAD_DIR + file.getOriginalFilename()" + UPLOAD_DIR + file.getOriginalFilename());
                    uploadedFiles.append(file.getOriginalFilename()).append(",");
                } catch (IOException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + file.getOriginalFilename());
                }
            }
        }        

        UPLOAD_DIR = "uploads/";

        return null;
    }

    private void makeDirectoryIfNotExist(String imageDirectory) {
        File directory = new File(imageDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}

