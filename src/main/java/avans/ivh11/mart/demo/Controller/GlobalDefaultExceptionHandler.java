package avans.ivh11.mart.demo.Controller;

import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.thymeleaf.exceptions.TemplateEngineException;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;

@ControllerAdvice
class GlobalDefaultExceptionHandler {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView handleError404(HttpServletRequest request, Exception e) {
	    logger.error("404 - page was not found!");

		return this.errorValues("error/404", request, e, "Oeps - 404");
	}

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleError403(HttpServletRequest request, Exception e) {
        logger.error("403 - Access Denied!");

        return this.errorValues("error/403", request, e, "Oeps - 403");
    }

    @ExceptionHandler(TemplateEngineException.class)
    public ModelAndView handleThymeleaf(HttpServletRequest request, Exception e) {
        logger.error("Thymeleaf error - critical error while loading template!");

        return this.errorValues("error/5xx", request, e, "Oeps - 500");
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {
        logger.error("5xx - Error!");
        logger.error(e.getMessage());
        if (AnnotationUtils.findAnnotation
                (e.getClass(), ResponseStatus.class) != null)
            throw e;

        return this.errorValues("error/error", request, e, "5xx - Error");
    }

    private ModelAndView errorValues(String viewName, HttpServletRequest request, Exception e, String text) {
        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("title", text);
        mav.addObject("exception", e);
        mav.addObject("url", request.getRequestURL());

        return mav;
    }
}