package com.myproject.Model;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SanPham")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maSanPham;

    private String tenSanPham;
    private int tapPhim;// số nguyên

    private double giaSP;

    private float thoiLuong; // Đơn vị: phút
    private float soCong;
    private int viPham;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date ngayGiao;

    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChamCong> chamCongList;
    
    @ManyToOne
    @JoinColumn(name = "maNhanVien", referencedColumnName = "maNhanVien")
    private NhanVien nhanVien;

}
