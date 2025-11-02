package core.mvc;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {

    private final RequestMapper requestMapper = new RequestMapper();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String requestUri = request.getRequestURI();
        Controller controller = requestMapper.findController(requestUri);

        if (requestUri.equals("/user/loginFailed")) {
            request.getRequestDispatcher("/user/loginFailed.jsp").forward(request, response);
            return;
        }


        if (controller == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "요청하신 URL을 찾을 수 없습니다: " + requestUri);
            return;
        }

        try {
            String viewName = controller.execute(request, response);

            if (viewName.startsWith("redirect:")) {
                String redirectPath = viewName.substring("redirect:".length());
                response.sendRedirect(redirectPath);
            }
            else {
                request.getRequestDispatcher(viewName + ".jsp").forward(request, response);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
