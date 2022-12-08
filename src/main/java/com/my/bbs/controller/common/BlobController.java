package com.my.bbs.controller.common;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * @Author liuweicheng
 * @Date 2022/12/8
 **/

@RestController
@RequestMapping("/blob")
public class BlobController {
    @Autowired
    BlobContainerClient blobContainerClient;

    @PostMapping("/writeBlobFile")
    public String writeBlobFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = java.util.UUID.randomUUID() + "—" + file.getOriginalFilename();
// Get a reference to a blob
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
        blobClient.upload(file.getInputStream());
        return "liuy24-asc4gffvfjehgsg0.z01.azurefd.net/liuy24/" + fileName;
    }
}
