
package com.myproject.Model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "NhanVien")
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maNhanVien;

    private String hoVaTen;
    private int gioiTinh;
    private String soDienThoai;
    private String email;
    private String hinh;

    @OneToMany(mappedBy = "nhanVien")
    private List<DiemDanh> diemDanhs;
   
}

