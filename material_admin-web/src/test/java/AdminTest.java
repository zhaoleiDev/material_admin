import com.zhaolei.material.admin.domain.dao.OrganizationDO;
import com.zhaolei.material.admin.service.OrganizationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

//@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
//@ContextConfiguration(locations={"classpath*:/spring/application.xml"}) //加载配置文件
public class AdminTest {
    @Autowired
    private OrganizationService organizationService;

    /*@Test
    public void test1(){
        OrganizationDO organizationDO = new OrganizationDO(5,"电子信息工程","赵磊","222016333210145","13060219411","电子信息工程","token",new Date(),new Date(),0);

        System.out.println(organizationService.selectById(1));
    }*/
}
