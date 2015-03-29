-- create iate domains first level

drop table if exists iate_domain;

create table iate_domain
 (iate_id varchar(2),
  english_name varchar(64)  not null,
   PRIMARY KEY (iate_id))
DEFAULT CHARSET=utf8;

insert into iate_domain values('00','NO SUBJECT DOMAIN');
insert into iate_domain values('04','POLITICS');
insert into iate_domain values('08','INTERNATIONAL RELATIONS');
insert into iate_domain values('10','EUROPEAN COMMUNITIES');
insert into iate_domain values('12','LAW');
insert into iate_domain values('16','ECONOMICS');
insert into iate_domain values('20','TRADE');
insert into iate_domain values('24','FINANCE');
insert into iate_domain values('28','SOCIAL QUESTIONS');
insert into iate_domain values('32','EDUCATION AND COMMUNICATIONS');
insert into iate_domain values('36','SCIENCE');
insert into iate_domain values('44','EMPLOYMENT AND WORKING CONDITIONS');
insert into iate_domain values('48','TRANSPORT');
insert into iate_domain values('52','ENVIRONMENT');
insert into iate_domain values('56','AGRICULTURE,  FORESTRY AND FISHERIES');
insert into iate_domain values('60','AGRI-FOODSTUFFS');
insert into iate_domain values('64','PRODUCTION, TECHNOLOGY AND RESEARCH');
insert into iate_domain values('66','ENERGY');
insert into iate_domain values('68','INDUSTRY');
insert into iate_domain values('72','GEOGRAPHY');
insert into iate_domain values('76','INTERNATIONAL ORGANISATIONS');

commit;

-- delete from domains;

insert into domains select null, english_name from iate_domain;
SELECT * FROM myterm.domains;

commit;

select distinct substr(subject_field,1,2) from concepts;
select substr(subject_field,1,2), count(*) from concepts group by substr(subject_field,1,2);


SET SQL_SAFE_UPDATES=0;
update concepts set subject_field=substr(subject_field,1,2);

commit;

select subject_field, count(*) from concepts group by subject_field;

update concepts a set subject_field=(select english_name b from iate_domain 
                                      where a.subject_field=iate_id) 
  where subject_field in (select iate_id from iate_domain);

select distinct subject_field from concepts;


-- rollback;
commit;

-- unify multiple load

update concepts a set id_resource=1;

delete from resources where id_resource>1;

commit;







/* there is some mistake ...
, 	20
00	93179
04	100952
08	38188
1	244
1,	22
10	36173
12	104439
16	36210
2	15
2,	1
20	22142
24	69097
28	168724
32	122002
36	83355
4	87
4,	4
40	6243
42	1
43	2
44	24883
48	92949
52	54957
56	71954
60	4737
64	39987
66	17769
68	132855
72	1707
76	2353
80	1*/