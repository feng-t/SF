package num130;

public class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        s.solve(new char[][]{
                {'O','x','x'},
                {'x','O','x'},
                {'O','x','x'},
        });
    }

    public void solve(char[][] board) {
        if (board==null||board.length < 3 || board[0].length < 3) {
            return;
        }
        for(int i=0;i<board.length;i++){
            dfs(board,i,0);
            dfs(board,i,board[0].length-1);
        }
        for(int j=0;j<board[0].length;j++){
            dfs(board,0,j);
            dfs(board,board.length-1,j);
        }
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[i].length;j++){
                if(board[i][j]=='O')
                    board[i][j]='X';
                if(board[i][j]=='-')
                    board[i][j]='O';
            }
        }
        System.out.println("dd");
    }

    private void dfs(char[][] board, int i, int j) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != 'O') {
            return;
        }
        board[i][j]='-';
        dfs(board,i-1,j);
        dfs(board,i+1,j);
        dfs(board,i,j-1);
        dfs(board,i,j+1);
    }
}
