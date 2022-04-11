package com.dego.util;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ExcelNewUtils {

    public ExcelNewUtils() {
    }

    public static ExcelWriter getExcelWriter() {
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.getSheet().setDefaultColumnWidth(18);
        writer.getSheet().setDefaultRowHeightInPoints(18.0F);
        return writer;
    }

    public static void writerExcel(HttpServletResponse response, ExcelWriter writer, String fileName) throws IOException {
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1") + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out);
        writer.close();
    }

    public static void writerExcelByOutputStream(ByteArrayOutputStream out, ExcelWriter writer) throws IOException {
        writer.flush(out);
        writer.close();
    }



}
