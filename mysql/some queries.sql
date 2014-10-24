drop index  terms_index_custo2 on terms;
create index terms_index_custo2 on terms(id_language,term_form(16));


select t1.term_form, t1.id_language,
       t2.term_form, t2.id_language,
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
   and t1.term_form like '%horse%'
   and t1.id_language='EN';

select t1.term_form, t1.id_language,
       t2.term_form, t2.id_language,
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
   and t1.term_form = 'horse'
   and t1.id_language='EN'
   and t2.id_language='RU'
;


select t1.term_form, t1.id_language
 from terms t1
where 
   t1.term_form like '%horse%'
   and t1.id_language='EN';

select t1.term_form, t1.id_language
 from terms t1
where 
   t1.term_form = 'horse'
   and t1.id_language='EN';

select * from terms where id_term<1100;

select * from concepts where id_resource=1000;

select distinct subject_field from concepts where id_resource=1000;

select distinct subject_field from concepts;
