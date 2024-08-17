package com.myproject.Utils;

import com.myproject.Model.DiemDanh;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import com.myproject.UI.Application;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DateHelper {

    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formatter;
        // Định dạng ngày theo định dạng ngày-tháng-năm
        try {
            formatter = new SimpleDateFormat("dd-MM-yyyy");
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

        return formatter.format(date);
    }

    public static String formatDateYYYYMMDD(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formatter;
        // Định dạng ngày theo định dạng ngày-tháng-năm
        try {
            formatter = new SimpleDateFormat("yyyy-MM-dd");
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

        return formatter.format(date);
    }

    public static void checkDiemDanhThieu(Date ngaybd, Date ngaykt, List<DiemDanh> list) {
        if (ngaykt.after(new Date())) {
            ngaykt = new Date();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //System.out.println("ngaykt: "+DateHelper.convertNewDateToSQLDate(String.valueOf(ngaykt)));
        // Duyệt qua các ngày trong khoảng thời gian từ ngày bắt đầu đến ngày kết thúc
        for (Date date = ngaybd; !date.after(ngaykt); date = new Date(date.getTime() + 86400000)) {

            for (DiemDanh diemDanh : list) {
                //   System.out.println("ngay for: " + sdf.format(date));
                //   System.out.println("ngay kt: " + sdf.format(diemDanh.getNgayDiemDanh()));

                if (sdf.format(diemDanh.getNgayDiemDanh()).equals(sdf.format(date))) {

                    //break;
                } else {
                    //      // ds chua diem danh
                    System.out.println("Manv: " + diemDanh.getNhanVien().getMaNhanVien() + " hoten: " + diemDanh.getNhanVien().getHoVaTen());

                    //       System.out.println("chua diem danh: " + sdf.format(date));
                }
            }
            //   System.out.println("---------------------");

        }
    }

    public static String formatToYYYYMMDDDD(String inputDateStr) {
        // Chuỗi ngày đầu vào

        // Định dạng của chuỗi ngày đầu vào
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        // Định dạng đầu ra
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Phân tích chuỗi ngày đầu vào thành đối tượng Date
            Date date = inputFormat.parse(inputDateStr);
            // Định dạng lại đối tượng Date thành chuỗi ngày mới
            String outputDateStr = outputFormat.format(date);
            return outputDateStr;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String formatYYToDD(String inputDateStr) {
        // Chuỗi ngày đầu vào
        String ch = inputDateStr;
        if (checkFormatEEE(inputDateStr)) {
            // dung dinh dang

            inputDateStr = convertNewDateToSQLDate(ch);
        }
        // Định dạng của chuỗi ngày đầu vào
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        // Định dạng đầu ra
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-YYYY");

        try {
            // Phân tích chuỗi ngày đầu vào thành đối tượng Date
            Date date = inputFormat.parse(inputDateStr);
            // Định dạng lại đối tượng Date thành chuỗi ngày mới
            String outputDateStr = outputFormat.format(date);
            return outputDateStr;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String formatDDToYY(String inputDateStr) {
        // Chuỗi ngày đầu vào

        // Định dạng của chuỗi ngày đầu vào
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-YYYY");
        // Định dạng đầu ra
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Phân tích chuỗi ngày đầu vào thành đối tượng Date
            Date date = inputFormat.parse(inputDateStr);
            // Định dạng lại đối tượng Date thành chuỗi ngày mới
            String outputDateStr = outputFormat.format(date);
            return outputDateStr;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String convertNewDateToDate(String inputDateStr) {
        // Định dạng ngày đầu vào

        // Định dạng của đối tượng đầu vào
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        // Định dạng đầu ra
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

        try {
            // Phân tích ngày từ chuỗi
            Date date = inputFormat.parse(inputDateStr);
            // Định dạng lại ngày thành chuỗi mới
            String outputDateStr = outputFormat.format(date);

            return outputDateStr;
        } catch (ParseException e) {

            e.printStackTrace();
            return "";
        }
    }

    public static String convertNewDateToSQLDate(String inputDateStr) {
        // Định dạng ngày đầu vào

        // Định dạng của đối tượng đầu vào
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        // Định dạng đầu ra
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Phân tích ngày từ chuỗi
            Date date = inputFormat.parse(inputDateStr);
            // Định dạng lại ngày thành chuỗi mới
            String outputDateStr = outputFormat.format(date);

            return outputDateStr;
        } catch (ParseException e) {

            e.printStackTrace();
            return "";
        }
    }

    /**
     *
     * @return
     */
    public static boolean checkNgayNamGiua(String ngayNhapStr, String ngay1Str, String ngay2Str) {
        //yyyy-MM-dd

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate ngay1 = LocalDate.parse(ngay1Str, formatter);
        LocalDate ngay2 = LocalDate.parse(ngay2Str, formatter);
        LocalDate ngayNhap = LocalDate.parse(ngayNhapStr, formatter);

        boolean isInRange = checkDateInRange(ngayNhap, ngay1, ngay2);

        if (isInRange) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkDateInRange(LocalDate ngayNhap, LocalDate ngay1, LocalDate ngay2) {
        return (ngayNhap.isEqual(ngay1) || ngayNhap.isAfter(ngay1))
                && (ngayNhap.isEqual(ngay2) || ngayNhap.isBefore(ngay2));
    }

    public static String getStartOfMonth() {
        // Lấy lịch hiện tại
        Calendar calendar = Calendar.getInstance();
        // Đặt ngày về ngày đầu tiên của tháng
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        // Lấy đối tượng Date
        Date startOfMonth = calendar.getTime();
        // Định dạng ngày theo ngày-tháng-năm
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(startOfMonth);
    }

    public static String getStartOfMonthByDay(int ngay) {
        // Lấy lịch hiện tại
        Calendar calendar = Calendar.getInstance();
        // Đặt ngày về ngày đầu tiên của tháng
        // Trừ đi 1 tháng
    calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, ngay);

        // Lấy đối tượng Date
        Date startOfMonth = calendar.getTime();
        // Định dạng ngày theo ngày-tháng-năm
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(startOfMonth);
    }

    public static String getEndOfMonth() {
        // Lấy lịch hiện tại
        Calendar calendar = Calendar.getInstance();
        // Đặt ngày về ngày cuối cùng của tháng
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        // Lấy đối tượng Date
        Date endOfMonth = calendar.getTime();
        // Định dạng ngày theo ngày-tháng-năm
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(endOfMonth);
    }

    public static String getEndOfMonthByDay(int ngay) {
        // Lấy lịch hiện tại
        Calendar calendar = Calendar.getInstance();
        // Đặt ngày về ngày cuối cùng của tháng
//        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, ngay);
        // Lấy đối tượng Date
        Date endOfMonth = calendar.getTime();
        // Định dạng ngày theo ngày-tháng-năm
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(endOfMonth);
    }

    public static Date convertStringToDate(String dateStr) {
        // Định dạng ngày-tháng-năm
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            // Chuyển đổi chuỗi sang Date
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date convertStringYYYYMMDDToDate(String dateStr) {
        // Định dạng ngày-tháng-năm
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // Chuyển đổi chuỗi sang Date
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean checkFormatEEE(String dateStr) {
        String DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setLenient(false); // Đặt lenient thành false để kiểm tra chính xác định dạng

        try {
            dateFormat.parse(dateStr);
            return true; // Nếu parse thành công, định dạng hợp lệ
        } catch (ParseException e) {
            // Nếu parse thất bại, định dạng không hợp lệ
            return false;
        }
    }

    public static void writeFile(Properties properties, String file) {
        try {
            // Đường dẫn đến tệp tin properties trong thư mục gốc của project
            String filePath = System.getProperty("user.dir") + "/" + file + ".properties";

            // Tạo đối tượng File với đường dẫn filePath
            File propertiesFile = new File(filePath);

            // Nếu tệp không tồn tại, tạo mới tệp
            if (!propertiesFile.exists()) {
                if (propertiesFile.createNewFile()) {
                    System.out.println("Tạo tệp tin mới: " + filePath);
                } else {
                    System.out.println("Không thể tạo tệp tin mới: " + filePath);
                    return;
                }
            }

            // Ghi dữ liệu vào tệp tin properties
            try (OutputStream outputstream = new FileOutputStream(propertiesFile)) {
                properties.store(outputstream, "File Created/Updated");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readFile(String key, String file) {
        try {
            // Đường dẫn đến tệp tin properties trong thư mục gốc của project
            String filePath = System.getProperty("user.dir") + "/" + file + ".properties";

            // Kiểm tra sự tồn tại của tệp tin
            File propertiesFile = new File(filePath);
            if (!propertiesFile.exists()) {
                return "err";
            } else {
                Properties properties = new Properties();
                try (FileInputStream ip = new FileInputStream(propertiesFile)) {
                    properties.load(ip);
                    return properties.getProperty(key);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static long calculateDaysBetween(Date startDate, Date endDate) {
        long diffInMillies = endDate.getTime() - startDate.getTime();
        return (diffInMillies / (1000 * 60 * 60 * 24)) + 1;
    }

    public static String getFilePathToSave(String tenfile) {
        try {
            // Đường dẫn tới thư mục gốc của dự án
            String projectPath = System.getProperty("user.dir");

            // Đường dẫn tới thư mục "excel"
            Path excelDir = Paths.get(projectPath, "excel");

            // Lấy ngày hiện tại và định dạng
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'ngay-'dd-MM-yyyy");
            String formattedDate = today.format(formatter);

            // Đường dẫn tới thư mục ngày
            Path dateDir = excelDir.resolve(formattedDate);

            // Tạo thư mục "excel" nếu chưa tồn tại
            if (!Files.exists(excelDir)) {
                Files.createDirectories(excelDir);
            }
            // Tạo thư mục ngày nếu chưa tồn tại
            if (!Files.exists(dateDir)) {
                Files.createDirectories(dateDir);
            }

            //System.out.println(String.valueOf(dateDir)+"\\"+tenfile);
            if (dateDir != null) {
                return String.valueOf(dateDir) + "\\" + tenfile;
            } else {
                return "err";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "err";

        }
    }
    
        public static String getFilePathToOpen() {
        try {
            // Đường dẫn tới thư mục gốc của dự án
            String projectPath = System.getProperty("user.dir");

            // Đường dẫn tới thư mục "excel"
            Path excelDir = Paths.get(projectPath, "excel");

            // Lấy ngày hiện tại và định dạng
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'ngay-'dd-MM-yyyy");
            String formattedDate = today.format(formatter);

            // Đường dẫn tới thư mục ngày
            Path dateDir = excelDir.resolve(formattedDate);

            

            //System.out.println(String.valueOf(dateDir)+"\\"+tenfile);
            if (dateDir != null) {
                return String.valueOf(dateDir);
            } else {
                return "err";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "err";

        }
    }

    public static String getFilePathToRead() {
        try {
            // Đường dẫn tới thư mục gốc của dự án
            String projectPath = System.getProperty("user.dir");

            // Đường dẫn tới thư mục "excel"
            Path excelDir = Paths.get(projectPath, "excel");

            // Lấy ngày hiện tại và định dạng
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'ngay-'dd-MM-yyyy");
            String formattedDate = today.format(formatter);

            // Đường dẫn tới thư mục ngày
            Path dateDir = excelDir.resolve(formattedDate);

            // Tạo thư mục "excel" nếu chưa tồn tại
            if (!Files.exists(excelDir)) {
                Files.createDirectories(excelDir);
            }
            // Tạo thư mục ngày nếu chưa tồn tại
            if (!Files.exists(dateDir)) {
                Files.createDirectories(dateDir);
            }

            //System.out.println(String.valueOf(dateDir));
            if (dateDir != null) {
                return String.valueOf(dateDir);
            } else {
                return "err";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "err";

        }
    }

    public static void main(String[] args) {
        //  System.out.println("j:"+readFile("tiennghi", "tiennghi"));
    }
}
