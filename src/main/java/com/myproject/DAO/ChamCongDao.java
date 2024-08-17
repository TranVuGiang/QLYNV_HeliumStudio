package com.myproject.DAO;

import com.myproject.Model.ChamCong;
import com.myproject.Utils.EntityManagerFStory;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class ChamCongDao {
   private EntityManager entityManager = EntityManagerFStory.getEntityManagerFactory().createEntityManager();

   public void addSanPham(ChamCong entity) {
      EntityTransaction transaction = this.entityManager.getTransaction();
      transaction.begin();
      this.entityManager.persist(entity);
      transaction.commit();
   }

   public void updateChamCong(ChamCong entity) {
      EntityTransaction transaction = this.entityManager.getTransaction();
      transaction.begin();
      this.entityManager.merge(entity);
      transaction.commit();
   }

   public void deleteChamCong(int maChamCong) {
      EntityTransaction transaction = this.entityManager.getTransaction();
      transaction.begin();
      ChamCong entity = (ChamCong)this.entityManager.find(ChamCong.class, maChamCong);
      this.entityManager.remove(entity);
      transaction.commit();
   }

   public ChamCong getChamCongById(int maChamCong) {
      return (ChamCong)this.entityManager.find(ChamCong.class, maChamCong);
   }

   public List<ChamCong> getAllChamCongs() {
      TypedQuery<ChamCong> query = this.entityManager.createQuery("SELECT nv FROM ChamCong nv", ChamCong.class);
      return query.getResultList();
   }

   public ChamCong getChamCongByMaAndMaSP(int manv, int masp) {
      TypedQuery<ChamCong> query = this.entityManager.createQuery("SELECT nv FROM ChamCong nv where nv.nhanVien.maNhanVien= :manv and nv.sanPham.maSanPham= :masp", ChamCong.class);
      query.setParameter("manv", manv);
      query.setParameter("masp", masp);
      return query.getResultList().size() > 0 ? (ChamCong)query.getResultList().get(0) : null;
   }
}