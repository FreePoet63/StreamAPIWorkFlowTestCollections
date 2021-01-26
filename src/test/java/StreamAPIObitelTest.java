import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.webdriver.WebDriverConfiguration;
import io.qameta.atlas.webdriver.WebPage;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class StreamAPIObitelTest {

    FirefoxDriver driver;
    Atlas atlas;

    @BeforeClass
    public void startFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        atlas = new Atlas(new WebDriverConfiguration(driver));
        onPage(ElementPage.class).open("https://lavka-obitel.ru/");
    }

    private <T extends WebPage> T onPage(Class<T> page) {
        return atlas.create(driver, page);
    }

    @Attachment(value = "screenshot", type = "image/PNG")
    public byte[] screenshotPNG() {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    @Test
    @Description("Working with a collection using, StreamAPI")
    @Severity(SeverityLevel.NORMAL)
    @Story("Workflow with collections 'Utensils'")
    public void sortingListItems() throws InterruptedException {
        Thread.sleep(5000);
        onPage(ElementPage.class).eleObitel("Утварь").click();
        onPage(ElementPage.class).eleObitel("Четки").click();
        List<WebElement> ele = onPage(AtlasElementCollections.class).prodCollection();
        ArrayList<String> allItems = new ArrayList<>();
        ele.stream().forEach(webElement -> allItems.add(webElement.getText()));
        System.out.println(allItems);
        assertThat(allItems, hasItems(containsString("Браслет"), containsString("Четки")));
        List<String> itemsAll = ele.stream().map(webElement -> webElement.getText()).filter(s -> s.contains("Браслет"))
                .collect(Collectors.toList());
        System.out.println(itemsAll);
        assertThat(itemsAll, hasItem(containsString("Браслет")));
        screenshotPNG();
    }

    @Test
    @Description("Working with a collection using, StreamAPI")
    @Severity(SeverityLevel.NORMAL)
    @Story("Workflow with collections 'Utensils'")
    public void sortingListCandles() throws InterruptedException {
        Thread.sleep(5000);
        onPage(ElementPage.class).eleObitel("Утварь").click();
        onPage(ElementPage.class).eleObitel("Церковные свечи").click();
        List<WebElement> elle = onPage(AtlasElementCollections.class).candlesCollection();
        Set<String> allCandles = elle.stream().map(webElement -> webElement.getText()).collect(Collectors.toSet());
        System.out.println(allCandles);
        assertThat(allCandles, hasItems(containsString("Набор паломника"),
                containsString("Свечи для домашней молитвы"),
                containsString("Поминальные свечи для домашней молитвы")));
        Set<String> candlesFilter = allCandles.stream().filter(s -> s.contains("Свечи для домашней молитвы"))
                .collect(Collectors.toSet());
        System.out.println(candlesFilter);
        assertThat(candlesFilter, hasItems(containsString("Неупиваемая Чаша"),
                containsString("Серафиму Саровскому")));
        screenshotPNG();
    }

    @Test
    @Description("Working with a collection using, StreamAPI")
    @Severity(SeverityLevel.NORMAL)
    @Story("Workflow with collections 'Utensils'")
    public void sortItemsCollections() throws InterruptedException {
        Thread.sleep(5000);
        onPage(ElementPage.class).eleObitel("Утварь").click();
        onPage(ElementPage.class).eleObitel("Дарохранительницы").click();
        List<WebElement> el = onPage(AtlasElementCollections.class).prodCollection();
        List<String> sortElementProduct = el.stream().map(webElement -> webElement.getText()).collect(Collectors.toList());
        System.out.println(sortElementProduct);
        assertThat(sortElementProduct, hasItems(containsString("DR-05"), containsString("DR-06")));
        List<WebElement> price = onPage(AtlasElementCollections.class).priceItem();
        List<String> priceProd = price.stream().map(webElement -> webElement.getText()).limit(2)
                .collect(Collectors.toList());
        Assert.assertTrue(priceProd.size() == 2);
        System.out.println(priceProd);
        screenshotPNG();
    }

    @Test
    @Description("Working with a collection using, StreamAPI")
    @Severity(SeverityLevel.NORMAL)
    @Story("Workflow with collections 'Books'")
    public void setBooks() throws InterruptedException {
        Thread.sleep(5000);
        onPage(ElementPage.class).eleObitel("Книги").click();
        onPage(ElementPage.class).elementObitel("Все Книги").click();
        List<WebElement> book = onPage(AtlasElementCollections.class).prodCollection();
        List<String> allBooks = Collections.singletonList(book.stream().map(webElement -> webElement.getText()).
                max(String::compareTo).get());
        System.out.println(allBooks);
        assertThat(allBooks, hasItem(containsString("подарок")));
        List<WebElement> elyo = onPage(AtlasElementCollections.class).priceItem();
        List<String> itemsCode = elyo.stream().map(webElement -> webElement.getText())
                .map(s -> s.replace(" руб.", "")).collect(Collectors.toList());
        int[] itemsNumber = itemsCode.stream().mapToInt(Integer::parseInt)
                .filter(value -> value >= 50 && value <= 300).toArray();
        List<Integer> ints = Arrays.stream(itemsNumber).boxed().sorted().collect(Collectors.toList());
        System.out.println(ints);
        for (int i = 1; i < itemsNumber.length; i++) {
            if (itemsNumber[i] <= itemsNumber[i - 1]) {
                System.out.println("True");
            }
        }
        screenshotPNG();
    }

    @Test
    @Description("Working with a collection using, StreamAPI")
    @Severity(SeverityLevel.NORMAL)
    @Story("Workflow with collections 'Ceramics'")
    public void setCeramics() throws InterruptedException {
        Thread.sleep(7000);
        onPage(ElementPage.class).eleObitel("Керамика").click();
        onPage(ElementPage.class).eleObitel("Керамическая посуда").click();
        List<WebElement> dishesItem = onPage(AtlasElementCollections.class).prodCollection();
        List<String> dishesElement = dishesItem.stream().map(webElement -> webElement.getText()).sorted()
                .collect(Collectors.toList());
        System.out.println(dishesElement);
        assertThat(dishesElement, hasItems(containsString("Чайник \"Улыбка\""),containsString(
                "Чашка \"Мечта\"")));
        dishesElement.stream().filter(Objects::nonNull).filter(s -> !s.isEmpty() && s.contains("Чайник"))
                .forEach(System.out::println);
        screenshotPNG();
    }

    @Test
    @Description("Working with a collection using, StreamAPI")
    @Severity(SeverityLevel.NORMAL)
    @Story("Workflow with collections 'Children'")
    public void setChildren() throws InterruptedException {
        Thread.sleep(5000);
        onPage(ElementPage.class).eleObitel("Для детей").click();
        onPage(ElementPage.class).elementObitel("Детское аудио и видео").click();
        List<WebElement> childrenProduct = onPage(AtlasElementCollections.class).priceItem();
        List<String> childrenElement = childrenProduct.stream().map(webElement -> webElement.getText())
                .map(s -> s.replace(" руб.", "")).filter(
                        s -> s.contains("120") || s.contains("150") || s.contains("200"))
                .collect(Collectors.toList());
        assertThat(childrenElement,hasItems("120", "150", "200"));
        System.out.println(childrenElement);
        int[] childrenSum = childrenElement.stream().mapToInt(Integer::parseInt).filter(
                value -> value <= 130 || value <= 1000).toArray();
        Set<Integer> intChild = Arrays.stream(childrenSum).boxed().collect(Collectors.toCollection(TreeSet::new));
        System.out.println(intChild);
        Assert.assertTrue(intChild.size() == 3);
        screenshotPNG();
    }

    @Test
    @Description("Working with a collection using, StreamAPI")
    @Severity(SeverityLevel.NORMAL)
    @Story("Workflow with collections 'Crosses'")
    public void setCrosses() throws InterruptedException {
        Thread.sleep(5000);
        onPage(ElementPage.class).eleObitel("Кресты").click();
        onPage(ElementPage.class).elementObitel("Все Кресты").click();
        List<WebElement> onCrosses = onPage(AtlasElementCollections.class).prodCollection();
        List<String> itemsCrosses = onCrosses.stream().map(webElement -> webElement.getText()).skip(12)
                .collect(Collectors.toList());
        System.out.println(itemsCrosses);
        assertThat(itemsCrosses, hasItems(containsString("Крест Голгофа"),containsString("" +
                "Крест наперсный"),containsString("Крест деревянный")));
        Optional<String> crosses = itemsCrosses.stream().findFirst();
        System.out.println(crosses);
        screenshotPNG();
    }

    @Test
    @Description("Working with a collection using, StreamAPI")
    @Severity(SeverityLevel.NORMAL)
    @Story("Workflow with collections 'Clothes'")
    public void setClothes() throws InterruptedException {
        Thread.sleep(5000);
        onPage(ElementPage.class).eleObitel("Швейная продукция").click();
        Actions actions = new Actions(driver);
        WebElement elementCloth = onPage(ElementPage.class).eleObitel("Облачения");
        actions.moveToElement(elementCloth).build().perform();
        onPage(ElementPage.class).eleObitel("Архиерейские облачения").click();
        List<WebElement> setClothes = onPage(AtlasElementCollections.class).priceItem();
        List<String> clothesElement = setClothes.stream().map(webElement -> webElement.getText())
                .map(s -> s.replace(" руб.", ""))
                .map(s -> s.replace(" ", "")).collect(Collectors.toList());
        System.out.println(clothesElement);
        assertThat(clothesElement,hasItems("57200", "89000", "108300"));
        int[] clothesNumber = clothesElement.stream().mapToInt(Integer::parseInt).toArray();
        List<Integer> clothes = Arrays.stream(clothesNumber).boxed().sorted().collect(Collectors.toList());
        System.out.println(clothes);
        Assert.assertTrue(clothes.get(0) < clothes.get(1) || clothes.get(1) < clothes.get(2));
        screenshotPNG();
    }

    @Test
    @Description("Working with a collection using, StreamAPI")
    @Severity(SeverityLevel.NORMAL)
    @Story("Workflow with collections 'Icons'")
    public void setIcons() throws InterruptedException {
        Thread.sleep(5000);
        onPage(ElementPage.class).eleObitel("Иконы").click();
        onPage(ElementPage.class).elementObitel("Все Иконы").click();
        List<WebElement> icons = onPage(AtlasElementCollections.class).prodCollection();
        List<String> itemIcons = icons.stream().map(webElement -> webElement.getText()).filter(
                s -> s.contains("Спасител")).collect(Collectors.toList());
        System.out.println(itemIcons);
        assertThat(itemIcons,hasItem(containsString("Икона Спасителя")));
        Assert.assertTrue(itemIcons.size() >= 2);
        screenshotPNG();
    }

    @AfterClass
    public void finishFirefoxDriver () {
        driver.quit();
    }
}

