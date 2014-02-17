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