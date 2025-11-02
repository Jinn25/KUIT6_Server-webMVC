package core.mvc;

import jwp.controller.*;

import java.util.HashMap;
import java.util.Map;

public class RequestMapper {
    private final Map<String, Controller> controllers = new HashMap<>();

    public RequestMapper() {
        controllers.put("/", new HomeController());
        controllers.put("/user/login", new LoginController());
        controllers.put("/user/logout", new LogoutController());
        controllers.put("/user/signup", new CreateUserController());
        controllers.put("/user/list", new UserListController());
        controllers.put("/user/updateForm", new UpdateUserFormController());
        controllers.put("/user/update", new UpdateUserController());
        controllers.put("/qna/form", new CreateQuestionFormController());
        controllers.put("/qna/create", new CreateQuestionController());
        controllers.put("/qna/show", new ShowController());
    }

    public Controller findController(String requestUri) {
        return controllers.get(requestUri);
    }
}
