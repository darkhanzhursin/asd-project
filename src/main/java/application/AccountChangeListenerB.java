package application;

import framework.annotations.EventListener;
import framework.annotations.Service;

@Service
public class AccountChangeListenerB {
    @EventListener
    public void listen(AccountChangeEvent accountChangeEvent){
        System.out.println("AccountChangeListenerB: "+accountChangeEvent);
    }
}
