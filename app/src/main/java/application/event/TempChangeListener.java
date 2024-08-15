package application.event;

import framework.annotations.EventListener;
import framework.annotations.Service;

@Service
public class TempChangeListener {

    @EventListener
    public void listen(TempChangeEvent event) {
        System.out.println("TempChangeListener: " + event);
    }
}
