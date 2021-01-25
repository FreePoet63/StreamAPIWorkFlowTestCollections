import io.qameta.atlas.webdriver.AtlasWebElement;
import io.qameta.atlas.webdriver.WebPage;
import io.qameta.atlas.webdriver.extension.FindBy;
import io.qameta.atlas.webdriver.extension.Param;

public interface ElementPage extends WebPage {

    @SuppressWarnings("rawtypes")
    @FindBy("//span[contains(text(), '{{ text }}')]")
    AtlasWebElement eleObitel (@Param("text") String text);

    @FindBy("//a[contains(text(), '{{ text }}')]")
    AtlasWebElement elementObitel (@Param("text") String text);
}
