package exercise;

import java.net.URI;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts") // Список страниц
    public ResponseEntity<List<Post>> index(@RequestParam(defaultValue = "10") Integer limit) {
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(posts.size()))
                        .body(posts.stream().limit(limit).toList());
    }

    @PostMapping("/posts") // Создание страницы
    public ResponseEntity<Post> create(@RequestBody Post page) {
        posts.add(page);
        return ResponseEntity.status(HttpStatus.CREATED).body(page);
    }

    @GetMapping("/posts/{id}") // Вывод страницы
    public ResponseEntity<Post> show(@PathVariable String id) {
        Post answer = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst().orElse(null);
        if(answer == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok()
                            .body(answer);
        }
    }

    @PutMapping("/posts/{id}") // Обновление страницы
    public ResponseEntity<Post> update(@PathVariable String id, @RequestBody Post data) {
        var maybePage = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        if (maybePage.isPresent()) {
            var page = maybePage.get();
            page.setId(data.getId());
            page.setTitle(data.getTitle());
            page.setBody(data.getBody());
            return ResponseEntity.ok().body(data);
        }else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
    // END

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
