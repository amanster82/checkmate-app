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
import java.util.List;

@RestController
@RequestMapping("/api/files")
@CrossOrigin
public class FileUploadController {

    // Define the directory where files will be stored
    private String UPLOAD_DIR = "";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadMultipleFiles(

            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @RequestParam(value = "title", required = true) String title,
            @RequestParam(value = "oldTitle", required = false) String oldTitle,
            @RequestParam(value = "isUpdate", required = false) Boolean isUpdate) {

        System.out.println("Title: " + title);
        System.out.println("Files: " + files);
        System.out.println("OldTitle: " + oldTitle);
        System.out.println("isUpdate: " + isUpdate);

        if (isUpdate) {
            if (files != null) {
                UPLOAD_DIR = "./uploads/" + title + "/";
                parseFiles(files, true, oldTitle);
            } else if (title != null && oldTitle !=null) {
                /* RENAME DIRECTORY ONLY */ 

                // Original directory path
                File oldDir = new File("./uploads/" + oldTitle + "/");



                // New directory path
                File newDir = new File("./uploads/" + title + "/");
                System.out.println("What is my oldDIR: "+ oldDir);
                System.out.println("What is my newDIR: "+ newDir);

                System.out.println("Old Directory Absolute Path: " + oldDir.getAbsolutePath());
                System.out.println("New Directory Absolute Path: " + newDir.getAbsolutePath());

                // Attempt to rename the directory
                if (oldDir.renameTo(newDir)) {
                    System.out.println("Directory renamed successfully!");
                } else {
                    System.out.println("Failed to rename directory.");
                }

                return ResponseEntity.status(HttpStatus.ACCEPTED).body("No files to upload... Update static info only");
            }
        } else if (files == null || files.isEmpty() || title.isEmpty() || title.equals("undefined")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select files to upload.");
        } else {
            UPLOAD_DIR = "./uploads/" + title + "/";
            parseFiles(files, false, null);
        }

        return null;

    }

    private ResponseEntity<String> parseFiles(List<MultipartFile> files, boolean isUpdate, String oldTitle) {
        StringBuilder uploadedFiles = new StringBuilder();

        // Check if an old title is provided and delete the old directory
        if (isUpdate && oldTitle != null && !oldTitle.isEmpty()) {
            String oldDirPath = "./uploads/" + oldTitle;
            File oldDirectory = new File(oldDirPath);

            // Check if the directory exists and delete it
            if (oldDirectory.exists() && oldDirectory.isDirectory()) {
                try {
                    deleteDirectoryRecursively(oldDirectory);
                } catch (IOException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Failed to delete old directory: " + oldTitle);
                }
            }
        }

        // Ensure upload directory exists
        makeDirectoryIfNotExist(UPLOAD_DIR);

        // Check and delete any existing files in the upload directory
        checkIfFilesExistsIfSoDeleteAndUpload(UPLOAD_DIR);

        // Process new files
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
                    Files.write(path, bytes);

                    if (files.size() > 1) {
                        uploadedFiles.append(file.getOriginalFilename()).append(",");
                    } else {
                        uploadedFiles.append(file.getOriginalFilename());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Failed to upload file: " + file.getOriginalFilename());
                }
            }
        }

        return ResponseEntity.ok("Files uploaded: " + uploadedFiles.toString());
    }

    // Helper method to recursively delete a directory and its contents
    private void deleteDirectoryRecursively(File directory) throws IOException {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectoryRecursively(file);
                }
            }
        }
        Files.delete(directory.toPath()); // Delete the directory/file itself
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
        File directoryPath = new File("./uploads/" + "\\" + "Membership");
        // List all files and directories
        String contents[] = directoryPath.list();

        if (contents != null && contents.length != 0) {
            return contents[0];
        } else {
            return "Does not exist!";
        }
    }
}
