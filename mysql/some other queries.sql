commit;
select * from terms where id_term > 10880;
select * from langsets where id_concept > 3530;
select * from concepts where id_concept > 3530;
SELECT v.* FROM Vj_Source v WHERE v.solang = 'EN' AND v.status like 'e' AND v.id_owner = 1001;