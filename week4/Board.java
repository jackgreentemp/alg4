/*
 * Created by jack.green.temp@gmail.com on 17-12-27 下午3:51
 * Copyright (c) 2017. All rights reserved
 */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

//参考
//https://github.com/joyossarian/CourseraAlgo/blob/master/Board.java#L108:36，这个twin()方法有点问题，违反了immutable原则，使用下一个参考方案的twin方法
//https://github.com/xnutsive/algs4-assignments/blob/master/puzzle/src/Board.java
//待改进：第一个参考实现，在neighbors()方法的实现中太冗余了，应该直接new一个Queue,经过逻辑处理，将该Queue返回即可（Queue实现了Iterable）

public class Board {

    private int[][] initBlocks;
    private int N;
    private int zeroRow;
    private int zeroCol;

    // construct a board from an n-by-n array of blocks(where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        N = blocks.length;

        initBlocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                initBlocks[i][j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }
    }

    public int dimension() {
        return N;
    }

    public int hamming() {
        int number = 0;
        for (int i = 0; i < initBlocks.length; i++) {
            for (int j = 0; j < initBlocks[i].length; j++) {
                if (initBlocks[i][j] != 0 && initBlocks[i][j] != (i * dimension() + j + 1)) {
                    number++;
                }
            }
        }
        return number;
    }

    public int manhattan() {
        int number = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (initBlocks[i][j] != 0) {

                    int dRow = initBlocks[i][j] / N;
                    int dCol = initBlocks[i][j] % N;

                    if (dCol == 0) {
                        dRow = dRow - 1;
                        dCol = N - 1;
                    } else {
                        dCol = dCol - 1;
                    }

                    number += Math.abs(i - dRow) + Math.abs(j - dCol);
                }
            }
        }
        return number;
    }

    public boolean isGoal() {
        return (hamming() == 0);
    }

    public Board twin() {
        /*
        Board tw = new Board(this.initBlocks);
        boolean exchange = false;
        while(!exchange) {
            int i = StdRandom.uniform(N);
            int j = StdRandom.uniform(N);
            int ej = j;
            if (ej == (N - 1)) {
                ej--;
            } else {
                ej++;
            }
            if(tw.initBlocks[i][j] != 0 && tw.initBlocks[i][ej] != 0) {
                int temp = tw.initBlocks[i][j];
                tw.initBlocks[i][j] = tw.initBlocks[i][ej];
                tw.initBlocks[i][ej] = temp;
                exchange = true;
            }
        }
        return tw;
        */
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - 1; j++) {
                if (initBlocks[i][j] != 0 && initBlocks[i][j+1] != 0) {
                    return new Board(switchBlocks(i, j, i, j+1));
                }
            }
        }

        return null;
    }

    private int[][] switchBlocks(int fromI, int fromJ, int toI, int toJ) {
        int[][] blocksCopy = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocksCopy[i][j] = initBlocks[i][j];
            }
        }

        blocksCopy[toI][toJ] = blocksCopy[fromI][fromJ];
        blocksCopy[fromI][fromJ] = initBlocks[toI][toJ];

        return blocksCopy;
    }

    public boolean equals(Object y) {
        if (y == this)
            return true;
        if (y == null)
            return false;
        if (!y.getClass().equals(this.getClass()))
            return false;

        Board yBoard = (Board) y;

        if (yBoard.N != N)
            return false;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (initBlocks[i][j] != yBoard.initBlocks[i][j])
                    return false;
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() {
        Iterable<Board> iter = new NeighborIterable();
        return iter;
    }

    private class NeighborIterable implements Iterable<Board> {
        public Iterator<Board> iterator() {
            NeighborIterator iter = new NeighborIterator();
            return iter;
        }
    }

    private class NeighborIterator implements Iterator<Board> {

        private Queue<Board> neighbors;

        public NeighborIterator() {
            neighbors = new Queue<Board>();
            if (zeroRow > 0) {
                Board upBoard = new Board(initBlocks);
                upBoard.initBlocks[zeroRow][zeroCol] = upBoard.initBlocks[zeroRow - 1][zeroCol];
                upBoard.initBlocks[zeroRow - 1][zeroCol] = 0;
                upBoard.zeroRow--;
                neighbors.enqueue(upBoard);
            }
            if (zeroRow < N - 1) {
                Board downBoard = new Board(initBlocks);
                downBoard.initBlocks[zeroRow][zeroCol] = downBoard.initBlocks[zeroRow + 1][zeroCol];
                downBoard.initBlocks[zeroRow + 1][zeroCol] = 0;
                downBoard.zeroRow++;
                neighbors.enqueue(downBoard);
            }
            if (zeroCol > 0) {
                Board leftBoard = new Board(initBlocks);
                leftBoard.initBlocks[zeroRow][zeroCol] = leftBoard.initBlocks[zeroRow][zeroCol - 1];
                leftBoard.initBlocks[zeroRow][zeroCol - 1] = 0;
                leftBoard.zeroCol--;
                neighbors.enqueue(leftBoard);
            }
            if (zeroCol < N - 1) {
                Board rightBoard = new Board(initBlocks);
                rightBoard.initBlocks[zeroRow][zeroCol] = rightBoard.initBlocks[zeroRow][zeroCol + 1];
                rightBoard.initBlocks[zeroRow][zeroCol + 1] = 0;
                rightBoard.zeroCol++;
                neighbors.enqueue(rightBoard);
            }

        }

        public boolean hasNext() {
            return (neighbors.isEmpty() == false);
        }

        public Board next() {
            return neighbors.dequeue();
        }

        public void remove() {}
    }

    public String toString() {
        String result = new String();
        result += N + "\n";
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                result += initBlocks[i][j];
                if (j != N - 1)
                    result += " ";
            }
            if (i != N - 1)
                result += "\n";
        }
        return result;
    }

    public static void main(String[] args) {
        //StdRandom.setSeed(System.currentTimeMillis());
        int N = 3;
        int[][] blocks = new int[N][N];
        blocks[0][0] = 8;
        blocks[0][1] = 1;
        blocks[0][2] = 3;
        blocks[1][0] = 4;
        blocks[1][1] = 0;
        blocks[1][2] = 2;
        blocks[2][0] = 7;
        blocks[2][1] = 6;
        blocks[2][2] = 5;

        Board b = new Board(blocks);
        StdOut.println(b + "\nhamming : " + b.hamming() + "\nmanhattan: "
                + b.manhattan());

        StdOut.println(b.twin());

        Iterator<Board> it = b.neighbors().iterator();
        while(it.hasNext()) {
            StdOut.println("===================");
            StdOut.println(it.next());
        }
    }


}
