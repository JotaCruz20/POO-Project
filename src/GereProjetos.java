import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import static java.lang.System.exit;
import static java.lang.System.setOut;


public class GereProjetos implements Serializable{

    private ArrayList<Projeto> projetos;
    private ArrayList<Pessoa> pessoas;

    //GUI Menu
    private JFrame frame;
    private JPanel selectProjectPanel;
    private JButton nextButton;
    private JList projetosBox;
    private int windowX, windowY;

    //gui CriaProjeto
    private JFrame frameCriaProjeto;
    private JTextField inputNome, inputAcron, inputDataInicio,inputDuracao,inputDataFim;

    public GereProjetos() {
        windowX = 300;
        windowY = 150;

        projetos = new ArrayList<>();
        pessoas = new ArrayList<>();

        lerFicheiro();

        int width = windowX - 2 * (windowX / 10);
        int height = width - width / 10;

        frame = new JFrame();
        frame.setTitle("Menu Inicial");
        frame.setSize(windowX, windowY);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //optionPanel
        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new GridLayout(4, 1));

        JLabel label = new JLabel("Escolha uma das seguintes opções");
        label.setSize(width, height);

        JButton addProjectButton = new JButton("Adicionar projeto");
        addProjectButton.setSize(width, height);
        addProjectButton.addActionListener(new addProjectAction());

        //DefaultListModel listaProjetosCriados = listaProjetos();

        JButton selecionaProjecto=new JButton("Abrir Projeto");
        selecionaProjecto.setSize(width,height);
        selecionaProjecto.addActionListener(new selecionarProjecto());

        optionPanel.add(label);
        optionPanel.add(addProjectButton);
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

        frame.add(optionPanel,BorderLayout.NORTH);
        frame.add(selectProjectPanel,BorderLayout.CENTER);
        frame.add(nextPanel,BorderLayout.SOUTH);
        //selectProjectPanel.setVisible(tru);
        frame.setSize(500,500);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
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
                        frame.setVisible(false);
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

    private class addProjectAction implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            String nome, acron;
            GregorianCalendar dataInicio, dataFim;
            double duracao;

            frame.setVisible(false);

            frameCriaProjeto = new JFrame();
            frameCriaProjeto.setTitle("Criar projeto novo");
            frameCriaProjeto.setSize(350,320);
            frameCriaProjeto.setLayout(new BorderLayout());
            frameCriaProjeto.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

            frameCriaProjeto.add(panelInfo,BorderLayout.NORTH);
            frameCriaProjeto.add(panelButoes,BorderLayout.SOUTH);

            frameCriaProjeto.setVisible(true);
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
            frame.setVisible(true);
            frameCriaProjeto.setVisible(false);

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

                guardarFicheiro();
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

                    frame.setSize(windowX+150,windowY+200);
                    frame.setLocationRelativeTo(null);
                    selectProjectPanel.setVisible(true);
                    frameCriaProjeto.setVisible(false);
                    frame.setVisible(true);
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
        File ficheiroObjetos = new File("dados.obj");
        File textoPessoas = new File("TextoPessoas.txt");
        File textoProjetos = new File("TextoProjetos.txt");

        if (textoPessoas.isFile() && textoPessoas.exists()) { //vai abrir o ficheiro de texto
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
        if (ficheiroObjetos.exists() && ficheiroObjetos.isFile()) { //abre ficheiro de objetos
            try {
                FileInputStream fo = new FileInputStream(ficheiroObjetos);
                ObjectInputStream ois = new ObjectInputStream(fo);

                ArrayList<Projeto> projeto = (ArrayList<Projeto>) ois.readObject();
                projetos = projeto;

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
                        String[] tarefasString = projeto[2].split("/");
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

                        String[] bolseirosString = projeto[3].split("/");
                        ArrayList<Pessoa> bolseiros = new ArrayList<Pessoa>();
                        for (String bolseiro : bolseirosString) {
                            String[] bolseiroInfo = bolseiro.split(":");
                            for (Pessoa pessoa : pessoas) {
                                if (pessoa.getNome().equals(bolseiroInfo[0]) && pessoa.getClass().toString() != "class Docente") {
                                    bolseiros.add(pessoa);
                                }
                            }
                        }

                        String[] docentesString = projeto[4].split("/");
                        ArrayList<Pessoa> docentes = new ArrayList<Pessoa>();
                        for (String docente : docentesString) {
                            String[] docenteInfo = docente.split(":");
                            for (Pessoa pessoa : pessoas) {
                                if (pessoa.getNome().equals(docenteInfo[0]) && pessoa.getClass().toString() == "class Docente") {
                                    docentes.add(pessoa);
                                }
                            }
                        }
                        projetos.add(new Projeto(projeto[0], projeto[1], tarefas, bolseiros, docentes , formatoGregorianCalendar(projeto[5]), formatoGregorianCalendar(projeto[6]), Double.parseDouble(projeto[7])));
                    }
                }
                br.close();
            } catch (IOException ex) {
                System.out.println("Erro a ler ficheiro de texto.");
            }
        }
        else{
            System.out.println("Erro a ler ficheiro.(final)");
        }
    }

    private void guardarFicheiro(){
        File ficheiro = new File("dados.obj");

        try {

            //limpar ficheiro
            FileOutputStream limpar = new FileOutputStream(ficheiro);
            limpar.write(("").getBytes());
            limpar.close();

            FileOutputStream fos = new FileOutputStream(ficheiro);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(projetos);

            oos.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Erro a criar ficheiro.");
        } catch (IOException ex) {
            System.out.println("Erro a escrever para o ficheiro.");
        }
    }

    public static void main(String[] args) {
        GereProjetos test = new GereProjetos();
    }
}