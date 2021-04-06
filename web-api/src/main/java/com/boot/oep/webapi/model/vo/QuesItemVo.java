package com.boot.oep.webapi.model.vo;

import com.boot.oep.webapi.model.dto.AnswerItem;
import lombok.Data;

import java.util.List;

/**
 * @author zrf
 * @date 2021/4/6
 */
@Data
public class QuesItemVo {

    private String questionId;
    private String question;
    private Integer type;
    private List<AnswerItem> answerItems;

}
