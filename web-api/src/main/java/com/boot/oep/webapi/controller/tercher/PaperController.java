package com.boot.oep.webapi.controller.tercher;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.boot.oep.model.Paper;
import com.boot.oep.result.ApiResponse;
import com.boot.oep.utils.Utils;
import com.boot.oep.utils.excel.UploadQuestionListener;
import com.boot.oep.webapi.model.dto.PaperDto;
import com.boot.oep.webapi.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author ccjr
 * @date 2021/3/18 9:02 下午
 */
@RestController
@RequestMapping("/teacher/paper")
public class PaperController {

    private final PaperService paperService;

    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }

    @PostMapping("/addOne")
    public ApiResponse<String> addOne(PaperDto dto){
        Paper paper = new Paper();
        BeanUtil.copyProperties(dto, paper);
        paper.setId(Utils.getUuId());
        paperService.save(paper);
        return ApiResponse.ok();
    }

    @PostMapping("/upload")
    public ApiResponse<String> importMore(@RequestParam("file") MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), new UploadQuestionListener()).sheet().doRead();
        return ApiResponse.ok();
    }

    @PostMapping("/updateOne")
    public ApiResponse<String> importMore(PaperDto dto){
        Paper paper = new Paper();
        BeanUtil.copyProperties(dto, paper);
        paperService.updateById(paper);
        return ApiResponse.ok();
    }


}
