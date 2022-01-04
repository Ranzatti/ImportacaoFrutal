public class ImporaGeral {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        ImportaCodigoAplicacao importaCodigoAplicacao = new ImportaCodigoAplicacao();
        ImportaContaExtra importaContaExtra = new ImportaContaExtra();
        ImportaContasBancarias importaContasBancarias = new ImportaContasBancarias();
        ImportaCreditoDotacao importaCreditoDotacao = new ImportaCreditoDotacao();
        ImportaElemDespesa importaElemDespesa = new ImportaElemDespesa();
        ImportaEmpenhos importaEmpenhos = new ImportaEmpenhos();
        ImportaGuiaReceita importaGuiaReceita = new ImportaGuiaReceita();
        ImportaOrdemPagamento importaOrdemPagamento = new ImportaOrdemPagamento();
        ImportaPagamento importaPagamento = new ImportaPagamento();
        ImportaReceita importaReceita = new ImportaReceita();
        ImportaRestosInscritos importaRestosInscritos = new ImportaRestosInscritos();
        ImportaAnulacaoReceita importaAnulacaoReceita = new ImportaAnulacaoReceita();
        ImportaTransferencia importaTransferencia = new ImportaTransferencia();


        System.out.println("====== INICIOU =======");
        int ano = 2005;
        while (ano <= 2020) {
            //importaEmpenhos.main(args, ano);
            //importaReceita.main(args, ano);
            //importaCreditoDotacao.main(args, ano);
            //importaElemDespesa.main(args, ano);
            //importaGuiaReceita.init(ano);
            //importaAnulacaoReceita.init(ano);
            //importaTransferencia.main(args, ano);
            //importaOrdemPagamento.main(args, ano);
            //importaRestosInscritos.main(args, ano);
            //importaPagamento.main(args, ano);
            ano++;
        }
        System.out.println("====== ACABOU =======");

    }

}
