package avans.ivh11.mart.demo.Controller;


import avans.ivh11.mart.demo.Domain.Product;
import avans.ivh11.mart.demo.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductAPIController {

    @Autowired
    private ProductRepository productRepository;


    @RequestMapping(method= RequestMethod.GET, value="/products")
    public Iterable<Product> product() {
        return productRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, value="/products")
    public Long save(@RequestBody Product product){
        productRepository.save(product);
        return product.getId();
    }

    @RequestMapping(method = RequestMethod.GET, value="/products/{id}")
    public Product show(@PathVariable Long id){
        return productRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/products/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product){
        Product prodToUpdate = productRepository.findOne(id);

        if(product.getPrice() != prodToUpdate.getPrice()){
            prodToUpdate.setActive(false);
            productRepository.save(prodToUpdate);
            productRepository.save(product);
            return product;
        }else {

            if (product.getName() != null) {
                prodToUpdate.setName(product.getName());
            }
            if (product.getPrice() != 0.0f) {
                prodToUpdate.setPrice(product.getPrice());
            }
            if (product.getDescription() != null) {
                prodToUpdate.setDescription(product.getDescription());
            }
            if (product.getCategory() != null) {
                prodToUpdate.setCategory(product.getCategory());
            }
            if (product.getPhoto() != null) {
                prodToUpdate.setPhoto(product.getPhoto());
            }
            if (product.getQuantity() != null) {
                prodToUpdate.setQuantity(product.getQuantity());
            }
            if (product.getCreated() != null) {
                prodToUpdate.setCreated(product.getCreated());
            }
            productRepository.save(prodToUpdate);
            return prodToUpdate;
        }
    }


    @RequestMapping(method = RequestMethod.DELETE, value="/products/{id}")
    public String delete(@PathVariable Long id){
        Product productToDelete = productRepository.findOne(id);
        productRepository.delete(productToDelete);
        return "Product: " + productToDelete.getName() + " verwijderd";
    }






}
