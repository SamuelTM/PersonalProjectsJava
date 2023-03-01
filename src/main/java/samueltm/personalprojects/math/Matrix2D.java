package samueltm.personalprojects.math;

import java.util.Arrays;
import java.util.Objects;

public class Matrix2D {

    private final double[] flatMatrix;
    private final int nRows, nColumns;
    private int[] biggestNumberOfDecimalPlacesInEachColumn;

    public Matrix2D(double[] flatMatrix, int nRows, int nColumns) {
        boolean withinLimits = nRows > 0 && nColumns > 0 && nRows <= flatMatrix.length
                && nColumns <= flatMatrix.length;
        boolean distributable = nRows * nColumns == flatMatrix.length;
        if (withinLimits && distributable) {
            this.flatMatrix = flatMatrix;
            this.nRows = nRows;
            this.nColumns = nColumns;
        } else {
            throw new IllegalArgumentException("Unable to build a matrix with the provided parameters");
        }
    }


    private void getBiggestNumberOfDecimalPlacesInEachColumn() {
        for (int i = 0; i < flatMatrix.length; i++) {
            int currentColumnIndex = i % nColumns;
            int currentBiggest = biggestNumberOfDecimalPlacesInEachColumn[currentColumnIndex];
            int maybeNextBiggest = String.valueOf(flatMatrix[i]).length();
            biggestNumberOfDecimalPlacesInEachColumn[currentColumnIndex] = Math.max(maybeNextBiggest, currentBiggest);
        }
    }

    public double getElement(int rowIndex, int columnIndex) {
        return flatMatrix[rowIndex * nColumns + columnIndex];
    }

    public Matrix2D add(Matrix2D b) {
        if (nRows == b.nRows && nColumns == b.nColumns) {
            final int length = flatMatrix.length;
            final double[] numbers = new double[length];

            System.arraycopy(flatMatrix, 0, numbers, 0, length);

            int i = 0;
            for (double number : b.flatMatrix) {
                numbers[i++] += number;
            }

            return new Matrix2D(numbers, nRows, nColumns);
        } else {
            throw new IllegalArgumentException("Both matrices must have the same size");
        }
    }

    public Matrix2D subtract(Matrix2D b) {
        if (nRows == b.nRows && nColumns == b.nColumns) {
            final int length = flatMatrix.length;
            final double[] numbers = new double[length];

            System.arraycopy(flatMatrix, 0, numbers, 0, length);

            int i = 0;
            for (double number : b.flatMatrix) {
                numbers[i++] -= number;
            }

            return new Matrix2D(numbers, nRows, nColumns);
        } else {
            throw new IllegalArgumentException("Both matrices must have the same size");
        }
    }

    public Matrix2D hadamard(Matrix2D b) {
        if (nRows == b.nRows && nColumns == b.nColumns) {
            final int length = flatMatrix.length;
            final double[] numbers = new double[length];

            System.arraycopy(flatMatrix, 0, numbers, 0, length);

            int i = 0;
            for (double number : b.flatMatrix) {
                numbers[i++] *= number;
            }

            return new Matrix2D(numbers, nRows, nColumns);
        } else {
            throw new IllegalArgumentException("Both matrices must have the same size");
        }
    }

    /**
     * Vanilla matrix multiplication algorithm. Each array is accessed
     * m*n^2 times.
     */
    public Matrix2D classicMultiply(Matrix2D b) {
        if (nColumns == b.nRows) {
            double[] numbers = new double[nRows * b.nColumns];

            for (int i = 0; i < nRows; i++) {
                for (int j = 0; j < b.nColumns; j++) {
                    for (int k = 0; k < nColumns; k++) {
                        numbers[i * b.nColumns + j] += getElement(i, k) * b.getElement(k, j);
                    }
                }
            }
            return new Matrix2D(numbers, nRows, b.nColumns);
        } else {
            throw new IllegalArgumentException("Number of columns of A must be equal to the number of rows of B");
        }
    }

    /**
     * Matrix multiplication algorithm that performs considerably fewer
     * accesses to the resulting matrix than the vanilla method. While the
     * vanilla method accesses the resulting matrix m*n^2 times, this one
     * accesses it only m*n times.
     */

    public Matrix2D improvedMultiply(Matrix2D b) {
        if (nColumns == b.nRows) {
            Matrix2D bT = b.transpose();
            double[] numbers = new double[nRows * b.nColumns];
            for (int i = 0; i < numbers.length; i++) {
                int rowIndex = i / b.nColumns;
                int colIndex = i % b.nColumns;
                double sum = 0;
                for (int currentDotProduct = 0; currentDotProduct < b.nRows; currentDotProduct++) {
                    sum += getElement(rowIndex, currentDotProduct) * bT.getElement(colIndex, currentDotProduct);
                }
                numbers[i] = sum;
            }
            return new Matrix2D(numbers, nRows, b.nColumns);
        } else {
            throw new IllegalArgumentException("Number of columns of A must be equal to the number of rows of B");
        }
    }

    public boolean isSquare() {
        return nRows == nColumns;
    }

    private Matrix2D squarePad(int dimensionSize) {
        if (isSquare() && nRows == dimensionSize) return this;

        if (Math.max(nRows, nColumns) > dimensionSize)
            throw new IllegalArgumentException("Cannot pad a matrix with a size smaller than its biggest dimension");

        double[] numbers = new double[dimensionSize * dimensionSize];
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nColumns; j++) {
                int oldIndex = i * nColumns + j;
                int newIndex = i * dimensionSize + j;
                numbers[newIndex] = flatMatrix[oldIndex];
            }
        }

        return new Matrix2D(numbers, dimensionSize, dimensionSize);
    }

    private Matrix2D[] splitIntoQuadrants() {
        if (isSquare() && nRows % 2 == 0) {
            int quadrantSize = nRows / 2;
            double[][] result = new double[4][quadrantSize * quadrantSize];

            for (int i = 0; i < flatMatrix.length; i++) {
                int rowIndex = i / nColumns;
                int colIndex = i % nColumns;

                int quadrantRowIndex = rowIndex % quadrantSize;
                int quadrantColIndex = colIndex % quadrantSize;

                int quadrantIndex = (rowIndex / quadrantSize) * 2 + (colIndex / quadrantSize);

                result[quadrantIndex][quadrantRowIndex * quadrantSize + quadrantColIndex] = flatMatrix[i];
            }

            return new Matrix2D[]{
                    new Matrix2D(result[0], quadrantSize, quadrantSize),
                    new Matrix2D(result[1], quadrantSize, quadrantSize),
                    new Matrix2D(result[2], quadrantSize, quadrantSize),
                    new Matrix2D(result[3], quadrantSize, quadrantSize)
            };
        } else {
            throw new RuntimeException("Cannot split a matrix of size " + nRows + "x" + nColumns + " into quadrants");
        }
    }

    private Matrix2D[] getIntermediateMatrices(Matrix2D[] submatricesA, Matrix2D[] submatricesB) {
        Matrix2D P1 = (submatricesA[0].add(submatricesA[3])).improvedMultiply(submatricesB[0].add(submatricesB[3]));
        Matrix2D P2 = (submatricesA[2].add(submatricesA[3])).improvedMultiply(submatricesB[0]);
        Matrix2D P3 = submatricesA[0].improvedMultiply(submatricesB[1].subtract(submatricesB[3]));
        Matrix2D P4 = submatricesA[3].improvedMultiply(submatricesB[2].subtract(submatricesB[0]));
        Matrix2D P5 = (submatricesA[0].add(submatricesA[1])).improvedMultiply(submatricesB[3]);
        Matrix2D P6 = (submatricesA[2].subtract(submatricesA[0])).improvedMultiply(submatricesB[0]
                .add(submatricesB[1]));
        Matrix2D P7 = (submatricesA[1].subtract(submatricesA[3])).improvedMultiply(submatricesB[2]
                .add(submatricesB[3]));
        return new Matrix2D[]{P1, P2, P3, P4, P5, P6, P7};
    }

    private Matrix2D[] calculateResultQuadrants(Matrix2D[] intermediateMatrices) {
        return new Matrix2D[]{
                intermediateMatrices[0].add(intermediateMatrices[3]).subtract(intermediateMatrices[4])
                        .add(intermediateMatrices[6]),
                intermediateMatrices[2].add(intermediateMatrices[4]),
                intermediateMatrices[1].add(intermediateMatrices[3]),
                intermediateMatrices[0].subtract(intermediateMatrices[1]).add(intermediateMatrices[2])
                        .add(intermediateMatrices[5])
        };
    }

    public Matrix2D coppersmithWinograd(Matrix2D b) {
        if (nColumns == b.nRows) {
            int biggestDimension = (int) GeneralMath.maxValue(nRows, nColumns, b.nColumns);
            if (biggestDimension % 2 != 0) biggestDimension++;

            Matrix2D A = squarePad(biggestDimension), B = b.squarePad(biggestDimension);
            Matrix2D[] intermediateMatrices = getIntermediateMatrices(A.splitIntoQuadrants(), B.splitIntoQuadrants());
            Matrix2D[] resultQuadrants = calculateResultQuadrants(intermediateMatrices);

            int quadrantSize = A.nRows / 2;
            double[] paddedResult = new double[A.nRows * A.nRows];

            for (int i = 0; i < paddedResult.length; i++) {
                int rowIndex = i / A.nRows, colIndex = i % A.nRows;
                int quadrantRowIndex = rowIndex % quadrantSize, quadrantColIndex = colIndex % quadrantSize;
                int quadrantIndex = (rowIndex / quadrantSize) * 2 + (colIndex / quadrantSize);

                paddedResult[i] = resultQuadrants[quadrantIndex].flatMatrix[quadrantRowIndex * quadrantSize
                        + quadrantColIndex];
            }

            double[] trueResult = new double[nRows * b.nColumns];
            for (int i = 0; i < trueResult.length; i++) {
                int rowIndex = i / b.nColumns, colIndex = i % b.nColumns;
                trueResult[i] = paddedResult[rowIndex * A.nRows + colIndex];
            }

            return new Matrix2D(trueResult, nRows, b.nColumns);
        } else {
            throw new IllegalArgumentException("Number of columns of A must be equal to the number of rows of B");
        }
    }

    public Matrix2D multiply(Matrix2D b) {
        if (isSquare() && Arrays.equals(getShape(), b.getShape()) && nRows >= 1000) {
            return coppersmithWinograd(b);
        } else {
            return improvedMultiply(b);
        }
    }

    public Matrix2D multiply(double scalar) {
        final int length = flatMatrix.length;
        final double[] numbers = new double[length];

        System.arraycopy(flatMatrix, 0, numbers, 0, length);

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] *= scalar;
        }
        return new Matrix2D(numbers, nRows, nColumns);
    }

    public Matrix2D transpose() {
        double[] numbers = new double[flatMatrix.length];
        for (int i = 0; i < numbers.length; i++) {
            int rowIndex = i / nRows;
            int colIndex = i % nRows;
            numbers[i] = getElement(colIndex, rowIndex);
        }

        return new Matrix2D(numbers, nColumns, nRows);
    }

    public static Matrix2D identity(int squareSize) {
        if (squareSize > 0) {
            double[] numbers = new double[squareSize * squareSize];
            for (int i = 0; i < squareSize; i++) {
                numbers[i * squareSize + i] = 1;
            }

            return new Matrix2D(numbers, squareSize, squareSize);
        } else {
            throw new IllegalArgumentException("Unable to create an identity matrix with the provided parameter");
        }
    }

    public Matrix2D zeroPad(int nLayers) {
        if (nLayers > 0) {
            int newAmountRows = nRows + (2 * nLayers);
            int newAmountColumns = nColumns + (2 * nLayers);
            double[] numbers = new double[newAmountRows * newAmountColumns];
            for (int rowIndex = nLayers; rowIndex < nRows + nLayers; rowIndex++) {
                for (int colIndex = nLayers; colIndex < nColumns + nLayers; colIndex++) {
                    int oldRowIndex = rowIndex - nLayers;
                    int oldColIndex = colIndex - nLayers;
                    numbers[rowIndex * newAmountColumns + colIndex] = flatMatrix[oldRowIndex * nColumns + oldColIndex];
                }
            }

            return new Matrix2D(numbers, newAmountRows, newAmountColumns);
        } else {
            throw new IllegalArgumentException("Number of layers must be equal or greater than 1");
        }
    }

    public Matrix2D getRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < nRows) {
            double[] numbers = new double[nColumns];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = getElement(rowIndex, i);
            }
            return new Matrix2D(numbers, 1, nColumns);
        } else {
            throw new IndexOutOfBoundsException("Row index out of bounds");
        }
    }

    public Matrix2D getColumn(int colIndex) {
        if (colIndex >= 0 && colIndex < nColumns) {
            double[] numbers = new double[nRows];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = getElement(i, colIndex);
            }
            return new Matrix2D(numbers, nRows, 1);
        } else {
            throw new IndexOutOfBoundsException("Column index out of bounds");
        }
    }

    public int[] getShape() {
        return new int[]{nRows, nColumns};
    }

    public Matrix2D flatten() {
        return new Matrix2D(flatMatrix, 1, nRows * nColumns);
    }

    public Matrix2D reshape(int nRows, int nColumns) {
        return new Matrix2D(flatMatrix, nRows, nColumns);
    }

    public Matrix2D copy() {
        return new Matrix2D(flatMatrix, nRows, nColumns);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Matrix2D m = (Matrix2D) o;
        return nRows == m.nRows && nColumns == m.nColumns && Arrays.equals(flatMatrix, m.flatMatrix);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(nRows, nColumns);
        result = 31 * result + Arrays.hashCode(flatMatrix);
        return result;
    }

    @Override
    public String toString() {
        if (biggestNumberOfDecimalPlacesInEachColumn == null) {
            biggestNumberOfDecimalPlacesInEachColumn = new int[nColumns];
            getBiggestNumberOfDecimalPlacesInEachColumn();
        }
        final StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < flatMatrix.length; i++) {
            final int currentRowIndex = i / nColumns;
            final int currentColIndex = i % nColumns;
            final boolean mustPrintColumn = nColumns < 20 || (currentColIndex < 3 || currentColIndex >= nColumns - 3);
            final boolean mustPrintRow = nRows < 20 || (currentRowIndex < 3 || currentRowIndex >= nRows - 3);
            if (mustPrintRow) {
                if (currentColIndex == 0) {
                    if (currentRowIndex > 0) {
                        sb.append(" ");
                    }
                    sb.append("[");
                }
                final double number = flatMatrix[i];

                if (mustPrintColumn) {
                    final int numberToFormat = biggestNumberOfDecimalPlacesInEachColumn[currentColIndex] + 1;
                    sb.append(String.format("%" + numberToFormat + "s", number));
                    if (currentColIndex < nColumns - 1) {
                        sb.append(" ");
                    }
                } else if (currentColIndex == 7) {
                    sb.append(" ... ");
                }

                if (currentColIndex == nColumns - 1) {
                    sb.append("]");

                    if (currentRowIndex < nRows - 1) {
                        sb.append("\n");
                    }
                }
            } else if (currentRowIndex == 7 && currentColIndex == 0) {
                sb.append(" ...\n");
            }
        }
        sb.append("]");

        return sb.toString();
    }


}