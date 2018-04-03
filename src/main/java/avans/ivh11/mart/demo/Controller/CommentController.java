package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Domain.Product;
import avans.ivh11.mart.demo.Domain.RegisteredUser;
import avans.ivh11.mart.demo.Domain.Review;
import avans.ivh11.mart.demo.Repository.ProductRepository;
import avans.ivh11.mart.demo.Repository.ReviewRepository;
import avans.ivh11.mart.demo.Service.FlashService;
import avans.ivh11.mart.demo.Service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class CommentController {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @Autowired
    private FlashService flashService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional
    @PostMapping(value = "/comment/product/{productID}/create")
    public ModelAndView create(@PathVariable("productID") String id, Review review, BindingResult result, RedirectAttributes redirect) {
        ModelAndView mav = new ModelAndView();

        Product product = this.productRepository.findOne(Long.parseLong(id));
        review.updateTime();
        review.setProduct(product);
        review.setUser(this.userService.getCurrentUser());
        this.reviewRepository.save(review);
//        product.addReview(review);

        if (result.hasErrors()) {
            mav.addObject("title", "Comment - Create");
            mav.addObject("form_errors", result.getAllErrors());
            mav.setViewName("views/product/form");

            return mav;
        }

        mav.setViewName("redirect:/product/{productID}");
        redirect.addFlashAttribute("flash", this.flashService.createFlash("success", "Successfully created a new comment"));

        return mav;
    }

    @GetMapping(value = "/comment/product/{id}/delete/{commentId}")
    public ModelAndView delete(@PathVariable("id") String productId, @PathVariable("commentId") String commentId, RedirectAttributes redirect) {
        ModelAndView mav = new ModelAndView();

        try {
            this.reviewRepository.delete(Long.parseLong(commentId));
            redirect.addFlashAttribute("flash", this.flashService.createFlash("success", "Comment " + commentId + " was deleted"));
        } catch (Exception e) {
            redirect.addFlashAttribute("flash", this.flashService.createFlash("error", "Comment " + commentId + " could not be removed"));
        }

        mav.setViewName("redirect:/product/{id}");

        return mav;
    }
}
