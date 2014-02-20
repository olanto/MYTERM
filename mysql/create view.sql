
create or replace view v_sourcetarget as
select t1.term_form source, t1.id_language solang,
       t2.term_form cible, t2.id_language talang,
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

select * from v_sourcetarget where source like 'mye' and solang='FR';
select * from v_sourcetarget where source like 'sand%' and solang='EN';
select * from v_sourcetarget where source like 'CESAP' and solang='FR';