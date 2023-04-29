package com.wu.mapper;

package com.wu.mapper;

import com.wu.entity.Cart;
import com.wu.entity.Dish;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DishMapperTests {

    @Autowired
    private DishMapper mapper;

    @Test
    public void insert() {
        Dish cart = new Cart();
        cart.setUid(1);
        cart.setPid(2);
        cart.setNum(3);
        cart.setPrice(4L);
        Integer rows = mapper.insert(cart);
        System.err.println("rows=" + rows);
        System.err.println(cart);
    }

}
