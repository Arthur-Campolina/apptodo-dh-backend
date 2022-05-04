
insert into tb_users values (1, now(), null, 'admin@admin.com', 'admin',  true, 'administrator' , '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
insert into tb_users values (2, now(), null, 'user@user.com', 'user',  true, 'user' , '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
insert into tb_users values (3, now(), null, 'arthur@gmail.com', 'arthur',  true, 'moura' , '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');

insert into tb_roles values (1, 'ROLE_ADMIN', 'admin');
insert into tb_roles values (2, 'ROLE_USER', 'user');

insert into tb_users_roles values (1, 1);
insert into tb_users_roles values (2, 2);
insert into tb_users_roles values (3, 2);

insert into tb_tasks values (1, now(), now(), false, 'estudar java', 1);
insert into tb_tasks values (2, now(), now(), false, 'estudar react js', 1);
insert into tb_tasks values (3, now(), now(), false, 'jogar bola', 2);
insert into tb_tasks values (4, now(), now(), false, 'finalizar tarefa', 2);
insert into tb_tasks values (5, now(), now(), false, 'levar o carro no mecanico', 3);