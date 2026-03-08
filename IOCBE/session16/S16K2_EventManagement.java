import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class S16K2_EventManagement {
    static class Event {
        private String name;
        private LocalDateTime startDate;
        private LocalDateTime endDate;

        public Event(String name, LocalDateTime startDate, LocalDateTime endDate) {
            this.name = name;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getStatus() {
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(startDate)) {
                return "Sắp tới";
            } else if (now.isAfter(endDate)) {
                return "Đã qua";
            } else {
                return "Đang diễn ra";
            }
        }

        @Override
        public String toString() {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            return String.format("Event{name='%s', startDate=%s, endDate=%s} - Trạng thái: %s",
                    name, startDate.format(fmt), endDate.format(fmt), getStatus());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Event> eventList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        while (true) {
            String eventName = "";

            while (true) {
                System.out.print("Nhập tên sự kiện (hoặc 'exit' để thoát):\n");
                eventName = scanner.nextLine().trim();

                if (eventName.equalsIgnoreCase("exit")) {
                    break;
                }

                if (eventName.isEmpty()) {
                    System.out.println("Can not enter empty string"); //
                } else {
                    break;
                }
            }

            if (eventName.equalsIgnoreCase("exit")) {
                break;
            }

            LocalDateTime startDate = null;
            while (startDate == null) {
                System.out.print("Nhập thời gian bắt đầu (dd-MM-yyyy HH:mm):\n"); //
                String startInput = scanner.nextLine().trim();
                try {
                    startDate = LocalDateTime.parse(startInput, formatter);
                } catch (DateTimeParseException e) {
                    System.out.println("Enter not valid date"); //
                }
            }

            LocalDateTime endDate = null;
            while (endDate == null) {
                System.out.print("Nhập thời gian kết thúc (dd-MM-yyyy HH:mm):\n"); //
                String endInput = scanner.nextLine().trim();
                try {
                    endDate = LocalDateTime.parse(endInput, formatter);

                    if (endDate.isBefore(startDate)) {
                        System.out.println("Lỗi: Thời gian kết thúc phải sau thời gian bắt đầu. Vui lòng nhập lại.");
                        endDate = null;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Enter not valid date"); //
                }
            }

            eventList.add(new Event(eventName, startDate, endDate));
        }

        System.out.println("Danh sách sự kiện:"); //
        for (Event event : eventList) {
            System.out.println(event);
        }

        scanner.close();
    }
}