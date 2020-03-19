package com.yun.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Date : 2019-10-22 19:05
 * @Desc :
 */
@Getter
@Setter
@ToString
public class TableJoinBean {

    private String leftTable;
    private String rightTable;
    private String targetTable;
    private String leftKey;
    private String rightKey;
}
