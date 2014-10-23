/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.Types;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author nizar ghoula - simple
 */
public class Concept implements IsSerializable {

    public long ID;
    public String subject_field;
    public String definition;
    public String defintion_source;
    public String note;
}
