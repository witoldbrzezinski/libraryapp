CREATE TABLE user_role(
user_id int NOT NULL,
role_id int NOT NULL,
PRIMARY KEY (user_id,role_id),
CONSTRAINT user_role_fk_1
FOREIGN KEY (user_id) REFERENCES users(id),
CONSTRAINT use_role_fk_2
FOREIGN KEY (role_id) REFERENCES roles(id)
);
