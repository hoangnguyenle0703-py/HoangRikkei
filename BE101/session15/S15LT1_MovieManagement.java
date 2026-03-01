import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class S15LT1_MovieManagement {

    // 1. Lớp Movie chứa dữ liệu phim
    static class Movie {
        private String id; // Sử dụng String để giữ nguyên số 0 ở đầu (ví dụ: "01", "02")
        private String title;
        private String director;
        private LocalDate releaseDate;
        private double rating;

        public Movie(String id, String title, String director, LocalDate releaseDate, double rating) {
            this.id = id;
            this.title = title;
            this.director = director;
            this.releaseDate = releaseDate;
            this.rating = rating;
        }

        // Getters & Setters
        public String getId() { return id; }
        public String getTitle() { return title; }
        public double getRating() { return rating; }

        public void setTitle(String title) { this.title = title; }
        public void setDirector(String director) { this.director = director; }
        public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }
        public void setRating(double rating) { this.rating = rating; }

        @Override
        public String toString() {
            // Định dạng hiển thị ngày theo chuẩn dd-MM-yyyy
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return "Movie{id=" + id + ", title='" + title + "', director='" + director +
                    "', releaseDate=" + releaseDate.format(formatter) + ", rating=" + rating + "}";
        }
    }

    // 2. Lớp MovieManager sử dụng Generic <T>
    // Bound <T extends Movie> giúp Generic nhận diện được các phương thức của đối tượng Movie
    static class MovieManager<T extends Movie> {
        private ArrayList<T> movieList = new ArrayList<>(); //

        public void addMovie(T movie) {
            movieList.add(movie);
            System.out.println("Phim đã được thêm thành công."); //
        }

        public void deleteMovie(String id) {
            // Xóa phim theo id
            boolean removed = movieList.removeIf(m -> m.getId().equals(id));
            if (removed) {
                System.out.println("Phim đã được xóa thành công."); //
            } else {
                System.out.println("Không tìm thấy phim muốn xóa !"); //
            }
        }

        public T findById(String id) {
            for (T m : movieList) {
                if (m.getId().equals(id)) {
                    return m;
                }
            }
            return null;
        }

        public void displayMovies() {
            if (movieList.isEmpty()) {
                System.out.println("Danh sách phim trống.");
                return;
            }
            System.out.println("Danh sách phim:"); //
            for (T m : movieList) {
                System.out.println(m);
            }
        }

        public void searchByName(String name) {
            boolean found = false;
            for (T m : movieList) {
                // Tìm kiếm gần đúng và không phân biệt chữ hoa/thường
                if (m.getTitle().toLowerCase().contains(name.toLowerCase())) {
                    System.out.println("Phim tìm thấy: " + m); //
                    found = true;
                }
            }
            if (!found) {
                System.out.println("Không tìm thấy phim"); //
            }
        }

        public void filterByRating(double minRating) {
            System.out.println("Phim có rating lớn hơn " + minRating + ":"); //
            for (T m : movieList) {
                // Lọc hiển thị phim có rating > 8.0 (hoặc giá trị được nhập)
                if (m.getRating() > minRating) {
                    System.out.println(m);
                }
            }
        }
    }

    // 3. Lớp Main điều khiển (chứa menu và gọi các chức năng)
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MovieManager<Movie> manager = new MovieManager<>();

        // Cấu hình định dạng ngày để khớp với các yêu cầu giao diện
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        while (true) {
            System.out.println("\nChọn chức năng:"); //
            System.out.println("1. Thêm phim");
            System.out.println("2. Xóa phim");
            System.out.println("3. Sửa phim");
            System.out.println("4. Hiển thị phim");
            System.out.println("5. Tìm kiếm phim theo tên");
            System.out.println("6. Lọc phim theo rating");
            System.out.println("7. Thoát");

            // Xử lý người dùng nhập sai menu
            String choiceStr = sc.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số !");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.println("Nhập ID phim:"); //
                    String id = sc.nextLine();
                    System.out.println("Nhập tiêu đề phim:");
                    String title = sc.nextLine();
                    System.out.println("Nhập đạo diễn:");
                    String director = sc.nextLine();

                    LocalDate releaseDate = null;
                    while (releaseDate == null) {
                        System.out.println("Nhập ngày phát hành (dd-MM-yyyy):"); //
                        try {
                            // Chuyển đổi String thành LocalDate và bắt lỗi
                            releaseDate = LocalDate.parse(sc.nextLine(), dateFormatter);
                        } catch (DateTimeParseException e) {
                            System.out.println("Lỗi: Định dạng ngày không hợp lệ. Vui lòng nhập đúng định dạng (VD: 23-02-2025)."); //
                        }
                    }

                    double rating = -1;
                    while (rating < 0) {
                        System.out.println("Nhập rating:"); //
                        try {
                            // Bắt ngoại lệ nhập chữ thay vì số cho rating
                            rating = Double.parseDouble(sc.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Lỗi: Rating phải là một con số. Vui lòng nhập lại!"); //
                        }
                    }

                    manager.addMovie(new Movie(id, title, director, releaseDate, rating));
                }
                case 2 -> {
                    System.out.println("Nhập ID phim cần xóa:"); //
                    String id = sc.nextLine();
                    manager.deleteMovie(id);
                }
                case 3 -> {
                    System.out.println("Mời nhập id phim muốn sửa :"); //
                    String id = sc.nextLine();
                    Movie movieToUpdate = manager.findById(id);

                    if (movieToUpdate == null) {
                        System.out.println("Không tìm thấy phim với id = " + id); //
                    } else {
                        System.out.println("Nhập tiêu đề phim:"); //
                        movieToUpdate.setTitle(sc.nextLine());

                        System.out.println("Nhập đạo diễn:"); //
                        movieToUpdate.setDirector(sc.nextLine());

                        while (true) {
                            System.out.println("Nhập ngày phát hành (dd-MM-yyyy):"); //
                            try {
                                LocalDate releaseDate = LocalDate.parse(sc.nextLine(), dateFormatter);
                                movieToUpdate.setReleaseDate(releaseDate);
                                break;
                            } catch (DateTimeParseException e) {
                                System.out.println("Lỗi: Định dạng ngày không hợp lệ."); //
                            }
                        }

                        while (true) {
                            System.out.println("Nhập rating:"); //
                            try {
                                double rating = Double.parseDouble(sc.nextLine());
                                movieToUpdate.setRating(rating);
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Lỗi: Rating phải là một con số."); //
                            }
                        }
                        System.out.println("Cập nhật phim thành công !"); //
                    }
                }
                case 4 -> manager.displayMovies(); //
                case 5 -> {
                    System.out.println("Nhập tiêu đề để phim tìm kiếm:"); //
                    String name = sc.nextLine();
                    manager.searchByName(name);
                }
                case 6 -> {
                    System.out.println("Nhập rating tối thiểu để lọc:"); //
                    try {
                        double minRating = Double.parseDouble(sc.nextLine());
                        manager.filterByRating(minRating);
                    } catch (NumberFormatException e) {
                        System.out.println("Lỗi: Rating phải là số!");
                    }
                }
                case 7 -> System.exit(0); //
                default -> System.out.println("Chọn sai chức năng! Vui lòng chọn từ 1 đến 7.");
            }
        }
    }
}