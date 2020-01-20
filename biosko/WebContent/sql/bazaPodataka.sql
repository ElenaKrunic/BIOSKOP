drop index if exists korisnickoIme;
drop table if exists korisnici;
drop table if exists filmovi;
drop table if exists zanrovi;

create table korisnici(
	korisnickoIme varchar(30) primary key not null,
	lozinka text not null,
	uloga varchar(5) not null default 'Korisnik'
);

insert into korisnici(korisnickoIme,lozinka,uloga) values ('elelele','mrkva17', 'Administrator');
insert into korisnici(korisnickoIme,lozinka,uloga) values ('Genius', 'mis', 'Administrator');
insert into korisnici(korisnickoIme,lozinka,uloga) values('Voda','vatra', 'Korisnik');


create table filmovi(
	id integer primary key,
	naziv text not null, 
	zanr text not null, 
	trajanje integer not null, 
	distributer text not null, 
	zemlja porijekla text not null,
	godina proizvodnje integer not null
);

create table zanrovi (
	id integer primary key, 
	zanr text not null
);

insert into zanrovi (zanr) values ("Avantura");
insert into zanrovi (zanr) values ("Akcija");
insert into zanrovi (zanr) values ("Djeciji");
insert into zanrovi (zanr) values ("Dokumentarni");
insert into zanrovi (zanr) values ("Biografski");
insert into zanrovi (zanr) values ("Drama");
insert into zanrovi (zanr) values ("Kriminalisticki");
insert into zanrovi (zanr) values ("Misterija");
insert into zanrovi (zanr) values ("SciFi");
insert into zanrovi (zanr) values ("Ratni");
insert into zanrovi (zanr) values ("Psiholoski");
insert into zanrovi (zanr) values ("Musical");
