\*\*\\\[Bài tập 1 - Khá\\] Tầm quan trọng của Kiến trúc \& Thuộc tính chất lượng\*\*



\_Môn: Kiến trúc Phần mềm\_



\# \*\*Câu 1. Tại sao việc chọn kiến trúc lại là bước sống còn trước khi bắt đầu dự án?\*\*



Trong vai trò tư vấn viên cho startup đang đứng trước lựa chọn giữa ra mắt nhanh và xây dựng nền tảng bền vững, câu trả lời rõ ràng là: \*\*kiến trúc là quyết định khó đảo ngược nhất trong vòng đời phần mềm\*\*, và việc lựa chọn sai có thể gây thiệt hại lớn hơn nhiều so với chi phí thiết kế đúng từ đầu.



\## \*\*1.1. Kiến trúc định hình mọi quyết định kỹ thuật về sau\*\*



Kiến trúc phần mềm là bộ khung quyết định cách các thành phần của hệ thống được tổ chức, tương tác và triển khai. Một khi đã đi vào vận hành, việc thay đổi kiến trúc đòi hỏi viết lại phần lớn codebase - tốn kém về thời gian, tiền bạc và rủi ro lỗi. Martin Fowler gọi đây là \*\*"architectural debt"\*\* - nợ kỹ thuật ở tầng nền tảng, nguy hiểm hơn bất kỳ loại technical debt nào khác.



\## \*\*1.2. Kiến trúc sai → Hệ thống không thể mở rộng\*\*



Startup thường mắc sai lầm kinh điển: xây ứng dụng Monolithic để ra mắt nhanh, nhưng khi lượng người dùng tăng đột biến, hệ thống không thể scale từng phần độc lập. Twitter từng phải viết lại toàn bộ hệ thống từ Ruby on Rails sang Java/Scala vì kiến trúc ban đầu không chịu được tải. Chi phí tái kiến trúc (re-architecture) luôn lớn hơn gấp nhiều lần so với chi phí thiết kế đúng ngay từ đầu.



\## \*\*1.3. Kiến trúc quyết định khả năng bảo trì và phát triển đội nhóm\*\*



Một kiến trúc tốt cho phép nhiều team làm việc song song mà không xung đột - điều cực kỳ quan trọng khi startup bắt đầu tuyển dụng và mở rộng đội ngũ. Kiến trúc Microservices hay Module Monolith cho phép phân tách ownership rõ ràng; trong khi kiến trúc Monolithic truyền thống tạo ra \*\*"big ball of mud"\*\* - mọi thứ phụ thuộc vào nhau, không ai dám sửa vì sợ vỡ chỗ khác.



\## \*\*1.4. Kiến trúc ảnh hưởng trực tiếp đến trải nghiệm người dùng cuối\*\*



Các thuộc tính chất lượng như \*\*Performance\*\*, \*\*Availability\*\*, \*\*Security\*\* đều là kết quả trực tiếp của quyết định kiến trúc. Không có kiến trúc tốt, không thể đạt được các chỉ số SLA mà người dùng và nhà đầu tư mong đợi.



\*\*Kết luận tư vấn:\*\* Startup không nên chọn giữa "ra mắt nhanh" và "kiến trúc tốt" - mà nên chọn kiến trúc phù hợp với giai đoạn hiện tại (ví dụ: Modular Monolith), có lộ trình rõ ràng để migrate sang Microservices khi cần. Đầu tư vào kiến trúc ngay từ đầu là đầu tư vào khả năng sống sót dài hạn của sản phẩm.



\# \*\*Câu 2. Các loại kiến trúc phần mềm phổ biến\*\*



\## \*\*2.1. Kiến trúc Monolithic (Nguyên khối)\*\*



Toàn bộ ứng dụng được đóng gói và triển khai như một đơn vị duy nhất. Tất cả các module (UI, business logic, database access) nằm trong một codebase và chạy trong cùng một tiến trình.



\- \*\*Ưu điểm:\*\* Đơn giản để phát triển, test và deploy ban đầu; không có overhead mạng giữa các module.

\- \*\*Nhược điểm:\*\* Khó scale theo chiều ngang; một lỗi nhỏ có thể làm sập toàn bộ hệ thống; mỗi deploy phải build lại toàn bộ ứng dụng.

\- \*\*Phù hợp:\*\* Startup giai đoạn đầu, ứng dụng quy mô nhỏ đến vừa, team nhỏ.

\- \*\*Ví dụ thực tế:\*\* WordPress, ứng dụng nội bộ doanh nghiệp nhỏ, MVP (Minimum Viable Product).



\## \*\*2.2. Kiến trúc Microservices (Vi dịch vụ)\*\*



Ứng dụng được tách thành nhiều service nhỏ, độc lập, mỗi service chịu trách nhiệm một chức năng kinh doanh cụ thể, giao tiếp qua API (thường là REST hoặc gRPC) hoặc message queue.



\- \*\*Ưu điểm:\*\* Scale từng service độc lập; deploy riêng lẻ không ảnh hưởng toàn hệ thống; phù hợp với nhiều team làm việc song song.

\- \*\*Nhược điểm:\*\* Phức tạp về infrastructure (cần service discovery, load balancer, monitoring); khó debug khi lỗi xảy ra qua nhiều service; latency tăng do giao tiếp mạng.

\- \*\*Phù hợp:\*\* Hệ thống lớn, nhiều team, yêu cầu scale cao, traffic không đồng đều giữa các chức năng.

\- \*\*Ví dụ thực tế:\*\* Netflix, Amazon, Uber, Grab.



\## \*\*2.3. Kiến trúc Layered / N-Tier (Phân tầng)\*\*



Ứng dụng được tổ chức theo các tầng ngang: Presentation Layer (UI), Business Logic Layer, Data Access Layer. Mỗi tầng chỉ giao tiếp với tầng liền kề.



\- \*\*Ưu điểm:\*\* Tách biệt rõ ràng các mối quan tâm (Separation of Concerns); dễ bảo trì và thay thế từng tầng; cấu trúc quen thuộc, dễ onboard người mới.

\- \*\*Nhược điểm:\*\* Có thể dẫn đến "Anemic Domain Model"; mỗi request phải đi qua tất cả các tầng dù chỉ cần một tầng; khó tối ưu performance.

\- \*\*Phù hợp:\*\* Ứng dụng doanh nghiệp truyền thống, hệ thống CRM/ERP, web application phổ thông.

\- \*\*Ví dụ thực tế:\*\* Hầu hết ứng dụng web MVC (Spring MVC, ASP.NET MVC), hệ thống quản lý nội bộ.



\## \*\*2.4. Kiến trúc Event-Driven (Hướng sự kiện)\*\*



Các thành phần giao tiếp với nhau thông qua việc phát ra và lắng nghe sự kiện (events), thay vì gọi trực tiếp. Thường sử dụng message broker như Kafka, RabbitMQ.



\- \*\*Ưu điểm:\*\* Decoupling cao - producer và consumer không biết về nhau; xử lý bất đồng bộ tốt; dễ mở rộng số lượng consumer.

\- \*\*Nhược điểm:\*\* Khó trace và debug luồng xử lý; eventual consistency có thể phức tạp; cần infrastructure bổ sung cho message broker.

\- \*\*Phù hợp:\*\* Hệ thống cần xử lý khối lượng lớn sự kiện, IoT, real-time analytics, hệ thống thông báo.

\- \*\*Ví dụ thực tế:\*\* Hệ thống giao dịch ngân hàng, pipeline xử lý dữ liệu, hệ thống notification.



\## \*\*2.5. Kiến trúc Serverless (Không máy chủ)\*\*



Logic ứng dụng được triển khai dưới dạng các function nhỏ (FaaS - Function as a Service), chạy theo yêu cầu trên hạ tầng của cloud provider (AWS Lambda, Google Cloud Functions).



\- \*\*Ưu điểm:\*\* Không cần quản lý server; tự động scale; chỉ trả tiền khi code thực sự chạy.

\- \*\*Nhược điểm:\*\* Cold start latency; giới hạn thời gian thực thi; vendor lock-in; khó test và debug locally.

\- \*\*Phù hợp:\*\* API không thường xuyên, background jobs, xử lý file/media, webhook handler.

\- \*\*Ví dụ thực tế:\*\* Image resize khi upload ảnh, gửi email tự động, xử lý thanh toán Stripe webhook.



\# \*\*Câu 3. Các thuộc tính chất lượng (Quality Attributes) của phần mềm\*\*



Thuộc tính chất lượng (hay còn gọi là \*\*Non-Functional Requirements - NFR\*\*) là những tiêu chí đánh giá \_cách\_ hệ thống thực hiện các chức năng của nó, trái ngược với các yêu cầu chức năng mô tả \_những gì\_ hệ thống làm. Chúng là kết quả trực tiếp của quyết định kiến trúc và ảnh hưởng sâu sắc đến trải nghiệm người dùng cũng như khả năng vận hành của hệ thống.



| \*\*Thuộc tính\*\*                            | \*\*Định nghĩa\*\*                                                                                                                       | \*\*Ví dụ thực tế\*\*                                                                                                           |

| ----------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------ | --------------------------------------------------------------------------------------------------------------------------- |

| \*\*Scalability (Khả năng mở rộng)\*\*        | Hệ thống có thể xử lý tải tăng thêm bằng cách thêm tài nguyên (scale out) hoặc nâng cấp tài nguyên (scale up) mà không cần viết lại. | Thêm server khi lượng người dùng tăng gấp đôi trong dịp sale 12/12; database sharding khi dữ liệu vượt ngưỡng.              |

| \*\*Availability (Tính sẵn sàng)\*\*          | Tỷ lệ thời gian hệ thống hoạt động bình thường, thường được đo bằng SLA (ví dụ: 99.9% = downtime tối đa \~8.7 giờ/năm).               | Hệ thống ngân hàng yêu cầu 99.99% uptime; deploy không downtime (zero-downtime deployment) bằng Blue-Green deployment.      |

| \*\*Performance (Hiệu năng)\*\*               | Tốc độ và thông lượng của hệ thống: thời gian phản hồi (response time), số request/giây (throughput), sử dụng tài nguyên.            | API phải trả về kết quả trong < 200ms với 1000 concurrent users; trang web tải xong trong < 3 giây.                         |

| \*\*Security (Bảo mật)\*\*                    | Khả năng bảo vệ hệ thống khỏi truy cập trái phép, rò rỉ dữ liệu, tấn công (SQL Injection, XSS, DDoS...).                             | Mã hóa dữ liệu nhạy cảm AES-256; xác thực JWT; rate limiting để chống brute force; HTTPS bắt buộc.                          |

| \*\*Maintainability (Khả năng bảo trì)\*\*    | Mức độ dễ dàng để sửa lỗi, thêm tính năng, cập nhật code mà không làm vỡ những phần đang hoạt động.                                  | Code coverage > 80%; CI/CD pipeline tự động; thiết kế theo SOLID principles; tài liệu API rõ ràng.                          |

| \*\*Reliability (Độ tin cậy)\*\*              | Hệ thống hoạt động đúng và ổn định trong một khoảng thời gian xác định, không có lỗi ngoài ý muốn.                                   | Tỷ lệ lỗi < 0.1% trong 30 ngày liên tục; transaction atomicity trong thanh toán; retry mechanism cho API calls.             |

| \*\*Testability (Khả năng kiểm thử)\*\*       | Mức độ dễ dàng để viết và chạy các bài kiểm tra tự động, đảm bảo code hoạt động đúng sau mỗi thay đổi.                               | Dependency Injection để mock external services; kiến trúc Hexagonal (Ports \& Adapters) tách biệt logic khỏi infrastructure. |

| \*\*Interoperability (Khả năng tương tác)\*\* | Khả năng tích hợp và giao tiếp với các hệ thống khác (nội bộ hoặc bên thứ ba) thông qua các chuẩn mở.                                | REST API theo chuẩn OpenAPI; hỗ trợ OAuth2 để tích hợp với Google/Facebook Login; webhook cho bên thứ ba.                   |

| \*\*Observability (Khả năng quan sát)\*\*     | Khả năng hiểu được trạng thái nội tại của hệ thống thông qua các đầu ra như logs, metrics, traces.                                   | Dashboard Grafana theo dõi CPU/Memory/Error rate; distributed tracing với Jaeger; structured logging với ELK Stack.         |



\## \*\*3.1. Mối quan hệ đánh đổi giữa các thuộc tính chất lượng (Quality Attribute Trade-offs)\*\*



Trong thực tế, các thuộc tính chất lượng thường \*\*mâu thuẫn với nhau\*\*. Kiến trúc sư phần mềm phải đưa ra quyết định đánh đổi (trade-off) có chủ đích dựa trên yêu cầu kinh doanh:



\- \*\*Security vs. Performance:\*\* Mã hóa và xác thực tăng cường bảo mật nhưng làm tăng latency. HTTPS an toàn hơn HTTP nhưng tốn chi phí xử lý TLS handshake.

\- \*\*Availability vs. Consistency:\*\* Theo định lý CAP, hệ thống phân tán không thể đồng thời đảm bảo cả tính nhất quán (Consistency) và tính sẵn sàng (Availability) khi có network partition. Nhiều hệ thống chọn Eventual Consistency để ưu tiên Availability.

\- \*\*Scalability vs. Maintainability:\*\* Microservices cho phép scale từng phần nhưng làm tăng độ phức tạp vận hành. Monolith dễ bảo trì hơn nhưng khó scale.

\- \*\*Performance vs. Testability:\*\* Tối ưu hóa performance thường dẫn đến code phức tạp, khó test hơn.



\# \*\*Kết luận: Kiến trúc tốt và Sự thành công của dự án\*\*



Quay lại bài toán của startup: \*\*kiến trúc tốt không phải là kiến trúc phức tạp nhất\*\* - mà là kiến trúc phù hợp nhất với giai đoạn và bài toán hiện tại, đồng thời có khả năng tiến hóa theo thời gian.



\- \*\*Giai đoạn MVP (0-6 tháng):\*\* Chọn Modular Monolith. Ra mắt nhanh, đơn giản, nhưng code được tổ chức theo module rõ ràng, dễ tách ra sau này.

\- \*\*Giai đoạn tăng trưởng (6-18 tháng):\*\* Tách dần các module thành service khi cần thiết (Strangler Fig Pattern). Không tái kiến trúc toàn bộ một lúc.

\- \*\*Giai đoạn scale (18+ tháng):\*\* Áp dụng Microservices cho các domain thực sự cần scale độc lập (thường chỉ 20-30% chức năng cần Microservices thực sự).



Như Grady Booch đã nói: \_"Architecture represents the significant design decisions that shape a system, where significant is measured by cost of change."\_ - Kiến trúc là tập hợp các quyết định thiết kế quan trọng nhất, và "quan trọng" được đo bằng chi phí thay đổi. Đầu tư vào kiến trúc đúng đắn ngay từ đầu chính là đầu tư vào sự thành công bền vững của dự án.

