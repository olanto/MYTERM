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

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author nizar ghoula - simple
 */
public class GuiConstant implements IsSerializable {

    private static final long serialVersionUID = 1L;
    public static String INTERFACE_LANG = "EN";
    public static final String PROFILE_READER = "READER";
    public static final String PROFILE_REDACTOR = "REDACTOR";
    public static final String PROFILE_REVISOR = "REVISOR";
    public static final String PROFILE_ADMIN = "ADMIN";
    public static final int EXP_DAYS = 24;
    public static final int TEXTAREA_HEIGHT = 100;
    public static final int HEADER_HEIGHT = 130;
    public static final int CPT_HEIGHT = 140;
    public static final int SIDE_WIDTH = 340;
    public static final int WIDTH_UNIT = 8;
    public static final int HEIGHT_UNIT = 20;
    public static final int HEADER_HEIGHT_EXTRA = 150;
    public static final int WIDTH_UNIT_EXTRA = 18;
    public static final int ADMIN_HEADER_HEIGHT = 170;
    public static final String OLANTO_URL = "Powered by Olanto|http://olanto.org|_blank";
    public static final String FEEDBACK_MAIL = "Feedback|info@olanto.org";
    public static final String C_SUBJECT_FIELD = "c.subject_field";
    public static final String C_DEFINITION = "c.definition";
    public static final String C_RESOURCE = "c.resource";
    public static final String C_SOURCE_DEFINITION = "c.source_definition";
    public static final String C_NOTE = "c.note";
    public static final String C_CREATION = "c.creation";
    public static final String C_CREATED_BY = "c.created_by";
    public static final String C_MODIFICATION = "c.modification";
    public static final String C_LAST_MODIF_BY = "c.last_modified_by";
    public static final String C_CROSS_REF = "c.cross_ref";
    public static final String C_EXTRA_CROSS_REF = "c.extra_cross_ref";
    public static final String C_IMPORTED_REF = "c.imported_ref";
    public static final String C_IMAGE = "c.image";
    public static final String C_EXTRA = "c.extra";
    public static final String O_ID = "o.id";
    public static final String O_ROLE = "o.role";
    public static final String O_STATUS = "o.status";
    public static final String O_MAILING = "o.mailing";
    public static final String O_FIRST_NAME = "o.first_name";
    public static final String O_LAST_NAME = "o.last_name";
    public static final String O_PWD = "o.hash";
    public static final String LBL_R_ID = "lbl.r.id";
    public static final String LBL_R_NAME = "lbl.r.name";
    public static final String LBL_R_PRIVACY = "lbl.r.privacy";
    public static final String LBL_R_OWNER = "lbl.r.owner_id";
    public static final String LBL_R_NOTE = "lbl.r.note";
    public static final String LBL_R_EXTRA = "lbl.r.extra";
    public static final String LBL_L_ID = "lbl.l.id";
    public static final String LBL_L_DEFAULT_NAME = "lbl.l.default_name";
    public static final String LBL_D_ID = "lbl.d.id";
    public static final String LBL_D_DEFAULT_NAME = "lbl.d.default_name";
    public static final String LBL_O_ID = "lbl.o.id";
    public static final String LBL_O_ROLE = "lbl.o.role";
    public static final String LBL_O_STATUS = "lbl.o.status";
    public static final String LBL_O_MAILING = "lbl.o.mailing";
    public static final String LBL_O_FIRST_NAME = "lbl.o.first_name";
    public static final String LBL_O_LAST_NAME = "lbl.o.last_name";
    public static final String LBL_O_PWD = "lbl.o.hash";
    public static final String LBL_C_DEFINITION = "lbl.c.definition";
    public static final String LBL_C_SUBJECT_FIELD = "lbl.c.subject_field";
    public static final String LBL_C_RESOURCE = "lbl.c.resource";
    public static final String LBL_C_SOURCE_DEFINITION = "lbl.c.source_definition";
    public static final String LBL_C_CREATION = "lbl.c.creation";
    public static final String LBL_C_CREATED_BY = "lbl.c.created_by";
    public static final String LBL_C_MODIFICATION = "lbl.c.modification";
    public static final String LBL_C_LAST_MODIF_BY = "lbl.c.last_modified_by";
    public static final String LBL_C_CROSS_REF = "lbl.c.cross_ref";
    public static final String LBL_C_EXTRA_CROSS_REF = "lbl.c.extra_cross_ref";
    public static final String LBL_C_IMPORTED_REF = "lbl.c.imported_ref";
    public static final String LBL_C_NOTE = "lbl.c.note";
    public static final String LBL_C_IMAGE = "lbl.c.image";
    public static final String LBL_C_EXTRA = "lbl.c.extra";
    public static final String LBL_T_LANG = "lbl.t.lang";
    public static final String LBL_T_FORM = "lbl.t.form";
    public static final String LBL_T_SOURCE = "lbl.t.source";
    public static final String LBL_T_DEFINITION = "lbl.t.definition";
    public static final String LBL_T_SOURCE_DEFINITION = "lbl.t.source_definition";
    public static final String LBL_T_STATUS = "lbl.t.status";
    public static final String LBL_T_TYPE = "lbl.t.type";
    public static final String LBL_T_NOTE = "lbl.t.note";
    public static final String LBL_T_USAGE = "lbl.t.usage";
    public static final String LBL_T_EXTRA = "lbl.t.extra";
    public static final String LBL_T_GENDER = "lbl.t.gender";
    public static final String LBL_T_POS = "lbl.t.part_of_speech";
    public static final String LBL_T_CONTEXT = "lbl.t.context";
    public static final String LBL_T_SOURCE_CONTEXT = "lbl.t.source_context";
    public static final String LBL_T_GEO_USG = "lbl.t.geo_usage";
    public static final String LBL_T_CREATION = "lbl.t.creation";
    public static final String LBL_T_CREATED_BY = "lbl.t.created_by";
    public static final String LBL_T_MODIFICATION = "lbl.t.modification";
    public static final String LBL_T_LAST_MODIF_BY = "lbl.t.last_modified_by";
    public static final String LBL_T_CROSS_REF = "lbl.t.cross_ref";
    public static final String LBL_T_EXTRA_CROSS_REF = "lbl.t.extra_cross_ref";
    public static final String LBL_T_LING_NOTE = "lbl.t.linguistic_note";
    public static final String LBL_T_TECH_NOTE = "lbl.t.technical_note";
    public static final String LBL_T_REFERENCE_NOTE = "lbl.t.reference_note";
    public static final String LBL_T_LING_NOTE_SRC = "lbl.t.linguistic_note_source";
    public static final String LBL_T_TECH_NOTE_SRC = "lbl.t.technical_note_source";
    public static final String LBL_T_REFERENCE_NOTE_SRC = "lbl.t.reference_note_source";
    public static final String LBL_T_IMAGE = "lbl.t.image";
    public static final String T_FORM = "t.form";
    public static final String T_SOURCE = "t.source";
    public static final String T_DEFINITION = "t.definition";
    public static final String T_SOURCE_DEFINITION = "t.source_definition";
    public static final String T_STATUS = "t.status";
    public static final String T_TYPE = "t.type";
    public static final String T_NOTE = "t.note";
    public static final String T_LING_NOTE = "t.linguistic_note";
    public static final String T_TECH_NOTE = "t.technical_note";
    public static final String T_REFERENCE_NOTE = "t.reference_note";
    public static final String T_LING_NOTE_SRC = "t.linguistic_note_source";
    public static final String T_TECH_NOTE_SRC = "t.technical_note_source";
    public static final String T_REFERENCE_NOTE_SRC = "t.reference_note_source";
    public static final String T_USAGE = "t.usage";
    public static final String T_EXTRA = "t.extra";
    public static final String T_GENDER = "t.gender";
    public static final String T_POS = "t.part_of_speech";
    public static final String T_CONTEXT = "t.context";
    public static final String T_SOURCE_CONTEXT = "t.source_context";
    public static final String T_GEO_USG = "t.geo_usage";
    public static final String T_CREATION = "t.creation";
    public static final String T_CREATED_BY = "t.created_by";
    public static final String T_MODIFICATION = "t.modification";
    public static final String T_LAST_MODIF_BY = "t.last_modified_by";
    public static final String T_CROSS_REF = "t.cross_ref";
    public static final String T_EXTRA_CROSS_REF = "t.extra_cross_ref";
    public static final String T_IMAGE = "t.image";
    public static final String T_LANG = "t.lang";
    public static final String R_ID = "r.id";
    public static final String R_OWNER_ID = "r.owner_id";
    public static final String R_NAME = "r.name";
    public static final String R_PRIVACY = "r.privacy";
    public static final String R_NOTE = "r.note";
    public static final String R_EXTRA = "r.extra";
    public static final String L_ID = "l.id";
    public static final String L_NAME = "l.default_name";
    public static final String D_ID = "d.id";
    public static final String D_DEFAULT_NAME = "d.default_name";
    public static final String TAB_SEARCH = "tab.searchPanel";
    public static final String TAB_REDACTION = "tab.redactionPanel";
    public static final String TAB_REVISION = "tab.revisionPanel";
    public static final String TAB_ADMINISTRATION = "tab.adminPanel";
    public static final String TAB_USERS = "tab.usersPanel";
    public static final String TAB_RESOURCES = "tab.resourcesPanel";
    public static final String TAB_LANGUAGES = "tab.languagesPanel";
    public static final String TAB_DOMAINS = "tab.domainsPanel";
    public static final String TAB_USERS_RESOURCES = "tab.usersResourcesPanel";
    public static final String TAB_USERS_LANGUAGES = "tab.usersLanguagesPanel";
    public static final String TAB_ENTRIES = "tab.entriesPanel";
    public static final String BTN_DELETE = "btn.delete";
    public static final String BTN_SAVE = "btn.save";
    public static final String BTN_SUBMIT = "btn.submit";
    public static final String BTN_APPROVE = "btn.approve";
    public static final String BTN_DISAPPROVE = "btn.disapprove";
    public static final String BTN_EDIT = "btn.edit";
    public static final String BTN_DISAPPROVEALL = "btn.disapproveAll";
    public static final String BTN_APPROVEALL = "btn.approveAll";
    public static final String BTN_ESCAPE = "btn.escape";
    public static final String BTN_CANCEL = "btn.cancel";
    public static final String BTN_ABORT = "btn.abort";
    public static final String BTN_CREATE = "btn.create";
    public static final String BTN_CREATE_NEW = "btn.createNew";
    public static final String BTN_ADD = "btn.add";
    public static final String BTN_DISPLAY_ALL = "btn.displayAll";
    public static final String BTN_ADD_TERM = "btn.addTerm";
    public static final String BTN_SEARCH = "btn.search";
    public static final String MSG_WELCOME = "msg.welcome";
    public static final String MSG_SEARCH_INPUT = "msg.seachMessage";
    public static final String MSG_WORKSPACE_ENTRIES = "msg.workspaceEntries";
    public static final String MSG_TERM_RANK = "msg.termRank";
    public static final String MSG_DEF_DETAILS = "msg.definitionDetails";
    public static final String MSG_RESOURCE_DETAILS = "msg.resourceDetails";
    public static final String MSG_CREATION_DETAILS = "msg.creationDetails";
    public static final String MSG_EXTRA_DETAILS = "msg.extraDetails";
    public static final String MSG_STATUS_ED = "msg.statusEdited";
    public static final String MSG_STATUS_PUB = "msg.statusPublic";
    public static final String MSG_STATUS_REV = "msg.statusRevision";
    public static final String MSG_SEARCH_TITLE = "msg.searchTitle";
    public static final String MSG_ADD_TITLE = "msg.addTitle";
    public static final String MSG_DISPLAY_ALL_TITLE = "msg.displayAllTitle";
    public static final String MSG_SOURCE_LANG = "msg.sourceLanguage";
    public static final String MSG_TARGET_LANG = "targetLanguage";
    public static final String MSG_RESOURCE = "msg.resource";
    public static final String MSG_DOMAIN = "msg.domain";
    public static final String MSG_ALL_VALUE = "msg.value.all";

    public GuiConstant() {
    }
}
