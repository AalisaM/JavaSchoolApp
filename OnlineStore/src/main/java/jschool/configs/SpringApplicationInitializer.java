package jschool.configs;

import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringApplicationInitializer  extends AbstractAnnotationConfigDispatcherServletInitializer implements WebApplicationInitializer{

  protected Class<?>[] getRootConfigClasses() {
    return new Class[] {SecurityConfig.class};
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class[] { SpringWebConfig.class };
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] { "/" };
  }

  @Override
  public void onStartup(ServletContext container){
    AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
    rootContext.register(SecurityConfig.class);

    ContextLoaderListener contextLoaderListener = new ContextLoaderListener(rootContext);
    container.addListener(contextLoaderListener);
    container.setInitParameter("contextInitializerClasses", "jschool.configs.ContextInitializer");

    AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
    webContext.register(SpringWebConfig.class);

    DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(webContext);
    dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);

    ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", dispatcherServlet);
    dispatcher.addMapping("/");

    FilterRegistration.Dynamic multipartFilter = container.addFilter("multipartFilter", MultipartFilter.class);
    multipartFilter.addMappingForUrlPatterns(null, true, "/*");

    dispatcher.setLoadOnStartup(1);
    dispatcher.setMultipartConfig(new MultipartConfigElement(
            null, 20_971_520L, 41_943_040L, 512_000
    ));

  }
}
