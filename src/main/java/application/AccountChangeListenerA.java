package application;

import framework.annotations.EventListener;
import framework.annotations.Service;

@Service
public class AccountChangeListenerA {
    @EventListener
    public void listen(AccountChangeEvent accountChangeEvent){
        System.out.println("AccountChangeListenerA: "+accountChangeEvent);
    }
}
