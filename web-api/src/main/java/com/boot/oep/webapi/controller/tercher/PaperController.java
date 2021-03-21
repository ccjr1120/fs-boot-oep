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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author ccjr
 * @date 2021/3/18 9:02 下午
 */
@RestController
@RequestMapping("/teacher/paper")
public class PaperController {

}
