package model;

public class Minimax {
    
    static int MAX_DEPTH = 6;

    public static int findBestMove(int[][] board, int numColumns) {
        int bestValue = Integer.MIN_VALUE;
        int bestMove = -1;

        for (int col = 0; col < numColumns; col++) {
            if (isValidMove(board, col)) {
                int[][] newBoard = makeMove(board, col, 2); // Assuming AI is player 2
                int moveValue = minimax(newBoard, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                if (moveValue > bestValue) {
                    bestValue = moveValue;
                    bestMove = col;
                }
            }
        }
        return bestMove;
    }

    private static int minimax(int[][] board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        int boardVal = evaluateBoard(board);
        if (depth == 0 || boardVal == 1000 || boardVal == -1000 || isFull(board)) {
            return boardVal;
        }

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int col = 0; col < board[0].length; col++) {
                if (isValidMove(board, col)) {
                    int[][] newBoard = makeMove(board, col, 2); // Assuming AI is player 2
                    int eval = minimax(newBoard, depth - 1, alpha, beta, false);
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int col = 0; col < board[0].length; col++) {
                if (isValidMove(board, col)) {
                    int[][] newBoard = makeMove(board, col, 1); // Assuming human is player 1
                    int eval = minimax(newBoard, depth - 1, alpha, beta, true);
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return minEval;
        }
    }

    private static boolean isValidMove(int[][] board, int col) {
        return board[0][col] == 0;
    }

    private static int[][] makeMove(int[][] board, int col, int player) {
        int[][] newBoard = copyBoard(board);
        for (int row = board.length - 1; row >= 0; row--) {
            if (newBoard[row][col] == 0) {
                newBoard[row][col] = player;
                break;
            }
        }
        return newBoard;
    }

    private static int[][] copyBoard(int[][] board) {
        int[][] newBoard = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, board[0].length);
        }
        return newBoard;
    }

    private static boolean isFull(int[][] board) {
        for (int col = 0; col < board[0].length; col++) {
            if (board[0][col] == 0) {
                return false;
            }
        }
        return true;
    }

    private static int evaluateBoard(int[][] board) {
        int score = 0;
        
        // Pontuar linhas, colunas e diagonais
        score += evaluateLines(board);
        score += evaluateColumns(board);
        score += evaluateDiagonals(board);
        
        return score;
    }
    
    private static int evaluateLines(int[][] board) {
        int score = 0;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length - 3; col++) {
                int[] window = {board[row][col], board[row][col+1], board[row][col+2], board[row][col+3]};
                score += evaluateWindow(window);
            }
        }
        return score;
    }
    
    private static int evaluateColumns(int[][] board) {
        int score = 0;
        for (int col = 0; col < board[0].length; col++) {
            for (int row = 0; row < board.length - 3; row++) {
                int[] window = {board[row][col], board[row+1][col], board[row+2][col], board[row+3][col]};
                score += evaluateWindow(window);
            }
        }
        return score;
    }
    
    private static int evaluateDiagonals(int[][] board) {
        int score = 0;
        // Diagonais ascendentes (\)
        for (int row = 0; row < board.length - 3; row++) {
            for (int col = 0; col < board[0].length - 3; col++) {
                int[] window = {board[row][col], board[row+1][col+1], board[row+2][col+2], board[row+3][col+3]};
                score += evaluateWindow(window);
            }
        }
        // Diagonais descendentes (/)
        for (int row = 0; row < board.length - 3; row++) {
            for (int col = 3; col < board[0].length; col++) {
                int[] window = {board[row][col], board[row+1][col-1], board[row+2][col-2], board[row+3][col-3]};
                score += evaluateWindow(window);
            }
        }
        return score;
    }
    
    private static int evaluateWindow(int[] window) {
        int score = 0;
        int player = 2; // AI é o jogador 2
        int opponent = 1;
    
        int playerCount = 0;
        int opponentCount = 0;
        int emptyCount = 0;
        
        for (int i : window) {
            if (i == player) playerCount++;
            else if (i == opponent) opponentCount++;
            else emptyCount++;
        }
    
        // Atribuir pontuações para diferentes situações
        if (playerCount == 4) score += 1000;
        else if (playerCount == 3 && emptyCount == 1) score += 5;
        else if (playerCount == 2 && emptyCount == 2) score += 2;
    
        if (opponentCount == 3 && emptyCount == 1) score -= 4;
    
        return score;
    }
}
