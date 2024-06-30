package exercise.controller.users;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import exercise.model.Post;
import exercise.Data;

//Реализуйте дополнительные обработчики маршрутов для Post:

//        GET /api/users/{id}/posts — список всех постов, написанных пользователем с таким же userId, как id в маршруте

//        POST /api/users/{id}/posts – создание нового поста, привязанного к пользователю по id. Код должен возвращать
//        статус 201, тело запроса должно содержать slug, title и body. Обратите внимание, что userId берется из маршрута
// BEGIN
@RestController
@RequestMapping("/api/users/")
public class PostsController {

    private List<Post> posts = Data.getPosts();

    @GetMapping("{id}/posts") // Список страниц
    public List<Post> index(@PathVariable int id) {
        return posts.stream().filter(post -> id == post.getUserId()).toList();
    }

    @PostMapping("{id}/posts") // Создание страницы
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post page, @PathVariable int id) {
        page.setUserId(id);
        posts.add(page);
        return page;
    }
}
// END
