import java.util.Scanner;

public class S4G2_OESort {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Nhập độ dài mảng: ");
        int n = input.nextInt();
        int[] arr = new int[n+2];
        System.out.print("Nhập phần tử: ");
        for(int i = 0; i < n; i++)
            arr[i] = input.nextInt();

        int eid = 0;
        for(int i = 0; i < n; i++){
            if(arr[i] % 2 == 0){
                int val = arr[i];
                for(int j = i; j >= eid; j--){
                    if(j == eid){
                        arr[j] = val;
                        eid++;
                    }
                    else{
                        arr[j] = arr[j-1];
                    }
                }
            }
        }

        if(n == 0)
            System.err.println("Mảng không có phần tử");
        else {
            for (int i = 0; i < n; i++)
                System.out.print(arr[i] + " ");
        }
    }
}
