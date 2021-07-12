insert into warehouse (code, name) values ('OSAF', 'Fullfillment Osasco');
insert into warehouse (code, name) values ('CAJF', 'Fullfillment Cajamar');

insert into supervisor (id, name, warehouse_code) values ('27a40a9e-3838-4717-935d-b9f6f4a4f623', 'Monaliza', 'OSAF');
insert into supervisor (id, name, warehouse_code) values ('0f6df1fd-3767-4a9a-9705-66ce04275027', 'Marcus', 'OSAF');
insert into supervisor (id, name, warehouse_code) values ('740bbe6c-0a49-4c3b-92d8-90c1aa036064', 'Barbara', 'CAJF');

insert into seller (id, name) values ('5d5ba4e5-7919-4ffc-abe5-de9e23fe3faa', 'Lucas');
insert into seller (id, name) values ('8232aedf-cc56-4ca5-a0d5-720f21a9a2c3', 'Ingrid');
insert into seller (id, name) values ('26f48269-2775-45c4-a627-490e131bec5b', 'Ton');
insert into seller (id, name) values ('8f54d037-bfd4-43e2-ad2e-58731d52fe37', 'Ana');
insert into seller (id, name) values ('e4654a5a-e247-4981-af1e-b83dee3329e1', 'Fernando');
insert into seller (id, name) values ('516298ad-c7d4-4195-ab54-d7471623405f', 'Gustavo');

INSERT INTO role (id) VALUES('ROLE_CLIENT');
INSERT INTO role (id) VALUES('ROLE_SUPERVISOR');

INSERT INTO account (id, user_name, password, role) VALUES('5d5ba4e5-7919-4ffc-abe5-de9e23fe3faa', 'lucas', '$2b$12$DF5MEEf6Jfl4Tzszjb2XVuqJNe6O8./eD3viYs4lhJn4hxrYxJAMi', 'ROLE_CLIENT' );
INSERT INTO account (id, user_name, password, role) VALUES('8232aedf-cc56-4ca5-a0d5-720f21a9a2c3', 'ingrid', '$2b$12$7/rmCRkKyOrjdJZSqKU7GO48j0kddaTfv3Nl6GGwiggXhWXyOhZ1C', 'ROLE_CLIENT');
INSERT INTO account (id, user_name, password, role) VALUES('26f48269-2775-45c4-a627-490e131bec5b', 'ton', '$2b$12$cWgnCHOSpU5h38EtUrVPjOb2Wl9c63UAnsY7ZrTZ5Ti1RPFr2prRC', 'ROLE_CLIENT');
INSERT INTO account (id, user_name, password, role) VALUES('8f54d037-bfd4-43e2-ad2e-58731d52fe37', 'ana', '$2b$12$3fmHwbbCXe.uVVtyEzd3jeZXZ8Ne6a2fGnFNGwpeUN4VEwps0.sQi', 'ROLE_CLIENT');
INSERT INTO account (id, user_name, password, role) VALUES('e4654a5a-e247-4981-af1e-b83dee3329e1', 'fernando', '$2b$12$mE7K00Yc3Kp5GZ5VZ.upq.1yCCcGJQEcp5eDQV9KqhFsdUxF9CA/u', 'ROLE_CLIENT');
INSERT INTO account (id, user_name, password, role) VALUES('516298ad-c7d4-4195-ab54-d7471623405f', 'gustavo', '$2b$12$7/rmCRkKyOrjdJZSqKU7GO48j0kddaTfv3Nl6GGwiggXhWXyOhZ1C', 'ROLE_CLIENT');

INSERT INTO account (id, user_name, password, role) VALUES('27a40a9e-3838-4717-935d-b9f6f4a4f623', 'monaliza', '$2b$12$7/rmCRkKyOrjdJZSqKU7GO48j0kddaTfv3Nl6GGwiggXhWXyOhZ1C', 'ROLE_SUPERVISOR');
INSERT INTO account (id, user_name, password, role) VALUES('0f6df1fd-3767-4a9a-9705-66ce04275027', 'marcus', '$2b$12$7/rmCRkKyOrjdJZSqKU7GO48j0kddaTfv3Nl6GGwiggXhWXyOhZ1C', 'ROLE_SUPERVISOR');
INSERT INTO account (id, user_name, password, role) VALUES('740bbe6c-0a49-4c3b-92d8-90c1aa036064', 'barbara', '$2b$12$7/rmCRkKyOrjdJZSqKU7GO48j0kddaTfv3Nl6GGwiggXhWXyOhZ1C', 'ROLE_SUPERVISOR');

insert into section (code, capacity, category, warehouse_code) values ('OSAF001', 10, 'FS', 'OSAF');
insert into section (code, capacity, category, warehouse_code) values ('OSAF002', 10, 'RF', 'OSAF');
insert into section (code, capacity, category, warehouse_code) values ('OSAF003', 10, 'FF', 'OSAF');
insert into section (code, capacity, category, warehouse_code) values ('CAJF001', 100, 'FS', 'CAJF');
insert into section (code, capacity, category, warehouse_code) values ('CAJF002', 100, 'RF', 'CAJF');
insert into section (code, capacity, category, warehouse_code) values ('CAJF003', 100, 'FF', 'CAJF');

insert into product (id, name, category, seller_id) values ('51b3b287-0b78-484c-90c3-606c4bae9401', 'Alface', 'FS', '5d5ba4e5-7919-4ffc-abe5-de9e23fe3faa');
insert into product (id, name, category, seller_id) values ('1b0d82fa-277f-4f13-a9b7-a6c4c4eec204', 'Leite', 'RF', '5d5ba4e5-7919-4ffc-abe5-de9e23fe3faa');
insert into product (id, name, category, seller_id) values ('fa0d9b2e-3eac-417e-8ee6-f26037336522', 'Batata', 'FS', '5d5ba4e5-7919-4ffc-abe5-de9e23fe3faa');
insert into product (id, name, category, seller_id) values ('0c62333d-f233-4e1c-979b-84c48f8b534b', 'Danoninho', 'RF', '5d5ba4e5-7919-4ffc-abe5-de9e23fe3faa');
insert into product (id, name, category, seller_id) values ('70b946e4-f8ba-441d-9100-221ef03fe33d', 'Figo', 'FS', '5d5ba4e5-7919-4ffc-abe5-de9e23fe3faa');
insert into product (id, name, category, seller_id) values ('a27169c2-8893-4929-8f08-f58758b4e3e0', 'Beterraba', 'FS', '8232aedf-cc56-4ca5-a0d5-720f21a9a2c3');
insert into product (id, name, category, seller_id) values ('3a59c768-0b62-49d5-a8ab-1f35477204d2', 'Couve Flor', 'FS', '8232aedf-cc56-4ca5-a0d5-720f21a9a2c3');
insert into product (id, name, category, seller_id) values ('cb61ae38-a476-446f-94b0-f29072b46102', 'Batata Monaliza', 'FS', '8232aedf-cc56-4ca5-a0d5-720f21a9a2c3');
insert into product (id, name, category, seller_id) values ('f2d75a5e-c9a7-498b-97c4-a563568d38ae', 'Grao de bico', 'FS', '8232aedf-cc56-4ca5-a0d5-720f21a9a2c3');
insert into product (id, name, category, seller_id) values ('5460512e-088d-40cc-8a8c-d2d2fbc2cf5a', 'Batata doce', 'FS', '26f48269-2775-45c4-a627-490e131bec5b');
insert into product (id, name, category, seller_id) values ('cc00ba83-4e0a-4245-a7d1-035f583bcc6b', 'Cenoura', 'FS', '26f48269-2775-45c4-a627-490e131bec5b');
insert into product (id, name, category, seller_id) values ('a666f8a3-7ef3-4292-a4b0-9c4d656faaae', 'Banana', 'FS', '26f48269-2775-45c4-a627-490e131bec5b');
insert into product (id, name, category, seller_id) values ('d7ffe85a-5cad-4167-8539-319c6cfb6263', 'Pao de queijo congelado', 'FF', '26f48269-2775-45c4-a627-490e131bec5b');
insert into product (id, name, category, seller_id) values ('868ed6fd-3d5b-4f22-9fc2-fb4ca82d286d', 'Yogurt', 'RF', '8f54d037-bfd4-43e2-ad2e-58731d52fe37');
insert into product (id, name, category, seller_id) values ('6bc01115-3e3f-43fe-b21f-1fa336308021', 'Ameixa', 'FS', '8f54d037-bfd4-43e2-ad2e-58731d52fe37');
insert into product (id, name, category, seller_id) values ('03fa373c-2378-4af3-80e9-d4740e1845b9', 'Batata', 'FS', 'e4654a5a-e247-4981-af1e-b83dee3329e1');
insert into product (id, name, category, seller_id) values ('c2f5c72f-7171-454b-a58d-58e6b034c9fd', 'Brocolis', 'FS', 'e4654a5a-e247-4981-af1e-b83dee3329e1');
insert into product (id, name, category, seller_id) values ('749189b8-c6a6-43ff-abb2-609bfc177632', 'Couve manteiga', 'FS', 'e4654a5a-e247-4981-af1e-b83dee3329e1');
insert into product (id, name, category, seller_id) values ('cb61d2d3-c62f-44fa-909c-cf4d1c8e432f', 'Couve Flor', 'FS', '516298ad-c7d4-4195-ab54-d7471623405f');
insert into product (id, name, category, seller_id) values ('e1008235-9bbe-436c-a4f4-1b66718c4179', 'Abacaxi', 'FS', '516298ad-c7d4-4195-ab54-d7471623405f');
insert into product (id, name, category, seller_id) values ('22bfc952-b765-47ff-8603-f24ea179b861', 'Alface', 'FS', '516298ad-c7d4-4195-ab54-d7471623405f');