import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShapeDrawer extends JPanel {

    private String command = ""; // Store the user's input command

    // Method to draw the shapes based on the 4-bit command input
    private void drawShape(Graphics g, String command) {
        // Ensure the command is exactly 4 characters long
        if (command.length() != 4) {
            g.setColor(Color.BLACK);
            g.drawString("Invalid command. Command must be 4 characters long.", 50, 50);
            return;
        }

        // Split the command into two 2-bit sections
        String corner = command.substring(0, 2); // Corner command
        String shape = command.substring(2, 4);  // Shape command

        // Draw the shape at the specified corner
        drawShapeAtCorner(g, corner, shape);
    }

    // Method to draw the shape at the specified corner
    private void drawShapeAtCorner(Graphics g, String corner, String shape) {
        int x = 0, y = 0;

        // Determine the x, y coordinates based on corner command
        switch (corner) {
            case "00": // Upper left
                x = 50;
                y = 50;
                break;
            case "01": // Upper right
                x = 300;
                y = 50;
                break;
            case "10": // Bottom right
                x = 300;
                y = 300;
                break;
            case "11": // Bottom left
                x = 50;
                y = 300;
                break;
        }

        // Draw the shape based on shape command
        switch (shape) {
            case "00": // Square
                g.setColor(Color.RED);
                g.fillRect(x, y, 50, 50);
                break;
            case "01": // Triangle
                g.setColor(Color.GREEN);
                int[] xPoints = {x, x + 25, x + 50};
                int[] yPoints = {y + 50, y, y + 50};
                g.fillPolygon(xPoints, yPoints, 3);
                break;
            case "10": // Circle
                g.setColor(Color.BLUE);
                g.fillOval(x, y, 50, 50);
                break;
            case "11": // Star
                g.setColor(Color.YELLOW);
                int[] xStar = {x + 25, x + 35, x + 50, x + 40, x + 45, x + 25, x + 5, x + 10, x, x + 15};
                int[] yStar = {y, y + 15, y + 15, y + 25, y + 40, y + 30, y + 40, y + 25, y + 15, y + 15};
                g.fillPolygon(xStar, yStar, 10);
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw shape based on the current 4-bit command
        drawShape(g, command);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Shape Drawer");
        ShapeDrawer panel = new ShapeDrawer();

        // Input field and button for user to enter command
        JPanel inputPanel = new JPanel();
        JTextField commandField = new JTextField(5); // For 4-bit command
        JButton drawButton = new JButton("Draw Shape");

        // Action listener for the button
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the command input from the text field
                String inputCommand = commandField.getText().trim();

                // Validate if the input command is exactly 4 characters long
                if (inputCommand.length() == 4 && inputCommand.matches("[01]{4}")) {
                    panel.command = inputCommand;  // Update the command
                    panel.repaint();  // Redraw the shape
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid command. Please enter a 4-character command containing only 0s and 1s.");
                }
            }
        });

        // Add components to the input panel
        inputPanel.add(new JLabel("Enter Command (4 bits):"));
        inputPanel.add(commandField);
        inputPanel.add(drawButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}