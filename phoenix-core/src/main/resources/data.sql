-- ROLES START
INSERT INTO ROL_ROLE VALUES (
  'ROLE_ADMIN'
);
INSERT INTO ROL_ROLE VALUES (
  'ROLE_USER'
);
-- ROLES END


-- USERS START
INSERT INTO USR_USER VALUES (
  'admin', '$2a$04$BT2NWmlIgbX.OWtOwSlmpO3U3bQ5r/8Gd96WmrL112.SYqUSiiCNm', 'Mate', 'Karolyi', 27, 'phoenix.noreply.test@gmail.com'
);

INSERT INTO USR_USER VALUES (
  'user1', '$2a$04$BT2NWmlIgbX.OWtOwSlmpO3U3bQ5r/8Gd96WmrL112.SYqUSiiCNm', 'Gergo', 'Pap', 44, 'test2@localhost'
);

INSERT INTO USR_USER VALUES (
  'user2', '$2a$04$BT2NWmlIgbX.OWtOwSlmpO3U3bQ5r/8Gd96WmrL112.SYqUSiiCNm', 'Attila', 'Kovacs', 33, 'test3@localhost'
);

INSERT INTO USR_USER VALUES (
  'user3', '$2a$04$BT2NWmlIgbX.OWtOwSlmpO3U3bQ5r/8Gd96WmrL112.SYqUSiiCNm', 'Miklos', 'Nagy', 15, 'test4@localhost'
);

INSERT INTO USR_USER VALUES (
  'user4', '$2a$04$BT2NWmlIgbX.OWtOwSlmpO3U3bQ5r/8Gd96WmrL112.SYqUSiiCNm', 'David', 'Varadi', 50, 'test5@localhost'
);

INSERT INTO USR_USER VALUES (
  'user5', '$2a$04$BT2NWmlIgbX.OWtOwSlmpO3U3bQ5r/8Gd96WmrL112.SYqUSiiCNm', 'Jozsef', 'Almasi', 44, 'test6@localhost'
);

INSERT INTO USR_USER VALUES (
  'user6', '$2a$04$BT2NWmlIgbX.OWtOwSlmpO3U3bQ5r/8Gd96WmrL112.SYqUSiiCNm', 'Ferenc', 'Kis', 30, 'test7@localhost'
);
-- USERS END


-- USER-ROLE ASSOCIATION START
INSERT INTO USR_ROLE VALUES (
  'admin', 'ROLE_ADMIN'
);

INSERT INTO USR_ROLE VALUES (
  'user1', 'ROLE_USER'
);

INSERT INTO USR_ROLE VALUES (
  'user2', 'ROLE_USER'
);

INSERT INTO USR_ROLE VALUES (
  'user3', 'ROLE_USER'
);

INSERT INTO USR_ROLE VALUES (
  'user4', 'ROLE_USER'
);

INSERT INTO USR_ROLE VALUES (
  'user5', 'ROLE_USER'
);

INSERT INTO USR_ROLE VALUES (
  'user6', 'ROLE_USER'
);
-- USER-ROLE ASSOCIATION END