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
import jpaviewtest.entities.VjConceptdetail;
import jpaviewtest.entities.VjReslang;
import jpaviewtest.entities.VjSourcetarget;
import org.olanto.myterm.coredb.Queries;

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
//        System.out.println("param:" + term);
        init();
        StringBuilder res = new StringBuilder("");
        Query query = em.createNamedQuery("VjSourcetarget.findBySource");
        query.setParameter("source", term);
        query.setParameter("solang", solang);
        query.setParameter("talang", talang);
        List<VjSourcetarget> resultQ = query.getResultList();

        for (VjSourcetarget result : resultQ) {
            res.append("<tr>");
            res.append("<td>").append(result.getSource()).append("</td>");
            res.append("<td>").append(result.getTarget()).append("</td>");
            res.append("<td><a href=\"#").append(result.getIdConcept()).append("\" onClick=\"return gwtnav(this);\">").append("Expand for details</a></td>");
            res.append("</tr>");
        }
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
        res.append("<th>Resource</th>");
        res.append("<th>Language</th>");
        res.append("<th>Number</th>");
        res.append("</tr>");
        for (VjReslang result : resultQ) {
            res.append("<tr>");
            res.append("<td>").append(result.getResourceName()).append("</td>");
            res.append("<td>").append(result.getIdLanguage()).append("</td>");
            res.append("<td>").append(Long.toString(result.getNbTerms())).append("</td>");
            res.append("</tr>");
        }
        res.append("</table>");

        return res.toString();
    }

    public static String getTargetForThis(long conceptID) {
//        System.out.println("param:" + term);
        init();
        StringBuilder res = new StringBuilder("");
        Query query = em.createNamedQuery("VjConceptdetail.findByIdConcept");
        query.setParameter("idConcept", conceptID);
        List<VjConceptdetail> resultQ = query.getResultList();

        res.append("<table>");
        res.append("<caption>" + "Details" + "</caption>");
        res.append("<tr>");
        res.append("<th>Language</th>");
        res.append("<th>Term</th>");
        res.append("<th>Source</th>");
        res.append("<th>Definition</th>");
        res.append("<th>Note</th>");
        res.append("</tr>");
        for (VjConceptdetail result : resultQ) {
            res.append("<tr>");
            res.append("<td>").append("&nbsp").append(Queries.getLanguageByID(result.getIdLanguage()).getLanguageDefaultName()).append("</td>");
            res.append("<td>").append("&nbsp").append(result.getTermForm()).append("</td>");
            res.append("<td>").append("&nbsp").append(result.getTermSource()).append("</td>");
            res.append("<td>").append("&nbsp").append(result.getTermDefinition()).append("</td>");
            res.append("<td>").append("&nbsp").append(result.getTermNote()).append("</td>");
            res.append("</tr>");
        }
        res.append("</table>");
        return res.toString();
    }
}
