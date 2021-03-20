package com.boot.oep.webapi.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ccjr
 * @date 2021/3/20 10:59 下午
 */
@Data
public class PaperDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String question;
    private String rightAnswer;
    private String wrongAnswer;

}
