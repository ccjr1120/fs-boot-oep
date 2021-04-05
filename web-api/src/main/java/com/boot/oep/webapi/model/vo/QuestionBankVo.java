package com.boot.oep.webapi.model.vo;

import com.boot.oep.model.QuestionBank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ccjr
 * @date 2021/4/5 4:00 下午
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionBankVo extends QuestionBank {

    private Integer questionCount;

}
