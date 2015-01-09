insert into owners
  values (null,'demo','only for demo','demo','demo','ACTIVE','READER');
insert into owners
  values (null,'admin','only for demo','admin','admin','ACTIVE','ADMIN');
insert into owners
  values (null,'revisor','only for demo','revisor','revisor','ACTIVE','REVISOR');
insert into owners
  values (null,'redactor','only for demo','redactor','redactor','ACTIVE','REDACTOR');

-- Empty users_languages table--
-- delete from users_languages;


insert into users_languages select null, 'EN', id_owner, null from owners;
insert into users_languages select null, 'FR', id_owner, null from owners;
insert into users_languages select null, 'AR', id_owner, null from owners;

-- add rights for users on resources --
insert into users_resources select null, '1', id_owner, 'REDACTOR', null, null from owners where owner_first_name = 'redactor';
insert into users_resources select null, '1001', id_owner, 'REDACTOR', null, null from owners where owner_first_name = 'redactor';

insert into users_resources select null, '1', id_owner, 'REVISOR', null, null from owners where owner_first_name = 'revisor';
insert into users_resources select null, '1001', id_owner, 'REDACTOR', null, null from owners where owner_first_name = 'revisor';

insert into users_resources select null, '1', id_owner, 'ADMIN', null, null from owners where owner_first_name = 'admin';
insert into users_resources select null, '1001', id_owner, 'ADMIN', null, null from owners where owner_first_name = 'admin';

commit;