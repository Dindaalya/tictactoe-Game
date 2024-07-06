import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame implements ActionListener {
    private static final int SIZE = 3;
    private JButton[][] buttons = new JButton[SIZE][SIZE];
    private int playerXScore = 0;
    private int playerOScore = 0;
    private boolean isXTurn = true;
    private TextField player1 = new TextField();
    private TextField player2 = new TextField();
    private JLabel playerXLabel = new JLabel("Player X: 0");
    private JLabel playerOLabel = new JLabel("Player O: 0");

    // Dino : buat global variable currentPlayerTurn
    private JLabel currentPlayerTurn = new JLabel("Player X's turn");

    public TicTacToe() {
        setTitle("ASD Tic Tac Toe Game");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("TIC TAC TOE GAME", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(SIZE, SIZE, 5, 5));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(this);
                buttons[i][j].setBackground(Color.LIGHT_GRAY); // Set the background color
                boardPanel.add(buttons[i][j]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        // ubah jadi 6 row
        JPanel controlPanel = new JPanel(new GridLayout(9, 1, 5, 5));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // set font size 20
        currentPlayerTurn.setFont(new Font("Arial", Font.PLAIN, 20));
        playerXLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        playerOLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        // tambah currentPlayerTurn ke controlPanel
        controlPanel.add(currentPlayerTurn);
        controlPanel.add(player1);
        controlPanel.add(playerXLabel);
        controlPanel.add(player2);
        controlPanel.add(playerOLabel);


        JButton newGameButton = new JButton("New Game");
        newGameButton.setFont(new Font("Arial", Font.PLAIN, 20));
        newGameButton.addActionListener(e -> resetBoard());
        controlPanel.add(newGameButton);

        JButton resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 20));
        resetButton.addActionListener(e -> resetGame());
        controlPanel.add(resetButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        exitButton.addActionListener(e -> showWinnerTable());
        controlPanel.add(exitButton);

        add(controlPanel, BorderLayout.EAST);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToe frame = new TicTacToe();
            frame.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        if (!clickedButton.getText().equals("")) {
            return;
        }

        if (isXTurn) {
            clickedButton.setText("X");
            clickedButton.setForeground(Color.RED);
            clickedButton.setBackground(Color.PINK); // Set the background color for X
        } else {
            clickedButton.setText("O");
            clickedButton.setForeground(Color.BLUE);
            clickedButton.setBackground(Color.CYAN); // Set the background color for O
        }

        if (checkWinner()) {
            if (isXTurn) {
                playerXScore++;
                playerXLabel.setText("Player X: " + playerXScore);
                JOptionPane.showMessageDialog(this, "Player " + player1.getText()+ " wins!");
            } else {
                playerOScore++;
                playerOLabel.setText("Player O: " + playerOScore);
                JOptionPane.showMessageDialog(this, "Player " + player2.getText() + " wins!");
            }
            resetBoard();
            
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "It's a draw!");
            resetBoard();
        }
        isXTurn = !isXTurn;

        //ubah currentPlayerTurn
        if (isXTurn) {
            currentPlayerTurn.setText("Player X's turn");
        } else {
            currentPlayerTurn.setText("Player O's turn");
        }
    }

    private boolean checkWinner() {
        //horizontal(baris kolom)
        for (int i = 0; i < SIZE; i++) {
            if (buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                buttons[i][1].getText().equals(buttons[i][2].getText()) &&
                !buttons[i][0].getText().equals("")) {
                return true;
            }
        }
        //vertikal (baris kolom)
        for (int i = 0; i < SIZE; i++) {
            if (buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                buttons[1][i].getText().equals(buttons[2][i].getText()) &&
                !buttons[0][i].getText().equals("")) {
                return true;
            }
        }
        //diagonal
        if (buttons[0][0].getText().equals(buttons[1][1].getText()) &&
            buttons[1][1].getText().equals(buttons[2][2].getText()) &&
            !buttons[0][0].getText().equals("")) {
            return true;
        }

        return buttons[0][2].getText().equals(buttons[1][1].getText()) &&
               buttons[1][1].getText().equals(buttons[2][0].getText()) &&
               !buttons[0][2].getText().equals("");
    }
    //seri
    private boolean isBoardFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }
    //menang
    private void resetBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackground(Color.LIGHT_GRAY); // Reset background color
            }
        }
        isXTurn = true;
        
    }

    private void resetGame() {
        resetBoard();
        playerXScore = 0;
        playerOScore = 0;
        playerXLabel.setText("Player X: 0");
        playerOLabel.setText("Player O: 0");
        
        currentPlayerTurn.setText("Player X's turn");
        
    }

    private void showWinnerTable() {
        String[][] data = {
            {"Player X", String.valueOf(playerXScore)},
            {"Player O", String.valueOf(playerOScore)}
        };

        // Sort the data based on scores in descending order
        java.util.Arrays.sort(data, (a, b) -> Integer.compare(Integer.parseInt(b[1]), Integer.parseInt(a[1])));

        String[][] sortedData = {
            {"1", data[0][0], data[0][1]},
            {"2", data[1][0], data[1][1]}
        };

        String[] columnNames = {"Rank", "Player", "Score"};

        JTable table = new JTable(sortedData, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Winners and Scores", JLabel.CENTER), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(this, panel, "Winners and Scores", JOptionPane.PLAIN_MESSAGE);

        System.exit(0);
    }
}
