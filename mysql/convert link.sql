SELECT * FROM myterm.terms ;
SELECT * FROM myterm.terms where term_source like '%http%';
SELECT * FROM myterm.terms where term_definition like '%http%';
SELECT * FROM myterm.terms where term_context like '%http%';
SELECT * FROM myterm.terms where term_note like '%http%';

SELECT count(*) FROM myterm.terms where term_source like '%http%';
SELECT count(*)  FROM myterm.terms where term_definition like '%http%';
SELECT count(*)  FROM myterm.terms where term_context like '%http%';
SELECT count(*)  FROM myterm.terms where term_note like '%http%';


SELECT term_source, SUBSTRING_INDEX((SUBSTRING_INDEX((SUBSTRING_INDEX(term_source, 'http://', -1)), '/', 1)), '.', -2) FROM myterm.terms where term_source like '%http%';

SELECT  term_form, term_source, 
 replace(replace(replace(
          concat('[[links!http',SUBSTRING_INDEX(term_source, 'http', -1), ']]')
            ,').]]',']]')
            ,'.]]',']]') 
            ,')]]',']]'),
  replace(replace(
  replace(term_source,SUBSTRING_INDEX(term_source, 'http', -1),
  replace(replace(replace(
          concat('[[links!http',SUBSTRING_INDEX(term_source, 'http', -1), ']]')
            ,').]]',']]')
            ,'.]]',']]') 
            ,')]]',']]'))
            ,'(http[[',' --> [[')
           ,'http[[',' --> [[')
 FROM myterm.terms where term_source like '%http%';


