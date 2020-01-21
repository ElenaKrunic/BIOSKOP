DROP INDEX IF EXISTS userName;

DROP TABLE IF EXISTS filmovi;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    userName VARCHAR(10) NOT NULL, 
    password VARCHAR(10) NOT NULL, 
    uloga VARCHAR(5) NOT NULL DEFAULT 'KORISNIK', 
    PRIMARY KEY(userName)
);
INSERT INTO users (userName, password, uloga) VALUES ('a', 'a', 'ADMIN');
INSERT INTO users (userName, password, uloga) VALUES ('b', 'b', 'KORISNIK');

CREATE TABLE filmovi (
    id INTEGER PRIMARY KEY, -- SQLite AUTO_INCREMENT
    naziv VARCHAR(50) NOT NULL, 
    reziser VARCHAR(50) NULL,
    glumci VARCHAR(50) NOT NULL,
    zanr VARCHAR NOT NULL DEFAULT 'Drama',
    trajanje 
    price DECIMAL(10, 2) NOT NULL DEFAULT 99999999.00
);
INSERT INTO products (name, price) VALUES ('Televizor marke Sony, 51 cm dijagonala', 22000);
INSERT INTO products (name, price) VALUES ('Sony digitalna kamera', 32000);
INSERT INTO products (name, price) VALUES ('Samsung monitor 17', 35000);
INSERT INTO products (name, price) VALUES ('Pioneer DVD pisac', 7100);

CREATE TABLE shoppingCartItems (
    userName VARCHAR(10) NOT NULL, 
    id INT NOT NULL, 
    productID BIGINT NOT NULL, 
    count INT NOT NULL DEFAULT 1, 
    PRIMARY KEY(userName, id), 
    FOREIGN KEY(userName) REFERENCES users(userName) ON DELETE RESTRICT, 
    FOREIGN KEY(productID) REFERENCES products(id) ON DELETE RESTRICT
);
CREATE INDEX userName ON shoppingCartItems(userName); -- SQLite ne može da doda indeks u okviru naredbe za kreiranje tabele
private int id;  //ne pisem u html-u
	private String naziv;
	private String reziser; //opcioni
	private String glumci;  //opciono
	private Zanr zanr;
	private int trajanje; 
	private String distributer;
	private String zemljaPorijekla;
	private int godinaProizvodnje;
	private String opis;