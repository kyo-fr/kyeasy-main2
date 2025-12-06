package org.ares.cloud.file.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ares.cloud.common.exception.FileUploadException;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.file.domain.BasicFile;
import org.ares.cloud.file.factory.FileStorageFactory;
import org.ares.cloud.file.properties.FileProperties;
import org.ares.cloud.file.service.FileService;
import org.ares.cloud.file.service.FileStorageService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

/**
 * @author hugo
 * @version 1.0
 * @description: 文件控制器
 * @date 2024/10/12 00:37
 */
@RestController
@RequestMapping("${ares.cloud.file.base_path}")
@ConditionalOnProperty(
        value = "ares.cloud.file.controllerEnabled",
        havingValue = "true",
        matchIfMissing = false
)
@Tag(name = "File Upload API", description = "API for uploading files")
public class FileController {
    @Autowired
    private FileStorageFactory fileStorageFactory;
    @Autowired
    private FileService fileService;
    @Autowired
    private FileProperties fileProperties;
    // 单文件上传接口
    @Operation(
            summary = "Single File Upload",
            description = "Uploads a single file to the server",
            responses = {
                    @ApiResponse(responseCode = "200", description = "File uploaded successfully", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "461", description = "Invalid file input")
            }
    )
    @PostMapping("/upload")
    public Result<String> upload( @Parameter(
            description = "File to be uploaded. The file is required.",
            required = true,
            content = @Content(mediaType = "multipart/form-data", schema = @Schema(type = "string", format = "binary"))
    ) @RequestParam("file")MultipartFile file){
        if (file.isEmpty()) {
            throw new FileUploadException("upload_file_empty");
        }
        try {
            FileStorageService storageService = fileStorageFactory.getFileStorageService();
            String s = storageService.uploadFile(file);
            // 获取文件名称
            String fileName = file.getOriginalFilename();
            BasicFile fileInfo = new BasicFile();
            fileInfo.setOriginalFileName(fileName);
            fileInfo.setFileSize(file.getSize());
            fileInfo.setFileType(file.getContentType());
            fileInfo.setName(s);
            fileInfo.setContainer(storageService.getStorageName());
            // 将文件写入到指定目录
            fileService.saveFile(fileInfo);
            return Result.success(s);
        } catch (Exception e) {
            e.fillInStackTrace();
            throw new FileUploadException(e);
        }
    }

    @Operation(summary = "获取文件完整访问地址", description = "根据文件名获取文件的完整访问URL")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功获取文件访问地址"),
            @ApiResponse(responseCode = "404", description = "文件未找到")
    })
    @GetMapping("/access-url/{date}/{fileName}")
    public Result<String> getAccessUrl(
            @Parameter(description = "时间目录", required = true)
            @PathVariable("date")  String date,
            @Parameter(description = "文件的名称", required = true)
            @PathVariable("fileName") String fileName) {
        FileStorageService storageService = fileStorageFactory.getFileStorageService();
        String accessUrl = storageService.getAccessUrl(buildCompleteName(date,fileName));
        return Result.success(accessUrl);
    }


    @Operation(summary = "下载文件", description = "从服务器下载指定的文件")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "文件下载成功", content = @Content(mediaType = "application/octet-stream")),
            @ApiResponse(responseCode = "404", description = "文件未找到")
    })
    @GetMapping("/download/{date}/{fileName}")
    public ResponseEntity<Resource> downloadFile(
            @Parameter(description = "要下载的时间目录", required = true)
            @PathVariable("date")  String date,
            @Parameter(description = "要下载的文件名称", required = true)
            @PathVariable("fileName") String fileName) {
        // 获取文件资源
        FileStorageService storageService = fileStorageFactory.getFileStorageService();
        return storageService.downloadFile(buildCompleteName(date,fileName));
    }


    @Operation(summary = "预览文件", description = "在浏览器中预览指定的文件")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "文件预览成功", content = @Content(mediaType = "application/octet-stream")),
            @ApiResponse(responseCode = "404", description = "文件未找到")
    })
    @GetMapping("/preview/{date}/{fileName}")
    public ResponseEntity<Resource> previewFile(
            @Parameter(description = "要预览的时间目录", required = true)
            @PathVariable("date")  String date,
            @Parameter(description = "要预览的文件名称", required = true)
            @PathVariable("fileName") String fileName) {
        // 获取文件资源
        FileStorageService storageService = fileStorageFactory.getFileStorageService();
        return storageService.previewFile(buildCompleteName(date,fileName));
    }


    @Operation(summary = "分段传输文件", description = "支持通过 HTTP Range 头进行文件的分段传输，适用于大文件传输")
    @ApiResponses({
            @ApiResponse(responseCode = "206", description = "部分内容传输成功", content = @Content(mediaType = "application/octet-stream")),
            @ApiResponse(responseCode = "404", description = "文件未找到"),
            @ApiResponse(responseCode = "416", description = "请求的范围无效")
    })
    @GetMapping("/stream/{date}/{fileName}")
    public ResponseEntity<Resource> streamFile(
            @Parameter(description = "要流式传输的时间目录", required = true)
            @PathVariable("date")  String date,
            @Parameter(description = "要流式传输的文件名称", required = true)
            @PathVariable("fileName") String fileName,
            @RequestHeader(value = "Range", required = false) String rangeHeader) {
        // 获取文件资源
        FileStorageService storageService = fileStorageFactory.getFileStorageService();
        return storageService.streamFile(buildCompleteName(date,fileName), rangeHeader);
    }

    /**
     * 删除文件接口
     *
     * @param fileName 文件名
     * @return 删除结果
     */
    @Operation(summary = "删除文件", description = "根据文件名删除文件")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "文件删除成功"),
            @ApiResponse(responseCode = "404", description = "文件未找到"),
            @ApiResponse(responseCode = "500", description = "文件删除失败")
    })
    @DeleteMapping("/delete/{date}/{fileName}")
    public Result<String> deleteFile(
            @Parameter(description = "要删除的时间目录", required = true)
            @PathVariable("date")  String date,
            @Parameter(description = "要删除的文件名称", required = true)
            @PathVariable("fileName") String fileName) {
        if (!fileProperties.isDeletionAllowed()){
            throw new FileUploadException("The system does not allow resource deletion");
        }
        // 获取文件资源
        FileStorageService storageService = fileStorageFactory.getFileStorageService();
        boolean isDeleted = storageService.deleteFile(buildCompleteName(date,fileName));
        if (isDeleted) {
            //删除存储的文件信息
            fileService.deleteFile(buildCompleteName(date,fileName));
            return Result.success();
        } else {
            return Result.error("delete_file_fail");
        }
    }

    private String buildCompleteName(String date,String fileName) {
        return date+"/"+fileName;
    }
}
