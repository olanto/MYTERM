-- MYTERM SCHEMA

-- patch to migrate to version 4.1
-- adding cross_ref

update `myterm`.`codes` set  `code_extra` = 'visible;4;visible' where `code_type` = 'sys_field' and `code_value` = 'c.cross_ref';
commit;

UPDATE `myterm`.`codeslang` SET `code_extra_lang`='Concept-Note' WHERE `id_language`='EN' and`code_type`='msg' and`code_value`='lbl.c.note';
UPDATE `myterm`.`codeslang` SET `code_extra_lang`='Note' WHERE `id_language`='FR' and`code_type`='msg' and`code_value`='lbl.c.note';


UPDATE `myterm`.`codeslang` SET `code_extra_lang`='Renvoi' WHERE `id_language`='FR' and`code_type`='msg' and`code_value`='lbl.c.cross_ref';


-- deploy new war