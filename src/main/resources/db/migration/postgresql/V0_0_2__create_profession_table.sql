create table profession
(
    profid    serial not null,
    prof_desc varchar(255),
    primary key (profid)
);
create table profession_persons
(
    profession_profid int4 not null,
    persons_personid  int4 not null
);

alter table profession_persons
    add constraint personFK foreign key (persons_personid) references person;

alter table profession_persons
    add constraint professionFK foreign key (profession_profid) references profession;