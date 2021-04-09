package com.boot.oep.webapi.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ccjr
 * @date 2021/4/9 3:36 下午
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExamRecordDto extends BaseQueryDto{

    private String examId;

}
