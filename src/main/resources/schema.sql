/*
CREATE TABLE users (
  id serial NOT NULL,
  name varchar(45) NOT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE user_group (
  id serial NOT NULL,
  name varchar(45) NOT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE user_user_group (
  user_id int NOT NULL,
  user_group_id int NOT NULL,
  PRIMARY KEY (user_id, user_group_id)
  ,
  CONSTRAINT fk_UserGroup_has_User_User1 FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_UserGroup_has_User_UserGroup1 FOREIGN KEY (user_group_id) REFERENCES user_group (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE INDEX fk_UserGroup_has_User_User1 ON user_user_group (user_id);
CREATE INDEX fk_UserGroup_has_User_UserGroup1 ON user_user_group (user_group_id);

CREATE TABLE product (
  id serial NOT NULL,
  description varchar(150) NOT NULL,
  price decimal(10,2) NOT NULL,
  creditor_id int NOT NULL,
  debtors_group_id int NOT NULL,
  creation_date date NOT NULL,
  PRIMARY KEY(id)
  ,
  CONSTRAINT fk_product_user1 FOREIGN KEY (creditor_id) REFERENCES users (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_product_user_group1 FOREIGN KEY (debtors_group_id) REFERENCES user_group (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE INDEX fk_product_user_group1 ON product (debtors_group_id);
CREATE INDEX fk_product_user1 ON product (creditor_id);

CREATE TABLE debt
(
  id serial NOT NULL,
  creditor_id int NOT NULL,
  debtor_id int NOT NULL,
  product_id int NOT NULL,
  amount decimal(10,2) NOT NULL,
  creation_date date NOT NULL,
  PRIMARY KEY(id)
  ,
  CONSTRAINT fk_debt_product1 FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_debt_user1 FOREIGN KEY (creditor_id) REFERENCES users (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_debt_user2 FOREIGN KEY (debtor_id) REFERENCES users (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE INDEX fk_debt_product1 ON debt (product_id);
CREATE INDEX fk_debt_user1 ON debt (creditor_id);
CREATE INDEX fk_debt_user2 ON debt (debtor_id);

INSERT INTO users (id, name) VALUES (1, 'Bartek'), (2, 'Aga'), (3, 'Rafał');
INSERT INTO user_group (id, name) VALUES (1, 'Wszyscy'), (2, 'Rafał i Aga'), (3, 'Bartek');
INSERT INTO user_user_group (user_id, user_group_id) VALUES (1, 1), (1, 3), (2, 1), (2, 2), (3, 1), (3, 2);*/
