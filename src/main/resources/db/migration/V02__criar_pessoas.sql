create table pessoa(
	codigo serial primary key,
	nome varchar(100) not null,
	logradouro varchar(50),
	numero varchar(10),
	complemento varchar(50),
	bairro varchar(50),
	cep varchar(50),
	cidade varchar(50),
	estado varchar(10),
	ativo boolean
);