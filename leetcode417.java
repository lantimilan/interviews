// https://leetcode.com/problems/pacific-atlantic-water-flow
class Solution {
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        Queue<Point> pac = new ArrayDeque<>();
        Queue<Point> atl = new ArrayDeque<>();
        Set<Point> pac2 = new HashSet<>();
        Set<Point> atl2 = new HashSet<>();

        Set<Point> seen = new HashSet<>();

        int nrow = heights.length;
        int ncol = heights[0].length;
        for (int row = 0; row < nrow; ++row) {
            Point p = new Point(row, 0);
            pac.add(p);
            seen.add(p);
        }
        for (int col = 0; col < ncol; ++col) {
            Point p = new Point(0, col);
            pac.add(p);
            seen.add(p);
        }

        for (int row = nrow-1; row >= 0; --row) {
            Point p = new Point(row, ncol-1);
            atl.add(p);
            seen.add(p);
        }
        for (int col = ncol-1; col >= 0; --col) {
            Point p = new Point(nrow-1, col);
            atl.add(p);
            seen.add(p);
        }

        while (!pac.isEmpty()) {
            Point p = pac.poll(); pac2.add(p);
            int[][] dir = {
                    {-1, 0}, {0, +1}, {+1, 0}, {0, -1}
            };
            for (int x = 0; x < 4; ++x) {
                int r2 = p.row + dir[x][0];
                int c2 = p.col + dir[x][1];
                if (0 <= r2 && r2 < nrow && 0 <= c2 && c2 < ncol) {
                    Point p2 = new Point(r2, c2);
                    if (!seen.contains(p2)) {
                        pac.add(p2);
                        seen.add(p2);
                    }
                }
            }
        }

        while (!atl.isEmpty()) {
            Point p = atl.poll(); atl2.add(p);
            int[][] dir = {
                    {-1, 0}, {0, +1}, {+1, 0}, {0, -1}
            };
            for (int x = 0; x < 4; ++x) {
                int r2 = p.row + dir[x][0];
                int c2 = p.col + dir[x][1];
                if (0 <= r2 && r2 < nrow && 0 <= c2 && c2 < ncol) {
                    Point p2 = new Point(r2, c2);
                    if (!seen.contains(p2)) {
                        atl.add(p2);
                        seen.add(p2);
                    }
                }
            }
        }

        pac2.retainAll(atl2);
        List<List<Integer>> result = new ArrayList<>();
        for (Point p : pac2) {
            result.add(List.of(p.row, p.col));
        }
        return result;
    }
}

class Point {
    public int row;
    public int col;
    public Point(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public boolean equals(Object o) {
        if (!(o instanceof Point p)) return false;
        return row == p.row && col == p.col;
    }
    public int hashCode() {
        return Objects.hash(row, col);
    }
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
