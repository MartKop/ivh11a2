package avans.ivh11.mart.demo.Service;

import avans.ivh11.mart.demo.Domain.OrderOption;
import org.springframework.stereotype.Service;

@Service
public class TypeChecker {
    public boolean isOrderOption(Object object) {
        return object instanceof OrderOption;
    }
}
