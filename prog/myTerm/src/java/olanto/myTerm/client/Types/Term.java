/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.Types;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author simple
 */
public class Term implements IsSerializable {

    public long ID;
    public long Concept_ID;
    public String language;
    public String form;
    public String source;
    public String definition;
    public String definition_source;
    public String usage;
    public String context;
    public String context_source;
    public String note;
    public String type;
    public String part_of_speech;
    public String gender;
    public String status;
}
