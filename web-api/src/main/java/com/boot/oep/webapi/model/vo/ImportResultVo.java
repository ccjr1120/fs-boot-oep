package com.boot.oep.webapi.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ccjr
 * @date 2021/4/9 9:45 上午
 */
@Data
public class ImportResultVo {

    private int successNum;
    private List<FailItem> failItemList;

    @Data
    public static class FailItem{
        private int col;
        private String reason;
    }

}
