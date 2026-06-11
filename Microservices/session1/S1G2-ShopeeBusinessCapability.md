**\[Bài tập 4 - Giỏi\] Phân rã Shopee theo Khả năng kinh doanh (Business Capability)**

# **Đặt vấn đề**

Shopee là một siêu ứng dụng (super-app) với hàng triệu giao dịch mỗi ngày. Trong vai trò kiến trúc sư trưởng, nhiệm vụ là chia nhỏ Shopee thành các module độc lập dựa trên **khả năng kinh doanh (Business Capability)** theo hướng **Top-down** - tức là xuất phát từ các năng lực nghiệp vụ mà doanh nghiệp cần có, rồi mới ánh xạ xuống các service kỹ thuật.

Nguyên tắc cốt lõi: mỗi module phải gắn với _một_ năng lực kinh doanh rõ ràng, sở hữu dữ liệu riêng (database per service), và **không chồng chéo chức năng** với module khác. Đây chính là cách phân tách theo Bounded Context trong Domain-Driven Design.

# **1\. Tổng quan các module chính**

Dưới đây là 8 module (service) chính được phân rã từ năng lực kinh doanh cốt lõi của Shopee:

| **#** | **Module (Service)**             | **Trách nhiệm chính**                                                          |
| ----- | -------------------------------- | ------------------------------------------------------------------------------ |
| 1     | **User & Account Service**       | Quản lý danh tính người dùng: đăng ký, đăng nhập, hồ sơ, xác thực, phân quyền. |
| 2     | **Product Catalog Service**      | Quản lý thông tin sản phẩm, danh mục, thuộc tính, tìm kiếm và lọc sản phẩm.    |
| 3     | **Order Management Service**     | Quản lý vòng đời đơn hàng: tạo, xác nhận, theo dõi trạng thái, hủy đơn.        |
| 4     | **Payment Service**              | Xử lý thanh toán, ví ShopeePay, hoàn tiền, đối soát giao dịch tài chính.       |
| 5     | **Shipping & Logistics Service** | Quản lý vận chuyển, kết nối đơn vị giao hàng, theo dõi hành trình kiện hàng.   |
| 6     | **Promotion & Voucher Service**  | Quản lý khuyến mãi, mã giảm giá, flash sale, chương trình tích điểm.           |
| 7     | **Review & Rating Service**      | Quản lý đánh giá, xếp hạng sản phẩm và người bán, kiểm duyệt nội dung.         |
| 8     | **Notification Service**         | Gửi thông báo đa kênh: push, SMS, email, in-app về đơn hàng và khuyến mãi.     |

# **2\. Mô tả chi tiết từng module**

## **2.1. User & Account Service (Quản lý người dùng & tài khoản)**

**Năng lực kinh doanh:** Quản lý danh tính khách hàng và người bán.

- Đăng ký, đăng nhập (bao gồm đăng nhập mạng xã hội, OTP).
- Quản lý hồ sơ cá nhân, địa chỉ giao hàng, thông tin liên hệ.
- Xác thực (authentication) và phân quyền (authorization) cho người mua, người bán, admin.
- **Dữ liệu sở hữu:** thông tin tài khoản, hồ sơ, địa chỉ.

## **2.2. Product Catalog Service (Quản lý sản phẩm)**

**Năng lực kinh doanh:** Quản lý toàn bộ thông tin hàng hóa được bày bán.

- Lưu trữ và quản lý thông tin sản phẩm: tên, mô tả, hình ảnh, giá, biến thể (size, màu).
- Quản lý danh mục (category) và thuộc tính sản phẩm.
- Cung cấp năng lực tìm kiếm, lọc và gợi ý sản phẩm.
- **Dữ liệu sở hữu:** catalog sản phẩm, danh mục, tồn kho hiển thị.

## **2.3. Order Management Service (Quản lý đơn hàng)**

**Năng lực kinh doanh:** Điều phối vòng đời đơn hàng từ lúc đặt đến lúc hoàn tất.

- Tạo đơn hàng từ giỏ hàng, xác nhận đơn, tính tổng tiền.
- Quản lý trạng thái đơn: chờ xác nhận, đang xử lý, đang giao, hoàn thành, đã hủy.
- Điều phối (orchestrate) các service khác: gọi Payment để thanh toán, gọi Shipping để giao hàng.
- **Dữ liệu sở hữu:** đơn hàng, chi tiết đơn, lịch sử trạng thái.

## **2.4. Payment Service (Thanh toán)**

**Năng lực kinh doanh:** Xử lý mọi luồng tiền trong hệ thống.

- Tích hợp nhiều cổng thanh toán: thẻ, chuyển khoản, COD, ví ShopeePay.
- Xử lý hoàn tiền (refund) và đối soát giao dịch (reconciliation).
- Đảm bảo tính nhất quán và bảo mật cho dữ liệu tài chính (PCI-DSS).
- **Dữ liệu sở hữu:** giao dịch thanh toán, số dư ví, lịch sử hoàn tiền.

## **2.5. Shipping & Logistics Service (Vận chuyển)**

**Năng lực kinh doanh:** Quản lý giao nhận hàng hóa đến tay người mua.

- Tính phí vận chuyển dựa trên khoảng cách, khối lượng, đơn vị giao hàng.
- Kết nối với các đối tác logistics (Giao Hàng Nhanh, J&T, ShopeeXpress).
- Theo dõi hành trình kiện hàng (tracking) theo thời gian thực.
- **Dữ liệu sở hữu:** vận đơn, trạng thái giao hàng, thông tin đối tác vận chuyển.

## **2.6. Promotion & Voucher Service (Khuyến mãi)**

**Năng lực kinh doanh:** Quản lý các chương trình kích cầu mua sắm.

- Tạo và quản lý mã giảm giá, voucher, khuyến mãi theo điều kiện.
- Vận hành flash sale, sự kiện sale lớn (9.9, 11.11, 12.12).
- Quản lý chương trình tích điểm, Shopee Coins, hoàn xu.
- **Dữ liệu sở hữu:** voucher, quy tắc khuyến mãi, lịch sử áp dụng mã.

## **2.7. Review & Rating Service (Đánh giá)**

**Năng lực kinh doanh:** Quản lý uy tín và phản hồi của cộng đồng.

- Cho phép người mua đánh giá sao và viết nhận xét sau khi mua.
- Tính toán điểm xếp hạng cho sản phẩm và người bán (shop rating).
- Kiểm duyệt nội dung đánh giá để chống spam, nội dung độc hại.
- **Dữ liệu sở hữu:** đánh giá, điểm rating, báo cáo vi phạm.

## **2.8. Notification Service (Thông báo)**

**Năng lực kinh doanh:** Giao tiếp với người dùng qua mọi kênh.

- Gửi thông báo đẩy (push), SMS, email, in-app notification.
- Thông báo cập nhật trạng thái đơn hàng, khuyến mãi, nhắc nhở.
- Quản lý mẫu (template) và tùy chọn nhận thông báo của người dùng.
- **Dữ liệu sở hữu:** hàng đợi thông báo, template, cấu hình kênh.

# **3\. Đảm bảo không chồng chéo chức năng**

Để đạt được kết quả mong muốn - "các module không bị chồng chéo chức năng" - cách phân rã trên tuân thủ các nguyên tắc sau:

- **Mỗi module một năng lực kinh doanh (Single Capability):** Mỗi service tương ứng với đúng một năng lực nghiệp vụ, không gánh trách nhiệm của service khác.
- **Database per Service (Sở hữu dữ liệu riêng):** Mỗi service sở hữu và quản lý dữ liệu của riêng nó. Service khác muốn truy cập phải gọi qua API, không truy cập trực tiếp database. Điều này loại bỏ chồng chéo về quyền sở hữu dữ liệu.
- **High Cohesion, Low Coupling:** Các chức năng liên quan chặt chẽ được gom vào cùng một service (cohesion cao), trong khi sự phụ thuộc giữa các service được giảm thiểu (coupling thấp).
- **Phân định ranh giới rõ qua Bounded Context:** Ví dụ, khái niệm "trạng thái" của một đơn hàng thuộc về Order Service; "trạng thái" giao hàng thuộc về Shipping Service - không nhập nhằng.

## **Minh họa ranh giới: ai chịu trách nhiệm gì?**

Một ví dụ kinh điển dễ gây chồng chéo là việc tính tiền. Cách phân định rõ ràng:

- **Promotion Service** quyết định _giảm giá bao nhiêu_ (áp dụng voucher).
- **Order Service** tổng hợp _đơn hàng gồm những gì và tổng tiền tạm tính_.
- **Shipping Service** tính _phí vận chuyển_.
- **Payment Service** chỉ _thực hiện thu đúng số tiền cuối cùng_ mà Order Service chốt lại - không tự tính khuyến mãi hay phí ship.

# **4\. Kết luận**

Việc phân rã Shopee theo Business Capability theo hướng Top-down cho ra 8 module độc lập, mỗi module gắn với một năng lực kinh doanh rõ ràng và sở hữu dữ liệu riêng. Cách tiếp cận này đảm bảo:

- Không chồng chéo chức năng - mỗi trách nhiệm chỉ thuộc về một service duy nhất.
- Khả năng mở rộng độc lập - Payment và Order có thể scale mạnh trong dịp sale mà không ảnh hưởng Review hay User.
- Dễ phân công team - mỗi team sở hữu trọn vẹn một capability.
- Nền tảng tốt để triển khai Microservices khi quy mô đòi hỏi.

_Lưu ý: Trong thực tế Shopee còn nhiều capability khác như Seller Center, Customer Support, Chat, Live Streaming, Anti-Fraud, Search & Recommendation... Tám module trên là các năng lực cốt lõi nhất, đủ để minh họa nguyên tắc phân rã không chồng chéo._