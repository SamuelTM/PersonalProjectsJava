package samueltm.personalprojects.math;

import samueltm.personalprojects.miscellaneous.ParallelTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

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

    public static void getPartialResult(Matrix2D a, Matrix2D bTransposed, double[] result,
                                        int startResultElementIndex, int totalResultElements) {
        if (result.length < 1) throw new IllegalArgumentException("Result array cannot be empty");
        if (startResultElementIndex < 0 || startResultElementIndex > result.length)
            throw new IllegalArgumentException("Invalid result element index");
        if (totalResultElements <= 0) throw new IllegalArgumentException("Invalid number of total result elements");

        int elementsLeft = result.length - startResultElementIndex;
        if (elementsLeft < totalResultElements) {
            totalResultElements = elementsLeft;
        }

        int endResultElementIndex = totalResultElements + startResultElementIndex;
        for (int i = startResultElementIndex; i < endResultElementIndex; i++) {
            int rowIndex = i / bTransposed.nRows;
            int colIndex = i % bTransposed.nRows;
            double sum = 0;
            for (int currentDotProduct = 0; currentDotProduct < bTransposed.nColumns; currentDotProduct++) {
                sum += a.getElement(rowIndex, currentDotProduct) * bTransposed.getElement(colIndex, currentDotProduct);
            }
            result[i] = sum;
        }
    }

    public Matrix2D improvedMultiplySingleThread(Matrix2D b) {
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
            List<Runnable> tasks = new ArrayList<>();

            double ratio = 1;
            if (numbers.length >= 10000 && numbers.length < 22500) {
                ratio = 0.09;
            } else if (numbers.length >= 22500) {
                ratio = 0.03;
            }

            int elementsPerThread = (int) Math.ceil(numbers.length * ratio);

            for (int resultElementIndex = 0; resultElementIndex < numbers.length;
                 resultElementIndex += elementsPerThread) {
                int finalElementIndex = resultElementIndex;
                tasks.add(() -> {
                    getPartialResult(this, bT, numbers, finalElementIndex, elementsPerThread);
                });
            }

            ForkJoinPool pool = new ForkJoinPool();
            ParallelTask task = new ParallelTask(tasks);
            pool.invoke(task);

            pool.shutdown();

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

    private void partialIntermediateMatrices(Matrix2D[] quadrantsA, Matrix2D[] quadrantsB, Matrix2D[] result,
                                             int taskIndex) {
        Matrix2D A11 = quadrantsA[0], A12 = quadrantsA[1], A21 = quadrantsA[2], A22 = quadrantsA[3];
        Matrix2D B11 = quadrantsB[0], B12 = quadrantsB[1], B21 = quadrantsB[2], B22 = quadrantsB[3];
        switch (taskIndex) {
            case 0:
                result[0] = (A11.add(A22)).improvedMultiply(B11.add(B22));
                result[1] = (A21.add(A22)).improvedMultiply(B11);
                result[2] = A11.improvedMultiply(B12.subtract(B22));
                break;
            case 1:
                result[3] = A22.improvedMultiply(B21.subtract(B11));
                result[4] = (A11.add(A12)).improvedMultiply(B22);
                break;
            case 2:
                result[6] = (A12.subtract(A22)).improvedMultiply(B21.add(B22));
                result[5] = (A21.subtract(A11)).improvedMultiply(B11.add(B12));
                break;
        }
    }

    private Matrix2D[] getIntermediateMatrices(Matrix2D[] quadrantsA, Matrix2D[] quadrantsB) {
        Matrix2D[] result = new Matrix2D[7];
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<?>> tasks = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            tasks.add(executor.submit(() -> {
                partialIntermediateMatrices(quadrantsA, quadrantsB, result, finalI);
            }));
        }
        try {
            for (Future<?> task : tasks) {
                task.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();

        return result;
    }

    private static Matrix2D[] calculateResultQuadrants(Matrix2D[] intermediateMatrices) {
        Matrix2D P1 = intermediateMatrices[0], P2 = intermediateMatrices[1], P3 = intermediateMatrices[2],
                P4 = intermediateMatrices[3], P5 = intermediateMatrices[4], P6 = intermediateMatrices[5],
                P7 = intermediateMatrices[6];
        return new Matrix2D[]{
                P1.add(P4).subtract(P5).add(P7),
                P3.add(P5),
                P2.add(P4),
                P1.subtract(P2).add(P3).add(P6)
        };
    }

    public Matrix2D coppersmithWinograd(Matrix2D b) {
        if (nColumns == b.nRows) {
            int biggestDimension = Math.max(Math.max(nRows, nColumns), b.nColumns);
            if (biggestDimension % 2 != 0) biggestDimension++;

            Matrix2D A = squarePad(biggestDimension), B = b.squarePad(biggestDimension);
            Matrix2D[] intermediateMatrices = getIntermediateMatrices(A.splitIntoQuadrants(), B.splitIntoQuadrants());
            Matrix2D[] resultQuadrants = calculateResultQuadrants(intermediateMatrices);

            int quadrantSize = A.nRows / 2;
            int paddedResultLength = A.nRows * A.nRows;
            double[] paddedResult = new double[paddedResultLength];

            for (int i = 0; i < paddedResultLength; i++) {
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
            double[] numbers = Arrays.copyOfRange(flatMatrix, rowIndex * nColumns, (rowIndex + 1) * nColumns);
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

    public Matrix2D removeRowAndColumn(int rowIndex, int colIndex) {
        if (rowIndex >= nRows || colIndex >= nColumns)
            throw new IllegalArgumentException("Row or column index out of bounds");

        int newDimensionSize = nRows - 1;
        double[] result = new double[newDimensionSize * newDimensionSize];
        int originalRowStart = rowIndex * nColumns;
        int originalRowEnd = originalRowStart + nColumns;
        int resultIndex = 0;

        for (int i = 0; i < originalRowStart; i += nColumns) {
            System.arraycopy(flatMatrix, i, result, resultIndex, colIndex);
            System.arraycopy(flatMatrix, i + colIndex + 1, result,
                    resultIndex + colIndex, nColumns - colIndex - 1);
            resultIndex += newDimensionSize;
        }

        for (int i = originalRowEnd; i < flatMatrix.length; i += nColumns) {
            System.arraycopy(flatMatrix, i, result, resultIndex, colIndex);
            System.arraycopy(flatMatrix, i + colIndex + 1, result,
                    resultIndex + colIndex, nColumns - colIndex - 1);
            resultIndex += newDimensionSize;
        }

        return new Matrix2D(result, newDimensionSize, newDimensionSize);
    }

    public double determinant() {
        if (!isSquare()) throw new RuntimeException("Only square matrices have determinants");
        if (nRows == 2) {
            return (flatMatrix[0] * flatMatrix[3]) - (flatMatrix[1] * flatMatrix[2]);
        } else if (nRows == 3) {
            // Thanks to u/robinhouston for this one
            double a = flatMatrix[0], b = flatMatrix[1], c = flatMatrix[2], d = flatMatrix[3],
                    e = flatMatrix[4], f = flatMatrix[5], g = flatMatrix[6], h = flatMatrix[7],
                    i = flatMatrix[8];
            double D = d * (h - i), E = e * (i - g), F = f * (g - h), G = g * (e - f), H = h * (f - d);
            return a * (E + F + G) + b * (D + F + H) - c * (F + G + H);
        } else {
            throw new UnsupportedOperationException("Haven't implemented this yet");
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
        double[] flatMatrixCopy = new double[flatMatrix.length];
        System.arraycopy(flatMatrix, 0, flatMatrixCopy, 0, flatMatrix.length);
        return new Matrix2D(flatMatrixCopy, nRows, nColumns);
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