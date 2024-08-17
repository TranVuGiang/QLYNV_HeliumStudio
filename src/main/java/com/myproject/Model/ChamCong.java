package com.myproject.Model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "ChamCong")
public class ChamCong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maChamCong;

    @ManyToOne
    @JoinColumn(name = "maNhanVien", referencedColumnName = "maNhanVien")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "maSanPham", referencedColumnName = "maSanPham")
    private SanPham sanPham;

    private boolean trangThai;
    private int viPham;
       private Date ngayGiao;
    private Date ngayHoanThanh;
}
