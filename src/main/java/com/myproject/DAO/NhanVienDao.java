package com.myproject.DAO;

import com.myproject.Model.ChamCong;
import com.myproject.Model.NhanVien;
import com.myproject.Model.SanPham;
import com.myproject.Utils.EntityManagerFStory;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class NhanVienDao {
   private EntityManager entityManager = EntityManagerFStory.getEntityManagerFactory().createEntityManager();

   public boolean addNhanVien(NhanVien nhanVien) {
      EntityTransaction transaction = this.entityManager.getTransaction();
      boolean added = false;

      try {
         transaction.begin();
         this.entityManager.persist(nhanVien);
         transaction.commit();
         added = true;
      } catch (Exception var5) {
         if (transaction.isActive()) {
            transaction.rollback();
         }

         var5.printStackTrace();
      }

      return added;
   }

   public boolean updateNhanVien(NhanVien nhanVien) {
      EntityTransaction transaction = this.entityManager.getTransaction();
      boolean updated = false;

      try {
         transaction.begin();
         this.entityManager.merge(nhanVien);
         transaction.commit();
         updated = true;
      } catch (Exception var5) {
         if (transaction.isActive()) {
            transaction.rollback();
         }

         var5.printStackTrace();
      }

      return updated;
   }

   public boolean deleteAllNhanVienData(int maNV) {
      EntityTransaction transaction = this.entityManager.getTransaction();

      try {
         transaction.begin();
         this.entityManager.createQuery("DELETE FROM DiemDanh dd WHERE dd.nhanVien.maNhanVien = :maNV").setParameter("maNV", maNV).executeUpdate();
         this.entityManager.createQuery("DELETE FROM ChamCong cc WHERE cc.nhanVien.maNhanVien = :maNV").setParameter("maNV", maNV).executeUpdate();
         this.entityManager.createQuery("DELETE FROM NhanVien nv WHERE nv.maNhanVien = :maNV").setParameter("maNV", maNV).executeUpdate();
         transaction.commit();
         return true;
      } catch (Exception var4) {
         if (transaction.isActive()) {
            transaction.rollback();
         }

         var4.printStackTrace();
         return false;
      }
   }

   public boolean deleteNhanVien(int maNhanVien) {
      EntityTransaction transaction = this.entityManager.getTransaction();
      boolean deleted = false;

      try {
         transaction.begin();
         NhanVien nhanVien = (NhanVien)this.entityManager.find(NhanVien.class, maNhanVien);
         if (nhanVien != null) {
            this.entityManager.remove(nhanVien);
            transaction.commit();
            deleted = true;
         } else {
            System.out.println("Không tìm thấy nhân viên với mã: " + maNhanVien);
         }
      } catch (Exception var5) {
         if (transaction.isActive()) {
            transaction.rollback();
         }

         var5.printStackTrace();
      }

      return deleted;
   }

   public NhanVien getNhanVienById(int maNhanVien) {
      return (NhanVien)this.entityManager.find(NhanVien.class, maNhanVien);
   }

//   public List<ChamCong> getAllChamCongCuaNhanVienBetWeenDay(int manv, Date ngaybd, Date ngaykt) {
//      TypedQuery<ChamCong> query = this.entityManager.createQuery("SELECT cc FROM ChamCong cc where cc.nhanVien.maNhanVien = :manv and cc.ngayGiao between :ngaybd and :ngaykt", ChamCong.class);
//      query.setParameter("manv", manv);
//      query.setParameter("ngaybd", ngaybd);
//      query.setParameter("ngaykt", ngaykt);
//      return query.getResultList();
//   }
   
      public List<SanPham> getAllSanPhamCuaNhanVienBetWeenDay(int manv, Date ngaybd, Date ngaykt) {
      TypedQuery<SanPham> query = this.entityManager.createQuery("SELECT sp FROM SanPham sp where sp.nhanVien.maNhanVien = :manv and sp.ngayGiao between :ngaybd and :ngaykt", SanPham.class);
      query.setParameter("manv", manv);
      query.setParameter("ngaybd", ngaybd);
      query.setParameter("ngaykt", ngaykt);
      return query.getResultList();
   }

   public List<NhanVien> getAllNhanViens() {
      TypedQuery<NhanVien> query = this.entityManager.createQuery("SELECT nv FROM NhanVien nv", NhanVien.class);
      return query.getResultList();
   }

   public NhanVien getNhanVienTonTai(String tennv) {
      TypedQuery<NhanVien> query = this.entityManager.createQuery("SELECT nv FROM NhanVien nv where nv.hoVaTen= :tennv", NhanVien.class);
      query.setParameter("tennv", tennv);
      return query.getResultList().size() > 0 ? (NhanVien)query.getResultList().get(0) : null;
   }

   public List<NhanVien> getAllNhanVienNotIn(List<Integer> list) {
      TypedQuery<NhanVien> query = this.entityManager.createQuery("SELECT d FROM NhanVien d WHERE d.maNhanVien NOT IN :ids", NhanVien.class);
      query.setParameter("ids", list);
      return query.getResultList();
   }

   public List<NhanVien> searchNhanViens(String searchText) {
      TypedQuery query;
      try {
         int maNhanVien = Integer.parseInt(searchText);
         query = this.entityManager.createQuery("SELECT nv FROM NhanVien nv WHERE nv.maNhanVien = :maNhanVien", NhanVien.class);
         query.setParameter("maNhanVien", maNhanVien);
      } catch (NumberFormatException var4) {
         query = this.entityManager.createQuery("SELECT nv FROM NhanVien nv WHERE nv.hoVaTen LIKE :searchText OR nv.soDienThoai LIKE :soDienThoai", NhanVien.class);
         query.setParameter("searchText", "%" + searchText + "%");
         query.setParameter("soDienThoai", "%" + searchText + "%");
      }

      return query.getResultList();
   }

   public int getNgayDiLamFromDate(int manv, Date ngaybd, Date ngaykt) {
      String sql = "select count(dd.MaDiemDanh) as qq from DiemDanh dd where dd.TrangThai = 1 and dd.NgayDiemDanh between :ngaybd and :ngaykt and dd.MaNhanVien = :manv";
      Query query = this.entityManager.createNativeQuery(sql);
      query.setParameter("manv", manv);
      query.setParameter("ngaybd", ngaybd);
      query.setParameter("ngaykt", ngaykt);
      int result = (Integer)query.getSingleResult();
      return result;
   }
}