package avans.ivh11.mart.demo.Controller;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
class GlobalDefaultExceptionHandler {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    public static final String DEFAULT_ERROR_VIEW = "error/error";

	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView handleError404(HttpServletRequest request, Exception e) {
		ModelAndView mav = new ModelAndView("error/404");
		mav.addObject("title", "Oeps - 404");
		mav.addObject("exception", e);
		mav.addObject("url", request.getRequestURL());

		return mav;
	}

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
		mav.addObject("title", "Oeps - 5xx");
		mav.addObject("errorcode", "Oeps");
        mav.addObject("exception", e);
        mav.addObject("url", request.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        logger.error("return to view");

        return mav;
    }
}