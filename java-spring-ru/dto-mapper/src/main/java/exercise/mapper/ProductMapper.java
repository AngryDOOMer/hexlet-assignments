package exercise.mapper;

// BEGIN

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.model.Product;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class ProductMapper {
    @Mapping(target = "barcode", source = "vendorCode")
    @Mapping(target = "cost", source = "price")
    @Mapping(target = "name", source = "title")
    public abstract Product map(ProductCreateDTO dto);

    @Mapping(target = "vendorCode", source = "barcode")
    @Mapping(target = "price", source = "cost")
    @Mapping(target = "title", source = "name")
    public abstract ProductDTO map(Product model);

    @InheritConfiguration
    public abstract List<ProductDTO> map(List<Product> model);
    @Mapping(target = "cost", source = "price")
    public abstract void update(ProductUpdateDTO dto, @MappingTarget Product model);
}
// END
