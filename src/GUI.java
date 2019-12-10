import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    private JPanel optionPanel, nextPanel;
    private JButton addProjectButton, selectProjectButton;
    private JTextField labelDia,labelMes,labelAno;
    private JButton closeButton, nextButton;
    private JComboBox selectProjectBox;
    private int windowX, windowY;

    public GUI(){
        JFrame frame=new JFrame("Ola");
        labelDia=new JTextField(1);
        labelMes=new JTextField(2);
        labelAno=new JTextField(4);
        optionPanel=new JPanel();
        optionPanel.setLayout(new BorderLayout());
        optionPanel.add(labelDia,BorderLayout.WEST);
        optionPanel.add(labelMes,BorderLayout.CENTER);
        optionPanel.add(labelAno,BorderLayout.EAST);
        frame.setLayout(new BorderLayout());
        frame.add(optionPanel, BorderLayout.NORTH);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        String teste="Data Atual: 12/12/12";
        String[] teste_oi= teste.split(" ");
        System.out.println(teste_oi[2]);
    }
}