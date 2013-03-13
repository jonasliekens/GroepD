package be.kdg.web.forms;

/**
 * User: Bart Verhavert
 * Date: 13/03/13 09:13
 */
public class ExpenseForm {
    private Double price;
    private String description;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
