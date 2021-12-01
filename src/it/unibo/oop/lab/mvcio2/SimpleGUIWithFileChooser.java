package it.unibo.oop.lab.mvcio2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import it.unibo.oop.lab.mvcio.Controller;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser { 

    private final JFrame frame = new JFrame("SimpleGUIWithFileChooser");
    /**
     * 
     * @param ctrl
     */
    public SimpleGUIWithFileChooser(final Controller ctrl) {
        final JPanel canvas = new JPanel();
        canvas.setLayout(new BorderLayout());
        final JTextArea textArea = new JTextArea();
        final JButton button = new JButton("Save");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    ctrl.write(textArea.getText());
                } catch (IOException ex) {
                    JOptionPane.showConfirmDialog(frame, ex, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        canvas.add(textArea, BorderLayout.CENTER);
        canvas.add(button, BorderLayout.SOUTH);
        final JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new BorderLayout());
        canvas.add(secondPanel, BorderLayout.NORTH);
        final JTextField secondJText = new JTextField(ctrl.getPath());
        secondJText.setEditable(false);
        secondPanel.add(secondJText, BorderLayout.CENTER);
        final JButton secondButton = new JButton("Browse...");
        secondButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JFileChooser openC = new JFileChooser("Choose where to sabe"); 
                openC.setSelectedFile(ctrl.getCurrentFile());
                final int result = openC.showSaveDialog(frame);
                switch (result) {
                case JFileChooser.APPROVE_OPTION:
                    final File newDest = openC.getSelectedFile();
                    ctrl.setDestination(newDest);
                    secondJText.setText(newDest.getPath());
                    break;
                case JFileChooser.CANCEL_OPTION:
                    break;
                default:
                    JOptionPane.showMessageDialog(frame, result, "Alt", JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }
        });
        secondPanel.add(secondButton, BorderLayout.SOUTH);
        /*
         * Make the frame half the resolution of the screen. This very method is
         * enough for a single screen setup. In case of multiple monitors, the
         * primary is selected.
         * 
         * In order to deal coherently with multimonitor setups, other
         * facilities exist (see the Java documentation about this issue). It is
         * MUCH better than manually specify the size of a window in pixel: it
         * takes into account the current resolution.
         */
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        frame.setSize(sw / 4, sh / 4);
        /*
         * Instead of appearing at (0,0), upper left corner of the screen, this
         * flag makes the OS window manager take care of the default positioning
         * on screen. Results may vary, but it is generally the best choice.
         */
        frame.setLocationByPlatform(true);
        frame.setContentPane(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void display() {
        frame.setVisible(true);
    }
    /**
     * 
     * @param a
     */
    public static void main(final String... a) {
        final SimpleGUIWithFileChooser gui = new SimpleGUIWithFileChooser(new Controller());
        gui.display();
    }
}

