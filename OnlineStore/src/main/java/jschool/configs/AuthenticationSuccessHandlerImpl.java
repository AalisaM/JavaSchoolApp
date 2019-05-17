package jschool.configs;

import jschool.service.CartService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;

@Component("authSuccessHandler")
public class AuthenticationSuccessHandlerImpl
        implements AuthenticationSuccessHandler {
    private static final Logger log = Logger.getLogger(AuthenticationSuccessHandlerImpl.class);

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private CartService cartService;
    private static String cookieFieldName = "cartItem";

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException {

        Cookie[] cookies = request.getCookies();
        String cartAnonJson = "";
        for (Cookie cookie : cookies) {
            if (cookieFieldName.equals(cookie.getName()))
                cartAnonJson = new String(Base64.getDecoder().decode(cookie.getValue().getBytes()));
        }
        log.info(cartAnonJson);
        if (!cartAnonJson.isEmpty()){
            try {
                cartService.mergeUserCartWithAnon(cartAnonJson);
            }catch (Exception e){
                log.error(e.toString());
            }
        }

        Cookie cookie = new Cookie(cookieFieldName, null); // Not necessary, but saves bandwidth.
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);

    }

    protected void handle(HttpServletRequest request,
                          HttpServletResponse response, Authentication authentication)
            throws IOException {

        String targetUrl = "/";

        if (response.isCommitted()) {
            log.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
        Cookie cookie = new Cookie("cartItem", null); // Not necessary, but saves bandwidth.
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        log.info(cookie.getMaxAge());
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
