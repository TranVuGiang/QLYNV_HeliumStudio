    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package com.myproject.Utils;

    import javax.persistence.EntityManagerFactory;
    import javax.persistence.Persistence;

    public class EntityManagerFStory {
        private static EntityManagerFactory entityManagerFactory;

        private EntityManagerFStory() {

        }

        public static EntityManagerFactory getEntityManagerFactory() {
            if (entityManagerFactory == null) {
                entityManagerFactory = Persistence.createEntityManagerFactory("dtb");
            }
            return entityManagerFactory;
        }

        public static void closeEntityManagerFactory() {
            if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
                entityManagerFactory.close();
            }
        }
    }
