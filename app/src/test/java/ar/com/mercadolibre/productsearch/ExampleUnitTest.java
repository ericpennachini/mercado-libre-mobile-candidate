package ar.com.mercadolibre.productsearch;

import org.junit.Test;

import ar.com.mercadolibre.productsearch.model.Product;
import ar.com.mercadolibre.productsearch.model.SearchResult;
import ar.com.mercadolibre.productsearch.service.SearchService;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_seachService_getByExample() {
        SearchService searchService = new SearchService();
        try {
            SearchResult searchResult = searchService.getProductsByNameExample("mochila", null, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}