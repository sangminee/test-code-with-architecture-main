insert into `users` (`id`, `email`,`nickname`,`address`,`certification_code`,`status`,`last_login_at`)
values (1,'kok202@naver.com','kok202','Seoul','aaa-aaa-aaa-aaa-aaa','ACTIVE', 0);

insert into `users` (`id`, `email`,`nickname`,`address`,`certification_code`,`status`,`last_login_at`)
values (2,'kok303@naver.com','kok303','Seoul','aaa-aaa-aaa-aaa-aab','PENDING', 0);

insert into `posts` (`id`,`content`,`created_at`,`modified_at`,`user_id`)
values (1,'helloworld',13513515,13513515,1);