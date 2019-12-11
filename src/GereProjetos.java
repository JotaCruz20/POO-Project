import javax.print.Doc;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import static java.lang.System.exit;
import static java.lang.System.setOut;


public class GereProjetos implements Serializable{

    private ArrayList<Projeto> projetos;
    private ArrayList<Pessoa> pessoas;

    //GUI Menu
    private JFrame framePrincipal,localFrame;
    private JPanel selectProjectPanel;
    private JButton nextButton;
    private JList projetosBox;
    private int windowX, windowY;

    private JComboBox opcaoAssocia;
    JList listaProjetos, listaPessoas;

    //gui CriaProjeto
    private JFrame framePrincipalCriaProjeto;
    private JTextField inputNome, inputAcron, inputDataInicio,inputDuracao,inputDataFim;

    public GereProjetos() {
        windowX = 300;
        windowY = 150;

        projetos = new ArrayList<>();
        pessoas = new ArrayList<>();

        lerFicheiro();

        int width = windowX - 2 * (windowX / 10);
        int height = width - width / 10;

        framePrincipal = new JFrame();
        framePrincipal.setTitle("Menu Inicial");
        framePrincipal.setSize(windowX, windowY);
        framePrincipal.setLayout(new BorderLayout());
        framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //optionPanel
        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new GridLayout(4, 1));

        JLabel label = new JLabel("Escolha uma das seguintes opções");
        label.setSize(width, height);

        JButton addProjetoButton = new JButton("Adicionar projeto");
        addProjetoButton.setSize(width, height);
        addProjetoButton.addActionListener(new addProjectAction());

        JButton addPessoaButton = new JButton("Associar pessoa ao projeto");
        addPessoaButton.setSize(width, height);
        addPessoaButton.addActionListener(new addPessoaAction());

        //DefaultListModel listaProjetosCriados = listaProjetos();

        JButton selecionaProjecto=new JButton("Abrir Projeto");
        selecionaProjecto.setSize(width,height);
        selecionaProjecto.addActionListener(new selecionarProjecto());

        optionPanel.add(label);
        optionPanel.add(addProjetoButton);
        optionPanel.add(addPessoaButton);
        optionPanel.add(selecionaProjecto);


        //selectProjectPanel.add(selectProjectBox);
        //selectProjectPanel.add(selectCreatedProjectButton);

        JButton closeButton = new JButton("Fechar");
        closeButton.setSize(width,height);
        closeButton.addActionListener(new fecharAction());

        JPanel nextPanel = new JPanel();

        nextPanel.add(closeButton);

        selectProjectPanel = new JPanel();

        projetosBox = new JList(listaProjetos());
        projetosBox.setVisibleRowCount(3);
        projetosBox.setFixedCellWidth(windowX - 2 * (windowX / 10));
        selectProjectPanel.add(projetosBox);

        JScrollPane listProjetosScroller = new JScrollPane(projetosBox);

        selectProjectPanel.add(listProjetosScroller);

        framePrincipal.add(optionPanel,BorderLayout.NORTH);
        framePrincipal.add(selectProjectPanel,BorderLayout.CENTER);
        framePrincipal.add(nextPanel,BorderLayout.SOUTH);
        //selectProjectPanel.setVisible(tru);
        framePrincipal.setSize(500,500);
        framePrincipal.setLocationRelativeTo(null);

        framePrincipal.setVisible(true);
    }

    private class selecionarProjecto implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String value;
            String[] keep_data;
            int[] data=new int[3];
            if(projetosBox!=null) {
                if (projetosBox.getSelectedIndices().length > 1) {
                    JOptionPane.showMessageDialog(null, "Demasiados Projectos Selecionadas", "ERROR", JOptionPane.PLAIN_MESSAGE);
                } else if (projetosBox.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(null, "Nenhum Projecto Selecionada", "ERROR", JOptionPane.PLAIN_MESSAGE);
                } else {
                    try {
                        value = JOptionPane.showInputDialog(null, "Introduza o valor", "Input", JOptionPane.QUESTION_MESSAGE);
                        keep_data = value.split("/");
                        data[0] = Integer.parseInt(keep_data[0]);
                        data[1] = Integer.parseInt(keep_data[1]);
                        data[2] = Integer.parseInt(keep_data[2]);
                        while (!verificarData(data)) {
                            JOptionPane.showMessageDialog(null, "Ponha uma data correta entre /", "ERROR!", JOptionPane.PLAIN_MESSAGE);
                            value = JOptionPane.showInputDialog(null, "Introduza o valor", "Input", JOptionPane.QUESTION_MESSAGE);
                            keep_data = value.split(" ");
                            data[0] = Integer.parseInt(keep_data[0]);
                            data[1] = Integer.parseInt(keep_data[1]);
                            data[2] = Integer.parseInt(keep_data[2]);
                        }
                        projetos.get(projetosBox.getSelectedIndex()).gui(data[0], data[1], data[2]);
                        framePrincipal.setVisible(false);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Ponha uma data correta entre /", "ERROR!", JOptionPane.PLAIN_MESSAGE);

                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Nenhum Projecto Selecionado", "ERROR", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    private class addPessoaAction implements ActionListener{
        public void actionPerformed(ActionEvent e) {

            framePrincipal.setVisible(false);

            windowX = 300;
            windowY = 300;
            int width = windowX - 2 * (windowX / 10);
            int height = width - width / 10;

            localFrame = new JFrame();
            localFrame.setTitle("Associar pessoa");
            localFrame.setSize(windowX, windowY);
            localFrame.setLayout(new GridLayout(2,1));
            //localFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JLabel selecionarLabel = new JLabel("Selecione uma pessoa para associar ao projeto");

            JButton atualizarButton = new JButton("Atualizar");
            atualizarButton.setSize(width,height);
            atualizarButton.addActionListener(new atualizarAction());

            JButton associarButton = new JButton("Associar a projeto");
            associarButton.setSize(width,height);
            associarButton.addActionListener(new associarAction());

            JButton voltarButton = new JButton("Voltar");
            voltarButton.setSize(width,height);
            voltarButton.addActionListener(new voltarMenuAction());

            JPanel listaPanel = new JPanel();
            listaPanel.setLayout(new GridLayout(1,2));

            listaPessoas = new JList(listaDocentes());
            projetosBox.setVisibleRowCount(3);
            projetosBox.setFixedCellWidth(windowX/2 -2);

            JScrollPane listPessoasScroller = new JScrollPane(listaPessoas);

            listaProjetos = new JList(listaProjetos());
            projetosBox.setVisibleRowCount(3);
            projetosBox.setFixedCellWidth(windowX/2 -2);
            JScrollPane listProjetosScroller = new JScrollPane(listaProjetos);


            String[] opcao= {"Investigador Principal", "Docente","Bolseiro"};
            opcaoAssocia= new JComboBox(opcao);
            opcaoAssocia.setSize(30,15);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(0,1));
            JPanel panelButtons = new JPanel();

            panel.add(selecionarLabel);
            panel.add(opcaoAssocia);

            listaPanel.add(listaPessoas);
            listaPanel.add(listPessoasScroller);
            listaPanel.add(listaProjetos);
            listaPanel.add(listProjetosScroller);

            panel.add(listaPanel);

            panelButtons.add(atualizarButton);
            panelButtons.add(associarButton);
            panelButtons.add(voltarButton);

            localFrame.add(panel,BorderLayout.NORTH);
            localFrame.add(panelButtons,BorderLayout.SOUTH);

            localFrame.setVisible(true);
        }
    }

    private class addProjectAction implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            String nome, acron;
            GregorianCalendar dataInicio, dataFim;
            double duracao;

            framePrincipal.setVisible(false);

            framePrincipalCriaProjeto = new JFrame();
            framePrincipalCriaProjeto.setTitle("Criar projeto novo");
            framePrincipalCriaProjeto.setSize(350,320);
            framePrincipalCriaProjeto.setLayout(new BorderLayout());
            framePrincipalCriaProjeto.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panelButoes = new JPanel();
            JPanel panelInfo = new JPanel();
            JPanel panelVazio = new JPanel();
            panelInfo.setLayout(new GridLayout(7,1));

            JButton buttonConcluir = new JButton("Concluir");
            buttonConcluir.addActionListener(new concluirAction());
            JButton buttonVoltar = new JButton("Voltar");
            buttonVoltar.addActionListener(new voltarAction());

            panelButoes.add(buttonVoltar);
            panelButoes.add(buttonConcluir);

            JLabel labelTitulo = new JLabel("Criar novo projeto");
            panelInfo.add(labelTitulo);
            panelInfo.add(panelVazio);

            JLabel labelNome = new JLabel("Nome:");
            inputNome = new JTextField();
            panelInfo.add(labelNome);
            panelInfo.add(inputNome);

            JLabel labelAcron = new JLabel("Acrónimo:");
            inputAcron = new JTextField();
            panelInfo.add(labelAcron);
            panelInfo.add(inputAcron);

            JLabel labelDataInicio = new JLabel("Data Início:");
            inputDataInicio = new JTextField();
            panelInfo.add(labelDataInicio);
            panelInfo.add(inputDataInicio);

            JLabel labelDuracao = new JLabel("Duração:");
            inputDuracao = new JTextField();
            panelInfo.add(labelDuracao);
            panelInfo.add(inputDuracao);

            JLabel labelDataFim = new JLabel("Data Fim");
            inputDataFim = new JTextField();
            inputDataFim.setEditable(false);

            panelInfo.add(labelDataFim);
            panelInfo.add(inputDataFim);

            framePrincipalCriaProjeto.add(panelInfo,BorderLayout.NORTH);
            framePrincipalCriaProjeto.add(panelButoes,BorderLayout.SOUTH);

            framePrincipalCriaProjeto.setVisible(true);
        }
    }

    private class atualizarAction implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            if(opcaoAssocia.getSelectedIndex() ==0 || opcaoAssocia.getSelectedIndex()==1 ){
                listaPessoas.setModel(listaDocentes());
            }
            else if(opcaoAssocia.getSelectedIndex()==2){
                listaPessoas.setModel(listaBolseiros());
            }
            else{
                JOptionPane.showMessageDialog(null,"Opção inválida");
            }
        }
    }

    private class associarAction implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            int opcao = 1;
            //testar se tanto projeto como pessoa estão selecionados

            if(listaPessoas.getSelectedIndex()<0 || listaProjetos.getSelectedIndex()<0 ) {
                JOptionPane.showMessageDialog(null, "Tem dados em falta");
                localFrame.setVisible(true);
            }
            else{
                if(opcaoAssocia.getSelectedIndex()==0){
                    //fazer verificação se não tem já investigador, se tiver, perguntar se quer mudar ou não
                    if(projetos.get(listaProjetos.getSelectedIndex()).getInvestigadorPrincipal()!=null){ //já tem investigador!
                        opcao = JOptionPane.showConfirmDialog(null,"Já tem um Investigador Principal associado a este projeto. Deseja mudar?","Sair",JOptionPane.YES_NO_OPTION);
                    }
                    if (opcao==1) {
                        //mudar investigador
                        Pessoa investigador = null;
                        for (Pessoa pessoa : pessoas) {
                            if (pessoa.getNome().equals(listaPessoas.getSelectedValue())) {
                                investigador = pessoa;
                            }
                        }
                        if (investigador == null) {
                            JOptionPane.showMessageDialog(null, "Parece ter havido algum erro... Selecione outro investigador");
                        } else {
                            projetos.get(listaProjetos.getSelectedIndex()).setInvestigadorPrincipal(investigador);
                        }
                    }
                } //investigador principal
                else if(opcaoAssocia.getSelectedIndex()==1 ){
                    //verifica se pessoa não está já associada, se estiver, manda-se mensagem a avisar; pergunta-se se se quer tirar
                    int flag=0;
                    if(projetos.get(listaProjetos.getSelectedIndex()).getDocentes().size()>0){
                        for(Pessoa pessoa: projetos.get(listaProjetos.getSelectedIndex()).getDocentes()) {
                            if (listaPessoas.getSelectedValue().equals(pessoa.getNome())) {
                                JOptionPane.showMessageDialog(null, "Investigador já pertence ao projeto");
                                flag = 1;
                            }
                        }
                        if(flag==0){
                            Docente docente = null;

                            for(Pessoa pessoaDocente: pessoas){
                                if(pessoaDocente.getNome().equals(listaPessoas.getSelectedValue().toString())){
                                    docente = (Docente)pessoaDocente;
                                }
                            }
                            if (docente == null) {
                                JOptionPane.showMessageDialog(null, "Parece ter havido algum erro... Selecione outro investigador");
                            } else {
                                System.out.println("adicionou!");
                                projetos.get(listaProjetos.getSelectedIndex()).getDocentes().add(docente);
                            }
                        }
                    }
                    else{ //se não tiver nenhum seleciona logo!
                        Docente docente = null;

                        for(Pessoa pessoaDocente: pessoas){
                            System.out.println(pessoaDocente.getNome() +" "+listaPessoas.getSelectedValue().toString()+" "+pessoaDocente.getNome().equals(listaPessoas.getSelectedValue().toString()));
                            if(pessoaDocente.getNome().equals(listaPessoas.getSelectedValue().toString())){
                                docente = (Docente)pessoaDocente;
                            }
                        }
                        System.out.println(docente);
                        if (docente == null) {
                            JOptionPane.showMessageDialog(null, "Parece ter havido algum erro... Selecione outro investigador");
                        } else {
                            projetos.get(listaProjetos.getSelectedIndex()).getDocentes().add(docente);
                        }
                    }



                } //docentes
                else if(opcaoAssocia.getSelectedIndex()==2){
                    int flag=0;
                    if(projetos.get(listaProjetos.getSelectedIndex()).getFormandos().size()>0 || projetos.get(listaProjetos.getSelectedIndex()).getDoutorados().size()>0){ //já existe no projeto
                        for(Pessoa pessoa: projetos.get(listaProjetos.getSelectedIndex()).getFormandos()) {
                            if (listaPessoas.getSelectedValue().equals(pessoa.getNome())) {
                                JOptionPane.showMessageDialog(null, "Bolseiro já pertence ao projeto");
                                flag = 1;
                            }
                        }
                        for(Pessoa pessoa: projetos.get(listaProjetos.getSelectedIndex()).getDoutorados()) {
                            if (listaPessoas.getSelectedValue().equals(pessoa.getNome())) {
                                JOptionPane.showMessageDialog(null, "Bolseiro já pertence ao projeto");
                                flag = 1;
                            }
                        }
                        if(flag==0){
                            Bolseiro bolseiro = null;
                            for(Pessoa pessoaBolseiro: pessoas){
                                if(pessoaBolseiro.getNome().equals(listaPessoas.getSelectedValue().toString())){
                                    bolseiro = (Bolseiro)pessoaBolseiro;
                                }
                            }
                            if (bolseiro == null) {
                                JOptionPane.showMessageDialog(null, "Parece ter havido algum erro... Selecione outro investigador");
                            } else {
                                System.out.println(bolseiro.getClass());
                                if(bolseiro.getClass().getSuperclass().equals(Formando.class)){ //se for formando
                                    Formando formando = (Formando)bolseiro;
                                    //verificar se já tem projeto
                                    if(formando.getProjeto()!=null) { //verificar se tem algum projeto
                                        JOptionPane.showMessageDialog(null, "Já existe projeto associado a formando");
                                    }
                                    else{ //se não tiver projeto
                                        //verificar se tem algum docente
                                        if(projetos.get(listaProjetos.getSelectedIndex()).getDocentes().size()>0 || projetos.get(listaProjetos.getSelectedIndex()).getInvestigadorPrincipal()!=null){
                                            projetos.get(listaProjetos.getSelectedIndex()).getFormandos().add(formando);
                                            formando.setProjeto(projetos.get(listaProjetos.getSelectedIndex()));
                                        }
                                        else{
                                            JOptionPane.showMessageDialog(null, "Não existe nenhum oriente atribuído ao projeto. Selecione um antes de adicionar Formando.");
                                        }
                                    }

                                }
                                else if(bolseiro.getClass().getSuperclass().equals(Doutorado.class)) {
                                    Doutorado doutorado = (Doutorado)bolseiro; //cast
                                    projetos.get(listaProjetos.getSelectedIndex()).getDoutorados().add(doutorado);
                                }



                            }
                        }
                    }


                    //é formando?
                    //se sim tem docente? ; já tem projeto associado?

                } // bolseiros
                else{
                    JOptionPane.showMessageDialog(null,"Opção inválida");
                }
            }

        }
    } //ACABAR

    private class voltarMenuAction implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            localFrame.setVisible(false);
            framePrincipal.setVisible(true);
        }
    }

    private class concluirAction implements ActionListener { //Após os dados terem sido inseridos, se esta opção for acionado, são verificados os dados e se estiverem criados é gerado um novo projeto, que é adicionado ao arrayList
        @Override
        public void actionPerformed (ActionEvent e) {
            concluirAdicionarProjeto();
        }
    }

    private class voltarAction implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            framePrincipal.setVisible(true);
            framePrincipalCriaProjeto.setVisible(false);

        }
    }

    private class fecharAction implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            int opcao = JOptionPane.showConfirmDialog(null,"Tem a certeza que deseja sair?","Sair",JOptionPane.YES_NO_OPTION);
            if(opcao==0){ //opção para sair selecionada
                //DEBUG
                System.out.println("sair\n");
                /*for(Pessoa pessoa: pessoas){
                    System.out.println(pessoa.getNome());
                }*/

                for (Projeto projeto:projetos){
                    System.out.println(projeto.getNome());
                }

                guardar();
                exit(0);
            }
        }
    }

    private boolean verificarData(int[] data){
        if(data[1]>12 || data[0]<0 || data[2] < 2000){
            return false;
        }
        else{
            if(data[1]== 1 || data[1]== 3 || data[1]== 5 || data[1]== 8 || data[1]== 10 || data[1]== 12){
                return !(data[0]>31);
            }
            else{
                return !(data[0]>30);
            }
        }
    }

    private String formatoData(GregorianCalendar data){
        return data.get(Calendar.DAY_OF_MONTH)+"/"+data.get(Calendar.MONTH)+"/"+data.get(Calendar.YEAR);
    }

    private GregorianCalendar formatoGregorianCalendar(String data){
        String[] dataSplit = data.split("-");
        return new GregorianCalendar(Integer.parseInt(dataSplit[2]),Integer.parseInt(dataSplit[1]),Integer.parseInt(dataSplit[0]));
    }

    private DefaultListModel listaProjetos(){
        DefaultListModel list=new DefaultListModel();
        for(Projeto projeto: projetos){
            list.addElement(projeto.getNome());
        }
        return list;
    }

    private DefaultListModel listaBolseiros(){
        DefaultListModel list=new DefaultListModel();
        for(Pessoa pesssoa: pessoas){
            if(pesssoa.getClass().getSuperclass().getSuperclass().equals(Bolseiro.class)) {
                list.addElement(pesssoa.getNome());
            }
        }
        return list;
    }

    private DefaultListModel listaDocentes(){
        DefaultListModel list=new DefaultListModel();
        for(Pessoa pesssoa: pessoas){
            if(pesssoa.getClass().equals(Docente.class)) {
                list.addElement(pesssoa.getNome());
            }
        }
        return list;
    }

    private void concluirAdicionarProjeto(){
        double duracao;
        String nome, acron;

        GregorianCalendar dataFim, dataInicio;

        if (inputNome.getText().isEmpty() || inputAcron.getText().isEmpty() || inputDataInicio.getText().isEmpty() || inputDuracao.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Preencha todos os espaços");
        }
        else{
            nome = inputNome.getText();
            acron = inputAcron.getText();
            String[] dataString = inputDataInicio.getText().split("/");
            int[] dataInt = new int[3];
            int flag = 0;
            while (flag!=3) {
                for (int i=0;i<3;i++){
                    try{
                        dataInt[i] = Integer.parseInt(dataString[i]);
                        flag++;
                    }
                    catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "Formato de data inválido");
                    }
                }
            }

            if(verificarData(dataInt)) {
                dataInicio = new GregorianCalendar(dataInt[2],dataInt[1],dataInt[0]);

                try{
                    duracao = Integer.parseInt(inputDuracao.getText());
                    dataFim = dataInicio;

                    double resto = duracao%1;
                    int inteiro =  (int)(duracao - resto);

                    //CORRIGIR
                    dataFim.add(Calendar.MONTH,inteiro);
                    dataFim.add(Calendar.DAY_OF_MONTH,(int)(resto*30));

                    inputDataFim.setText(formatoData(dataFim));

                    Projeto projeto = new Projeto(nome,acron,dataInicio,dataFim,duracao);
                    projetos.add(projeto);
                    JOptionPane.showMessageDialog(null,"Projeto criado com sucesso");
                    //selectProjectPanel.removeAll();

                    projetosBox.setModel(listaProjetos());

                    framePrincipal.setSize(windowX+150,windowY+200);
                    framePrincipal.setLocationRelativeTo(null);
                    selectProjectPanel.setVisible(true);
                    framePrincipalCriaProjeto.setVisible(false);
                    framePrincipal.setVisible(true);
                }
                catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "valor inválido de duração");
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Formato de data inválido");
            }
        }
    }

    private void lerFicheiro() {
        String line;
        File ficheiroProjetos = new File("projetos.obj");
        File ficheiroPessoas = new File("pessoas.obj");
        File textoPessoas = new File("TextoPessoas.txt");
        File textoProjetos = new File("TextoProjetos.txt");

        if (ficheiroPessoas.exists() && ficheiroPessoas.isFile()) { //abre ficheiro de objetos
            try {
                FileInputStream fo = new FileInputStream(ficheiroPessoas);
                ObjectInputStream ois = new ObjectInputStream(fo);

                ArrayList<Pessoa> pessoaObjeto = (ArrayList<Pessoa>) ois.readObject();
                pessoas = pessoaObjeto;

                ois.close();
            } catch (IOException ex) {
                System.out.println("Erro a ler ficheiro.");
            } catch (ClassNotFoundException ex) {
                System.out.println("Erro a converter objeto.");
            }
        }
        else if (textoPessoas.isFile() && textoPessoas.exists()) { //vai abrir o ficheiro de texto
            try { //guardar pessoas
                FileReader fr = new FileReader(textoPessoas);
                BufferedReader br = new BufferedReader(fr);
                while ((line = br.readLine()) != null) {
                    String[] info = line.split("<");
                    if (info[0].equalsIgnoreCase("Docente") && info.length == 5) {
                        pessoas.add(new Docente(info[1], info[2], Integer.parseInt(info[3]), info[4]));
                    } else if (info[0].equalsIgnoreCase("Doutorado") && info.length == 6) {
                        pessoas.add(new Doutorado(info[1], info[2], Integer.parseInt(info[3]), Integer.parseInt(info[4]), Integer.parseInt(info[5])));
                    } else if (info[0].equalsIgnoreCase("Mestre") && info.length == 6) {
                        pessoas.add(new Mestre(info[1], info[2], Integer.parseInt(info[3]), Integer.parseInt(info[4]), Integer.parseInt(info[5])));
                    } else if (info[0].equalsIgnoreCase("Licenciado") && info.length == 6) {
                        pessoas.add(new Licenciado(info[1], info[2], Integer.parseInt(info[3]), Integer.parseInt(info[4]), Integer.parseInt(info[5])));
                    }
                }
                br.close();
            } catch (IOException ex) {
                System.out.println("Erro a ler ficheiro de texto.");
            }
        }
        else{
            System.out.println("Erro a ler ficheiro de Pessoas");
        }
        if (ficheiroProjetos.exists() && ficheiroProjetos.isFile()) { //abre ficheiro de objetos
            try {
                FileInputStream fo = new FileInputStream(ficheiroProjetos);
                ObjectInputStream ois = new ObjectInputStream(fo);

                ArrayList<Projeto> projetoObjeto = (ArrayList<Projeto>) ois.readObject();
                projetos = projetoObjeto;

                ois.close();
            } catch (IOException ex) {
                System.out.println("Erro a ler ficheiro.");
            } catch (ClassNotFoundException ex) {
                System.out.println("Erro a converter objeto.");
            }
        }
        else if (textoProjetos.isFile() && textoProjetos.exists()) { //vai abrir o ficheiro de texto
            try { //guardar projetos
                FileReader fr = new FileReader(textoProjetos);
                BufferedReader br = new BufferedReader(fr);
                while ((line = br.readLine()) != null) {
                    String[] projeto = line.split("<");
                    if (projeto.length == 8) {

                        Pessoa investigadorPrincipal = null;
                        for (Pessoa pessoa : pessoas) {
                            if (pessoa.getNome().equals(projeto[2]) && pessoa.getClass().toString() != "class Docente") {
                                investigadorPrincipal = pessoa;
                            }
                        }

                        String[] tarefasString = projeto[4].split("/");
                        ArrayList<Tarefa> tarefas = new ArrayList<Tarefa>();

                        for (String tarefa : tarefasString) {
                            String[] tarefaInfo = tarefa.split(":");
                            if (tarefaInfo[0].equalsIgnoreCase("Desenvolvimento") && tarefaInfo.length == 4) {
                                for (Pessoa pessoa : pessoas) {
                                    if (pessoa.getNome().equals(tarefaInfo[3]) && pessoa.getClass().toString().equals("class Docente")) {
                                        tarefas.add(new Desenvolvimento(formatoGregorianCalendar(tarefaInfo[1]), Integer.parseInt(tarefaInfo[2]), pessoa));
                                    }
                                }
                            } else if (tarefaInfo[0].equalsIgnoreCase("Design") && tarefaInfo.length == 4) {
                                for (Pessoa pessoa : pessoas) {
                                    if (pessoa.getNome().equals(tarefaInfo[3]) && pessoa.getClass().toString().equals("class Docente")) {
                                        tarefas.add(new Design(formatoGregorianCalendar(tarefaInfo[1]), Integer.parseInt(tarefaInfo[2]), pessoa));
                                    }
                                }
                            } else if (tarefaInfo[0].equalsIgnoreCase("Documentacao") && tarefaInfo.length == 4) {
                                for (Pessoa pessoa : pessoas) {
                                    if (pessoa.getNome().equals(tarefaInfo[3]) && pessoa.getClass().toString().equals("class Docente")) {
                                        tarefas.add(new Documentacao(formatoGregorianCalendar(tarefaInfo[1]), Integer.parseInt(tarefaInfo[2]), pessoa));
                                    }
                                }
                            }
                        }

                        String[] bolseirosString = projeto[5].split("/");
                        ArrayList<Pessoa> bolseiros = new ArrayList<Pessoa>();
                        for (String bolseiro : bolseirosString) {
                            String[] bolseiroInfo = bolseiro.split(":");
                            for (Pessoa pessoa : pessoas) {
                                if (pessoa.getNome().equals(bolseiroInfo[0]) && pessoa.getClass().toString() != "class Docente") {
                                    bolseiros.add(pessoa);
                                }
                            }
                        }

                        String[] docentesString = projeto[6].split("/");
                        ArrayList<Pessoa> docentes = new ArrayList<Pessoa>();
                        for (String docente : docentesString) {
                            String[] docenteInfo = docente.split(":");
                            for (Pessoa pessoa : pessoas) {
                                if (pessoa.getNome().equals(docenteInfo[0]) && pessoa.getClass().toString() == "class Docente") {
                                    docentes.add(pessoa);
                                }
                            }
                        }
                        //projetos.add(new Projeto(projeto[0], projeto[1],investigadorPrincipal, tarefas, bolseiros, docentes , formatoGregorianCalendar(projeto[6]), formatoGregorianCalendar(projeto[7]), Double.parseDouble(projeto[8])));
                    }
                }
                br.close();
            } catch (IOException ex) {
                System.out.println("Erro a ler ficheiro de texto.");
            }
        }
        else{
            System.out.println("Erro a ler ficheiro de Projetos");
        }
    }

    private void guardar(){
        File ficheiro = new File("projetos.obj");
        File ficheiroPessoas = new File("pessoas.obj");

        try {

            FileOutputStream fos = new FileOutputStream(ficheiro);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(projetos);

            oos.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Erro a criar ficheiro.");
        } catch (IOException ex) {
            System.out.println("Erro a escrever para o ficheiro de Projetos.");
        }

        try {
            FileOutputStream fos = new FileOutputStream(ficheiroPessoas);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(pessoas);

            oos.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Erro a criar ficheiro.");
        } catch (IOException ex) {
            System.out.println("Erro a escrever para o ficheiro de Pessoas.");
        }
    }

    public static void main(String[] args) {
        GereProjetos test = new GereProjetos();
    }
}