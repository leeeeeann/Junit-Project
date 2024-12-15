package pojo;

import DAO.locationDAO;  // Updated to use locationDAO
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean
@ViewScoped
public class Location implements java.io.Serializable {

    private Integer id;
    private String city;
    private String branchLocation;
    private String searchCriteria;  // For the search filter
    private List<Location> locationList;
    private locationDAO locationDAO = new locationDAO();  // Uses locationDAO with lowercase

    public Location() {
        this.locationDAO = new locationDAO(); // or any other initialization
    }
    
    public Location(locationDAO locationDAO) {
        this.locationDAO = locationDAO;
    }

    public void setLocationDAO(locationDAO locationDAO) {
        this.locationDAO = locationDAO;
    }

    public Location(Integer id, String city, String branchLocation) {
        this.id = id;
        this.city = city;
        this.branchLocation = branchLocation;
    }
    
    @PostConstruct
    public void init() {
        if (id != null) {
            List<Location> LocationData = locationDAO.getById(id);
            if (LocationData != null && !LocationData.isEmpty()) {
                Location loc = LocationData.get(0);
                this.city = loc.getCity();        // Set waktu dari data database
                this.branchLocation = loc.getBranchLocation(); // Set layanan dari data database
            }
        }
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBranchLocation() {
        return branchLocation;
    }

    public void setBranchLocation(String branchLocation) {
        this.branchLocation = branchLocation;
    }
    
    public String getSearchCriteria() { return searchCriteria; }
    public void setSearchCriteria(String searchCriteria) { this.searchCriteria = searchCriteria; }

    
    // Method to retrieve all locations
    public List<Location> getLocationList() {
        if (locationList == null || searchCriteria == null || searchCriteria.isEmpty()) {
            locationList = locationDAO.retrieveTblLocations();
        }
        return locationList;
    }
    
    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public void filterLocation(AjaxBehaviorEvent event) {
        if (searchCriteria != null && !searchCriteria.isEmpty()) {
            locationList = locationDAO.searchLocation(searchCriteria, null, null);
        } else {
            locationList = locationDAO.retrieveTblLocations();
        }
    }
    // Method to save a new location
    public String saveLocation() {
        locationDAO.addLocation(this);
        return "adminlocation"; // Navigate back to location admin page
    }
    
    
    // Method to edit location
    public String editLocation() {
        Location location = new Location();
        location.setId(this.id);
        location.setCity(this.city);
        location.setBranchLocation(this.branchLocation);
        locationDAO.editLocation(this);
        return "adminlocation.xhtml?faces-redirect=true"; // Navigate back to location admin page
    }
    
    
    // Method to delete location
    public String deleteLocation() {
    if (this.id != null) {
        locationDAO locationDAO = new locationDAO();
        locationDAO.deleteLocation(this.id);
    }   
        return "adminlocation"; // Navigate back to location admin page
    }
    
    
    // Method to get location by ID
    
    public String getById(int locationId) {
        List<Location> locationData = locationDAO.getById(locationId);
        if (locationData != null && !locationData.isEmpty()) {
            this.id = locationData.get(0).getId();
            this.city = locationData.get(0).getCity();
            this.branchLocation = locationData.get(0).getBranchLocation();
            return "adminlocation"; // Should return a string
        }
        System.out.println("Location not found for ID: " + locationId);
        return "index"; // Should return a string
    }
    
public void loadLocation() {
        if (id != null) {
            List<Location> locationList = locationDAO.getById(id);
            if (locationList != null && !locationList.isEmpty()) {
                Location loc = locationList.get(0);
                this.city = loc.getCity(); // Load time from DB
                this.branchLocation = loc.getBranchLocation(); // Load services from DB
            }
        }
    }

public List<Location> searchLocations() {
        return locationDAO.searchLocation(null, city, branchLocation);
}

}

