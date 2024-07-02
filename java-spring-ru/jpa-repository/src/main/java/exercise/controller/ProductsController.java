package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @GetMapping(path = "")
    public List<Product> show(@RequestParam(defaultValue = "0") int min, @RequestParam(defaultValue = "0") int max) {
        List<Product> products;
        if (min != 0 && max != 0) {
            products = productRepository.findAllByPriceBetweenOrderByPrice(min, max);
        } else if (min != 0) {
            products = productRepository.findAllByPriceLessThanOrderByPrice(min);
        } else if (max != 0){
            products = productRepository.findAllByPriceGreaterThanOrderByPrice(max);
        } else
            products = productRepository.findAll(Sort.by(Sort.Order.asc("price")));

        products.sort(Comparator.comparingInt(Product::getPrice));
        return products;
    }
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}
