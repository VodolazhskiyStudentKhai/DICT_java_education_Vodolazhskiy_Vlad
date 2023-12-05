package feature.matrixProcessing;

import java.util.Scanner;

public class MatrixProcessing {
    public static void main(String[] args) {
        boolean close = false;

        while (!close) {
            String menuStr = """
                    [Main menu]
                    1. Add matrices
                    2. Multiply matrix by a constant
                    3. Multiply matrices
                    4. Transpose matrices
                    5. Calculate a determinant
                    6. Inverse matrix
                    0. Exit
                    """;
            System.out.println(menuStr);

            Scanner scanner = new Scanner(System.in);
            int answer = scanner.nextInt();

            switch (answer) {
                case 0:
                    close = true;
                    break;
                case 1:
                    MatrixProcessing.add();
                    break;
                case 2:
                    MatrixProcessing.multiply();
                    break;
                case 3:
                    MatrixProcessing.multiplyM();
                    break;
                case 4:
                    MatrixProcessing.transposeMenu();
                    break;
                case 5:
                    MatrixProcessing.findDeterminant();
                    break;
                case 6:
                    MatrixProcessing.inverseMatrix();
                    break;
            }
        }
    }

    public static void add() {
        Matrix aMatrix = new Matrix("Enter size of matrix A: >");
        Matrix bMatrix = new Matrix("Enter size of matrix B: >");
        Matrix resultMatrix = new Matrix();

        if (aMatrix.getRows() != bMatrix.getRows() || aMatrix.getColumns() != bMatrix.getColumns()) {
            System.out.println("Error: matrices must have equal size");
            return;
        }

        for (int i = 0; i < aMatrix.getRows(); i++) {
            double[] row1 = aMatrix.getRow(i);
            double[] row2 = bMatrix.getRow(i);

            for (int j = 0; j < row1.length; j++) {
                row1[j] += row2[j];
            }
            resultMatrix.addRow(row1);
        }

        System.out.println("Result matrix:\n" + Matrix.toFormatMatrix(resultMatrix));
    }

    public static void multiply() {
        Matrix matrix = new Matrix("Enter size of matrix: >");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter const: > ");
        int constant = scanner.nextInt();

        Matrix resultMatrix = new Matrix();

        for (int i = 0; i < matrix.getRows(); i++) {
            double[] row = matrix.getRow(i);

            for (int j = 0; j < row.length; j++) {
                row[j] *= constant;
            }
            resultMatrix.addRow(row);
        }

        System.out.println("Result matrix:\n" + Matrix.toFormatMatrix(resultMatrix));
    }

    public static void multiplyM() {
        Matrix aMatrix = new Matrix("Enter size of matrix A: >");
        Matrix bMatrix = new Matrix("Enter size of matrix B: >");
        Matrix resultMatrix = new Matrix();

        if (aMatrix.getColumns() != bMatrix.getRows()) {
            System.out.println("Error: columns of the first matrix must be equal to rows of the second matrix");
            return;
        }

        for (int i = 0; i < aMatrix.getRows(); i++) {
            double[] arr = new double[bMatrix.getColumns()];
            resultMatrix.addRow(arr);

            for (int j = 0; j < bMatrix.getColumns(); j++) {
                for (int k = 0; k < bMatrix.getRows(); k++) {
                    resultMatrix.setValue(i, j, resultMatrix.getValue(i, j) + aMatrix.getValue(i, k) * bMatrix.getValue(k, j));
                }
            }
        }

        System.out.println("Result matrix:\n" + Matrix.toFormatMatrix(resultMatrix));
    }

    public static void transposeMenu() {
        String menuStr = "[Transpose menu]\n 1. Main diagonal\n 2. Side diagonal\n 3. Vertical line\n 4. Horizontal line\n";
        System.out.println(menuStr);

        Scanner scanner = new Scanner(System.in);
        int answer = scanner.nextInt();

        switch (answer) {
            case 1:
                MatrixProcessing.transposeMainDiagonal();
                break;
            case 2:
                MatrixProcessing.transposeSideDiagonal();
                break;
            case 3:
                MatrixProcessing.transposeVertical();
                break;
            case 4:
                MatrixProcessing.transposeHorizontal();
                break;
        }
    }

    public static void transposeMainDiagonal() {
        Matrix matrix = new Matrix("Enter size of matrix: >");
        Matrix resultMatrix = new Matrix(matrix.getColumns(), matrix.getRows());

        for (int i = 0; i < resultMatrix.getRows(); i++) {
            for (int j = 0; j < resultMatrix.getColumns(); j++) {
                resultMatrix.setValue(i, j, matrix.getValue(j, i));
            }
        }

        System.out.println("Result matrix:\n " + Matrix.toFormatMatrix(resultMatrix));
    }

    public static void transposeSideDiagonal() {
        Matrix matrix = new Matrix("Enter size of matrix: >");
        Matrix resultMatrix = new Matrix(matrix.getRows(), matrix.getColumns());

        for (int i = 0; i < resultMatrix.getRows(); i++) {
            for (int j = 0; j < resultMatrix.getColumns(); j++) {
                resultMatrix.setValue(i, j, matrix.getValue(matrix.getRows() - 1 - j, matrix.getColumns() - 1 - i));
            }
        }

        System.out.println("Result matrix:\n" + Matrix.toFormatMatrix(resultMatrix));
    }

    public static void transposeVertical() {
        Matrix matrix = new Matrix("Enter size of matrix: >");
        Matrix resultMatrix = new Matrix();

        for (int i = 0; i < matrix.getRows(); i++) {
            double[] row = matrix.getRow(i);
            double[] reversedRow = new double[row.length];

            for (int j = 0; j < row.length; j++) {
                reversedRow[j] = row[row.length - 1 - j];
            }

            resultMatrix.addRow(reversedRow);
        }

        System.out.println("Result matrix:\n" + Matrix.toFormatMatrix(resultMatrix));
    }

    public static void transposeHorizontal() {
        Matrix matrix = new Matrix("Enter size of matrix: >");
        Matrix resultMatrix = new Matrix();

        for (int i = 0; i < matrix.getRows(); i++) {
            double[] row = matrix.getRow(matrix.getRows() - 1 - i);
            resultMatrix.addRow(row);
        }

        System.out.println("Result matrix:\n" + Matrix.toFormatMatrix(resultMatrix));
    }

    public static void findDeterminant() {
        Matrix matrix = new Matrix("Enter size of matrix: >");
        double result = MatrixProcessing.getDeterminant(matrix);
        System.out.println("The result is:\n" + result);
    }

    public static void inverseMatrix() {
        Matrix matrix = new Matrix("Enter size of matrix: >");

        if (MatrixProcessing.getDeterminant(matrix) == 0) {
            System.out.println("This matrix doesn't have an inverse.");
            return;
        }

        Matrix resultMatrix = MatrixProcessing.getSecondMatrix(matrix);
        Matrix tempMatrix = matrix.clone();

        for (int f = 0; f < tempMatrix.getRows(); f++) {
            double scaler1 = 1.0 / tempMatrix.getValue(f, f);

            for (int j = 0; j < tempMatrix.getRows(); j++) {
                tempMatrix.setValue(f, j, tempMatrix.getValue(f, j) * scaler1);
                resultMatrix.setValue(f, j, resultMatrix.getValue(f, j) * scaler1);
            }

            for (double i : MatrixProcessing.getIndices(tempMatrix, f)) {
                double scaler2 = tempMatrix.getValue((int) i, f);

                for (int j = 0; j < tempMatrix.getRows(); j++) {
                    tempMatrix.setValue((int) i, j, tempMatrix.getValue((int) i, j) - scaler2 * tempMatrix.getValue(f, j));
                    resultMatrix.setValue((int) i, j, resultMatrix.getValue((int) i, j) - scaler2 * resultMatrix.getValue(f, j));
                }
            }
        }

        System.out.println("Result matrix:\n" + Matrix.toFormatMatrix(resultMatrix));
    }

    public static double getDeterminant(Matrix matrix) {
        if (matrix.getRows() != matrix.getColumns()) {
            System.out.println("Matrix must be NxN format");
            return 0;
        }

        if (matrix.getRows() == 1) {
            return matrix.getValue(0, 0);
        } else if (matrix.getRows() == 2) {
            return matrix.getValue(0, 0) * matrix.getValue(1, 1) - matrix.getValue(1, 0) * matrix.getValue(0, 1);
        } else {
            double result = 0;
            for (int i = 0; i < matrix.getRows(); i++) {
                double rex = matrix.getValue(0, i) * getDeterminant(MatrixProcessing.getSubMatrix(matrix, i));
                result += (i % 2 == 0) ? rex : -rex;
            }
            return result;
        }
    }

    public static Matrix getSubMatrix(Matrix matrix, int columnIndex) {
        int size = matrix.getRows();
        Matrix subMatrix = new Matrix(size - 1, size - 1);

        for (int i = 1; i < size; i++) {
            int k = 0;
            for (int j = 0; j < size; j++) {
                if (j != columnIndex) {
                    subMatrix.setValue(i - 1, k++, matrix.getValue(i, j));
                }
            }
        }

        return subMatrix;
    }

    public static Matrix getSecondMatrix(Matrix matrix) {
        int size = matrix.getRows();
        Matrix secondMatrix = new Matrix(size, size);

        for (int i = 0; i < size; i++) {
            secondMatrix.setValue(i, i, 1);
        }

        return secondMatrix;
    }

    public static double[] getIndices(Matrix matrix, int excludeIndex) {
        double[] indices = new double[matrix.getRows() - 1];

        for (int i = 0, j = 0; i < matrix.getRows(); i++) {
            if (i != excludeIndex) {
                indices[j++] = i;
            }
        }

        return indices;
    }
}

class Matrix {
    private double[][] matrix;

    public Matrix() {
        this.matrix = new double[0][0];
    }

    public Matrix(int rows, int columns) {
        this.matrix = new double[rows][columns];
    }

    public Matrix(String message) {
        this.matrix = new double[0][0];
        while (this.matrix.length == 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.print(message);
            String[] size = scanner.nextLine().trim().split(" ");

            if (size.length != 2) {
                System.out.println("Enter matrix in format: 'n m'\n where 'n' - column, 'm' - row\n Try again!");
                continue;
            }

            int n = Integer.parseInt(size[0]);
            int m = Integer.parseInt(size[1]);

            this.matrix = new double[n][m];

            for (int i = 0; i < n; i++) {
                System.out.print("Enter matrix: >");
                String[] input = scanner.nextLine().trim().split(" ");

                if (input.length != m) {
                    System.out.println("Row must be " + m + " length");
                    break;
                }

                for (int j = 0; j < m; j++) {
                    this.matrix[i][j] = Integer.parseInt(input[j]);
                }
            }
        }
    }

    public double[][] getMatrix() {
        return this.matrix;
    }
    public void setMatrix(double[][] m) {
        this.matrix = m;
    }
    public Matrix clone() {
        Matrix m = new Matrix();
        m.setMatrix(this.getMatrix());
        return m;
    }
    public int getRows() {
        return this.matrix.length;
    }

    public int getColumns() {
        return (this.matrix.length > 0) ? this.matrix[0].length : 0;
    }

    public double[] getRow(int index) {
        return this.matrix[index];
    }

    public double getValue(int row, int column) {
        return this.matrix[row][column];
    }

    public void addRow(double[] row) {
        double[][] newMatrix = new double[this.matrix.length + 1][this.getColumns()];

        for (int i = 0; i < this.matrix.length; i++) {
            newMatrix[i] = this.matrix[i].clone();
        }

        newMatrix[this.matrix.length] = row.clone();
        this.matrix = newMatrix;
    }

    public void setValue(int row, int column, double value) {
        this.matrix[row][column] = value;
    }

    public static String toFormatMatrix(Matrix matrix) {
        StringBuilder strMatrix = new StringBuilder();

        if (matrix.getRows() > 0 && matrix.getColumns() > 0) {
            for (int i = 0; i < matrix.getRows(); i++) {
                for (int j = 0; j < matrix.getColumns(); j++) {
                    double v = matrix.getValue(i, j);
                    strMatrix.append(formatDouble(String.format("%.2f", v))).append(" ");
                }
                strMatrix.append("\n");
            }
        }

        return strMatrix.toString().trim();
    }
    public static String formatDouble(String d) {
        char last = d.charAt(d.length()-1);
        if(last == '0' || last == '.') {
            return formatDouble(d.substring(0, d.length()-1));
        }
        return d;
    }
}

