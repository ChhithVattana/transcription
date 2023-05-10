INSERT INTO tbl_privilege (id, name, description)
VALUES (1, 'PRIVILEGE_ADMIN_READ', 'description for privilege admin read');
INSERT INTO tbl_privilege (id, name, description)
VALUES (2, 'PRIVILEGE_STAFF_READ', 'description for privilege staff read');
--get all and Read are work the same
INSERT INTO tbl_privilege (id, name, description)
VALUES (3, 'PRIVILEGE_ADMIN_GETALL', 'description for privilege admin getall');
INSERT INTO tbl_privilege (id, name, description)
VALUES (4, 'PRIVILEGE_ADMIN_UPDATE', 'description for privilege admin update');
INSERT INTO tbl_privilege (id, name, description)
VALUES (5, 'PRIVILEGE_ADMIN_DELETE', 'description for privilege admin delete');
INSERT INTO tbl_privilege (id, name, description)
VALUES (6, 'PRIVILEGE_ADMIN_INSERT', 'description for privilege admin insert');
INSERT INTO tbl_privilege (id, name, description)
VALUES (7, 'PRIVILEGE_ADMIN_GETBYID', 'description for privilege admin getbyid');
INSERT INTO tbl_privilege (id, name, description)
VALUES (8, 'PRIVILEGE_STAFF_INSERT', 'description for privilege staff insert');
INSERT INTO tbl_privilege (id, name, description)
VALUES (9, 'PRIVILEGE_STAFF_UPDATE', 'description for privilege staff update');
INSERT INTO tbl_privilege (id, name, description)
VALUES (10, 'PRIVILEGE_USER_READ', 'Web client to get the data');
INSERT INTO tbl_role (id, name, description, date_created, date_updated, status)
VALUES (1, 'ROLE_ADMIN', 'description for role admin', now(), null, true);
INSERT INTO tbl_role (id, name, description, date_created, date_updated, status)
VALUES (2, 'ROLE_STAFF', 'description for role staff', now(), null, true);
INSERT INTO tbl_role (id, name, description, date_created, date_updated, status)
VALUES (3, 'ROLE_USER', 'description for role user', now(), null, true);

--password admin for role admin
INSERT INTO tbl_account (enabled, username, password, date_created, date_updated, status)
VALUES (true, 'admin', '$2a$12$6chVYmeMMb0OlIB40g4wReZLnohUG.pAWAajJU8iItvzwm/o6ObIW', now(), null, true);

INSERT INTO tbl_account_roles (account_id, roles_id)
VALUES (1, 1);

--Connect between role and privilege admin can access all privilege
INSERT INTO tbl_role_privileges (role_id, privileges_id)
VALUES (1, 1);
INSERT INTO tbl_role_privileges (role_id, privileges_id)
VALUES (1, 2);
INSERT INTO tbl_role_privileges (role_id, privileges_id)
VALUES (1, 3);
INSERT INTO tbl_role_privileges (role_id, privileges_id)
VALUES (1, 4);
INSERT INTO tbl_role_privileges (role_id, privileges_id)
VALUES (1, 5);
INSERT INTO tbl_role_privileges (role_id, privileges_id)
VALUES (1, 6);
INSERT INTO tbl_role_privileges (role_id, privileges_id)
VALUES (1, 7);
INSERT INTO tbl_role_privileges (role_id, privileges_id)
VALUES (1, 8);
INSERT INTO tbl_role_privileges (role_id, privileges_id)
VALUES (1, 9);
INSERT INTO tbl_role_privileges (role_id, privileges_id)
VALUES (1, 10);
--Connect between role and privilege staff can access only
INSERT INTO tbl_role_privileges (role_id, privileges_id)
VALUES (2, 1);
INSERT INTO tbl_role_privileges (role_id, privileges_id)
VALUES (2, 2);
INSERT INTO tbl_role_privileges (role_id, privileges_id)
VALUES (2, 8);
INSERT INTO tbl_role_privileges (role_id, privileges_id)
VALUES (2, 9);
