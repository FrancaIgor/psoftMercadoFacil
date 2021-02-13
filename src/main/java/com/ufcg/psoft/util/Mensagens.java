package com.ufcg.psoft.util;

public class Mensagens {
    public static final String ID_INVALIDO = "Número de id inválido";
  

    public static class Produto {
        public static final String JAH_CADASTRADO = "Produto já cadastrado";
        public static final String NAO_ENCONTRADO = "Produto não encontrado";
        public static final String NENHUM_ENCONTRADO = "Nenhum produto encontrado";
        public static final String NENHUM_EM_BAIXA = "Nenhum produto em baixa";
        public static final String NULO = "Produto nulo";
        public static final String NOME_NULO = "Nome do produto é nulo";
        public static final String CATEGORIA_NULA = "Categoria do produto nulo";
        public static final String PRECO_NULO = "Preço do produto é nulo";
        public static final String CODIGO_BARRA_NULO = "Código de barras do produto é nulo";
        public static final String FABRICANTE_NULO = "Fabricante do produto é nulo";
    }

    public static class Lote {
        public static final String NAO_ENCONTRADO = "Lote não encontrado";
        public static final String NENHUM_ENCONTRADO = "Nenhum lote foi encontrado";
        public static final String NULO = "Lote nulo";
        public static final String DATA_VALIDADE_NULA = "Data de validade nula";

    }

    public static class Categoria {
        public static final String NAO_ENCONTRADA = "Categoria não encontrada";
        public static final String NULO = "Categoria nulo";
        public static final String NENHUMA_ENCONTRADA = "Nenhuma categoria foi encontrada";
    }
    
    public static class Venda {

        public static final String NAO_ENCONTRADA = "Venda não encontrada";
        public static final String NENHUM_PRODUTO_ENCONTRADO = "Nenhum produto listado nessa venda";
        public static final String NULO = "Venda nula";
        public static final String NENHUMA_ENCONTRADA = "Nenhuma venda encontrada";
        public static final String Valor_NULO = "Preço da venda é nulo";
    }
}

