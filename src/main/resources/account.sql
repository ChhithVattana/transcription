INSERT INTO tbl_privilege (id, name, description)
VALUES (1, 'PRIVILEGE_ADMIN', 'description for privilege admin');
INSERT INTO tbl_privilege (id, name, description)
VALUES (2, 'PRIVILEGE_STAFF', 'description for privilege staff');
INSERT INTO tbl_privilege (id, name, description)
VALUES (3, 'PRIVILEGE_USER', 'description for privilege user');
INSERT INTO tbl_privilege (id, name, description)
VALUES (4, 'PRIVILEGE_ADMIN_READ', 'description for privilege admin read');
INSERT INTO tbl_privilege (id, name, description)
VALUES (5, 'PRIVILEGE_STAFF_READ', 'description for privilege staff read');
--get all and Read are work the same
INSERT INTO tbl_privilege (id, name, description)
VALUES (6, 'PRIVILEGE_ADMIN_GETALL', 'description for privilege admin getall');
INSERT INTO tbl_privilege (id, name, description)
VALUES (7, 'PRIVILEGE_ADMIN_UPDATE', 'description for privilege admin update');
INSERT INTO tbl_privilege (id, name, description)
VALUES (8, 'PRIVILEGE_ADMIN_DELETE', 'description for privilege admin delete');
INSERT INTO tbl_privilege (id, name, description)
VALUES (9, 'PRIVILEGE_ADMIN_INSERT', 'description for privilege admin insert');
INSERT INTO tbl_privilege (id, name, description)
VALUES (10, 'PRIVILEGE_ADMIN_GETBYID', 'description for privilege admin getbyid');
INSERT INTO tbl_privilege (id, name, description)
VALUES (11, 'PRIVILEGE_STAFF_INSERT', 'description for privilege staff insert');
INSERT INTO tbl_privilege (id, name, description)
VALUES (12, 'PRIVILEGE_STAFF_UPDATE', 'description for privilege staff update');
INSERT INTO tbl_privilege (id, name, description)
VALUES (13, 'PRIVILEGE_USER_READ', 'Web client to get the data');
INSERT INTO tbl_role (id, name, description, date_created, date_updated, status)
VALUES (1, 'ROLE_ADMIN', 'description for role admin', now(), null, true);
INSERT INTO tbl_role (id, name, description, date_created, date_updated, status)
VALUES (2, 'ROLE_STAFF', 'description for role staff', now(), null, true);
INSERT INTO tbl_role (id, name, description, date_created, date_updated, status)
VALUES (3, 'ROLE_USER', 'description for role user', now(), null, true);

--password admin for role admin
INSERT INTO tbl_account (enabled, username, password, date_created, date_updated, status)
VALUES (true, 'admin', '$2a$12$.RXUfKzgB/Ux/YqIgepyEe4aaxLtVJLnIyTajYI5drKfzNx40pmvG', now(), null, true);

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
INSERT INTO tbl_role_privileges (role_id, privileges_id)
VALUES (1, 11);
INSERT INTO tbl_role_privileges (role_id, privileges_id)
VALUES (1, 12);
INSERT INTO tbl_role_privileges (role_id, privileges_id)
VALUES (1, 13);
--Connect between role and privilege staff can access only
INSERT INTO tbl_role_privileges (role_id, privileges_id)
VALUES (2, 2);
INSERT INTO tbl_role_privileges (role_id, privileges_id)
VALUES (2, 3);
INSERT INTO tbl_role_privileges (role_id, privileges_id)
VALUES (2, 5);
INSERT INTO tbl_role_privileges (role_id, privileges_id)
VALUES (2, 11);
INSERT INTO tbl_role_privileges (role_id, privileges_id)
VALUES (2, 12);
INSERT INTO tbl_role_privileges (role_id, privileges_id)
VALUES (2, 13);

