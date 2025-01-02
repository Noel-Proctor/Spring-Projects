package com.ecommerce.store.service.FileService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //Get file names of current / original path.
        String originalFileName = file.getOriginalFilename();

        //rename file uniquely - Generate unique filename.
        String guid = UUID.randomUUID().toString();
        String newFileName = guid.concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
        String filePath = path + File.separator + newFileName;

        //Check if path exists and create
        File folder = new File(path);

        if(!folder.exists()) {
            folder.mkdir();
        }

        //upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return newFileName;
    }
}
