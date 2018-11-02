package com.yunhui.excel.annotation;

import java.lang.annotation.*;

/**
 * Title: ExcelTable.java <br>
 * Description: <br>
 * Copyright (c) 聚阿网络科技版权所有 2018 <br>
 * Create DateTime: 2018年10月24日 17:11 <br>
 *
 * @author yun
 */
@Target(value={ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelTable {

    String tableName() default "";
}
