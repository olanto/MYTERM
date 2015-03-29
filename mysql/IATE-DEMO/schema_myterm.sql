-- MYTERM SCHEMA
-- 
-- created 10.1.2014 by Jacques Guyot 
-- modified   by 

drop table if exists descriptors;
drop table if exists terms;
drop table if exists langsets;
drop table if exists concepts_domains;
drop table if exists concepts;
drop table if exists translations;
drop table if exists users_languages;
drop table if exists languages;
drop table if exists resources_domains;
drop table if exists domains;
drop table if exists users_resources;
drop table if exists resources;
drop table if exists owners;
drop table if exists dummy;
drop table if exists versionDB;

-- --------------------------------- 

create table versionDB(id_version bigint not null auto_increment,
  version varchar(32) not null,
  version_note varchar(255),
   PRIMARY KEY (id_version))
AUTO_INCREMENT=1000,DEFAULT CHARSET=utf8;

insert into versionDB values(1,'1','initial creation');

create table dummy(x varchar(1));
insert into dummy values('X');

create table owners
 (id_owner bigint not null auto_increment,
  owner_first_name varchar(32) not null,
  owner_last_name varchar(32) not null,
  owner_mailing varchar(255) not null,
  owner_hash varchar(255),
  owner_status varchar(16) not null, -- ACTIVE, INACTIVE, DORMANT
  owner_roles varchar(16)  default 'READER', -- ADMIN, REVISOR, REDACTOR, READER
  PRIMARY KEY (id_owner))
AUTO_INCREMENT=1000,DEFAULT CHARSET=utf8;

insert into owners
  values (1,'demo','only for demo','demo','demo','ACTIVE','READER');
commit; 
select * from owners;   

-- --------------------------------- 


create table resources
 (id_resource bigint not null auto_increment,
  id_owner bigint  not null,
  resource_name varchar(32)  not null,
  resource_privacy varchar(16)  not null, -- PRIVATE, PUBLIC
  resource_note text,
  extra text,
PRIMARY KEY (id_resource),
CONSTRAINT resources_FK1_owners
   FOREIGN KEY (id_owner)
   REFERENCES owners (id_owner)
ON DELETE NO ACTION ON UPDATE CASCADE)
AUTO_INCREMENT=1000,DEFAULT CHARSET=utf8;

insert into resources
  values (1,1,'IATE','PUBLIC',null,null);
commit; 
select * from resources;   

-- --------------------------------- 

create table users_resources
 (id_link bigint not null auto_increment,
  id_resource bigint not null,
  id_owner bigint  not null,
  owner_roles varchar(16)  default 'READER', -- ADMIN, REVISOR, REDACTOR, READER
  default_resource varchar(16) default 'PUBLIC', 
  extra text,
PRIMARY KEY (id_link),
CONSTRAINT users_resources_FK1_owners
   FOREIGN KEY (id_owner)
   REFERENCES owners (id_owner),
CONSTRAINT users_resources_FK2_resources
   FOREIGN KEY (id_resource)
   REFERENCES resources (id_resource)
ON DELETE NO ACTION ON UPDATE CASCADE)
AUTO_INCREMENT=1000,DEFAULT CHARSET=utf8;

-- --------------------------------- 

insert into users_resources select null, '1', id_owner, 'READER', null, null from owners where owner_first_name = 'demo';
commit; 
select * from users_resources; 

create table domains
 (id_domain bigint not null auto_increment,
  domain_default_name varchar(64)  not null,
   PRIMARY KEY (id_domain))
AUTO_INCREMENT=1000,DEFAULT CHARSET=utf8;

commit; 
select * from domains; 

-- --------------------------------- 

create table resources_domains
 (id_domain bigint not null,
  id_resource bigint not null,
PRIMARY KEY (id_domain,id_resource),
CONSTRAINT resources_domains_FK1_resources
   FOREIGN KEY (id_resource)
   REFERENCES resources (id_resource)
ON DELETE NO ACTION ON UPDATE CASCADE,
CONSTRAINT resources_domains_FK2_domains
   FOREIGN KEY (id_domain)
   REFERENCES domains (id_domain)
ON DELETE NO ACTION ON UPDATE CASCADE
)
DEFAULT CHARSET=utf8;

commit; 
select * from resources_domains; 



-- --------------------------------- 

create table languages
 (id_language varchar(5)  not null,
  language_default_name varchar(32)  not null,
   PRIMARY KEY (id_language))
AUTO_INCREMENT=1000,DEFAULT CHARSET=utf8;

insert into languages values ('EN','English');
insert into languages values ('FR','French');
insert into languages values ('ES','Spanish');
insert into languages values ('DE','German');
insert into languages values ('PT','Portuguese');

insert into languages values ('BG','Bulgarian');
insert into languages values ('CS','Czech');
insert into languages values ('DA','Danish');
insert into languages values ('EL','Greek');
insert into languages values ('ET','Estonian');
insert into languages values ('FI','Finnish');
insert into languages values ('GA','Irish');
insert into languages values ('HR','Croatian');
insert into languages values ('HU','Hungarian');
insert into languages values ('IT','Italian');
insert into languages values ('LA','Latin');
insert into languages values ('LT','Lithuanian');
insert into languages values ('LV','Latvian');
insert into languages values ('MT','Maltese');
insert into languages values ('NL','Dutch');
insert into languages values ('PL','Polish');
insert into languages values ('RO','Romanian');
insert into languages values ('SK','Slovak');
insert into languages values ('SL','Slovene');
insert into languages values ('SV','Swedish');
insert into languages values ('MUL','Multilingual');

insert into languages values ('JA','Japonese');


commit; 
select * from languages; 


create table users_languages
 (id_link bigint not null auto_increment,
  id_language varchar(5)  not null,
  id_owner bigint  not null,
   extra text,
PRIMARY KEY (id_link),
CONSTRAINT users_languages_FK1_owners
   FOREIGN KEY (id_owner)
   REFERENCES owners (id_owner),
CONSTRAINT users_languages_FK2_languages
   FOREIGN KEY (id_language)
   REFERENCES languages (id_language)
ON DELETE NO ACTION ON UPDATE CASCADE)
AUTO_INCREMENT=1000,DEFAULT CHARSET=utf8;

commit; 
select * from users_languages; 


-- --------------------------------- 


create table translations
 (id_language varchar(5)  not null,
  type_obj varchar(32) not null, -- DOMAINS, ...
  id_obj bigint not null,
  label varchar(4000) not null,
PRIMARY KEY (id_language,type_obj,id_obj),
CONSTRAINT translations_FK1_languages
   FOREIGN KEY (id_language)
   REFERENCES languages (id_language)
ON DELETE NO ACTION ON UPDATE CASCADE
)
DEFAULT CHARSET=utf8;

-- insert into translations values ('EN','DOMAINS',1,'Computer');
commit; 
select * from translations; 


-- --------------------------------- 



create table concepts
 (id_concept bigint not null auto_increment,
  id_resource bigint not null,
  subject_field varchar(128),
  concept_definition text,
  concept_source_definition text,
  concept_note text,
  creation date,
  create_by bigint,
  lastmodified date,
  lastmodified_by bigint,
 extra text,
 image text,
 extcrossref text,
crossref text,
PRIMARY KEY (id_concept),
CONSTRAINT concepts_FK1_resources
   FOREIGN KEY (id_resource)
   REFERENCES resources (id_resource),
CONSTRAINT concepts_FK2_create_by
   FOREIGN KEY (create_by)
   REFERENCES owners (id_owner)
ON DELETE NO ACTION ON UPDATE CASCADE,
CONSTRAINT concepts_FK3_lastmodified_by
   FOREIGN KEY (lastmodified_by)
   REFERENCES owners (id_owner)
ON DELETE NO ACTION ON UPDATE CASCADE
)
AUTO_INCREMENT=1000,DEFAULT CHARSET=utf8;

commit; 
select * from concepts; 


alter table concepts add  importedref text;

alter table concepts add  sup0 text;
alter table concepts add  sup1 text;
alter table concepts add  sup2 text;
alter table concepts add  sup3 text;
alter table concepts add  sup4 text;

-- --------------------------------- 

create table concepts_domains
 (id_domain bigint  not null,
  id_concept bigint  not null,
PRIMARY KEY (id_domain,id_concept),
CONSTRAINT concepts_domains_FK1_concept
   FOREIGN KEY (id_concept)
   REFERENCES concepts (id_concept)
ON DELETE NO ACTION ON UPDATE CASCADE,
CONSTRAINT concepts_FK2_domains
   FOREIGN KEY (id_domain)
   REFERENCES domains (id_domain)
ON DELETE NO ACTION ON UPDATE CASCADE
)
DEFAULT CHARSET=utf8;

commit; 
select * from concepts_domains; 

-- --------------------------------- 

create table langsets
 (id_langset bigint not null auto_increment,
  id_language varchar(5)  not null,
  id_concept bigint not null,
  langset_note varchar(512),
  seq int default 0,
  extra text,
PRIMARY KEY (id_langset),
CONSTRAINT langsets_FK1_concept
   FOREIGN KEY (id_concept)
   REFERENCES concepts (id_concept)
ON DELETE NO ACTION ON UPDATE CASCADE,
CONSTRAINT langsets_FK2_languages
   FOREIGN KEY (id_language)
   REFERENCES languages (id_language)
ON DELETE NO ACTION ON UPDATE CASCADE

)
AUTO_INCREMENT=1000,DEFAULT CHARSET=utf8;

commit; 
select * from langsets; 

-- --------------------------------- 


create table terms
 (id_term bigint not null auto_increment,
  id_language varchar(5)  not null,
  id_langset bigint  not null,
  term_form varchar(8000)  not null,
  term_source text,
  term_definition text,
  term_source_definition text,
  term_usage text,
  term_context text, -- example
  term_source_context text,
  term_note text,
  term_type varchar(24), -- fullForm, acronym, abbreviation, shortForm, variant, phrase
  term_partofspeech varchar(16), -- noun, verb, adjective, adverb, properNoun,other
  term_gender varchar(16), -- masculine, feminine, neuter, other
  term_admin_status varchar(32), -- admittedTerm-admn-sts, ...
  term_geo_usage varchar(32), -- en-US
  creation date,
  create_by bigint,
  lastmodified date,
  lastmodified_by bigint,
  status char(1)  not null, -- published p, to be revised r, draft e
  seq int default 0,
  extra text,
 extcrossref text,
crossref text,
PRIMARY KEY (id_term),
CONSTRAINT terms_FK1_langset
   FOREIGN KEY (id_langset)
   REFERENCES langsets (id_langset)
ON DELETE NO ACTION ON UPDATE CASCADE,
CONSTRAINT terms_FK2_languages
   FOREIGN KEY (id_language)
   REFERENCES languages (id_language)
ON DELETE NO ACTION ON UPDATE CASCADE,
CONSTRAINT terms_FK3_create_by
   FOREIGN KEY (create_by)
   REFERENCES owners (id_owner)
ON DELETE NO ACTION ON UPDATE CASCADE,
CONSTRAINT terms_FK4_lastmodified_by
   FOREIGN KEY (lastmodified_by)
   REFERENCES owners (id_owner)
ON DELETE NO ACTION ON UPDATE CASCADE

)
AUTO_INCREMENT=1000,DEFAULT CHARSET=utf8;

commit; 
select * from terms; 

alter table terms add  importedref text;
alter table terms add  image text;


alter table terms add  sup0 text;
alter table terms add  sup1 text;
alter table terms add  sup2 text;
alter table terms add  sup3 text;
alter table terms add  sup4 text;

-- --------------------------------- 


create table descriptors
 (type_obj char(1),  -- concept, langset, term
  id_obj bigint,
  header TEXT,
  footer TEXT,
PRIMARY KEY (type_obj,id_obj)
)
DEFAULT CHARSET=utf8;

commit; 
select * from descriptors; 

-- indexes

create index terms_index_custo2 on terms(id_language,term_form(16));

-- set owners default

update concepts set create_by=1 where create_by is null;
update concepts set lastmodified_by=1 where lastmodified_by is null;

update terms set create_by=1 where create_by is null;
update terms set lastmodified_by=1 where lastmodified_by is null;

commit;

SET foreign_key_checks = 0; 
DELETE FROM `myterm`.`owners` WHERE `id_owner`='1001';
SET foreign_key_checks = 1;
