package com.yunhui.excel.annotation;

import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.lang.annotation.*;

/**
 * Title: ExcelField.java <br>
 * Description: <br>
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

    HorizontalAlignment cellAlign() default HorizontalAlignment.GENERAL;
}
