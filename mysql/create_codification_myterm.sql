-- MYTERM SCHEMA
-- 
-- created 7.1.2014 by Jacques Guyot 
-- modified   by 

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
insert into codes values ('term_type','abbreviation',null,null);
insert into codes values ('term_type','shortForm',null,null);
insert into codes values ('term_type','variant',null,null);
insert into codes values ('term_type','phrase',null,null);
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



-- msg
insert into codes values ('msg','msg.welcome','Welcome to olanto''s Terminology Manager',null);
insert into codes values ('msg','btn.search','Search',null);


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
select * from vj_codifications where id_language='FR' and code_type='term_type';
select * from vj_codifications where id_language='AR' and code_type='term_type';

select * from vj_codifications where id_language='FR' and code_type='msg';


