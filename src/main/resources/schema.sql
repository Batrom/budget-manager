/*
CREATE TABLE users (
  id serial NOT NULL,
  email varchar(45) NOT NULL,
  login varchar(45) NOT NULL,
  password varchar(45) NOT NULL,
  active boolean NOT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE member (
  id serial NOT NULL,
  name varchar(45) NOT NULL,
  user_id int NOT NULL,
  PRIMARY KEY(id)
  ,
  CONSTRAINT fk_member_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE member_group (
  id serial NOT NULL,
  name varchar(45) NOT NULL,
  user_id int NOT NULL,
  PRIMARY KEY(id)
  ,
  CONSTRAINT fk_member_group_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE member_member_group (
  member_id int NOT NULL,
  member_group_id int NOT NULL,
  PRIMARY KEY (member_id, member_group_id)
  ,
  CONSTRAINT fk_member_group_member FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_member_member_group FOREIGN KEY (member_group_id) REFERENCES member_group (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE INDEX idx_member_group_member ON member_member_group (member_id);
CREATE INDEX idx_member_member_group ON member_member_group (member_group_id);

CREATE TABLE product (
  id serial NOT NULL,
  description varchar(150) NOT NULL,
  price decimal(10,2) NOT NULL,
  creditor_id int NOT NULL,
  debtor_group_id int NOT NULL,
  category varchar(45) NOT NULL,
  creation_date date NOT NULL,
  PRIMARY KEY(id)
  ,
  CONSTRAINT fk_product_member FOREIGN KEY (creditor_id) REFERENCES member (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_product_member_group FOREIGN KEY (debtor_group_id) REFERENCES member_group (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE INDEX idx_product_member_group ON product (debtor_group_id);
CREATE INDEX idx_product_member ON product (creditor_id);

CREATE TABLE debt
(
  id serial NOT NULL,
  creditor_id int NOT NULL,
  debtor_id int NOT NULL,
  amount decimal(10,2) NOT NULL,
  date date NOT NULL,
  PRIMARY KEY(id)
  ,
  CONSTRAINT fk_debt_member FOREIGN KEY (creditor_id) REFERENCES member (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_debt_member1 FOREIGN KEY (debtor_id) REFERENCES member (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE INDEX idx_debt_member ON debt (creditor_id);
CREATE INDEX idx_debt_member1 ON debt (debtor_id);

CREATE TABLE role (
  id serial NOT NULL,
  name varchar(45) NOT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE user_role (
  user_id int NOT NULL,
  role_id int NOT NULL,
  PRIMARY KEY (user_id, role_id)
  ,
  CONSTRAINT fk_role_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE INDEX idx_role_user ON user_role (user_id);
CREATE INDEX idx_user_role ON user_role (role_id);

CREATE TABLE duty
(
  id serial NOT NULL,
  description varchar(45) NOT NULL,
  orderer_id int NOT NULL,
  executor_id int NOT NULL,
  date date NOT NULL,
  points int NOT NULL,
  done boolean NOT NULL,
  PRIMARY KEY(id)
  ,
  CONSTRAINT fk_duty_member FOREIGN KEY (orderer_id) REFERENCES member (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_duty_member1 FOREIGN KEY (executor_id) REFERENCES member (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE INDEX fk_duty_member ON duty (orderer_id);
CREATE INDEX fk_duty_member1 ON duty (executor_id);

INSERT INTO members (id, name) VALUES (1, 'Bartek'), (2, 'Aga'), (3, 'Rafał');
INSERT INTO member_group (id, name) VALUES (1, 'Wszyscy'), (2, 'Aga i Rafał'), (3, 'Bartek');
INSERT INTO member_member_group (member_id, member_group_id) VALUES (1, 1), (1, 3), (2, 1), (2, 2), (3, 1), (3, 2);

-- DELETE FROM debt
-- ALTER TABLE debt DROP COLUMN product_id;*/
