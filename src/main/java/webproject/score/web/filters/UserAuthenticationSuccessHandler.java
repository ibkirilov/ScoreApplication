package webproject.score.web.filters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import webproject.score.data.models.User;
import webproject.score.errors.UserNotFoundException;
import webproject.score.services.models.UserServiceModel;
import webproject.score.services.services.AuthenticatedUserService;
import webproject.score.services.services.UsersService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final AuthenticatedUserService authenticatedUserService;
    private final UsersService usersService;
    private final ModelMapper mapper;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    public UserAuthenticationSuccessHandler(AuthenticatedUserService authenticatedUserService, UsersService usersService, ModelMapper mapper) {
        this.authenticatedUserService = authenticatedUserService;
        this.usersService = usersService;
        this.mapper = mapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String username = authenticatedUserService.getUsername();
        try {
            User user = usersService.getUserByUsername(username);
            UserServiceModel userServiceModel = mapper.map(user, UserServiceModel.class);
            httpServletRequest.getSession()
                    .setAttribute("username", userServiceModel.getUsername());
        } catch (UserNotFoundException ex) {
            //TODO what to do here? and why my exception is not working if user is not existing?
        }

        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/home");
    }
}
