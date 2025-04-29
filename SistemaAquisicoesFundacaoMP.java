import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.NumberFormat;
import java.util.Locale;
import java.io.Serializable;

/**
 * Sistema de Aquisições para Fundação vinculada ao Ministério Público
 * 
 * Este sistema gerencia todo o processo de aquisições públicas conforme
 * a legislação brasileira (Lei 8.666/93, Lei 14.133/21, etc.)
 */
public class SistemaAquisicoesFundacaoMP {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE AQUISIÇÕES - FUNDAÇÃO VINCULADA AO MINISTÉRIO PÚBLICO ===\n");
        
        // Inicializar o sistema
        SistemaAquisicoes sistema = new SistemaAquisicoes();
        
        // Demonstrar funcionalidades do sistema
        demonstrarFuncionalidades(sistema);
    }
    
    private static void demonstrarFuncionalidades(SistemaAquisicoes sistema) {
        // Criar usuários de exemplo
        Usuario gestor = new Usuario("10987654321", "Maria Silva", "maria.silva@fundacao.mp.gov.br", 
                                    TipoUsuario.GESTOR, "Departamento de Compras");
        
        Usuario fiscal = new Usuario("12345678901", "João Santos", "joao.santos@fundacao.mp.gov.br", 
                                    TipoUsuario.FISCAL_CONTRATO, "Departamento Jurídico");
        
        Usuario ordenador = new Usuario("98765432101", "Ana Oliveira", "ana.oliveira@fundacao.mp.gov.br", 
                                       TipoUsuario.ORDENADOR_DESPESA, "Diretoria Executiva");
        
        sistema.cadastrarUsuario(gestor);
        sistema.cadastrarUsuario(fiscal);
        sistema.cadastrarUsuario(ordenador);
        
        // Criar fornecedores de exemplo
        Fornecedor fornecedor1 = new Fornecedor("12345678000190", "Tech Solutions LTDA", 
                                              "contato@techsolutions.com.br", "Tecnologia");
        
        Fornecedor fornecedor2 = new Fornecedor("98765432000110", "Mobiliário Corporativo S.A.", 
                                              "vendas@mobiliariocorp.com.br", "Mobiliário");
        
        sistema.cadastrarFornecedor(fornecedor1);
        sistema.cadastrarFornecedor(fornecedor2);
        
        // Criar um processo de aquisição
        ProcessoAquisicao processo = sistema.iniciarProcessoAquisicao(
            "Aquisição de Computadores e Periféricos",
            "Aquisição de equipamentos de informática para modernização do parque tecnológico da Fundação",
            gestor,
            ModalidadeLicitacao.PREGAO_ELETRONICO,
            LocalDate.now(),
            2500000.0
        );
        
        // Adicionar itens ao processo
        Item item1 = new Item("Computador Desktop", "Computador com processador i7, 16GB RAM, 512GB SSD", 
                             5000.0, 100, UnidadeMedida.UNIDADE);
        
        Item item2 = new Item("Monitor LED 24\"", "Monitor LED Full HD 24 polegadas", 
                             1200.0, 100, UnidadeMedida.UNIDADE);
        
        Item item3 = new Item("Notebook", "Notebook com processador i7, 16GB RAM, 512GB SSD", 
                             6500.0, 50, UnidadeMedida.UNIDADE);
        
        sistema.adicionarItemAoProcesso(processo, item1);
        sistema.adicionarItemAoProcesso(processo, item2);
        sistema.adicionarItemAoProcesso(processo, item3);
        
        // Adicionar documentos ao processo
        Documento termoReferencia = new Documento(
            "Termo de Referência",
            "Especificações técnicas detalhadas dos equipamentos",
            gestor,
            LocalDateTime.now()
        );
        
        Documento parecerJuridico = new Documento(
            "Parecer Jurídico",
            "Análise jurídica do processo de aquisição",
            fiscal,
            LocalDateTime.now()
        );
        
        sistema.adicionarDocumentoAoProcesso(processo, termoReferencia);
        sistema.adicionarDocumentoAoProcesso(processo, parecerJuridico);
        
        // Avançar o processo nas etapas
        sistema.avancarEtapaProcesso(processo, EtapaProcesso.ELABORACAO_EDITAL, gestor);
        sistema.avancarEtapaProcesso(processo, EtapaProcesso.PUBLICACAO_EDITAL, gestor);
        
        // Registrar propostas
        Proposta proposta1 = new Proposta(
            fornecedor1,
            processo,
            LocalDateTime.now(),
            1150000.0,
            "Proposta para fornecimento de equipamentos de informática"
        );
        
        Proposta proposta2 = new Proposta(
            fornecedor2,
            processo,
            LocalDateTime.now(),
            1250000.0,
            "Proposta alternativa para fornecimento de equipamentos"
        );
        
        sistema.registrarProposta(processo, proposta1);
        sistema.registrarProposta(processo, proposta2);
        
        // Selecionar proposta vencedora
        sistema.selecionarPropostaVencedora(processo, proposta1, gestor);
        
        // Avançar para contratação
        sistema.avancarEtapaProcesso(processo, EtapaProcesso.HOMOLOGACAO, ordenador);
        sistema.avancarEtapaProcesso(processo, EtapaProcesso.CONTRATACAO, ordenador);
        
        // Criar contrato
        Contrato contrato = sistema.gerarContrato(
            processo,
            "CT-2023-001",
            LocalDate.now(),
            LocalDate.now().plusMonths(12),
            proposta1.getValorTotal(),
            fiscal
        );
        
        // Registrar empenho
        NotaEmpenho empenho = sistema.registrarEmpenho(
            contrato,
            "NE-2023-001",
            LocalDate.now(),
            proposta1.getValorTotal(),
            ordenador
        );
        
        // Registrar entrega parcial
        EntregaItem entrega1 = new EntregaItem(
            contrato,
            LocalDate.now().plusDays(30),
            "Entrega parcial - 50% dos equipamentos",
            fiscal
        );
        
        entrega1.adicionarItemEntregue(item1, 50);
        entrega1.adicionarItemEntregue(item2, 50);
        
        sistema.registrarEntrega(contrato, entrega1);
        
        // Registrar pagamento parcial
        Pagamento pagamento1 = sistema.registrarPagamento(
            contrato,
            "PG-2023-001",
            LocalDate.now().plusDays(45),
            575000.0,
            "Pagamento referente à primeira entrega",
            ordenador
        );
        
        // Gerar relatório do processo
        String relatorio = sistema.gerarRelatorioProcesso(processo);
        System.out.println(relatorio);
        
        // Gerar relatório de auditoria
        String relatorioAuditoria = sistema.gerarRelatorioAuditoria(processo);
        System.out.println("\n" + relatorioAuditoria);
        
        // Gerar relatório de transparência
        String relatorioTransparencia = sistema.gerarRelatorioTransparencia();
        System.out.println("\n" + relatorioTransparencia);
    }
}

/**
 * Classe principal que gerencia o sistema de aquisições
 */
class SistemaAquisicoes {
    private List<Usuario> usuarios;
    private List<Fornecedor> fornecedores;
    private List<ProcessoAquisicao> processos;
    private List<Contrato> contratos;
    private List<NotaEmpenho> empenhos;
    private List<Pagamento> pagamentos;
    private List<LogAuditoria> logsAuditoria;
    private NumberFormat formatoMoeda;
    
    public SistemaAquisicoes() {
        this.usuarios = new ArrayList<>();
        this.fornecedores = new ArrayList<>();
        this.processos = new ArrayList<>();
        this.contratos = new ArrayList<>();
        this.empenhos = new ArrayList<>();
        this.pagamentos = new ArrayList<>();
        this.logsAuditoria = new ArrayList<>();
        this.formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    }
    
    // Métodos de gerenciamento de usuários
    public void cadastrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        registrarLog("Cadastro de Usuário", "Usuário cadastrado: " + usuario.getNome(), usuario);
    }
    
    public Usuario buscarUsuarioPorCPF(String cpf) {
        return usuarios.stream()
            .filter(u -> u.getCpf().equals(cpf))
            .findFirst()
            .orElse(null);
    }
    
    // Métodos de gerenciamento de fornecedores
    public void cadastrarFornecedor(Fornecedor fornecedor) {
        fornecedores.add(fornecedor);
        registrarLog("Cadastro de Fornecedor", "Fornecedor cadastrado: " + fornecedor.getRazaoSocial(), null);
    }
    
    public Fornecedor buscarFornecedorPorCNPJ(String cnpj) {
        return fornecedores.stream()
            .filter(f -> f.getCnpj().equals(cnpj))
            .findFirst()
            .orElse(null);
    }
    
    // Métodos de gerenciamento de processos de aquisição
    public ProcessoAquisicao iniciarProcessoAquisicao(String titulo, String descricao, 
                                                    Usuario responsavel, ModalidadeLicitacao modalidade,
                                                    LocalDate dataAbertura, double valorEstimado) {
        // Verificar permissão do usuário
        if (!responsavel.getTipo().equals(TipoUsuario.GESTOR) && 
            !responsavel.getTipo().equals(TipoUsuario.ORDENADOR_DESPESA)) {
            throw new IllegalArgumentException("Usuário não tem permissão para iniciar processo de aquisição");
        }
        
        String numeroProcesso = gerarNumeroProcesso();
        ProcessoAquisicao processo = new ProcessoAquisicao(
            numeroProcesso, titulo, descricao, responsavel, 
            modalidade, dataAbertura, valorEstimado
        );
        
        processos.add(processo);
        registrarLog("Início de Processo", "Processo iniciado: " + numeroProcesso, responsavel);
        
        return processo;
    }
    
    public void adicionarItemAoProcesso(ProcessoAquisicao processo, Item item) {
        processo.adicionarItem(item);
        registrarLog("Adição de Item", "Item adicionado ao processo " + processo.getNumero() + ": " + item.getDescricao(), null);
    }
    
    public void adicionarDocumentoAoProcesso(ProcessoAquisicao processo, Documento documento) {
        processo.adicionarDocumento(documento);
        registrarLog("Adição de Documento", "Documento adicionado ao processo " + processo.getNumero() + ": " + documento.getTitulo(), documento.getResponsavel());
    }
    
    public void avancarEtapaProcesso(ProcessoAquisicao processo, EtapaProcesso novaEtapa, Usuario responsavel) {
        // Verificar permissão do usuário conforme a etapa
        boolean temPermissao = verificarPermissaoEtapa(responsavel, novaEtapa);
        
        if (!temPermissao) {
            throw new IllegalArgumentException("Usuário não tem permissão para avançar o processo para esta etapa");
        }
        
        EtapaProcesso etapaAnterior = processo.getEtapaAtual();
        processo.setEtapaAtual(novaEtapa);
        
        registrarLog("Avanço de Etapa", "Processo " + processo.getNumero() + 
                   " avançou de " + etapaAnterior + " para " + novaEtapa, responsavel);
    }
    
    public void registrarProposta(ProcessoAquisicao processo, Proposta proposta) {
        processo.adicionarProposta(proposta);
        registrarLog("Registro de Proposta", "Proposta registrada para o processo " + 
                   processo.getNumero() + " do fornecedor " + proposta.getFornecedor().getRazaoSocial(), null);
    }
    
    public void selecionarPropostaVencedora(ProcessoAquisicao processo, Proposta proposta, Usuario responsavel) {
        // Verificar permissão do usuário
        if (!responsavel.getTipo().equals(TipoUsuario.GESTOR) && 
            !responsavel.getTipo().equals(TipoUsuario.ORDENADOR_DESPESA)) {
            throw new IllegalArgumentException("Usuário não tem permissão para selecionar proposta vencedora");
        }
        
        processo.setPropostaVencedora(proposta);
        registrarLog("Seleção de Proposta", "Proposta do fornecedor " + 
                   proposta.getFornecedor().getRazaoSocial() + " selecionada como vencedora", responsavel);
    }
    
    public Contrato gerarContrato(ProcessoAquisicao processo, String numero, 
                                LocalDate dataInicio, LocalDate dataFim, 
                                double valor, Usuario fiscal) {
        // Verificar se o processo está na etapa adequada
        if (processo.getEtapaAtual() != EtapaProcesso.CONTRATACAO) {
            throw new IllegalStateException("Processo não está na etapa de contratação");
        }
        
        // Verificar se o fiscal tem o papel adequado
        if (!fiscal.getTipo().equals(TipoUsuario.FISCAL_CONTRATO)) {
            throw new IllegalArgumentException("Usuário não tem permissão para ser fiscal de contrato");
        }
        
        Contrato contrato = new Contrato(
            numero, processo, processo.getPropostaVencedora().getFornecedor(),
            dataInicio, dataFim, valor, fiscal
        );
        
        contratos.add(contrato);
        processo.setContrato(contrato);
        
        registrarLog("Geração de Contrato", "Contrato " + numero + " gerado para o processo " + 
                   processo.getNumero(), fiscal);
        
        return contrato;
    }
    
    public NotaEmpenho registrarEmpenho(Contrato contrato, String numero, 
                                      LocalDate data, double valor, Usuario ordenador) {
        // Verificar se o ordenador tem o papel adequado
        if (!ordenador.getTipo().equals(TipoUsuario.ORDENADOR_DESPESA)) {
            throw new IllegalArgumentException("Usuário não tem permissão para ser ordenador de despesa");
        }
        
        NotaEmpenho empenho = new NotaEmpenho(numero, contrato, data, valor, ordenador);
        empenhos.add(empenho);
        contrato.adicionarEmpenho(empenho);
        
        registrarLog("Registro de Empenho", "Nota de Empenho " + numero + 
                   " registrada para o contrato " + contrato.getNumero(), ordenador);
        
        return empenho;
    }
    
    public void registrarEntrega(Contrato contrato, EntregaItem entrega) {
        contrato.adicionarEntrega(entrega);
        
        registrarLog("Registro de Entrega", "Entrega registrada para o contrato " + 
                   contrato.getNumero() + ": " + entrega.getDescricao(), entrega.getResponsavel());
    }
    
    public Pagamento registrarPagamento(Contrato contrato, String numero, 
                                      LocalDate data, double valor, String descricao, 
                                      Usuario ordenador) {
        // Verificar se o ordenador tem o papel adequado
        if (!ordenador.getTipo().equals(TipoUsuario.ORDENADOR_DESPESA)) {
            throw new IllegalArgumentException("Usuário não tem permissão para autorizar pagamento");
        }
        
        Pagamento pagamento = new Pagamento(numero, contrato, data, valor, descricao, ordenador);
        pagamentos.add(pagamento);
        contrato.adicionarPagamento(pagamento);
        
        registrarLog("Registro de Pagamento", "Pagamento " + numero + 
                   " registrado para o contrato " + contrato.getNumero(), ordenador);
        
        return pagamento;
    }
    
    // Métodos de geração de relatórios
    public String gerarRelatorioProcesso(ProcessoAquisicao processo) {
        StringBuilder relatorio = new StringBuilder();
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        relatorio.append("RELATÓRIO DE PROCESSO DE AQUISIÇÃO\n");
        relatorio.append("==================================\n\n");
        
        relatorio.append("Número do Processo: ").append(processo.getNumero()).append("\n");
        relatorio.append("Título: ").append(processo.getTitulo()).append("\n");
        relatorio.append("Descrição: ").append(processo.getDescricao()).append("\n");
        relatorio.append("Responsável: ").append(processo.getResponsavel().getNome()).append("\n");
        relatorio.append("Modalidade: ").append(processo.getModalidade()).append("\n");
        relatorio.append("Data de Abertura: ").append(processo.getDataAbertura().format(formatoData)).append("\n");
        relatorio.append("Valor Estimado: ").append(formatoMoeda.format(processo.getValorEstimado())).append("\n");
        relatorio.append("Etapa Atual: ").append(processo.getEtapaAtual()).append("\n\n");
        
        relatorio.append("ITENS DO PROCESSO\n");
        relatorio.append("-----------------\n");
        for (Item item : processo.getItens()) {
            relatorio.append("- ").append(item.getNome()).append(": ")
                    .append(item.getQuantidade()).append(" ")
                    .append(item.getUnidade()).append(" x ")
                    .append(formatoMoeda.format(item.getValorUnitario()))
                    .append(" = ").append(formatoMoeda.format(item.getValorTotal()))
                    .append("\n");
        }
        relatorio.append("\n");
        
        relatorio.append("DOCUMENTOS DO PROCESSO\n");
        relatorio.append("----------------------\n");
        for (Documento doc : processo.getDocumentos()) {
            relatorio.append("- ").append(doc.getTitulo())
                    .append(" (").append(doc.getDataCriacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                    .append(") - Responsável: ").append(doc.getResponsavel().getNome())
                    .append("\n");
        }
        relatorio.append("\n");
        
        relatorio.append("PROPOSTAS RECEBIDAS\n");
        relatorio.append("------------------\n");
        for (Proposta proposta : processo.getPropostas()) {
            relatorio.append("- Fornecedor: ").append(proposta.getFornecedor().getRazaoSocial())
                    .append(" - Valor: ").append(formatoMoeda.format(proposta.getValorTotal()))
                    .append(" - Data: ").append(proposta.getDataEnvio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                    .append("\n");
        }
        relatorio.append("\n");
        
        if (processo.getPropostaVencedora() != null) {
            relatorio.append("PROPOSTA VENCEDORA\n");
            relatorio.append("-----------------\n");
            relatorio.append("Fornecedor: ").append(processo.getPropostaVencedora().getFornecedor().getRazaoSocial()).append("\n");
            relatorio.append("Valor: ").append(formatoMoeda.format(processo.getPropostaVencedora().getValorTotal())).append("\n");
            relatorio.append("Economia: ").append(formatoMoeda.format(processo.getValorEstimado() - processo.getPropostaVencedora().getValorTotal()))
                    .append(" (").append(String.format("%.2f", (1 - processo.getPropostaVencedora().getValorTotal() / processo.getValorEstimado()) * 100))
                    .append("%)\n\n");
        }
        
        if (processo.getContrato() != null) {
            Contrato contrato = processo.getContrato();
            
            relatorio.append("CONTRATO\n");
            relatorio.append("--------\n");
            relatorio.append("Número: ").append(contrato.getNumero()).append("\n");
            relatorio.append("Fornecedor: ").append(contrato.getFornecedor().getRazaoSocial()).append("\n");
            relatorio.append("Vigência: ").append(contrato.getDataInicio().format(formatoData))
                    .append(" a ").append(contrato.getDataFim().format(formatoData)).append("\n");
            relatorio.append("Valor: ").append(formatoMoeda.format(contrato.getValor())).append("\n");
            relatorio.append("Fiscal: ").append(contrato.getFiscal().getNome()).append("\n\n");
            
            relatorio.append("EMPENHOS\n");
            relatorio.append("--------\n");
            for (NotaEmpenho empenho : contrato.getEmpenhos()) {
                relatorio.append("- ").append(empenho.getNumero())
                        .append(" - Data: ").append(empenho.getData().format(formatoData))
                        .append(" - Valor: ").append(formatoMoeda.format(empenho.getValor()))
                        .append(" - Ordenador: ").append(empenho.getOrdenador().getNome())
                        .append("\n");
            }
            relatorio.append("\n");
            
            relatorio.append("ENTREGAS\n");
            relatorio.append("--------\n");
            for (EntregaItem entrega : contrato.getEntregas()) {
                relatorio.append("- Data: ").append(entrega.getData().format(formatoData))
                        .append(" - ").append(entrega.getDescricao())
                        .append(" - Responsável: ").append(entrega.getResponsavel().getNome())
                        .append("\n");
                
                for (ItemEntregue itemEntregue : entrega.getItensEntregues()) {
                    relatorio.append("  * ").append(itemEntregue.getItem().getNome())
                            .append(": ").append(itemEntregue.getQuantidade())
                            .append(" ").append(itemEntregue.getItem().getUnidade())
                            .append("\n");
                }
            }
            relatorio.append("\n");
            
            relatorio.append("PAGAMENTOS\n");
            relatorio.append("----------\n");
            for (Pagamento pagamento : contrato.getPagamentos()) {
                relatorio.append("- ").append(pagamento.getNumero())
                        .append(" - Data: ").append(pagamento.getData().format(formatoData))
                        .append(" - Valor: ").append(formatoMoeda.format(pagamento.getValor()))
                        .append(" - ").append(pagamento.getDescricao())
                        .append("\n");
            }
            
            // Calcular saldo do contrato
            double totalPago = contrato.getPagamentos().stream()
                .mapToDouble(Pagamento::getValor)
                .sum();
            
            relatorio.append("\nTotal Pago: ").append(formatoMoeda.format(totalPago))
                    .append(" (").append(String.format("%.2f", (totalPago / contrato.getValor()) * 100))
                    .append("%)\n");
            relatorio.append("Saldo: ").append(formatoMoeda.format(contrato.getValor() - totalPago))
                    .append("\n");
        }
        
        return relatorio.toString();
    }
    
    public String gerarRelatorioAuditoria(ProcessoAquisicao processo) {
        StringBuilder relatorio = new StringBuilder();
        DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        
        relatorio.append("RELATÓRIO DE AUDITORIA DO PROCESSO\n");
        relatorio.append("=================================\n\n");
        
        relatorio.append("Processo: ").append(processo.getNumero()).append(" - ").append(processo.getTitulo()).append("\n\n");
        
        relatorio.append("LOGS DE AUDITORIA\n");
        relatorio.append("----------------\n");
        
        List<LogAuditoria> logsDoProcesso = logsAuditoria.stream()
            .filter(log -> log.getDescricao().contains(processo.getNumero()))
            .sorted(Comparator.comparing(LogAuditoria::getDataHora))
            .toList();
        
        for (LogAuditoria log : logsDoProcesso) {
            relatorio.append("- ").append(log.getDataHora().format(formatoDataHora))
                    .append(" - ").append(log.getTipo())
                    .append(" - ").append(log.getDescricao());
            
            if (log.getUsuario() != null) {
                relatorio.append(" - Usuário: ").append(log.getUsuario().getNome());
            }
            
            relatorio.append("\n");
        }
        
        return relatorio.toString();
    }
    
    public String gerarRelatorioTransparencia() {
        StringBuilder relatorio = new StringBuilder();
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        relatorio.append("RELATÓRIO DE TRANSPARÊNCIA - AQUISIÇÕES\n");
        relatorio.append("======================================\n\n");
        
        relatorio.append("PROCESSOS DE AQUISIÇÃO EM ANDAMENTO\n");
        relatorio.append("---------------------------------\n");
        
        List<ProcessoAquisicao> processosEmAndamento = processos.stream()
            .filter(p -> p.getEtapaAtual() != EtapaProcesso.CONCLUIDO && p.getEtapaAtual() != EtapaProcesso.CANCELADO)
            .sorted(Comparator.comparing(ProcessoAquisicao::getDataAbertura).reversed())
            .toList();
        
        for (ProcessoAquisicao processo : processosEmAndamento) {
            relatorio.append("- Processo: ").append(processo.getNumero())
                    .append(" - ").append(processo.getTitulo())
                    .append(" - Modalidade: ").append(processo.getModalidade())
                    .append(" - Valor Estimado: ").append(formatoMoeda.format(processo.getValorEstimado()))
                    .append(" - Etapa: ").append(processo.getEtapaAtual())
                    .append("\n");
        }
        relatorio.append("\n");
        
        relatorio.append("CONTRATOS VIGENTES\n");
        relatorio.append("-----------------\n");
        
        LocalDate hoje = LocalDate.now();
        List<Contrato> contratosVigentes = contratos.stream()
            .filter(c -> !c.getDataFim().isBefore(hoje))
            .sorted(Comparator.comparing(Contrato::getDataInicio).reversed())
            .toList();
        
        for (Contrato contrato : contratosVigentes) {
            relatorio.append("- Contrato: ").append(contrato.getNumero())
                    .append(" - Processo: ").append(contrato.getProcesso().getNumero())
                    .append(" - Fornecedor: ").append(contrato.getFornecedor().getRazaoSocial())
                    .append(" - Valor: ").append(formatoMoeda.format(contrato.getValor()))
                    .append(" - Vigência: ").append(contrato.getDataInicio().format(formatoData))
                    .append(" a ").append(contrato.getDataFim().format(formatoData))
                    .append("\n");
        }
        relatorio.append("\n");
        
        relatorio.append("PAGAMENTOS REALIZADOS (ÚLTIMOS 30 DIAS)\n");
        relatorio.append("-------------------------------------\n");
        
        LocalDate trintaDiasAtras = hoje.minusDays(30);
        List<Pagamento> pagamentosRecentes = pagamentos.stream()
            .filter(p -> !p.getData().isBefore(trintaDiasAtras))
            .sorted(Comparator.comparing(Pagamento::getData).reversed())
            .toList();
        
        for (Pagamento pagamento : pagamentosRecentes) {
            relatorio.append("- Pagamento: ").append(pagamento.getNumero())
                    .append(" - Contrato: ").append(pagamento.getContrato().getNumero())
                    .append(" - Fornecedor: ").append(pagamento.getContrato().getFornecedor().getRazaoSocial())
                    .append(" - Data: ").append(pagamento.getData().format(formatoData))
                    .append(" - Valor: ").append(formatoMoeda.format(pagamento.getValor()))
                    .append("\n");
        }
        
        // Calcular total de pagamentos no período
        double totalPagamentosRecentes = pagamentosRecentes.stream()
            .mapToDouble(Pagamento::getValor)
            .sum();
        
        relatorio.append("\nTotal de Pagamentos (últimos 30 dias): ")
                .append(formatoMoeda.format(totalPagamentosRecentes))
                .append("\n");
        
        return relatorio.toString();
    }
    
    // Métodos auxiliares
    private String gerarNumeroProcesso() {
        // Formato: PA-ANO-SEQUENCIAL
        int ano = LocalDate.now().getYear();
        int sequencial = processos.size() + 1;
        return String.format("PA-%d-%04d", ano, sequencial);
    }
    
    private boolean verificarPermissaoEtapa(Usuario usuario, EtapaProcesso etapa) {
        TipoUsuario tipoUsuario = usuario.getTipo();
        
        switch (etapa) {
            case ELABORACAO_TERMO_REFERENCIA:
            case PESQUISA_PRECOS:
            case ELABORACAO_EDITAL:
            case PUBLICACAO_EDITAL:
            case RECEBIMENTO_PROPOSTAS:
            case ANALISE_PROPOSTAS:
                return tipoUsuario == TipoUsuario.GESTOR;
                
            case HOMOLOGACAO:
            case CONTRATACAO:
                return tipoUsuario == TipoUsuario.ORDENADOR_DESPESA;
                
            case EXECUCAO_CONTRATO:
                return tipoUsuario == TipoUsuario.FISCAL_CONTRATO;
                
            case CONCLUIDO:
                return tipoUsuario == TipoUsuario.ORDENADOR_DESPESA || 
                       tipoUsuario == TipoUsuario.GESTOR;
                
            default:
                return false;
        }
    }
    
    private void registrarLog(String tipo, String descricao, Usuario usuario) {
        LogAuditoria log = new LogAuditoria(LocalDateTime.now(), tipo, descricao, usuario);
        logsAuditoria.add(log);
    }
}

/**
 * Enumeração dos tipos de usuários do sistema
 */
enum TipoUsuario {
    GESTOR,               // Responsável por gerenciar processos de aquisição
    FISCAL_CONTRATO,      // Responsável por fiscalizar a execução de contratos
    ORDENADOR_DESPESA,    // Responsável por autorizar despesas
    ADMINISTRADOR         // Administrador do sistema
}

/**
 * Enumeração das modalidades de licitação conforme legislação brasileira
 */
enum ModalidadeLicitacao {
    DISPENSA,             // Dispensa de licitação
    INEXIGIBILIDADE,      // Inexigibilidade de licitação
    CONVITE,              // Modalidade Convite
    TOMADA_PRECOS,        // Tomada de Preços
    CONCORRENCIA,         // Concorrência
    PREGAO_PRESENCIAL,    // Pregão Presencial
    PREGAO_ELETRONICO     // Pregão Eletrônico
}

/**
 * Enumeração das etapas de um processo de aquisição
 */
enum EtapaProcesso {
    ELABORACAO_TERMO_REFERENCIA,  // Elaboração do Termo de Referência
    PESQUISA_PRECOS,              // Pesquisa de Preços
    ELABORACAO_EDITAL,            // Elaboração do Edital
    PUBLICACAO_EDITAL,            // Publicação do Edital
    RECEBIMENTO_PROPOSTAS,        // Recebimento de Propostas
    ANALISE_PROPOSTAS,            // Análise de Propostas
    HOMOLOGACAO,                  // Homologação
    CONTRATACAO,                  // Contratação
    EXECUCAO_CONTRATO,            // Execução do Contrato
    CONCLUIDO,                    // Processo Concluído
    CANCELADO                     // Processo Cancelado
}

/**
 * Enumeração das unidades de medida para itens
 */
enum UnidadeMedida {
    UNIDADE,
    CAIXA,
    PACOTE,
    METRO,
    METRO_QUADRADO,
    LITRO,
    QUILO,
    HORA,
    MES
}

/**
 * Classe que representa um usuário do sistema
 */
class Usuario implements Serializable {
    private String cpf;
    private String nome;
    private String email;
    private TipoUsuario tipo;
    private String departamento;
    
    public Usuario(String cpf, String nome, String email, TipoUsuario tipo, String departamento) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
        this.departamento = departamento;
    }
    
    // Getters
    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public TipoUsuario getTipo() { return tipo; }
    public String getDepartamento() { return departamento; }
}

/**
 * Classe que representa um fornecedor
 */
class Fornecedor implements Serializable {
    private String cnpj;
    private String razaoSocial;
    private String email;
    private String categoria;
    
    public Fornecedor(String cnpj, String razaoSocial, String email, String categoria) {
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.email = email;
        this.categoria = categoria;
    }
    
    // Getters
    public String getCnpj() { return cnpj; }
    public String getRazaoSocial() { return razaoSocial; }
    public String getEmail() { return email; }
    public String getCategoria() { return categoria; }
}

/**
 * Classe que representa um item a ser adquirido
 */
class Item implements Serializable {
    private String nome;
    private String descricao;
    private double valorUnitario;
    private int quantidade;
    private UnidadeMedida unidade;
    
    public Item(String nome, String descricao, double valorUnitario, int quantidade, UnidadeMedida unidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
        this.unidade = unidade;
    }
    
    // Getters
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public double getValorUnitario() { return valorUnitario; }
    public int getQuantidade() { return quantidade; }
    public UnidadeMedida getUnidade() { return unidade; }
    
    // Métodos auxiliares
    public double getValorTotal() {
        return valorUnitario * quantidade;
    }
}

/**
 * Classe que representa um documento do processo
 */
class Documento implements Serializable {
    private String titulo;
    private String conteudo;
    private Usuario responsavel;
    private LocalDateTime dataCriacao;
    
    public Documento(String titulo, String conteudo, Usuario responsavel, LocalDateTime dataCriacao) {
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.responsavel = responsavel;
        this.dataCriacao = dataCriacao;
    }
    
    // Getters
    public String getTitulo() { return titulo; }
    public String getConteudo() { return conteudo; }
    public Usuario getResponsavel() { return responsavel; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
}

/**
 * Classe que representa um processo de aquisição
 */
class ProcessoAquisicao implements Serializable {
    private String numero;
    private String titulo;
    private String descricao;
    private Usuario responsavel;
    private ModalidadeLicitacao modalidade;
    private LocalDate dataAbertura;
    private double valorEstimado;
    private EtapaProcesso etapaAtual;
    private List<Item> itens;
    private List<Documento> documentos;
    private List<Proposta> propostas;
    private Proposta propostaVencedora;
    private Contrato contrato;
    
    public ProcessoAquisicao(String numero, String titulo, String descricao, 
                           Usuario responsavel, ModalidadeLicitacao modalidade, 
                           LocalDate dataAbertura, double valorEstimado) {
        this.numero = numero;
        this.titulo = titulo;
        this.descricao = descricao;
        this.responsavel = responsavel;
        this.modalidade = modalidade;
        this.dataAbertura = dataAbertura;
        this.valorEstimado = valorEstimado;
        this.etapaAtual = EtapaProcesso.ELABORACAO_TERMO_REFERENCIA;
        this.itens = new ArrayList<>();
        this.documentos = new ArrayList<>();
        this.propostas = new ArrayList<>();
    }
    
    // Getters
    public String getNumero() { return numero; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public Usuario getResponsavel() { return responsavel; }
    public ModalidadeLicitacao getModalidade() { return modalidade; }
    public LocalDate getDataAbertura() { return dataAbertura; }
    public double getValorEstimado() { return valorEstimado; }
    public EtapaProcesso getEtapaAtual() { return etapaAtual; }
    public List<Item> getItens() { return itens; }
    public List<Documento> getDocumentos() { return documentos; }
    public List<Proposta> getPropostas() { return propostas; }
    public Proposta getPropostaVencedora() { return propostaVencedora; }
    public Contrato getContrato() { return contrato; }
    
    // Setters
    public void setEtapaAtual(EtapaProcesso etapaAtual) { this.etapaAtual = etapaAtual; }
    public void setPropostaVencedora(Proposta propostaVencedora) { this.propostaVencedora = propostaVencedora; }
    public void setContrato(Contrato contrato) { this.contrato = contrato; }
    
    // Métodos auxiliares
    public void adicionarItem(Item item) {
        itens.add(item);
    }
    
    public void adicionarDocumento(Documento documento) {
        documentos.add(documento);
    }
    
    public void adicionarProposta(Proposta proposta) {
        propostas.add(proposta);
    }
}

/**
 * Classe que representa uma proposta de fornecedor
 */
class Proposta implements Serializable {
    private Fornecedor fornecedor;
    private ProcessoAquisicao processo;
    private LocalDateTime dataEnvio;
    private double valorTotal;
    private String descricao;
    
    public Proposta(Fornecedor fornecedor, ProcessoAquisicao processo, 
                  LocalDateTime dataEnvio, double valorTotal, String descricao) {
        this.fornecedor = fornecedor;
        this.processo = processo;
        this.dataEnvio = dataEnvio;
        this.valorTotal = valorTotal;
        this.descricao = descricao;
    }
    
    // Getters
    public Fornecedor getFornecedor() { return fornecedor; }
    public ProcessoAquisicao getProcesso() { return processo; }
    public LocalDateTime getDataEnvio() { return dataEnvio; }
    public double getValorTotal() { return valorTotal; }
    public String getDescricao() { return descricao; }
}

/**
 * Classe que representa um contrato
 */
class Contrato implements Serializable {
    private String numero;
    private ProcessoAquisicao processo;
    private Fornecedor fornecedor;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private double valor;
    private Usuario fiscal;
    private List<NotaEmpenho> empenhos;
    private List<EntregaItem> entregas;
    private List<Pagamento> pagamentos;
    
    public Contrato(String numero, ProcessoAquisicao processo, Fornecedor fornecedor, 
                  LocalDate dataInicio, LocalDate dataFim, double valor, Usuario fiscal) {
        this.numero = numero;
        this.processo = processo;
        this.fornecedor = fornecedor;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.valor = valor;
        this.fiscal = fiscal;
        this.empenhos = new ArrayList<>();
        this.entregas = new ArrayList<>();
        this.pagamentos = new ArrayList<>();
    }
    
    // Getters
    public String getNumero() { return numero; }
    public ProcessoAquisicao getProcesso() { return processo; }
    public Fornecedor getFornecedor() { return fornecedor; }
    public LocalDate getDataInicio() { return dataInicio; }
    public LocalDate getDataFim() { return dataFim; }
    public double getValor() { return valor; }
    public Usuario getFiscal() { return fiscal; }
    public List<NotaEmpenho> getEmpenhos() { return empenhos; }
    public List<EntregaItem> getEntregas() { return entregas; }
    public List<Pagamento> getPagamentos() { return pagamentos; }
    
    // Métodos auxiliares
    public void adicionarEmpenho(NotaEmpenho empenho) {
        empenhos.add(empenho);
    }
    
    public void adicionarEntrega(EntregaItem entrega) {
        entregas.add(entrega);
    }
    
    public void adicionarPagamento(Pagamento pagamento) {
        pagamentos.add(pagamento);
    }
}

/**
 * Classe que representa uma nota de empenho
 */
class NotaEmpenho implements Serializable {
    private String numero;
    private Contrato contrato;
    private LocalDate data;
    private double valor;
    private Usuario ordenador;
    
    public NotaEmpenho(String numero, Contrato contrato, LocalDate data, double valor, Usuario ordenador) {
        this.numero = numero;
        this.contrato = contrato;
        this.data = data;
        this.valor = valor;
        this.ordenador = ordenador;
    }
    
    // Getters
    public String getNumero() { return numero; }
    public Contrato getContrato() { return contrato; }
    public LocalDate getData() { return data; }
    public double getValor() { return valor; }
    public Usuario getOrdenador() { return ordenador; }
}

/**
 * Classe que representa uma entrega de itens
 */
class EntregaItem implements Serializable {
    private Contrato contrato;
    private LocalDate data;
    private String descricao;
    private Usuario responsavel;
    private List<ItemEntregue> itensEntregues;
    
    public EntregaItem(Contrato contrato, LocalDate data, String descricao, Usuario responsavel) {
        this.contrato = contrato;
        this.data = data;
        this.descricao = descricao;
        this.responsavel = responsavel;
        this.itensEntregues = new ArrayList<>();
    }
    
    // Getters
    public Contrato getContrato() { return contrato; }
    public LocalDate getData() { return data; }
    public String getDescricao() { return descricao; }
    public Usuario getResponsavel() { return responsavel; }
    public List<ItemEntregue> getItensEntregues() { return itensEntregues; }
    
    // Métodos auxiliares
    public void adicionarItemEntregue(Item item, int quantidade) {
        itensEntregues.add(new ItemEntregue(item, quantidade));
    }
}

/**
 * Classe que representa um item entregue
 */
class ItemEntregue implements Serializable {
    private Item item;
    private int quantidade;
    
    public ItemEntregue(Item item, int quantidade) {
        this.item = item;
        this.quantidade = quantidade;
    }
    
    // Getters
    public Item getItem() { return item; }
    public int getQuantidade() { return quantidade; }
}

/**
 * Classe que representa um pagamento
 */
class Pagamento implements Serializable {
    private String numero;
    private Contrato contrato;
    private LocalDate data;
    private double valor;
    private String descricao;
    private Usuario ordenador;
    
    public Pagamento(String numero, Contrato contrato, LocalDate data, 
                   double valor, String descricao, Usuario ordenador) {
        this.numero = numero;
        this.contrato = contrato;
        this.data = data;
        this.valor = valor;
        this.descricao = descricao;
        this.ordenador = ordenador;
    }
    
    // Getters
    public String getNumero() { return numero; }
    public Contrato getContrato() { return contrato; }
    public LocalDate getData() { return data; }
    public double getValor() { return valor; }
    public String getDescricao() { return descricao; }
    public Usuario getOrdenador() { return ordenador; }
}

/**
 * Classe que representa um log de auditoria
 */
class LogAuditoria implements Serializable {
    private LocalDateTime dataHora;
    private String tipo;
    private String descricao;
    private Usuario usuario;
    
    public LogAuditoria(LocalDateTime dataHora, String tipo, String descricao, Usuario usuario) {
        this.dataHora = dataHora;
        this.tipo = tipo;
        this.descricao = descricao;
        this.usuario = usuario;
    }
    
    // Getters
    public LocalDateTime getDataHora() { return dataHora; }
    public String getTipo() { return tipo; }
    public String getDescricao() { return descricao; }
    public Usuario getUsuario() { return usuario; }
}

// Executar o sistema
SistemaAquisicoesFundacaoMP.main(null);