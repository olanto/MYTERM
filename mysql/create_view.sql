 /*
create or replace view v_users_resources as
select o.id_owner, 
	   r.id_resource, r.resource_name,
	   r.resource_privacy, r.resource_note
 from resources r, owners o, users_resources ur
where r.id_resource = ur.id_resource
   and o.id_owner=ur.id_owner
union 
  select o.id_owner, 
	   r.id_resource, r.resource_name,
	   r.resource_privacy, r.resource_note
 from resources r, owners o, users_resources ur
where r.resource_privacy="PUBLIC"
;
*/

create or replace view v_users_resources as
select o.id_owner, o.owner_mailing,
	   r.id_resource, r.resource_name,
	   r.resource_privacy, r.resource_note,
       'ADMIN' owner_roles
 from resources r, owners o, users_resources ur
where r.id_resource = ur.id_resource
   and o.id_owner=ur.id_owner
   and ur.owner_roles='ADMIN'
union
select o.id_owner, o.owner_mailing, 
	   r.id_resource, r.resource_name,
	   r.resource_privacy, r.resource_note,
       'REVISOR' owner_roles
 from resources r, owners o, users_resources ur
where r.id_resource = ur.id_resource
   and o.id_owner=ur.id_owner
   and ur.owner_roles IN ('ADMIN','REVISOR')
union
select o.id_owner, o.owner_mailing, 
	   r.id_resource, r.resource_name,
	   r.resource_privacy, r.resource_note,
       'REDACTOR' owner_roles
 from resources r, owners o, users_resources ur
where r.id_resource = ur.id_resource
   and o.id_owner=ur.id_owner
   and ur.owner_roles IN ('ADMIN','REVISOR','REDACTOR')
union
select o.id_owner, o.owner_mailing, 
	   r.id_resource, r.resource_name,
	   r.resource_privacy, r.resource_note,
       'READER' owner_roles
 from resources r, owners o, users_resources ur
where r.id_resource = ur.id_resource
   and o.id_owner=ur.id_owner
   and ur.owner_roles IN ('ADMIN','REVISOR','REDACTOR','READER')
union
select o.id_owner, o.owner_mailing, 
	   r.id_resource, r.resource_name,
	   r.resource_privacy, r.resource_note,
       ur.owner_roles
 from resources r, owners o, users_resources ur
where r.id_resource = ur.id_resource
   and o.id_owner=ur.id_owner
union 
  select o.id_owner, o.owner_mailing, 
	   r.id_resource, r.resource_name,
	   r.resource_privacy, r.resource_note,
       'READER'
 from resources r, owners o, users_resources ur
where r.resource_privacy="PUBLIC"
;

select * from v_users_resources;
select * from v_users_resources WHERE  owner_mailing='demo';
select * from v_users_resources WHERE  owner_mailing='redactor';
select * from v_users_resources WHERE  owner_mailing='revisor';
select * from v_users_resources WHERE  owner_mailing='admin';

create or replace view vj_users_resources as
SELECT uuid()  uuid,
v_users_resources.* FROM v_users_resources;

select vj_users_resources.* FROM vj_users_resources; 

create or replace view v_sourcetarget as
select t1.term_form source, t1.id_term id_term_source, t1.id_language solang,
       t2.term_form target, t2.id_term id_term_target, t2.id_language talang,
	   t1.status status_source,
	   t2.status status_target,
	   c.id_concept,
	   c.subject_field,
	   r.id_resource,
	   r.resource_name
 from terms t1, langsets l1,
      terms t2, langsets l2,
	  concepts c,
      resources r
where t1.id_langset=l1.id_langset
   and l1.id_concept=c.id_concept
   and t2.id_langset=l2.id_langset
   and l2.id_concept=c.id_concept
   and c.id_resource=r.id_resource
   and l1.id_langset!=l2.id_langset
   and t1.id_term!=t2.id_term
;


/*
create or replace view v_sourcetarget as
select t1.term_form source, t1.id_term id_term_source, t1.id_language solang,
       t2.term_form target, t2.id_term id_term_target, t2.id_language talang,
	   t1.status status_source,
	   t2.status status_target,
	   c.id_concept,
	   c.subject_field,
	   r.id_resource,
	   r.resource_name,
	   vur.id_owner

 from terms t1, langsets l1,
      terms t2, langsets l2,
	  concepts c,
      resources r,
	  v_users_resources vur
where t1.id_langset=l1.id_langset
   and l1.id_concept=c.id_concept
   and t2.id_langset=l2.id_langset
   and l2.id_concept=c.id_concept
   and c.id_resource=r.id_resource
   and l1.id_langset!=l2.id_langset
   and t1.id_term!=t2.id_term
   and r.id_resource=vur.id_resource
;
*/

create or replace view vj_sourcetarget as
SELECT uuid()  uuid,
v_sourcetarget.* FROM v_sourcetarget;

select * from v_sourcetarget;
select * from v_sourcetarget where source like 'mye' and solang='FR' and status_source like 'p';
select * from v_sourcetarget where source like 'tuna%' and solang='EN';
select * from v_sourcetarget where source like 'sand%' and solang='EN';
select * from v_sourcetarget where source like 'CESAP' and solang='FR';
select * from v_sourcetarget where source like 'CESAP' and solang='FR';
select * from v_sourcetarget where resource_name='WIPO-TEST' and  solang='EN' and talang='FR';
select * from v_sourcetarget where id_resource in (1,1000)  and  solang='EN' and talang='FR';

select * from v_sourcetarget where id_resource in (1)  and  solang='EN' and talang='FR';


create or replace view v_conceptdetail as
select  
       t1.id_term,
       t1.id_language,
       t1.term_form,
       t1.term_source,
       t1.term_definition,
       t1.term_note,
       t1.status,
	   c.id_concept,
	   l1.id_langset
  from terms t1, langsets l1,
	  concepts c
where t1.id_langset=l1.id_langset
   and l1.id_concept=c.id_concept ;

create or replace view vj_conceptdetail as
SELECT uuid()  uuid,
v_conceptdetail.* FROM v_conceptdetail;


select * from v_conceptdetail where  id_concept=108300;


create or replace view v_source as
select t1.term_form source,
       t1.id_term id_term_source,
       t1.id_language solang,
	   t1.status status,
	   c.id_concept,
       t1.lastmodified_by,
       r.resource_name,
	   r.id_resource,
	   c.subject_field
 from terms t1, langsets l1,
	  concepts c,
      resources r
where t1.id_langset=l1.id_langset
	and l1.id_concept=c.id_concept
    and c.id_resource=r.id_resource ;

create or replace view vj_source as
SELECT uuid()  uuid,
v_source.* FROM v_source;

-- select * from v_source where status='e' and lastmodified_by=1070 ;

create or replace view v_editedbyowner as
select distinct id_concept,status,lastmodified_by from v_source;



select * from v_editedbyowner  where status='e' and lastmodified_by=1070 ;

create or replace view v_getformsbyconcept
as 
select c.id_concept,
  t.source, t.solang
from concepts c, v_source t
where c.id_concept=t.id_concept;


create or replace view v_conceptprodlang as
select c.*,   l.id_language
from concepts c , languages l;

create or replace view v_getformsbyconcept
as 
select c.id_concept,c.id_language,
ifnull(t.source,'?') source
from v_conceptprodlang c left join  v_source t
on( c.id_concept=t.id_concept and c.id_language=t.solang);


create or replace view vj_getformsbyconcept as
SELECT uuid()  uuid,
v_getformsbyconcept.* FROM v_getformsbyconcept;


select * from v_getformsbyconcept  where id_language='EN';
select * from v_getformsbyconcept  where id_language!='AR';

select Distinct id_concept from v_source where status = 'r' and solang IN ('EN') and id_resource in (1);

/*
create or replace view v_source as
select t1.term_form source, t1.id_term id_term_source, t1.id_language solang,
	   t1.status status,
	   c.id_concept,
       r.resource_name,
	   r.id_resource,
	   c.subject_field,
	   vur.id_owner
 from terms t1, langsets l1,
	  concepts c,
      resources r,
	  v_users_resources vur
where t1.id_langset=l1.id_langset
	and l1.id_concept=c.id_concept
    and c.id_resource=r.id_resource
    and vur.id_resource=r.id_resource
 ;
*/

create or replace view v_source_lang as
select distinct t1.id_language solang,
	   c.id_concept
 from terms t1, langsets l1,
	  concepts c
where t1.id_langset=l1.id_langset
   and l1.id_concept=c.id_concept
 ;

create or replace view v_conceptres as
select 
       r.resource_name,
       c.id_concept, c.subject_field,
       c.concept_definition,  c.concept_source_definition,
       c.concept_note,
       c.creation, c.create_by,
       o1.owner_first_name creator_first_name,
       c.lastmodified, c.lastmodified_by,
       o2.owner_first_name modifier_first_name,
       c.extra
 from 
 	  concepts c,
      resources r,
      owners o1,
      owners o2
where 
    c.id_resource=r.id_resource
    and c.create_by=o1.id_owner
   and c.lastmodified_by=o2.id_owner
;

create or replace view vj_conceptres as
SELECT uuid()  uuid,
v_conceptres.* FROM v_conceptres;

select * from v_conceptres where  id_concept=108300;

create or replace view v_rescon as
select 
       r.resource_name, r.resource_privacy, count(*) nb_concepts
 from 
 	  concepts c,
      resources r
where 
    c.id_resource=r.id_resource
 group by resource_name, r.resource_privacy
;

select  * from v_rescon ; 
select  * from v_rescon ; 

create or replace view v_reslang as
select 
       r.resource_name, l.id_language
 from 
 	  concepts c,
      resources r,
      langsets l
where 
    c.id_resource=r.id_resource
and c.id_concept=l.id_concept
 ;

create or replace view vj_reslang as
SELECT uuid()  uuid,
v_reslang.* FROM v_reslang;

create or replace view v_users_languages as
select o.id_owner, 
	   l.id_language, l.language_default_name

 from languages l, owners o, users_languages ul
where l.id_language = ul.id_language
   and o.id_owner=ul.id_owner
;

select * from v_users_languages;

create or replace view vj_users_languages as
SELECT uuid()  uuid,
v_users_languages.* FROM v_users_languages;


select  resource_name, id_language, count(*) nbterms from v_reslang group by resource_name, id_language; 

select * from vj_source;

