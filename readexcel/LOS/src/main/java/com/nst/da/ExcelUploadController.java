package com.nst.da;
import com.nst.da.ExcelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class ExcelUploadController {
    @Autowired
    private  ExcelService excelDataService;

    @PostMapping("/uploadExcel")
    public String uploadExcel(@RequestParam("file") MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            int totalRows = sheet.getPhysicalNumberOfRows();
            int availableProcessors = Runtime.getRuntime().availableProcessors();
            int threadPoolSize = 18;
            int batchSize = totalRows / threadPoolSize;
            List<Thread> threads = new ArrayList<>();
            // take current time and store it in starttime.txt
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("starttime.txt"))) {
                writer.write(Instant.now().toString());
                //System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                //System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }
            for (int i = 0; i < threadPoolSize; i++) {
                int startRow = i * batchSize;
                int endRow = (i == threadPoolSize - 1) ? totalRows : (i + 1) * batchSize;
                ProcessBatchRunnable processBatchRunnable = new ProcessBatchRunnable(file, sheet, startRow, endRow);
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.submit(processBatchRunnable);
                executorService.shutdown();
            }
            workbook.close();
            inputStream.close();
            return "Excel data uploaded successfully!";
        } catch (Exception e) {
            return "Failed to upload Excel data: " + e.getMessage();
        }
    }

    private class ProcessBatchRunnable implements Runnable {
        private final MultipartFile file;
        private final Sheet sheet;
        private final int startRow;
        private final int endRow;

        public ProcessBatchRunnable(MultipartFile file, Sheet sheet, int startRow, int endRow) {
            this.file = file;
            this.sheet = sheet;
            this.startRow = startRow;
            this.endRow = endRow;
        }
        @Override
        public void run() {
            processBatch(file, sheet, startRow, endRow);
        }
    }
    private void processBatch(MultipartFile file, Sheet sheet, int startRow, int endRow) {
        List<YourEntity> batchData = new ArrayList<>();
        for (int i = startRow; i < endRow; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                YourEntity entity = new YourEntity();
                entity.setField1(getCellValueAsString(row.getCell(0)));
                entity.setField2(getCellValueAsString(row.getCell(1)));
                entity.setField3(getCellValueAsString(row.getCell(2)));
                entity.setField4(getCellValueAsString(row.getCell(3)));
                entity.setField5(getCellValueAsString(row.getCell(4)));
                entity.setField6(getCellValueAsString(row.getCell(5)));
                entity.setField7(getCellValueAsString(row.getCell(6)));
                entity.setField8(getCellValueAsString(row.getCell(7)));
                entity.setField9(getCellValueAsString(row.getCell(8)));
                entity.setField10(getCellValueAsString(row.getCell(9)));
                entity.setField11(getCellValueAsString(row.getCell(10)));
                entity.setField12(getCellValueAsString(row.getCell(11)));
                entity.setField13(getCellValueAsString(row.getCell(12)));
                entity.setField14(getCellValueAsString(row.getCell(13)));
                entity.setField15(getCellValueAsString(row.getCell(14)));
                entity.setField16(getCellValueAsString(row.getCell(15)));
                entity.setField17(getCellValueAsString(row.getCell(16)));
                entity.setField18(getCellValueAsString(row.getCell(17)));
                entity.setField19(getCellValueAsString(row.getCell(18)));
                entity.setField20(getCellValueAsString(row.getCell(19)));
                entity.setField21(getCellValueAsString(row.getCell(20)));
                entity.setField22(getCellValueAsString(row.getCell(21)));
                entity.setField23(getCellValueAsString(row.getCell(22)));
                entity.setField24(getCellValueAsString(row.getCell(23)));
                entity.setField25(getCellValueAsString(row.getCell(24)));
                entity.setField26(getCellValueAsString(row.getCell(25)));
                entity.setField27(getCellValueAsString(row.getCell(26)));
                entity.setField28(getCellValueAsString(row.getCell(27)));
                entity.setField29(getCellValueAsString(row.getCell(28)));
                entity.setField30(getCellValueAsString(row.getCell(29)));
                entity.setField31(getCellValueAsString(row.getCell(30)));
                entity.setField32(getCellValueAsString(row.getCell(31)));
                entity.setField33(getCellValueAsString(row.getCell(32)));
                entity.setField34(getCellValueAsString(row.getCell(33)));
                entity.setField35(getCellValueAsString(row.getCell(34)));
                entity.setField36(getCellValueAsString(row.getCell(35)));
                entity.setField37(getCellValueAsString(row.getCell(36)));
                entity.setField38(getCellValueAsString(row.getCell(37)));
                entity.setField39(getCellValueAsString(row.getCell(38)));
                entity.setField40(getCellValueAsString(row.getCell(39)));
               // batchData.add(entity);
                excelDataService.saveBatchData(entity);
            }
        }
        //buf
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("endtime.txt"))) {
            writer.write(Instant.now().toString());
                System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");

            e.printStackTrace();
        }
       // excelDataService.saveBatchData(batchData);
        // take current time and store in endtime.txt
    }


    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            default:
                return "";
        }
    }
}
