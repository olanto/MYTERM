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
drop table if exists languages;
drop table if exists resources_domains;
drop table if exists domains;
drop table if exists resources;
drop table if exists owners;
drop table if exists dummy;

-- --------------------------------- 

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
  values (0,'anonymous','not defined','','','INACTIVE',null);
insert into owners
  values (1,'jacques','guyot','jacques@olanto.org','aaaabbbb','ACTIVE','ADMIN');
insert into owners
  values (null,'nizar','ghoula','nizar@olanto.org','fasdfasdfsadf','ACTIVE','ADMIN');
commit; 
select * from owners;   

-- --------------------------------- 


create table resources
 (id_resource bigint not null auto_increment,
  id_owner bigint  not null,
  resource_name varchar(32)  not null,
  resource_privacy varchar(16)  not null, -- PRIVATE, PUBLIC
  resource_note varchar(512),
  extra varchar(1024),
PRIMARY KEY (id_resource),
CONSTRAINT resources_FK1_owners
   FOREIGN KEY (id_owner)
   REFERENCES owners (id_owner)
ON DELETE NO ACTION ON UPDATE CASCADE)
AUTO_INCREMENT=1000,DEFAULT CHARSET=utf8;

insert into resources
  values (1,1,'INSERTED WITH SQL','PUBLIC',null,null);
commit; 
select * from resources;   

-- --------------------------------- 

create table domains
 (id_domain bigint not null auto_increment,
  domain_default_name varchar(32)  not null,
   PRIMARY KEY (id_domain))
AUTO_INCREMENT=1000,DEFAULT CHARSET=utf8;

insert into domains values (1,'Computer');
insert into domains values (2,'Astronomy');
insert into domains values (3,'Painting');
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

insert into resources_domains values (1,1);
insert into resources_domains values (2,1);
commit; 
select * from resources_domains; 



-- --------------------------------- 

create table languages
 (id_language varchar(5)  not null,
  language_default_name varchar(32)  not null,
   PRIMARY KEY (id_language))
AUTO_INCREMENT=1000,DEFAULT CHARSET=utf8;

insert into languages values ('EN','English');
insert into languages values ('EN-GB','English-GB');
insert into languages values ('FR','French');
insert into languages values ('ES','Spanish');
insert into languages values ('AR','Arab');
insert into languages values ('RU','Russian');
insert into languages values ('DE','German');
insert into languages values ('ZH','Chinese');
insert into languages values ('LA','Latina');
commit; 
select * from languages; 

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

insert into translations values ('EN','DOMAINS',1,'Computer');
insert into translations values ('EN','DOMAINS',2,'Astronomy');
insert into translations values ('EN','DOMAINS',3,'Painting');
insert into translations values ('FR','DOMAINS',1,'Ordinateur');
insert into translations values ('FR','DOMAINS',2,'Astronomie');
insert into translations values ('FR','DOMAINS',3,'Peinture');
commit; 
select * from translations; 


-- --------------------------------- 

create table concepts
 (id_concept bigint not null auto_increment,
  id_resource bigint not null,
  subject_field varchar(128),
  concept_definition varchar(512),
  concept_source_definition varchar(512),
  concept_note varchar(512),
  creation date,
  create_by bigint,
  lastmodified date,
  lastmodified_by bigint,
 extra varchar(1024),
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

insert into concepts values (1,1,null,null,null,null,null,null,null,null,null);
insert into concepts values (2,1,null,null,null,null,null,null,null,null,null);
commit; 
select * from concepts; 

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

insert into concepts_domains values (2,1);
insert into concepts_domains values (2,2);
commit; 
select * from concepts_domains; 

-- --------------------------------- 

create table langsets
 (id_langset bigint not null auto_increment,
  id_language varchar(5)  not null,
  id_concept bigint not null,
  langset_note varchar(512),
  seq int default 0,
  extra varchar(1024),
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

insert into langsets values (1,'EN',1,null,1,null);
insert into langsets values (2,'EN',2,null,1,null);
insert into langsets values (3,'FR',1,null,1,null);
insert into langsets values (4,'FR',2,null,1,null);
commit; 
select * from langsets; 

-- --------------------------------- 


create table terms
 (id_term bigint not null auto_increment,
  id_language varchar(5)  not null,
  id_langset bigint  not null,
  term_form varchar(8000)  not null,
  term_source varchar(512),
  term_definition varchar(2048),
  term_source_definition varchar(512),
  term_usage varchar(512),
  term_context varchar(2048), -- example
  term_source_context varchar(512),
  term_note varchar(2048),
  term_type varchar(16), -- fullForm, acronym, abbreviation, shortForm, variant, phrase
  term_partofspeech varchar(16), -- noun, verb, adjective, adverb, properNoun,other
  term_gender varchar(16), -- masculine, feminine, neuter, other
  term_admin_status varchar(32), -- admittedTerm-admn-sts, ...
  term_geo_usage varchar(32), -- en-US
  creation date,
  create_by bigint,
  lastmodified date,
  lastmodified_by bigint,
  status char(1)  not null, -- published, revised, edited
  seq int default 0,
  extra varchar(4096),
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

insert into terms values (1,'EN',1,
 'star','encyclodia ..',
  'a star is ','from dictionary ..','used to ...',
 'a new star in the sky','from book...','here a note','fullForm','noun','neuter','admittedTerm-admn-sts',null,
  '31.12.2000',1,null,null,'P',1,null);
insert into terms values (2,'EN',2,
 'moon','encyclodia ..',
  'a moon is ','from dictionary ..','used to ...',
 'a new moon in the night','from book...','here a note','fullForm','noun','neuter','admittedTerm-admn-sts',null,
  '31.12.2000',1,null,null,'P',1,null);
insert into terms values (3,'FR',3,
 'étoile','encyclodie ..',
  'une étoile est ....','du dictionnaire ..','utilisé ...',
 'une nouvelle étoile dans le ciel','du roman ...','ici une note','fullForm','noun','feminine','admittedTerm-admn-sts',null,
  '31.12.2000',1,null,null,'P',1,null);
insert into terms values (4,'FR',4,
 'lune','encyclodie ..',
  'la lune est le satellite ...','du dictionnaire ..','utilisé ...',
 'la lune est pleine ce soir','du roman ...','ici une note','fullForm','noun','feminine','admittedTerm-admn-sts',null,
  '31.12.2000',1,null,null,'P',1,null);
commit; 
select * from terms; 

-- --------------------------------- 


create table descriptors
 (type_obj char(1),  -- concept, langset, term
  id_obj bigint,
  header TEXT,
  footer TEXT,
PRIMARY KEY (type_obj,id_obj)
)
DEFAULT CHARSET=utf8;

insert into descriptors values ('C',1,
'<termEntry>
	<transacGrp>
		<transac type="terminologyManagementTransactions">origination</transac>
		<date>2012-08-10T14:35:13</date>
		<transacNote type="responsibility">xxxx</transacNote>
	</transacGrp>
	<transacGrp>
		<transac type="terminologyManagementTransactions">modification</transac>
		<date>2012-08-15T15:15:14</date>
		<transacNote type="responsibility">xxxx</transacNote>
	</transacGrp>
<descripGrp>
	<descrip type="Collection">WWW</descrip>
</descripGrp>
<descripGrp>
	<descrip type="Domain">Astronomy</descrip>
</descripGrp>',
'</termEntry>');
insert into descriptors values ('C',2,
'<termEntry>
	<transacGrp>
		<transac type="terminologyManagementTransactions">origination</transac>
		<date>2012-08-10T14:35:13</date>
		<transacNote type="responsibility">xxxx</transacNote>
	</transacGrp>
	<transacGrp>
		<transac type="terminologyManagementTransactions">modification</transac>
		<date>2012-08-15T15:15:14</date>
		<transacNote type="responsibility">xxxx</transacNote>
	</transacGrp>
<descripGrp>
	<descrip type="Collection">WWW</descrip>
</descripGrp>
<descripGrp>
	<descrip type="Domain">Astronomy</descrip>
</descripGrp>',
'</termEntry>');
insert into descriptors values ('L',1,
'<langSet xml:lang="EN">',
'</langSet>');
insert into descriptors values ('L',2,
'<langSet xml:lang="EN">',
'</langSet>');
insert into descriptors values ('L',3,
'<langSet xml:lang="FR">',
'</langSet>');
insert into descriptors values ('L',4,
'<langSet xml:lang="FR">',
'</langSet>');
insert into descriptors values ('T',1,
'<tig>
	<term>star</term>
	<transacGrp>
		<transac type="terminologyManagementTransactions">origination</transac>
		<date>2012-08-10T14:35:13</date>
		<transacNote type="responsibility">xxxx</transacNote>
	</transacGrp>
	<transacGrp>
		<transac type="terminologyManagementTransactions">modification</transac>
		<date>2012-08-15T15:15:14</date>
		<transacNote type="responsibility">xxxx</transacNote>
	</transacGrp>
		<descripGrp>
			<descrip type="Usage">Astronomy</descrip>
		</descripGrp>
	</tig>',
null);
insert into descriptors values ('T',2,
'<tig>
	<term>moon</term>
	<transacGrp>
		<transac type="terminologyManagementTransactions">origination</transac>
		<date>2012-08-10T14:35:13</date>
		<transacNote type="responsibility">xxxx</transacNote>
	</transacGrp>
	<transacGrp>
		<transac type="terminologyManagementTransactions">modification</transac>
		<date>2012-08-15T15:15:14</date>
		<transacNote type="responsibility">xxxx</transacNote>
	</transacGrp>
		<descripGrp>
			<descrip type="Usage">Astronomy</descrip>
		</descripGrp>
	</tig>',
null);
insert into descriptors values ('T',3,
'<tig>
	<term>étoile</term>
	<transacGrp>
		<transac type="terminologyManagementTransactions">origination</transac>
		<date>2012-08-10T14:35:13</date>
		<transacNote type="responsibility">xxxx</transacNote>
	</transacGrp>
	<transacGrp>
		<transac type="terminologyManagementTransactions">modification</transac>
		<date>2012-08-15T15:15:14</date>
		<transacNote type="responsibility">xxxx</transacNote>
	</transacGrp>
		<descripGrp>
			<descrip type="Usage">Astronomy</descrip>
		</descripGrp>
	</tig>',
null);
insert into descriptors values ('T',4,
'<tig>
	<term>lune</term>
	<transacGrp>
		<transac type="terminologyManagementTransactions">origination</transac>
		<date>2012-08-10T14:35:13</date>
		<transacNote type="responsibility">xxxx</transacNote>
	</transacGrp>
	<transacGrp>
		<transac type="terminologyManagementTransactions">modification</transac>
		<date>2012-08-15T15:15:14</date>
		<transacNote type="responsibility">xxxx</transacNote>
	</transacGrp>
		<descripGrp>
			<descrip type="Usage">Astronomy</descrip>
		</descripGrp>
	</tig>',
null);
commit; 
select * from descriptors; 

-- indexes

create index terms_index_custo2 on terms(id_language,term_form(16));


