import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {

        int m = classroom.length;
        int n = classroom[0].length();

        int[][] id = new int[m][n];

        for (int[] row : id) {
            Arrays.fill(row, -1);
        }

        int sr = 0, sc = 0;
        int litterCount = 0;

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {

                char ch = classroom[r].charAt(c);

                if (ch == 'S') {
                    sr = r;
                    sc = c;
                }

                if (ch == 'L') {
                    id[r][c] = litterCount++;
                }
            }
        }

        if (litterCount == 0) {
            return 0;
        }

        int totalMasks = 1 << litterCount;
        boolean[][][][] visited =
            new boolean[m][n][energy + 1][totalMasks];

        int fullMask = totalMasks - 1;

        Queue<int[]> queue = new LinkedList<>();

        queue.offer(new int[]{sr, sc, energy, fullMask});

        visited[sr][sc][energy][fullMask] = true;

        int moves = 0;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {

            int size = queue.size();

            while (size-- > 0) {

                int[] cur = queue.poll();

                int r = cur[0];
                int c = cur[1];
                int currEnergy = cur[2];
                int mask = cur[3];

                if (mask == 0) {
                    return moves;
                }

                if (currEnergy == 0) {
                    continue;
                }

                for (int d = 0; d < 4; d++) {

                    int nr = r + dr[d];
                    int nc = c + dc[d];

                    if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                        continue;
                    }

                    if (classroom[nr].charAt(nc) == 'X') {
                        continue;
                    }

                    int newEnergy = currEnergy - 1;

                    if (classroom[nr].charAt(nc) == 'R') {
                        newEnergy = energy;
                    }

                    int newMask = mask;

                    if (classroom[nr].charAt(nc) == 'L') {
                        newMask &= ~(1 << id[nr][nc]);
                    }

                    if (!visited[nr][nc][newEnergy][newMask]) {

                        visited[nr][nc][newEnergy][newMask] = true;

                        queue.offer(
                            new int[]{
                                nr,
                                nc,
                                newEnergy,
                                newMask
                            }
                        );
                    }
                }
            }

            moves++;
        }

        return -1;
    }
}