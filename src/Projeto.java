import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Projeto implements Serializable {
    private String nome;
    private String acron;
    private GregorianCalendar dataInicio;
    private double duracao;
    private int concluir;
    private GregorianCalendar dataFim;
    private ArrayList<Tarefa> tarefas=new ArrayList<>();
    private Pessoa investigadorPrincipal;
    private ArrayList<Docente> docentes;
    private ArrayList<Formando> formandos;
    private ArrayList<Doutorado> doutorados;
    //gui main
    private JFrame frame,frameOriginal;
    private JButton buttonCriaTarefa,buttonEliminaTarefa,buttonListaTarefaNConcluidas,buttonListaTarefasNaoIniciadas,buttonCusto,buttonConclusao,buttonAtualizarConclusao,buttonAtribuiTarefa,buttonAtualizaData,buttonFecharFrame;
    private JPanel panel,panelJuncao,panelJuncaoPesoas,panelResize,panelInformacao;
    private JButton buttonVoltarAtras;
    private JList listaTarefas,listaPessoas;
    private JScrollPane listTarefasScroller,listaPessoasScroller;
    private JLabel tarefasLista,pessoasLista,labelInformacaoNome,labelInformacaoAcron,labelInformacaoDuracao,labelInformacaoDataInicio,labelInformacaoDataAtual;
    private JPanel panelLista=new JPanel(),panelPessoas=new JPanel();
    //gui cria
    private JFrame frameCria=new JFrame();
    private JPanel panelCria=new JPanel();
    private Choice c=new Choice();
    private JLabel labelInputData,labelDuracaoEstimada,labelTipo;
    private JTextField textFieldData,textFieldDuracaoEstimada;
    private JButton buttonCria;
    //gui lista
    private JFrame frameLista=new JFrame();
    //gui atualiza
    private JFrame frameAtualiza=new JFrame();
    private JPanel panelAtualiza=new JPanel();
    private JLabel labelInputConclusao;
    private JTextField textFieldConclusao;
    private JButton buttonAtualiza;

    /**
     * Construtor de um Projeto que recebe os dados necessários para criar um novo projeto nulo
     * @param nome Nome do projeto
     * @param acron Nome do acronimo do projeto
     * @param dataInicio Data de inicio do projeto
     * @param dataFim Data de fim do Projeto
     * @param duracao Duração estimada do projeto
     */
    public Projeto(String nome, String acron, GregorianCalendar dataInicio, GregorianCalendar dataFim, double duracao,JFrame frameOriginal){
        this.nome=nome;
        this.acron = acron;
        this.dataInicio = dataInicio;
        this.duracao = duracao;
        this.dataFim = dataFim;
        this.frameOriginal=frameOriginal;
        investigadorPrincipal = null;
        docentes = new ArrayList<Docente>();
        formandos = new ArrayList<Formando>();
        doutorados = new ArrayList<Doutorado>();
    }

    /**
     * Construtor de um Projeto que recebe os dados necessários para criar um novo projeto
     * @param nome Nome do projeto
     * @param acron Nome do acronimo do projeto
     * @param investigadorPrincipal Docente Principal
     * @param tarefas Lista de Tarefas a fazer no projeto
     * @param formandos Lista de ambos Licenciados e Mestres do Projeto
     * @param doutorados Lista dos Doutorados do Projecto
     * @param docentes Lista dos Docentes do Projeto
     * @param dataInicio Data de inicio do projeto
     * @param dataFim Data de fim do Projeto
     * @param duracao Duração estimada do projeto
     */
    public Projeto(String nome, String acron,Pessoa investigadorPrincipal,ArrayList<Tarefa> tarefas,JFrame frameOriginal ,ArrayList<Formando> formandos,ArrayList<Doutorado> doutorados, ArrayList<Docente> docentes, GregorianCalendar dataInicio, GregorianCalendar dataFim, double duracao){
        this.nome=nome;
        this.acron = acron;
        this.investigadorPrincipal = investigadorPrincipal;
        this.dataInicio = dataInicio;
        this.duracao = duracao;
        this.dataFim = dataFim;
        this.docentes = docentes;
        this.doutorados = doutorados;
        this.formandos = formandos;
        this.tarefas = tarefas;
        this.frameOriginal=frameOriginal;
    }

    /**
     * Vai construir todo o GUI prinicial do projeto
     */
    public void gui(int dia,int mes,int ano){
        //main gui
        frame=new JFrame();
        frame.setLayout(new BorderLayout(30,200));
        panel=new JPanel();
        panel.setLayout(new GridLayout(10,1,10,10));


        //adicionar a escolhas
        c.add("Design");
        c.add("Desenvolvimento");
        c.add("Documentação");

        //butao de voltar atras usado em varias frames
        buttonVoltarAtras=new JButton("Voltar Atras");
        buttonVoltarAtras.addActionListener(new buttonVoltaAtras());

        //butoes main
        buttonFecharFrame=new JButton("Voltar Atras");
        buttonFecharFrame.addActionListener(new fecharFrame());
        buttonAtualizaData=new JButton("Atualizar Data");
        buttonAtualizaData.addActionListener(new atualizaData());
        buttonCriaTarefa= new JButton("Criar Tarefa");
        buttonCriaTarefa.addActionListener(new criaTarefa());
        buttonAtribuiTarefa=new JButton("Atribuir Tarefas");
        buttonAtribuiTarefa.addActionListener(new buttonAtribuiTarefa());
        buttonAtualizarConclusao=new JButton("Atualizar Conclusao de uma Tarefa");
        buttonAtualizarConclusao.addActionListener(new buttonAtualizaConclusao());
        buttonCusto=new JButton("Custo do Projecto");
        buttonCusto.addActionListener(new buttonPrintCusto());
        buttonEliminaTarefa=new JButton("Eliminar Tarefa");
        buttonEliminaTarefa.addActionListener(new buttonEliminaTarefa());
        buttonListaTarefaNConcluidas=new JButton("Listar Tarefas não Concluidas");
        buttonListaTarefaNConcluidas.addActionListener(new buttonListaTarefasNaoConcluidas());
        buttonListaTarefasNaoIniciadas=new JButton("Listar Tarefas não Inicializadas");
        buttonListaTarefasNaoIniciadas.addActionListener(new buttonListaTarefasNaoIniciadas());
        buttonConclusao=new JButton("Concluir Projeto");
        buttonConclusao.addActionListener(new buttonConcluir());

        //adicionar ao painel principallistListaValues
        panel.add(buttonAtribuiTarefa);
        panel.add(buttonCriaTarefa);
        panel.add(buttonCusto);
        panel.add(buttonEliminaTarefa);
        panel.add(buttonListaTarefaNConcluidas);
        panel.add(buttonListaTarefasNaoIniciadas);
        panel.add(buttonAtualizarConclusao);
        panel.add(buttonAtualizaData);
        panel.add(buttonConclusao);
        panel.add(buttonFecharFrame);

        labelInformacaoDataAtual=new JLabel("Data Atual: "+dia+"/"+mes+"/"+ano);
        labelInformacaoDataAtual.setFont(labelInformacaoDataAtual.getFont().deriveFont(20.0f));
        labelInformacaoNome=new JLabel("Nome: "+getNome());
        labelInformacaoNome.setFont(labelInformacaoNome.getFont().deriveFont(20.0f));
        labelInformacaoAcron=new JLabel("Acronimo: "+getAcron());
        labelInformacaoAcron.setFont(labelInformacaoNome.getFont().deriveFont(20.0f));
        labelInformacaoDuracao=new JLabel("Duracao Estimada: "+getDuracao());
        labelInformacaoDuracao.setFont(labelInformacaoNome.getFont().deriveFont(20.0f));
        labelInformacaoDataInicio=new JLabel("Data Inicio: "+getDataInicio() );
        labelInformacaoDataInicio.setFont(labelInformacaoNome.getFont().deriveFont(20.0f));

        panelInformacao=new JPanel();
        panelInformacao.setLayout(new GridLayout(5,1,10,10));
        panelInformacao.add(labelInformacaoNome);
        panelInformacao.add(labelInformacaoAcron);
        panelInformacao.add(labelInformacaoDataInicio);
        panelInformacao.add(labelInformacaoDuracao);
        panelInformacao.add(labelInformacaoDataAtual);

        panelResize=new JPanel();
        panelResize.setPreferredSize(new Dimension(500,250));
        panelResize.setLayout(new BorderLayout());
        panelResize.add(panel,BorderLayout.CENTER);
        panelResize.add(panelInformacao,BorderLayout.NORTH);

        //listar Tarefas
        panelJuncao=new JPanel();
        panelJuncao.setLayout(new BorderLayout());
        DefaultListModel listListaValues;
        listListaValues=listarTarefa();
        listaTarefas=new JList(listListaValues);
        listTarefasScroller = new JScrollPane(listaTarefas);
        tarefasLista=new JLabel("Lista das Tarefas do Projeto");
        tarefasLista.setPreferredSize(new Dimension(250,100));
        panelLista.setLayout(new BorderLayout());
        panelLista.add(tarefasLista,BorderLayout.NORTH);
        panelLista.add(listaTarefas,BorderLayout.CENTER);
        panelJuncao.add(panelLista,BorderLayout.CENTER);
        panelJuncao.add(new JSeparator(SwingConstants.VERTICAL),BorderLayout.EAST);

        //listar pesoas
        panelJuncaoPesoas=new JPanel();
        panelJuncaoPesoas.setLayout((new BorderLayout()));
        DefaultListModel listPessoas;
        listPessoas=listaPessoas();
        listaPessoas=new JList(listPessoas);
        listaPessoasScroller=new JScrollPane(listaPessoas);
        pessoasLista=new JLabel("Lista de Pessoas no Projeto");
        pessoasLista.setPreferredSize(new Dimension(300,100));
        panelPessoas.setLayout(new BorderLayout());
        panelPessoas.add(pessoasLista,BorderLayout.NORTH);
        panelPessoas.add(listaPessoas,BorderLayout.CENTER);
        panelJuncaoPesoas.add(panelPessoas,BorderLayout.CENTER);
        panelJuncaoPesoas.add(new JSeparator(SwingConstants.VERTICAL),BorderLayout.WEST);

        //adicionar a frame
        frame.setTitle("Teste");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panelResize,BorderLayout.CENTER);
        frame.add(panelJuncao,BorderLayout.WEST);
        frame.add(panelJuncaoPesoas,BorderLayout.EAST);
        frame.setVisible(true);
    }

    //funções para o projecto
    /**
     * Obter duracao do projecto
     * @return duracao do projeto
     */
    public double getDuracao() {
        return duracao;
    }

    /**
     * Obter Data inicial do Projecto
     * @return Data Inicio do Projecto
     */
    public String getDataInicio() {
        return dataInicio.get(Calendar.DAY_OF_MONTH)+"/"+ dataInicio.get(Calendar.MONTH)+"/"+dataInicio.get(Calendar.YEAR);
    }

    /**
     * Get data final do Projeto
     * @return Data final do Projeto
     */
    public GregorianCalendar getDataFim() {
        return dataFim;
    }

    public int getConcluir() {
        return concluir;
    }

    /**
     * Obter Acronimo do Projeto
     * @return Acronimo do Projecto
     */
    public String getAcron() {
        return acron;
    }

    /**
     *  Obter o nome do Prjecto
     * @return nome do Projecto
     */
    public String getNome() {
        return nome;
    }

    /**
     * Set do investigador Principal
     * @param investigadorPrincipal Investigador Principal a meter no Projeto
     */
    public void setInvestigadorPrincipal(Pessoa investigadorPrincipal) {
        this.investigadorPrincipal = investigadorPrincipal;
    }

    /**
     * Get dos Docentes
     * @return Lista dos Docentes
     */
    public ArrayList<Docente> getDocentes() {
        return docentes;
    }

    public ArrayList<Doutorado> getDoutorados() {
        return doutorados;
    }

    /**
     * Get dos Formandos
     * @return Lista de Formandos
     */
    public ArrayList<Formando> getFormandos() {
        return formandos;
    }

    /**
     * Get Investigador
     * @return Investigador Principal
     */
    public Pessoa getInvestigadorPrincipal() {
        return investigadorPrincipal;
    }

    /**
     * Serve para testar a validade da Data inserida pelo user
     * @param dia Dia
     * @param mes Mes
     * @param ano Ano
     * @return Retorna 1 se a data for válida e 0 se for inválida
     */
    public int verificaData(int dia,int mes,int ano){
        if(ano<0){
            return 0;
        }
        if(dia>31){
            return 0;
        }
        if(mes>12){
            return 0;
        }
        if(mes==1 | mes==3 | mes==5 | mes==7 | mes==8 | mes==10 | mes==12){
            return 1;
        }
        else if(mes==4 | mes==6 | mes==9| mes==11){
            if(dia<=30) {
                return 1;
            }
            else{
                return 0;
            }
        }
        else{
            if(dia<=28){
                return 1;
            }
            else{
                return 0;
            }
        }
    }

    //funções para a gui
    /**
     * Serve para por todas as pessoas associadas ao Projeto numa lista
     * @return Lista com pesssoas associadas ao projeto
     */
    public DefaultListModel listaPessoas(){
        DefaultListModel list=new DefaultListModel();
        list.addElement(investigadorPrincipal.toString());
        for(int i=0;i<doutorados.size();i++){
            list.addElement(doutorados.get(i).toString());
        }
        for(int i=0;i<formandos.size();i++){
            list.addElement(formandos.get(i).toString());
        }
        for (int i = 0; i <docentes.size() ; i++) {
            list.addElement(docentes.get(i).toString());
        }
        return list;
    }

    /**
     * Serve para listar todas as tarefas do Projeto
     * @return lista de pessoas no projeto
     */
    public DefaultListModel listarTarefa(){
        DefaultListModel list=new DefaultListModel();
        for (int i = 0; i <tarefas.size() ; i++) {
            list.addElement(tarefas.get(i).toString());
        }
        return list;
    }

    /**
     * Serve para listar as Tarefas não concluidas na data estimada
     * @return lista de tarefas não concluidas na data estimada
     */
    public DefaultListModel listaTarefasNaoConcluidas(){//falta mudar aqui para comparar a data com a duracao
        DefaultListModel list=new DefaultListModel();
        String[] dataAtual;
        String dataBuffer;
        GregorianCalendar data1,data2;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for(int i=0;i<tarefas.size();i++) {
            dataBuffer = labelInformacaoDataAtual.getText();
            dataAtual = dataBuffer.split(" ");
            dataAtual = dataAtual[2].split("/");
            data1=new GregorianCalendar(Integer.parseInt(dataAtual[0]),Integer.parseInt(dataAtual[1]),Integer.parseInt(dataAtual[2]));
            data2 = tarefas.get(i).dataInicio;
            if (data2.compareTo(data1) > 0) {
                list.addElement(tarefas.get(i).toString());
            }
        }
        return list;
    }

    /**
     * Serve para listar as Tarefas não iniciadas
     * @return lista de tarefas não iniciadas
     */
    public DefaultListModel listaTarefasNaoIniciadas(){
        DefaultListModel list=new DefaultListModel();
        for(int i=0;i<tarefas.size();i++){
            if(tarefas.get(i).getPerConclusao()==0){
                list.addElement(tarefas.get(i).toString());
            }
        }
        return list;
    }

    /**
     * Vai eliminar uma tarefa da lista, da array list de tarefas da pessoa e tirar a sua taxa de esforço
     * @param index Vai eliminar a tarefa na posiçao index da lista
     */
    public void eliminaTarefa(int index){
        Pessoa pessoa=tarefas.get(index).getPessoaResponsavel();
        String[] keep_data;
        GregorianCalendar dataAtual;
        try {
            for (int i = 0; i < pessoa.getTarefas().size(); i++) {
                if (tarefas.get(index) == pessoa.getTarefas().get(i)) {
                    keep_data = labelInformacaoDataAtual.getText().split("/");
                    dataAtual = new GregorianCalendar(Integer.parseInt(keep_data[0]), Integer.parseInt(keep_data[1]), Integer.parseInt(keep_data[2]));
                    if(tarefas.get(index).getDataInicio().after(dataAtual)) {
                        pessoa.setEsforco(pessoa.getEsforco() - pessoa.getTarefas().get(i).getTaxaEsforco());
                    }
                    pessoa.removeTarefa(i);
                }
            }
            tarefas.remove(index);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error a eliminar Tarefa","ERROR!",JOptionPane.PLAIN_MESSAGE);
        }
    }

    //nao sei se esta certo tough
    /**
     * Verifica se é possivel adicionar a tarefa no mes certo
     * @param dataInicial Data Inicial da Tarefa a comparar
     * @param dataFinalTarefa Data Final da Tarefa a comparar
     * @param dataFinalTarefaLista Data Inicial da tarefa a criar/atribuir
     * @param dataInicialTarefaLista Data Final da tarefa a criar/atribuir
     * @param tarefa Tarefa a adicionar
     * @return Taxa de esforço da tarefa
     */
    public double verificaEsforço(GregorianCalendar dataInicial,GregorianCalendar dataFinalTarefa,GregorianCalendar dataFinalTarefaLista,GregorianCalendar dataInicialTarefaLista,Tarefa tarefa){
        if(dataInicialTarefaLista.after(dataInicial) & dataFinalTarefa.before(dataFinalTarefa)){
            return tarefa.getTaxaEsforco();
        }
        else if(dataFinalTarefaLista.after(dataInicial) & dataFinalTarefaLista.before(dataFinalTarefa)){
            return tarefa.getTaxaEsforco();
        }
        else{
            return 0;
        }
    }

    /**
     * Cria uma nova Tarefa
     * @param pessoa Pessoa a qual vai ser associada a Tarefa
     * @param duracaoEstimada Duração estimada da duraçao da tarefa
     * @param dia Dia do mes da data Inicial
     * @param mes  Mes da data Inicial
     * @param ano  Ano da data Inicial
     * @param opcao Que tipo de tarefa é, Design,Documentação ou Desenvolvimento
     */
    public void criaTarefa(Pessoa pessoa,double duracaoEstimada,int dia,int mes,int ano,int opcao) {
        GregorianCalendar data;
        double esforco;
        GregorianCalendar dataFinalTarefaAtribuir,dataFinalTarefa;
        int durTarefa,durTarefaFinal;
        Tarefa tarefa;
        data = new GregorianCalendar(ano, mes, dia);
        if(pessoa==null) {
            if (opcao==0) {
                tarefa = new Desenvolvimento(data, duracaoEstimada);
                tarefas.add(tarefa);
            } else if (opcao==1) {
                tarefa = new Design(data, duracaoEstimada);
                tarefas.add(tarefa);
            } else {
                tarefa = new Documentacao(data, duracaoEstimada);
                tarefas.add(tarefa);
            }
        }
        else{
            if (opcao==0) {
                tarefa = new Desenvolvimento(data, duracaoEstimada, pessoa);
                durTarefa=(int)Math.ceil(tarefa.getDuracao());
                esforco=tarefa.getTaxaEsforco();
                dataFinalTarefaAtribuir=tarefa.getDataInicio();
                dataFinalTarefaAtribuir.add(GregorianCalendar.MONTH,durTarefa);
                for (int i = 0; i <pessoa.getTarefas().size() ; i++) {
                    durTarefaFinal=(int)Math.ceil(pessoa.getTarefas().get(i).getDuracao());
                    dataFinalTarefa=pessoa.getTarefas().get(i).getDataInicio();
                    dataFinalTarefa.add(GregorianCalendar.MONTH,durTarefaFinal);
                    esforco+=verificaEsforço(pessoa.getTarefas().get(i).getDataInicio(),dataFinalTarefa,tarefa.getDataInicio(),dataFinalTarefaAtribuir,pessoa.getTarefas().get(i));
                    if(esforco>1){
                        JOptionPane.showMessageDialog(null, "Pessoa com taxa de Esforço ao máximo!");
                        return;
                    }
                }
                tarefas.add(tarefa);
                pessoa.addTarefa(tarefa);
            } else if (opcao==1) {
                tarefa = new Design(data, duracaoEstimada, pessoa);
                durTarefa=(int)Math.ceil(tarefa.getDuracao());
                esforco=tarefa.getTaxaEsforco();
                dataFinalTarefaAtribuir=tarefa.getDataInicio();
                dataFinalTarefaAtribuir.add(GregorianCalendar.MONTH,durTarefa);
                for (int i = 0; i <pessoa.getTarefas().size() ; i++) {
                    durTarefaFinal=(int)Math.ceil(pessoa.getTarefas().get(i).getDuracao());
                    dataFinalTarefa=pessoa.getTarefas().get(i).getDataInicio();
                    dataFinalTarefa.add(GregorianCalendar.MONTH,durTarefaFinal);
                    esforco+=verificaEsforço(pessoa.getTarefas().get(i).getDataInicio(),dataFinalTarefa,tarefa.getDataInicio(),dataFinalTarefaAtribuir,pessoa.getTarefas().get(i));
                    if(esforco>1){
                        JOptionPane.showMessageDialog(null, "Pessoa com taxa de Esforço ao máximo!");
                        return;
                    }
                }
                tarefas.add(tarefa);
                pessoa.addTarefa(tarefa);
            } else {
                tarefa = new Documentacao(data, duracaoEstimada, pessoa);
                durTarefa=(int)Math.ceil(tarefa.getDuracao());
                esforco=tarefa.getTaxaEsforco();
                dataFinalTarefaAtribuir=tarefa.getDataInicio();
                dataFinalTarefaAtribuir.add(GregorianCalendar.MONTH,durTarefa);
                for (int i = 0; i <pessoa.getTarefas().size() ; i++) {
                    durTarefaFinal=(int)Math.ceil(pessoa.getTarefas().get(i).getDuracao());
                    dataFinalTarefa=pessoa.getTarefas().get(i).getDataInicio();
                    dataFinalTarefa.add(GregorianCalendar.MONTH,durTarefaFinal);
                    esforco+=verificaEsforço(pessoa.getTarefas().get(i).getDataInicio(),dataFinalTarefa,tarefa.getDataInicio(),dataFinalTarefaAtribuir,pessoa.getTarefas().get(i));
                    if(esforco>1){
                        JOptionPane.showMessageDialog(null, "Pessoa com taxa de Esforço ao máximo!");
                        return;
                    }
                }
                tarefas.add(tarefa);
                pessoa.addTarefa(tarefa);
            }
        }
    }

    //falta testar se esta certoooo
    /**
     * Atribui uma tarefa a uma pessoa
     * @param tarefa Tarefa a atribuir
     * @param pessoa Pessoa cuja tarefa se vai atribuir
     */
    public void atribuirTarefa(Tarefa tarefa,Pessoa pessoa) {
        double esforco;
        GregorianCalendar dataFinalTarefaAtribuir,dataFinalTarefa;
        int durTarefa=(int)Math.ceil(tarefa.getDuracao()),durTarefaFinal;
        if (pessoa == null) {
            JOptionPane.showMessageDialog(null, "Pessoa não existe");
        }
        else{
            esforco=tarefa.getTaxaEsforco();
            dataFinalTarefaAtribuir=tarefa.getDataInicio();
            dataFinalTarefaAtribuir.add(GregorianCalendar.MONTH,durTarefa);
            for (int i = 0; i <pessoa.getTarefas().size() ; i++) {
                durTarefaFinal=(int)Math.ceil(pessoa.getTarefas().get(i).getDuracao());
                dataFinalTarefa=pessoa.getTarefas().get(i).getDataInicio();
                dataFinalTarefa.add(GregorianCalendar.MONTH,durTarefaFinal);
                esforco+=verificaEsforço(pessoa.getTarefas().get(i).getDataInicio(),dataFinalTarefa,tarefa.getDataInicio(),dataFinalTarefaAtribuir,pessoa.getTarefas().get(i));
                if(esforco>1){
                    JOptionPane.showMessageDialog(null, "Pessoa com taxa de Esforço ao máximo!");
                    return;
                }
            }
            tarefa.atribuirPessoa(pessoa);
            pessoa.addTarefa(tarefa);
        }
    }

    /**
     * Vai atualizar a conclusao da tarefa
     * @param tarefa Tarefa a qual se vai mudar a conclusão
     * @param perConclusao Percentagem de Conclusão a meter na tarefa
     */
    public void atualizaConclusao(Tarefa tarefa, double perConclusao){
        GregorianCalendar dataFim;
        String data=labelInformacaoDataAtual.getText();
        String[] data_list=data.split(" ");
        data_list=data_list[1].split("/");
        dataFim=new GregorianCalendar(Integer.parseInt(data_list[0]),Integer.parseInt(data_list[1]),Integer.parseInt(data_list[2]));
        tarefa.setPerConclusao(perConclusao);
        if(perConclusao==100){
            Pessoa pessoa=tarefa.getPessoaResponsavel();
            pessoa.setEsforco(pessoa.getEsforco() - tarefa.getTaxaEsforco());
            tarefa.setDataFim(dataFim);
        }
    }

    /**
     * Função para calcular o custo estimado do Projeto
     * @return Custo estimado do Projeto
     */
    public double custo(){
        double total=0;
        for (int i = 0; i <doutorados.size() ; i++) {
            total+=doutorados.get(i).getCusto();
        }
        for (int i = 0; i <doutorados.size() ; i++) {
            total+=doutorados.get(i).getCusto();
        }
        return total;
    }

    //butoes
    /**
     * Butão que vai atualizar a Data Atual
     */
    private class atualizaData implements ActionListener{
        public void actionPerformed(ActionEvent actionEvent){
            String value = JOptionPane.showInputDialog(null, "Introduza a data", "Data", JOptionPane.QUESTION_MESSAGE);
            String[] keep_data;
            ArrayList<Tarefa> tarefas;
            int duracao;
            GregorianCalendar dataFinalEstimada,dataAtual;
            try {
                keep_data=value.split("/");
                labelInformacaoDataAtual.setText("Data atual: "+keep_data[0]+"/"+keep_data[1]+"/"+keep_data[2]);
                dataAtual=new GregorianCalendar(Integer.parseInt(keep_data[0]),Integer.parseInt(keep_data[1]),Integer.parseInt(keep_data[2]));
                for(int i = 0; i < formandos.size() ; i++) {
                    tarefas=formandos.get(i).getTarefas();
                    if(tarefas!=null) {
                        for (int j = 0; j < tarefas.size(); j++) {
                            dataFinalEstimada = tarefas.get(j).getDataInicio();
                            duracao = (int) Math.ceil(tarefas.get(j).getDuracao());
                            dataFinalEstimada.add(GregorianCalendar.MONTH, duracao);
                            if (dataFinalEstimada.after(dataAtual)) {
                                formandos.get(i).setEsforco(formandos.get(i).getEsforco() + tarefas.get(j).getTaxaEsforco());
                            }
                        }
                    }
                }
                for (int i = 0; i <doutorados.size() ; i++) {
                    tarefas=doutorados.get(i).getTarefas();
                    if(tarefas!=null) {
                        for (int j = 0; j < tarefas.size(); j++) {
                            dataFinalEstimada = tarefas.get(j).getDataInicio();
                            duracao = (int) Math.ceil(tarefas.get(j).getDuracao());
                            dataFinalEstimada.add(GregorianCalendar.MONTH, duracao);
                            if (dataFinalEstimada.after(dataAtual)) {
                                doutorados.get(i).setEsforco(doutorados.get(i).getEsforco() + tarefas.get(j).getTaxaEsforco());
                            }
                        }
                    }
                }
            }catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(null, "Error a converter data. Verifique que está separada por / e são numeros", "ERRO!", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    /**
     * Classe que vai implementar o butao para criar uma nova tarefa
     */
    private class buttonTarefa implements ActionListener{
        public void actionPerformed(ActionEvent actionEvent) {
            String[] data,dataAtual;
            Date data1,data2;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dataBuffer;
            int dia,mes,ano;
            double duracao;
            Pessoa pessoa;
            try {
                data = textFieldData.getText().split("/");
                try {
                    dia = Integer.parseInt(data[0]);
                    mes = Integer.parseInt(data[1]);
                    ano = Integer.parseInt(data[2]);
                    if (verificaData(dia, mes, ano) == 1) {
                        try {
                            duracao = Double.parseDouble(textFieldDuracaoEstimada.getText());
                            try {
                                if (listaPessoas.getSelectedIndex() == -1) {
                                    dataBuffer = labelInformacaoDataAtual.getText();
                                    dataAtual = dataBuffer.split(" ");
                                    dataAtual = dataAtual[2].split("/");
                                    data1 = sdf.parse(dataAtual[0] + "/" + dataAtual[1] + "/" + dataAtual[2]);
                                    data2 = sdf.parse(dia + "/" + mes + "/" + ano);
                                    if (data2.compareTo(data1)<0) {
                                        JOptionPane.showMessageDialog(null, "Data Inserida inferior a data atual!");
                                        return ;
                                    } else {
                                        criaTarefa(null, duracao, dia, mes, ano, c.getSelectedIndex());
                                    }
                                }
                                else if (listaPessoas.getSelectedIndices().length > 1) {
                                    JOptionPane.showMessageDialog(null, "Demasiadas Pessoas Selecionadas!");
                                } else {
                                    System.out.println(listaPessoas.getSelectedIndex());
                                    if (listaPessoas.getSelectedIndex() == 0) {
                                        pessoa = investigadorPrincipal;
                                        if (pessoa.getEsforco() == 1) {
                                            JOptionPane.showMessageDialog(null, "Pessoa tem o esforço no maximo");
                                            return;
                                        }
                                    } else if (listaPessoas.getSelectedIndex() > 0 & listaPessoas.getSelectedIndex() < formandos.size()) {
                                        pessoa = formandos.get(listaPessoas.getSelectedIndex() - 1);
                                        if (pessoa.getEsforco() == 1) {
                                            JOptionPane.showMessageDialog(null, "Pessoa tem o esforço no maximo");
                                            return;
                                        }
                                        //falta ver se a tarefa pode ser feita ou nao
                                    } else if(listaPessoas.getSelectedIndex()>formandos.size() & listaPessoas.getSelectedIndex()<formandos.size()+doutorados.size()) {
                                        pessoa = doutorados.get(listaPessoas.getSelectedIndex() - 1 - formandos.size());
                                        if (pessoa.getEsforco() == 1) {
                                            JOptionPane.showMessageDialog(null, "Pessoa tem o esforço no maximo");
                                            return;
                                        }
                                    }
                                    else{
                                        pessoa=docentes.get(listaPessoas.getSelectedIndex()-1-formandos.size()-doutorados.size());
                                        if (pessoa.getEsforco() == 1) {
                                            JOptionPane.showMessageDialog(null, "Pessoa tem o esforço no maximo");
                                            return;
                                        }
                                    }
                                    dataBuffer = labelInformacaoDataAtual.getText();
                                    dataAtual = dataBuffer.split(" ");
                                    dataAtual = dataAtual[2].split("/");
                                    data1 = sdf.parse(dataAtual[0] + "/" + dataAtual[1] + "/" + dataAtual[2]);
                                    data2 = sdf.parse(dia + "/" + mes + "/" + ano);
                                    if (data2.compareTo(data1)<0) {
                                        JOptionPane.showMessageDialog(null, "Data Inserida inferior a data atual!");
                                        return ;
                                    }else {
                                            criaTarefa(pessoa, duracao, dia, mes, ano, c.getSelectedIndex());
                                        }
                                    }
                                panelJuncao.removeAll();
                                panelLista.removeAll();
                                DefaultListModel listListaValues;
                                listListaValues = listarTarefa();
                                listaTarefas = new JList(listListaValues);
                                listTarefasScroller = new JScrollPane(listaTarefas);
                                tarefasLista = new JLabel("Lista das Tarefas do Projeto");
                                tarefasLista.setPreferredSize(new Dimension(250, 100));
                                panelLista.add(tarefasLista, BorderLayout.NORTH);
                                panelLista.add(listaTarefas, BorderLayout.CENTER);
                                panelJuncao.add(panelLista, BorderLayout.CENTER);
                                panelJuncao.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.EAST);
                                frame.setVisible(true);
                                frameCria.setVisible(false);
                            }catch(ParseException ex){
                                JOptionPane.showMessageDialog(null, "Error no parse!");
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Duração não é um double");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Data não é válida");
                    }
                }catch (ArrayIndexOutOfBoundsException ex){
                    JOptionPane.showMessageDialog(null, "Data sem todos os numeros");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Veja se a data esta separada por /");
            }
        }
    }

    /**
     * Classe que vai abrir a frame com os paineis para criar uma nova tarefa
     */
    private class criaTarefa implements ActionListener{
        public void actionPerformed(ActionEvent e){
            panelCria.removeAll();

            panelCria.setLayout(new GridLayout(4,2));
            labelInputData= new JLabel("Data");
            labelDuracaoEstimada= new JLabel("Duração Estimada");
            labelTipo=new JLabel("Tipo de Tarefa");
            textFieldData=new JTextField(15);
            textFieldDuracaoEstimada=new JTextField(5);
            buttonCria=new JButton("Criar Tarefa");
            buttonCria.addActionListener(new buttonTarefa());

            panelCria.add(labelDuracaoEstimada);
            panelCria.add(textFieldDuracaoEstimada);
            panelCria.add(labelInputData);
            panelCria.add(textFieldData);
            panelCria.add(labelTipo);
            panelCria.add(c);
            panelCria.add(buttonCria);
            panelCria.add(buttonVoltarAtras);
            frameCria.add(panelCria);
            frameCria.setTitle("Cria uma Tarefa");

            frame.setVisible(false);
            frameCria.setSize(300,500);
            frameCria.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frameCria.setLocationRelativeTo(null);
            frameCria.setVisible(true);

        }
    }

    /**
     * Classe que vai por todas as frames inviseveis menos a principal
     */
    private class buttonVoltaAtras implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            panelJuncao.removeAll();
            panelLista.removeAll();
            DefaultListModel listListaValues;
            listListaValues = listarTarefa();
            listaTarefas = new JList(listListaValues);
            listTarefasScroller = new JScrollPane(listaTarefas);
            tarefasLista = new JLabel("Lista das Tarefas do Projeto");
            tarefasLista.setPreferredSize(new Dimension(250, 100));
            panelLista.add(tarefasLista, BorderLayout.NORTH);
            panelLista.add(listaTarefas, BorderLayout.CENTER);
            panelJuncao.add(panelLista, BorderLayout.CENTER);
            panelJuncao.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.EAST);
            frame.setVisible(true);
            frameCria.setVisible(false);
            frameAtualiza.setVisible(false);
            frameLista.setVisible(false);
            frame.setVisible(true);
        }
    }

    /**
     * Fecha a frame e abre a dos projetos
     */
    private class fecharFrame implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            frameOriginal.setVisible(true);
        }
    }

    /**
     * Vai eliminar a tarefa selecionada na lista pelo user
     */
    private class buttonEliminaTarefa implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(listaTarefas.getSelectedIndex()==-1){
                JOptionPane.showMessageDialog(null,"Escolha uma opção");
            }
            else if(listaTarefas.getSelectedIndices().length>1){
                JOptionPane.showMessageDialog(null,"Escolha só uma tarefa");
            }
            else{
                eliminaTarefa(listaTarefas.getSelectedIndex());
                panelJuncao.removeAll();
                panelLista.removeAll();
                DefaultListModel listListaValues;
                listListaValues = listarTarefa();
                listaTarefas = new JList(listListaValues);
                listTarefasScroller = new JScrollPane(listaTarefas);
                tarefasLista = new JLabel("Lista das Tarefas do Projeto");
                tarefasLista.setPreferredSize(new Dimension(250, 100));
                panelLista.add(tarefasLista, BorderLayout.NORTH);
                panelLista.add(listaTarefas, BorderLayout.CENTER);
                panelJuncao.add(panelLista, BorderLayout.CENTER);
                panelJuncao.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.EAST);
                frame.setVisible(true);
            }
        }
    }

    /**
     * Vai atribuir a Tarefa selecionada na lista a pessoa selecionada na lista
     */
    private class buttonAtribuiTarefa implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Pessoa pessoa;
            if(listaPessoas.getSelectedIndices().length>1 | listaTarefas.getSelectedIndices().length>1){
                JOptionPane.showMessageDialog(null, "Tem demasiadas Pessoas/Tarefas Selecionadas");
            }
            else if(listaPessoas.getSelectedIndex()==-1 | listaTarefas.getSelectedIndex()==-1){
                JOptionPane.showMessageDialog(null, "Não tem Pessoas/Tarefas Selecionadas");
            }
            else {
                if (listaPessoas.getSelectedIndex() == 1) {
                    pessoa = investigadorPrincipal;
                    if (pessoa.getEsforco() == 1) {
                        JOptionPane.showMessageDialog(null, "Pessoa tem o esforço no maximo");
                        return;
                    }
                } else if (listaPessoas.getSelectedIndex() > 1 & listaPessoas.getSelectedIndex() < 1 + formandos.size()) {
                    pessoa = formandos.get(listaPessoas.getSelectedIndex() - 2);
                    if (pessoa.getEsforco() == 1) {
                        JOptionPane.showMessageDialog(null, "Pessoa tem o esforço no maximo");
                        return;
                    }
                    //falta ver se a tarefa pode ser feita ou nao
                } else if(listaPessoas.getSelectedIndex()>1+formandos.size() & listaPessoas.getSelectedIndex()<1+formandos.size()+doutorados.size()) {
                    pessoa = doutorados.get(listaPessoas.getSelectedIndex() - 2 - formandos.size());
                    if (pessoa.getEsforco() == 1) {
                        JOptionPane.showMessageDialog(null, "Pessoa tem o esforço no maximo");
                        return;
                    }
                }
                else{
                    pessoa=docentes.get(listaPessoas.getSelectedIndex()-2-formandos.size()-doutorados.size());
                    if (pessoa.getEsforco() == 1) {
                        JOptionPane.showMessageDialog(null, "Pessoa tem o esforço no maximo");
                        return;
                    }
                }
                atribuirTarefa(tarefas.get(listaTarefas.getSelectedIndex()),pessoa);
            }
            panelJuncao.removeAll();
            panelLista.removeAll();
            DefaultListModel listListaValues;
            listListaValues = listarTarefa();
            listaTarefas = new JList(listListaValues);
            listTarefasScroller = new JScrollPane(listaTarefas);
            tarefasLista = new JLabel("Lista das Tarefas do Projeto");
            tarefasLista.setPreferredSize(new Dimension(250, 100));
            panelLista.add(tarefasLista, BorderLayout.NORTH);
            panelLista.add(listaTarefas, BorderLayout.CENTER);
            panelJuncao.add(panelLista, BorderLayout.CENTER);
            panelJuncao.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.EAST);
            frame.setVisible(true);
            frameCria.setVisible(false);
        }
    }

    /**
     * Vai atualizar a conlusão da tarefa selecionada pelo user
     */
    private class atualizaConclusao implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Double conclusao;
            if(listaTarefas.getSelectedIndices().length>1){
                JOptionPane.showMessageDialog(null, "Demasiadas Tarefas Selecionadas");
            }
            else if(listaTarefas.getSelectedIndex()==-1){
                JOptionPane.showMessageDialog(null, "Nenhuma Tarefa Selecionada");
            }
            else{
                try{
                    conclusao=Double.parseDouble(textFieldConclusao.getText());
                    if(tarefas.get(listaTarefas.getSelectedIndex()).perConclusao>conclusao){
                        JOptionPane.showMessageDialog(null, "Conclusao atual menor que a anterior");
                    }
                    else if(conclusao>100){
                        JOptionPane.showMessageDialog(null, "Conclusao atual menor que a anterior");
                    }
                    else {
                        atualizaConclusao(tarefas.get(listaTarefas.getSelectedIndex()), conclusao);
                        panelJuncao.removeAll();
                        panelLista.removeAll();
                        DefaultListModel listListaValues;
                        listListaValues = listarTarefa();
                        listaTarefas = new JList(listListaValues);
                        listTarefasScroller = new JScrollPane(listaTarefas);
                        tarefasLista = new JLabel("Lista das Tarefas do Projeto");
                        tarefasLista.setPreferredSize(new Dimension(250, 100));
                        panelLista.add(tarefasLista, BorderLayout.NORTH);
                        panelLista.add(listaTarefas, BorderLayout.CENTER);
                        panelJuncao.add(panelLista, BorderLayout.CENTER);
                        panelJuncao.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.EAST);
                        frame.setVisible(true);
                        frameAtualiza.setVisible(false);
                    }
                }
                catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Conclusão nao é um double");
                }
            }
        }
    }

    /**
     * Vai abrir a frame com os elementos necessários para atualizar a conclusao da tarefa
     */
    private class buttonAtualizaConclusao implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            panelAtualiza.removeAll();

            labelInputConclusao=new JLabel("Percentagem de Conclusão");
            textFieldConclusao=new JTextField(10);

            buttonAtualiza=new JButton("Atualizar Conclusão");
            buttonAtualiza.addActionListener(new atualizaConclusao());

            panelAtualiza.add(labelInputConclusao);
            panelAtualiza.add(textFieldConclusao);
            panelAtualiza.add(buttonAtualiza);
            panelAtualiza.add(buttonVoltarAtras);

            frameAtualiza.add(panelAtualiza);
            frameAtualiza.setLocationRelativeTo(null);
            frameAtualiza.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            frameAtualiza.setTitle("Atualizar Conclusao de uma tarefa");
            frameAtualiza.setSize(500,400);
            frameAtualiza.setVisible(true);
            frame.setVisible(false);
        }
    }

    /**
     * Frame com todas as tarefas nao iniciadas
     */
    private class buttonListaTarefasNaoIniciadas implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            DefaultListModel listListaValues;
            panelLista.removeAll();

            listListaValues = listaTarefasNaoIniciadas();
            if (listListaValues.size() == 0) {
                JOptionPane.showMessageDialog(null, "Sem Tarefas não Iniciadas");
            } else {
                listaTarefas = new JList(listListaValues);
                listTarefasScroller = new JScrollPane(listaTarefas);
                panelLista.setLayout(new BorderLayout());
                buttonVoltarAtras = new JButton("Voltar Atras");
                buttonVoltarAtras.addActionListener(new buttonVoltaAtras());
                panelLista.add(listaTarefas,BorderLayout.CENTER);
                //panelLista.add(listTarefasScroller,BorderLayout.CENTER);
                panelLista.add(buttonVoltarAtras,BorderLayout.SOUTH);
                frameLista.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                frameLista.setTitle("Lista de Tarefas Não Iniciadas");
                frameLista.setSize(600, 400);
                frameLista.setLocationRelativeTo(null);
                frameLista.add(panelLista);
                frameLista.setVisible(true);
                frame.setVisible(false);
            }
        }
    }

    /**
     * Frame com todas as tarefas nao concluidas na data prevista
     */
    private class buttonListaTarefasNaoConcluidas implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            DefaultListModel listListaValues;
            panelLista.removeAll();

            listListaValues = listaTarefasNaoConcluidas();
            if (listListaValues.size() == 0) {
                JOptionPane.showMessageDialog(null, "Sem Tarefas não concluidas");
            } else {
                listaTarefas = new JList(listListaValues);
                listTarefasScroller = new JScrollPane(listaTarefas);
                panelLista.setLayout(new BorderLayout());
                buttonVoltarAtras = new JButton("Voltar Atras");
                buttonVoltarAtras.addActionListener(new buttonVoltaAtras());
                panelLista.add(listaTarefas,BorderLayout.CENTER);
                //panelLista.add(listTarefasScroller,BorderLayout.CENTER);
                panelLista.add(buttonVoltarAtras,BorderLayout.SOUTH);
                frameLista.setLocationRelativeTo(null);
                frameLista.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                frameLista.setTitle("Lista de Tarefas não Concluidas");
                frameLista.setSize(600, 500);
                frameLista.setVisible(true);
                frame.setVisible(false);
                frameLista.add(panelLista);
            }
        }
    }

    /**
     * Mensagem com o custo do projeto
     */
    private class buttonPrintCusto implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(formandos.size()==0 || doutorados.size()==0){
                JOptionPane.showMessageDialog(null, "Não tem nenhum Bolseiro no projeto, custo 0.", "ERRO", JOptionPane.PLAIN_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, custo() + "€", "Custo do Projeto", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    /**
     * Vai ver se é possivel concluir o projeto ou nao
     */
    private class buttonConcluir implements ActionListener{
        public void actionPerformed(ActionEvent e){
            GregorianCalendar dateFim,dataAtual;
            int dur=(int)Math.ceil(duracao);
            String[] data=labelInformacaoDataAtual.getText().split(" ");
            data=data[1].split("/");
            dataAtual=new GregorianCalendar(Integer.parseInt(data[0]),Integer.parseInt(data[1]),Integer.parseInt(data[2]));
            dateFim = dataAtual;
            dateFim.add(GregorianCalendar.MONTH,dur);
            for (int i = 0; i < tarefas.size() ; i++) {
                if(tarefas.get(i).perConclusao!=100){
                    JOptionPane.showMessageDialog(null, "Há tarefas por concluir não pode acabar o projeto", "Concluir Tarefas", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
            }
            if(dataAtual.after(dateFim)) {
                concluir = 1;
                frame.setVisible(false);
                frameOriginal.setVisible(true);

            }
            else{
                JOptionPane.showMessageDialog(null, "Ainda não passou o tempo necessário para acabar a tarefa!", "Concluir Tarefas", JOptionPane.PLAIN_MESSAGE);
            }

        }
    }

}