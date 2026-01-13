import java.util.Scanner;

public class S4K2_SumOSumE {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Nhập số hàng: ");
        int r = input.nextInt();
        System.out.print("Nhập số cột: ");
        int c = input.nextInt();

        int[][] mat = new int[r+2][c+2];
        for(int i = 0; i < r; i++)
            for(int j = 0; j < c; j++){
                System.out.printf("Phần tử [%d][%d]: ",i,j);
                mat[i][j] = input.nextInt();
            }

        int sume = 0,sumo = 0;
        for(int i = 0; i < r; i++)
            for(int j = 0; j < c; j++){
                if(mat[i][j] % 2 == 0)sume += mat[i][j];
                else sumo += mat[i][j];
            }

        System.out.println("Tổng các số chẵn: " + sume);
        System.out.print("Tổng các số lẻ: " + sumo);
    }
}
