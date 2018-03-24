package avans.ivh11.mart.demo.Controller;

import avans.ivh11.mart.demo.Domain.Product;
import avans.ivh11.mart.demo.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController()
public class ProductAPIController {

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/api/products")
    public Iterable<Product> product() {
        return productRepository.findAll();
    }

    @PostMapping(value = "/api/products")
    public Long create(@RequestBody Product product) {
        productRepository.save(product);
        return product.getId();
    }

    @PutMapping(value = "/api/products")
    public boolean update(@RequestBody Set<Product> product) {
        for (Product p : product) {
            this.updateProduct(p.getId(), p);
        }

        return false;
    }

    @DeleteMapping(value = "/api/products")
    public boolean delete() {
        try {
            this.productRepository.deleteAll();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @GetMapping(value = "/api/products/{id}")
    public Product get(@PathVariable Long id) {
        return productRepository.findOne(id);
    }

    @PutMapping(value = "/api/products/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        return this.updateProduct(id, product);
    }

    @DeleteMapping(value="/api/products/{id}")
    public String delete(@PathVariable Long id){
        Product productToDelete = productRepository.findOne(id);
        productRepository.delete(productToDelete);
        return "Product: " + productToDelete.getName() + " deleted";
    }

    @PostMapping(value = "/api/products/{id}")
    public String create(@PathVariable Long id) {
        return "Error - Function not supported";
    }

    private Product updateProduct(Long id, Product product) {
        Product prodToUpdate = productRepository.findOne(id);

        if (product.getPrice() != prodToUpdate.getPrice()) {
            prodToUpdate.setActive(false);
            productRepository.save(prodToUpdate);
            productRepository.save(product);
            return product;
        }

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
