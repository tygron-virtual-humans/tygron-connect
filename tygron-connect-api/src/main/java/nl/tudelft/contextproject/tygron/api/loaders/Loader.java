package nl.tudelft.contextproject.tygron.api.loaders;

import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.api.Session;

public abstract class Loader<T> {
  private T cached;

  HttpConnection connection;
  Session session;

  public Loader(HttpConnection connection, Session session) {
    this.connection = connection;
    this.session = session;
  }

  protected abstract T load();

  public T reload() {
    cached = load();
    return cached;
  }

  public T get() {
    if (cached == null) {
      throw new RuntimeException("Get called on a not-cached object");
    }
    return cached;
  }

  public abstract Class<T> getDataClass();
}
