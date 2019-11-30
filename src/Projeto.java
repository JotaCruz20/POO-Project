import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.GregorianCalendar;


public class Projeto{
    private String nome;
    private String acron;
    private GregorianCalendar dataInicio;
    private GregorianCalendar duracao;
    private GregorianCalendar dataFim;
    private int numTarefas;
    private ArrayList<Tarefa> tarefas;
    private Pessoa investigadrPrincipal;
    private ArrayList<Pessoa> docentes;
    private ArrayList<Pessoa> bolseiros;
    //gui main
    private JFrame frame;
    private JButton buttonCriaTarefa,buttonListaTarefa,buttonEliminaTarefa,buttonListaTarefaNConcluidas,buttonListaTarefaInicializadas,buttonListaTarefasNaoIniciadas,buttonCusto,buttonConclusao,buttonAtribuiTarefa;
    private JPanel panel;
    private JButton buttonVoltarAtras;
    //gui cria
    private JFrame frameCria=new JFrame();
    private JPanel panelCria=new JPanel();
    private Choice c=new Choice();
    private JLabel labelInputData,labelInputNome,labelDuracaoEstimada,labelTipo;
    private JTextField textFieldData, textFieldNome,textFieldDuracaoEstimada;
    private JButton buttonCria;
    //gui lista
    private JFrame frameLista=new JFrame();
    private JPanel panelLista=new JPanel();
    private JList listaLista;
    private JScrollPane listListaScroller;
    //gui elimina
    private JFrame frameElimina=new JFrame();
    private JPanel panelElimina=new JPanel();
    private JList listaElimina;
    private JScrollPane listEliminaScroller;
    private JButton buttonElimina;


    public Projeto(){
        //main gui
        frame=new JFrame();
        panel=new JPanel();
        panel.setLayout(new GridLayout(9,1));

        buttonCriaTarefa= new JButton("Criar Tarefa");
        buttonCriaTarefa.addActionListener(new criaTarefa());
        buttonListaTarefa=new JButton("Listar Tarefas");
        buttonListaTarefa.addActionListener(new buttonListaTarefa());
        buttonAtribuiTarefa=new JButton("Atribuir Tarefas");
        //buttonAtribuiTarefa.addActionListener(new ButtonListenner());
        buttonConclusao=new JButton("Terminar Projecto");
        //buttonConclusao.addActionListener(new ButtonListenner());
        buttonCusto=new JButton("Custo do Projecto");
        //buttonCusto.addActionListener(new ButtonListenner());
        buttonEliminaTarefa=new JButton("Eliminar Tarefa");
        buttonEliminaTarefa.addActionListener(new buttonEliminaTarefa());
        buttonListaTarefaInicializadas=new JButton("Listar Tarefas Inicializadas");
        //buttonCusto.addActionListener(new ButtonListenner());
        buttonListaTarefaNConcluidas=new JButton("Listar Tarefas não Concluidas");
        //buttonCusto.addActionListener(new ButtonListenner());
        buttonListaTarefasNaoIniciadas=new JButton("Listar Tarefas não Inicializadas");
        //buttonCusto.addActionListener(new ButtonListenner());

        panel.add(buttonAtribuiTarefa);
        panel.add(buttonConclusao);
        panel.add(buttonCriaTarefa);
        panel.add(buttonCusto);
        panel.add(buttonEliminaTarefa);
        panel.add(buttonListaTarefa);
        panel.add(buttonListaTarefaInicializadas);
        panel.add(buttonListaTarefaNConcluidas);
        panel.add(buttonListaTarefasNaoIniciadas);
        frame.setTitle("Teste");
        frame.setSize(300,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(panel);

    }

    public Pessoa procuraPessoa(String nome){
        int count=0;
        while(count!=docentes.size() && !docentes.get(count).nome.equals(nome)){
            count++;
            if(docentes.get(count).nome.equals(nome)){
                return docentes.get(count);
            }
        }
        count=0;
        while(count!=bolseiros.size() && !bolseiros.get(count).nome.equals(nome)){
            count++;
            if(bolseiros.get(count).nome.equals(nome)){
                return bolseiros.get(count);
            }
        }
        return null;
    }

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

    public DefaultListModel listarTarefa(DefaultListModel list){
        for (int i = 0; i <tarefas.size() ; i++) {
           list.addElement(tarefas.get(i).toString());
        }
        return list;
    }

    private class criaTarefa implements ActionListener {
        public void actionPerformed(ActionEvent e){
            c.add("Design");
            c.add("Desenvolvimento");
            c.add("Documentação");

            panelCria.removeAll();

            panelCria.setLayout(new GridLayout(5,2));
            labelInputData= new JLabel("Data");
            labelInputNome= new JLabel("Nome do Bolseiro/Docente");
            labelDuracaoEstimada= new JLabel("Duração Estimada");
            labelTipo=new JLabel("Tipo de Tarefa");
            textFieldData=new JTextField(15);
            textFieldNome=new JTextField(20);
            textFieldDuracaoEstimada=new JTextField(5);
            buttonCria=new JButton("Criar Tarefa");
            buttonCria.addActionListener(new buttonCriaTarefa());
            buttonVoltarAtras=new JButton("Voltar Atras");
            buttonVoltarAtras.addActionListener(new buttonVoltaAtras());

            panelCria.add(labelInputNome);
            panelCria.add(textFieldNome);
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
            frameCria.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameCria.setTitle("Adiciona Tarefa");
            frameCria.setVisible(true);

        }
    }

    private class buttonCriaTarefa implements ActionListener{
        public void actionPerformed(ActionEvent e){
            GregorianCalendar data;
            Pessoa pessoa=new Docente("Jose","mail",12,"Poop");
            String[] keep_data;
            Tarefa tarefa;
            int dia,mes,ano;
            //pessoa=procuraPessoa(textFieldNome.getText());
            if(pessoa==null){
                JOptionPane.showMessageDialog(null,"Pessoa não existe");
            }
            else {
                keep_data=textFieldData.getText().split("/");
                try {
                    dia = Integer.parseInt(keep_data[0]);
                    mes = Integer.parseInt(keep_data[1]);
                    ano = Integer.parseInt(keep_data[2]);
                    if (verificaData(dia, mes, ano) == 1) {
                        data = new GregorianCalendar(ano, mes, dia);
                        try {
                            if (c.getSelectedItem().equals("Desenvolvimento")) {
                                tarefa = new Desenvolvimento(data, Double.parseDouble(textFieldDuracaoEstimada.getText()), pessoa);
                                //tarefas.add(tarefa);
                            } else if (c.getSelectedItem().equals("Design")) {
                                tarefa = new Design(data, Double.parseDouble(textFieldDuracaoEstimada.getText()), pessoa);
                                //tarefas.add(tarefa);
                            } else {
                                tarefa = new Documentacao(data, Double.parseDouble(textFieldDuracaoEstimada.getText()), pessoa);
                                //tarefas.add(tarefa);
                            }
                            frame.setVisible(true);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Duração Estimada não é um double");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Data não é permitida");
                    }
                }catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Verifique que a Data é válida e está separada por /");
                }
            }
        }
    }

    private class buttonVoltaAtras implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            frameCria.setVisible(false);
            frame.setVisible(true);
        }
    }

    private class buttonListaTarefa implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            DefaultListModel listListaValues=new DefaultListModel();
            panelLista.removeAll();

            //listListaValues=listarTarefa(listListaValues);
            listaLista=new JList(listListaValues);
            listListaScroller = new JScrollPane(listaLista);

            buttonVoltarAtras=new JButton("Voltar Atras");
            buttonVoltarAtras.addActionListener(new buttonVoltaAtras());
            panelLista.add(listaLista);
            panelLista.add(listListaScroller);
            panelLista.add(buttonVoltarAtras);

            frameLista.setTitle("Lista de Tarefas");
            frameLista.setSize(300,150);
            frameLista.setVisible(true);
            frame.setVisible(false);
            frameLista.add(panelLista);
        }
    }

    public class eliminaTarefa implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(listaElimina.getSelectedIndex()==-1){
                JOptionPane.showMessageDialog(null,"Escolha uma opção");
            }
            else{
                tarefas.remove(listaElimina.getSelectedIndex());
            }
        }
    }

    private class buttonEliminaTarefa implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            DefaultListModel listEliminaValues=new DefaultListModel();
            panelElimina.removeAll();

            //listEliminaValues=listarTarefa(listEliminavalues);
            listaElimina=new JList(listEliminaValues);
            listEliminaScroller = new JScrollPane(listaElimina);

            buttonElimina=new JButton("Eliminar Tarefa");
            buttonElimina.addActionListener(new eliminaTarefa());
            buttonVoltarAtras=new JButton("Voltar Atras");
            buttonVoltarAtras.addActionListener(new buttonVoltaAtras());

            panelElimina.add(buttonElimina);
            panelElimina.add(listaElimina);
            panelElimina.add(listEliminaScroller);
            panelElimina.add(buttonVoltarAtras);

            frameElimina.setTitle("Elimina Tarefas");
            frameElimina.setSize(300,150);
            frameElimina.setVisible(true);
            frame.setVisible(false);
            frameElimina.add(panelElimina);
        }
    }

    /*
    public void atribuirTarefa(Tarefa tarefa,Pessoa pessoa){

    }

    public void atualizaConclusao(Tarefa tarefa, double perConclusao){

    }

    public void listaTarefasIniciadas(){

    }

    public void listaTarefasNaoIniciadas(){

    }

    public double custo(){
    }

    public void terminaProjeto(){

    }

    public void atualizaTarefa(Tarefa tarefa){

    }
    */
    public static void main(String[] args) {
        Projeto test=new Projeto();
    }
}