/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.myproject.Form;

import com.myproject.CNC.ExcelExporter;
import com.myproject.DAO.NhanVienDao;
import com.myproject.Model.ChamCong;
import com.myproject.Model.NhanVien;
import com.myproject.Model.SanPham;
import com.myproject.Utils.DateHelper;
import com.myproject.Utils.MessageHelper;
import com.myproject.swing.ScrollBar;
import java.awt.Color;
import java.awt.Dimension;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC
 */
public class Form_THONGKE extends javax.swing.JPanel {

    private NhanVienDao daoNV = null;

    /**
     * Creates new form Form_THONGKE1
     */
    public Form_THONGKE() {
        daoNV = new NhanVienDao();
        initComponents();
        spTable.setVerticalScrollBar(new ScrollBar());
        dateChooser1.setLocale(new Locale("vi", "VN"));
        dateChooser2.setLocale(new Locale("vi", "VN"));
        loadNgay();
        loadNhanVien();
        loadLuong();
    }

    private void loadNgay() {
        dateChooser2.setDate(DateHelper.convertStringToDate(DateHelper.getStartOfMonthByDay(28)));
        dateChooser1.setDate(DateHelper.convertStringToDate(DateHelper.getEndOfMonthByDay(28)));
        // load luong mac dinh
        String luong = "3000000";
        String vipham = "5";
//        String tiennghi = "115000";
        String tienthue = "55";
        try {
            luong = DateHelper.readFile("luong", "luong");
            if (luong == null || luong == "" || luong == "err") {
                Properties properties = new Properties();
                properties.setProperty("luong", "3000000");

                DateHelper.writeFile(properties, "luong");
                luong = DateHelper.readFile("luong", "luong");
            }

            vipham = DateHelper.readFile("vipham", "vipham");
            if (vipham == null || vipham == "" || vipham == "err") {
                Properties properties = new Properties();
                properties.setProperty("vipham", "50000");

                DateHelper.writeFile(properties, "vipham");
                vipham = DateHelper.readFile("vipham", "vipham");
            }

            tienthue = DateHelper.readFile("tienthue", "tienthue");
            if (tienthue == null || tienthue == "" || tienthue == "err") {
                Properties properties = new Properties();
                properties.setProperty("tienthue", "115000");

                DateHelper.writeFile(properties, "tienthue");
                tienthue = DateHelper.readFile("tienthue", "tienthue");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        textField2.setText(luong);
        textField1.setText(vipham);
        // System.out.println("hha: " + tiennghi);
        jLabel15.setText("Tiền Thuế TNCN: " + currencyFormat.format(Double.valueOf(tienthue)));
        //jLabel19.setText("Trừ ngày nghỉ: " + "(" + currencyFormat.format(Double.valueOf(tiennghi)) + "/Ngày" + ")");
    }

    private int getMaNhanVienFromFullName(String fullname) {
        int start = 0;
        int end = 0;
        int manv = -1;
        start = fullname.indexOf("[");
        end = fullname.indexOf("]");
        String ss = fullname.substring(start, end + 1);
        manv = Integer.parseInt(ss.replace("[", "").replace("]", ""));
        return manv;
    }

    private void loadNhanVien() {
        customComboBox1.removeAllItems();
        List<NhanVien> list = daoNV.getAllNhanViens();
        if (list != null) {
            for (NhanVien nhanVien : list) {
                customComboBox1.addItem("[" + nhanVien.getMaNhanVien() + "] " + nhanVien.getHoVaTen());
            }
        }

    }

    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    int solan;

    private void loadLuong() {
        String nhanvien = "";
        Date ngaybd = dateChooser2.getDate();
        Date ngaykt = dateChooser1.getDate();

        try {
            nhanvien = String.valueOf(customComboBox1.getSelectedItem());
            if (nhanvien == null || !nhanvien.contains("[") || !nhanvien.contains("]")) {
                return;
            }

            int manv = getMaNhanVienFromFullName(nhanvien);
            NhanVien nhanVien2 = daoNV.getNhanVienById(manv);

            Double luong = 0.0;
            String luongcung = textField2.getText().trim();
            if (!luongcung.equals("err") || luongcung != null) {
                luong = Double.valueOf(luongcung);// luong cung
            }

            List<SanPham> list = daoNV.getAllSanPhamCuaNhanVienBetWeenDay(nhanVien2.getMaNhanVien(), ngaybd, ngaykt);
            // lay ds sp
//            List<SanPham> listSP = new ArrayList<>();
//            for (ChamCong chamCong : list) {
//                if (chamCong.isTrangThai()) {
//                    listSP.add(chamCong.getSanPham());
//                }
//            }

            DefaultTableModel model = (DefaultTableModel) tbl_NhanVien.getModel();
            model.setRowCount(0);
            Double thanhtien = 0.0;
            Double giaSP = 0.0;
            float soCong = 1;
            int viPham = 0;
            Double tienViPham = 0.0;
            Double tongTienTp = 0.0;
            Double tongThanhVien = 0.0;// tien thuc nhan
            Double tongTienNhan = 0.0;// chua tru tien vp
            if (!String.valueOf(textField1.getText()).equals("err") || textField1.getText() != null) {
                tienViPham = Double.valueOf(String.valueOf(textField1.getText()));
            }

            for (SanPham sanPham : list) {

                giaSP = sanPham.getGiaSP(); // Giá của từng sản phẩm có thể lấy ra thông tin
                soCong = sanPham.getSoCong();

                thanhtien = (giaSP);
                tongTienNhan += thanhtien;
                viPham = sanPham.getViPham();
                tongTienTp += (viPham * tienViPham);
                // thanhtien = thanhtien - (viPham * tienViPham);
                // Thành tiền dựa vào các sản phẩm đã hoàn thành của nhân viên
                ///

                ///
                tongThanhVien += thanhtien;
                // DateHelper.readFile("luong", "luong"); Lấy ra lương cứng

                String ngayGiao = "";
                //  String ngayHoanThanh = "";

                if (sanPham.getNgayGiao() != null) {
                    ngayGiao = DateHelper
                            .formatYYToDD(String.valueOf(sanPham.getNgayGiao()));

                }

                tbl_NhanVien.addRow(new Object[]{
                    sanPham.getMaSanPham(),
                    sanPham.getTenSanPham(),
                    sanPham.getTapPhim(),
                    sanPham.getViPham(),
                    ngayGiao,
                    sanPham.getSoCong(),
                    currencyFormat.format(thanhtien)});
            }

            loadThongTin(tongTienTp, tongTienNhan, tongThanhVien, luong, ngaybd, ngaykt, nhanVien2.getMaNhanVien());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    private void loadThongTin(Double tienvp, Double tongTienNhan, Double thucNhan, Double luong, Date ngaybd,
            Date ngaykt, int manv) {
        String tienThue = "100000";
       
        //Double trutiennghi = 0.0;
        int songaydd = daoNV.getNgayDiLamFromDate(manv, ngaybd, ngaykt);
        // System.out.println("ngay diem danh: " + songaydd);
        int tongsongay = Integer.valueOf(String.valueOf(DateHelper.calculateDaysBetween(ngaybd, ngaykt)));
        int songaydanghi = tongsongay - songaydd;
        // System.out.println("Tong so ngay: " + tongsongay);
       

        tienThue = DateHelper.readFile("tienthue", "tienthue");
        if (tienThue == null || tienThue == "") {
            Properties properties = new Properties();
            properties.setProperty("tienthue", "100000");

            DateHelper.writeFile(properties, "thue");
            tienThue = DateHelper.readFile("tienthue", "tienthue");
        }
        if (!tienThue.equals("err") || tienThue != null) {
            jLabel15.setText("Tiền Thuế TNCN: " + currencyFormat.format(Double.valueOf(tienThue)));
        }
        //trutiennghi = songaydanghi * Double.valueOf(tiennghi);
        jLabel14.setText("Lương Tổng: " + String.valueOf(currencyFormat.format(tongTienNhan + luong)));
        jLabel16.setText("Tiền Vi Phạm: " + String.valueOf(currencyFormat.format(tienvp)));
        String thue = DateHelper.readFile("tienthue", "tienthue");
        //jLabel20.setText("Số ngày đã nghỉ: " + songaydanghi);
        if (!thue.equals("err") || thue != null) {
            jLabel17.setText("Tiền Thực Nhận: "
                    + currencyFormat.format((luong + thucNhan) - Double.valueOf(thue) - tienvp ));
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        p_search = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnAdd = new com.myproject.swing.Button();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        customComboBox1 = new com.myproject.swing.CustomComboBox();
        p_total = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        button3 = new com.myproject.swing.Button();
        jPanel3 = new javax.swing.JPanel();
        p_date = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dateChooser1 = new com.myproject.swing.DateChooser();
        dateChooser2 = new com.myproject.swing.DateChooser();
        textField1 = new com.myproject.swing.TextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        textField2 = new com.myproject.swing.TextField();
        button2 = new com.myproject.swing.Button();
        p_table = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        spTable = new javax.swing.JScrollPane();
        tbl_NhanVien = new com.myproject.swing.Table();
        jPanel11 = new javax.swing.JPanel();
        btn_phong = new javax.swing.JPanel();
        btn_setSizeTable = new javax.swing.JLabel();
        button1 = new com.myproject.swing.Button();
        customComboBox2 = new com.myproject.swing.CustomComboBox();

        setBackground(new java.awt.Color(204, 255, 204));
        setPreferredSize(new java.awt.Dimension(1266, 720));
        setLayout(new java.awt.BorderLayout());

        p_search.setBackground(new java.awt.Color(227, 224, 224));
        p_search.setPreferredSize(new java.awt.Dimension(80, 100));
        p_search.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(227, 224, 224));
        jPanel4.setPreferredSize(new java.awt.Dimension(142, 100));

        btnAdd.setBackground(new java.awt.Color(227, 224, 224));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/myproject/Image/reload.png"))); // NOI18N
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        p_search.add(jPanel4, java.awt.BorderLayout.LINE_END);

        jPanel5.setBackground(new java.awt.Color(227, 224, 224));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 142, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        p_search.add(jPanel5, java.awt.BorderLayout.LINE_START);

        jPanel6.setBackground(new java.awt.Color(227, 224, 224));
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        jPanel7.setBackground(new java.awt.Color(227, 224, 224));

        customComboBox1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        customComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(customComboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 970, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(customComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel7);

        p_search.add(jPanel6, java.awt.BorderLayout.CENTER);

        add(p_search, java.awt.BorderLayout.PAGE_START);

        p_total.setBackground(new java.awt.Color(227, 224, 224));
        p_total.setPreferredSize(new java.awt.Dimension(1266, 240));

        jPanel10.setBackground(new java.awt.Color(227, 224, 224));
        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 153, 153), null));

        jLabel14.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel14.setText("Lương Tổng");

        jLabel15.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel15.setText("Tiền Thuế TNCN");

        jLabel16.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel16.setText("Tiền Vi Phạm");

        jLabel17.setFont(new java.awt.Font("Segoe UI Semilight", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 0, 51));
        jLabel17.setText("Tiền Thực Nhận");

        jLabel18.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel18.setText("Nhập tiền thuế");

        button3.setBackground(new java.awt.Color(0, 0, 0));
        button3.setText("Lưu");
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel16)
                    .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 942, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel17)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel18))
                    .addComponent(button3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout p_totalLayout = new javax.swing.GroupLayout(p_total);
        p_total.setLayout(p_totalLayout);
        p_totalLayout.setHorizontalGroup(
            p_totalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_totalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(231, Short.MAX_VALUE))
        );
        p_totalLayout.setVerticalGroup(
            p_totalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_totalLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        add(p_total, java.awt.BorderLayout.PAGE_END);

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));
        jPanel3.setLayout(new java.awt.BorderLayout());

        p_date.setBackground(new java.awt.Color(227, 224, 224));
        p_date.setPreferredSize(new java.awt.Dimension(1266, 60));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel1.setText("Từ");

        jLabel2.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel2.setText("Đến");

        dateChooser1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        dateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateChooser1PropertyChange(evt);
            }
        });

        dateChooser2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        dateChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateChooser2PropertyChange(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel4.setText("Vi phạm/lần");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel5.setText("Lương");

        button2.setBackground(new java.awt.Color(0, 0, 0));
        button2.setText("Lưu");
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout p_dateLayout = new javax.swing.GroupLayout(p_date);
        p_date.setLayout(p_dateLayout);
        p_dateLayout.setHorizontalGroup(
            p_dateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_dateLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 234, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textField2, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        p_dateLayout.setVerticalGroup(
            p_dateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_dateLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(p_dateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(p_dateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(dateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                        .addComponent(dateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addGroup(p_dateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textField2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(p_date, java.awt.BorderLayout.PAGE_START);

        p_table.setBackground(new java.awt.Color(227, 224, 224));
        p_table.setPreferredSize(new java.awt.Dimension(1266, 300));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semilight", 1, 24)); // NOI18N
        jLabel3.setText("THỐNG KÊ");

        spTable.setBorder(null);

        tbl_NhanVien.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        tbl_NhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ SP", "TÊN PHIM", "TẬP", "VI PHẠM", "NGÀY GIAO", "SỐ CÔNG", "THÀNH TIỀN"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spTable.setViewportView(tbl_NhanVien);

        jPanel11.setBackground(new java.awt.Color(227, 224, 224));

        btn_phong.setBackground(new java.awt.Color(227, 224, 224));
        btn_phong.setLayout(new java.awt.BorderLayout());

        btn_setSizeTable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_setSizeTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/myproject/Image/maximize.png"))); // NOI18N
        btn_setSizeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_setSizeTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_setSizeTableMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_setSizeTableMouseExited(evt);
            }
        });
        btn_phong.add(btn_setSizeTable, java.awt.BorderLayout.CENTER);

        button1.setBackground(new java.awt.Color(0, 0, 0));
        button1.setText("Xuất Excel");
        button1.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_phong, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_phong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        customComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Theo nhân viên được chọn", "Tất cả" }));
        customComboBox2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        customComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customComboBox2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout p_tableLayout = new javax.swing.GroupLayout(p_table);
        p_table.setLayout(p_tableLayout);
        p_tableLayout.setHorizontalGroup(
            p_tableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_tableLayout.createSequentialGroup()
                .addGroup(p_tableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(p_tableLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(spTable, javax.swing.GroupLayout.DEFAULT_SIZE, 1254, Short.MAX_VALUE))
                    .addGroup(p_tableLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(customComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        p_tableLayout.setVerticalGroup(
            p_tableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_tableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(p_tableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(p_tableLayout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(p_tableLayout.createSequentialGroup()
                        .addGroup(p_tableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(p_tableLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel3))
                            .addGroup(p_tableLayout.createSequentialGroup()
                                .addComponent(customComboBox2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(2, 2, 2)))
                        .addComponent(spTable, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel3.add(p_table, java.awt.BorderLayout.CENTER);

        add(jPanel3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void customComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_customComboBox2ActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        loadLuong();
        loadNhanVien();

    }// GEN-LAST:event_btnAddActionPerformed

    private void dateChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_dateChooser2PropertyChange
        // TODO add your handling code here:
        loadLuong();
    }// GEN-LAST:event_dateChooser2PropertyChange

    private void dateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_dateChooser1PropertyChange
        // TODO add your handling code here:
        loadLuong();
    }// GEN-LAST:event_dateChooser1PropertyChange

    private void customComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_customComboBox1ActionPerformed
        // TODO add your handling code here:
        loadLuong();
    }// GEN-LAST:event_customComboBox1ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_button2ActionPerformed
        try {
            Properties properties = new Properties();
            String svipham = String.valueOf(textField1.getText()).trim();
            String sluong = String.valueOf(textField2.getText()).trim();

            if (sluong == "" || sluong == null || sluong.isEmpty()) {
//                properties.setProperty("luong", "3000000");
//                DateHelper.writeFile(properties, "luong");
                JOptionPane.showMessageDialog(null, "Không được để trống", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            } else {
                properties.setProperty("luong", sluong);

                DateHelper.writeFile(properties, "luong");
            }

            if (svipham == "" || svipham == null || svipham.isEmpty()) {
//                properties.setProperty("vipham", "50000");
//                DateHelper.writeFile(properties, "vipham");
                JOptionPane.showMessageDialog(null, "Lưu thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            } else {
                properties.setProperty("vipham", svipham);
                DateHelper.writeFile(properties, "vipham");
            }

            JOptionPane.showMessageDialog(null, "Lưu thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            String luong = DateHelper.readFile("luong", "luong");
            String vipham = DateHelper.readFile("vipham", "vipham");
            textField1.setText(vipham);
            textField2.setText(luong);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadLuong();
    }// GEN-LAST:event_button2ActionPerformed

    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_button3ActionPerformed
        try {
            Properties properties = new Properties();
            String tienThue = String.valueOf(jTextField1.getText()).trim();

            if (tienThue == "" || tienThue == null || tienThue.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Không được để trống", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
                //    properties.setProperty("tienthue", "100000");
                //    DateHelper.writeFile(properties, "tienthue");
            } else {
                properties.setProperty("tienthue", tienThue);
                DateHelper.writeFile(properties, "tienthue");
            }

            JOptionPane.showMessageDialog(null, "Lưu thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            String tienThue2 = DateHelper.readFile("tienthue", "tienthue");

            jLabel15.setText("Tiền Thuế TNCN: " + currencyFormat.format(Double.valueOf(tienThue2)));

        } catch (Exception e) {
            e.printStackTrace();
        }
        loadLuong();
    }// GEN-LAST:event_button3ActionPerformed

    private void btn_setSizeTableMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btn_setSizeTableMouseClicked

        int newHeight;

        if (p_date.isVisible() && p_search.isVisible() && p_total.isVisible()) {
            p_date.setVisible(false);
            p_search.setVisible(false);
            p_total.setVisible(false);
            newHeight = 720;
        } else {
            p_date.setVisible(true);
            p_search.setVisible(true);
            p_total.setVisible(true);

            newHeight = 330;

        }

        setSizeTable(p_table, getWidth(), newHeight);
    }// GEN-LAST:event_btn_setSizeTableMouseClicked

    private void btn_setSizeTableMouseEntered(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btn_setSizeTableMouseEntered
        changecolor(btn_phong, new Color(204, 204, 204));
    }// GEN-LAST:event_btn_setSizeTableMouseEntered

    private void btn_setSizeTableMouseExited(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btn_setSizeTableMouseExited
        changecolor(btn_phong, new Color(227, 224, 224));
    }
    

    private void button4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_button4ActionPerformed
        try {
            Properties properties = new Properties();
            //String tiennghi = String.valueOf(textField3.getText()).trim();

//            if (tiennghi == "" || tiennghi == null || tiennghi.isEmpty()) {
//
//                JOptionPane.showMessageDialog(null, "Không được để trống", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//                return;
//            } else {
//                properties.setProperty("tiennghi", tiennghi);
//                DateHelper.writeFile(properties, "tiennghi");
//            }

            JOptionPane.showMessageDialog(null, "Lưu thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    
            //System.out.println("gg: " + tiennghi2);
//            jLabel19.setText(
//                    "Trừ ngày nghỉ: " + "(" + currencyFormat.format(Double.valueOf(tiennghi2)) + "/Ngày" + ")");

        } catch (Exception e) {
            e.printStackTrace();
        }
        loadLuong();
    }// GEN-LAST:event_button4ActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_button1ActionPerformed
        Date ngaybd = dateChooser2.getDate();
        Date ngaykt = dateChooser1.getDate();
        List<NhanVien> listALL = daoNV.getAllNhanViens();
        Double luongCung = Double.valueOf(DateHelper.readFile("luong", "luong"));
        Double tienThue = Double.valueOf(DateHelper.readFile("tienthue", "tienthue"));
        Double tienViPham2 = Double.valueOf(DateHelper.readFile("vipham", "vipham"));
        
        String tenNVToCombo = String.valueOf(customComboBox1.getSelectedItem());
        List<Object[]> dataForExcel = new ArrayList<>();
        Double luongtong = 0.0;
        Double tongTienSP = 0.0;
        Double tongTienThue = 0.0;
        Double tongTienViPham = 0.0;
        Double tongTienThucNhan = 0.0;
        Double songaynghi = 0.0;
        String filePath = "";
        int manv = -1;
        if (customComboBox2.getSelectedIndex() == 0) {
            if (tenNVToCombo.contains("[") && tenNVToCombo.contains("]")) {
                manv = getMaNhanVienFromFullName(tenNVToCombo);
            }
        }

        for (NhanVien nhanVien : listALL) {
            if (manv != -1) {// xuat theo nv
                if (manv != nhanVien.getMaNhanVien()) {
                    continue;
                }
            }
            dataForExcel = new ArrayList<>();
            Double tongTien = 0.0;
            Double tongTienVP = 0.0;
            Double tongTienNghi = 0.0;

            int songaydd = daoNV.getNgayDiLamFromDate(nhanVien.getMaNhanVien(), ngaybd, ngaykt);
            int tongsongay = Integer.valueOf(String.valueOf(DateHelper.calculateDaysBetween(ngaybd, ngaykt)));
            int songaydanghi = tongsongay - songaydd;
            songaynghi = Double.valueOf(songaydanghi);
            List<SanPham> list22 = daoNV.getAllSanPhamCuaNhanVienBetWeenDay(nhanVien.getMaNhanVien(), ngaybd, ngaykt);
            for (SanPham sanPham : list22) {

                //  String tennv = chamCong1.getNhanVien().getHoVaTen();
                //   String TenNV = tenNVToCombo.substring(tenNVToCombo.indexOf("]") + 2);
                String tensp = sanPham.getTenSanPham();
                if (nhanVien.getMaNhanVien() == sanPham.getNhanVien().getMaNhanVien()) {
                    int tap = sanPham.getTapPhim();

                    Double giaCuaSP = sanPham.getGiaSP();
                    tongTien += giaCuaSP;
                    tongTienSP = tongTien;
                    tongTienVP += sanPham.getViPham() * tienViPham2;
                    dataForExcel.add(new Object[]{tensp, String.valueOf(tap), sanPham.getSoCong() ,giaCuaSP.toString()});
                }

            }

           
            tongTien = luongCung + tongTienSP;
            luongtong = tongTien;
            tongTien -= tienThue + tongTienVP + tongTienNghi;

            tongTienThue = tienThue;
            tongTienViPham += tongTienVP;
            tongTienThucNhan = tongTien;

            if (dataForExcel.isEmpty()) {
                dataForExcel.add(new Object[]{"Trống", "Trống", "Trống", "Trống"});
            }

            if (!dataForExcel.isEmpty()) {
                String[] columnHeaders = new String[]{"Tên phim", "Tập", "Số công", "Giá tiền"};
                JTable table = new JTable((Object[][]) dataForExcel.toArray(new Object[0][0]), columnHeaders);
                ExcelExporter ex = new ExcelExporter();

                try {
                    String var10002 = nhanVien.getHoVaTen();
                    String var10003 = nhanVien.getEmail();
                    String var10004 = nhanVien.getSoDienThoai();
                    double var10005 = tongTienSP;
                    double var10006 = luongtong;
                    double var10007 = tongTienThue;
                    double var10008 = tongTienViPham;
                    double var10009 = luongCung;
                    double var10010 = tongTienThucNhan;
                    //double var10011 = (double) songaydanghi;
                    
                    int var10013 = nhanVien.getMaNhanVien();
                    ex.exportTable(table, var10002, var10003, var10004, var10005, var10006, var10007, var10008, var10009, var10010, DateHelper.getFilePathToSave(var10013 + "-" + nhanVien.getHoVaTen()));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Xuất file Excel không thành công!", "Lỗi", 0);
                    e.printStackTrace();
                    return;
                }
            }
        }
        //JOptionPane.showMessageDialog(null, "Xuất file Excel thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        MessageHelper.showSuccessDialog(DateHelper.getFilePathToOpen());
    }// GEN-LAST:event_button1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.myproject.swing.Button btnAdd;
    private javax.swing.JPanel btn_phong;
    private javax.swing.JLabel btn_setSizeTable;
    private com.myproject.swing.Button button1;
    private com.myproject.swing.Button button2;
    private com.myproject.swing.Button button3;
    private com.myproject.swing.CustomComboBox customComboBox1;
    private com.myproject.swing.CustomComboBox customComboBox2;
    private com.myproject.swing.DateChooser dateChooser1;
    private com.myproject.swing.DateChooser dateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel p_date;
    private javax.swing.JPanel p_search;
    private javax.swing.JPanel p_table;
    private javax.swing.JPanel p_total;
    private javax.swing.JScrollPane spTable;
    private com.myproject.swing.Table tbl_NhanVien;
    private com.myproject.swing.TextField textField1;
    private com.myproject.swing.TextField textField2;
    // End of variables declaration//GEN-END:variables

    public void setSizeTable(JPanel panel, int width, int height) {
        panel.setPreferredSize(new Dimension(width, height));
        panel.revalidate();
        panel.repaint();
    }

    public void changecolor(JPanel hover, Color rand) {
        hover.setBackground(rand);
    }
}
