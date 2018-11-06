package com.yunhui.excel.poi;

import com.yunhui.excel.annotation.ExcelField;
import com.yunhui.excel.bean.SortedField;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Title: ExcelUtils.java <br>
 * Description: <br>
 * Copyright (c) 聚阿网络科技版权所有 2018 <br>
 * Create DateTime: 2018年10月25日 11:35 <br>
 *
 * @author yun
 */
public class ExcelUtils {

    public static <T> void export(List<T> dataList, String fileName, HttpServletResponse response) {
        try {
            fillResponse(response,fileName);
            export(dataList, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> void export(List<T> dataList, Map<String, String> headMap, String fileName, HttpServletResponse response) {
        try {
            fillResponse(response,fileName);
            export(dataList, headMap, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> void export(List<T> dataList, OutputStream outputStream) {
        try {
            Workbook workBook = createWorkBook(dataList);
            workBook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> void export(List<T> dataList, Map<String, String> headMap, OutputStream outputStream) {
        try {
            Workbook workBook = createWorkBook(dataList, headMap);
            workBook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static <T> Workbook createWorkBook(List<T> dataList) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        fillSheet(dataList, sheet, workbook);
        return workbook;
    }

    public static <T> Workbook createWorkBook(List<T> dataList, Map<String, String> headMap) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        fillSheet(dataList, headMap, sheet, workbook);
        return workbook;
    }

    private static <T> void fillSheet(List<T> dataList, Map<String, String> headMap, HSSFSheet sheet, HSSFWorkbook workbook) throws Exception {
        if (CollectionUtils.isEmpty(dataList)) {
            throw new RuntimeException("dataList is null");
        }
        //将headMap 解析成SortField
        List<SortedField> sortedFields = parseHeadMap(headMap);
        createHead(sheet, sortedFields);
        fillBody(sheet, dataList, sortedFields, workbook);
    }

    private static List<SortedField> parseHeadMap(Map<String, String> headMap) {
        List<SortedField> sortedFields = new ArrayList<>();
        Set<String> keys = headMap.keySet();
        for (String key : keys) {
            SortedField sortedField = new SortedField();
            sortedField.setName(headMap.get(key));
            sortedField.setField(key);
            sortedFields.add(sortedField);
        }
        return sortedFields;
    }


    public static <T> void fillSheet(List<T> dataList, HSSFSheet sheet, Workbook workbook) throws Exception {
        if (CollectionUtils.isEmpty(dataList)) {
            throw new RuntimeException("dataList is null");
        }
        List<SortedField> sortedFields = parseDataList(dataList);
        createHead(sheet, sortedFields);
        fillBody(sheet, dataList, sortedFields, workbook);
    }

    private static <T> List<SortedField> parseDataList(List<T> dataList) {
        Field[] fields = dataList.get(0).getClass().getDeclaredFields();
        List<SortedField> list = new ArrayList<>();
        for (Field field : fields) {
            ExcelField annotation = field.getAnnotation(ExcelField.class);
            if (annotation == null) {
                continue;
            }
            SortedField sortedField = new SortedField();
            sortedField.setSort(annotation.sort());
            sortedField.setField(field.getName());
            sortedField.setName(annotation.name());
            list.add(sortedField);
        }
        List<SortedField> sortedFields = list.stream().sorted(Comparator.comparing(SortedField::getSort)).collect(Collectors.toList());
        return sortedFields;
    }

    private static <T> void fillBody(HSSFSheet sheet, List<T> dataList, List<SortedField> sortedFields, Workbook workbook) throws Exception {
        for (int i = 1; i < dataList.size(); i++) {
            T t = dataList.get(i);
            HSSFRow row = sheet.createRow(i);
            for (int j = 0; j < sortedFields.size(); j++) {
                Field field = t.getClass().getDeclaredField(sortedFields.get(j).getField());
                HSSFCell cell = row.createCell(j, getFieldCellType(field));
                setCellValue(cell, getFieldValue(field, t));
                cell.setCellStyle(getCellStyle(workbook, field));
            }
        }
        //自动调整单元格宽度
        for (int k = 0; k < dataList.size(); k++) {
            sheet.autoSizeColumn(k);
        }
    }

    private static void createHead(HSSFSheet sheet, List<SortedField> sortedFields) {
        //Excel头部
        HSSFRow topRow = sheet.createRow(0);
        for (int i = 0; i < sortedFields.size(); i++) {
            HSSFCell topCell = topRow.createCell(i, CellType.STRING);
            topCell.setCellValue(sortedFields.get(i).getName());
        }
    }

    public static Object getFieldValue(Field field, Object object) throws Exception {
        field.setAccessible(true);
        return field.get(object);
    }

    public static void setCellValue(HSSFCell cell, Object value) {
        if (value instanceof Byte) {
            cell.setCellValue(Double.valueOf(value.toString()));
        } else if (value instanceof Short) {
            cell.setCellValue(Double.valueOf(value.toString()));
        } else if (value instanceof Integer) {
            cell.setCellValue(Double.valueOf(value.toString()));
        } else if (value instanceof Long) {
            cell.setCellValue(Double.valueOf(value.toString()));
        } else if (value instanceof Float) {
            cell.setCellValue(Double.valueOf(value.toString()));
        } else if (value instanceof Double) {
            cell.setCellValue(Double.valueOf(value.toString()));
        } else if (value instanceof BigDecimal) {
            cell.setCellValue(Double.valueOf(value.toString()));
        } else if (value instanceof String) {
            cell.setCellValue(value.toString());
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Calendar) {
            cell.setCellValue((Calendar) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
        } else {
            cell.setCellValue((String) value);
        }
    }

    private static CellType getFieldCellType(Field field) {
        Class<?> type = field.getType();
        if (String.class.equals(type)) {
            return CellType.STRING;
        } else if (Long.class.equals(type) || Short.class.equals(type) || Byte.class.equals(type) || Integer.class.equals(type) || Float.class.equals(type) || Double.class.equals(type) || BigDecimal.class.equals(type)) {
            return CellType.NUMERIC;
        } else if (Boolean.class.equals(type)) {
            return CellType.BOOLEAN;
        } else {
            return CellType.STRING;
        }
    }

    private static CellStyle getCellStyle(Workbook workbook, Field field) {
//        CellStyle cellStyle = workbook.createCellStyle();
//        ExcelField annotation = field.getAnnotation(ExcelField.class);
//        cellStyle.setAlignment(annotation.cellAlign());
//        //自动换行
//        cellStyle.setWrapText(true);
        return null;
    }


    private static void fillResponse(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        response.setHeader("Content-disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes("UTF-8"), "ISO-8859-1"));
        response.setContentType("application/vnd.ms-excel");
    }

}
