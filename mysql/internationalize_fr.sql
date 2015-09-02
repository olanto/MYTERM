
delete from codeslang where id_language='FR';


-- User roles: ADMIN, REVISOR, REDACTOR, READER
insert into codeslang values ('FR',' role','ADMIN','ADMIN',null);
insert into codeslang values ('FR',' role','REVISOR','REVISEUR',null);
insert into codeslang values ('FR',' role','REDACTOR','REDACTEUR',null);
insert into codeslang values ('FR',' role','READER','LECTEUR','default');

-- User status: ACTIVE, INACTIVE, DORMANT
insert into codeslang values ('FR','owner_status','ACTIVE','ACTIVE','default');
insert into codeslang values ('FR','owner_status','INACTIVE','INACTIVE',null);
insert into codeslang values ('FR','owner_status','DORMANT','DORMANT',null);

-- Term type: fullForm, acronym, abbreviation, shortForm, variant, phrase
insert into codeslang values ('FR', 'term_type','fullForm','Forme complète','default');
insert into codeslang values ('FR', 'term_type','acronym','acronyme',null);
insert into codeslang values ('FR', 'term_type','abbreviation','abréviation',null);
insert into codeslang values ('FR', 'term_type','shortForm','Forme courte',null);
insert into codeslang values ('FR', 'term_type','variant','Variante',null);
insert into codeslang values ('FR', 'term_type','phrase','phrase',null);

-- noun, verb, adjective, adverb, properNoun,other
insert into codeslang values ('FR', 'term_partofspeech','noun','nom','default');
insert into codeslang values ('FR', 'term_partofspeech','verb','verbe',null);
insert into codeslang values ('FR', 'term_partofspeech','adjective','adjective',null);
insert into codeslang values ('FR', 'term_partofspeech','adverb','adverbe',null);
insert into codeslang values ('FR', 'term_partofspeech','properNoun','Nom propre',null);
insert into codeslang values ('FR', 'term_partofspeech','other','autre',null);
-- masculine, feminine, neuter, other
insert into codeslang values ('FR', 'term_gender','masculine','masculin',null);
insert into codeslang values ('FR', 'term_gender','feminine','féminin',null);
insert into codeslang values ('FR', 'term_gender','neuter','neutre',null);
insert into codeslang values ('FR', 'term_gender','other','autre','default');
-- PRIVATE, PUBLIC
insert into codeslang values ('FR', 'privacy','PRIVATE','PRIVATE',null);
insert into codeslang values ('FR', 'privacy','PUBLIC','PUBLIC',null);

-- msg
insert into codeslang values ('FR', 'msg','tab.usersPanel','Users Management', 'Gestion des utilisateurs');
insert into codeslang values ('FR', 'msg','tab.resourcesPanel','Resources Management', 'Gestion des ressources');
insert into codeslang values ('FR', 'msg','tab.languagesPanel','Languages Management', 'Gestion des langues');
insert into codeslang values ('FR', 'msg','tab.domainsPanel','Domains Management', 'Gestion des domaine');
insert into codeslang values ('FR', 'msg','tab.usersResourcesPanel','Users-Resources Management', 'Droits sur les ressources');
insert into codeslang values ('FR', 'msg','tab.usersLanguagesPanel','Users-Languages Management', 'Droits sur les langues');
insert into codeslang values ('FR', 'msg','tab.entriesPanel','Entries Management', 'Gestion des Termes');

insert into codeslang values ('FR', 'msg','tab.searchPanel','Term Search', 'Recherche Terminologique');
insert into codeslang values ('FR', 'msg','tab.redactionPanel','Workspace', 'Espace de travail');
insert into codeslang values ('FR', 'msg','tab.revisionPanel','To approve', 'A approuver');
insert into codeslang values ('FR', 'msg','tab.adminPanel','Administration', 'Administration');

insert into codeslang values ('FR', 'msg','btn.addTerm','ADD TERM', 'AJOUTER UN TERME');
insert into codeslang values ('FR', 'msg','btn.delete','DELETE', 'SUPPRIMER');
insert into codeslang values ('FR', 'msg','btn.save','SAVE', 'SAUVER');
insert into codeslang values ('FR', 'msg','btn.edit','EDIT', 'EDITER');
insert into codeslang values ('FR', 'msg','btn.submit','SUBMIT', 'SOUMETTRE');
insert into codeslang values ('FR', 'msg','btn.approve','APPROVE', 'APPROUVER');
insert into codeslang values ('FR', 'msg','btn.disapprove','DISAPPROVE', 'REJETER');
insert into codeslang values ('FR', 'msg','btn.disapproveAll','Disapprove All', 'TOUT REJETER');
insert into codeslang values ('FR', 'msg','btn.approveAll','Approve All', 'TOUT APPROUVER');
insert into codeslang values ('FR', 'msg','btn.create','CREATE', 'CREER');
insert into codeslang values ('FR', 'msg','btn.escape','ESCAPE', 'SORTIR');
insert into codeslang values ('FR', 'msg','btn.cancel','CANCEL', 'ANNULER');
insert into codeslang values ('FR', 'msg','btn.abort','ABORT', 'ABANDONNER');
insert into codeslang values ('FR', 'msg','btn.createNew','Create New', 'XXXXXXX30');
insert into codeslang values ('FR', 'msg','btn.search','SEARCH', 'Chercher');
insert into codeslang values ('FR', 'msg','btn.add','ADD', 'XXXXXXX31');

insert into codeslang values ('FR', 'msg','msg.resource','Resource', 'Ressource');
insert into codeslang values ('FR', 'msg','msg.domain','Domain', 'Domaine');
insert into codeslang values ('FR', 'msg','msg.sourceLanguage','Source lang.', 'Lang. Source');
insert into codeslang values ('FR', 'msg','msg.targetLanguage','Target lang.', 'Lang. Cible');
insert into codeslang values ('FR', 'msg','msg.addTitle','Click here to add entries', 'XXXXXXX32');
insert into codeslang values ('FR', 'msg','msg.searchTitle','Click here to retreive entries', 'XXXXXXX33');
insert into codeslang values ('FR', 'msg','msg.workspaceEntries','Workspace Entries', 'Zone de travail');
insert into codeslang values ('FR', 'msg','msg.seachMessage','Input your search expression', 'Expression recherchée');
insert into codeslang values ('FR', 'msg','msg.welcome','Welcome to olanto''s Terminology Manager', 'Bienvenue dans le gestionnaire de terminologie Olanto');
insert into codeslang values ('FR', 'msg','msg.statusEdited','Under edition', 'XXXXXXX35');
insert into codeslang values ('FR', 'msg','msg.statusPublic','Published', 'XXXXXXX36');
insert into codeslang values ('FR', 'msg','msg.statusRevision','To be revised', 'XXXXXXX37');
insert into codeslang values ('FR', 'msg','msg.termRank','Term Number: ', 'XXXXXXX38');
insert into codeslang values ('FR', 'msg','msg.definitionDetails','Definition details', 'Détail pour');
insert into codeslang values ('FR', 'msg','msg.resourceDetails','Resource''s Details', 'Ressource');
insert into codeslang values ('FR', 'msg','msg.creationDetails','Creation Details', 'Détail création');
insert into codeslang values ('FR', 'msg','msg.extraDetails','Other Information', 'Autres informations');

insert into codeslang values ('FR', 'msg','lbl.t.form','Form', 'Forme');
insert into codeslang values ('FR', 'msg','lbl.t.lang','Language', 'Langue');
insert into codeslang values ('FR', 'msg','lbl.t.source','Source', 'Source');
insert into codeslang values ('FR', 'msg','lbl.t.definition','Definition', 'Définition');
insert into codeslang values ('FR', 'msg','lbl.t.source_definition','Definition''s source', 'Source de définition');
insert into codeslang values ('FR', 'msg','lbl.t.status','Status', 'Statut');
insert into codeslang values ('FR', 'msg','lbl.t.type','Type', 'Type');
insert into codeslang values ('FR', 'msg','lbl.t.note','Note', 'Note');
insert into codeslang values ('FR', 'msg','lbl.t.linguistic_note','Linguistic Note', 'Note linguistique');
insert into codeslang values ('FR', 'msg','lbl.t.technical_note','Technical Note', 'Note Technique');
insert into codeslang values ('FR', 'msg','lbl.t.reference_note','Reference Note', 'Note Référence');
insert into codeslang values ('FR', 'msg','lbl.t.linguistic_note_source','Linguistic Note Source', 'Source note linguistique');
insert into codeslang values ('FR', 'msg','lbl.t.technical_note_source','Technical Note Source', 'Source note Technique');
insert into codeslang values ('FR', 'msg','lbl.t.reference_note_source','Reference Note Source', 'Source note Référence');
insert into codeslang values ('FR', 'msg','lbl.t.usage','Usage', 'Usage');
insert into codeslang values ('FR', 'msg','lbl.t.extra','Extra', 'Extra');
insert into codeslang values ('FR', 'msg','lbl.t.gender','Gender', 'Genre');
insert into codeslang values ('FR', 'msg','lbl.t.part_of_speech','Part of speech', 'Catégorie grammaticale');
insert into codeslang values ('FR', 'msg','lbl.t.context','Context', 'Contexte');
insert into codeslang values ('FR', 'msg','lbl.t.source_context','Context''s source', 'Source du Contexte');
insert into codeslang values ('FR', 'msg','lbl.t.geo_usage', 'Geo usage', 'ZZZZ1');
insert into codeslang values ('FR', 'msg','lbl.t.creation', 'Creation date', 'ZZZZ2');
insert into codeslang values ('FR', 'msg','lbl.t.created_by', 'Created by', 'ZZZZ3');
insert into codeslang values ('FR', 'msg','lbl.t.modification', 'Modification date', 'ZZZZ4');
insert into codeslang values ('FR', 'msg','lbl.t.last_modified_by', 'Modified by', 'ZZZZ5');
insert into codeslang values ('FR', 'msg','lbl.t.cross_ref', 'Cross reference', 'ZZZZ6');
insert into codeslang values ('FR', 'msg','lbl.t.extra_cross_ref', 'Extra Cross reference', 'ZZZZ7');
insert into codeslang values ('FR', 'msg','lbl.t.image','Image', 'Image');

insert into codeslang values ('FR', 'msg','lbl.c.subject_field','Subject field', 'Domaine');
insert into codeslang values ('FR', 'msg','lbl.c.definition','Definition', 'Définition');
insert into codeslang values ('FR', 'msg','lbl.c.resource','Resource', 'Ressource');
insert into codeslang values ('FR', 'msg','lbl.c.source_definition','Definition''s source', 'Source de la définition');
insert into codeslang values ('FR', 'msg','lbl.c.note','Note', 'Note');
insert into codeslang values ('FR', 'msg','lbl.c.image','Image', 'Image');
insert into codeslang values ('FR', 'msg','lbl.c.extra','Extra information', 'Information suppl.');
insert into codeslang values ('FR', 'msg','lbl.c.creation', 'Creation date', 'ZZZZ8');
insert into codeslang values ('FR', 'msg','lbl.c.created_by', 'Created by', 'ZZZZ9');
insert into codeslang values ('FR', 'msg','lbl.c.modification', 'Modification date', 'ZZZZ10');
insert into codeslang values ('FR', 'msg','lbl.c.last_modified_by', 'Modified by', 'ZZZZ11');
insert into codeslang values ('FR', 'msg','lbl.c.cross_ref', 'Cross reference', 'ZZZZ12');
insert into codeslang values ('FR', 'msg','lbl.c.extra_cross_ref', 'Extra Cross reference', 'ZZZZ13');
insert into codeslang values ('FR', 'msg','lbl.c.imported_ref', 'Imported reference', 'ZZZZ14');

insert into codeslang values ('FR', 'msg','lbl.o.id', 'Owner''s ID', 'ZZZZ15');
insert into codeslang values ('FR', 'msg','lbl.o.role', 'Owner''s Role', 'ZZZZ16');
insert into codeslang values ('FR', 'msg','lbl.o.status', 'Owner''s Status', 'ZZZZ17');
insert into codeslang values ('FR', 'msg','lbl.o.mailing', 'Owner''s Mailing', 'ZZZZ18');
insert into codeslang values ('FR', 'msg','lbl.o.hash', 'Owner''s Password', 'ZZZZ19');
insert into codeslang values ('FR', 'msg','lbl.o.first_name', 'Owner''s First Name', 'ZZZZ20');
insert into codeslang values ('FR', 'msg','lbl.o.last_name', 'Owner''s Last Name', 'ZZZZ21');

insert into codeslang values ('FR', 'msg','lbl.d.id', 'Domaine''s ID', 'ZZZZ22');
insert into codeslang values ('FR', 'msg','lbl.d.default_name', 'Domaine''s Default Name', 'ZZZZ23');

insert into codeslang values ('FR', 'msg','lbl.l.id', 'Language''s ID', 'ZZZZ24');
insert into codeslang values ('FR', 'msg','lbl.l.default_name', 'Language''s Default Name', 'ZZZZ25');

insert into codeslang values ('FR', 'msg','lbl.r.id', 'Resource''s ID', 'ZZZZ26');
insert into codeslang values ('FR', 'msg','lbl.r.owner_id', 'Resource''s Owner', 'ZZZZ27');
insert into codeslang values ('FR', 'msg','lbl.r.name', 'Resource''s Name', 'ZZZZ28');
insert into codeslang values ('FR', 'msg','lbl.r.privacy', 'Resource''s Privacy', 'ZZZZ29');
insert into codeslang values ('FR', 'msg','lbl.r.note', 'Resource''s Note', 'ZZZZ30');
insert into codeslang values ('FR', 'msg','lbl.r.extra', 'Resource''s Extra', 'ZZZZ31');

commit;