package com.schedule.tool;



import com.schedule.paramterBody.Taskwithfeedback;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dell on 2017/8/27.
 */
public class excel {

    public HSSFWorkbook exportExcel(List<Taskwithfeedback> taskwithfeedbackList) {
        //创建工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建新的一页,并命名
        HSSFSheet sheet = workbook.createSheet("First sheet");

        String[] header = {"学号","昵称","反馈"};
        List<String[]> info = new ArrayList<String[]>();
        for(int k = 0;k<taskwithfeedbackList.size();k++)
        {
            String[] data = {taskwithfeedbackList.get(k).getStudentid(),taskwithfeedbackList.get(k).getNickname(),
                    taskwithfeedbackList.get(k).getFeedback()};
            info.add(data);

        }


        HSSFRow headerRow = sheet.createRow(0);
        //设置标题,row行，cell方格,从0开始计数
        for(int i=0;i<header.length;i++)
        {

           HSSFCell hssfCell = headerRow.createCell(i);
           hssfCell.setCellValue(header[i]);
        }

        //设置内容，row/cell同上
        for(int i=1;i<info.size()+1;i++)
        {
            //创建一行
            HSSFRow contentRow = sheet.createRow(i);
            //在行上创建一个方格
            for(int j=0;j<header.length;j++)
            {
                String[] temp = info.get(i-1);
                HSSFCell hssfCell = contentRow.createCell(j);
                hssfCell.setCellValue(temp[j]);
            }

        }

        return workbook;
    }


}
