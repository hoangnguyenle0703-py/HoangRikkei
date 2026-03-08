import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class S16G1_ChatApp {

    static class Message {
        private String sender;
        private String content;
        private LocalDateTime timestamp;

        public Message(String sender, String content) {
            this.sender = sender;
            this.content = content;
            this.timestamp = LocalDateTime.now();
        }

        public String getSender() {
            return sender;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            return String.format("[%s] %s: %s", timestamp.format(formatter), sender, content);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Message> messageList = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        while (true) {
            System.out.println("Nhập tên người gửi (hoặc 'exit' để thoát):");
            String sender = scanner.nextLine().trim();

            if (sender.equalsIgnoreCase("exit")) {
                break;
            }

            System.out.println("Nhập nội dung tin nhắn:");
            String content = scanner.nextLine().trim();
            messageList.add(new Message(sender, content));

            while (true) {
                System.out.println("Nhập 'history' để xem lịch sử, hoặc 'filter' để lọc tin nhắn theo người gửi, hoặc 'date' để lọc theo ngày:");
                String choice = scanner.nextLine().trim();

                if (choice.equalsIgnoreCase("history")) {
                    System.out.println("Lịch sử chat:");
                    for (Message msg : messageList) {
                        System.out.println(msg);
                    }
                    break;

                } else if (choice.equalsIgnoreCase("filter")) {
                    System.out.println("Nhập tên người gửi để lọc:");
                    String filterName = scanner.nextLine().trim();
                    System.out.println("Tin nhắn từ " + filterName + ":");

                    List<Message> filteredMessages = messageList.stream()
                            .filter(msg -> msg.getSender().equalsIgnoreCase(filterName))
                            .collect(Collectors.toList());

                    if (filteredMessages.isEmpty()) {
                        //
                    } else {
                        filteredMessages.forEach(System.out::println);
                    }
                    break;

                } else if (choice.equalsIgnoreCase("date")) {
                    System.out.println("Nhập ngày (dd-MM-yyyy):");
                    String dateInput = scanner.nextLine().trim();

                    try {
                        LocalDate targetDate = LocalDate.parse(dateInput, dateFormatter);
                        System.out.println("Tin nhắn trong ngày " + targetDate.toString() + ":");

                        List<Message> dateFilteredMessages = messageList.stream()
                                .filter(msg -> msg.getTimestamp().toLocalDate().equals(targetDate))
                                .collect(Collectors.toList());

                        dateFilteredMessages.forEach(System.out::println);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Ngày nhập không hợp lệ, vui lòng thử lại.");
                    }
                }
            }
        }
        scanner.close();
    }
}