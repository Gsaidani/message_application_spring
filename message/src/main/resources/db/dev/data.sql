INSERT INTO user (user_uuid, user_firstname, user_lastname)
VALUES ('cdd4637f-9b5f-11ee-82a7-0242ac120003', 'user1', 'user1'),
       ('54d5e2b6-2faa-4775-aa7f-bffc5e3a5440', 'user2', 'user2');

INSERT INTO message (message_uuid, message_description, user_uuid)
VALUES ('54d5e2b6-2faa-4775-aa7f-bffc5e3a5440', 'message1', 'cdd4637f-9b5f-11ee-82a7-0242ac120003'),
       ('54d5e2b6-2faa-4775-aa7f-bffc5e3a5441', 'message2', 'cdd4637f-9b5f-11ee-82a7-0242ac120003'),
       ('54d5e2b6-2faa-4775-aa7f-bffc5e3a5442', 'message3', '54d5e2b6-2faa-4775-aa7f-bffc5e3a5440');

INSERT INTO tag (tag_uuid, tag_name)
VALUES ('00f73434-483c-4731-b608-9d3d05bc5063', "TAG1"),
       ('00f73434-483c-4731-b608-9d3d05bc5064', "TAG2"),
       ('00f73434-483c-4731-b608-9d3d05bc5065', "TAG3");

INSERT INTO message_tag (message_uuid, tag_uuid)
VALUES ('54d5e2b6-2faa-4775-aa7f-bffc5e3a5440', '00f73434-483c-4731-b608-9d3d05bc5063'),
       ('54d5e2b6-2faa-4775-aa7f-bffc5e3a5441', '00f73434-483c-4731-b608-9d3d05bc5063');