package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.config.Config;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
  private PostController controller;
  private static String path = null;
  private static String method = null;
  private static Long param = null;

  @Override
  public void init() {
//    final var repository = new PostRepository();
//    final var service = new PostService(repository);
//    controller = new PostController(service);

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    controller = (PostController) context.getBean("postController");
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {
    try {
      path = req.getRequestURI();
      method = req.getMethod();

      try {
        int idx = path.lastIndexOf("/") + 1;
        String value = path.substring((idx));
        param = Long.parseLong(value);
      }
      catch (Exception ex) { }

      if (method.equals("GET") && (path.equals("/api/posts") || path.matches("/api/posts/\\d+"))) {
        if (param == null)
          controller.all(resp);
        else  controller.getById(param, resp);
        return;
      }

      if (method.equals("POST") && path.equals("/api/posts")) {
        controller.save(req.getReader(), resp);
        return;
      }
      if (method.equals("DELETE") && path.matches("/api/posts/\\d+")) {
        controller.removeById(param, resp);
        return;
      }
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}

