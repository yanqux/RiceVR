package rice.ec.vr;

import java.util.EventListener;

public interface DataServiceListener<E> extends EventListener {
    void onQuery(E result, int itemCount);
}
