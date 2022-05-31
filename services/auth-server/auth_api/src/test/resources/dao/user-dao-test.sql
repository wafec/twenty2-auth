INSERT INTO users (id, name, passwd) VALUES (1, 'test_user1', 'Password123');
INSERT INTO claims (id, val) VALUES (1, 'test_claim_user1.claim1');
INSERT INTO claims (id, val) VALUES (2, 'test_claim_user1.claim2');
INSERT INTO claims (id, val) VALUES (3, 'test_claim_user1.claim3');
INSERT INTO user_claims (user_id, claim_id) VALUES (1, 1);
INSERT INTO user_claims (user_id, claim_id) VALUES (1, 2);
INSERT INTO roles (id, name) VALUES (1, 'test_admin');
INSERT INTO roles (id, name) VALUES (2, 'test_user');
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);