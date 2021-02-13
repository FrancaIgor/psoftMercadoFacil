-- Popula o banco

INSERT INTO CATEGORIA ( ID, DESCONTO, NOME )

VALUES ( 1, 0.10, 'Higiene' ),
       ( 2, 0.15, 'limpeza' ),
       ( 3, 0.50, 'Alimentos' );


-- Inserção de produtos
INSERT INTO PRODUTO(ID, NOME, CODIGO_BARRA, FABRICANTE, CATEGORIA_ID, PRECO, DISPONIBILIDADE)
VALUES  (1,'Leite Integral', '87654321-B', 'Parmalat', 3, 4.5, false),
        (2,'Arroz Integral', '87654322-B', 'Tio Joao', 3, 5.5, false),
        (3,'Sabao em Po', '87654323-B', 'OMO', 2, 12, false),
        (4,'Agua Sanitaria', '87654324-C', 'Dragao', 2, 3, false),
        (5,'Creme Dental', '87654325-C', 'Colgate', 1, 2.5, false);

-- Inserção de lotes
INSERT INTO LOTE(ID, DATA_DE_VALIDADE, NUMERO_DE_ITENS, PRODUTO_ID)
VALUES (1, '02-03-2021', 3, 1),
       (2, '12-03-2022', 3, 1),
       (3, '02-03-2021', 3, 2);
       
UPDATE PRODUTO SET DISPONIBILIDADE = true WHERE ID = 1;
UPDATE PRODUTO SET DISPONIBILIDADE = true WHERE ID = 2;

INSERT INTO VENDA( ID, DATA_VENDA , VALOR )
VALUES ( 1, '2019-12-12', 30.00);

INSERT INTO PRODUTO_VENDIDO ( ID, QUANT_PRODUTOS, PRODUTO_ID , VENDA_ID )
VALUES (1, 4, 1, 1),
       (2, 7, 2, 1);

-- Inserção dos usuarios
INSERT INTO USUARIO(NOME_COMPLETO, LOGIN, SENHA)
VALUES ('Administrador', 'admin','$2a$10$g7PqvkCffviWNR/LuiP6QuDGNpVUVxHse3GWpgS7g9NOy9ZfaLV8G'),
       ('Usuário', 'user','$2a$10$ItNwvRywH9JY.wrgFsc5mOYl42JFkROW4/uZPI63Ytd.FvxntImSe');

-- Inserção de papéis
INSERT INTO ROLE(NOME_ROLE)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');

-- Inserção de relacionamentos (PAPEL -> USUARIO)
INSERT INTO USUARIOS_ROLES (USUARIO_ID, ROLE_ID)
VALUES ('admin', 'ROLE_ADMIN'),
       ('user', 'ROLE_USER');
