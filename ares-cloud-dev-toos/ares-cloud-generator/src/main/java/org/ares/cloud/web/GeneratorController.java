package org.ares.cloud.web;

import cn.hutool.core.io.IoUtil;
import org.ares.cloud.dto.CodeGenerator;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.service.GeneratorService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author hugo  tangxkwork@163.com
 * @description 生成器
 * @date 2024/01/24/00:08
 **/
@Controller
@RequestMapping("ares-gen/generator")
@AllArgsConstructor
public class GeneratorController {
    private final GeneratorService generatorService;

    /**
     * 生成代码（zip压缩包）
     */
    @GetMapping("download")
    public void download(String modelIds, HttpServletResponse response) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        // 生成代码
        for (String modelId : modelIds.split(",")) {
            generatorService.downloadCode(modelId, zip);
        }

        IoUtil.close(zip);

        // zip压缩包数据
        byte[] data = outputStream.toByteArray();

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"ares.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IoUtil.write(response.getOutputStream(), false, data);
    }

    /**
     * 生成代码（自定义目录）
     */
    @ResponseBody
    @PostMapping("code")
    public Result<String> code(@RequestBody String[] modelIds) throws Exception {
        // 生成代码
        for (String modelId : modelIds) {
            generatorService.generatorCode(modelId);
        }
        return Result.success();
    }

    /**
     * 生成代码（zip压缩包）
     */
    @GetMapping("model_download")
    public void modelDownload(@RequestBody CodeGenerator req, HttpServletResponse response) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        // 生成代码
       generatorService.modelDownloadCode(req, zip);

        IoUtil.close(zip);

        // zip压缩包数据
        byte[] data = outputStream.toByteArray();
        String fileName = "ares"+req.getClassName()+"_"+req.getVersion()+".zip";
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IoUtil.write(response.getOutputStream(), false, data);
    }

    /**
     * 模型代码生成
     */
    @ResponseBody
    @PostMapping("model_gen")
    public Result<String> modelCode(@RequestBody CodeGenerator req) throws Exception {
      try {
        generatorService.modelCode(req);
        return Result.success();
      } catch (Exception e) {
        return   Result.error(e.getMessage());
      }
    }
}
