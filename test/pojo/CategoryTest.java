/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import DAO.CategoryDAO;
import java.sql.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pojo.Category;
import static org.junit.Assert.*;
import javax.faces.event.AjaxBehaviorEvent;
import java.util.ArrayList;

public class CategoryTest {
    
    private Category category;
    private CategoryDAO testCategoryDAO;
    
    public CategoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    category = new Category();
       
    testCategoryDAO = new CategoryDAO() {
    public List<Category> getById(Integer categoryId) {
        List<Category> categories = new ArrayList<>();
        if (categoryId == 1) {
            categories.add(new Category(1, "Pedicure", "A pedicure is a cosmetic treatment for the feet that involves soaking, exfoliating, trimming toenails, and often includes a massage and nail polish application for beauty and hygiene.", "\"C:\\Users\\Aspire 7\\Downloads\\pedicure.jpg\""));
        }
        return categories; // Return the list with one location for id 1
    }



    @Override
    public List<Category> searchCategory(String searchCriteria, String categoryName, String description, String poster) {
        List<Category> categories = new ArrayList<>();
        if ("Nail Art".equalsIgnoreCase(categoryName)) {
            categories.add(new Category(1, "Nail Art", "Artistic nail designs", "nailart.jpg")); 
            categories.add(new Category(2, "Nail Art", "Creative nail designs", "creative.jpg")); 
        } else if ("Manicure".equalsIgnoreCase(categoryName)) {
            categories.add(new Category(3, "Manicure", "Nail care and polish", "manicure.jpg")); 
        }
        return categories;
    }
};
       
        category = new Category();
        category.setCategoryDAO(testCategoryDAO);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testgetCategoryList() {
    System.out.println("Test Get All Record");
    Category instance = new Category();
    int expResult = 3; 
    List<Category> result = instance.getCategoryList();
    assertEquals(expResult, result.size());
    }
    
    @Test
    public void testGetById() {

    int testcategoryId = 2; 
    Category instance = new Category(testCategoryDAO); 
    String result = instance.getById(testcategoryId); 
    assertEquals("categoryedit", result); 
    assertEquals(testcategoryId, (int) instance.getCategoryId());
    assertEquals("Nail Art 3D", instance.getCategoryName()); 
    assertEquals("Nail art is a creative way to decorate nails with unique designs, from simple patterns to intricate 3D embellishments. It’s a popular service for self-expression and style, attracting customers who want to personalize their look.", instance.getDescription()); // Ensure branch location is set correctly
    assertEquals("C:\\Users\\Aspire 7\\Downloads\\Nailart.png", instance.getPoster());
}
    
    @Test
    public void testsaveCategory() {
    System.out.println("Test Save Category");
    Category cat = new Category();
        cat.setCategoryId(4);
        cat.setCategoryName("Nail Extension");
        cat.setDescription("Nail extensions are a beauty treatment that enhances the length and appearance of natural nails using materials like acrylic or gel. They can be customized in various shapes and designs, providing a durable and polished look. Ideal for those with short or weak nails, nail extensions offer a glamorous touch and last several weeks with proper care.");
        cat.setPoster("\"C:\\Users\\Aspire 7\\Downloads\\nailextension.jpg\"");
    String expResult = "categorydetail";
    String result = cat.saveCategory();
    assertEquals(expResult, result);
  }
    
    @Test
  public void testeditCategory() {
    System.out.println("editCategory");
    Integer categoryId = 2;
    Category cat = new Category();
        cat.setCategoryName("Nail Art 3D");
        cat.setDescription("Nail art is a creative way to decorate nails with unique designs, from simple patterns to intricate 3D embellishments. It’s a popular service for self-expression and style, attracting customers who want to personalize their look.");
        cat.setPoster("\"C:\\Users\\Aspire 7\\Downloads\\nailart.png\"");
    String expResult = "categorydetail.xhtml?faces-redirect=true";
    String result = cat.editCategory();
    assertEquals(expResult, result);
  }
  
  @Test
    public void testdeleteCategory() {
    System.out.println("deleteCategory");
    Category instance = new Category();
    instance.setCategoryId(291);
    String result = instance.deleteCategory();
    String expResult = null;
    assertEquals(expResult, result);
}
    
    @Test
    public void testSetAndGetSearchCriteria() {
        Category category = new Category();
        String expectedCriteria = "Pedicure";
        category.setSearchCriteria(expectedCriteria);
        String result = category.getSearchCriteria();
        assertEquals(expectedCriteria, result);
    }
    
    @Test
    public void testFilterCategoryWithSearchCriteria() {
    Category category = new Category();
    category.setSearchCriteria("Pedicure");

    List<Category> mockCategoryList = new ArrayList<>();
    mockCategoryList.add(new Category(1, "Pedicure", "A pedicure is a cosmetic treatment for the feet that involves soaking, exfoliating, trimming toenails, and often includes a massage and nail polish application for beauty and hygiene.", "\"C:\\Users\\Aspire 7\\Downloads\\pedicure.jpg\"")); // Provide an id

    category.setCategoryList(mockCategoryList);
    category.filterCategory(null); 
    assertNotNull(category.getCategoryList());
    }
    
    @Test
    public void testFilterCategoryWithoutSearchCriteria() {
    Category category = new Category();
    category.setSearchCriteria(""); 

    List<Category> mockCategoryList = new ArrayList<>();
    mockCategoryList.add(new Category(1, "Pedicure", "A pedicure is a cosmetic treatment for the feet that involves soaking, exfoliating, trimming toenails, and often includes a massage and nail polish application for beauty and hygiene.", "\"C:\\Users\\Aspire 7\\Downloads\\pedicure.jpg\"")); // Provide an id
    mockCategoryList.add(new Category(2, "Pedicure", "A pedicure is a cosmetic treatment for the feet that involves soaking, exfoliating, trimming toenails, and often includes a massage and nail polish application for beauty and hygiene.", "C:\\Users\\Aspire 7\\Downloads\\pedicure.jpgg" )); // Include id

    category.setCategoryList(mockCategoryList);

    category.filterCategory(null); 
    assertNotNull(category.getCategoryList());
    assertEquals(4, category.getCategoryList().size());
    assertEquals("Menicure", category.getCategoryList().get(0).getCategoryName());
    }
    
   @Test
    public void testLoadCategory() {
        System.out.println("Test loadCategory");
        category.setCategoryId(1);
        category.loadCategory();
        assertEquals("Menicure", category.getCategoryName());
        assertEquals("A manicure is a pampering treatment that beautifies and revitalizes your hands and nails. It involves cleaning, shaping, and polishing the nails, along with cuticle care and soothing hand massages. With various colors and styles available, manicures offer a chance to express personal style while promoting relaxation and nail health.", category.getDescription());
        assertEquals("\"C:\\Users\\Aspire 7\\Downloads\\menicure.png\"", category.getPoster());
        
        category.setCategoryId(1);
        category.loadCategory();
        assertEquals("Menicure", category.getCategoryName());
        assertEquals("A manicure is a pampering treatment that beautifies and revitalizes your hands and nails. It involves cleaning, shaping, and polishing the nails, along with cuticle care and soothing hand massages. With various colors and styles available, manicures offer a chance to express personal style while promoting relaxation and nail health.", category.getDescription());
        assertEquals("\"C:\\Users\\Aspire 7\\Downloads\\menicure.png\"", category.getPoster());
        
    } 
    
    @Test
public void testSearchCategories() {
    System.out.println("Test searchCategory");
    category.setCategoryName("Nail Art");
    category.setDescription(""); 
    category.setPoster("");

    List<Category> result = category.searchCategory();
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Nail Art", result.get(0).getCategoryName());
    assertEquals("Artistic nail designs", result.get(0).getDescription());
    assertEquals("nailart.jpg", result.get(0).getPoster());

    assertEquals("Nail Art", result.get(1).getCategoryName());
    assertEquals("Creative nail designs", result.get(1).getDescription());
    assertEquals("creative.jpg", result.get(1).getPoster());
    category.setCategoryName("Manicure");
    category.setDescription(""); 
    category.setPoster(""); 
    result = category.searchCategory();
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Manicure", result.get(0).getCategoryName());
    assertEquals("Nail care and polish", result.get(0).getDescription());
    assertEquals("manicure.jpg", result.get(0).getPoster());
}

    
    @Test
    public void testInit() {
    int testcategoryId = 1;
    Category instance = new Category();
    instance.setCategoryId(testcategoryId); 


    CategoryDAO testCategoryDAO = new CategoryDAO() {
        public List<Category> getById(Integer categoryId) {
            List<Category> category = new ArrayList<>();
            if (categoryId == 1) {
                category.add(new Category(1, "Pedicure", "A pedicure is a cosmetic treatment for the feet that involves soaking, exfoliating, trimming toenails, and often includes a massage and nail polish application for beauty and hygiene.", "\"C:\\Users\\Aspire 7\\Downloads\\pedicure.jpg\"")); // Provide an id
            }
            return category;
        }
    };
    instance.setCategoryDAO(testCategoryDAO);
    instance.init(); 
    assertEquals("Menicure", instance.getCategoryName());     
    assertEquals("A manicure is a pampering treatment that beautifies and revitalizes your hands and nails. It involves cleaning, shaping, and polishing the nails, along with cuticle care and soothing hand massages. With various colors and styles available, manicures offer a chance to express personal style while promoting relaxation and nail health.", instance.getDescription());
    assertEquals("\"C:\\Users\\Aspire 7\\Downloads\\menicure.png\"", instance.getPoster());

    }
    
    @Test
    public void testgetByID_NonExistentId() {
    // Arrange
    int nonExistentcategoryId = 999; // An ID that doesn't exist
    Category instance = new Category(testCategoryDAO);
    String result = instance.getById(nonExistentcategoryId);
    assertEquals("index", result); 
}
    
}
