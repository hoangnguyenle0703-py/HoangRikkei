import java.util.Scanner;

public class S4K1_Sort {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Nhập độ dài mảng: ");
        int n =  input.nextInt();
        int[] arr;
        arr = new int[n+2];
        for(int i = 1; i <= n; i++){
            System.out.printf("Nhập phần tử thứ %d: ",i);
            arr[i-1] = input.nextInt();
        }

        for(int i = 0; i < n; i++){
            boolean swaped = false;
            for(int j = 0; j < n-1; j++){
                if(arr[j] > arr[j+1]){
                    swaped = true;
                    int tmp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = tmp;
                }
            }
            if(!swaped)break;
        }

        for(int i = 0; i < n; i++)
            System.out.print(arr[i] + " ");
    }
}
