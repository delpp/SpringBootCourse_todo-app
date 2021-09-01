create table project_steps(
       id int primary key auto_increment,
       description varchar(255),
       days_to_deadline int null,
       project_id int not null,
       foreign key (project_id) references projects(id)
)