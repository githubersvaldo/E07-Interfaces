public abstract class Conta implements ITaxas{

    private int numero;

    protected Cliente dono;

    protected double saldo;

    private double limite;

    private Operacao[] operacoes = new Operacao[6];

    private int proximaOperacao = 0;

    private static int totalContas = 0;

    public Conta() {}
    public void sacar(double valor) {
        this.saldo -= valor;

        this.operacoes[proximaOperacao] = new OperacaoSaque(valor);
        this.proximaOperacao++;
    }

    public void depositar(double valor) {
        this.saldo += valor;

        this.operacoes[proximaOperacao] = new OperacaoDeposito(valor);
        this.proximaOperacao++;
    }

    public boolean transferir(Conta destino, double valor) {
        if (valor >= 0 && valor <= this.limite) {
            this.sacar(valor);
            destino.depositar(valor);
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        return this.numero + this.dono.toString() + this.saldo + this.limite;
    }
    @Override
    public boolean equals(Object obj) {
        return this.numero == ((Conta) obj).numero;
    }

    public void imprimirExtrato() {
        System.out.println("======= Extrato Conta " + (this.numero + 1) + " ======");
        for(Operacao atual : this.operacoes) {
            if (atual != null) {
                atual.toString();
            }
        }
        System.out.println("====================");
    }
    public double calculaTaxas(){return 0;}
    public void imprimirExtratoTaxas(){
        double Total = calculaTaxas();
        System.out.println("======= Extrato Conta Taxas ======");
        System.out.printf("Manutenção da conta: %.2f\n" ,this.calculaTaxas());
        System.out.printf("Operações\n");
        for(int i = 0; i < proximaOperacao; i++) {
            if(operacoes[i].calculaTaxas() != 0) {
                System.out.printf("%s %.2f\n", operacoes[i].getTipo(), operacoes[i].calculaTaxas());
                Total += operacoes[i].calculaTaxas();
            }
        }
        this.saldo -= Total;
        System.out.printf("Total: %.2f\n", Total);
    }

    public int getNumero() {
        return numero;
    }

    public Cliente getDono() {
        return dono;
    }

    public double getSaldo() {
        return saldo;
    }

    public double getLimite() {
        return limite;
    }

    public static int getTotalContas() {
        return Conta.totalContas;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setDono(Cliente dono) {
        this.dono = dono;
    }

    public abstract boolean setLimite(double limite);
}
