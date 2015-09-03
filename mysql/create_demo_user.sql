insert into owners
  values (null,'joe','DEMO only for demo','demo','demo','ACTIVE','READER');
-- insert into owners values (null,'boss','ADMIN only for demo','admin','admin','ACTIVE','ADMIN');
insert into owners
  values (null,'linx','REVISOR only for demo','revisor','revisor','ACTIVE','REVISOR');
insert into owners
  values (null,'eva','REDACTOR only for demo','redactor','redactor','ACTIVE','REDACTOR');

-- Empty users_languages table--
-- delete from users_languages;


insert into users_languages select null, 'EN', id_owner, null from owners where id_owner<>1;
insert into users_languages select null, 'FR', id_owner, null from owners where id_owner<>1;
insert into users_languages select null, 'AR', id_owner, null from owners where id_owner<>1;

-- add rights for users on resources --
insert into users_resources select null, '1', id_owner, 'REDACTOR', null, null from owners where owner_first_name = 'eva';
-- insert into users_resources select null, '1001', id_owner, 'REDACTOR', null, null from owners where owner_first_name = 'redactor';

insert into users_resources select null, '1', id_owner, 'REVISOR', null, null from owners where owner_first_name = 'linx';
-- insert into users_resources select null, '1001', id_owner, 'REDACTOR', null, null from owners where owner_first_name = 'revisor';

-- insert into users_resources select null, '1', id_owner, 'ADMIN', null, null from owners where owner_first_name = 'boss';
-- insert into users_resources select null, '1001', id_owner, 'ADMIN', null, null from owners where owner_first_name = 'admin';

commit;