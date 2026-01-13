import java.util.Scanner;

public class S4G1_SortAndSearch {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Nhập độ dài mảng: ");
        int n = input.nextInt();
        int[] arr = new int[n+2];
        for(int i = 0; i < n; i++){
            System.out.printf("Phần tử thứ %d: ",i);
            arr[i] = input.nextInt();
        }

        for(int i = 0; i < n-1; i++){
            int max = arr[i];
            int maxid = i;
            for(int j = i+1; j < n; j++){
                if(arr[j] > max){
                    max = arr[j];
                    maxid = j;
                }
            }
            arr[maxid] = arr[i];
            arr[i] = max;
        }
        System.out.println("Mảng sau khi sắp xếp giảm dần:");
        for(int i = 0; i < n; i++)
            System.out.print(arr[i] + " ");
        System.out.print("\n");

        System.out.print("Nhập số cần tìm: ");
        int val = input.nextInt();
        int idx=0;
        for(int i = 0; i < n; i++){
            if(arr[i] == val){
                idx = i;
                break;
            }
        }
        System.out.printf("Tìm kiếm tuyến tính: số %d có tại vị trí %d\n",val,idx);

        idx = 0;
        int l = 0, r = n-1;
        while(l <= r){
            int m = (l + r) >> 1;
            if(arr[m] < val)m = r;
            else if(arr[m] > val)l = m+1;
            else {
                idx = m;
                break;
            }
        }
        System.out.printf("Tìm kiếm nhị phân: số %d có tại vị trí %d",val,idx);
    }
}
