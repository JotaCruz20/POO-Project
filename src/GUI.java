import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static javax.swing.SwingConstants.CENTER;

public class GUI extends JFrame {

    private JPanel optionPanel, nextPanel;
    private JButton addProjectButton, selectProjectButton;
    private JLabel label;
    private JButton closeButton, nextButton;
    private JComboBox selectProjectBox;
    private int windowX, windowY;

    public GUI(ArrayList<Projeto> projetos) {
        super();
        windowX = 300;
        windowY = 150;

        int width = windowX-2*(windowX/10);
        int height = width - width/10;

        label = new JLabel("Escolher texto");
        label.setSize(width,height);
        //addProjectButton.setHorizontalAlignment(CENTER);

        addProjectButton = new JButton("Add project");
        addProjectButton.setSize(width,height);
        //addProjectButton.setHorizontalAlignment(CENTER);
        addProjectButton.addActionListener(new addProjectAction());

        selectProjectButton = new JButton("Select a project from list");
        selectProjectButton.setSize(width,height);
        //addProjectButton.setHorizontalAlignment(CENTER);
        selectProjectButton.addActionListener(new selectProjectAction());

        selectProjectBox = new JComboBox(comboBoxList(projetos));
        selectProjectBox.setSize(width,height);
        selectProjectBox.setSelectedIndex(0);
        //addProjectButton.setHorizontalAlignment(CENTER);
        selectProjectBox.addActionListener(new comboBoxAction());
        selectProjectBox.setVisible(false);


        optionPanel = new JPanel();
        optionPanel.setLayout(new GridLayout(3, 1));

        optionPanel.add(label);
        optionPanel.add(addProjectButton);
        optionPanel.add(selectProjectButton);
        optionPanel.add(selectProjectBox);

        nextButton = new JButton("Next");
        nextButton.setSize(30,15);
        //nextButton.setHorizontalAlignment(CENTER);
        nextButton.addActionListener(new nextProjectAction());

        closeButton = new JButton("Close");
        closeButton.setSize(30,15);
        //closeButton.setHorizontalAlignment(CENTER);
        closeButton.addActionListener(new closetAction());

        nextPanel = new JPanel();
        nextPanel.setLayout(new GridLayout(1, 2));

        nextPanel.add(nextButton);
        nextPanel.add(closeButton);

        this.add(nextPanel);
        this.add(optionPanel);

    }

    private class addProjectAction implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {

        }
    }

    private class selectProjectAction implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {

        }
    }

    private class closetAction implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {

        }
    }

    private class nextProjectAction implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {

        }
    }

    private class comboBoxAction implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {

        }
    }

    private String[] comboBoxList(ArrayList<Projeto> projetos){ //cria a lista de projetos para a combo Box
        String[] array = {"Select a project"};
        for (int i=0;i<projetos.size();i++ ){
            array[i+1] =  projetos.toArray()[i].toString();
        }
        return array;
    }





    public static void createFrame(ArrayList<Projeto> projetos){

        GUI frame = new GUI(projetos); //(?)
        frame.setTitle("");
        frame.setSize(frame.windowX,frame.windowY);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);

    }


    public static void main(String[] args) {
        ArrayList<Projeto> projeto = new ArrayList<>();
        createFrame(projeto); //create window
    }
}