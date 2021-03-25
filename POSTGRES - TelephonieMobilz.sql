create role telephonie_role login password '123456';

create database telephonie;

alter database telephonie owner to telephonie_role;


create sequence USER_SEQ increment 1 start 1;

create table utilisateur (
    IdUtilisateur varchar(50) NOT NULL DEFAULT CONCAT('USER-00', CAST(nextval('USER_SEQ') AS varchar(30))),
    Nom varchar(50),
    Prenom varchar(50),
    Numero varchar(50),
    Mdp varchar(50),
    primary key (IdUtilisateur)
);

create table UserToken(
    IdUtilisateur varchar(50) not NULL,
    token varchar(40) DEFAULT md5(CONCAT ('user',LOCALTIME)),
    foreign key (IdUtilisateur) references utilisateur(IdUtilisateur)
)
insert into UserToken(IdUtilisateur) VALUES (USER-001);
insert into utilisateur(Nom,Prenom,Numero,Mdp) values ('Hery', 'Toavina', '0326358687', md5('2001'));
insert into utilisateur(Nom,Prenom,Numero,Mdp) values ('Francky', 'Rado', '0326358688', md5('2000'));

create sequence DEPOT_SEQ increment 1 start 1;
create table depot (
    IdDepot varchar(50) NOT NULL DEFAULT CONCAT('DPT-00', CAST(nextval('DEPOT_SEQ') AS varchar(30))),
    IdUtilisateur varchar(50),
    Montant int,
    DateDepot timestamp,
    primary key (IdDepot),
    foreign key (IdUtilisateur) references utilisateur(IdUtilisateur)
);

create table depotAttente (
    IdDepot varchar(50),
    foreign key (IdDepot) references depot(IdDepot)
);

create table depotValider (
    IdDepot varchar(50),
    foreign key (IdDepot) references depot(IdDepot)
);

CREATE OR REPLACE FUNCTION insertAttente()
  RETURNS trigger AS
$$
BEGIN
         INSERT INTO depotAttente
         VALUES(NEW.IdDepot);

    RETURN NEW;
END;
$$
LANGUAGE 'plpgsql';

CREATE TRIGGER depotAttente_trigger
  AFTER INSERT
  ON depot
  FOR EACH ROW
  EXECUTE PROCEDURE insertAttente();

insert into depot (IdUtilisateur, Montant, DateDepot) values ('USER-001', 200000, CURRENT_TIMESTAMP);
insert into depot (IdUtilisateur, Montant, DateDepot) values ('USER-002', 300000, CURRENT_TIMESTAMP);


create view ALL_DEPOT_ATTENTE AS
 select * from depot where IdDepot in (select IdDepot from depotAttente ) and IdDepot not in (select IdDepot from depotValider);

create view ALL_DEPOT_VALIDER AS
 select * from depot where IdDepot in (select IdDepot from depotValider);
