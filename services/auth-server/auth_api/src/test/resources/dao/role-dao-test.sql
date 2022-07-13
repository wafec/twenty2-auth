INSERT INTO roles (id, name) VALUES (1, 'test_admin');
INSERT INTO claims (id, val) VALUES (1, 'claim1');
INSERT INTO claims (id, val) VALUES (2, 'claim2');
INSERT INTO claims (id, val) VALUES (3, 'claim3');
INSERT INTO role_claims (role_id, claim_id) VALUES (1, 1);
INSERT INTO role_claims (role_id, claim_id) VALUES (1, 2);