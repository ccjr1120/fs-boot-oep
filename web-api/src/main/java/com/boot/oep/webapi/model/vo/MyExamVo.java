package com.boot.oep.webapi.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ccjr
 * @date 2021/4/9 12:40 下午
 */
@Data
public class MyExamVo {

    private String id;
    private String examName;
    private Integer grade;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updateTime;
    private Integer questionNum;
    private Integer wrongNum;

}
