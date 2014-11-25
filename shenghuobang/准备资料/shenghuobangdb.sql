Create Table tableCharge(id Integer Primary Key AutoIncrement ,time DateTime,sum Integer,type Boolen,des Text);
Create Table tablePassword(id Integer Primary key AutoIncrement,name Text,passWord Text,soundFileName Text);
Create Table tableUnforget(id Integer Primary key AutoIncrement,time DateTime,name Text,soundFileName Text);

insert into tableCharge(time,sum,type,des)values('2014-01-01 01:01:01',10,1,'¹¤×Ê');
