
create or replace view v_sourcetarget as
select t1.term_form source, t1.id_term id_term_source, t1.id_language solang,
       t2.term_form target, t2.id_term id_term_target, t2.id_language talang,
	   c.id_concept,
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
;

create or replace view vj_reslang as
SELECT uuid()  uuid,
v_reslang.* FROM v_reslang;


create or replace view v_sourcetarget as
select t1.term_form source, t1.id_term id_term_source, t1.id_language solang,
       t2.term_form target, t2.id_term id_term_target, t2.id_language talang,
	   c.id_concept,
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
;
create or replace view vj_sourcetarget as
SELECT uuid()  uuid,
v_sourcetarget.* FROM v_sourcetarget;


select * from v_sourcetarget where source like 'mye' and solang='FR';
select * from v_sourcetarget where source like 'tuna%' and solang='EN';
select * from v_sourcetarget where source like 'sand%' and solang='EN';
select * from v_sourcetarget where source like 'CESAP' and solang='FR';
select * from v_sourcetarget where resoutermsrce_name='TESTTBX' and  solang='FR';

create or replace view v_source as
select t1.term_form source, t1.id_term id_term_source, t1.id_language solang,
	   c.id_concept,
       r.resource_name
 from terms t1, langsets l1,
	  concepts c,
      resources r
where t1.id_langset=l1.id_langset
   and l1.id_concept=c.id_concept
    and c.id_resource=r.id_resource
 ;

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
       c.lastmodified, c.lastmodified_by,
       c.extra
 from 
 	  concepts c,
      resources r
where 
    c.id_resource=r.id_resource
;


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

select  resource_name, id_language, count(*) nbterms from v_reslang group by resource_name, id_language; 
