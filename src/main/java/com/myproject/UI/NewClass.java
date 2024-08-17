/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.UI;

import com.myproject.Utils.DateHelper;
import java.util.Properties;

/**
 *
 * @author anhba
 */
public class NewClass {
    public static void main(String[] args) {
        Properties properties = new Properties();
                properties.setProperty("luong", "3000000");

                DateHelper.writeFile(properties, "luong2");
    }
}
