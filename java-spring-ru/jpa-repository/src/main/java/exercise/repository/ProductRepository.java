package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import exercise.model.Product;

import org.springframework.data.domain.Sort;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // BEGIN
    List<Product> findAllByTitle(String title);

    List<Product> findAllByPriceBetweenOrderByPrice(int startPrice, int endPrice);

    List<Product> findAllByPriceLessThanOrderByPrice(int startPrice);

    List<Product> findAllByPriceGreaterThanOrderByPrice(int startPrice);
    // END
}
