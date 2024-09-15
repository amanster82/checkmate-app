package com.papilion.checkmate;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

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
    private String UPLOAD_DIR = "./uploads/";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadMultipleFiles(@RequestParam("files") List<MultipartFile> files,
            @RequestParam("title") String str) {
        System.out.println("GETS HERE" + str);
        if (files.isEmpty() || str.isEmpty() || str.equals("undefined")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select files to upload.");
        } else {
            UPLOAD_DIR += str + "/";
        }

        StringBuilder uploadedFiles = new StringBuilder();
        makeDirectoryIfNotExist(UPLOAD_DIR);
        checkIfFilesExistsIfSoDeleteAndUpload(UPLOAD_DIR);
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
                    Files.write(path, bytes);
                    System.out.println(
                            "UPLOAD_DIR + file.getOriginalFilename()" + UPLOAD_DIR + file.getOriginalFilename());

                    if (files.size() > 1) {
                        uploadedFiles.append(file.getOriginalFilename()).append(",");
                    }else{
                        uploadedFiles.append(file.getOriginalFilename());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Failed to upload file: " + file.getOriginalFilename());
                }
            }
        }

        UPLOAD_DIR = "./uploads/";

        System.out.println("WHAT IS UPLOADED FILE"+ uploadedFiles);
        return ResponseEntity.status(HttpStatus.OK).body("" + uploadedFiles);
    }

    private void checkIfFilesExistsIfSoDeleteAndUpload(String path) {
        File directoryPath = new File(path);
        // List all files and directories
        String contents[] = directoryPath.list();

        if (contents != null && contents.length != 0) {

            // Delete existing files
            for (String fileName : contents) {
                File file = new File(directoryPath.getAbsolutePath() + File.separator + fileName);
                if (file.isFile()) {
                    boolean deleted = file.delete();
                    if (!deleted) {
                        System.out.println("Failed to delete file: " + fileName);
                    } else {
                        System.out.println("Deleted file: " + fileName);
                    }
                }
            }
        } else {
            System.out.println("No files to delete in the directory: " + path);
        }

    }

    private void makeDirectoryIfNotExist(String imageDirectory) {
        System.out.println("WHAT IS THE IMGAGEDIRECTORY?" + imageDirectory);
        File directory = new File(imageDirectory);
        System.out.println("WHAT IS THE DIRECTORY " + directory);

        if (!directory.exists()) {
            System.out.println("DOES IT GET INSIDE HER?E?????");
            directory.mkdirs();
        }
    }

    @GetMapping("/membershipExists")
    public String membershipCheck() {
        // Create a file Object for Directory
        File directoryPath = new File(UPLOAD_DIR + "\\" + "Membership");
        // List all files and directories
        String contents[] = directoryPath.list();

        if (contents != null && contents.length != 0) {
            return contents[0];
        } else {
            return "Does not exist!";
        }
    }
}
