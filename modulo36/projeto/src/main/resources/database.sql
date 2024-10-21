create table cliente (
	id bigint,
	nome varchar(50) not null,
	cpf varchar(20) not null,
	telefone varchar(20) not null,
	endereco varchar(50) not null,
	numero varchar(10) not null,
	cidade varchar(50) not null,
	estado varchar(50) not null,
	constraint pk_id_cliente primary key(id)
);

create table produto(
	id bigint,
	codigo varchar(10) not null,
	nome varchar(50) not null,
	descricao varchar(100) not null,
	valor numeric(10,2) not null,
	constraint pk_id_produto primary key(id)
);

create table venda(
	id bigint,
	codigo varchar(10) not null,
	id_cliente bigint not null,
	valor_total numeric(10,2) not null,
	data_venda TIMESTAMPTZ not null,
	status_venda varchar(50) not null,
	constraint pk_id_venda primary key(id),
	constraint fk_id_cliente_venda foreign key(id_cliente) references cliente(id)
);

create table item_venda(
	id bigint,
	id_produto bigint not null,
	id_venda bigint not null,
	quantidade int not null,
	valor_total numeric(10,2) not null,
	constraint pk_id_prod_venda primary key(id),
	constraint fk_id_prod_venda foreign key(id_produto) references produto(id),
	constraint fk_id_prod_venda_venda foreign key(id_venda) references venda(id)
);

create sequence sq_cliente
start 1
increment 1
owned by cliente.id;

create sequence sq_produto
start 1
increment 1
owned by produto.id;

create sequence sq_venda
start 1
increment 1
owned by venda.id;

create sequence sq_item_venda
start 1
increment 1
owned by item_venda.id;

ALTER TABLE cliente
ADD CONSTRAINT UK_CPF_CLIENTE UNIQUE (cpf);

ALTER TABLE produto
ADD CONSTRAINT UK_CODIGO_PRODUTO UNIQUE (codigo);

ALTER TABLE venda
ADD CONSTRAINT UK_CODIGO_VENDA UNIQUE (codigo);