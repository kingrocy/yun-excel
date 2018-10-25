package com.yunhui.excel;

import java.lang.annotation.*;

/**
 * Title: ExcelField.java <br>
 * Description: <br>
 * Copyright (c) 聚阿网络科技版权所有 2018 <br>
 * Create DateTime: 2018年10月24日 14:40 <br>
 *
 * @author yun
 */
@Target(value={ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelField {

    int sort() default 0;

    String name() default "";

}
