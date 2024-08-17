package com.myproject.Model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "DiemDanh")
public class DiemDanh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maDiemDanh;

    @ManyToOne
    @JoinColumn(name = "maNhanVien",referencedColumnName = "maNhanVien")
    private NhanVien nhanVien;
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayDiemDanh;
    private boolean trangThai;
}
