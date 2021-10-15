INSERT INTO authority (id, authority, code) VALUES (1, 'ADMIN', 0);
INSERT INTO authority (id, authority, code) VALUES (2, 'USER', 1);
INSERT INTO customer (id, email, iban, password, sold) VALUES (nextval('customer_id_seq'), 'test@yopmail.com', '123456789', '$2a$10$.NSIwXrQoaSy1SFwJH3O2eUUho4FS8xOpiM94K3Cx5JrRkpmVSJoi', 130);
INSERT INTO customer (id, email, password, sold) VALUES (nextval('customer_id_seq'), 'test2@yopmail.com', '$2a$10$.NSIwXrQoaSy1SFwJH3O2eUUho4FS8xOpiM94K3Cx5JrRkpmVSJoi', 110);
INSERT INTO customer (id, email, password, sold) VALUES (nextval('customer_id_seq'), 'test3@yopmail.com', '$2a$10$.NSIwXrQoaSy1SFwJH3O2eUUho4FS8xOpiM94K3Cx5JrRkpmVSJoi', 210);
INSERT INTO customer_contacts (user_id, contacts_id) VALUES (1, 2);
INSERT INTO customer_contacts (user_id, contacts_id) VALUES (2, 1);
INSERT INTO internal_transaction (id, amount, date, description, receiver_id, sender_id) VALUES (nextval('internal_transaction_id_seq'), 130, '13-01-2017 17:09:42', 'description', '1', '2');