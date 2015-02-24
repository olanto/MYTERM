/**
 * ********
 * Copyright © 2013-2014 Olanto Foundation Geneva
 *
 * This file is part of myTERM.
 *
 * myTERM is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * myCAT is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with myTERM. If not, see <http://www.gnu.org/licenses/>.
 *
 *********
 */
package olanto.myTerm.shared;

/**
 *
 * @author nizar ghoula - simple
 */
import com.google.gwt.user.client.rpc.IsSerializable;

public class OwnerDTO implements IsSerializable {

    private long id;
    private String firstname;
    private String lastname;
    private String hash;
    private String email;
    private String role;
    private String status;
    private String sessionID;

    public OwnerDTO() {
    }

    public OwnerDTO(Long id, String fn, String ln, String pwd, String mail, String role, String status) {
        this.id = id;
        this.firstname = fn;
        this.lastname = ln;
        this.hash = pwd;
        this.email = mail;
        this.role = role;
        this.status = status;
    }

    public String getSessionID() {
        return sessionID;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public String getHash() {
        return hash;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public void setFirstName(String fname) {
        this.firstname = fname;
    }

    public void setLastName(String lname) {
        this.lastname = lname;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRole(String role) {
        this.role = role;
    }
     public String toStringDTO() {
        String details = "----Owner Details----\n";
        details += "ID: " + this.getId() + "\n";
        details += "FN: " + this.getFirstName()+ "\n";
        details += "LN: " + this.getLastName() + "\n";
        details += "Mail: " + this.getEmail() + "\n";
        details += "St: " + this.getStatus()+ "\n";
        details += "Rl: " + this.getRole()+ "\n";
        return details;
    }
}