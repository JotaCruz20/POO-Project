import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Projeto{
    private String nome;
    private String acron;
    private GregorianCalendar dataInicio;
    private int duracao;
    private GregorianCalendar dataFim;
    private int numTarefas,concluir;
    private ArrayList<Tarefa> tarefas=new ArrayList<>();
    private Pessoa investigadrPrincipal;
    private ArrayList<Pessoa> docentes=new ArrayList<>();
    private ArrayList<Pessoa> bolseiros=new ArrayList<>();
    //gui main
    private JFrame frame;
    private JButton buttonCriaTarefa,buttonEliminaTarefa,buttonListaTarefaNConcluidas,buttonListaTarefasNaoIniciadas,buttonCusto,buttonConclusao,buttonAtualizarConclusao,buttonAtribuiTarefa;
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
    //gui elimina
    private JFrame frameElimina=new JFrame();
    private JPanel panelElimina=new JPanel();
    private JButton buttonElimina=new JButton();
    //gui atualiza
    private JFrame frameAtualiza=new JFrame();
    private JPanel panelAtualiza=new JPanel();
    private JLabel labelInputConclusao;
    private JTextField textFieldConclusao;
    private JButton buttonAtualiza;

    /**
     * Construtor de um Projeto que recebe os dados necessários para criar um novo projeto
     * @param nome Nome do projeto
     * @param acron Nome do acronimo do projeto
     * @param dataInicio Data de inicio do projeto
     * @param dataFim Data de fim do Projeto
     * @param duracao Duração estimada do projeto
     */
    public Projeto(String nome, String acron, GregorianCalendar dataInicio, GregorianCalendar dataFim, int duracao){
        this.nome=nome;
        this.acron = acron;
        this.dataInicio = dataInicio;
        this.duracao = duracao;
        this.dataFim = dataFim;
        docentes = new ArrayList<Pessoa>(); //CORRIGIR
        bolseiros = new ArrayList<Pessoa>();
    }

    /**
     * Vai construir todo o GUI prinicial do projeto
     */
    public void gui(int dia,int mes,int ano){
        //main gui
        frame=new JFrame();
        frame.setLayout(new BorderLayout(30,200));
        panel=new JPanel();
        panel.setLayout(new GridLayout(8,1,10,10));

        //adicionar a escolhas
        c.add("Design");
        c.add("Desenvolvimento");
        c.add("Documentação");

        //butao de voltar atras usado em varias frames
        buttonVoltarAtras=new JButton("Voltar Atras");
        buttonVoltarAtras.addActionListener(new buttonVoltaAtras());

        //butoes main
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
        panel.add(buttonConclusao);

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
    public int getDuracao() {
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
        //list.addElement(investigadrPrincipal.toString());
        for(int i=0;i<bolseiros.size();i++){
            if(tarefas.get(i).getPerConclusao()>0){
                list.addElement(bolseiros.get(i).toString());
            }
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
        for(int i=0;i<tarefas.size();i++){
            if(tarefas.get(i).getPerConclusao()<0){
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
     * Vai eliminar uma tarefa da lista
     * @param index Vai eliminar a tarefa na posiçao index da lista
     */
    public void eliminaTarefa(int index){
        tarefas.remove(index);
    }

    /**
     * Cria uma nova Tarefa
     * @param pessoa1 Pessoa a qual vai ser associada a Tarefa
     * @param duracaoEstimada Duração estimada da duraçao da tarefa
     * @param dia Dia do mes da data Inicial
     * @param mes  Mes da data Inicial
     * @param ano  Ano da data Inicial
     * @param opcao Que tipo de tarefa é, Design,Documentação ou Desenvolvimento
     */
    public void criaTarefa(Pessoa pessoa1,double duracaoEstimada,int dia,int mes,int ano,int opcao) {
        GregorianCalendar data;
        Pessoa pessoa = new Docente("Jose", "mail", 12, "Poop");
        Tarefa tarefa;
        data = new GregorianCalendar(ano, mes, dia);
        this.numTarefas+=1;
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
                tarefas.add(tarefa);
            } else if (opcao==1) {
                tarefa = new Design(data, duracaoEstimada, pessoa);
                tarefas.add(tarefa);
            } else {
                tarefa = new Documentacao(data, duracaoEstimada, pessoa);
                tarefas.add(tarefa);
            }
        }
    }

    /**
     * Atribui uma tarefa a uma pessoa
     * @param tarefa Tarefa a atribuir
     * @param pessoa Pessoa cuja tarefa se vai atribuir
     */
    public void atribuirTarefa(Tarefa tarefa,Pessoa pessoa) {
        if (pessoa == null) {
            JOptionPane.showMessageDialog(null, "Pessoa não existe");
        }
        else{
            tarefa.atribuirPessoa(pessoa);
        }
    }

    /**
     * Vai atualizar a conclusao da tarefa
     * @param tarefa Tarefa a qual se vai mudar a conclusão
     * @param perConclusao Percentagem de Conclusão a meter na tarefa
     */
    public void atualizaConclusao(Tarefa tarefa, double perConclusao){
        tarefa.setPerConclusao(perConclusao);
    }

    /**
     * Função para calcular o custo estimado do Projeto
     * @return Custo estimado do Projeto
     */
    public double custo(){
        double total=0;
        for (int i = 0; i <bolseiros.size() ; i++) {
            total+=bolseiros.get(i).getCusto()*bolseiros.get(i).getEsforco()*bolseiros.get(i).duracaoTarefas();
        }
        return total;
    }

    //butoes
    /**
     * Classe que vai implementar o butao para criar uma nova tarefa
     */
    private class buttonTarefa implements ActionListener{
        public void actionPerformed(ActionEvent actionEvent) {
            String[] data,dataAtual;
            Date data1,data2;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dataFim,dataBuffer;
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
                                    if (listaPessoas.getSelectedIndex() == 1) {
                                        pessoa = investigadrPrincipal;
                                        if (pessoa.getEsforco() == 1) {
                                            JOptionPane.showMessageDialog(null, "Pessoa tem o esforço no maximo");
                                            return;
                                        }
                                    } else if (listaPessoas.getSelectedIndex() > 1 & listaPessoas.getSelectedIndex() < 1 + bolseiros.size()) {
                                        pessoa = bolseiros.get(listaPessoas.getSelectedIndex() - 1);
                                        if (pessoa.getEsforco() == 1) {
                                            JOptionPane.showMessageDialog(null, "Pessoa tem o esforço no maximo");
                                            return;
                                        }
                                        //falta ver se a tarefa pode ser feita ou nao
                                    } else {
                                        pessoa = docentes.get(listaPessoas.getSelectedIndex() - 1 - bolseiros.size());
                                        if (pessoa.getEsforco() == 1) {
                                            JOptionPane.showMessageDialog(null, "Pessoa tem o esforço no maximo");
                                            return;
                                        }
                                    }
                                    dataBuffer = labelInformacaoDataAtual.getText();
                                    dataAtual = dataBuffer.split(" ");
                                    dataAtual = dataAtual[2].split("/");
                                    JOptionPane.showMessageDialog(null, dataAtual[2] + "" + ano + " " + dataAtual[1] + " " + mes + " " + dataAtual[0] + " " + dia);
                                    if (Integer.parseInt(dataAtual[2]) < ano) {
                                        JOptionPane.showMessageDialog(null, "Data inferior a data atual!");
                                        return;
                                    } else if (Integer.parseInt(dataAtual[1]) < mes) {
                                        JOptionPane.showMessageDialog(null, "Data inferior a data atual!");
                                        return;
                                    } else if (Integer.parseInt(dataAtual[0]) < dia) {
                                        JOptionPane.showMessageDialog(null, "Data inferior a data atual!");
                                        return;
                                    } else {
                                        dataBuffer = labelInformacaoDataAtual.getText();
                                        dataAtual = dataBuffer.split(" ");
                                        dataAtual = dataAtual[2].split("/");
                                        data1 = sdf.parse(dataAtual[0] + "/" + dataAtual[1] + "/" + dataAtual[2]);
                                        data2 = sdf.parse(dia + "/" + mes + "/" + ano);
                                        if (data2.compareTo(data1)<0) {
                                            JOptionPane.showMessageDialog(null, "Data Inserida inferior a data atual!");
                                            return ;
                                        } else {
                                            criaTarefa(pessoa, duracao, dia, mes, ano, c.getSelectedIndex());
                                        }
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
            frameElimina.setVisible(false);
            frameLista.setVisible(false);
            frame.setVisible(true);
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
                frameElimina.setVisible(false);
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
                    pessoa = investigadrPrincipal;
                    if (pessoa.getEsforco() == 1) {
                        JOptionPane.showMessageDialog(null, "Pessoa tem o esforço no maximo");
                        return;
                    }
                } else if (listaPessoas.getSelectedIndex() > 1 & listaPessoas.getSelectedIndex() < 1 + bolseiros.size()) {
                    pessoa = bolseiros.get(listaPessoas.getSelectedIndex() - 1);
                    if (pessoa.getEsforco() == 1) {
                        JOptionPane.showMessageDialog(null, "Pessoa tem o esforço no maximo");
                        return;
                    }
                } else {
                    pessoa = docentes.get(listaPessoas.getSelectedIndex() - 1 - bolseiros.size());
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

            frameAtualiza.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            frameAtualiza.setTitle("Atualizar Conclusao de uma tarefa");
            frameAtualiza.setSize(300,150);
            frameAtualiza.setVisible(true);
            frame.setVisible(false);
            frameAtualiza.add(panelAtualiza);
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
            if(bolseiros.size()==0){
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
            for (int i = 0; i < tarefas.size() ; i++) {
                if(tarefas.get(i).perConclusao!=100){
                    JOptionPane.showMessageDialog(null, "Há tarefas por concluir não pode acabar o projeto", "Concluir Tarefas", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
            }
            concluir=1;
        }
    }

}