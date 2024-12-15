package pojo;

import DAO.CategoryDAO;  // Updated to use CategoryDAO
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

/**
 * Category generated by hbm2java
 */
@ManagedBean
@ViewScoped
public class Category implements java.io.Serializable {

    private Integer categoryId;
    private String categoryName;
    private String description;
    private String poster;
    private String searchCriteria;
    private List<Category> categoryList;
    private CategoryDAO categoryDAO = new CategoryDAO();  // Uses CategoryDAO

    public Category() {
        this.categoryDAO = new CategoryDAO(); // or any other initialization
    }
    
    public Category(CategoryDAO CategoryDAO) {
        this.categoryDAO = CategoryDAO;
    }


    
    public void setCategoryDAO(CategoryDAO CategoryDAO) {
        this.categoryDAO = CategoryDAO;
    }

    public Category(Integer categoryId, String categoryName, String description, String poster) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.poster = poster;
    }
    
    @PostConstruct
    public void init() {
        if (categoryId != null) {
            List<Category> categoryData = categoryDAO.getByID(categoryId);
            if (categoryData != null && !categoryData.isEmpty()) {
                Category cat = categoryData.get(0);
                this.categoryName = cat.getCategoryName();        // Set waktu dari data database
                this.description = cat.getDescription();// Set layanan dari data database
                this.poster = cat.getPoster();
            }
        }
    }
    
    // Getter and Setter for categoryId
    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    // Getter and Setter for categoryName
    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    // Getter and Setter for description
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for poster
    public String getPoster() {
        return this.poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
    
    public String getSearchCriteria() { return searchCriteria; }
    public void setSearchCriteria(String searchCriteria) { this.searchCriteria = searchCriteria; }
    
    
    // Getter and Setter for categoryList
    public List<Category> getCategoryList() {
         if (categoryList == null || searchCriteria == null || searchCriteria.isEmpty()) {
            categoryList = categoryDAO.retrieveTblCategories();
        }
        return categoryList;
    }
    
     public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
    
    public void filterCategory(AjaxBehaviorEvent event) {
        if (searchCriteria != null && !searchCriteria.isEmpty()) {
            categoryList = categoryDAO.searchCategory(searchCriteria, null, null, null);
        } else {
            categoryList = categoryDAO.retrieveTblCategories();
        }
    }
    
     // Method to save a new Category
    public String saveCategory() {
        categoryDAO.addCategory(this);
        return "categorydetail";
    }
    
    // Method to edit a Category
    public String editCategory() {
        Category category = new Category();
        category.setCategoryId(this.categoryId);            // Set the ID from the current bean
        category.setCategoryName(this.categoryName);        // Set time from the form
        category.setDescription(this.description);
        category.setPoster(this.poster);
        
        categoryDAO.editCategory(this);
        return "categorydetail.xhtml?faces-redirect=true";
    }
    
    // Method to delete a Category by ID
    public String deleteCategory() {
       if (this.categoryId != null) {
        CategoryDAO categoryDAO = new CategoryDAO();
        categoryDAO.deleteCategory(this.categoryId); // Call DAO to delete schedule by ID
    }
    return null; // Stay on the same page after deletion
}
    
     // Method to retrieve a Category by ID
    public String getById(int categoryId) {
        List<Category> categoryData = categoryDAO.getByID(categoryId);
        if (categoryData != null && !categoryData.isEmpty()) {
            this.categoryId = categoryData.get(0).getCategoryId();
            this.categoryName = categoryData.get(0).getCategoryName();
            this.description = categoryData.get(0).getDescription();
            this.poster = categoryData.get(0).getPoster();
            return "categoryedit";
            }
        System.out.println("Category not found for categoryId: " + categoryId);
        return "index";
    }

    public void loadCategory() {
        if (categoryId != null) {
            List<Category> categoryList = categoryDAO.getByID(categoryId);
            if (categoryList != null && !categoryList.isEmpty()) {
                Category cat = categoryList.get(0);
                this.categoryName = cat.getCategoryName(); // Load time from DB
                this.description = cat.getDescription(); // Load services from DB
                this.poster = cat.getPoster();
            }
        }
    }

 public List<Category> searchCategory() {
        return categoryDAO.searchCategory(null, categoryName, description, poster);
}  

 
}

     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     


   

    

    



