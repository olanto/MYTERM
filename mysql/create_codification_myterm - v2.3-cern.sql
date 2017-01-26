-- MYTERM SCHEMA
/**
 * ********
 * Copyright © 2013-2015 Olanto Foundation Geneva
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

drop table if exists codes;

drop table if exists codeslang;

-- --------------------------------- 

create table codes
 (code_type varchar(32) not null,
  code_value varchar(64) not null,
  code_extra varchar(256) ,
  code_default varchar(12) , -- 'default'
  PRIMARY KEY (code_type,code_value))
AUTO_INCREMENT=1000,DEFAULT CHARSET=utf8;

-- ADMIN, REVISOR, REDACTOR, READER
insert into codes values ('role','ADMIN',null,null);
insert into codes values ('role','REVISOR',null,null);
insert into codes values ('role','REDACTOR',null,null);
insert into codes values ('role','READER',null,'default');
-- ACTIVE, INACTIVE, DORMANT
insert into codes values ('owner_status','ACTIVE',null,'default');
insert into codes values ('owner_status','INACTIVE',null,null);
insert into codes values ('owner_status','DORMANT',null,null);
-- fullForm, acronym, abbreviation, shortForm, variant, phrase
insert into codes values ('term_type','fullForm',null,'default');
insert into codes values ('term_type','acronym',null,null);
-- insert into codes values ('term_type','abbreviation',null,null);
insert into codes values ('term_type','shortForm',null,null);
insert into codes values ('term_type','variant',null,null);
-- insert into codes values ('term_type','phrase',null,null);
-- noun, verb, adjective, adverb, properNoun,other
insert into codes values ('term_partofspeech','noun',null,'default');
insert into codes values ('term_partofspeech','verb',null,null);
insert into codes values ('term_partofspeech','adjective',null,null);
insert into codes values ('term_partofspeech','adverb',null,null);
insert into codes values ('term_partofspeech','properNoun',null,null);
insert into codes values ('term_partofspeech','other',null,null);
-- masculine, feminine, neuter, other
insert into codes values ('term_gender','masculine',null,null);
insert into codes values ('term_gender','feminine',null,null);
insert into codes values ('term_gender','neuter',null,null);
insert into codes values ('term_gender','other',null,'default');
-- PRIVATE, PUBLIC
insert into codes values ('privacy','PRIVATE',null,null);
insert into codes values ('privacy','PUBLIC',null,null);

-- msg
insert into codes values ('msg','tab.usersPanel','Users Management',null);
insert into codes values ('msg','tab.resourcesPanel','Resources Management',null);
insert into codes values ('msg','tab.languagesPanel','Languages Management',null);
insert into codes values ('msg','tab.domainsPanel','Domains Management',null);
insert into codes values ('msg','tab.usersResourcesPanel','Users-Resources Management',null);
insert into codes values ('msg','tab.usersLanguagesPanel','Users-Languages Management',null);
insert into codes values ('msg','tab.entriesPanel','Entries Management',null);

insert into codes values ('msg','tab.searchPanel','Term Search',null);
insert into codes values ('msg','tab.redactionPanel','Workspace',null);
insert into codes values ('msg','tab.revisionPanel','To approve',null);
insert into codes values ('msg','tab.adminPanel','Administration',null);

-- Values of seraches with all values
insert into codes values ('msg','msg.value.all','ALL',null);

insert into codes values ('msg','btn.addTerm','ADD TERM',null);
insert into codes values ('msg','btn.delete','DELETE',null);
insert into codes values ('msg','btn.save','SAVE',null);
insert into codes values ('msg','btn.edit','EDIT',null);
insert into codes values ('msg','btn.submit','SUBMIT',null);
insert into codes values ('msg','btn.approve','APPROVE',null);
insert into codes values ('msg','btn.disapprove','DISAPPROVE',null);
insert into codes values ('msg','btn.disapproveAll','Disapprove All',null);
insert into codes values ('msg','btn.approveAll','Approve All',null);
insert into codes values ('msg','btn.create','CREATE',null);
insert into codes values ('msg','btn.escape','ESCAPE',null);
insert into codes values ('msg','btn.cancel','CANCEL',null);
insert into codes values ('msg','btn.abort','ABORT',null);
insert into codes values ('msg','btn.createNew','Create New',null);
insert into codes values ('msg','btn.search','SEARCH',null);
insert into codes values ('msg','btn.add','ADD',null);
insert into codes values ('msg','btn.displayAll','DisplayAll',null);

insert into codes values ('msg','msg.resource','Resource',null);
insert into codes values ('msg','msg.domain','Domain',null);
insert into codes values ('msg','msg.sourceLanguage','Source lang.',null);
insert into codes values ('msg','msg.targetLanguage','Target lang.',null);
insert into codes values ('msg','msg.addTitle','Click here to add entries',null);
insert into codes values ('msg','msg.displayAllTitle','Click here display all entries',null);
insert into codes values ('msg','msg.searchTitle','Click here to retreive entries',null);
insert into codes values ('msg','msg.workspaceEntries','Workspace Entries',null);
insert into codes values ('msg','msg.seachMessage','Input your search expression',null);
insert into codes values ('msg','msg.welcome','Welcome to olanto''s Terminology Manager',null);
insert into codes values ('msg','msg.statusEdited','Under edition',null);
insert into codes values ('msg','msg.statusPublic','Published',null);
insert into codes values ('msg','msg.statusRevision','To be revised',null);
insert into codes values ('msg','msg.termRank','Term Number: ',null);
insert into codes values ('msg','msg.definitionDetails','Definition details',null);
insert into codes values ('msg','msg.resourceDetails','Resource''s Details',null);
insert into codes values ('msg','msg.creationDetails','Creation Details',null);
insert into codes values ('msg','msg.extraDetails','Other Information',null);

insert into codes values ('msg','lbl.t.form','Form',null);
insert into codes values ('msg','lbl.t.lang','Language',null);
insert into codes values ('msg','lbl.t.source','Source',null);
insert into codes values ('msg','lbl.t.definition','Definition',null);
insert into codes values ('msg','lbl.t.source_definition','Definition''s source',null);
insert into codes values ('msg','lbl.t.status','Status',null);
insert into codes values ('msg','lbl.t.type','Type',null);
insert into codes values ('msg','lbl.t.note','Note',null);
insert into codes values ('msg','lbl.t.linguistic_note','Linguistic Note',null);
insert into codes values ('msg','lbl.t.technical_note','Technical Note',null);
insert into codes values ('msg','lbl.t.reference_note','Reference Note',null);
insert into codes values ('msg','lbl.t.linguistic_note_source','Linguistic Note Source',null);
insert into codes values ('msg','lbl.t.technical_note_source','Technical Note Source',null);
insert into codes values ('msg','lbl.t.reference_note_source','Reference Note Source',null);
insert into codes values ('msg','lbl.t.usage','Usage',null);
insert into codes values ('msg','lbl.t.extra','Extra',null);
insert into codes values ('msg','lbl.t.gender','Gender',null);
insert into codes values ('msg','lbl.t.part_of_speech','Part of speech',null);
insert into codes values ('msg','lbl.t.context','Context',null);
insert into codes values ('msg','lbl.t.source_context','Context''s source',null);
insert into codes values ('msg','lbl.t.geo_usage', 'Geo usage', null);
insert into codes values ('msg','lbl.t.creation', 'Creation date', null);
insert into codes values ('msg','lbl.t.created_by', 'Created by', null);
insert into codes values ('msg','lbl.t.modification', 'Modification date', null);
insert into codes values ('msg','lbl.t.last_modified_by', 'Modified by', null);
insert into codes values ('msg','lbl.t.cross_ref', 'Cross reference', null);
insert into codes values ('msg','lbl.t.extra_cross_ref', 'Extra Cross reference', null);
insert into codes values ('msg','lbl.t.image','Image',null);

insert into codes values ('msg','lbl.c.subject_field','Subject field',null);
insert into codes values ('msg','lbl.c.definition','Definition',null);
insert into codes values ('msg','lbl.c.resource','Resource',null);
insert into codes values ('msg','lbl.c.source_definition','Definition''s source',null);
insert into codes values ('msg','lbl.c.note','Note',null);
insert into codes values ('msg','lbl.c.image','Image',null);
insert into codes values ('msg','lbl.c.extra','Extra information',null);
insert into codes values ('msg','lbl.c.creation', 'Creation date', null);
insert into codes values ('msg','lbl.c.created_by', 'Created by', null);
insert into codes values ('msg','lbl.c.modification', 'Modification date', null);
insert into codes values ('msg','lbl.c.last_modified_by', 'Modified by', null);
insert into codes values ('msg','lbl.c.cross_ref', 'Cross reference', null);
insert into codes values ('msg','lbl.c.extra_cross_ref', 'Extra Cross reference', null);
insert into codes values ('msg','lbl.c.imported_ref', 'Imported reference', null);

insert into codes values ('msg','lbl.o.id', 'Owner''s ID', null);
insert into codes values ('msg','lbl.o.role', 'Owner''s Role', null);
insert into codes values ('msg','lbl.o.status', 'Owner''s Status', null);
insert into codes values ('msg','lbl.o.mailing', 'Owner''s Mailing', null);
insert into codes values ('msg','lbl.o.hash', 'Owner''s Password', null);
insert into codes values ('msg','lbl.o.first_name', 'Owner''s First Name', null);
insert into codes values ('msg','lbl.o.last_name', 'Owner''s Last Name', null);

insert into codes values ('msg','lbl.d.id', 'Domaine''s ID', null);
insert into codes values ('msg','lbl.d.default_name', 'Domaine''s Default Name', null);

insert into codes values ('msg','lbl.l.id', 'Language''s ID', null);
insert into codes values ('msg','lbl.l.default_name', 'Language''s Default Name', null);

insert into codes values ('msg','lbl.r.id', 'Resource''s ID', null);
insert into codes values ('msg','lbl.r.owner_id', 'Resource''s Owner', null);
insert into codes values ('msg','lbl.r.name', 'Resource''s Name', null);
insert into codes values ('msg','lbl.r.privacy', 'Resource''s Privacy', null);
insert into codes values ('msg','lbl.r.note', 'Resource''s Note', null);
insert into codes values ('msg','lbl.r.extra', 'Resource''s Extra', null);

-- system --- values visibility (visible, extra or hidden); position in form (1,2 or 3); visibility in search (visible, extra or hidden)
insert into codes values ('sys_field','t.lang','visible;1;visible;',null);
insert into codes values ('sys_field','t.form','visible;1;visible;',null);
insert into codes values ('sys_field','t.source','visible;1;visible',null);
insert into codes values ('sys_field','t.definition','visible;1;visible',null);
insert into codes values ('sys_field','t.source_definition','visible;1;visible',null);
insert into codes values ('sys_field','t.status','visible;2;visible',null);
insert into codes values ('sys_field','t.type','visible;2;visible',null);
insert into codes values ('sys_field','t.note','visible;2;visible',null);
insert into codes values ('sys_field','t.linguistic_note','visible;2;visible',null);
insert into codes values ('sys_field','t.technical_note','visible;2;visible',null);
insert into codes values ('sys_field','t.reference_note','visible;2;visible',null);
insert into codes values ('sys_field','t.linguistic_note_source','visible;2;extra',null);
insert into codes values ('sys_field','t.technical_note_source','visible;2;visible',null);
insert into codes values ('sys_field','t.reference_note_source','visible;2;extra',null);
insert into codes values ('sys_field','t.usage','extra;2;extra',null);
insert into codes values ('sys_field','t.gender','hidden;3;hidden',null);
insert into codes values ('sys_field','t.part_of_speech','hidden;3;hidden',null);
insert into codes values ('sys_field','t.context','visible;3;visible',null);
insert into codes values ('sys_field','t.source_context','visible;3;visible',null);
insert into codes values ('sys_field','t.geo_usage', 'extra;1;extra', null);
insert into codes values ('sys_field','t.creation', 'hidden;1;hidden', null);
insert into codes values ('sys_field','t.created_by', 'hidden;1;hidden', null);
insert into codes values ('sys_field','t.modification', 'hidden;1;extra', null);
insert into codes values ('sys_field','t.last_modified_by', 'hidden;1;visible', null);
insert into codes values ('sys_field','t.cross_ref', 'hidden;2;extra', null);
insert into codes values ('sys_field','t.extra_cross_ref', 'hidden;3;extra', null);
insert into codes values ('sys_field','t.image', 'hidden;3;extra', null);
insert into codes values ('sys_field','t.extra', 'hidden;3;extra', null);

insert into codes values ('sys_field','c.subject_field','visible;1;visible',null);
insert into codes values ('sys_field','c.definition','visible;1;visible',null);
insert into codes values ('sys_field','c.resource','visible;2;visible',null);
insert into codes values ('sys_field','c.source_definition','visible;2;visible',null);
insert into codes values ('sys_field','c.note','visible;3;visible',null);
insert into codes values ('sys_field','c.creation', 'hidden;1;hidden', null);
insert into codes values ('sys_field','c.created_by', 'hidden;1;hidden', null);
insert into codes values ('sys_field','c.modification', 'hidden;1;extra', null);
insert into codes values ('sys_field','c.last_modified_by', 'hidden;1;extra', null);
insert into codes values ('sys_field','c.cross_ref', 'hidden;2;extra', null);
insert into codes values ('sys_field','c.extra_cross_ref', 'hidden;3;extra', null);
insert into codes values ('sys_field','c.imported_ref', 'hidden;3;extra', null);
insert into codes values ('sys_field','c.image', 'hidden;3;extra', null);
insert into codes values ('sys_field','c.extra', 'hidden;3;extra', null);

insert into codes values ('sys_field','o.id', 'visible;1;visible', null);
insert into codes values ('sys_field','o.role', 'visible;1;visible', null);
insert into codes values ('sys_field','o.status', 'visible;1;visible', null);
insert into codes values ('sys_field','o.mailing', 'visible;1;visible', null);
insert into codes values ('sys_field','o.hash', 'visible;1;visible', null);
insert into codes values ('sys_field','o.first_name', 'visible;1;visible', null);
insert into codes values ('sys_field','o.last_name', 'visible;1;visible', null);

insert into codes values ('sys_field','r.id', 'visible;1;visible', null);
insert into codes values ('sys_field','r.owner_id', 'visible;1;visible', null);
insert into codes values ('sys_field','r.name', 'visible;1;visible', null);
insert into codes values ('sys_field','r.privacy', 'visible;1;visible', null);
insert into codes values ('sys_field','r.note', 'visible;1;visible', null);
insert into codes values ('sys_field','r.extra', 'visible;1;visible', null);

insert into codes values ('sys_field','l.id', 'visible;1;visible', null);
insert into codes values ('sys_field','l.default_name', 'visible;1;visible', null);

insert into codes values ('sys_field','d.id', 'visible;1;visible', null);
insert into codes values ('sys_field','d.default_name', 'visible;1;visible', null);


commit;
select * from codes;   


create table codeslang
 (id_language varchar(5) not null,
  code_type varchar(32) not null,
  code_value varchar(64) not null,
  code_value_lang varchar(64) not null,
 code_extra_lang varchar(256) ,
  PRIMARY KEY (id_language,code_type,code_value))
AUTO_INCREMENT=1000,DEFAULT CHARSET=utf8;

-- ADMIN, REVISOR, REDACTOR, READER
insert into codeslang values ('FR','role','ADMIN','Administrateur',null);
insert into codeslang values ('FR','role','REVISOR','Réviseur',null);
insert into codeslang values ('FR','role','REDACTOR','Rédacteur',null);
insert into codeslang values ('FR','role','READER','Lecteur',null);
-- fullForm, acronym, abbreviation, shortForm, variant, phrase
insert into codeslang values ('FR','term_type','fullForm','Forme complète',null);


-- msg
insert into codeslang values ('FR', 'msg','tab.redactionPanel','Workspace','Espace de Travail');

insert into codeslang values ('FR','msg','tab.searchPanel','?','Recherche des Termes');
insert into codeslang values ('FR','msg','msg.welcome','?','Bienvenue dans le gestionnaire de terminologie Olanto');
insert into codeslang values ('FR','msg','btn.search','?','Chercher');


commit;
select * from codeslang;   

-- --------------------------------- 
 
create or replace view v_codeprodlang as
select
  l.id_language,
  c.code_type,
  c.code_value,
  c.code_extra,
  c.code_default
from codes c , languages l;

select * from v_codeprodlang where id_language='FR' and code_type in ('role', 'term_type');

create or replace view v_codifications as
select
  c.id_language,
  c.code_type,
  c.code_value,
  c.code_extra,
  ifnull(cl.code_value_lang,c.code_value) code_value_lang,
  ifnull(cl.code_extra_lang,c.code_extra) code_extra_lang,
  c.code_default
from v_codeprodlang c left join (  codeslang cl)
   on ( c.id_language=cl.id_language
   and c.code_type=cl.code_type
   and c.code_value=cl.code_value)
order by c.code_default desc, code_value_lang;

select * from v_codifications;

create or replace view vj_codifications as
SELECT uuid()  uuid,
v_codifications.* FROM v_codifications;

select * from vj_codifications where id_language='EN' and code_type='role';
select * from vj_codifications where  id_language='FR' and code_type in ('role', 'term_type');


select * from vj_codifications where id_language='FR' and code_type='term_partofspeech';
select * from vj_codifications where id_language='FR' and code_type='term_type';
select * from vj_codifications where id_language='AR' and code_type='term_type';

select * from vj_codifications where id_language='FR' and code_type='msg';


