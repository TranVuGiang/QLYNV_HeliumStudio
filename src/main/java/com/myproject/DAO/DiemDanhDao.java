//package com.myproject.DAO;
//
//import com.myproject.Model.DiemDanh;
//import com.myproject.Utils.EntityManagerFStory;
//import java.util.Date;
//import java.util.List;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityTransaction;
//import javax.persistence.Query;
//import javax.persistence.TypedQuery;
//
//public class DiemDanhDao {
//   private EntityManager entityManager = EntityManagerFStory.getEntityManagerFactory().createEntityManager();
//
//   public void addDiemDanh(DiemDanh diemDanh) {
//      EntityTransaction transaction = this.entityManager.getTransaction();
//
//      try {
//         transaction.begin();
//         this.entityManager.persist(diemDanh);
//         transaction.commit();
//      } catch (Exception var4) {
//         if (transaction.isActive()) {
//            transaction.rollback();
//         }
//
//         var4.printStackTrace();
//      }
//
//   }
//
//   public List<DiemDanh> getDiemDanhByDateAndNhanVien(Date startDate, Date endDate, int maNhanVien) {
//      String jpql = "SELECT dd FROM DiemDanh dd WHERE dd.ngayDiemDanh BETWEEN :startDate AND :endDate AND dd.nhanVien.maNhanVien = :maNhanVien";
//      TypedQuery<DiemDanh> query = this.entityManager.createQuery(jpql, DiemDanh.class);
//      query.setParameter("startDate", startDate);
//      query.setParameter("endDate", endDate);
//      query.setParameter("maNhanVien", maNhanVien);
//      return query.getResultList();
//   }
//
//   public void updateDiemDanh(DiemDanh diemDanh) {
//      EntityTransaction transaction = this.entityManager.getTransaction();
//
//      try {
//         transaction.begin();
//         this.entityManager.merge(diemDanh);
//         transaction.commit();
//      } catch (Exception var4) {
//         if (transaction.isActive()) {
//            transaction.rollback();
//         }
//
//         var4.printStackTrace();
//      }
//
//   }
//
//   public void deleteDiemDanh(int maDiemDanh) {
//      EntityTransaction transaction = this.entityManager.getTransaction();
//
//      try {
//         transaction.begin();
//         DiemDanh diemDanh = (DiemDanh)this.entityManager.find(DiemDanh.class, maDiemDanh);
//         this.entityManager.remove(diemDanh);
//         transaction.commit();
//      } catch (Exception var4) {
//         if (transaction.isActive()) {
//            transaction.rollback();
//         }
//
//         var4.printStackTrace();
//      }
//
//   }
//
//   public DiemDanh getDiemDanhById(int maDiemDanh) {
//      return (DiemDanh)this.entityManager.find(DiemDanh.class, maDiemDanh);
//   }
//
//   public List<DiemDanh> getAllDiemDanh() {
//      TypedQuery<DiemDanh> query = this.entityManager.createQuery("SELECT g FROM DiemDanh g", DiemDanh.class);
//      return query.getResultList();
//   }
//
//   public List<DiemDanh> getAllDiemDanhByMaNV(int manv) {
//      TypedQuery<DiemDanh> query = this.entityManager.createQuery("SELECT g FROM DiemDanh g where g.nhanVien.maNhanVien = :manv", DiemDanh.class);
//      query.setParameter("manv", manv);
//      return query.getResultList();
//   }
//
//   public List<DiemDanh> getAllDiemDanhByDay() {
//      Date today = new Date();
//      TypedQuery<DiemDanh> query = this.entityManager.createQuery("select d from DiemDanh d where d.ngayDiemDanh = :ngay", DiemDanh.class);
//      query.setParameter("ngay", today);
//      return query.getResultList();
//   }
//
//   public boolean checkDaDiemDanh(int manv) {
//      String sql = "SELECT CASE            WHEN EXISTS (               SELECT 1                FROM DiemDanh d                WHERE d.MaNhanVien = :manv                  AND d.NgayDiemDanh = CONVERT(date, GETDATE())           )            THEN 1            ELSE 0        END AS exists_flag;";
//      Query query = this.entityManager.createNativeQuery(sql);
//      query.setParameter("manv", manv);
//      Number result = (Number)query.getSingleResult();
//      return result.intValue() == 1;
//   }
//}