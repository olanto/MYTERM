-- User roles: ADMIN, REVISOR, REDACTOR, READER
insert into codeslang values ('XX',' role','ADMIN','XXXXXXX',null);
insert into codeslang values ('XX',' role','REVISOR','XXXXXXX',null);
insert into codeslang values ('XX',' role','REDACTOR','XXXXXXX',null);
insert into codeslang values ('XX',' role','READER','XXXXXXX','default');

-- User status: ACTIVE, INACTIVE, DORMANT
insert into codeslang values ('XX','owner_status','ACTIVE','XXXXXXX','default');
insert into codeslang values ('XX','owner_status','INACTIVE','XXXXXXX',null);
insert into codeslang values ('XX','owner_status','DORMANT','XXXXXXX',null);

-- Term type: fullForm, acronym, abbreviation, shortForm, variant, phrase
insert into codeslang values ('XX', 'term_type','fullForm','XXXXXXX','default');
insert into codeslang values ('XX', 'term_type','acronym','XXXXXXX',null);
insert into codeslang values ('XX', 'term_type','abbreviation','XXXXXXX',null);
insert into codeslang values ('XX', 'term_type','shortForm','XXXXXXX',null);
insert into codeslang values ('XX', 'term_type','variant','XXXXXXX',null);
insert into codeslang values ('XX', 'term_type','phrase','XXXXXXX',null);

-- noun, verb, adjective, adverb, properNoun,other
insert into codeslang values ('XX', 'term_partofspeech','noun','XXXXXXX','default');
insert into codeslang values ('XX', 'term_partofspeech','verb','XXXXXXX',null);
insert into codeslang values ('XX', 'term_partofspeech','adjective','XXXXXXX',null);
insert into codeslang values ('XX', 'term_partofspeech','adverb','XXXXXXX',null);
insert into codeslang values ('XX', 'term_partofspeech','properNoun','XXXXXXX',null);
insert into codeslang values ('XX', 'term_partofspeech','other','XXXXXXX',null);
-- masculine, feminine, neuter, other
insert into codeslang values ('XX', 'term_gender','masculine','XXXXXXX',null);
insert into codeslang values ('XX', 'term_gender','feminine','XXXXXXX',null);
insert into codeslang values ('XX', 'term_gender','neuter','XXXXXXX',null);
insert into codeslang values ('XX', 'term_gender','other','XXXXXXX','default');
-- PRIVATE, PUBLIC
insert into codeslang values ('XX', 'privacy','PRIVATE','XXXXXXX',null);
insert into codeslang values ('XX', 'privacy','PUBLIC','XXXXXXX',null);

-- msg
insert into codeslang values ('XX', 'msg','tab.usersPanel','Users Management', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','tab.resourcesPanel','Resources Management', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','tab.languagesPanel','Languages Management', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','tab.domainsPanel','Domains Management', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','tab.usersResourcesPanel','Users-Resources Management', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','tab.usersLanguagesPanel','Users-Languages Management', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','tab.entriesPanel','Entries Management', 'XXXXXXX');

insert into codeslang values ('XX', 'msg','tab.searchPanel','Term Search', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','tab.redactionPanel','Workspace', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','tab.revisionPanel','To approve', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','tab.adminPanel','Administration', 'XXXXXXX');

insert into codeslang values ('XX', 'msg','btn.addTerm','ADD TERM', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','btn.delete','DELETE', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','btn.save','SAVE', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','btn.edit','EDIT', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','btn.submit','SUBMIT', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','btn.approve','APPROVE', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','btn.disapprove','DISAPPROVE', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','btn.disapproveAll','Disapprove All', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','btn.approveAll','Approve All', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','btn.create','CREATE', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','btn.escape','ESCAPE', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','btn.cancel','CANCEL', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','btn.abort','ABORT', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','btn.createNew','Create New', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','btn.search','SEARCH', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','btn.add','ADD', 'XXXXXXX');

insert into codeslang values ('XX', 'msg','msg.resource','Resource', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','msg.domain','Domain', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','msg.sourceLanguage','Source lang.', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','msg.targetLanguage','Target lang.', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','msg.addTitle','Click here to add entries', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','msg.searchTitle','Click here to retreive entries', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','msg.workspaceEntries','Workspace Entries', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','msg.seachMessage','Input your search expression', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','msg.welcome','Welcome to olanto''s Terminology Manager', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','msg.statusEdited','Under edition', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','msg.statusPublic','Published', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','msg.statusRevision','To be revised', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','msg.termRank','Term Number: ', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','msg.definitionDetails','Definition details', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','msg.resourceDetails','Resource''s Details', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','msg.creationDetails','Creation Details', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','msg.extraDetails','Other Information', 'XXXXXXX');

insert into codeslang values ('XX', 'msg','lbl.t.form','Form', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.t.lang','Language', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.t.source','Source', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.t.definition','Definition', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.t.source_definition','Definition''s source', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.t.status','Status', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.t.type','Type', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.t.note','Note', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.t.linguistic_note','Linguistic Note', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.t.technical_note','Technical Note', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.t.reference_note','Reference Note', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.t.linguistic_note_source','Linguistic Note Source', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.t.technical_note_source','Technical Note Source', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.t.reference_note_source','Reference Note Source', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.t.usage','Usage', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.t.extra','Extra', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.t.gender','Gender', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.t.part_of_speech','Part of speech', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.t.context','Context', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.t.source_context','Context''s source', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.t.geo_usage', 'Geo usage', null);
insert into codeslang values ('XX', 'msg','lbl.t.creation', 'Creation date', null);
insert into codeslang values ('XX', 'msg','lbl.t.created_by', 'Created by', null);
insert into codeslang values ('XX', 'msg','lbl.t.modification', 'Modification date', null);
insert into codeslang values ('XX', 'msg','lbl.t.last_modified_by', 'Modified by', null);
insert into codeslang values ('XX', 'msg','lbl.t.cross_ref', 'Cross reference', null);
insert into codeslang values ('XX', 'msg','lbl.t.extra_cross_ref', 'Extra Cross reference', null);
insert into codeslang values ('XX', 'msg','lbl.t.image','Image', 'XXXXXXX');

insert into codeslang values ('XX', 'msg','lbl.c.subject_field','Subject field', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.c.definition','Definition', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.c.resource','Resource', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.c.source_definition','Definition''s source', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.c.note','Note', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.c.image','Image', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.c.extra','Extra information', 'XXXXXXX');
insert into codeslang values ('XX', 'msg','lbl.c.creation', 'Creation date', null);
insert into codeslang values ('XX', 'msg','lbl.c.created_by', 'Created by', null);
insert into codeslang values ('XX', 'msg','lbl.c.modification', 'Modification date', null);
insert into codeslang values ('XX', 'msg','lbl.c.last_modified_by', 'Modified by', null);
insert into codeslang values ('XX', 'msg','lbl.c.cross_ref', 'Cross reference', null);
insert into codeslang values ('XX', 'msg','lbl.c.extra_cross_ref', 'Extra Cross reference', null);
insert into codeslang values ('XX', 'msg','lbl.c.imported_ref', 'Imported reference', null);

insert into codeslang values ('XX', 'msg','lbl.o.id', 'Owner''s ID', null);
insert into codeslang values ('XX', 'msg','lbl.o.role', 'Owner''s Role', null);
insert into codeslang values ('XX', 'msg','lbl.o.status', 'Owner''s Status', null);
insert into codeslang values ('XX', 'msg','lbl.o.mailing', 'Owner''s Mailing', null);
insert into codeslang values ('XX', 'msg','lbl.o.hash', 'Owner''s Password', null);
insert into codeslang values ('XX', 'msg','lbl.o.first_name', 'Owner''s First Name', null);
insert into codeslang values ('XX', 'msg','lbl.o.last_name', 'Owner''s Last Name', null);

insert into codeslang values ('XX', 'msg','lbl.d.id', 'Domaine''s ID', null);
insert into codeslang values ('XX', 'msg','lbl.d.default_name', 'Domaine''s Default Name', null);

insert into codeslang values ('XX', 'msg','lbl.l.id', 'Language''s ID', null);
insert into codeslang values ('XX', 'msg','lbl.l.default_name', 'Language''s Default Name', null);

insert into codeslang values ('XX', 'msg','lbl.r.id', 'Resource''s ID', null);
insert into codeslang values ('XX', 'msg','lbl.r.owner_id', 'Resource''s Owner', null);
insert into codeslang values ('XX', 'msg','lbl.r.name', 'Resource''s Name', null);
insert into codeslang values ('XX', 'msg','lbl.r.privacy', 'Resource''s Privacy', null);
insert into codeslang values ('XX', 'msg','lbl.r.note', 'Resource''s Note', null);
insert into codeslang values ('XX', 'msg','lbl.r.extra', 'Resource''s Extra', null);
