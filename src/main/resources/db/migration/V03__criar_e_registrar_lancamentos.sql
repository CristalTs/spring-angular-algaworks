create table lancamento(
	codigo serial primary key,
	descricao varchar(50) not null,
	data_vencimento date not null,
	data_pagamento date,
	valor decimal(10,2) not null,
	observacao varchar(100),
	tipo varchar(20) not null,
	codigo_categoria integer not null,
	codigo_pessoa integer not null,
	foreign key (codigo_categoria) references categoria(codigo),
	foreign key (codigo_pessoa) references pessoa(codigo)
)