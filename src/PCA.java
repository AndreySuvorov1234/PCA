
/**
 * @author Andrey Suvorov, Kean University
 *
 */

import java.util.Arrays;
import Jama.EigenvalueDecomposition;
import Jama.Matrix;

public class PCA 
{
	public static void main(String[] args)
	{
		double[][] matrix = {	{2.5, 0.5, 2.2, 1.9, 3.1, 2.3, 2,   1,   1.5, 1.1},
								{2.4, 0.7, 2.9, 2.2, 3.0, 2.7, 1.6, 1.1, 1.6, 0.9}	};
		System.out.println("Column 1: "+Arrays.toString(matrix[0]));
		System.out.println("Column 2: "+Arrays.toString(matrix[1]));
		
		//this is already centralized
		double[] meanVector = getDMeanVector(matrix);
		System.out.println("meanVector: "+Arrays.toString(meanVector));
		
		/*
		 * V= eigenVectors
		 * Vr = V(all the rows of first "2" columns - maybe an algorithmic approach
		 * Projection = (Centralzied Data) * (Vr)
		 */
	
		
		double[][] covarianceMatrix = getCovarianceMatrix(matrix, meanVector);
		for(int i =0; i<covarianceMatrix.length; i++)
		{
			System.out.print("scatterMatrix row "+i+": "+Arrays.toString(covarianceMatrix[i])+"\n");
		}
	
		Matrix m = new Matrix(covarianceMatrix);
		EigenvalueDecomposition eig = m.eig();
		double[] realEigValues = eig.getRealEigenvalues();
		m = eig.getV();
		double[][] diagonalEigMatrix = m.getArray();
		
		//new addition to compute projection
		m=eig.getD();
		double[][] eigenVectors =m.getArray();
		double[][] finalMatrix = new double[covarianceMatrix.length][eigenVectors[0].length];
		for(int a=0; a<covarianceMatrix.length; a++)
		{
				for(int i=0; i<covarianceMatrix.length; i++)
				{
					for(int j=0; j<covarianceMatrix[i].length; j++)
					{
						finalMatrix[a][i] += (matrix[a][j]*eigenVectors[j][i]);
					}
				}
		}
		
		
		
		
		
		System.out.print("realEigValues: "+Arrays.toString(realEigValues)+"\n");
		for(int i =0; i<diagonalEigMatrix.length; i++)
		{
			System.out.print("diagonalEigMatrix: "+Arrays.toString(diagonalEigMatrix[i])+"\n");
		}
		for(int i =0; i<diagonalEigMatrix.length; i++)
		{
			System.out.print("EigenVectors: "+Arrays.toString(eigenVectors[i])+"\n");
		}
		for(int i =0; i<diagonalEigMatrix.length; i++)
		{
			System.out.print("finalMatrix: "+Arrays.toString(finalMatrix[i])+"\n");
		}
				
		
	}

	//dDimentionalVector calculation
	public static double[] getDMeanVector(double[][] matrix)
	{
		double[] meanMatrix = new double[matrix.length];
		for(int i=0; i<matrix.length; i++)
		{
			System.out.print("\ni:"+i);
			for(int j=0; j<matrix[i].length; j++)
			{
				System.out.print("j:"+j);
				meanMatrix[i] += (matrix[i][j]/matrix[i].length);
			}
		}

		System.out.print("\n");
		return meanMatrix;
	}
	
	
	
	//Computing the covariance matrix
	public static double[][] getCovarianceMatrix(double[][] matrix, double[] meanVector)
	{
		//matrix multiplication	-> [A]ab * [B]cd	= [X]ad
		//matrix transposition	-> [A]ab 			= [AT]ba
		//Therefore, 			-> [A]ab * [AT]ba 	= [X]aa
		double[][] covarianceMatrix = new double[matrix.length][matrix.length];
		
		//next two steps may be combined for efficiency's sake
		//create "R"
		for(int i=0; i<matrix.length; i++)
		{
			System.out.print("\ni:"+i);
			for(int j=0; j<matrix[i].length; j++)
			{
				System.out.print("j:"+j);
				matrix[i][j] -= meanVector[i];///matrix[0].length;
			}
		}
		//(R transpose)*(R)
		double[][] matrixT = new double[matrix[0].length][matrix.length];
		for(int i=0; i<matrix.length; i++)
		{
			System.out.print("\ni:"+i);
			for(int j=0; j<matrix[i].length; j++)
			{
				System.out.print("j:"+j);
				matrixT[j][i] = matrix[i][j];
			}
		}
		//for every value in the scatter matrix
		for(int a=0; a<covarianceMatrix.length; a++)
		{
			System.out.print("\na:"+a);
				//compute corresponding value of RT*R result
				for(int i=0; i<matrix.length; i++)
				{
					System.out.print("\ni:"+i);
					for(int j=0; j<matrix[i].length; j++)
					{
						System.out.println("j:"+j);
						System.out.println("matrix  "+matrix[i][j]);
						System.out.println("matrixT "+matrixT[j][i]);
						covarianceMatrix[a][i] += (matrix[a][j]*matrixT[j][i]);
					}
				}
		}
		
		for(int i=0; i<matrix.length; i++)
		{
			System.out.print("\ni:"+i);
			for(int j=0; j<covarianceMatrix[i].length; j++)
			{
				System.out.print("j:"+j);
				covarianceMatrix[i][j] /= matrix[0].length-1;
			}
		}
				
		System.out.print("\n");
		return covarianceMatrix;
	}
	
	
}