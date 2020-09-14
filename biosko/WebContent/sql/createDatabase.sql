drop table if exists Filmovi; 
drop table if exists Karta; 
drop table if exists Projekcije; 
drop table if exists Sale; 
drop table if exists Sedista;
drop table if exists Tipovi_Projekcija; 
drop table if exists Users; 
drop table if exists Zanrovi; 

create table Filmovi(
	ID integer primary key, 
	Naziv varchar(50) not null, 
	Reziser varchar(50) default null,
	Glumci varchar(1000) default null,
	Zanrovi varchar(1000) default null, 
	Trajanje int(50) not null, 
	Distributer varchar(50) not null,
	Zemlja_Porekla varchar(50) not null, 
	Godina_Proizvodnje int(50) not null, 
	Opis varchar(500) default null,
	Status varchar(50) default "Active"
);

CREATE TABLE Karta (
  ID integer PRIMARY KEY,
  ID_Projekcije int(50) NOT NULL,
  ID_Sedista int(50) NOT NULL,
  VremeProdaje datetime NOT NULL,
  Korisnik varchar(50) NOT NULL
);

CREATE TABLE Projekcije (
  ID integer PRIMARY KEY,
  ID_Filma int(50) NOT NULL,
  TipProjekcije varchar(50) NULL,
  ID_Sale int(50) NOT NULL,
  Termin datetime NOT NULL,
  CenaKarte int(50) NOT NULL,
  Administrator varchar(50) NOT NULL,
  Status varchar(50) default "Active",
  MaksimumKarata int(100) DEFAULT 5 not null, 
  BrojProdanihKarata int(100) default 0, 
  KrajTermina datetime not null
);

CREATE TABLE Sedista (
  ID integer PRIMARY KEY,
  ID_Sale varchar(50) NOT NULL,
  Broj_Sedista int(50) NOT NULL
);

CREATE TABLE Sale (
  ID integer PRIMARY KEY,
  Naziv varchar(50) NOT NULL,
  ID_Tipova_Projekcija varchar(50) NOT NULL
);

CREATE TABLE Zanrovi (
  ID integer PRIMARY KEY,
  Zanr varchar(50) NOT NULL
);

CREATE TABLE Users (
  ID integer PRIMARY KEY,
  Username varchar(50) NOT NULL,
  Password varchar(50) NOT NULL,
  DatumRegistracije varchar(50)NOT NULL,
  Uloga varchar(50) NOT NULL
);

CREATE TABLE Tipovi_Projekcija (
  ID integer PRIMARY KEY,
  Naziv varchar(50) NOT NULL
);

insert into Users(Username,Password, DatumRegistracije,Uloga,Status) values ('d','d','2020-09-09', 'obicanKorisnik', 'Active');
insert into Users(Username,Password, DatumRegistracije,Uloga,Status) values ('b','b','2020-09-09', 'obicanKorisnik', 'Active');
insert into Users(Username,Password, DatumRegistracije,Uloga,Status) values ('obican','obican','2020-09-09', 'obicanKorisnik', 'Active');
insert into Users(Username,Password,DatumRegistracije,Uloga,Status) values ('ele','ele', '2020-09-09', 'Admin', 'Active');
insert into Users(Username,Password,DatumRegistracije,Uloga,Status) values ('Lele','Lele', '2020-09-09', 'Admin', 'Active');

INSERT INTO Zanrovi(Zanr) VALUES ("Akcija");
INSERT INTO Zanrovi(Zanr) VALUES ("Avantura");
INSERT INTO Zanrovi(Zanr) VALUES ("Animacija");
INSERT INTO Zanrovi(Zanr) VALUES ("Biografski");
INSERT INTO Zanrovi(Zanr) VALUES ("Western");
INSERT INTO Zanrovi(Zanr) VALUES ("Komedija");
INSERT INTO Zanrovi(Zanr) VALUES ("Kriminalisticki");
INSERT INTO Zanrovi(Zanr) VALUES ("Deciji");
INSERT INTO Zanrovi(Zanr) VALUES ("Porodicni");
INSERT INTO Zanrovi(Zanr) VALUES ("Tragicni");
INSERT INTO Zanrovi(Zanr) VALUES ("Fantastika");
INSERT INTO Zanrovi(Zanr) VALUES ("NeunesenZanr");
INSERT INTO Zanrovi(Zanr) VALUES ("Ljubavni");

INSERT INTO Filmovi(Naziv,Reziser,Glumci,Zanrovi,Trajanje,Distributer,Zemlja_Porekla,Godina_Proizvodnje,Opis) VALUES("Tenet","Christopher Nolan","Robert Pattinson;Elizabeth Debicki","Ljubavni",115,"Paramount Pictures","USA",2020," Armed with only one word, Tenet, and fighting for the survival of the entire world, a Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time. ");--pogledati zanrove koje imam u modelu--
INSERT INTO Filmovi(Naziv,Reziser,Glumci,Zanrovi,Trajanje,Distributer,Zemlja_Porekla,Godina_Proizvodnje,Opis) VALUES("Knives out","Rian Johnson","Daniel Craig;Chris Evans","Kriminalisticki",115,"WPO","USA",2019,"A detective investigates the death of a patriarch of an eccentric, combative family.");--pogledati zanrove koje imam u modelu--
INSERT INTO Filmovi(Naziv,Reziser,Glumci,Zanrovi,Trajanje,Distributer,Zemlja_Porekla,Godina_Proizvodnje,Opis) VALUES("Shazam!","David Sandberg","Zachary Levi;Mark Strong","Akcija;Avantura",132,"Warner Bros.","Canada",2019," A newly fostered young boy in search of his mother instead finds unexpected super powers and soon gains a powerful enemy. ");--pogledati zanrove koje imam u modelu--
INSERT INTO Filmovi(Naziv,Reziser,Glumci,Zanrovi,Trajanje,Distributer,Zemlja_Porekla,Godina_Proizvodnje,Opis) VALUES("It is kind of a funny story","Anna Boden","Keir Gilchrist;Emma Roberts","Komedija",101,"Focus Features","USA",2010," A clinically depressed teenager gets a new start after he checks himself into an adult psychiatric ward.");--pogledati zanrove koje imam u modelu--
INSERT INTO Filmovi(Naziv,Reziser,Glumci,Zanrovi,Trajanje,Distributer,Zemlja_Porekla,Godina_Proizvodnje,Opis) VALUES("Iron man","Jon Favreau","Robert Downey Jr;Terrence Howard","Akcija",126,"Marvel Enterprises","USA",2008," After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil. ");--pogledati zanrove koje imam u modelu--
INSERT INTO Filmovi(Naziv,Reziser,Glumci,Zanrovi,Trajanje,Distributer,Zemlja_Porekla,Godina_Proizvodnje,Opis) VALUES("Thor Ragnarok","Taika Waititi","Chris Hemsworth;Cate Blanchett","Akcija;Avantura",130,"Marvel Studios","USA",2017," Imprisoned on the planet Sakaar, Thor must race against time to return to Asgard and stop Ragnarök, the destruction of his world, at the hands of the powerful and ruthless villain Hela. ");--pogledati zanrove koje imam u modelu--
INSERT INTO Filmovi(Naziv,Reziser,Glumci,Zanrovi,Trajanje,Distributer,Zemlja_Porekla,Godina_Proizvodnje,Opis) VALUES("Her","Spike Jonze","Joaquin Phoneix;Amy Adams","Fantastika",126,"Annapurna Pictures","USA",2013,"In a near future, a lonely writer develops an unlikely relationship with an operating system designed to meet his every need. ");--pogledati zanrove koje imam u modelu--
INSERT INTO Filmovi(Naziv,Reziser,Glumci,Zanrovi,Trajanje,Distributer,Zemlja_Porekla,Godina_Proizvodnje,Opis) VALUES("Dumb and dubmer","Peter Farrelly","Jim Carrey;Jeff Daniels","Komedija",107,"New Line Cinema","USA",1994," After a woman leaves a briefcase at the airport terminal, a dumb limo driver and his dumber friend set out on a hilarious cross-country road trip to Aspen to return it. ");--pogledati zanrove koje imam u modelu--
INSERT INTO Filmovi(Naziv,Reziser,Glumci,Zanrovi,Trajanje,Distributer,Zemlja_Porekla,Godina_Proizvodnje,Opis) VALUES("The Shawshank Redemption","Frank Darabont","Tim Robbins;Morgan Freeman","Biografski",142,"Paramount Pictures","USA",1994," Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency. ");--pogledati zanrove koje imam u modelu--
INSERT INTO Filmovi(Naziv,Reziser,Glumci,Zanrovi,Trajanje,Distributer,Zemlja_Porekla,Godina_Proizvodnje,Opis) VALUES("The Perks of Being a Wallflower","Stephen Chbosky","Logan Lerman;Ema Watson","Ljubavni",103,"Summit Entertainment","USA",2012," An introvert freshman is taken under the wings of two seniors who welcome him to the real world. ");--pogledati zanrove koje imam u modelu--

insert into Tipovi_Projekcija(Naziv) values ('2D'); 
insert into Tipovi_Projekcija(Naziv) values ('3D'); 
insert into Tipovi_Projekcija(Naziv) values ('4D'); 
insert into Tipovi_Projekcija(Naziv) values ('5D'); 

insert into Sale(Naziv, ID_Tipova_Projekcija) values ('Sala1', 1); 
insert into Sale(Naziv, ID_Tipova_Projekcija) values ('Sala2', 2); 
insert into Sale(Naziv, ID_Tipova_Projekcija) values ('Sala3', 3); 
insert into Sale(Naziv, ID_Tipova_Projekcija) values ('Sala4', 4);

insert into Sedista(ID_Sale, Broj_Sedista) values('1', 1);
insert into Sedista(ID_Sale, Broj_Sedista) values('1', 2);
insert into Sedista(ID_Sale, Broj_Sedista) values('1', 3);
insert into Sedista(ID_Sale, Broj_Sedista) values('1', 4);
insert into Sedista(ID_Sale, Broj_Sedista) values('1', 5);

insert into Sedista(ID_Sale, Broj_Sedista) values('2', 1);
insert into Sedista(ID_Sale, Broj_Sedista) values('2', 2);
insert into Sedista(ID_Sale, Broj_Sedista) values('2', 3);
insert into Sedista(ID_Sale, Broj_Sedista) values('2', 4);
insert into Sedista(ID_Sale, Broj_Sedista) values('2', 5);

insert into Sedista(ID_Sale, Broj_Sedista) values('3', 1);
insert into Sedista(ID_Sale, Broj_Sedista) values('3', 2);
insert into Sedista(ID_Sale, Broj_Sedista) values('3', 3);
insert into Sedista(ID_Sale, Broj_Sedista) values('3', 4);
insert into Sedista(ID_Sale, Broj_Sedista) values('3', 5);

insert into Sedista(ID_Sale, Broj_Sedista) values('4', 1);
insert into Sedista(ID_Sale, Broj_Sedista) values('4', 2);
insert into Sedista(ID_Sale, Broj_Sedista) values('4', 3);
insert into Sedista(ID_Sale, Broj_Sedista) values('4', 4);
insert into Sedista(ID_Sale, Broj_Sedista) values('4', 5);


--moraju biti 
insert into Projekcije(ID_Filma, TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values (1, '4D', 2, '2020-09-13 15:00',
150,'a','Active',3,2, '2020-09-09 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(2,'3D',2,'2020-09-13 15:00', 340, 'a', 'Active', 5,0,'2020-09-13 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(4,'3D',2,'2020-09-13 15:00', 340, 'a', 'Active', 5,0,'2020-09-13 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(5,'3D',2,'2020-09-13 15:00', 340, 'a', 'Active', 5,0,'2020-09-13 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(7,'3D',2,'2020-09-13 15:00', 340, 'a', 'Active', 5,0,'2020-09-13 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(8,'3D',2,'2020-09-13 15:00', 340, 'a', 'Active', 5,0,'2020-09-13 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(9,'3D',2,'2020-09-13 15:00', 340, 'a', 'Active', 5,0,'2020-09-13 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(10,'3D',2,'2020-09-13 15:00', 340, 'a', 'Active', 5,0,'2020-09-13 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(10,'3D',2,'2020-09-12 09:00', 340, 'a', 'Active', 5,0,'2020-09-12 12:00');

insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(10,'3D',2,'2020-09-14 09:00', 340, 'a', 'Active', 5,0,'2020-09-14 12:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(9,'3D',2,'2020-09-14 09:00', 340, 'a', 'Active', 5,0,'2020-09-14 12:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(5,'3D',2,'2020-09-14 09:00', 340, 'a', 'Active', 5,0,'2020-09-14 12:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(2,'3D',2,'2020-09-14 09:00', 340, 'a', 'Active', 5,0,'2020-09-14 12:00');

insert into Projekcije(ID_Filma, TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values (1, '4D', 2, '2020-09-15 15:00',
150,'a','Active',3,2, '2020-09-15 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(2,'3D',2,'2020-09-15 15:00', 340, 'a', 'Active', 5,0,'2020-09-15 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(4,'3D',2,'2020-09-15 15:00', 340, 'a', 'Active', 5,0,'2020-09-15 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(5,'3D',2,'2020-09-15 15:00', 340, 'a', 'Active', 5,0,'2020-09-15 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(7,'3D',2,'2020-09-15 15:00', 340, 'a', 'Active', 5,0,'2020-09-15 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(8,'3D',2,'2020-09-15 15:00', 340, 'a', 'Active', 5,0,'2020-09-15 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(9,'3D',2,'2020-09-15 15:00', 340, 'a', 'Active', 5,0,'2020-09-15 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(10,'3D',2,'2020-09-15 15:00', 340, 'a', 'Active', 5,0,'2020-09-15 17:00');


insert into Projekcije(ID_Filma, TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values (1, '4D', 2, '2020-09-16 15:00',
150,'a','Active',3,2, '2020-09-16 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(2,'3D',2,'2020-09-16 15:00', 340, 'a', 'Active', 5,0,'2020-09-16 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(4,'3D',2,'2020-09-16 15:00', 340, 'a', 'Active', 5,0,'2020-09-16 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(5,'3D',2,'2020-09-16 15:00', 340, 'a', 'Active', 5,0,'2020-09-16 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(7,'3D',2,'2020-09-16 15:00', 340, 'a', 'Active', 5,0,'2020-09-16 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(8,'3D',2,'2020-09-16 15:00', 340, 'a', 'Active', 5,0,'2020-09-16 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(9,'3D',2,'2020-09-16 15:00', 340, 'a', 'Active', 5,0,'2020-09-16 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(10,'3D',2,'2020-09-16 15:00', 340, 'a', 'Active', 5,0,'2020-09-16 17:00');

insert into Projekcije(ID_Filma, TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values (1, '4D', 2, '2020-09-17 15:00',
150,'a','Active',3,2, '2020-09-17 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(2,'3D',2,'2020-09-17 15:00', 340, 'a', 'Active', 5,0,'2020-09-17 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(4,'3D',2,'2020-09-17 15:00', 340, 'a', 'Active', 5,0,'2020-09-17 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(5,'3D',2,'2020-09-17 15:00', 340, 'a', 'Active', 5,0,'2020-09-17 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(7,'3D',2,'2020-09-17 15:00', 340, 'a', 'Active', 5,0,'2020-09-17 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(8,'3D',2,'2020-09-17 15:00', 340, 'a', 'Active', 5,0,'2020-09-17 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(9,'3D',2,'2020-09-17 15:00', 340, 'a', 'Active', 5,0,'2020-09-17 17:00');
insert into Projekcije(ID_Filma,TipProjekcije,ID_Sale,Termin,CenaKarte,Administrator,Status,MaksimumKarata,BrojProdanihKarata,KrajTermina) values 
(10,'3D',2,'2020-09-17 15:00', 340, 'a', 'Active', 5,0,'2020-09-17 17:00');
