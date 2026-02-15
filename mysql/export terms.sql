create or replace view v_conceptmoredetails as
    select 
        t1.id_term,
        t1.id_language,
        t1.term_form,
        t1.term_source,
        t1.term_definition,
        t1.term_note,
		t1.term_type,
        t1.status,
        c.id_concept,
		r.resource_name,
        l1.id_langset
    from
        terms t1,
        langsets l1,
        concepts c,
        resources r
    where
        t1.id_langset = l1.id_langset
            and l1.id_concept = c.id_concept
			and c.id_resource = r.id_resource;

select source.id_language,source.term_type,source.term_form,
       target.id_language,target.term_type,target.term_form,
       source.resource_name
 from v_conceptmoredetails source, v_conceptmoredetails target
    where source.id_concept=target.id_concept
	  and source.id_language="EN-GB"
	  and target.id_language="FR"
      and source.resource_name="CERN";


select * from v_conceptmoredetails source, v_conceptmoredetails target
    where source.id_concept=target.id_concept
	  and source.id_language="EN-GB"
	  and target.id_language="FR"
	  and r.resource_name="CERN";