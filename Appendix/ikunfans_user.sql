create table ikunfans_user
(
    id       varchar(255) not null
        primary key,
    account  varchar(255) not null,
    password varchar(255) not null
);

INSERT INTO ikunfans.ikunfans_user (id, account, password) VALUES ('1791402656022798336', 'admin', '123456');
INSERT INTO ikunfans.ikunfans_user (id, account, password) VALUES ('1792486119106805760', 'ctey', '123456');
