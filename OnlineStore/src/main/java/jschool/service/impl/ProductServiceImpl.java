package jschool.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jschool.dao.CategoryDAO;
import jschool.dao.ProductDAO;
import jschool.dto.CategoryRawDTO;
import jschool.dto.ProductDTO;
import jschool.model.Category;
import jschool.model.Product;
import jschool.service.DTOConverterService;
import jschool.service.ImageService;
import jschool.service.ProductService;
import jschool.validator.Message;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);

    private DTOConverterService dtoConverterService;
    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;
    private ImageService imageService;


    @Autowired
    public ProductServiceImpl(DTOConverterService dtoConverterService,
                              ProductDAO productDAO, CategoryDAO categoryDAO, ImageService imageService) {
        this.dtoConverterService = dtoConverterService;
        this.categoryDAO = categoryDAO;
        this.productDAO = productDAO;
        this.imageService = imageService;
    }

    /**
     * Get list of product entities from db
     * @return list
     */
    @Override
    @Transactional
    public List<Product> list() {
        return  this.productDAO.list();

    }

    /**
     * Get list of product dto
     * @return list
     */
    @Override
    @Transactional
    public Set<ProductDTO> listDTO(){
        return dtoConverterService.getProductDTOList(list());
    }


    /**
     * Update product by dto
     * @param p dto
     * @return Message object info
     */
    @Override
    @Transactional
    public Message update(ProductDTO p) {
        Message m = new Message();
        try {
            this.productDAO.update(this.convertToEntity(p));
            m.getConfirms().add("Product successfully updated");
        }catch (Exception e){
            m.getErrors().add("Product update failed.");
        }
        return m;
    }

    /**
     * Get product by id
     * @param id id
     * @return product
     */
    @Override
    @Transactional
    public Product findById(int id) {
        return this.productDAO.findById(id);
    }


    /**
     * Get product dto by id
     * @param id id
     * @return product
     */
    @Override
    @Transactional
    public ProductDTO findByIdFTO(int id){
        return this.dtoConverterService.getProductDTO(this.findById(id));
    }


    /**
     * Remove product by id
     * @param id id
     * @return message with removal result
     */
    @Override
    @Transactional
    public Message remove(int id) {
        Message m = new Message();
        try {
            this.productDAO.remove(id);
            m.getConfirms().add("Product remove succeed");
        }catch (Exception e){
            m.getErrors().add("Product removal failed");
        }
        return m;
    }
    /**
     * Revives product by id
     * @param id id
     * @return message with revive result
     */
    @Override
    @Transactional
    public Message revive(int id) {
        Message m = new Message();
        try {
            this.productDAO.revive(id);
            m.getConfirms().add("Product revival succeed");
        }catch (Exception e){
            m.getErrors().add("Product revival failed");
        }
        return m;
    }

    /**
     * Add product to db by dto
     * @param p dto
     */
    @Override
    @Transactional
    public void add(ProductDTO p) {
            this.productDAO.add(convertToEntity(p));
    }


    /**
     * Add product with image to db
     * @param p productdto
     * @param f image
     * @return Message object info
     */
    @Override
    @Transactional
    public Message addWithImage(ProductDTO p, MultipartFile f){
        Message m = new Message();
        try {
            if (!Objects.isNull(f)){
                String ext  = FilenameUtils.getExtension(f.getOriginalFilename());
                if (!ext.equals("png") && !ext.equals("jpg")){
                    logger.info("Cannot add image with extension::" + ext);
                    m.getErrors().add("Inappropriate image extension");
                    return m;
                }
            }
            int pid = this.productDAO.add(this.convertToEntity(p));
            if (!Objects.isNull(f)) {
                String imageFile = imageService.uploadFileHandler(f, String.valueOf(pid));
                this.productDAO.addImageToProduct(pid, imageFile);
            }
            m.getConfirms().add("Product successfully created");
        }catch (Exception e){
            m.getErrors().add("Product creation failed");
        }
        return m;
    }

    /**
     * Edit product with image to db
     * @param p productdto
     * @param f image
     * @return Message object info
     */
    @Override
    @Transactional
    public Message editWithImage(ProductDTO p, MultipartFile f) {
        Message m = new Message();
        try {
            if (!Objects.isNull(f)) {
                String ext = FilenameUtils.getExtension(f.getOriginalFilename());
                if (!ext.equals("png") && !ext.equals("jpg")) {
                    logger.info("Cannot edit image with extension::" + ext);
                    m.getErrors().add("Inappropriate image extension");
                    return m;
                }
            }
            this.productDAO.update(this.convertToEntity(p));
            if (!Objects.isNull(f)) {
                String imageFile = imageService.uploadFileHandler(f, String.valueOf(p.getId()));
                this.productDAO.addImageToProduct(p.getId(), imageFile);
            }
            m.getConfirms().add("Product successfully updated");
        }catch (Exception e){
            logger.error(e.toString());
            m.getErrors().add("Product update failed");
        }
        return m;
    }

    /**
     * Get filtered list of products
     * @param json input filter data {'minPlayer' :, 'maxPlayer' : , 'minPrice' :, 'maxPrice' : , 'categoryId' :}
     * @return list
     * @throws IOException
     */
    @Override
    @Transactional
    public  List<Product> filter(String json) throws IOException {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            int minPrice = jsonNode.get("minPrice").asText().isEmpty() ? 0 :  Integer.valueOf(jsonNode.get("minPrice").asText());
            int maxPrice = jsonNode.get("price").asText().isEmpty() ? 1000000 :  Integer.valueOf(jsonNode.get("price").asText());

            int minPlayer = jsonNode.get("minPlayer").asText().isEmpty() ? 0 : Integer.valueOf(jsonNode.get("minPlayer").asText());
            int maxPlayer = jsonNode.get("maxPlayer").asText().isEmpty() ? 40 : Integer.valueOf(jsonNode.get("maxPlayer").asText());
            int categoryId = jsonNode.get("category_id").asText().isEmpty() ? -1 : Integer.valueOf(jsonNode.get("category_id").asText());
            return this.productDAO.getProductsByFilter(Math.min(minPrice, maxPrice), Math.max(minPrice,maxPrice),
                                                       Math.min(minPlayer, maxPlayer), Math.max(minPlayer,maxPlayer),
                                                       categoryId);
        }catch (Exception e){
            logger.error(e.toString());
            return  this.productDAO.list();
        }

    }

    /**
     * Get filtered dto list of products
     * @param json input filter data {'minPlayer' :, 'maxPlayer' : , 'minPrice' :, 'maxPrice' : , 'categoryId' :}
     * @return list
     * @throws IOException
     */
    @Override
    @Transactional
    public Set<ProductDTO> filterDTO(String json) throws IOException {
       return dtoConverterService.getProductDTOList(filter(json));
    }

    /**
     * Get dto list of products by search phrase
     * @param json search phrase
     * @return list
     * @throws IOException
     */
    @Override
    @Transactional
    public Set<ProductDTO> search(String json) throws IOException {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            String name = jsonNode.get("request").asText();
            return dtoConverterService.getProductDTOList(this.productDAO.searchByRequest(name));
        }catch (Exception e){
            logger.error(e.toString());
            return  new HashSet<>();
        }
    }


    private Product convertToEntity(ProductDTO productDTO) {
        Product product = this.findById(productDTO.getId());
        if (Objects.isNull(product)) {
            logger.info("is null");
            product = new Product();
        }
        product = this.dtoConverterService.getProductFromDTO(product, productDTO);
        product.setPrice(productDTO.getPrice());
        product.setRule(productDTO.getRule());
        product.setDeleted(false);
        product.setAmount(productDTO.getAmount());
        product.setCategory(this.categoryDAO.findById(productDTO.getCategory().getId()));
        if (!Objects.isNull(productDTO.getUploadFile())) {
            product.setImageSource(imageService.uploadFileHandler(productDTO.getUploadFile(), productDTO.getUploadFile().getOriginalFilename()));
        }
        return product;
    }

    /**
     * Get list of categories
     * @return list
     */
    @Override
    @Transactional
    public List<Category> listCategory() {
        return  this.categoryDAO.listCategory();
    }


    /**
     * Get list of categories dto without foreign lists
     * @return list
     */
    @Override
    @Transactional
    public List<CategoryRawDTO> listCategoryDTO(){
        return dtoConverterService.getCategoryRAWDTOList(listCategory());
    }


}
