package nl.tudelft.contextproject.tygron.api.loaders;

/**
 * A loader provides an abstraction for loading objects from the API.
 * A loader caches it's last result.
 * @param <T> the class that this loader provides.
 */
public abstract class Loader<T> {
  private T cached;

  public Loader() {
  }

  /**
   * Provides an object. Other classes should not call this function.
   * @return an object provided by this loader
   */
  protected abstract T load();

  /**
   * Reload caches the result of load and returns the object
   * @return the result of load
   */
  public T reload() {
    cached = load();
    return cached;
  }

  /**
   * Return cached object or loads it if it has not been loaded already.
   * @return cached object.
   */
  public T get() {
    if (cached == null) {
      cached = load();
    }
    return cached;
  }

  /**
   * Provides information about the class that this Loader loads.
   * @return the class this Loader loads.
   */
  public abstract Class<T> getDataClass();

  /**
   * Describes how often this Loader should be loaded.
   * @return NEVER, NORMAL
   */
  public abstract RefreshInterval getRefreshInterval();

  public enum RefreshInterval {
    NEVER, NORMAL
  }
}
