package com.example.authdemo.controllers;

import com.example.authdemo.models.ResponseMessage;
import com.example.authdemo.models.ResultantFileResponse;
import com.example.authdemo.services.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
public class FileController {

    private final FileServiceImpl fileService;

    @Autowired
    public FileController(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    @PostMapping("upload")
    public ResponseEntity<ResponseMessage> uploadSourceFile(@RequestParam("source-file") MultipartFile sourceFile,
                                                            @RequestPart("sourceDescription") String sourceDescription,
                                                            @RequestParam("target-file") MultipartFile targetFile,
                                                            @RequestPart("targetDescription") String targetDescription,
                                                            Principal principal) {
        String message = "", id = "";
        try {
            id = this.fileService.saveAndCompareFiles(principal.getName(), sourceFile, sourceDescription, targetFile, targetDescription);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Could not upload the files!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, id));
        }
        message = "Uploaded the files successfully";
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, id));
    }

    @GetMapping("/result/{id}")
    public ResponseEntity<ResultantFileResponse> getFileResult(Principal principal, @PathVariable String id) {
        ResultantFileResponse resultantFileInfo = this.fileService.getResultantFileInfo(principal.getName(), id);
        return ResponseEntity.status(HttpStatus.OK).body(resultantFileInfo);
    }

}
