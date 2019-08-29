package ar.com.mercadolibre.productsearch.gsonAdapters;

import ar.com.mercadolibre.productsearch.model.SearchResult;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public class SearchResultAdapter extends TypeAdapter<SearchResult> {

    @Override
    public void write(final JsonWriter out, final SearchResult value) throws IOException {

    }

    @Override
    public SearchResult read(final JsonReader in) throws IOException {
        return null;
    }
}
