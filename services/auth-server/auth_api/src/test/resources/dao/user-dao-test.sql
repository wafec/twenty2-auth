INSERT INTO users (id, name, passwd) VALUES (1, 'test_user1', 'Password123');
INSERT INTO claims (id, val) VALUES (1, 'test_claim_user1.claim1');
INSERT INTO claims (id, val) VALUES (2, 'test_claim_user1.claim2');
INSERT INTO user_claims (user_id, claim_id) VALUES (1, 1);
INSERT INTO user_claims (user_id, claim_id) VALUES (1, 2);