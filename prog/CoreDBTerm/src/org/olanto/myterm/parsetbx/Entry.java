package org.olanto.myterm.parsetbx;

import org.olanto.myterm.coredb.ManageConcept;
import org.olanto.myterm.coredb.ManageLangsets;
import org.olanto.myterm.coredb.ManageTerm;
import org.olanto.myterm.coredb.entityclasses.Concepts;
import org.olanto.myterm.coredb.entityclasses.Langsets;
import org.olanto.myterm.coredb.entityclasses.Terms;

/**
 *
 * @author simple
 */
public class Entry {

    private String resourceName;
    private Concepts concept;
    private Langsets langset;
    private Terms term;
    private String extraRessources;
    private String extraConcepts;
    private String extraLangsets;
    private String extraTerms;
    private boolean createInDB = false;

    public Entry(String resourceName, boolean createInDB) {
        this.resourceName = resourceName;
        this.createInDB = createInDB;
        prepareConcept();
    }

    public void prepareConcept() {
        concept = new Concepts();
    }

    public void addConcept() {
        if (createInDB) {
            concept.setExtra(extraConcepts);
            concept = ManageConcept.addConceptToResource(resourceName, concept);
        }
    }

    public void addLangSet(String lang) {
        if (createInDB) {
            langset = ManageLangsets.addLangsetToConcept(concept, lang.toUpperCase());
        }
    }

    public void addExtraLangSet() {
        if (createInDB) {
            langset = ManageLangsets.updateExtra(langset.getIdLangset(), extraLangsets);
        }
    }

    public void prepareTerm(String termForm) {
        term = new Terms(null, termForm, 'p'); // minimal information
    }

    public void addTerm() {
        if (createInDB) {
            term.setExtra(extraTerms);
            term = ManageTerm.addTermToLangset(langset, term); //status is published
        }
    }

    public void dump() {
        System.out.println(resourceName + ";");
    }

    /**
     * @return the term
     */
    public Terms getTerm() {
        return term;
    }

    /**
     * @return the concept
     */
    public Concepts getConcept() {
        return concept;
    }

    /**
     * @param concept the concept to set
     */
    public void setConcept(Concepts concept) {
        this.concept = concept;
    }

    /**
     * @return the resourceName
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * @param resourceName the resourceName to set
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * @return the extraRessources
     */
    public String getExtraRessources() {
        return extraRessources;
    }

    /**
     * @param extraRessources the extraRessources to set
     */
    public void setExtraRessources(String extraRessources) {
        this.extraRessources = extraRessources;
    }

    /**
     * @return the extraConcepts
     */
    public String getExtraConcepts() {
        return extraConcepts;
    }

    /**
     * @param extraConcepts the extraConcepts to set
     */
    public void setExtraConcepts(String extraConcepts) {
        this.extraConcepts = extraConcepts;
    }

    /**
     * @return the extraLangsets
     */
    public String getExtraLangsets() {
        return extraLangsets;
    }

    /**
     * @param extraLangsets the extraLangsets to set
     */
    public void setExtraLangsets(String extraLangsets) {
        this.extraLangsets = extraLangsets;
    }

    /**
     * @return the extraTerms
     */
    public String getExtraTerms() {
        return extraTerms;
    }

    /**
     * @param extraTerms the extraTerms to set
     */
    public void setExtraTerms(String extraTerms) {
        this.extraTerms = extraTerms;
    }
}
