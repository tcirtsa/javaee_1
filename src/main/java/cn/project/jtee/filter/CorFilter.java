package cn.project.jtee.filter;

import cn.project.jtee.annotation.LogAnnotation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Author yiren
 * @Date 2024/5/26
 * @Description filter the request and response before and after
 */
public class CorFilter implements HandlerInterceptor{
    private static final Logger logger = LogManager.getLogger("log4j2.xml");
    /**
     * create by: yiren
     * description: after url dispatch and Determine if the user is allowed to pass the request || log aspect
     * create time: 2024/5/26 下午2:13
     *
     * @params [request, response, handler]
     * @return [request, response, handler] || [boolean (pass or not)]
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession()==null) return false;
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            boolean present = method.isAnnotationPresent(LogAnnotation.class);
            if(present){
                logPresent(method.getAnnotation(LogAnnotation.class).value(),method.getAnnotation(LogAnnotation.class).level());
            }
            System.out.println("present: " + present);
        }
        return Objects.equals(request.getSession(false).getAttribute("username"), "yiren");
    }
    /**
     * create by: yiren
     * description: after handler execute
     * create time: 2024/5/26 下午2:15
     *
     * @params [request, response, handler, modelAndView]
     * @return [request, response, handler, modelAndView]
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("this is cor filter postHandle");
    }
    /**
     * create by: yiren
     * description: after view rendering
     * create time: 2024/5/26 下午2:16
     *
     * @params [request, response, handler, ex]
     * @return [request, response, handler, ex]
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("this is cor filter afterCompletion");
    }

    private static boolean logPresent(String msg,String level) {
        if(level.equals("info")){
            logger.info(msg);
        }
        if(level.equals("debug")){
            logger.debug(msg);
        }
        if(level.equals("error")){
            logger.error(msg);
        }
        return true;
    }
}
