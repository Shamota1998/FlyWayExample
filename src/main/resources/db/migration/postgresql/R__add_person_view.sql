create or replace view person_profession AS
SELECT p.personid,
       p.first_name,
       p.last_name,
       prof.prof_desc
from person p ,
     profession prof,
     profession_persons pp
where
      pp.profession_profid = prof.profid
  and
      pp.persons_personid = p.personid;