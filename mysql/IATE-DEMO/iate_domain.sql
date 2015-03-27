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

commit;

/* there is some mistake ...
00	97882
04	143496
08	22096
1	255
1,	22
10	28942
12	114747
16	27337
2	13
20	19763
24	62293
28	87463
32	71041
36	45903
4	23
4,	1
40	4553
44	42647
48	58864
52	47398
56	71650
60	4167
64	16765
66	12586
68	54333
72	1352
76	1446
*/