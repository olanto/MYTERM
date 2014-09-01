/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaviewtest;

import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpaviewtest.entities.VjReslang;
import jpaviewtest.entities.VjSourcetarget;

/**
 *
 * @author simple
 */
public class TestView {

    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {

        System.out.println(getTargetForThis("tunas", "EN", "FR"));
        System.out.println(getTargetForThis("tuna%", "EN", "FR"));

    }

    public static void init() {
        if (emf == null) {
            System.out.println("init BD connection");
            emf = Persistence.createEntityManagerFactory("JPAViewTestPU");
            em = emf.createEntityManager();
        }
    }

    public static String getTargetForThis(String term, String solang, String talang) {
        System.out.println("param:" + term);
        init();
        StringBuilder res = new StringBuilder("");
        Query query = em.createNamedQuery("VjSourcetarget.findBySource");
        query.setParameter("source", term);
        query.setParameter("solang", solang);
        query.setParameter("talang", talang);
        List<VjSourcetarget> resultQ = query.getResultList();

        res.append("<table>");
        res.append("<tr>");
        res.append("<th>" + "Source" + "</th>");
        res.append("<th>" + "Target" + "</th>");
        res.append("<th>" + "Lang. Src" + "</th>");
        res.append("<th>" + "Lang Tgt" + "</td>");
        res.append("</tr>");

        for (VjSourcetarget result : resultQ) {

            res.append("<tr>");

            res.append("<td>" + result.getSource() + "</td>");
            res.append("<td>" + result.getTarget() + "</td>");
            res.append("<td>" + result.getSolang() + "</td>");
            res.append("<td>" + result.getTalang() + "</td>");
            res.append("</tr>");
        }
        res.append("</table>");


        return res.toString();
    }

    public static Vector<String> getListForThis(String term, String solang, String talang) {
        System.out.println("param:" + term);
        init();
        Vector<String> res = new Vector<>();
        Query query = em.createNamedQuery("VjSourcetarget.findBySource");
        query.setParameter("source", term);
        query.setParameter("solang", solang);
        query.setParameter("talang", talang);
        List<VjSourcetarget> resultQ = query.getResultList();
        for (VjSourcetarget result : resultQ) {
            res.add(result.getSource());
        }
        return res;
    }

    public static String getReslang() {
        init();
        StringBuilder res = new StringBuilder("");
        Query query = em.createNamedQuery("VjReslang.findAll");


        List<VjReslang> resultQ = query.getResultList();
        res.append("<table>");
        res.append("<caption>" + "Inventory" + "</caption>");
        res.append("<tr>");
        res.append("<th>" + "Resource" + "</th>");
        res.append("<th>" + "Language" + "</th>");
        res.append("<th>" + "Number" + "</th>");
        res.append("</tr>");
        for (VjReslang result : resultQ) {

            res.append("<tr>");

            res.append("<td>" + result.getResourceName() + "</td>");
            res.append("<td>" + result.getIdLanguage() + "</td>");
            res.append("<td>" + Long.toString(result.getNbTerms()) + "</td>");
            res.append("</tr>");
        }
        res.append("</table>");

        return res.toString();
    }
}
