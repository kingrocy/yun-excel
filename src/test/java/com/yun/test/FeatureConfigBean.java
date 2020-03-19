package com.yun.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Date : 2019-10-21 18:23
 * @Desc :
 */
@Getter
@Setter
@ToString
public class FeatureConfigBean {

    private String col;
    private String valueType;
    private Integer type;
    private String dbField;
    private String dbKey;
    private String table;
    private Integer group;
    private TableJoinBean join;
    private Boolean queryOne;
}
