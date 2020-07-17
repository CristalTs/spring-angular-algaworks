create table public.categoria(
	codigo serial primary key,
	nome character varying(50) not null
);

insert into public.categoria(nome) values ('Lazer');
insert into public.categoria(nome) values ('Alimentação');
insert into public.categoria(nome) values ('Supermercado');
insert into public.categoria(nome) values ('Farmácia');
insert into public.categoria(nome) values ('Outros');