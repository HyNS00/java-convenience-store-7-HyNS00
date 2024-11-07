package store.loader;

import java.util.List;

public class TestLoader extends Loader<List<String>> {
    private final String testPath;

    public TestLoader(String testPath) {
        this.testPath = testPath;
    }

    @Override
    public List<String> load() {
        return loadFromMd(testPath);
    }
}
