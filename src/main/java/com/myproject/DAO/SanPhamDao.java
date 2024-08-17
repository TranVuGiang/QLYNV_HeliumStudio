package com.myproject.DAO;

import com.myproject.Model.SanPham;
import com.myproject.Utils.EntityManagerFStory;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class SanPhamDao {
   private EntityManager entityManager = EntityManagerFStory.getEntityManagerFactory().createEntityManager();

   public void addSanPham(SanPham sanPham) {
      EntityTransaction transaction = this.entityManager.getTransaction();

      try {
         transaction.begin();
         this.entityManager.persist(sanPham);
         transaction.commit();
      } catch (Exception var4) {
         if (transaction.isActive()) {
            transaction.rollback();
         }

         var4.printStackTrace();
      }

   }

   public void updateSanPham(SanPham sanPham) {
      EntityTransaction transaction = this.entityManager.getTransaction();

      try {
         transaction.begin();
         this.entityManager.merge(sanPham);
         transaction.commit();
      } catch (Exception var4) {
         if (transaction.isActive()) {
            transaction.rollback();
         }

         var4.printStackTrace();
      }

   }

   public void deleteSanPham(int maSanPham) {
      EntityTransaction transaction = this.entityManager.getTransaction();

      try {
         transaction.begin();
         SanPham sanPham = (SanPham)this.entityManager.find(SanPham.class, maSanPham);
         if (sanPham != null) {
            this.entityManager.remove(sanPham);
         }

         transaction.commit();
      } catch (Exception var4) {
         if (transaction.isActive()) {
            transaction.rollback();
         }

         var4.printStackTrace();
      }

   }

   public SanPham getSanPhamById(int maSanPham) {
      return (SanPham)this.entityManager.find(SanPham.class, maSanPham);
   }

   public List<SanPham> getAllSanPhams() {
      TypedQuery<SanPham> query = this.entityManager.createQuery("SELECT sp FROM SanPham sp", SanPham.class);
      return query.getResultList();
   }
   
      public List<SanPham> getAllSanPhamByMaNV(int manv) {
      TypedQuery<SanPham> query = this.entityManager.createQuery("SELECT sp FROM SanPham sp where sp.nhanVien.maNhanVien= :manv", SanPham.class);
      query.setParameter("manv", manv);
      return query.getResultList();
   }

   public List<SanPham> getAllSanPhamByName(String tensp) {
      TypedQuery<SanPham> query = this.entityManager.createQuery("SELECT sp FROM SanPham sp where sp.tenSanPham= :tensp", SanPham.class);
      query.setParameter("tensp", tensp);
      return query.getResultList();
   }

   public SanPham getSanPhamTonTai(String tenPhim, int tapPhim) {
      TypedQuery<SanPham> query = this.entityManager.createQuery("SELECT sp FROM SanPham sp where sp.tenSanPham= :tenPhim and sp.tapPhim= :tapPhim", SanPham.class);
      query.setParameter("tenPhim", tenPhim);
      query.setParameter("tapPhim", tapPhim);
      return query.getResultList().size() > 0 ? (SanPham)query.getResultList().get(0) : null;
   }

   public List<SanPham> searchSanPhamByName(String keyword) {
      TypedQuery<SanPham> query = this.entityManager.createQuery("SELECT sp FROM SanPham sp WHERE LOWER(sp.tenSanPham) LIKE :keyword", SanPham.class);
      query.setParameter("keyword", "%" + keyword.toLowerCase() + "%");
      return query.getResultList();
   }
}