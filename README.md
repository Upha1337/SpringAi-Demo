## Hướng dẫn chạy demo
### Yêu cầu:
* Docker (tùy chọn)
  * redis-stack
* Redis
* Mistral API Key
```
spring.application.name=SpringAiDemo
springdoc.api-docs.path=/api-docs

spring.ai.mistralai.api-key=YOUR_KEY
spring.ai.mistralai.chat.options.model=pixtral-large-latest
spring.ai.mistralai.chat.options.temperature=0.7
spring.ai.mistralai.chat.options.max-tokens=2000

spring.ai.mistralai.embedding.options.model=mistral-embed
spring.ai.mistralai.embedding.options.encoding-format=float

spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.data.redis.username=default
spring.data.redis.password=
```

## Vector Database
***
### Định nghĩa
```
- Loại cơ sở dữ liệu lưu trữ thông tin dưới dạng vector biểu diễn dữ liệu 
  trong không gian nhiều chiều. 
- Cho phép máy tính tìm kiếm theo độ tương đồng thay vì khớp chính xác, 
  giúp mô hình AI hiểu ngữ cảnh và mối liên hệ giữa các dữ liệu
```
### Cơ chế
```
- Mỗi vector tương ứng với một đối tượng: văn bản, hình ảnh, video, tài liệu…
- Các vector được sắp xếp theo nhiều chiều như: 
  thể loại, thời lượng, năm phát hành, số người xem chung
- Các đối tượng tương tự sẽ được gom nhóm gần nhau, 
  giúp tìm kiếm theo ngữ nghĩa
```
### Ứng dụng
```
Cosine Smilarity (tìm kiếm theo độ tương đồng):
  - Tìm ảnh giống nhau
  - Gợi ý sản phẩm, phim, bài hát
  - Phân tích ngữ nghĩa văn bản
Machine learning và Deep learning:
  - kết nối các thông tin liên quan giúp xây dựng mô hình học máy
LLMs và Generative AI:
  - mô hình được sử dụng trong ChatGPT và Bard, 
  dựa vào khả năng phân tích ngữ cảnh của văn bản nhờ sử dụng vector database
```