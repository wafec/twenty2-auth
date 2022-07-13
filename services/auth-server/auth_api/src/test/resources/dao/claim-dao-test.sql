INSERT INTO users (id, name, passwd) VALUES (1, 'user1', 'Password123');
INSERT INTO claims (id, val) VALUES (1, 'claim1');
INSERT INTO claims (id, val) VALUES (2, 'claim2');
INSERT INTO roles (id, name) VALUES (1, 'role1');
INSERT INTO user_claims (user_id, claim_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO role_claims (role_id, claim_id) VALUES (1, 1);
INSERT INTO role_claims (role_id, claim_id) VALUES (1, 2);