/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author anhba
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiemDanhBu {
    private int index;
    private int maNhanVien;
    private String hoVaTen;
    private String ngayDiemDanhBu;
}
