import io.qameta.atlas.webdriver.ElementsCollection;
import io.qameta.atlas.webdriver.WebPage;
import io.qameta.atlas.webdriver.extension.FindBy;
import org.openqa.selenium.WebElement;

public interface AtlasElementCollections extends WebPage {

    @FindBy("//ol[@class = \"products list items product-items\"]/li")
    ElementsCollection<WebElement> prodCollection();

    @FindBy("//div[@class=\"product details product-item-details\"]")
    ElementsCollection<WebElement> candlesCollection();

    @FindBy("//span[starts-with(@id,\"product-price\")]")
    ElementsCollection<WebElement> priceItem();
}
