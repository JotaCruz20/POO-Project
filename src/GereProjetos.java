import javax.print.Doc;
import javax.swing.*;
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


public class GereProjetos implements Serializable {

    private ArrayList<Projeto> projetos;
    private ArrayList<Pessoa> pessoas;

    //GUI Menu
    private JFrame framePrincipal, frameAssocia, listaFrame;
    private JPanel selectProjectPanel;
    private JButton nextButton;
    private JList projetosBox,concluidosBox;
    private int windowX, windowY;

    private JComboBox opcaoAssocia;
    JList listaProjetos, listaPessoas;

    //gui CriaProjeto
    private JFrame framePrincipalCriaProjeto;
    private JTextField inputNome, inputAcron, inputDataInicio, inputDuracao, inputDataFim;

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
        optionPanel.setLayout(new GridLayout(6, 1));

        JLabel label = new JLabel("Escolha uma das seguintes opções");
        label.setSize(width, height);

        JButton addProjetoButton = new JButton("Adicionar projeto");
        addProjetoButton.setSize(width, height);
        addProjetoButton.addActionListener(new addProjectAction());

        JButton delProjetoButton = new JButton("Eliminar Projeto");
        delProjetoButton.setSize(width, height);
        delProjetoButton.addActionListener(new delProjectAction());

        JButton addPessoaButton = new JButton("Associar pessoa ao projeto");
        addPessoaButton.setSize(width, height);
        addPessoaButton.addActionListener(new addPessoaAction());

        JButton projetosConcluidosButton = new JButton("Projetos Concluidos");
        projetosConcluidosButton.setSize(width, height);
        projetosConcluidosButton.addActionListener(new projetosConcluidosAction());

        //DefaultListModel listaProjetosCriados = listaProjetos();

        JButton selecionaProjecto = new JButton("Abrir Projeto");
        selecionaProjecto.setSize(width, height);
        selecionaProjecto.addActionListener(new selecionarProjecto());

        optionPanel.add(label);
        optionPanel.add(addProjetoButton);
        optionPanel.add(delProjetoButton);
        optionPanel.add(addPessoaButton);
        optionPanel.add(projetosConcluidosButton);
        optionPanel.add(selecionaProjecto);

        JButton closeButton = new JButton("Fechar");
        closeButton.setSize(width, height);
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

        framePrincipal.add(optionPanel, BorderLayout.NORTH);
        framePrincipal.add(selectProjectPanel, BorderLayout.CENTER);
        framePrincipal.add(nextPanel, BorderLayout.SOUTH);
        //selectProjectPanel.setVisible(tru);
        framePrincipal.setSize(500, 500);
        framePrincipal.setLocationRelativeTo(null);

        framePrincipal.setVisible(true);
    }


    /**
     * Ação do botão para selecionar um projeto
     */
    private class selecionarProjecto implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String value;
            String[] keep_data;
            int[] data = new int[3];
            if (projetosBox != null) {
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
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum Projecto Selecionado", "ERROR", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }



    //MELHORAR DESIGN
    private class addPessoaAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            framePrincipal.setVisible(false);

            windowX = 300;
            windowY = 300;
            int width = windowX - 2 * (windowX / 10);
            int height = width - width / 10;

            frameAssocia = new JFrame();
            frameAssocia.setTitle("Associar pessoa");
            frameAssocia.setSize(windowX, windowY);
            frameAssocia.setLayout(new GridLayout(2, 1));
            //frameAssocia.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JLabel selecionarLabel = new JLabel("Selecione uma pessoa para associar ao projeto");

            JButton atualizarButton = new JButton("Atualizar");
            atualizarButton.setSize(width, height);
            atualizarButton.addActionListener(new atualizarAction());

            JButton associarButton = new JButton("Associar a projeto");
            associarButton.setSize(width, height);
            associarButton.addActionListener(new associarAction());

            JButton voltarButton = new JButton("Voltar");
            voltarButton.setSize(width, height);
            voltarButton.addActionListener(new voltar3Action());

            JPanel listaPanel = new JPanel();
            listaPanel.setLayout(new GridLayout(1, 2));


            listaPessoas = new JList(listaDocentes());
            listaPessoas.setVisibleRowCount(3);
            listaPessoas.setFixedCellWidth(windowX / 2 - 2);
            listaPanel.add(listaPessoas);
            JScrollPane listPessoasScroller = new JScrollPane(listaPessoas);
            listaPanel.add(listPessoasScroller);

            listaProjetos = new JList(listaProjetos());
            listaProjetos.setVisibleRowCount(3);
            listaProjetos.setFixedCellWidth(windowX / 2 - 2);
            listaPanel.add(listaProjetos);
            JScrollPane listProjetosScroller = new JScrollPane(listaProjetos);
            listaPanel.add(listProjetosScroller);

            String[] opcao = {"Investigador Principal", "Docente", "Bolseiro"};
            opcaoAssocia = new JComboBox(opcao);
            opcaoAssocia.setSize(30, 15);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(0, 1));
            JPanel panelButtons = new JPanel();

            panel.add(selecionarLabel);
            panel.add(opcaoAssocia);

            panel.add(listaPanel);

            panelButtons.add(atualizarButton);
            panelButtons.add(associarButton);
            panelButtons.add(voltarButton);

            frameAssocia.add(panel, BorderLayout.NORTH);
            frameAssocia.add(panelButtons, BorderLayout.SOUTH);

            frameAssocia.setVisible(true);
        }
    }



    /**
     * GUI para pedir dados e adicionar projeto novo
     */
    private class addProjectAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nome, acron;
            GregorianCalendar dataInicio, dataFim;
            double duracao;

            framePrincipal.setVisible(false);

            framePrincipalCriaProjeto = new JFrame();
            framePrincipalCriaProjeto.setTitle("Criar projeto novo");
            framePrincipalCriaProjeto.setSize(350, 320);
            framePrincipalCriaProjeto.setLayout(new BorderLayout());
            framePrincipalCriaProjeto.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panelButoes = new JPanel();
            JPanel panelInfo = new JPanel();
            JPanel panelVazio = new JPanel();
            panelInfo.setLayout(new GridLayout(7, 1));

            JButton buttonConcluir = new JButton("Concluir");
            buttonConcluir.addActionListener(new concluirAction());
            JButton voltarButton = new JButton("Voltar");
            voltarButton.addActionListener(new voltar1Action());

            panelButoes.add(voltarButton);
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

            framePrincipalCriaProjeto.add(panelInfo, BorderLayout.NORTH);
            framePrincipalCriaProjeto.add(panelButoes, BorderLayout.SOUTH);

            framePrincipalCriaProjeto.setVisible(true);
        }
    }

    /**
     * GUI para pedir dados e adicionar projeto novo
     */
    private class delProjectAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            projetos.remove(projetosBox.getSelectedIndex());
            projetosBox.setModel(listaProjetos());

        }
    }

    /**
     * Ação do botão para atualizar a lista de pessoas, na frame de adicionar a projeto
     */
    private class atualizarAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (opcaoAssocia.getSelectedIndex() == 0 || opcaoAssocia.getSelectedIndex() == 1) {
                listaPessoas.setModel(listaDocentes());
            } else if (opcaoAssocia.getSelectedIndex() == 2) {
                listaPessoas.setModel(listaBolseiros());
            } else {
                JOptionPane.showMessageDialog(null, "Opção inválida");
            }
        }
    }

    /**
     * Ação do botão para mostrar a lista de projetos concluídos
     */
    private class projetosConcluidosAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            listaFrame = new JFrame();
            JPanel listaPanel = new JPanel(new GridLayout(0, 1));

            listaFrame.setTitle("Projetos Concluidos");
            listaFrame.setSize(windowX, windowY);
            listaFrame.setLayout(new BorderLayout());
            listaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JButton voltarButton = new JButton("Voltar");
            voltarButton.addActionListener(new voltar2Action());

            JButton maisButton = new JButton("Mais Informações sobre o projeto");
            maisButton.addActionListener(new infoAction());

            JLabel titulo = new JLabel("Projetos concluídos");
            listaPanel.add(titulo);
            JLabel espaco = new JLabel(" ");
            listaPanel.add(espaco);

            if(listaConcluidos().size()>0){
                concluidosBox = new JList(listaConcluidos());
                concluidosBox.setVisibleRowCount(3);
                concluidosBox.setFixedCellWidth(windowX - 2 * (windowX / 10));
                listaPanel.add(concluidosBox);

                JScrollPane listProjetosScroller = new JScrollPane(concluidosBox);

                listaPanel.add(listProjetosScroller);

                JPanel botoesPanel = new JPanel();
                botoesPanel.add(voltarButton);
                botoesPanel.add(maisButton);
                listaPanel.add(botoesPanel);

                listaFrame.add(listaPanel);
                listaFrame.add(botoesPanel);

                listaFrame.setVisible(true);

            }
            else{
                JOptionPane.showMessageDialog(null, "Não existem projetos Concluidos");
            }
        }
    } //TESTAR

    /**
     *
     */
    private class associarAction implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            int opcao = 1;
            //testar se tanto projeto como pessoa estão selecionados

            if(listaPessoas.getSelectedIndex()<0 || listaProjetos.getSelectedIndex()<0 ) {
                JOptionPane.showMessageDialog(null, "Tem dados em falta");
                frameAssocia.setVisible(true);
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
                    if(projetos.get(listaProjetos.getSelectedIndex()).getFormandos().size()>0 || projetos.get(listaProjetos.getSelectedIndex()).getDoutorados().size()>0) { //já existe no projeto
                        for (Pessoa pessoa : projetos.get(listaProjetos.getSelectedIndex()).getFormandos()) {
                            if (listaPessoas.getSelectedValue().equals(pessoa.getNome())) {
                                JOptionPane.showMessageDialog(null, "Bolseiro já pertence ao projeto");
                                flag = 1;
                            }
                        }
                        for (Pessoa pessoa : projetos.get(listaProjetos.getSelectedIndex()).getDoutorados()) {
                            if (listaPessoas.getSelectedValue().equals(pessoa.getNome())) {
                                JOptionPane.showMessageDialog(null, "Bolseiro já pertence ao projeto");
                                flag = 1;
                            }
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
                            if (bolseiro.getClass().getSuperclass().equals(Formando.class)) { //se for formando
                                Formando formando = (Formando) bolseiro;
                                if (formando.getProjeto() != null) { //verificar se tem algum projeto
                                    JOptionPane.showMessageDialog(null, "Já existe projeto associado a formando");
                                } else { //se não tiver projeto
                                    //verificar se tem algum docente
                                    if (projetos.get(listaProjetos.getSelectedIndex()).getDocentes().size() > 0 || projetos.get(listaProjetos.getSelectedIndex()).getInvestigadorPrincipal() != null) {
                                        projetos.get(listaProjetos.getSelectedIndex()).getFormandos().add(formando);
                                        formando.setProjeto(projetos.get(listaProjetos.getSelectedIndex()));
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Não existe nenhum oriente atribuído ao projeto. Selecione um antes de adicionar Formando.");
                                    }
                                }

                            } else if (bolseiro.getClass().equals(Doutorado.class)) {
                                Doutorado doutorado = (Doutorado) bolseiro; //cast
                                projetos.get(listaProjetos.getSelectedIndex()).getDoutorados().add(doutorado);
                            }


                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Opção inválida");
                }
            }
        }
    }

    //FUNÇÃO NÃO TESTADA!!!
    /**
     * Ação do botão para apresentar informações sobre Objeto conluido
     */
    private class infoAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String mensagem;
            mensagem = "PROJETO\n"+"Nome: "+ projetos.get(concluidosBox.getSelectedIndex()).getNome()+"\nDuração: " + projetos.get(concluidosBox.getSelectedIndex()).getDuracao();
            JOptionPane.showMessageDialog(null, mensagem);
        }
    }

    /**
     * Ação do botão para adicionar um projeto à ArrayList, chamando a função concluirAdicionarProjeto
     */
    private class concluirAction implements ActionListener { //Após os dados terem sido inseridos, se esta opção for acionado, são verificados os dados e se estiverem criados é gerado um novo projeto, que é adicionado ao arrayList
        @Override
        public void actionPerformed(ActionEvent e) {
            concluirAdicionarProjeto();
        }
    }

    /**
     * Ação do botão para voltar da Frame de criação de um novo projeto para o menu principal
     */
    private class voltar1Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            framePrincipalCriaProjeto.setVisible(false);
            framePrincipal.setVisible(true);
        }
    }

    /**
     * Ação do botão para voltar da janela de associar pessoa a projeto para menu principal
     */
    private class voltar3Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frameAssocia.setVisible(false);
            framePrincipal.setVisible(true);
        }
    }

    /**
     * Ação do botão para voltar da frame de projetos concluídos para o menu principal
     */
    private class voltar2Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            listaFrame.setVisible(false);
            framePrincipal.setVisible(true);
        }
    }

    /**
     * Ação do botão para fechar a janela principal
     */
    private class fecharAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int opcao = JOptionPane.showConfirmDialog(null, "Tem a certeza que deseja sair?", "Sair", JOptionPane.YES_NO_OPTION);
            if (opcao == 0) { //opção para sair selecionada
                guardar();
                exit(0);
            }
        }
    }

    /**
     * Função para verificar a validade de uma data, no formato de Array de inteiros
     *
     * @param data Data em int[]
     * @return Retorna "true" se a data for válida; "falso" se se verificar o contrário
     */
    private boolean verificarData(int[] data) {
        if (data[1] > 12 || data[0] < 0 || data[2] < 2000) {
            return false;
        } else {
            if (data[1] == 1 || data[1] == 3 || data[1] == 5 || data[1] == 8 || data[1] == 10 || data[1] == 12) {
                return !(data[0] > 31);
            } else {
                return !(data[0] > 30);
            }
        }
    }

    /**
     * Conversor de GregorianCalendar para String
     *
     * @param data Data a ser convertida, em formato GregorianCalendar
     * @return Data convertida
     */
    private String formatoData(GregorianCalendar data) {
        return data.get(Calendar.DAY_OF_MONTH) + "/" + data.get(Calendar.MONTH) + "/" + data.get(Calendar.YEAR);
    }

    /**
     * Conversor de String para GregorianCalendar
     *
     * @param data Data a ser convertida, em formato String
     * @return Data convertida
     */
    private GregorianCalendar formatoGregorianCalendar(String data) {
        String[] dataSplit = data.split("-");
        return new GregorianCalendar(Integer.parseInt(dataSplit[2]), Integer.parseInt(dataSplit[1]), Integer.parseInt(dataSplit[0]));
    }

    /**
     * ,Double.parseDouble(inputDuracao.getText()
     *
     * @return lista de Projetos
     */
    private DefaultListModel listaProjetos() {
        DefaultListModel list = new DefaultListModel();
        for (Projeto projeto : projetos) {
            list.addElement(projeto.getNome());
        }
        return list;
    }

    /**
     * Criar lista do tipo DefaultListModel para as JLists
     *
     * @return lista de Bolseiros
     */
    private DefaultListModel listaBolseiros() {
        DefaultListModel list = new DefaultListModel();
        for (Pessoa pesssoa : pessoas) {
            if (pesssoa.getClass().getSuperclass().getSuperclass().equals(Bolseiro.class) || pesssoa.getClass().getSuperclass().equals(Bolseiro.class)) {
                list.addElement(pesssoa.getNome());
            }
        }
        return list;
    }

    /**
     * Criar lista do tipo DefaultListModel para as JLists
     *
     * @return lista de Docentes
     */
    private DefaultListModel listaDocentes() {
        DefaultListModel list = new DefaultListModel();
        for (Pessoa pesssoa : pessoas) {
            if (pesssoa.getClass().equals(Docente.class)) {
                list.addElement(pesssoa.getNome());
            }
        }
        return list;
    }

    /**
     * Criar lista do tipo DefaultListModel para as JLists
     *
     * @return lista de Projetos Concluidos
     */
    private DefaultListModel listaConcluidos() {
        DefaultListModel list = new DefaultListModel();
        for (Projeto projeto: projetos) {
            if (projeto.getConcluir()==1) {
                list.addElement(projeto.getNome());
            }
        }
        return list;
    }

    /**
     * Função adiciona projetos após dados terem sido insiridos na janela
     */
    private void concluirAdicionarProjeto() {
        if (inputNome.getText().isEmpty() || inputAcron.getText().isEmpty() || inputDataInicio.getText().isEmpty() || inputDuracao.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os espaços");
        } else {
            int retorno = criarProjeto(inputNome.getText(), inputAcron.getText(), inputDataInicio.getText().split("/"), Double.parseDouble(inputDuracao.getText()));
            if (retorno == 1) {
                JOptionPane.showMessageDialog(null, "Projeto criado com sucesso");
                //DEVIA DE PÔR O VALOR DA DATA FINAL AUQI!
            } else if (retorno == 2) {
                JOptionPane.showMessageDialog(null, "valor inválido de duração", "ERRO", JOptionPane.ERROR_MESSAGE);
            } else if (retorno == 3) {
                JOptionPane.showMessageDialog(null, "Formato de data inválido", "ERRO", JOptionPane.ERROR_MESSAGE);
            } else if (retorno == 4) {
                JOptionPane.showMessageDialog(null, "Formato de data inválido", "ERRO", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Não foi possível criar projeto", "ERRO", JOptionPane.ERROR_MESSAGE);
            }
        }


        projetosBox.setModel(listaProjetos());

        framePrincipal.setSize(windowX + 150, windowY + 200);
        framePrincipal.setLocationRelativeTo(null);
        selectProjectPanel.setVisible(true);
        framePrincipalCriaProjeto.setVisible(false);
        framePrincipal.setVisible(true);
    }

    /**
     * Função para criar um Projeto
     * @param nome       Nome do projeto
     * @param acron      Acrónimo atribuído ao projeto
     * @param dataString Data de início, no formato String[]
     * @param duracao    duração do projeto
     * @return Retorna 1, se projeto tiver sido criado, e 2, 3 ou 4 se tiver ocorrido um erro
     */
    private int criarProjeto(String nome, String acron, String[] dataString, double duracao) {
        int[] dataInt = new int[3];
        int flag = 0;
        while (flag != 3) {
            for (int i = 0; i < 3; i++) {
                try {
                    dataInt[i] = Integer.parseInt(dataString[i]);
                    flag++;
                } catch (NumberFormatException ex) {
                    return 4;
                }
            }
        }

        if (verificarData(dataInt)) {
            GregorianCalendar dataInicio = new GregorianCalendar(dataInt[2], dataInt[1], dataInt[0]);

            try {
                duracao = Double.parseDouble(inputDuracao.getText());

                GregorianCalendar dataFim = definirDataFim(dataInicio, duracao);
                inputDataFim.setText(formatoData(dataFim));

                Projeto projeto = new Projeto(nome, acron, dataInicio, dataFim, duracao);
                projetos.add(projeto);
                return 1;
            } catch (NumberFormatException ex) {
                return 2;
            }
        } else {
            return 3;
        }
    }

    /**
     * Função para associar um Bolseiro a um projeto
     *
     * @param projetoSelectedIndex  Index do projeto pretendido na JList
     * @param bolseiroSelectedIndex Index da pessoa pretendido na JList
     * @param projetoSelectedValue  projeto pretendido
     * @param bolseiroSelectedValue pessoa pretendida
     * @return Retorna 1 se tiver sido associado o Bolseiro, e 2, 3, 4 ou 5 se tiver ocorrido algum erro
     */
    private int associarBolseiro(int projetoSelectedIndex, int bolseiroSelectedIndex, Projeto projetoSelectedValue, Bolseiro bolseiroSelectedValue) {
        int flag = 0;

        System.out.println(projetos.get(projetoSelectedIndex).getFormandos().size());
        System.out.println(projetos.get(projetoSelectedIndex).getDoutorados().size());

        if (projetos.get(projetoSelectedIndex).getFormandos().size() > 0 || projetos.get(projetoSelectedIndex).getDoutorados().size() > 0) { //já existe no projeto
            for (Pessoa pessoa : projetos.get(projetoSelectedIndex).getFormandos()) {
                if (bolseiroSelectedValue.equals(pessoa.getNome())) {
                    flag = 1;
                    return 4;
                }
            }
            for (Pessoa pessoa : projetos.get(projetoSelectedIndex).getDoutorados()) {
                if (bolseiroSelectedValue.equals(pessoa.getNome())) {
                    flag = 1;
                    return 4;

                }
            }
            if (flag == 0) {
                Bolseiro bolseiro = null;
                for (Pessoa pessoaBolseiro : pessoas) {
                    if (pessoaBolseiro.getNome().equals(bolseiroSelectedValue.toString())) {
                        bolseiro = (Bolseiro) pessoaBolseiro;
                    }
                }
                if (bolseiro == null) { //não encontrou a pessoa na opção das pessoas
                    return 3;
                } else {
                    System.out.println(bolseiro.getClass());
                    if (bolseiro.getClass().getSuperclass().equals(Formando.class)) { //se for formando
                        System.out.println("É bolseiro!");
                        Formando formando = (Formando) bolseiro;
                        //verificar se já tem projeto
                        if (formando.getProjeto() != null) { //verificar se tem algum projeto
                            return 2;

                        } else { //se não tiver projeto
                            //verificar se tem algum docente
                            if (projetos.get(projetoSelectedIndex).getDocentes().size() > 0 || projetos.get(projetoSelectedIndex).getInvestigadorPrincipal() != null) {
                                projetoSelectedValue.getFormandos().add(formando);
                                formando.setProjeto(projetos.get(projetoSelectedIndex));
                                System.out.println("Adicionou!");
                                return 1;
                            } else {
                                return 5;
                            }
                        }

                    } else if (bolseiro.getClass().equals(Doutorado.class)) {
                        Doutorado doutorado = (Doutorado) bolseiro; //cast
                        projetos.get(projetoSelectedIndex).getDoutorados().add(doutorado);
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Função para associar um Docente a um projeto
     * @param projetoSelectedIndex Index do projeto pretendido na JList
     * @param docenteSelectedIndex Index da pessoa pretendido na JList
     * @param projetoSelectedValue projeto pretendido
     * @param docenteSelectedValue pessoa pretendida
     * @return Retorna 1 se tiver sido associado o Bolseiro, e 2, 3 ou 4 se tiver ocorrido algum erro
     */
    private int associarDocente(int projetoSelectedIndex, int docenteSelectedIndex, Projeto projetoSelectedValue, Docente docenteSelectedValue) {
        //verifica se pessoa não está já associada, se estiver, manda-se mensagem a avisar; pergunta-se se se quer tirar
        int flag = 0;
        if (projetos.get(projetoSelectedIndex).getDocentes().size() > 0) {
            for (Pessoa pessoa : projetos.get(projetoSelectedIndex).getDocentes()) {
                if (docenteSelectedValue.equals(pessoa.getNome())) {
                    flag = 1;
                    return 2;
                }
            }
            if (flag == 0) {
                Docente docente = null;

                for (Pessoa pessoaDocente : pessoas) {
                    if (pessoaDocente.getNome().equals(docenteSelectedValue.toString())) {
                        docente = (Docente) pessoaDocente;
                    }
                }
                if (docente == null) {
                    return 3;
                } else {
                    projetos.get(listaProjetos.getSelectedIndex()).getDocentes().add(docente);
                    return 1;
                }
            }
        } else { //se não tiver nenhum seleciona logo!
            Docente docente = null;

            for (Pessoa pessoaDocente : pessoas) {
                if (pessoaDocente.getNome().equals(docenteSelectedValue.toString())) {
                    docente = (Docente) pessoaDocente;
                }
            }
            System.out.println(docente);
            if (docente == null) {
                return 4;
            } else {
                projetos.get(listaProjetos.getSelectedIndex()).getDocentes().add(docente);
                return 1;
            }
        }
        return 0;
    }

    /**
     * Função para associar um Ivestigador a um projeto
     * @param projetoSelectedIndex Index do projeto pretendido na JList
     * @param docenteSelectedIndex Index da pessoa pretendido na JList
     * @param projetoSelectedValue projeto pretendido
     * @param docenteSelectedValue pessoa pretendida
     * @return Retorna 1 se tiver sido associado o Bolseiro, e 2, 3 ou 4 se tiver ocorrido algum erro
     */
    private int associarInvestigador(int projetoSelectedIndex, int docenteSelectedIndex, Projeto projetoSelectedValue, Docente docenteSelectedValue){
        if(projetos.get(projetoSelectedIndex).getInvestigadorPrincipal()!=null){ //já tem investigador!
            return 2;
        }
        Pessoa investigador = null;
        for (Pessoa pessoa : pessoas) {
            if (pessoa.getNome().equals(docenteSelectedValue)) {
                investigador = pessoa;
            }
        }
        if (investigador == null) {
            return 3;
        } else {
            projetos.get(projetoSelectedIndex).setInvestigadorPrincipal(investigador);
                return 1;
        }
    }

    /**
     * Esta função retorna a data após uma certa duração, a partir da data de início
     * @param dataInicio data de início, em GregorianCalendar
     * @param duracao duração pretendida
     * @return Retorna o valor da data de Início somado com a duração
     */
    private GregorianCalendar definirDataFim(GregorianCalendar dataInicio, double duracao){
        GregorianCalendar dataFim = dataInicio;

        int resto = (int)duracao%1;
        int inteiro =  (int)(duracao - resto);

        dataFim.add(Calendar.MONTH,inteiro);
        dataFim.add(Calendar.DAY_OF_MONTH,(resto*30));

        return dataFim;
    }

    /**
     * Carrega os dados dos ficheiros (de texto ou de objetos)
     */
    private void lerFicheiro() {
        File ficheiroPessoas = new File("pessoas.obj");
        File textoPessoas = new File("TextoPessoas.txt");
        File ficheiroProjetos = new File("projetos.obj");
        File textoProjetos = new File("TextoProjetos.txt");

        if(!(lerPessoa(ficheiroPessoas, textoPessoas) && lerProjeto(ficheiroProjetos,textoProjetos))){
            System.out.println("Erro a ler ficheiro");
            exit(0);
        }

    }

    /**
     * Função para ler ficheiro (de objeto ou de texto, se o primeiro não existir) e guardar os dados das pessoas na estrutura
     * @param ficheiroPessoas Ficheiro do tipo .obj com informações sobre as pessoas
     * @param textoPessoas Ficheiro do tipo .txt com informações sobre as pessoas; Este ficheiro apeas é utilizado no caso do primeiro não existir
     * @return Retorna "true" se for possível extrair os dados de algum dos ficheiros. Se não, retorna "false"
     */
    private boolean lerPessoa(File ficheiroPessoas,File textoPessoas ){
        String line;

        if (ficheiroPessoas.exists() && ficheiroPessoas.isFile()) { //abre ficheiro de objetos
            try {
                FileInputStream fo = new FileInputStream(ficheiroPessoas);
                ObjectInputStream ois = new ObjectInputStream(fo);

                ArrayList<Pessoa> pessoaObjeto = (ArrayList<Pessoa>) ois.readObject();
                pessoas = pessoaObjeto;

                ois.close();
            } catch (IOException ex) {
                System.out.println("Erro a ler ficheiro.");
                return false;
            } catch (ClassNotFoundException ex) {
                System.out.println("Erro a converter objeto.");
                return false;
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
                return false;
            }
        }
        else{
            System.out.println("Erro a ler ficheiro de Pessoas");
            return false;
        }
        return true;
    }

    /**
     * Função para ler ficheiro (de objeto ou de texto, se o primeiro não existir) e guardar os dados dos projetos na estrutura
     * @param ficheiroProjetos Ficheiro do tipo .obj com informações sobre os projetos
     * @param textoProjetos Ficheiro do tipo .txt com informações sobre os projetos; Este ficheiro apeas é utilizado no caso do primeiro não existir
     * @return Retorna "true" se for possível extrair os dados de algum dos ficheiros. Se não, retorna "false"
     */
    private boolean lerProjeto(File ficheiroProjetos ,File textoProjetos){
        String line;
        if (ficheiroProjetos.exists() && ficheiroProjetos.isFile()) { //abre ficheiro de objetos
            try {
                FileInputStream fo = new FileInputStream(ficheiroProjetos);
                ObjectInputStream ois = new ObjectInputStream(fo);

                ArrayList<Projeto> projetoObjeto = (ArrayList<Projeto>) ois.readObject();
                projetos = projetoObjeto;

                ois.close();
            } catch (IOException ex) {
                System.out.println("Erro a ler ficheiro.");
                return false;
            } catch (ClassNotFoundException ex) {
                System.out.println("Erro a converter objeto.");
                return false;
            }
        }
        else if (textoProjetos.isFile() && textoProjetos.exists()) { //vai abrir o ficheiro de texto
            try { //guardar projetos
                FileReader fr = new FileReader(textoProjetos);
                BufferedReader br = new BufferedReader(fr);
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    String[] projeto = line.split("<");
                    if (projeto.length == 9) {
                        Pessoa investigadorPrincipal = null;
                        for (Pessoa pessoa : pessoas) {
                            if (pessoa.getNome().equals(projeto[2]) && pessoa.getClass().toString() != "class Docente") {
                                investigadorPrincipal = pessoa;
                            }
                        }

                        String[] tarefasString = projeto[3].split("/");
                        ArrayList<Tarefa> tarefas = new ArrayList<Tarefa>();

                        for (String tarefa : tarefasString) {
                            String[] tarefaInfo = tarefa.split(":");
                            if (tarefaInfo[0].equalsIgnoreCase("Desenvolvimento") && tarefaInfo.length == 4) {
                                for (Pessoa pessoa : pessoas) {
                                    if (pessoa.getNome().equals(tarefaInfo[3]) && pessoa.getClass().equals(Docente.class)) {
                                        System.out.println("oi");
                                        tarefas.add(new Desenvolvimento(formatoGregorianCalendar(tarefaInfo[1]), Integer.parseInt(tarefaInfo[2]), pessoa));
                                    }
                                }
                            } else if (tarefaInfo[0].equalsIgnoreCase("Design") && tarefaInfo.length == 4) {
                                for (Pessoa pessoa : pessoas) {
                                    if (pessoa.getNome().equals(tarefaInfo[3]) && pessoa.getClass().equals(Docente.class)) {
                                        System.out.println("oi");
                                        tarefas.add(new Design(formatoGregorianCalendar(tarefaInfo[1]), Integer.parseInt(tarefaInfo[2]), pessoa));
                                    }
                                }
                            } else if (tarefaInfo[0].equalsIgnoreCase("Documentacao") && tarefaInfo.length == 4) {
                                for (Pessoa pessoa : pessoas) {
                                    if (pessoa.getNome().equals(tarefaInfo[3]) && pessoa.getClass().equals(Docente.class)) {
                                        System.out.println("oi");
                                        tarefas.add(new Documentacao(formatoGregorianCalendar(tarefaInfo[1]), Integer.parseInt(tarefaInfo[2]), pessoa));
                                    }
                                }
                            }
                        }
                        String[] bolseirosString = projeto[4].split("/");
                        ArrayList<Formando> formandos = new ArrayList<Formando>();
                        ArrayList<Doutorado> doutorados = new ArrayList<>();
                        for (String bolseiro : bolseirosString) {
                            String[] bolseiroInfo = bolseiro.split(":");
                            for (Pessoa pessoa : pessoas) {
                                if (pessoa.getNome().equals(bolseiroInfo[0]) && pessoa.getClass().getSuperclass().toString().equals("class Formando")) {
                                    formandos.add((Formando)pessoa);
                                }
                                else if(pessoa.getNome().equals(bolseiroInfo[0]) && pessoa.getClass().toString().equals("class Doutorado")){
                                    doutorados.add((Doutorado)pessoa);
                                }
                            }
                        }

                        String[] docentesString = projeto[5].split("/");
                        ArrayList<Docente> docentes = new ArrayList<Docente>();
                        for (String docente : docentesString) {
                            String[] docenteInfo = docente.split(":");
                            for (Pessoa pessoa : pessoas) {
                                if (pessoa.getNome().equals(docenteInfo[0]) && pessoa.getClass().toString() == "class Docente") {
                                    docentes.add((Docente)pessoa);
                                }
                            }
                        }

                        projetos.add(new Projeto(projeto[0], projeto[1],investigadorPrincipal, tarefas, formandos, doutorados, docentes , formatoGregorianCalendar(projeto[6]), formatoGregorianCalendar(projeto[7]), Double.parseDouble(projeto[8])));
                    }
                }
                br.close();
            } catch (IOException ex) {
                System.out.println("Erro a ler ficheiro de texto.");
                return false;
            }
        }
        return true;
    }

    /**
     * Guarda todos os dados nos ficheiro de Objetos, de modo a poderem ser utilizados na próxima utilização do programa
     */
    private void guardar(){
        guardarPessoas();
        guardarProjetos();
    }

    /**
     * Guarda os objetos Pessoa no ficheiro de Objetos
     */
    private void guardarPessoas(){
        File ficheiroPessoas = new File("pessoas.obj");
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

    /**
     * Guarda os objetos Projeto no ficheiro de Objetos
     */
    private void guardarProjetos(){
        File ficheiro = new File("projetos.obj");

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
    }

    public static void main(String[] args) {
        GereProjetos test = new GereProjetos();
    }
}