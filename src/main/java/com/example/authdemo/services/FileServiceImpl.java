package com.example.authdemo.services;

import com.example.authdemo.models.FileDescriptionInfo;
import com.example.authdemo.models.FileInfo;
import com.example.authdemo.models.ResultantFileResponse;
import com.example.authdemo.repository.FileRepository;
import com.example.authdemo.repository.ResultantFileRepository;
import com.example.authdemo.repository.entities.FileEntity;
import com.example.authdemo.repository.entities.ResultantFileEntity;
import com.example.authdemo.util.CSVHelper;
import com.example.authdemo.util.JsonHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final ResultantFileRepository resultantFileRepository;
    private final ReconciliationService reconciliationService;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, ResultantFileRepository resultantFileRepository, ReconciliationService reconciliationService) {
        this.fileRepository = fileRepository;
        this.resultantFileRepository = resultantFileRepository;
        this.reconciliationService = reconciliationService;
    }

    @Override
    public String saveAndCompareFiles(String username, MultipartFile sourceFile, String sourceDescription, MultipartFile targetFile, String targetDescription) throws JsonProcessingException {
        String id = UUID.randomUUID().toString().substring(0, 10);
        ObjectMapper objectMapper = new ObjectMapper();
        FileDescriptionInfo targetDescriptionInfo = objectMapper.readValue(targetDescription, FileDescriptionInfo.class);
        FileDescriptionInfo sourceDescriptionInfo = objectMapper.readValue(sourceDescription, FileDescriptionInfo.class);
        List<FileInfo> sourceFiles = this.getFileInfo(sourceFile, sourceDescriptionInfo.getFileType());
        List<FileInfo> targetFiles = this.getFileInfo(targetFile, targetDescriptionInfo.getFileType());
        String time = LocalDateTime.now().toString();

        this.saveRequestFileDetails(username, sourceDescriptionInfo, targetDescriptionInfo, id, sourceFiles, targetFiles, time);

        this.saveResultFileDetails(username, id, sourceFiles, targetFiles, time);

        this.reconciliationService.updateCompareReconciliation(username, id, time);

        return id;
    }

    @Override
    public ResultantFileResponse getResultantFileInfo(String username, String id) {
        ResultantFileResponse response = new ResultantFileResponse();
        ResultantFileEntity fileEntity = this.resultantFileRepository.findResultantFilesById(id).get(0);
        if (!username.equals(fileEntity.getUsername()))
            return null;
        response.setMatching(fileEntity.getMatch());
        response.setMissing(fileEntity.getMissing());
        response.setMismatching(fileEntity.getMismatch());
        return response;
    }


    private void saveResultFileDetails(String username, String id, List<FileInfo> sourceFiles, List<FileInfo> targetFiles, String time) {
        ResultantFileEntity resultantFileEntity = new ResultantFileEntity();
        resultantFileEntity.setId(id);
        resultantFileEntity.setUsername(username);
        resultantFileEntity.setTimestamp(time);
        resultantFileEntity.setMatch(matchingFileInfos(sourceFiles, targetFiles));
        resultantFileEntity.setMissing(missingFileInfos(sourceFiles, targetFiles));
        resultantFileEntity.setMismatch(missingFileInfos(targetFiles, sourceFiles));
        resultantFileRepository.save(resultantFileEntity);
    }

    private void saveRequestFileDetails(String username, FileDescriptionInfo sourceDescription, FileDescriptionInfo targetDescription,
                                        String id, List<FileInfo> sourceFiles, List<FileInfo> targetFiles, String time) throws JsonProcessingException {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setId(id);
        fileEntity.setSourceFile(sourceFiles);
        fileEntity.setTargetFile(targetFiles);
        fileEntity.setSourceDescription(sourceDescription);
        fileEntity.setTargetDescription(targetDescription);
        fileEntity.setUsername(username);
        fileEntity.setTime(time);
        fileRepository.save(fileEntity);
    }

    private List<FileInfo> matchingFileInfos(List<FileInfo> sourceInfos, List<FileInfo> targetInfos) {
        List<FileInfo> matching = new ArrayList<>();
        for (FileInfo source : sourceInfos) {
            for (FileInfo target : targetInfos) {
                if (source.getTransactionId().equals(target.getTransactionId())) {
                    matching.add(source);
                }
            }
        }
        return matching;
    }

    private List<FileInfo> missingFileInfos(List<FileInfo> sourceInfos, List<FileInfo> targetInfos) {
        List<FileInfo> missing = new ArrayList<>();
        for (FileInfo source : sourceInfos) {
            boolean found = false;
            for (FileInfo target : targetInfos) {
                if (source.getTransactionId().equals(target.getTransactionId())) {
                    found = true;
                    break;
                }
            }
            if (!found)
                missing.add(source);
        }
        return missing;
    }

    private List<FileInfo> getFileInfo(MultipartFile sourceFile, String fileType) {
        if (fileType.equals("csv")) {
            try {
                return CSVHelper.csvToFileInfo(sourceFile.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException("fail to store csv data: " + e.getMessage());
            }
        } else if (fileType.equals("json")) {
            try {
                List<FileInfo> jsonFileInfos = JsonHelper.jsonToSourceFile(sourceFile.getInputStream());
                if (jsonFileInfos == null)
                    throw new IOException("fail to store json data: ");
                return jsonFileInfos;
            } catch (IOException e) {
                throw new RuntimeException("fail to store json data: " + e.getMessage());
            }
        }
        return new ArrayList<>();
    }

}
