package nl.tudelft.contextproject.tygron.api.loaders;

public abstract class Loader<T> {
  private T cached;

  public Loader() {
  }  

  protected abstract T load();

  public T reload() {
    cached = load();
    return cached;
  }

  /**
   * Return cached Loader.
   * @return cached loader.
   */
  public T get() {
    if (cached == null) {
      cached = load();
    }
    return cached;
  }

  public abstract Class<T> getDataClass();

  public abstract RefreshInterval getRefreshInterval();

  public enum RefreshInterval {
    NEVER, NORMAL
  }
}
