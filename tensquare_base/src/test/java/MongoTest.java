import com.tensquare.base.BaseApplication;
import com.tensquare.base.domain.Label;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @program: tensquare
 * @description:
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= BaseApplication.class)
public class MongoTest {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Test
    public void test1(){
        mongoTemplate.createCollection(Label.class);
        Label label = new Label();
        label.setLabelname("xyz");
        mongoTemplate.save(label);
    }

}
